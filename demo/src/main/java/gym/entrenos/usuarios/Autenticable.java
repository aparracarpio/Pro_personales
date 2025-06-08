package gym.entrenos.usuarios;

public interface Autenticable {
    boolean checkPassword(String password); // Método que deben implementar Cliente y Equipo
}