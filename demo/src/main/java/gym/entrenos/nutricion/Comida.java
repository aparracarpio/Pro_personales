package gym.entrenos.nutricion;

import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

public class Comida {
    private int numeroComida;
    private LocalTime hora;
    private List<Ingrediente> ingredientes;

    // Constructor
    public Comida(int numeroComida, LocalTime hora) {
        this.numeroComida = numeroComida;
        this.hora = hora;
        this.ingredientes = new ArrayList<>(); // Inicializa la lista vacía
    }

    // Constructor alternativo (con lista de ingredientes)
    public Comida(int numeroComida, LocalTime hora, List<Ingrediente> ingredientes) {
        this.numeroComida = numeroComida;
        this.hora = hora;
        this.ingredientes = new ArrayList<>(ingredientes); // Copia defensiva
    }

    // ===== Getters =====
    public int getNumeroComida() {
        return numeroComida;
    }

    public LocalTime getHora() {
        return hora;
    }

    public List<Ingrediente> getIngredientes() {
        return new ArrayList<>(ingredientes); // Devuelve una copia para proteger la lista original
    }

    // ===== Setters =====
    public void setNumeroComida(int numeroComida) {
        this.numeroComida = numeroComida;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = new ArrayList<>(ingredientes); // Copia defensiva
    }

    // ===== Métodos para manejar ingredientes =====
    public void agregarIngrediente(Ingrediente ingrediente) {
        if (ingrediente != null) {
            ingredientes.add(ingrediente);
        }
    }

    public void eliminarIngrediente(Ingrediente ingrediente) {
        ingredientes.remove(ingrediente);
    }

    public void limpiarIngredientes() {
        ingredientes.clear();
    }

    // Método para calcular el total nutricional de la comida
    public String getResumenNutricional() {
        double caloriasTotales = 0;
        double proteinasTotales = 0;
        double carbosTotales = 0;
        double grasasTotales = 0;

        for (Ingrediente ing : ingredientes) {
            caloriasTotales += ing.getCalorias();
            proteinasTotales += ing.getProtes();
            carbosTotales += ing.getCarbos();
            grasasTotales += ing.getGrasas();
        }

        return String.format(
            "Resumen nutricional: %.2f kcal | P: %.2fg | C: %.2fg | G: %.2fg",
            caloriasTotales, proteinasTotales, carbosTotales, grasasTotales
        );
    }
}