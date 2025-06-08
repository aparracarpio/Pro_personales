package gym.entrenos.nutricion;

public class Ingrediente {
    private String nombre;
    private double calorias;
    private double protes;
    private double carbos;
    private double grasas;

    public Ingrediente(String nombreIngrediente, double calorias, double protes, double carbos, double grasas) {
        this.nombre = nombreIngrediente;
        this.calorias = calorias;
        this.protes = protes;
        this.carbos = carbos;
        this.grasas = grasas;
    }

    // ===== Getters =====
    public String getNombre() {
        return nombre;
    }

    public double getCalorias() {
        return calorias;
    }

    public double getProtes() {
        return protes;
    }

    public double getCarbos() {
        return carbos;
    }

    public double getGrasas() {
        return grasas;
    }

    // ===== Setters =====
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public void setProtes(double protes) {
        this.protes = protes;
    }

    public void setCarbos(double carbos) {
        this.carbos = carbos;
    }

    public void setGrasas(double grasas) {
        this.grasas = grasas;
    }
}