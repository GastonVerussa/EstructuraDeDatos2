package jerarquicas.estaticas;

public class CeldaBin {
    
    private Object elem;
    private int izquierdo, derecho;
    private boolean enUso;
    
    public CeldaBin(Object elem, int izquierdo, int derecho){
        this.elem = izquierdo;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        enUso = true;
    }
    
    public CeldaBin(){
        this.elem = null;
        this.izquierdo = -1;
        this.derecho = -1;
        this.enUso = false;
    }
    
    public Object getElem(){
        return this.elem;
    }
    
    public int getIzquierdo(){
        return izquierdo;
    }
    
    public int getDerecho(){
        return derecho;
    }
    
    public boolean getEnUso(){
        return enUso;
    }
    
    public void setElem(Object elem){
        this.elem = elem;
    }
    
    public void setIzquierdo(int izquierdo){
        this.izquierdo = izquierdo;
    }
    
    public void setDerecho(int derecho){
        this.derecho = derecho;
    }
    
    public void setEnUso(boolean enUso){
        this.enUso = enUso;
    }
    
    public void setAtributos(Object elem, int izquierdo, int derecho){
        this.elem = izquierdo;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        enUso = true;
    }
    
    public void vaciar(){
        this.elem = null;
        this.izquierdo = -1;
        this.derecho = -1;
        this.enUso = false;
    }
}
