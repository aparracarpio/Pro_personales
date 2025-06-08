package gym.entrenos.gestores;

import gym.entrenos.nutricion.Dieta;
import gym.entrenos.nutricion.Comida;
import gym.entrenos.nutricion.Ingrediente;
import gym.entrenos.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorDietas {
    private final List<Dieta> dietas;
    
    public GestorDietas() {
        this.dietas = new ArrayList<>();
        cargarDietasDesdeBD();
    }
    
    private void cargarDietasDesdeBD() {
        String sql = "SELECT * FROM dietas";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Dieta dieta = new Dieta(
                    rs.getString("tipo_dieta")
                );
                dieta.setComidas(cargarComidasParaDieta(rs.getInt("id_dieta")));
                dietas.add(dieta);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar dietas: " + e.getMessage());
        }
    }
    
    private List<Comida> cargarComidasParaDieta(int idDieta) {
        List<Comida> comidas = new ArrayList<>();
        String sql = "SELECT * FROM comidas WHERE id_dieta = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idDieta);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Comida comida = new Comida(
                    rs.getInt("numero_comida"),
                    rs.getTime("hora").toLocalTime()
                );
                comida.setIngredientes(cargarIngredientesParaComida(rs.getInt("id_comida")));
                comidas.add(comida);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar comidas: " + e.getMessage());
        }
        
        return comidas;
    }
    
    private List<Ingrediente> cargarIngredientesParaComida(int idComida) {
        List<Ingrediente> ingredientes = new ArrayList<>();
        String sql = "SELECT i.* FROM ingredientes i " +
                    "JOIN comida_ingredientes ci ON i.id_ingrediente = ci.id_ingrediente " +
                    "WHERE ci.id_comida = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idComida);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Ingrediente ingrediente = new Ingrediente(
                    rs.getString("nombre"),
                    rs.getDouble("calorias"),
                    rs.getDouble("proteinas"),
                    rs.getDouble("carbohidratos"),
                    rs.getDouble("grasas")
                );
                ingredientes.add(ingrediente);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar ingredientes: " + e.getMessage());
        }
        
        return ingredientes;
    }
    
    public void crearDieta(Dieta dieta) {
        String sql = "INSERT INTO dietas (tipo_dieta) VALUES (?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, dieta.getTipoDieta());
            pstmt.executeUpdate();
            
            // Obtener el ID generado
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idDieta = generatedKeys.getInt(1);
                    
                    // Guardar comidas
                    for (Comida comida : dieta.getComidas()) {
                        guardarComida(comida, idDieta);
                    }
                }
            }
            
            dietas.add(dieta);
        } catch (SQLException e) {
            System.err.println("Error al crear dieta: " + e.getMessage());
        }
    }
    
    private void guardarComida(Comida comida, int idDieta) throws SQLException {
        String sql = "INSERT INTO comidas (id_dieta, numero_comida, hora) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, idDieta);
            pstmt.setInt(2, comida.getNumeroComida());
            pstmt.setTime(3, Time.valueOf(comida.getHora()));
            pstmt.executeUpdate();
            
            // Obtener el ID generado
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idComida = generatedKeys.getInt(1);
                    
                    // Guardar ingredientes
                    for (Ingrediente ingrediente : comida.getIngredientes()) {
                        guardarIngredienteParaComida(ingrediente, idComida);
                    }
                }
            }
        }
    }
    
    private void guardarIngredienteParaComida(Ingrediente ingrediente, int idComida) throws SQLException {
        // Primero verificar si el ingrediente ya existe
        int idIngrediente = obtenerIdIngrediente(ingrediente);
        if (idIngrediente == -1) {
            idIngrediente = crearIngrediente(ingrediente);
        }
        
        // Relacionar ingrediente con comida
        String sql = "INSERT INTO comida_ingredientes (id_comida, id_ingrediente) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idComida);
            pstmt.setInt(2, idIngrediente);
            pstmt.executeUpdate();
        }
    }
    
    private int obtenerIdIngrediente(Ingrediente ingrediente) throws SQLException {
        String sql = "SELECT id_ingrediente FROM ingredientes WHERE nombre = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ingrediente.getNombre());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id_ingrediente");
            }
        }
        
        return -1;
    }
    
    private int crearIngrediente(Ingrediente ingrediente) throws SQLException {
        String sql = "INSERT INTO ingredientes (nombre, calorias, proteinas, carbohidratos, grasas) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, ingrediente.getNombre());
            pstmt.setDouble(2, ingrediente.getCalorias());
            pstmt.setDouble(3, ingrediente.getProtes());
            pstmt.setDouble(4, ingrediente.getCarbos());
            pstmt.setDouble(5, ingrediente.getGrasas());
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        
        return -1;
    }
    
    // Otros métodos CRUD para Dietas, Comidas e Ingredientes
    public List<Dieta> getTodasDietas() {
        return new ArrayList<>(dietas);
    }
    
    public Dieta buscarDietaPorTipo(String tipo) {
        return dietas.stream()
                    .filter(d -> d.getTipoDieta().equalsIgnoreCase(tipo))
                    .findFirst()
                    .orElse(null);
    }
    
    public void eliminarDieta(Dieta dieta) {
        if (dieta == null) return;
        
        String sql = "DELETE FROM dietas WHERE tipo_dieta = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, dieta.getTipoDieta());
            pstmt.executeUpdate();
            
            dietas.remove(dieta);
        } catch (SQLException e) {
            System.err.println("Error al eliminar dieta: " + e.getMessage());
        }
    }
    
    public void actualizarDieta(Dieta dieta) {
        if (dieta == null) return;
        
        // En este caso simple, solo actualizamos en la lista
        int index = dietas.indexOf(dieta);
        if (index != -1) {
            dietas.set(index, dieta);
        }
    }
    
    public List<Dieta> buscarDietasPorIngrediente(String nombreIngrediente) {
        return dietas.stream()
                .filter(d -> d.getComidas().stream()
                        .anyMatch(c -> c.getIngredientes().stream()
                                .anyMatch(i -> i.getNombre().equalsIgnoreCase(nombreIngrediente))))
                .collect(Collectors.toList());
    }

    public void guardarIngrediente(Ingrediente ingrediente) {
        try {
            crearIngrediente(ingrediente); // Reutiliza el método privado existente
        } catch (SQLException e) {
            System.err.println("Error al guardar ingrediente: " + e.getMessage());
        }
    }

    public void actualizarIngrediente(Ingrediente ingrediente) {
        String sql = "UPDATE ingredientes SET nombre = ? WHERE nombre = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ingrediente.getNombre());
            pstmt.setString(2, ingrediente.getNombre());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar ingrediente: " + e.getMessage());
        }
    }

    public void eliminarIngrediente(Ingrediente ingrediente) {
        String sql = "DELETE FROM ingredientes WHERE nombre = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ingrediente.getNombre());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar ingrediente: " + e.getMessage());
        }
    }

    public void actualizarComida(Comida comida) {
        String sql = "UPDATE comidas SET hora = ? WHERE numero_comida = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setTime(1, Time.valueOf(comida.getHora()));
            pstmt.setInt(2, comida.getNumeroComida());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar comida: " + e.getMessage());
        }
    }
}