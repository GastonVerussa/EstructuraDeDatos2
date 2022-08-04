package conjuntistas;

public class NodoHash {
    
    private Object elemento;
    private NodoHash enlace;
    
    public NodoHash(Object elemento, NodoHash enlace){
        this.elemento = elemento;
        this.enlace = enlace;
    }
    
    public Object getElem(){
        return elemento;
    }
    
    public NodoHash getEnlace(){
        return enlace;
    }
    
    public void setElem(Object elemento){
        this.elemento = elemento;
    }
    
    public void setEnlace(NodoHash enlace){
        this.enlace = enlace;
    }
}
