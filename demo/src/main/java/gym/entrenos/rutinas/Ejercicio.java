package gym.entrenos.rutinas;

public class Ejercicio {
    private String nombre;
    private String grupoMuscular;
    private String descripcion;
    private String ejecucion;
    private int series;
    private int repeticiones;

    public Ejercicio(String nombre, String grupoMuscular, String descripcion, String ejecucion, int series, int repes) {
        this.nombre = nombre;
        this.grupoMuscular = grupoMuscular;
        this.descripcion = descripcion;
        this.ejecucion = ejecucion;
        this.series = series;
        this.repeticiones = repes;
    }

    public String getNombre() {return nombre;}
    public String getGrupoMuscular() {return grupoMuscular;}
    public String getDescripcion() {return descripcion;}
    public String getEjecucion() {return ejecucion;}
    public int getSeries() {return series;}
    public int getRepeticiones() {return repeticiones;}
    public void setSeries(int series) {this.series = series;}
    public void setRepeticiones(int repeticiones) {this.repeticiones = repeticiones;}

    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setGrupoMuscular(String grupoMuscular) {this.grupoMuscular = grupoMuscular;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setEjecucion(String ejecucion) {this.ejecucion = ejecucion;}
}