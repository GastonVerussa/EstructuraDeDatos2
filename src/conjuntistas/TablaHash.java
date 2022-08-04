
package conjuntistas;

import FuncionesHash.*;
import lineales.dinamicas.Lista;

public class TablaHash {
    
    private final int TAMANIO;
    private final NodoHash[] tabla;
    private int cantidad;
    
    //  Constructor vacio
    public TablaHash(){
        TAMANIO = 20;
        tabla = new NodoHash[TAMANIO];
        cantidad = 0;
    }
    
    //  Constructor vacio
    public TablaHash(int TAMANIO){
        this.TAMANIO = TAMANIO;
        tabla = new NodoHash[TAMANIO];
        cantidad = 0;
    }
    
    //  Recibe un elemento e intenta insertarlo en la tabla. Si todo funciona OK (no está repetido y hay
    //      lugar suciente en la tabla) devuelve verdadero, si hay algún problema devuelve falso
    public boolean insertar(Object elemento){
        
        //  Variable que guarda el resultado
        boolean exito = true;
        
        
        
        //  Calcula la posicion
        int posicion = calcularPosicion(elemento); 
        //  Auxiliar para revisar la lista en la posicion
        NodoHash auxiliar = tabla[posicion];
        
        //  Mientras no se haya llegado al final de la lista ni haya ocurrido un error
        while(auxiliar != null && exito){
            //  Si encuentra el elemento
            if(auxiliar.getElem().equals(elemento)){
                //  Entonces esta repetido, por lo que no hay exito
                exito = false;
            } else {
                //  Si no es el elemento, se consigue el enlace para seguir revisando
                auxiliar = auxiliar.getEnlace();
            }
        }
        
        //  Si hubo exito, es decir que no esta repetido el elemento
        if(exito){
            //  Entonces asigna el inicio de la lista de la posicion como un nuevo nodo y lo enlaza al viejo inicio
            tabla[posicion] = new NodoHash(elemento, tabla[posicion]);
            //  Aumenta la cantidad
            cantidad++;
        }
        
        return exito;
    }
    
    //  Recibe el elemento que se desea eliminar y se procede a quitarlo de la tabla. Si todo funciona OK
    //      (el elemento estaba cargado previamente en la tabla) devuelve verdadero, si hay algún problema
    //      devuelve falso
    public boolean eliminar(Object elemento){
        
        //  Variable que guarda el resultado, si la tabla esta vacia, exito es falso
        boolean exito = false;
        
        // Si la tabla no esta vacia
        if(!this.esVacia()){
            //  Calcula la posicion
            int posicion = calcularPosicion(elemento);
            //  Auxiliar para revisar la lista en la posicion
            NodoHash auxiliar = tabla[posicion];
            //  Caso base, si el primer nodo en la posicion tiene el elemento
            if(auxiliar.getElem().equals(elemento)){
                //  Su enlace pasa a ser la cabecera, en caso de que sea el unico nodo sera null
                tabla[posicion] = auxiliar.getEnlace();
                //  Se reduce la cantidad y se pasa el exito a verdadero
                cantidad--;
                exito = true;
            } else {
                //  Si no es el primer nodo, se revisan los enlaces hasta que no hayan mas
                //      o la funcion haya tenido exito (el elemento fue encontrado y borrado)
                while(auxiliar.getEnlace() != null && !exito){
                    //  Si encuentra el elemento
                    if(auxiliar.getEnlace().getElem().equals(elemento)){
                        //  Lo borra, reduce la cantidad y pasa el exito a verdadero
                        auxiliar.setEnlace(auxiliar.getEnlace().getEnlace());
                        cantidad--;
                        exito = true;
                    } else {
                        //  Si no es el elemento, se consigue el enlace para seguir revisando
                        auxiliar = auxiliar.getEnlace();
                    }
                }
            }
        }
        
        return exito;
    }
    
    //  Recibe el elemento y devuelve verdadero si ya está cargado en la tabla y falso en caso contrario
    public boolean pertenece(Object elemento){
        
        //  Variable que guarda el exito
        boolean exito = false;
        
        //  Calcula la posicion
        int posicion = calcularPosicion(elemento);
        
        //  Variable para recorrer los enlaces
        NodoHash auxiliar = tabla[posicion];
        
        //  Recorre los enlaces hasta llegar el final o haber tenido exito buscando el elemento
        while(auxiliar != null && !exito){
            //  Si encuentra el elemento
            if(auxiliar.getElem().equals(elemento)){
                //  Entonces el elemento pertenece, hay exito
                exito = true;
            } else {
                //  Si no es el elemento, se consigue el enlace para seguir revisando
                auxiliar = auxiliar.getEnlace();
            }
        }
        
        return exito;
    }

    //  Devuelve falso si hay al menos un elemento cargado en la tabla y verdadero en caso contrario
    public boolean esVacia(){
        return this.cantidad == 0;
    }

    //  Recorre la tabla completa y devuelve una lista con los elementos que se encuentran almacenados
    //      en la tabla. Es útil para mostrar los datos sin depender del dispositivo de salida (consola, ventana,
    //      etc)
    public Lista listar (){
        
        //  Lista que se pasara como resultaod final
        Lista resultado = new Lista();
        
        //  Entero para saber en que posicion nos encontramos
        int posicion = 0;
        //  Auxiliar para recorrer la estructura
        NodoHash auxiliar;
        
        //  Mientras la longitud de la lista sea menor a la cantidad de elementos en la tabla,
        //      Esto significa que faltan elementos por enlistar.
        while(resultado.longitud() < this.cantidad){
            //  Auxiliar toma el nodo en la posicion actual
            auxiliar = tabla[posicion];
            //  Mientras queden nodos por enlistar en la posicion
            while(auxiliar != null){
                //  Lo inserta al final de la lista
                resultado.insertar(auxiliar.getElem(), resultado.longitud() + 1);
                //  Y va al siguiente nodo
                auxiliar = auxiliar.getEnlace();
            }
            //  Cuando termina de reivsar todos los nodos de la posicion, pasa a la siguiente posicion
            posicion++;
        }
        
        return resultado;
    }
    
    private int calcularPosicion(Object elemento){
        
        int resultado;    

        int elementoInt;
        
        //  Si no es int, le aplica una funcion para sumar los valores de todos los caracteres
        //      de su toString(), devolviendo un entero
        if(!elemento.getClass().getSimpleName().equals("Integer")){
            elementoInt = FuncionesString.hashCadena(elemento.toString(), TAMANIO);
        } else {
            //  Si es un int, simplemente obtiene su valor
            elementoInt = (int) elemento;
        }
        
        //  Luego aplica una funcion hash al valor obtenido
        
        //  Si no es muy grande (No le saca al menos dos digitos de diferencia al tamaño)
        if(elementoInt < TAMANIO * 100){
            //  Aplica al funcion cuadrado, esperando evitar conflictor
            resultado = FuncionesEnteros.hashCuadrado(elementoInt, TAMANIO);
        } else {
            //  Si es muy grande, aplica doblamiento, ya que es mas simple
            resultado = FuncionesEnteros.hashDoblamiento(elementoInt, TAMANIO);
        }

        return resultado;
    }
}
