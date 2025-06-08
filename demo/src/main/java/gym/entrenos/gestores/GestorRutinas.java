package gym.entrenos.gestores;

import gym.entrenos.rutinas.Rutina;
import gym.entrenos.rutinas.Ejercicio;
import gym.entrenos.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorRutinas {
    private final List<Rutina> rutinas;
    
    public GestorRutinas() {
        this.rutinas = new ArrayList<>();
        cargarRutinasDesdeBD();
    }
    
    private void cargarRutinasDesdeBD() {
        String sql = "SELECT * FROM rutinas";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Rutina rutina = new Rutina(
                    rs.getString("tipo_rutina"),
                    rs.getString("enfoque"),
                    rs.getString("consejos"),
                    cargarEjerciciosParaRutina(rs.getInt("id_rutina"))
                );
                rutinas.add(rutina);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar rutinas: " + e.getMessage());
        }
    }
    
    private List<Ejercicio> cargarEjerciciosParaRutina(int idRutina) {
        List<Ejercicio> ejercicios = new ArrayList<>();
        String sql = "SELECT e.* FROM ejercicios e " +
                    "JOIN rutina_ejercicios re ON e.id_ejercicio = re.id_ejercicio " +
                    "WHERE re.id_rutina = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idRutina);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Ejercicio ejercicio = new Ejercicio(
                    rs.getString("nombre"),
                    rs.getString("grupo_muscular"),
                    rs.getString("descripcion"),
                    rs.getString("ejecucion"),
                    rs.getInt("series"),
                    rs.getInt("repeticiones")
                );
                ejercicios.add(ejercicio);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar ejercicios: " + e.getMessage());
        }
        
        return ejercicios;
    }
    
    public void crearRutina(Rutina rutina) {
        String sql = "INSERT INTO rutinas (tipo_rutina, enfoque, consejos) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, rutina.getTipoRutina());
            pstmt.setString(2, rutina.getEnfoque());
            pstmt.setString(3, rutina.getConsejos());
            pstmt.executeUpdate();
            
            // Obtener el ID generado
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idRutina = generatedKeys.getInt(1);
                    
                    // Guardar ejercicios
                    for (Ejercicio ejercicio : rutina.getEjercicios()) {
                        guardarEjercicioParaRutina(ejercicio, idRutina);
                    }
                }
            }
            
            rutinas.add(rutina);
        } catch (SQLException e) {
            System.err.println("Error al crear rutina: " + e.getMessage());
        }
    }
    
    private void guardarEjercicioParaRutina(Ejercicio ejercicio, int idRutina) throws SQLException {
        // Primero verificar si el ejercicio ya existe
        int idEjercicio = obtenerIdEjercicio(ejercicio);
        if (idEjercicio == -1) {
            idEjercicio = crearEjercicio(ejercicio);
        }
        
        // Relacionar ejercicio con rutina
        String sql = "INSERT INTO rutina_ejercicios (id_rutina, id_ejercicio) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idRutina);
            pstmt.setInt(2, idEjercicio);
            pstmt.executeUpdate();
        }
    }
    
    private int obtenerIdEjercicio(Ejercicio ejercicio) throws SQLException {
        String sql = "SELECT id_ejercicio FROM ejercicios WHERE nombre = ? AND grupo_muscular = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ejercicio.getNombre());
            pstmt.setString(2, ejercicio.getGrupoMuscular());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id_ejercicio");
            }
        }
        
        return -1;
    }
    
    private int crearEjercicio(Ejercicio ejercicio) throws SQLException {
        String sql = "INSERT INTO ejercicios (nombre, grupo_muscular, descripcion, ejecucion, series, repeticiones) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, ejercicio.getNombre());
            pstmt.setString(2, ejercicio.getGrupoMuscular());
            pstmt.setString(3, ejercicio.getDescripcion());
            pstmt.setString(4, ejercicio.getEjecucion());
            pstmt.setInt(5, ejercicio.getSeries());
            pstmt.setInt(6, ejercicio.getRepeticiones());
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        
        return -1;
    }
    
    // Otros métodos CRUD para Rutinas y Ejercicios
    public List<Rutina> getTodasRutinas() {
        return new ArrayList<>(rutinas);
    }
    
    public List<Rutina> buscarRutinasPorTipo(String tipo) {
        List<Rutina> resultados = new ArrayList<>();
        for (Rutina r : rutinas) {
            if (r.getTipoRutina().equalsIgnoreCase(tipo)) {
                resultados.add(r);
            }
        }
        return resultados;
    }

    public void eliminarRutina(Rutina rutina) {
        if (rutina == null) return;
        
        String sql = "DELETE FROM rutinas WHERE tipo_rutina = ? AND enfoque = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, rutina.getTipoRutina());
            pstmt.setString(2, rutina.getEnfoque());
            pstmt.executeUpdate();
            
            rutinas.remove(rutina);
        } catch (SQLException e) {
            System.err.println("Error al eliminar rutina: " + e.getMessage());
        }
    }
    
    public void actualizarRutina(Rutina rutina) {
        if (rutina == null) return;
        
        String sql = "UPDATE rutinas SET consejos = ? WHERE tipo_rutina = ? AND enfoque = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, rutina.getConsejos());
            pstmt.setString(2, rutina.getTipoRutina());
            pstmt.setString(3, rutina.getEnfoque());
            pstmt.executeUpdate();
            
            // Actualizar en la lista
            int index = rutinas.indexOf(rutina);
            if (index != -1) {
                rutinas.set(index, rutina);
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar rutina: " + e.getMessage());
        }
    }

    public void guardarEjercicio(Ejercicio ejercicio) {
        try {
            crearEjercicio(ejercicio); // Reutiliza el método privado existente
        } catch (SQLException e) {
            System.err.println("Error al guardar ejercicio: " + e.getMessage());
        }
    }

    public void actualizarEjercicio(Ejercicio ejercicio) {
        String sql = "UPDATE ejercicios SET descripcion = ?, ejecucion = ? WHERE nombre = ? AND grupo_muscular = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ejercicio.getDescripcion());
            pstmt.setString(2, ejercicio.getEjecucion());
            pstmt.setString(3, ejercicio.getNombre());
            pstmt.setString(4, ejercicio.getGrupoMuscular());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar ejercicio: " + e.getMessage());
        }
    }

    public void eliminarEjercicio(Ejercicio ejercicio) {
        String sql = "DELETE FROM ejercicios WHERE nombre = ? AND grupo_muscular = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ejercicio.getNombre());
            pstmt.setString(2, ejercicio.getGrupoMuscular());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar ejercicio: " + e.getMessage());
        }
    }

    public List<Rutina> buscarRutinasPorGrupoMuscular(String grupoMuscular) {
        return rutinas.stream()
                .filter(r -> r.getEjercicios().stream()
                        .anyMatch(e -> e.getGrupoMuscular().equalsIgnoreCase(grupoMuscular)))
                .collect(Collectors.toList());
    }
}