package tests.propositoEspecifico;

import propositoEspecifico.ColaPrioridad;
import utiles.Par;

public class TestColaPrioridad {
    
    
    public static void main(String[] args) {
        
        ColaPrioridad cola1 = new ColaPrioridad();
        
        System.out.println("Insertando (Tomas, 2):");
        cola1.insertar("Tomas", 2);
        System.out.println("Cola1: ");
        System.out.println(cola1.toString());
        System.out.println("Insertando (Luca, 4):");
        cola1.insertar("Luca", 4);
        System.out.println("Cola1: ");
        System.out.println(cola1.toString());
        System.out.println("Insertando (Josefina, 2):");
        cola1.insertar("Josefina", 2);
        System.out.println("Cola1: ");
        System.out.println(cola1.toString());
        System.out.println("Insertando (Victoria, 1):");
        cola1.insertar("Victoria", 1);
        System.out.println("Cola1: ");
        System.out.println(cola1.toString());
        System.out.println("Insertando (Camila, 4):");
        cola1.insertar("Camila", 4);
        System.out.println("Cola1: ");
        System.out.println(cola1.toString());
        System.out.println("Insertando (Valentina, 1):");
        cola1.insertar("Valentina", 1);
        System.out.println("Cola1: ");
        System.out.println(cola1.toString());
        System.out.println("Insertando (Mateo, 2):");
        cola1.insertar("Mateo", 2);
        System.out.println("Cola1: ");
        System.out.println(cola1.toString());
        
        System.out.println("A) Clonamos la cola");
        ColaPrioridad cola2 = cola1.clone();
        System.out.println("Cola1: ");
        System.out.println(cola1.toString());
        System.out.println("Cola2: ");
        System.out.println(cola2.toString());
        
        System.out.println("B) Obtener tope y borrar de cola 1");
        System.out.println("tope: " + cola1.obtenerFrente());
        System.out.println("eliminar : " + cola1.eliminarFrente());
        System.out.println("tope: " + cola1.obtenerFrente());
        System.out.println("tope: " + cola1.obtenerFrente());
        System.out.println("eliminar : " + cola1.eliminarFrente());
        System.out.println("tope: " + cola1.obtenerFrente());
        System.out.println("eliminar : " + cola1.eliminarFrente());
        System.out.println("Cola1: ");
        System.out.println(cola1.toString());
        
        System.out.println("C) Vaciamos cola2.");
        cola2.vaciar();
        
        System.out.println("Estan vacios?");
        System.out.println("Cola1: " + cola1.esVacia());
        System.out.println("Cola2: " + cola2.esVacia());
        
        System.out.println("D) Intentamos eliminar cola2: " + cola2.eliminarFrente());
        System.out.println("Intentamos eliminar cola2: " + cola2.eliminarFrente());
        
        System.out.println("E) Son iguales?");
        System.out.println("Cola1: ");
        System.out.println(cola1.toString());
        System.out.println("Cola2: ");
        System.out.println(cola2.toString());
        
    }
    
}
