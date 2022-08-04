package grafos;

public class NodoAdy {
    
    private NodoVert vertice;
    private Object etiqueta;
    private NodoAdy sigAdyacente;
    
    public NodoAdy(NodoVert vertice, Object etiqueta, NodoAdy sigAdyacente){
        this.vertice = vertice;
        this.etiqueta = etiqueta;
        this.sigAdyacente = sigAdyacente;
    }
    
    public NodoVert getVertice(){
        return this.vertice;
    }
    
    public NodoAdy getSigAdyacente(){
        return this.sigAdyacente;
    }
    
    public Object getEtiqueta(){
        return this.etiqueta;
    }
    
    public void setVertice(NodoVert vertice){
        this.vertice = vertice;
    }
    
    public void setSigAdyacente(NodoAdy sigAdyacente){
        this.sigAdyacente = sigAdyacente;
    }
    
    public void setEtiqueta(Object etiqueta){
        this.etiqueta = etiqueta;
    }
}
