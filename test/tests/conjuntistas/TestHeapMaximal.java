package tests.conjuntistas;

import utiles.*;
import conjuntistas.ArbolHeapMaximal;
import lineales.dinamicas.Lista;

public class TestHeapMaximal {
    
    public static void main(String[] args){
        System.out.println("********  Test para arbol HeapMaximal ********");

        ArbolHeapMaximal arbol1 = new ArbolHeapMaximal();
        String[] elems = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre", "Diciembre"};
        Lista listaAux = new Lista();
        
        for(int i = 0; i < elems.length; i++){
            listaAux.insertar(elems[i], 1);
        }
        
        int posicion;
        while(!listaAux.esVacia()){
            posicion = Aleatorio.intAleatorio(1, listaAux.longitud());
            System.out.println("elemento: " + listaAux.recuperar(posicion));
            arbol1.insertar((Comparable) listaAux.recuperar(posicion));
            System.out.println(arbol1.toString());
            listaAux.eliminar(posicion);
        }
        
        System.out.println();
        ArbolHeapMaximal arbol2 = arbol1.clone();
        System.out.println("A) Arbol 1 y Arbol 2, son iguales?");
        System.out.println("Arbol 1: " + arbol1.toString());
        System.out.println("Arbol 2: " + arbol2.toString());
        
        System.out.println();
        System.out.println("B) Recuperar cima y eliminar elementos arbol1");
        System.out.println("Cima: " + arbol1.recuperarCima());
        System.out.println("Eliminar: " + arbol1.eliminarCima());
        System.out.println("Imprime Arbol 1: " + arbol1.toString());
        System.out.println("Cima: " + arbol1.recuperarCima());
        System.out.println("Cima: " + arbol1.recuperarCima());
        System.out.println("Eliminar: " + arbol1.eliminarCima());
        System.out.println("Imprime Arbol 1: " + arbol1.toString());
        System.out.println("Cima: " + arbol1.recuperarCima());
        System.out.println("Eliminar: " + arbol1.eliminarCima());
        System.out.println("Imprime Arbol 1: " + arbol1.toString());

        System.out.println();
        System.out.println("C) Poner 3 elementos (Uno, Dos y Tres) en el Arbol 1: ");
        System.out.println(arbol1.insertar("Uno") && arbol1.insertar("Dos") && arbol1.insertar("Tres"));
        System.out.println("Arbol 1 y Arbol 2 son iguales?");
        System.out.println("Arbol 1: " + arbol1.toString());
        System.out.println("Arbol 2: " + arbol2.toString());

        System.out.println();
        System.out.println("D) Vaciar arbol 2");
        arbol2.vaciar();
        System.out.println("Arbol 2 vacía: " + arbol2.esVacio());
        System.out.println("Arbol 1 vacía: " + arbol1.esVacio());
        
        System.out.println();
        System.out.println("E)Copiar valores de Arbol 1 y Arbol 2");
        System.out.println("Arbol 1: " + arbol1.toString());
        System.out.println("Arbol 2: " + arbol2.toString());
        
        System.out.println("F) Llenar Arbol 1");
        boolean exitoInsertar = true;
        while(exitoInsertar){
            exitoInsertar = arbol1.insertar("Elemento");
            System.out.println("Insertamos Elemento: " + exitoInsertar);
        }
        
        System.out.println("G) Intentamos eliminar en Arbol 2: ");
        System.out.println("Eliminamos elemento: " + arbol2.eliminarCima());
        
        System.out.println();
        System.out.println("H)Copiar valores de Arbol 1 y Arbol 2");
        System.out.println("Arbol 1: " + arbol1.toString());
        System.out.println("Arbol 2: " + arbol2.toString());
    }
}
