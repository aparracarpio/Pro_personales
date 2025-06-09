package gym.entrenos;

import gym.entrenos.gestores.GestionEntrenosService;
import gym.entrenos.gestores.GestorUsuarios;
import gym.entrenos.gestores.GestorRevisiones;
import gym.entrenos.gestores.GestorRutinas;
import gym.entrenos.nutricion.*;
import gym.entrenos.rutinas.*;
import gym.entrenos.usuarios.Cliente;
import gym.entrenos.usuarios.Equipo;
import gym.entrenos.revisiones.Revision;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// dasdasdasda

public class Main {
    private GestorRutinas grutinas = new GestorRutinas();
    private GestorUsuarios gestorUsuarios = new GestorUsuarios();
    private GestorRevisiones gestorRevisiones = new GestorRevisiones(this.gestorUsuarios);


    // Desarollar menu para miembors de Equipo y Clientes

    public void main(String[] args) {
        // 1. Prueba de conexión a la base de datos
        testDatabaseConnection();
        
        // 2. Prueba de gestión de usuarios
        testGestorUsuarios();
        
        // 3. Prueba de gestión de entrenos
        testGestionEntrenos();
        
        // 4. Prueba de revisiones
        testGestorRevisiones();
    }

    private static void testDatabaseConnection() {
        System.out.println("\n=== Probando conexión a la BD ===");
        try {
            var conn = gym.entrenos.database.DatabaseConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Conexión a la BD establecida correctamente");
                
                // Prueba adicional: mostrar versión de la BD
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery("SELECT version()");
                if (rs.next()) {
                    System.out.println("Versión de la BD: " + rs.getString(1));
                }
                
                conn.close();
            }
        } catch (Exception e) {
            System.err.println("❌ Error al conectar a la BD: " + e.getMessage());
        }
    }

    private void testGestorUsuarios() {
        System.out.println("\n=== Probando Gestor de Usuarios ===");
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        
        // Crear clientes de prueba
        Cliente cliente1 = new Cliente(100, "Juan", "Pérez", "555-1234", "juan@email.com", "hash123");
        Cliente cliente2 = new Cliente(101, "Ana", "Gómez", "555-5678", "ana@email.com", "hash456");
        Equipo equ = new Equipo(101, "Ana", "Gómez", "555-5678", "ana@email.com", "hash456");
        
        // Dar de alta clientes
        gestorUsuarios.altaCliente(cliente1);
        gestorUsuarios.altaCliente(cliente2);
        System.out.println("✅ Clientes añadidos");
        
        // Buscar cliente
        Cliente clienteEncontrado = gestorUsuarios.buscarClientePorId(100);
        System.out.println("Cliente encontrado: " + 
            (clienteEncontrado != null ? clienteEncontrado.getNombre() : "No encontrado"));
        
        // Contar clientes
        System.out.println("Total clientes: " + gestorUsuarios.getTotalClientes());

        equ.asignarRutina(cliente1, this.grutinas.getTodasRutinas().get(0));
        
        // Intentar duplicado (debería fallar por email único)
        try {
            Cliente clienteDuplicado = new Cliente(102, "Juan", "Pérez", "555-1234", "juan@email.com", "hash123");
            gestorUsuarios.altaCliente(clienteDuplicado);
        } catch (Exception e) {
            System.out.println("✅ Correcto: Fallo al intentar duplicar email (" + e.getMessage() + ")");
        }
    }

    private static void testGestionEntrenos() {
        System.out.println("\n=== Probando Gestión de Entrenos ===");
        GestionEntrenosService service = new GestionEntrenosService();
        
        // 1. Prueba de nutrición
        System.out.println("\n--- Prueba de módulo de nutrición ---");
        
        // Crear ingredientes
        Ingrediente pollo = service.crearIngrediente("Pechuga de pollo", 165, 31, 0, 3.6);
        Ingrediente arroz = service.crearIngrediente("Arroz integral", 216, 5, 45, 1.8);
        Ingrediente brócoli = service.crearIngrediente("Brócoli", 55, 3.7, 11, 0.6);
        
        // Crear comidas
        List<Ingrediente> ingredientesComida1 = new ArrayList<>();
        ingredientesComida1.add(pollo);
        ingredientesComida1.add(arroz);
        ingredientesComida1.add(brócoli);
        
        Comida comida1 = service.crearComida(1, LocalTime.of(13, 0), ingredientesComida1);
        System.out.println("Comida creada: " + comida1.getResumenNutricional());
        
        // Crear dieta
        List<Comida> comidas = new ArrayList<>();
        comidas.add(comida1);
        Dieta dieta = service.crearDieta("Definición", comidas);
        System.out.println("✅ Dieta creada: " + dieta.getTipoDieta());
        System.out.println("Calorías totales dieta: " + service.calcularCaloriasTotalesDieta(dieta));
        
        // 2. Prueba de rutinas
        System.out.println("\n--- Prueba de módulo de rutinas ---");
        
        // Crear ejercicios
        Ejercicio sentadilla = service.crearEjercicio(
            "Sentadilla", "Piernas", "Ejercicio básico para piernas", 
            "Pies al ancho de hombros, flexionar rodillas", 4, 12
        );
        
        Ejercicio pressBanca = service.crearEjercicio(
            "Press Banca", "Pecho", "Ejercicio para pectorales", 
            "Acostado en banco, bajar la barra al pecho", 4, 10
        );
        
        // Crear rutina
        List<Ejercicio> ejercicios = new ArrayList<>();
        ejercicios.add(sentadilla);
        ejercicios.add(pressBanca);
        
        Rutina rutina = service.crearRutina("Fuerza", "Principiantes", "Descansar 90s entre series", ejercicios);
        System.out.println("✅ Rutina creada: " + rutina.getTipoRutina());
        
        // Asignar series/repeticiones específicas
        rutina.setSeriesRepeticiones(sentadilla, 4, 12);
        rutina.setSeriesRepeticiones(pressBanca, 3, 10);
        System.out.println("Series/Reps para Sentadilla: " + rutina.getSeriesRepeticiones(sentadilla));
        
        // Buscar rutinas por grupo muscular
        System.out.println("\nRutinas para piernas:");
        service.obtenerRutinasPorGrupoMuscular("Piernas").forEach(r -> 
            System.out.println("- " + r.getTipoRutina())
        );
    }

    private void testGestorRevisiones() {
        System.out.println("\n=== Probando Gestor de Revisiones ===");
        
        
        
        // Crear cliente para la revisión (si no existe)
        Cliente clienteRevision = this.gestorUsuarios.buscarClientePorId(200);
        if (clienteRevision == null) {
            clienteRevision = new Cliente(200, "Carlos", "López", "555-9999", "carlos@email.com", "hash789");
            gestorUsuarios.altaCliente(clienteRevision);
        }
        
        // Crear revisiones
        Revision revision1 = new Revision(
            LocalDate.now(), 
            clienteRevision, 
            LocalTime.of(10, 30), 
            "Centro Principal", 
            75.5, 
            "Buena", 
            100.0, 85.0, 95.0, 35.0, 32.0, 60.0
        );
        
        Revision revision2 = new Revision(
            LocalDate.now().plusDays(7), 
            clienteRevision, 
            LocalTime.of(11, 30), 
            "Centro Norte", 
            74.0, 
            "Excelente", 
            98.0, 83.0, 93.0, 34.5, 31.5, 59.0
        );
        
        // Agregar revisiones
        gestorRevisiones.agregarRevision(revision1);
        gestorRevisiones.agregarRevision(revision2);
        System.out.println("✅ Revisiones agregadas para: " + clienteRevision.getNombre());
        
        // Mostrar revisiones
        System.out.println("\nRevisiones hoy:");
        gestorRevisiones.getRevisionesHoy().forEach(rev -> 
            System.out.println("- " + rev.getCentro() + " a las " + rev.getHora())
        );
        
        System.out.println("\nPróximas revisiones:");
        gestorRevisiones.getTodasRevisiones().stream()
            .filter(r -> r.getFecha().isAfter(LocalDate.now()))
            .forEach(r -> System.out.println("- " + r.getFecha() + " en " + r.getCentro()));
        
        // Centros con revisiones hoy
        System.out.println("\nCentros con revisiones hoy:");
        gestorRevisiones.getCentrosConRevisionHoy().forEach(System.out::println);
    }
}