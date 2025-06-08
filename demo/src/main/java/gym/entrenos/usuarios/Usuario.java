package gym.entrenos.usuarios;

import org.mindrot.jbcrypt.BCrypt;

public abstract class Usuario implements Autenticable {
    private int idUsuario;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String correo;
    private String passwordHash;  // Nuevo campo para el hash de la contraseña

    // Constructor actualizado (incluye contraseña en texto plano)
    public Usuario(int id, String nm, String ap, String tele, String gmail, String password) {
        this.idUsuario = id;
        this.nombre = nm;
        this.apellidos = ap;
        this.telefono = tele;
        this.correo = gmail;
        this.setPassword(password);  // Cifra y almacena la contraseña
    }

    // --- Métodos para manejar la contraseña ---
    public void setPassword(String password) {
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    @Override
    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.passwordHash);
    }
    
    public String getPasswordHash() {
        return this.passwordHash;
    }
    
    // --- Getters y Setters existentes (sin cambios) ---
    public int getIdUsr() { return this.idUsuario; }
    public String getNombre() { return this.nombre; }
    public String getApellidos() { return this.apellidos; }
    public String getTelefono() { return this.telefono; }
    public String getCorreo() { return this.correo; }

    public void setIdUsr(int id) { this.idUsuario = id; }
    public void setNombre(String nom) { this.nombre = nom; }
    public void setApellidos(String ap) { this.apellidos = ap; }
    public void setTlf(String tlf) { this.telefono = tlf; }
    public void setGmail(String gm) { this.correo = gm; }
}