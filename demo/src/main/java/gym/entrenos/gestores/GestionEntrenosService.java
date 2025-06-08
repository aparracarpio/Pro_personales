package gym.entrenos.gestores;

import gym.entrenos.nutricion.*;
import gym.entrenos.rutinas.*;
import java.time.LocalTime;
import java.util.List;

public class GestionEntrenosService {
    private final GestorRutinas gestorRutinas;
    private final GestorDietas gestorDietas;
    
    public GestionEntrenosService() {
        this.gestorRutinas = new GestorRutinas();
        this.gestorDietas = new GestorDietas();
    }
    
    // ========== MÉTODOS PARA RUTINAS ==========
    
    /**
     * Crea una nueva rutina con los parámetros especificados
     */
    public Rutina crearRutina(String tipoRutina, String enfoque, String consejos, List<Ejercicio> ejercicios) {
        Rutina rutina = new Rutina(tipoRutina, enfoque, consejos, ejercicios);
        gestorRutinas.crearRutina(rutina);
        return rutina;
    }
    
    /**
     * Elimina una rutina existente
     */
    public void eliminarRutina(Rutina rutina) {
        gestorRutinas.eliminarRutina(rutina);
    }
    
    /**
     * Modifica los datos básicos de una rutina
     */
    public void modificarRutina(Rutina rutina, String nuevoEnfoque, String nuevosConsejos) {
        rutina.setEnfoque(nuevoEnfoque);
        rutina.setConsejos(nuevosConsejos);
        gestorRutinas.actualizarRutina(rutina);
    }
    
    /**
     * Obtiene todas las rutinas disponibles
     */
    public List<Rutina> obtenerTodasRutinas() {
        return gestorRutinas.getTodasRutinas();
    }
    
    /**
     * Busca rutinas por tipo
     */
    public List<Rutina> buscarRutinasPorTipo(String tipo) {
        return gestorRutinas.buscarRutinasPorTipo(tipo);
    }
    
    /**
     * Añade un ejercicio a una rutina existente
     */
    public void agregarEjercicioARutina(Rutina rutina, Ejercicio ejercicio) {
        rutina.agregarEjercicio(ejercicio);
        gestorRutinas.actualizarRutina(rutina);
    }
    
    /**
     * Elimina un ejercicio de una rutina existente
     */
    public void eliminarEjercicioDeRutina(Rutina rutina, Ejercicio ejercicio) {
        rutina.eliminarEjercicio(ejercicio);
        gestorRutinas.actualizarRutina(rutina);
    }
    
    // ========== MÉTODOS PARA EJERCICIOS ==========
    
    /**
     * Crea un nuevo ejercicio
     */
    public Ejercicio crearEjercicio(String nombre, String grupoMuscular, String descripcion, String ejecucion, int series, int repeticiones) {
        Ejercicio ejercicio = new Ejercicio(nombre, grupoMuscular, descripcion, ejecucion, series, repeticiones);
        gestorRutinas.guardarEjercicio(ejercicio);
        return ejercicio;
    }
    
    /**
     * Modifica un ejercicio existente
     */
    public void modificarEjercicio(Ejercicio ejercicio, String nuevaDescripcion, String nuevaEjecucion) {
        ejercicio.setDescripcion(nuevaDescripcion);
        ejercicio.setEjecucion(nuevaEjecucion);
        gestorRutinas.actualizarEjercicio(ejercicio);
    }
    
    /**
     * Elimina un ejercicio existente
     */
    public void eliminarEjercicio(Ejercicio ejercicio) {
        gestorRutinas.eliminarEjercicio(ejercicio);
    }
    
    // ========== MÉTODOS PARA DIETAS ==========
    
    /**
     * Crea una nueva dieta
     */
    public Dieta crearDieta(String tipoDieta, List<Comida> comidas) {
        Dieta dieta = new Dieta(tipoDieta, comidas);
        gestorDietas.crearDieta(dieta);
        return dieta;
    }
    
    /**
     * Elimina una dieta existente
     */
    public void eliminarDieta(Dieta dieta) {
        gestorDietas.eliminarDieta(dieta);
    }
    
