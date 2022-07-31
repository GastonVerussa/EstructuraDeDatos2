package grafos;

public class NodoVert {
    
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;
    
    public NodoVert(Object elem, NodoVert sigVertice){
        this.elem = elem;
        this.sigVertice = sigVertice;
        this.primerAdy = null;
    }
    
    public Object getElem(){
        return this.elem;
    }
    
    public NodoVert getSigVertice(){
        return this.sigVertice;
    }
    
    public NodoAdy getPrimerAdy(){
        return this.primerAdy;
    }
    
    public void setElem(Object elem){
        this.elem = elem;
    }
    
    public void setSigVert(NodoVert sigVertice){
        this.sigVertice = sigVertice;
    }
    
    public void setPrimerAdy(NodoAdy primerAdy){
        this.primerAdy = primerAdy;
    }
}
