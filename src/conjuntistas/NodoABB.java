package conjuntistas;

public class NodoABB {
    
    private Comparable elem;
    private NodoABB izquierdo, derecho;
    
    public NodoABB(Comparable elem){
        this.elem = elem;
        this.izquierdo = null;
        this.derecho = null;
    }
    
    public Comparable getElem(){
        return this.elem;
    }
    
    public NodoABB getIzquierdo(){
        return izquierdo;
    }
    
    public NodoABB getDerecho(){
        return derecho;
    }
    
    public void setElem(Comparable elem){
        this.elem = elem;
    }
    
    public void setIzquierdo(NodoABB izquierdo){
        this.izquierdo = izquierdo;
    }
    
    public void setDerecho(NodoABB derecho){
        this.derecho = derecho;
    }
}
