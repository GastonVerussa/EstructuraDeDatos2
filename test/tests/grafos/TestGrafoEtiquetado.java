package tests.grafos;

import grafos.GrafoEtiquetado;
import lineales.dinamicas.Lista;
import utiles.*;

public class TestGrafoEtiquetado {
    
    public static void main(String args[]){
        
        System.out.println("********  Test para grafo etiquetado para enteros ********");
        
        GrafoEtiquetado grafo1 = new GrafoEtiquetado();
        
        String[] locaciones = {"Bosque", "Pantano", "Muelle", "Rio", "Plano", "Isla", "Montaña"};
        
        Lista listaLocaciones = new Lista();
        
        for(int i = 0; i < locaciones.length; i++){
            listaLocaciones.insertar(locaciones[i], 1);
        }
        
        System.out.println("Agregamos los vertices");
        while(!listaLocaciones.esVacia()){
            int aux = Aleatorio.intAleatorio(1, listaLocaciones.longitud());
            String auxString = (String) listaLocaciones.recuperar(aux);
            System.out.println("Insertando " + auxString + ": " + grafo1.insertarVertice(listaLocaciones.recuperar(aux)));
            listaLocaciones.eliminar(aux);
        }
        
        System.out.println("Imprimiendo grafo1: ");
        System.out.println(grafo1.toString());
        
        System.out.println("Agregamos arcos");
        for(int i = 0; i < 15; i++){
            String origen = locaciones[Aleatorio.intAleatorio(0, locaciones.length)];
            String destino = locaciones[Aleatorio.intAleatorio(0, locaciones.length)];
            int distancia = Aleatorio.intAleatorio(1, 10) * 50; //Distancia de 50 a 500
            
            System.out.println("Insertando arco de " + distancia + "km, desde " 
                    + origen + " hasta " + destino + ": " + grafo1.insertarArco(origen, destino, distancia));   
        }
        
        System.out.println("Imprimiendo grafo1: ");
        System.out.println(grafo1.toString());
        
        System.out.println("Clonamos grafo1 a grafo2. Imprimiendo grafo 2: ");
        GrafoEtiquetado grafo2 = grafo1.clone();
        System.out.println(grafo2.toString());
        
        System.out.println("A) Existen los siguientes vertices?");
        System.out.println("Bosque: " + grafo1.existeVertice("Bosque"));
        System.out.println("Lago: " + grafo1.existeVertice("Lago"));
        System.out.println("Pantano: " + grafo1.existeVertice("Pantano"));
        
        System.out.println("B) Existen los siguientes arcos?");
        System.out.println("Bosque a Isla: " + grafo1.existeArco("Bosque", "Isla"));
        System.out.println("Lago a Padrera: " + grafo1.existeArco("Lago", "Pradera"));
        System.out.println("Pantano a Plano: " + grafo1.existeArco("Pantano", "Plano"));
        
        System.out.println("C) Existen los siguientes caminos?");
        System.out.println("Bosque a Montaña: " + grafo1.existeCamino("Bosque", "Montaña"));
        System.out.println("Muelle a Rio: " + grafo1.existeCamino("Muelle", "Rio"));
        System.out.println("Rio a Isla: " + grafo1.existeCamino("Rio", "Isla"));
        
        System.out.println("D) Valor de los siguientes arcos?");
        System.out.println("Bosque a Montaña: " + grafo1.recuperarEtiqueta("Bosque", "Montaña"));
        System.out.println("Isla a Plano: " + grafo1.recuperarEtiqueta("Isla", "Plano"));
        System.out.println("Pantano a Rio: " + grafo1.recuperarEtiqueta("Pantano", "Rio"));
        
        System.out.println("E) Conseguir adyacentes a vertices:");
        System.out.println("Bosque: " + grafo1.obtenerAdyacentes("Bosque"));
        System.out.println("Isla: " + grafo1.obtenerAdyacentes("Isla"));
        System.out.println("Rio: " + grafo1.obtenerAdyacentes("Rio"));
        
        System.out.println("F) Listar en ");
        System.out.println("Anchura: " + grafo1.listarEnAnchura().toString());
        System.out.println("Profundidad: " + grafo1.listarEnProfundidad().toString());
        
        System.out.println("G) Conseguir todos los caminos de Bosque a Montaña:");
        System.out.println(grafo1.caminosPosibles("Bosque", "Montaña").toString());
        
        System.out.println("J) Conseguir ciertos caminos de Bosque a Montaña:");
        System.out.println("Camino con menos vertices: " + grafo1.caminoMasCorto("Bosque", "Montaña").toString());
        System.out.println("Camino con mas vertices: " + grafo1.caminoMasLargo("Bosque", "Montaña").toString());
        
        System.out.println("K) Intentamos eliminar los siguientes arcos: ");
        System.out.println("Bosque a Isla: " + grafo1.eliminarArco("Bosque", "Isla"));
        System.out.println("Pantano a Rio: " + grafo1.eliminarArco("Pantano", "Rio"));
        System.out.println("Muelle a Montaña: " + grafo1.eliminarArco("Muelle", "Montaña"));
        
        System.out.println("Imprimiendo grafo1: ");
        System.out.println(grafo1.toString());
        
        System.out.println("L) Intentamos eliminar los siguientes vertices: ");
        System.out.println("Bosque: " + grafo1.eliminarVertice("Bosque"));
        System.out.println("Plano: " + grafo1.eliminarVertice("Plano"));
        System.out.println("Lago: " + grafo1.eliminarVertice("Lago"));
        
        System.out.println("Imprimiendo grafo1: ");
        System.out.println(grafo1.toString());
        
        System.out.println("Vaciamos grafo2: ");
        grafo2.vaciar();
        
        System.out.println("M) Son vacios?: ");
        System.out.println("grafo1: " + grafo1.esVacio());
        System.out.println("grafo2: " + grafo2.esVacio());
        
        System.out.println("Imprimiendo grafo1: ");
        System.out.println(grafo1.toString());
        
        System.out.println("Imprimiendo grafo2: ");
        System.out.println(grafo2.toString());
    }
}
