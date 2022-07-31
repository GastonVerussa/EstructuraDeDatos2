package grafos;

public class NodoAdy {
    
    private NodoVert vertice;
    private int etiqueta;
    private NodoAdy sigAdyacente;
    
    public NodoAdy(NodoVert vertice, int etiqueta, NodoAdy sigAdyacente){
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
    
    public int getEtiqueta(){
        return this.etiqueta;
    }
    
    public void setVertice(NodoVert vertice){
        this.vertice = vertice;
    }
    
    public void setSigAdyacente(NodoAdy sigAdyacente){
        this.sigAdyacente = sigAdyacente;
    }
    
    public void setEtiqueta(int etiqueta){
        this.etiqueta = etiqueta;
    }
}
