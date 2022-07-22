package tests.lineales;

import lineales.estaticas.Cola;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Pila;
import utiles.*;

/**
 *
 * @author nacho
 */
public class TestTPO1 {

    public static void main(String[] args) {
        System.out.println("********  Trabajo Práctico Obligatorio N° 1 ********");

        int op;
        do {
            op = menu();
            switch (op) {
                case 1:
                    testLista();
                    break;
                case 2:
                    testCola();
                    break;
                case 3:
                    testPila();
                    break;
            }

        } while (op != 0);

    }

    public static void testCola() {
        Cola c1 = new Cola();
        String[] elems = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        // La cola estática debe tener capacidad para 6 elementos.
        // pero la completamos con una estructura repetitiva para
        // asegurar que esté llena
        boolean llena = false;
        int i = 0;
        while (!llena) {
            llena = !c1.poner(elems[i]);
            i = (i + 1) % elems.length;
        }

        Cola c2 = c1.clone();
        System.out.println("E) Cola 1 y Cola 2, son iguales?");
        System.out.println("Cola 1: " + c1.toString());
        System.out.println("Cola 2: " + c2.toString());
        System.out.println();

        System.out.print("Sacar 3 elementos de la cola 1: ");
        System.out.println(c1.sacar() && c1.sacar() && c1.sacar());
        System.out.println("F) Cola 1 y Cola 2, son iguales?");
        System.out.println("Cola 1: " + c1.toString());
        System.out.println("Cola 2: " + c2.toString());

        System.out.println();
        System.out.print("Poner 3 elementos en la cola 1: ");
        System.out.println(c1.poner("Uno") && c1.poner("Dos") && c1.poner("Tres"));
        System.out.println("G) Cola 1 y Cola 2 son iguales?");
        System.out.println("Cola 1: " + c1.toString());
        System.out.println("Cola 2: " + c2.toString());

        System.out.println();
        System.out.print("Poner 1 elemento en la cola 1: ");
        System.out.println(c1.poner("Error"));
        System.out.print("Poner 1 elemento en la cola 2: ");
        System.out.println(c2.poner("Error"));

        c2.vaciar();
        System.out.println("H)");
        System.out.println("Cola 2 vacía: " + c2.esVacia());
        System.out.println("Cola 1 vacía: " + c1.esVacia());
        
        System.out.println("I)");
        System.out.print("Obtener frente de la cola 2: ");
        System.out.println(c2.obtenerFrente());
        System.out.print("Obtener frente de la cola 1: ");
        System.out.println(c1.obtenerFrente());
        
        System.out.println("J)Copiar valores de Cola 1 y Cola 2");
        System.out.println("Cola 1: " + c1.toString());
        System.out.println("Cola 2: " + c2.toString());

    }

    public static void testPila() {
        String[] elems = {"Ana Tomía", "Armando Bronca Segura",
            "Elena Nito", "Elsa Capunta", "Estela Gartija", "Helen Chufe",
            "Inés Queleto", "Karen Latada", "Lola Mento", "Marcia Ana",
            "María Luisa Brazo Dorado", "Matías Queroso", "Oscar Acol", "Penélope Luda",
            "Ramona Ponte Alegre", "Rubén Fermizo", "Soila Cerda", "Susana Torio"};

        Pila p1 = new Pila();
        Pila p2 = p1.clone();

        System.out.print("Apilar 3 elementos en pila 1: ");
        System.out.println(p1.apilar(elems[Aleatorio.intAleatorio(0, elems.length - 1)])
                && p1.apilar(elems[Aleatorio.intAleatorio(0, elems.length - 1)])
                && p1.apilar(elems[Aleatorio.intAleatorio(0, elems.length - 1)]));

        p2 = p1.clone();

        System.out.println("A) Pila 1 y Pila 2, son iguales?");
        System.out.println("Pila 1: " + p1.toString());
        System.out.println("Pila 2: " + p2.toString());

        System.out.println();
        System.out.print("Desapilando 2 elementos de la pila 1: ");
        System.out.println(p1.desapilar() && p1.desapilar());
        System.out.print("Apilando 2 elementos de la pila 1: ");
        System.out.println(p1.apilar(elems[Aleatorio.intAleatorio(0, elems.length - 1)])
                && p1.apilar(elems[Aleatorio.intAleatorio(0, elems.length - 1)]));

        System.out.println("B) Pila 1 y Pila 2, son iguales?");
        System.out.println("Pila 1: " + p1.toString());
        System.out.println("Pila 2: " + p2.toString());

        p2.vaciar();
        System.out.println("C) Copiar resultados");
        System.out.println("Pila 1 vacía: " + p1.esVacia());
        System.out.println("Pila 2 vacía: " + p2.esVacia());
        System.out.println("D)Copiar valores de topes de Pila 1 y Pila 2");
        System.out.println("Elemento al tipo de la pila 1: " + p1.obtenerTope());
        System.out.println("Elemento al tipo de la pila 2: " + p2.obtenerTope());
    }

