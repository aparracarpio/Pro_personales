package gym.entrenos;

import javax.swing.*;

import gym.entrenos.nutricion.Comida;
import gym.entrenos.nutricion.Dieta;
import gym.entrenos.nutricion.Ingrediente;
import gym.entrenos.revisiones.Revision;
import gym.entrenos.rutinas.Ejercicio;
import gym.entrenos.rutinas.Rutina;
import gym.entrenos.usuarios.Cliente;
import gym.entrenos.usuarios.Equipo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class GymSystemGUI extends JFrame {
    private Cliente cliente;
    private Equipo entrenador;

    public GymSystemGUI() {
        // Inicializar datos
        inicializarDatos();
        
        // Configurar ventana principal
        setTitle("Sistema de Gestión de Gimnasio");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Cliente", crearPanelCliente());
        tabbedPane.addTab("Rutina", crearPanelRutina());
        tabbedPane.addTab("Dieta", crearPanelDieta());
        tabbedPane.addTab("Revisión", crearPanelRevision());
        
        add(tabbedPane);
    }
    
    private void inicializarDatos() {
        // Crear un cliente y un miembro del equipo (igual que en tu Main)
        cliente = new Cliente(1, "Ana", "López", "555-1234", "ana@ejemplo.com", "secreto123");
        entrenador = new Equipo(2, "Carlos", "Gómez", "555-5678", "carlos@ejemplo.com", "entrenador123");

        // Crear y asignar rutina
        Ejercicio sentadilla = new Ejercicio("Sentadilla", "Piernas", "...", "...", 4, 12);
        Ejercicio pressBanca = new Ejercicio("Press Banca", "Pecho", "...", "...", 3, 10);
        Rutina rutinaFuerza = new Rutina("Fuerza", "Full Body", "Descanso 90s", List.of(sentadilla, pressBanca));
        entrenador.asignarRutina(cliente, rutinaFuerza);

        // Crear y asignar dieta
        Ingrediente pollo = new Ingrediente("Pollo", 165, 31, 0, 3.6);
        Ingrediente arroz = new Ingrediente("Arroz", 130, 2.7, 28, 0.3);
        Comida almuerzo = new Comida(1, LocalTime.of(13, 0), List.of(pollo, arroz));
        Dieta dietaProteica = new Dieta("Proteica", List.of(almuerzo));
        entrenador.asignarDieta(cliente, dietaProteica);

        // Crear y agregar revisión
        Revision revision = new Revision(
            LocalDate.now(), 
            cliente, 
            LocalTime.now(), 
            "Centro Principal", 
            68.5, 
            "Buena", 
            95.0, 
            70.0, 
            98.0, 
            32.0, 
            30.0, 
            58.0
        );
        entrenador.agregarRevision(cliente, revision);
    }
    
    private JPanel crearPanelCliente() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de información
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        infoPanel.add(new JLabel("Nombre:"));
        infoPanel.add(new JLabel(cliente.getNombre() + " " + cliente.getApellidos()));
        
        infoPanel.add(new JLabel("Teléfono:"));
        infoPanel.add(new JLabel(cliente.getTelefono()));
        
        infoPanel.add(new JLabel("Email:"));
        infoPanel.add(new JLabel(cliente.getCorreo()));
        
        infoPanel.add(new JLabel("Tipo físico:"));
        infoPanel.add(new JLabel(cliente.getRevisiones().get(0).getCondicionCorporal()));
        
        panel.add(new JScrollPane(infoPanel), BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelRutina() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        Rutina rutina = cliente.getRutinaAsignada();
        
        JLabel titulo = new JLabel("Rutina: " + rutina.getTipoRutina(), JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo, BorderLayout.NORTH);
        
        JTextArea detalles = new JTextArea();
        detalles.setEditable(false);
        detalles.append("Enfoque: " + rutina.getEnfoque() + "\n");
        detalles.append("Consejo: " + rutina.getConsejos() + "\n\n");
        detalles.append("Ejercicios:\n");
        
        for (Ejercicio ejercicio : rutina.getEjercicios()) {
            detalles.append("- " + ejercicio.getNombre() + " (" + ejercicio.getGrupoMuscular() + ")\n");
            detalles.append("  Series: " + ejercicio.getSeries() + " | Reps: " + ejercicio.getRepeticiones() + "\n");
        }
        
        panel.add(new JScrollPane(detalles), BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelDieta() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        Dieta dieta = cliente.getDietaAsignada();
        
        JLabel titulo = new JLabel("Dieta: " + dieta.getTipoDieta(), JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo, BorderLayout.NORTH);
        
        JTextArea detalles = new JTextArea();
        detalles.setEditable(false);
        
        for (Comida comida : dieta.getComidas()) {
            detalles.append("Comida a las " + comida.getHora() + ":\n");
            
            for (Ingrediente ingrediente : comida.getIngredientes()) {
                detalles.append("- " + ingrediente.getNombre() + 
                             " | Calorías: " + ingrediente.getCalorias() + 
                             " | Proteína: " + ingrediente.getProtes() + "g\n");
            }
            detalles.append("\n");
        }
        
        panel.add(new JScrollPane(detalles), BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelRevision() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        if (cliente.getRevisiones().isEmpty()) {
            panel.add(new JLabel("No hay revisiones registradas", JLabel.CENTER), BorderLayout.CENTER);
            return panel;
        }
        
        Revision revision = cliente.getRevisiones().get(0); // Tomamos la última revisión
        
        JLabel titulo = new JLabel("Revisión del " + revision.getFecha(), JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo, BorderLayout.NORTH);
        
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        infoPanel.add(new JLabel("Peso:"));
        infoPanel.add(new JLabel(revision.getPeso() + " kg"));
        
        infoPanel.add(new JLabel("Estado:"));
        infoPanel.add(new JLabel(revision.getCondicionCorporal()));
        
        infoPanel.add(new JLabel("Medida pecho:"));
        infoPanel.add(new JLabel(revision.getMedidaPecho() + " lpm"));
        
        infoPanel.add(new JLabel("Medida brazo relajado:"));
//         infoPanel.add(new JLabel(revision.getMedidaBrazoRelajado()));
        
        infoPanel.add(new JLabel("Medida brazo apretado:"));
//         infoPanel.add(new JLabel(revision.getOxigenoSangre() + "%"));
        
        infoPanel.add(new JLabel("Grasa corporal:"));
//         infoPanel.add(new JLabel(revision.getGrasaCorporal() + "%"));
        
        infoPanel.add(new JLabel("Masa muscular:"));
//         infoPanel.add(new JLabel(revision.getMasaMuscular() + "%"));
        
        infoPanel.add(new JLabel("Hidratación:"));
//         infoPanel.add(new JLabel(revision.getHidratacion() + "%"));
        
        panel.add(new JScrollPane(infoPanel), BorderLayout.CENTER);
        
        return panel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GymSystemGUI gui = new GymSystemGUI();
            gui.setVisible(true);
        });
    }
}