package gym.entrenos.nutricion;

import java.util.ArrayList;
import java.util.List;

public class Dieta {
    private String tipoDieta;
    private List<Comida> comidas = new ArrayList<>();

    // Constructor vacío
    public Dieta() {
    }

    // Constructor con tipoDieta
    public Dieta(String tipoDieta) {
        this.tipoDieta = tipoDieta;
    }

    // Constructor con todos los campos
    public Dieta(String tipoDieta, List<Comida> comidas) {
        this.tipoDieta = tipoDieta;
        this.comidas = comidas;
    }

    // Getters y setters
    public String getTipoDieta() {
        return tipoDieta;
    }

    public void setTipoDieta(String tipoDieta) {
        this.tipoDieta = tipoDieta;
    }

    public List<Comida> getComidas() {
        return comidas;
    }

    public void setComidas(List<Comida> comidas) {
        this.comidas = comidas;
    }

    // Método para agregar una comida a la lista
    public void agregarComida(Comida comida) {
        this.comidas.add(comida);
    }

    // Método para remover una comida de la lista
    public void removerComida(Comida comida) {
        this.comidas.remove(comida);
    }
}