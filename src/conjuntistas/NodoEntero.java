package conjuntistas;

public class NodoEntero {
    
    private int elemento;
    private NodoEntero enlace;
    
    public NodoEntero(int elemento, NodoEntero enlace){
        this.elemento = elemento;
        this.enlace = enlace;
    }
    
    public int getElem(){
        return elemento;
    }
    
    public NodoEntero getEnlace(){
        return enlace;
    }
    
    public void setElem(int elemento){
        this.elemento = elemento;
    }
    
    public void setEnlace(NodoEntero enlace){
        this.enlace = enlace;
    }
}
