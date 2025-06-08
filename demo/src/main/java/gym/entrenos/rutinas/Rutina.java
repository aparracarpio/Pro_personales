package gym.entrenos.rutinas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rutina {
    private String tipoRutina;
    private String enfoque;
    private String consejos;
    private String descripcion;
    private List<Ejercicio> ejercicios;
    private Map<Ejercicio, String> seriesRepeticiones;  // Mapa para series x repeticiones

    // Constructor
    public Rutina(String tipoRutina, String enfoqueRutina, String consejoGeneral, 
                 List<Ejercicio> ejerciciosRutina) {
        this.tipoRutina = tipoRutina;
        this.enfoque = enfoqueRutina;
        this.consejos = consejoGeneral;
        this.ejercicios = ejerciciosRutina;
        this.seriesRepeticiones = new HashMap<>();  // Inicializa el mapa
    }

    // ========== Getters ==========
    public String getTipoRutina() { return tipoRutina; }
    public String getEnfoque() { return enfoque; }
    public String getConsejos() { return consejos; }
    public String getDescripcion() { return descripcion; }
    public List<Ejercicio> getEjercicios() { return ejercicios; }

    // ========== Setters ==========
    public void setTipoRutina(String tipoRutina) { this.tipoRutina = tipoRutina; }
    public void setEnfoque(String enfoque) { this.enfoque = enfoque; }
    public void setConsejos(String consejos) { this.consejos = consejos; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setEjercicios(List<Ejercicio> ejercicios) { this.ejercicios = ejercicios; }

    // ===== Métodos para series/repeticiones =====
    /**
     * Asigna series y repeticiones a un ejercicio en formato "SxR" (ej: "4x12").
     * @param ejercicio El ejercicio al que se asignan.
     * @param series Número de series.
     * @param repeticiones Número de repeticiones por serie.
     */
    public void setSeriesRepeticiones(Ejercicio ejercicio, int series, int repeticiones) {
        if (ejercicio == null) {
            throw new IllegalArgumentException("El ejercicio no puede ser nulo.");
        }
        String sr = series + "x" + repeticiones;
        this.seriesRepeticiones.put(ejercicio, sr);
    }

    /**
     * Obtiene las series y repeticiones de un ejercicio en formato "SxR".
     * @param ejercicio El ejercicio a consultar.
     * @return String en formato "SxR" o null si no existe.
     */
    public String getSeriesRepeticiones(Ejercicio ejercicio) {
        return this.seriesRepeticiones.get(ejercicio);
    }

    /**
     * Elimina un ejercicio del mapa de series/repeticiones.
     * @param ejercicio El ejercicio a eliminar.
     */
    public void removeSeriesRepeticiones(Ejercicio ejercicio) {
        this.seriesRepeticiones.remove(ejercicio);
    }

    // Métodos para manejar ejercicios
    public void agregarEjercicio(Ejercicio ejercicio) {
        if (ejercicio != null) {
            ejercicios.add(ejercicio);
        }
    }
    
    public void eliminarEjercicio(Ejercicio ejercicio) {
        ejercicios.remove(ejercicio);
    }
    
    public void limpiarEjercicios() {
        ejercicios.clear();
    }
}