    public static void testLista() {
        String[] elems = {"uno", "dos", "tres", "cuatro", "cinco", "seis"};
        int[] pos = {1, 2, 3, 2, 3, 1};
        int i;

        System.out.println("\n*** Probando Clase LISTA ***\n");

        Lista l1 = new Lista();
        Lista l2 = l1.clone();

        for (i = 0; i < elems.length; i++) {
            if (!l2.insertar(elems[i], pos[i])) {
                System.out.println(
                        "No se pudo insertar el elemento '"
                        + elems[i] + "' en la posición " + pos[i]);
            }
        }

        Lista l3 = l2.clone();

        System.out.println("K) Lista 2 y Lista 3, son iguales?");
        System.out.println("Lista 1: " + l1.toString());
        System.out.println("Lista 2: " + l2.toString());
        System.out.println("Lista 3: " + l3.toString());
        System.out.println();

        System.out.println("L) Copiar valor de la posición");
        System.out.print("Buscar la posición del elemento '" + elems[3] + "' en la Lista 2: ");
        System.out.println(l2.localizar(elems[3]));

        System.out.println("M) Copiar valor de la posición");
        System.out.print("Buscar la posición del elemento 'ocho' en la Lista 2: ");
        System.out.println(l2.localizar("ocho"));

        int posicion = utiles.Aleatorio.intAleatorio(2, 6);
        System.out.println("N) Copiar valor del elemento");
        System.out.print("Buscar el elemento de la posición " + posicion + ": ");
        System.out.println(l2.recuperar(posicion));

        System.out.println("O) Copiar valor del elemento");
        System.out.print("Buscar un elemento en una posición inválida: ");
        System.out.println(l2.recuperar(500));

        System.out.print("Eliminar el elemento de la posición 1: ");
        System.out.println(l2.eliminar(1));

        System.out.print("Eliminar el elemento de la posición 3: ");
        System.out.println(l2.eliminar(3));
        System.out.print("Eliminar elemento de lista vacía: ");
        System.out.println(l1.eliminar(1));

        System.out.println();
        System.out.println("P) Lista 2 y Lista 3, son iguales?");
        System.out.println("Lista 1: " + l1.toString());
        System.out.println("Lista 2: " + l2.toString());
        System.out.println("Lista 3: " + l3.toString());

        l2.vaciar();
        l3.vaciar();

        System.out.println("Q) Copiar valores de listas");
        System.out.println("Lista 1: " + l1.toString());
        System.out.println("Lista 2: " + l2.toString());
        System.out.println("Lista 3: " + l3.toString());

    }

    public static int menu() {
        int op;
        String[] opciones = {
            "Probar clase LISTA",
            "Probar clase COLA",
            "Probar clase PILA"
        };

        System.out.println();
        System.out.println("------------------  MENÚ  ---------------------");
        for (int i = 0; i < opciones.length; i++) {
            System.out.println((i + 1) + ".- " + opciones[i]);
        }
        System.out.println("0.- Salir");
        System.out.println("-----------------------------------------------");

        do {
            op = TecladoIn.readLineInt();
            if (op < 0 || op > opciones.length) {
                System.out.println("El valor ingresado no es válido");
            }
        } while (op < 0 || op > opciones.length);
        return op;
    }
}