package conjuntistas;

public class NodoAVL {
    
    private Comparable elem;
    private NodoAVL izquierdo, derecho;
    private int altura;
    
    public NodoAVL(Comparable elem){
        this.elem = elem;
        this.izquierdo = null;
        this.derecho = null;
        this.altura = 0;
    }
    
    public Comparable getElem(){
        return this.elem;
    }
    
    public NodoAVL getIzquierdo(){
        return izquierdo;
    }
    
    public NodoAVL getDerecho(){
        return derecho;
    }
    
    public int getAltura(){
        return altura;
    }
    
    public void setElem(Comparable elem){
        this.elem = elem;
    }
    
    public void setIzquierdo(NodoAVL izquierdo){
        this.izquierdo = izquierdo;
    }
    
    public void setDerecho(NodoAVL derecho){
        this.derecho = derecho;
    }
    
    public void recalcularAltura(){
        
        this.altura = 0;
        
        if(this.izquierdo != null){
            if(this.izquierdo.getAltura() > this.altura)
                this.altura = this.izquierdo.getAltura();
        }
        
        if(this.derecho != null){
            if(this.derecho.getAltura() > this.altura)
                this.altura = this.derecho.getAltura();
        }
    }
}
