package gym.entrenos.gestores;

import gym.entrenos.usuarios.Cliente;
import gym.entrenos.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios {
    private final List<Cliente> clientes;

    public GestorUsuarios() {
        this.clientes = new ArrayList<>();
        cargarClientesDesdeBD();
    }
    
    private void cargarClientesDesdeBD() {
        String sql = "SELECT * FROM clientes";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("password_hash")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar clientes: " + e.getMessage());
        }
    }
    
    public void altaCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (id, nombre, apellido, telefono, email, password_hash) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, cliente.getIdUsr());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getApellidos());
            pstmt.setString(4, cliente.getTelefono());
            pstmt.setString(5, cliente.getCorreo());
            pstmt.setString(6, cliente.getPasswordHash());
            
            pstmt.executeUpdate();
            clientes.add(cliente); // Añadir a la caché
            System.out.println("Cliente registrado en la BD: " + cliente.getNombre());
        } catch (SQLException e) {
            System.err.println("Error al registrar cliente: " + e.getMessage());
        }
    }
    
    public Cliente buscarClientePorId(int id) {
        // Primero buscar en caché
        for (Cliente c : clientes) {
            if (c.getIdUsr() == id) {
                return c;
            }
        }
        
        // Si no está en caché, buscar en BD
        String sql = "SELECT * FROM clientes WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("password_hash")
                );
                clientes.add(cliente); // Añadir a caché
                return cliente;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        
        return null;
    }
    
    public int getTotalClientes() {
        String sql = "SELECT COUNT(*) AS total FROM clientes";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error al contar clientes: " + e.getMessage());
        }
        
        return 0;
    }

    public Cliente getCliente(int id) {
        return clientes.stream()
                      .filter(c -> c.getIdUsr() == id)
                      .findFirst()
                      .orElse(null);
    }
}