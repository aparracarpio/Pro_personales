package gym.entrenos.gestores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import gym.entrenos.revisiones.Revision;
import gym.entrenos.usuarios.Cliente;

public class GestorRevisiones {
    private final List<Revision> revisiones = new ArrayList<>();
    private final GestorUsuarios gestorUsuarios;

    public GestorRevisiones(GestorUsuarios gestorUsuarios) {
        this.gestorUsuarios = gestorUsuarios;
    }

    // ========== CRUD Revisiones ==========
    public void agregarRevision(Revision revision) {
        if (revision == null) throw new IllegalArgumentException("Revisión no puede ser null");
        revisiones.add(revision);
        asignarRevisionACliente(revision);
    }

    public void eliminarRevision(Revision revision) {
        if (revisiones.remove(revision)) {
            desasignarRevisionDeCliente(revision);
        }
    }

    public void actualizarRevision(Revision revisionActualizada) {
        if (revisionActualizada == null) throw new IllegalArgumentException("Revisión no puede ser null");
        
        int index = revisiones.indexOf(revisionActualizada);
        if (index != -1) {
            revisiones.set(index, revisionActualizada);
            // Actualizar también en el cliente
            desasignarRevisionDeCliente(revisionActualizada);
            asignarRevisionACliente(revisionActualizada);
        }
    }

    public List<Revision> getTodasRevisiones() {
        return Collections.unmodifiableList(revisiones);
    }

    // ========== Operaciones específicas ==========
    private void asignarRevisionACliente(Revision revision) {
        Cliente cliente = revision.getCliente();
        if (cliente != null) {
            Cliente clienteRegistrado = gestorUsuarios.getCliente(cliente.getIdUsr());
            if (clienteRegistrado != null) {
                clienteRegistrado.agregarRevision(revision);
            } else {
                throw new IllegalArgumentException("Cliente no registrado");
            }
        }
    }

    private void desasignarRevisionDeCliente(Revision revision) {
        Cliente cliente = revision.getCliente();
        if (cliente != null) {
            Cliente clienteRegistrado = gestorUsuarios.getCliente(cliente.getIdUsr());
            if (clienteRegistrado != null) {
                clienteRegistrado.getRevisiones().remove(revision);
            }
        }
    }

    public List<Revision> getRevisionesPorCliente(int idCliente) {
        Cliente cliente = gestorUsuarios.getCliente(idCliente);
        if (cliente == null) throw new IllegalArgumentException("Cliente no encontrado");
        return Collections.unmodifiableList(cliente.getRevisiones());
    }

    public List<String> getCentrosConRevisionHoy() {
        LocalDate hoy = LocalDate.now();
        return revisiones.stream()
                .filter(r -> r.getFecha() != null && r.getFecha().equals(hoy))
                .map(Revision::getCentro)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Revision> getRevisionesHoy() {
        LocalDate hoy = LocalDate.now();
        return revisiones.stream()
                .filter(r -> r.getFecha() != null && r.getFecha().equals(hoy))
                .collect(Collectors.toList());
    }
}