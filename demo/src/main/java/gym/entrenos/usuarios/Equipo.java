package gym.entrenos.usuarios;

import java.time.LocalTime;
import java.util.List;

import gym.entrenos.gestores.GestionEntrenosService;
import gym.entrenos.nutricion.Comida;
import gym.entrenos.nutricion.Dieta;
import gym.entrenos.nutricion.Ingrediente;
import gym.entrenos.revisiones.Revision;
import gym.entrenos.rutinas.Ejercicio;
import gym.entrenos.rutinas.Rutina;

public class Equipo extends Usuario {
    private GestionEntrenosService gestionEntrenosService;
    
    public Equipo(int id, String nm, String ap, String tele, String gmail, String pass) {
        super(id, nm, ap, tele, gmail, pass);
        this.gestionEntrenosService = new GestionEntrenosService();
    }

    // Métodos para Rutinas
    public Rutina crearRutina(String tipoRutina, String enfoque, String consejos, List<Ejercicio> ejercicios) {
        return gestionEntrenosService.crearRutina(tipoRutina, enfoque, consejos, ejercicios);
    }

    public void eliminarRutina(Rutina rutina) {
        gestionEntrenosService.eliminarRutina(rutina);
    }

    public void modificarRutina(Rutina rutina, String nuevoEnfoque, String nuevosConsejos) {
        gestionEntrenosService.modificarRutina(rutina, nuevoEnfoque, nuevosConsejos);
    }

    // Métodos para Comidas (corregidos)
    public Comida crearComida(int numeroComida, LocalTime hora, List<Ingrediente> ingredientes) {
        return gestionEntrenosService.crearComida(numeroComida, hora, ingredientes);
    }

    public void eliminarComidaDeDieta(Dieta dieta, Comida comida) {
        gestionEntrenosService.eliminarComidaDeDieta(dieta, comida);
    }

    public void modificarHoraComida(Comida comida, LocalTime nuevaHora) {
        gestionEntrenosService.modificarHoraComida(comida, nuevaHora);
    }

    // Métodos para Ingredientes
    public Ingrediente crearIngrediente(String nombre, double calorias, double protes, double carbos, double grasas) {
        return gestionEntrenosService.crearIngrediente(nombre, calorias, protes, carbos, grasas);
    }

    public void eliminarIngrediente(Ingrediente ing) {
        gestionEntrenosService.eliminarIngrediente(ing);
    }

    public void modificarIngrediente(Ingrediente ingrediente, String nuevoNombre) {
        gestionEntrenosService.modificarIngrediente(ingrediente, nuevoNombre);
    }

    // Métodos para Ejercicios (corregido eliminarEjercicio)
    public Ejercicio crearEjercicio(String nombre, String grupoMus, String desc, String ejec, int series, int repes) {
        return gestionEntrenosService.crearEjercicio(nombre, grupoMus, desc, ejec, series, repes);
    }

    public void eliminarEjercicio(Ejercicio eje) {
        gestionEntrenosService.eliminarEjercicio(eje);
    }

    public void modificarEjercicio(Ejercicio ejercicio, String nuevaDescripcion, String nuevaEjecucion) {
        gestionEntrenosService.modificarEjercicio(ejercicio, nuevaDescripcion, nuevaEjecucion);
    }

    // Métodos para asignaciones
    public void asignarRutina(Cliente cliente, Rutina rutina) {
        cliente.setRutinaAsignada(rutina);
    }

    public void asignarDieta(Cliente cliente, Dieta dieta) {
        cliente.setDietaAsignada(dieta);
    }

    public void agregarRevision(Cliente cliente, Revision revision) {
        cliente.agregarRevision(revision);
    }

    // Métodos para Dietas
    public Dieta crearDieta(String tipoDieta, List<Comida> comidas) {
        return gestionEntrenosService.crearDieta(tipoDieta, comidas);
    }

    public void eliminarDieta(Dieta dieta) {
        gestionEntrenosService.eliminarDieta(dieta);
    }

    public void modificarTipoDieta(Dieta dieta, String nuevoTipo) {
        gestionEntrenosService.modificarTipoDieta(dieta, nuevoTipo);
    }
}