    /**
     * Modifica el tipo de una dieta
     */
    public void modificarTipoDieta(Dieta dieta, String nuevoTipo) {
        dieta.setTipoDieta(nuevoTipo);
        gestorDietas.actualizarDieta(dieta);
    }
    
    /**
     * Obtiene todas las dietas disponibles
     */
    public List<Dieta> obtenerTodasDietas() {
        return gestorDietas.getTodasDietas();
    }
    
    /**
     * Busca dietas por tipo
     */
    public Dieta buscarDietaPorTipo(String tipo) {
        return gestorDietas.buscarDietaPorTipo(tipo);
    }
    
    // ========== MÉTODOS PARA COMIDAS ==========
    
    /**
     * Crea una nueva comida
     */
    public Comida crearComida(int numeroComida, LocalTime hora, List<Ingrediente> ingredientes) {
        Comida comida = new Comida(numeroComida, hora, ingredientes);
        // Nota: Las comidas se guardan a través de las dietas
        return comida;
    }
    
    /**
     * Elimina una comida de una dieta
     */
    public void eliminarComidaDeDieta(Dieta dieta, Comida comida) {
        dieta.removerComida(comida);
        gestorDietas.actualizarDieta(dieta);
    }
    
    /**
     * Modifica la hora de una comida
     */
    public void modificarHoraComida(Comida comida, LocalTime nuevaHora) {
        comida.setHora(nuevaHora);
        gestorDietas.actualizarComida(comida);
    }
    
    /**
     * Obtiene el resumen nutricional de una comida
     */
    public String obtenerResumenNutricionalComida(Comida comida) {
        return comida.getResumenNutricional();
    }
    
    // ========== MÉTODOS PARA INGREDIENTES ==========
    
    /**
     * Crea un nuevo ingrediente
     */
    public Ingrediente crearIngrediente(String nombre, double calorias, double proteinas, 
                                      double carbohidratos, double grasas) {
        Ingrediente ingrediente = new Ingrediente(nombre, calorias, proteinas, carbohidratos, grasas);
        gestorDietas.guardarIngrediente(ingrediente);
        return ingrediente;
    }
    
    /**
     * Modifica un ingrediente existente
     */
    public void modificarIngrediente(Ingrediente ingrediente, String nuevoNombre) {
        ingrediente.setNombre(nuevoNombre);
        gestorDietas.actualizarIngrediente(ingrediente);
    }
    
    /**
     * Elimina un ingrediente existente
     */
    public void eliminarIngrediente(Ingrediente ingrediente) {
        gestorDietas.eliminarIngrediente(ingrediente);
    }
    
    /**
     * Añade un ingrediente a una comida
     */
    public void agregarIngredienteAComida(Comida comida, Ingrediente ingrediente) {
        comida.agregarIngrediente(ingrediente);
        gestorDietas.actualizarComida(comida);
    }
    
    /**
     * Elimina un ingrediente de una comida
     */
    public void eliminarIngredienteDeComida(Comida comida, Ingrediente ingrediente) {
        comida.eliminarIngrediente(ingrediente);
        gestorDietas.actualizarComida(comida);
    }
    
    // ========== MÉTODOS DE CONSULTA ==========
    
    /**
     * Obtiene todas las rutinas para un grupo muscular específico
     */
    public List<Rutina> obtenerRutinasPorGrupoMuscular(String grupoMuscular) {
        return gestorRutinas.buscarRutinasPorGrupoMuscular(grupoMuscular);
    }
    
    /**
     * Obtiene todas las dietas que contienen un ingrediente específico
     */
    public List<Dieta> obtenerDietasPorIngrediente(String nombreIngrediente) {
        return gestorDietas.buscarDietasPorIngrediente(nombreIngrediente);
    }
    
    /**
     * Calcula el total de calorías de una dieta
     */
    public double calcularCaloriasTotalesDieta(Dieta dieta) {
        double total = 0;
        for (Comida comida : dieta.getComidas()) {
            for (Ingrediente ingrediente : comida.getIngredientes()) {
                total += ingrediente.getCalorias();
            }
        }
        return total;
    }
}