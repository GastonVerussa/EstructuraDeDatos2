package propositoEspecifico;

public class CeldaCP{
    
    private Comparable prioridad;
    private int ordenLlegada;
    private Object elemento;

    public CeldaCP(Object elemento, Comparable prioridad, int ordenLlegada) {
        this.prioridad = prioridad;
        this.ordenLlegada = ordenLlegada;
        this.elemento = elemento;
    }
    
    public CeldaCP(){
        prioridad = null;
        ordenLlegada = 0;
        elemento = null;
    }

    public Comparable getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Comparable prioridad) {
        this.prioridad = prioridad;
    }

    public int getOrdenLlegada() {
        return ordenLlegada;
    }

    public void setOrdenLlegada(int ordenLlegada) {
        this.ordenLlegada = ordenLlegada;
    }

    public Object getElemento() {
        return elemento;
    }

    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }
}
