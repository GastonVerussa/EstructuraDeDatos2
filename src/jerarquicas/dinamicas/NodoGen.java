package jerarquicas.dinamicas;

public class NodoGen {
    
    private Object elem;
    private NodoGen hijoIzquierdo, hermanoDerecho;
    
    public NodoGen(Object elem, NodoGen hijoIzquierdo, NodoGen hermanoDerecho){
        this.elem = elem;
        this.hijoIzquierdo = hijoIzquierdo;
        this.hermanoDerecho = hermanoDerecho;
    }
    
    public Object getElem(){
        return this.elem;
    }
    
    public NodoGen getHijoIzquierdo(){
        return hijoIzquierdo;
    }
    
    public NodoGen getHermanoDerecho(){
        return hermanoDerecho;
    }
    
    public void setElem(Object elem){
        this.elem = elem;
    }
    
    public void setHijoIzquierdo(NodoGen hijoIzquierdo){
        if(this.hijoIzquierdo != null) hijoIzquierdo.setHermanoDerecho(this.hijoIzquierdo);
        this.hijoIzquierdo = hijoIzquierdo;
    }
    
    public void setHermanoDerecho(NodoGen hermanoDerecho){
        this.hermanoDerecho = hermanoDerecho;
    }
}
