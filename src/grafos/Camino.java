package grafos;

import lineales.dinamicas.Lista;

public class Camino {

    private Lista recorrido;
    private int distancia;

    public Camino(Lista recorrido, int distancia){
        this.recorrido = recorrido;
        this.distancia = distancia;
    }

    public Lista getRecorrido(){
        return this.recorrido;
    }

    public int getDistancia(){
        return this.distancia;
    }

    public void setRecorrido(Lista recorrido){
        this.recorrido = recorrido;
    }

    public void setDistancia(int distancia){
        this.distancia = distancia;
    }

    public void aumentarDistancia(int suma){
        this.distancia += suma;
    }

    @Override
    public Camino clone(){
        return new Camino(recorrido.clone(), this.distancia);
    }

    @Override
    public String toString(){
        return "Recorrido: " + recorrido.toString() + ". Distancia: " + distancia + "\n";
    }
}