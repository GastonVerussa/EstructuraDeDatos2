package tests.conjuntistas;

import utiles.*;
import conjuntistas.ArbolAVL;
import lineales.dinamicas.Lista;


public class TestAVL {
    
    public static void main(String[] args){
        System.out.println("********  Test para arbol AVL ********");

        ArbolAVL arbol1 = new ArbolAVL();
        String[] elems = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
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
        ArbolAVL arbol2 = arbol1.clone();
        System.out.println("A) Arbol 1 y Arbol 2, son iguales?");
        System.out.println("Arbol 1: " + arbol1.toString());
        System.out.println("Arbol 2: " + arbol2.toString());
        
        System.out.println();
        System.out.println("B) Pertenecen estos elementos al arbol 1?");
        System.out.println("Marzo: " + arbol1.pertenece("Marzo"));
        System.out.println("marzo: " + arbol2.pertenece("marzo"));
        System.out.println("Julio: " + arbol2.pertenece("Julio"));
        System.out.println("Sabado: " + arbol2.pertenece("Sabado"));
        
        System.out.println();
        System.out.println("C) Sacar 3 elementos del arbol 1: ");
        System.out.println("Eliminamos Enero: " + arbol1.eliminar("Enero"));
        System.out.println(arbol1.toString());
        System.out.println();
        System.out.println("Eliminamos Agosto: " + arbol1.eliminar("Agosto"));
        System.out.println(arbol1.toString());
        System.out.println();
        System.out.println("Eliminamos Noviembre: " + arbol1.eliminar("Noviembre"));
        System.out.println(arbol1.toString());
        System.out.println();
        
        System.out.println();
        System.out.println("D) Sacar 3 elementos repetidos o que no existen del arbol 1: ");
        System.out.println("Eliminamos Enero: " + arbol1.eliminar("Enero"));
        System.out.println(arbol1.toString());
        System.out.println();
        System.out.println("Eliminamos Agosto: " + arbol1.eliminar("Agosto"));
        System.out.println(arbol1.toString());
        System.out.println();
        System.out.println("Eliminamos Domingo: " + arbol1.eliminar("Domingo"));
        System.out.println(arbol1.toString());
        System.out.println();

        System.out.println();
        System.out.println("E) Poner 3 elementos (Uno, Dos y Tres) en el arbol 1: ");
        System.out.println(arbol1.insertar("Uno") && arbol1.insertar("Dos") && arbol1.insertar("Tres"));
        System.out.println("Arbol 1 y Arbol 2 son iguales?");
        System.out.println("Arbol 1: " + arbol1.toString());
        System.out.println("Arbol 2: " + arbol2.toString());

        System.out.println();
        System.out.println("F) Vaciar arbol 2");
        arbol2.vaciar();
        System.out.println("Arbol 2 vacía: " + arbol2.esVacio());
        System.out.println("Arbol 1 vacía: " + arbol1.esVacio());
        
        System.out.println();
        System.out.println("G)");
        System.out.print("Obtener lista arbol 2: ");
        System.out.println(arbol2.listar().toString());
        System.out.print("Obtener lista arbol 1: ");
        System.out.println(arbol1.listar().toString());
        
        System.out.println();
        System.out.println("H)");
        System.out.print("Obtener lista entre Inicio y Parar arbol 2: ");
        System.out.println(arbol2.listarRango("Inicio", "Parar").toString());
        System.out.print("Obtener lista entre Inicio y Parar arbol 1: ");
        System.out.println(arbol1.listarRango("Inicio", "Parar").toString());
        
        System.out.println();
        System.out.println("I)");
        System.out.println("Obtener minimo elemento arbol 1: " + arbol1.minimoElem());
        System.out.println("Obtener maximo elemento arbol 1: " + arbol1.maximoElem());
        System.out.println("Obtener minimo elemento arbol 2: " + arbol2.minimoElem());
        System.out.println("Obtener maximo elemento arbol 2: " + arbol2.maximoElem());
        
        System.out.println();
        System.out.println("J)Copiar valores de Arbol 1 y Arbol 2");
        System.out.println("Arbol 1: " + arbol1.toString());
        System.out.println("Arbol 2: " + arbol2.toString());
    }
}
