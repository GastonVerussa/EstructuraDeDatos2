package tests.propositoEspecifico;

import lineales.dinamicas.Lista;
import propositoEspecifico.MapeoAMuchosAVL;

public class TestMapeoAMuchosAVL {
    
    public static void main(String args[]){
        
        System.out.println("********  Test para diccionario ********");

        //  Multiplos de numeros menores a 20
        MapeoAMuchosAVL mapeo1 = new MapeoAMuchosAVL();

        for(int i = 2; i <= 10; i++){
            int multiplo = i;
            while(multiplo < 20){
                mapeo1.asociar(i, multiplo);
                multiplo += i;
            }
        }
     
        System.out.println();
        MapeoAMuchosAVL mapeo2 = mapeo1.clone();
        System.out.println("A) Mapeo 1 y Mapeo 2, son iguales?");
        System.out.println("Mapeo 1: " + mapeo1.toString());
        System.out.println("Mapeo 2: " + mapeo2.toString());
        
        System.out.println();
        System.out.println("B) Recuperar rangos de siguientes dominios:");
        System.out.println("Dos: " + mapeo1.obtenerValores(2).toString());
        System.out.println("Tres: " + mapeo1.obtenerValores(3).toString());
        System.out.println("Nueve: " + mapeo1.obtenerValores(9).toString());
        
        System.out.println();
        System.out.println("C) Sacar 3 duplas de Mapeo 1: ");
        System.out.println("Desasociamos 2 -> 8: " + mapeo1.desasociar(2, 8));
        System.out.println(mapeo1.toString());
        System.out.println();
        System.out.println("Desasociamos 4 -> 12: " + mapeo1.desasociar(4, 12));
        System.out.println(mapeo1.toString());
        System.out.println();
        System.out.println("Desasociamos 7 -> 7: " + mapeo1.desasociar(7, 7));
        System.out.println(mapeo1.toString());
        System.out.println();
        
        System.out.println();
        System.out.println("D) Desasociar 2 tuplas que no existen y el unico elemento faltante de dominio 7: ");
        System.out.println("Desasociamos 3 -> 5: " + mapeo1.desasociar(3, 5));
        System.out.println(mapeo1.toString());
        System.out.println();
        System.out.println("Desasociamos 2 -> 8: " + mapeo1.desasociar(1, 7));
        System.out.println(mapeo1.toString());
        System.out.println();
        System.out.println("Desasociamos 7 -> 14: " + mapeo1.desasociar(7, 14));
        System.out.println(mapeo1.toString());
        System.out.println();

        System.out.println();
        System.out.println("E) Asociamos 3 rangos al mapeo1: ");
        System.out.println("7 -> 7: " + mapeo1.asociar(7, 7));
        System.out.println("4 -> 7: " + mapeo1.asociar(4, 7));
        System.out.println("14 -> 3: " + mapeo1.asociar(14, 3));
        System.out.println("Dic 1 y 2 son iguales?");
        System.out.println("Dic 1: " + mapeo1.toString());
        System.out.println("Dic 2: " + mapeo2.toString());
        
        System.out.println();
        System.out.println("F) Vaciar Mapeo 2");
        mapeo2.vaciar();
        System.out.println("Mapeo 2 vacio: " + mapeo2.esVacio());
        System.out.println("Mapeo 1 vacio: " + mapeo1.esVacio());
        
        System.out.println();
        System.out.println("G) Obtener dominio de los mapeos:");
        System.out.println("Dominio mapeo 1: " + mapeo1.obtenerConjuntoDominio().toString());
        System.out.println("Dominio mapeo 2: " + mapeo2.obtenerConjuntoDominio().toString());

        System.out.println();
        System.out.println("H) Obtener rangos de los mapeos:");
        System.out.println("Rango mapeo 1: " + mapeo1.obtenerConjuntoRango().toString());
        System.out.println("Rango mapeo 2: " + mapeo2.obtenerConjuntoRango().toString());
        System.out.println();
        
        System.out.println();
        System.out.println("I)Copiar valores de Mapeo 1 y 2");
        System.out.println("Mapeo 1: " + mapeo1.toString());
        System.out.println("Mapeo 2: " + mapeo2.toString());
        
        
    }
}
