package utiles;

public class Resultado<Tipo> {
    
    private Tipo resultado;

    public Resultado(Tipo resultado) {
        this.resultado = resultado;
    }
    
    public Tipo get(){
        return this.resultado;
    }
    
    public void set(Tipo resultado){
        this.resultado = resultado;
    }
}
