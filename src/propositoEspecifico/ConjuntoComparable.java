package propositoEspecifico;

import conjuntistas.ArbolAVL;
import lineales.dinamicas.Lista;

public class ConjuntoComparable {
    
    ArbolAVL elementos;
    
    //  Crea un conjunto sin elementos.
    public ConjuntoComparable(){
        elementos = new ArbolAVL();
    }

    //  Recibe un valor de tipo elemento. Si no existe previamente en el conjunto y 
    //      lo puede agregar con éxito devuelve verdadero y falso en caso contrario.
    public boolean agregar(Comparable valor){
        return elementos.insertar(valor);
    }
    
    //  Elimina al elemento del conjunto. Si lo encuentra y la operación de eliminación 
    //      termina con éxito devuelve verdadero y falso en caso contrario.
    public boolean quitar(Comparable valor){
        return elementos.eliminar(valor);
    }

    //  Si en el conjunto se encuentra almacenado el elemento ingresado por parámetro, 
    //      esta operación devuelve verdadero y falso en caso contrario.
    public boolean pertenece(Comparable valor){
        return elementos.pertenece(valor);
    }

    //  Devuelve falso si hay al menos un elemento y verdadero en caso contrario.
    public boolean esVacio(){
        return elementos.esVacio();
    }

    //  Devuelve un Conjunto clon del original
    @Override
    public ConjuntoComparable clone(){
        
        //  Variable que se devolvera como resultado
        ConjuntoComparable resultado = new ConjuntoComparable();
        
        //  Lista auxiliar de los elementos del conjunto
        Lista auxLista = elementos.listar();
        
        //  Mientras no este vacia, quedan elementos por clonar
        while(!auxLista.esVacia()){
            //  Agrega el primer elemento de la lista, el mas rapido para encontrar, al clon
            resultado.agregar((Comparable) auxLista.recuperar(1));
            //  Y lo elimina de la lista
            auxLista.eliminar(1);
        }
        
        return resultado;
    }
    
    //  Devuelve un String con la informacion de los elementos que contiene
    @Override
    public String toString(){
        
        //  Variable que se devolvera como resultado
        String resultado;
        
        //  Lista auxiliar de los elementos del conjunto
        Lista auxLista = elementos.listar();
        
        //  Si la lista esta vacia
        if(auxLista.esVacia()){
            //  Entonces el resultado es el siguiente
            resultado = "Conjunto vacio";
        } else {
            //  Si no, entonces envia el String que devuelva la Lista
            resultado = auxLista.toString();
        }
        
        return resultado;
    }
}
