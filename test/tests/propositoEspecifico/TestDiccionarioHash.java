package tests.propositoEspecifico;

import propositoEspecifico.DiccionarioHash;
import lineales.dinamicas.Lista;
import utiles.Aleatorio;

public class TestDiccionarioHash {
      
    public static void main(String args[]){
        
        System.out.println("********  Test para diccionario ********");

        DiccionarioHash dic1 = new DiccionarioHash();
        Lista listaAux = new Lista();
        listaAux.insertar(new Prueba("Cero", 0), 1);
        listaAux.insertar(new Prueba("Uno", 1), 2);
        listaAux.insertar(new Prueba("Dos", 2), 3);
        listaAux.insertar(new Prueba("Tres", 3), 4);
        listaAux.insertar(new Prueba("Cuatro", 4), 5);
        listaAux.insertar(new Prueba("Cinco", 5), 6);
        listaAux.insertar(new Prueba("Seis", 6), 7);
        listaAux.insertar(new Prueba("Siete", 7), 8);
        
        int posicion;
        Prueba aux;
        while(!listaAux.esVacia()){
            posicion = Aleatorio.intAleatorio(1, listaAux.longitud());
            System.out.println("elemento: " + listaAux.recuperar(posicion).toString());
            aux = (Prueba) listaAux.recuperar(posicion);
            dic1.insertar(aux.getClave(), aux.getDato());
            System.out.println(dic1.toString());
            listaAux.eliminar(posicion);
        }
        
        System.out.println();
        DiccionarioHash dic2 = dic1.clone();
        System.out.println("A) Dic 1 y Dic 2, son iguales?");
        System.out.println("Dic 1: " + dic1.toString());
        System.out.println("Dic 2: " + dic2.toString());
        
        System.out.println();
        System.out.println("B) Existen estas claves en dic 1?");
        System.out.println("Uno: " + dic1.existeClave("Uno"));
        System.out.println("Cinco: " + dic1.existeClave("Cinco"));
        System.out.println("Nueve: " + dic1.existeClave("Nueve"));
        
        System.out.println();
        System.out.println("C) Sacar 3 elementos de dic 1: ");
        System.out.println("Eliminamos Uno: " + dic1.eliminar("Uno"));
        System.out.println(dic1.toString());
        System.out.println();
        System.out.println("Eliminamos Cuatro: " + dic1.eliminar("Cuatro"));
        System.out.println(dic1.toString());
        System.out.println();
        System.out.println("Eliminamos Cinco: " + dic1.eliminar("Cinco"));
        System.out.println(dic1.toString());
        System.out.println();
        
        System.out.println();
        System.out.println("D) Sacar 3 elementos repetidos o que no existen del dic 1: ");
        System.out.println("Eliminamos Uno: " + dic1.eliminar("Uno"));
        System.out.println(dic1.toString());
        System.out.println();
        System.out.println("Eliminamos Cuatro: " + dic1.eliminar("Cuatro"));
        System.out.println(dic1.toString());
        System.out.println();
        System.out.println("Eliminamos Once: " + dic1.eliminar("Once"));
        System.out.println(dic1.toString());
        System.out.println();

        System.out.println();
        System.out.println("E) Poner 3 elementos (Ocho, Nueve, Diez) en la cola 1: ");
        System.out.println(dic1.insertar("Ocho", 8) && dic1.insertar("Nueve", 9) && dic1.insertar("Diez", 10));
        System.out.println("Dic 1 y 2 son iguales?");
        System.out.println("Dic 1: " + dic1.toString());
        System.out.println("Dic 2: " + dic2.toString());
        
        System.out.println();
        System.out.println("F) Obtener informacion de dic 1");
        System.out.println("Cero: " + dic1.obtenerInformacion("Cero"));
        System.out.println("Diez: " + dic1.obtenerInformacion("Diez"));
        System.out.println("Seis: " + dic1.obtenerInformacion("Seis"));

        System.out.println();
        System.out.println("G) Vaciar Dic 2");
        dic2.vaciar();
        System.out.println("Dic 2 vacía: " + dic2.esVacio());
        System.out.println("Dic 1 vacía: " + dic1.esVacio());
        
        System.out.println();
        System.out.println("H)");
        System.out.print("Obtener lista claves Dic 2: ");
        System.out.println(dic2.listarClaves().toString());
        System.out.print("Obtener lista claves Dic 1: ");
        System.out.println(dic1.listarClaves().toString());
        
        System.out.println();
        System.out.println("I)");
        System.out.print("Obtener lista datos Dic 2: ");
        System.out.println(dic2.listarDatos().toString());
        System.out.print("Obtener lista datos Dic 1: ");
        System.out.println(dic1.listarDatos().toString());
        
        System.out.println();
        System.out.println("J)Copiar valores de Dic 1 y 2");
        System.out.println("Dic 1: " + dic1.toString());
        System.out.println("Dic 2: " + dic2.toString());
        
        
    }
    
}
