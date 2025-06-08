package gym.entrenos.revisiones;

import java.time.LocalDate;
import java.time.LocalTime;

import gym.entrenos.usuarios.Cliente;

public class Revision {
    private LocalDate fecha;
    private Cliente cliente;
    private LocalTime hora;
    private String centro;
    private double peso;
    private String condicionCorporal;
    private double medidaPecho;
    private double medidaCintura;
    private double medidaCadera;
    private double medidaBrazo;
    private double medidaBrazoRelajado;
    private double medidaPierna;

    public Revision(LocalDate fecha, Cliente cli, LocalTime hora, String centro, double peso, String condicionCorporal, double medidaPecho, double medidaCintura, double medidaCadera, double medidaBrazo, double medidaBrazoRelajado, double medidaPiernas) {
        this.fecha = fecha;
        this.cliente = cli;
        this.hora = hora;
        this.centro = centro;
        this.peso = peso;
        this.condicionCorporal = condicionCorporal;
        this.medidaPecho = medidaPecho;
        this.medidaCintura = medidaCintura;
        this.medidaCadera = medidaCadera;
        this.medidaBrazo = medidaBrazo;
        this.medidaBrazoRelajado = medidaBrazoRelajado;
        this.medidaPierna = medidaPiernas;
    }

    // ===== Getters =====
    public LocalDate getFecha() { return fecha; }
    public Cliente getCliente() { return cliente; }
    public LocalTime getHora() { return hora; }
    public String getCentro() { return centro; }
    public double getPeso() { return peso; }
    public String getCondicionCorporal() { return condicionCorporal; }
    public double getMedidaPecho() { return medidaPecho; }
    public double getMedidaCintura() { return medidaCintura; }
    public double getMedidaCadera() { return medidaCadera; }
    public double getMedidaBrazo() { return medidaBrazo; }
    public double getMedidaBrazoRelajado() { return medidaBrazoRelajado; }
    public double getMedidaPierna() { return medidaPierna; }

    // ===== Setters =====
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public void setCentro(String centro) { this.centro = centro; }
    public void setPeso(double peso) { this.peso = peso; }
    public void setCondicionCorporal(String condicionCorporal) { this.condicionCorporal = condicionCorporal; }
    public void setMedidaPecho(double medidaPecho) { this.medidaPecho = medidaPecho; }
    public void setMedidaCintura(double medidaCintura) { this.medidaCintura = medidaCintura; }
    public void setMedidaCadera(double medidaCadera) { this.medidaCadera = medidaCadera; }
    public void setMedidaBrazo(double medidaBrazo) { this.medidaBrazo = medidaBrazo; }
    public void setMedidaBrazoRelajado(double medidaBrazoRelajado) { this.medidaBrazoRelajado = medidaBrazoRelajado; }
    public void setMedidaPierna(double medidaPierna) { this.medidaPierna = medidaPierna; }
}