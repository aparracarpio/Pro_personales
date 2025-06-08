package gym.entrenos.usuarios;

import gym.entrenos.nutricion.Dieta;
import gym.entrenos.rutinas.Rutina;
import gym.entrenos.revisiones.Revision;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private Rutina rutinaAsignada;
    private Dieta dietaAsignada;
    private final List<Revision> revisiones;

    public Cliente(int id, String nm, String ap, String tele, String gmail, String pass) {
        super(id, nm, ap, tele, gmail, pass);
        this.revisiones = new ArrayList<>();
    }

    // --- Getters y Setters ---
    public Rutina getRutinaAsignada() {
        return rutinaAsignada;
    }

    public void setRutinaAsignada(Rutina rutina) {
        this.rutinaAsignada = rutina;
    }

    public Dieta getDietaAsignada() {
        return dietaAsignada;
    }

    public void setDietaAsignada(Dieta dieta) {
        this.dietaAsignada = dieta;
    }

    public List<Revision> getRevisiones() {
        return new ArrayList<>(revisiones); // Copia defensiva
    }

    // Solo para uso interno del GestorRevisiones
    public void agregarRevision(Revision revision) {
        if (revision != null && !revisiones.contains(revision)) {
            revisiones.add(revision);
        }
    }
}