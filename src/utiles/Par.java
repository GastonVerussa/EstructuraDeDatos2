package utiles;

public class Par<Tipo1, Tipo2>{
    
    private Tipo1 valor1;
    private Tipo2 valor2;

    public Par(Tipo1 valor1, Tipo2 valor2){
        this.valor1 = valor1;
        this.valor2 = valor2;
    }

    public Tipo1 getValor1(){
        return this.valor1;
    }

    public Tipo2 getValor2(){
        return this.valor2;
    }

    public void setValor1(Tipo1 valor1){
        this.valor1 = valor1;
    }

    public void setValor2(Tipo2 valor2){
        this.valor2 = valor2;
    }
}
