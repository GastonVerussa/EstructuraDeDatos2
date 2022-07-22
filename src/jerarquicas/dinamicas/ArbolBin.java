package jerarquicas.dinamicas;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;

public class ArbolBin {
    
    private NodoArbol raiz;
    
    // Crea un árbol binario vacío
    public ArbolBin(){
        raiz = null;
    }
    
    /*  Dado un elemento elemNuevo y un elemento elemPadre, inserta elemNuevo como hijo izquierdo o
        derecho de la primer aparición de elemPadre, según lo indique el parámetro posicion. Para que la operación
        termine con éxito debe existir un nodo en el árbol con elemento = elemPadre y ese nodo debe tener libre
        su hijo posicion. Si puede realizar la inserción devuelve verdadero, en caso contrario devuelve falso. */

    public boolean insertar(Object elemNuevo, Object elemPadre,char lugar){
        
        boolean exito = true;
        
        //  Si esta vacía, inserta como nueva raiz
        if(this.esVacio()){
            raiz = new NodoArbol(elemNuevo, null, null);
        } else {
            //  Si no esta vacía, obtiene el nodo padre
            NodoArbol nodo = ObtenerNodo(raiz, elemPadre);
            //  Si no existe, entonces no hay exito
            if(nodo == null){
                exito = false;
            } else {
                //  Si no,
                //  Si se desea poner a la izquierda y esta libre
                if(lugar == 'I' && nodo.getIzquierdo() == null){
                    //  Se lo crea y enlaza al padre como hijo izquierdo
                    nodo.setIzquierdo(new NodoArbol(elemNuevo, null, null));
                } else {
                    //  Si se desea poner a la derecha y esta libre
                    if(lugar == 'D' && nodo.getDerecho() == null){
                        //  Se lo crea y enlaza al padre como hijo derecho
                        nodo.setDerecho(new NodoArbol(elemNuevo, null, null));
                    } else {
                        //  Si no estaba libre el lugar deseado, no hay exito
                        exito = false;
                    }
                }
            }
        }
        
        return exito;
    }
        
    //  Funcion privada utilizada por insertar(), se utiliza para encontrar un nodo en un subarbol dado por la raiz nodo
    private NodoArbol ObtenerNodo(NodoArbol nodo, Object elemento){
        
        //  Caso default, si no se encuentra retorna null
        NodoArbol resultado = null;
        
        //  Si el nodo existe
        if(nodo != null){
            //  Si es el que buscamos
            if(nodo.getElem().equals(elemento)){
                //  Se retorna el nodo
                resultado = nodo;
            } else {
                //  Si no lo es, se busca recursivamente por el subarbol hijo izquierdo
                NodoArbol auxiliar;
                auxiliar = ObtenerNodo(nodo.getIzquierdo(), elemento);
                //  Si está alli, lo asigna a resultado
                if(auxiliar != null){
                    resultado = auxiliar;
                } else {
                    //  Si no esta alli, busca por el subarbol del hijo derecho
                    auxiliar = ObtenerNodo(nodo.getDerecho(), elemento);
                    //  Si esta allí, lo asigna a resultado
                    if(auxiliar != null){
                        resultado = auxiliar;
                    }
                }
            }
        }
        
        return resultado;
    }
    
    // Devuelve falso si hay al menos un elemento cargado en el árbol y verdadero en caso contrario.
    public boolean esVacio(){
        return raiz == null;
    }
    
    // Devuelve la altura del árbol, es decir la longitud del camino más largo desde la raíz hasta una hoja
    //      (Nota: un árbol vacío tiene altura -1 y una hoja tiene altura 0).
    public int altura(){
        //  Llama a la funcion privada
        return alturaSubArbol(raiz);
    }
    
    //  Funciona privada hecha exclusivamente para la funcion altura(), busca la altura de un nodo en un subarbol
    private int alturaSubArbol(NodoArbol nodo){
        
        int resultado;
        
        //  Caso base, si el nodo no existe (acabamos de pasar una hoja), retornamos -1
        if(nodo == null){
            resultado = -1;
        } else {
            //  Caso recursivo, si el nodo existe, se le asigna el valor maximo entre la altura de los dos subarboles formados por sus hijos, mas uno.
            resultado = Math.max(alturaSubArbol(nodo.getIzquierdo()), alturaSubArbol(nodo.getDerecho())) + 1;
        }
        
        return resultado;
    }
    
    // Devuelve el nivel de un elemento en el árbol. Si el elemento no existe en el árbol devuelve -1.
    public int nivel(Object elemento){
        //  En este caso devuelve la mayor altura entre las apariciones del elemento.
        return nivelEnSubarbol(elemento, raiz);
    } 
    
    //  Funcion privada para encontrar el nivel de un elemento en un subarbol dado
    private int nivelEnSubarbol(Object elemento, NodoArbol nodo){
        
        //  Caso base, si no se encuentra el elemento, retorna -1
        int resultado = -1;
        
        //  Mientras el nodo de parametro exista
        if(nodo != null){
            //  Si es el nodo que buscamos
            if(nodo.getElem().equals(elemento)){
                //  Otro caso base, regresa cero
                resultado = 0;
            } else {
                //  Caso recursivo, si es un nodo que existe y no es el buscado, busca el mayor valor entre el nivel del elemento, 
                //      en los subarboles formados por los hijos del nodo.
                int aux = Math.max(nivelEnSubarbol(elemento, nodo.getIzquierdo()), nivelEnSubarbol(elemento, nodo.getDerecho()));
                //  Mientras valga diferente a -1 (Significa que el elemento fue encontrado en algun subarbol de los hijos)
                if(aux != -1){
                    //  Le suma uno y lo asigna al resultado.
                    resultado = aux + 1;
                }
            }
        }
        
        return resultado;
    }
    
    // Dado un elemento devuelve el valor almacenado en su nodo padre (busca la primera 
    //      aparición de elemento). (En preorden)
    public Object padre(Object elemento){
        return padreEnSubarbol(elemento, raiz, null);
    }
    
    //  Funcion privada realizada para la funcion padre(). Busca al padre de un elemento en un subarbol.
    private Object padreEnSubarbol(Object elemento, NodoArbol nodo, Object elemPadre){
        
        Object resultado;
        
        //  Si se llego al final, entonces el resultado es que no existe, por este camino.
        if(nodo == null){
            resultado = null;
        } else {
            //  Si es el elemento buscado
            if(nodo.getElem().equals(elemento)){
                //  Devuelve el padre
                resultado = elemPadre;
            } else {
                //  Si no, busca en el hijo izquierdo
                Object auxiliar = padreEnSubarbol(elemento, nodo.getIzquierdo(), nodo.getElem());
                //  Si fue encontrado en el subarbol del hijo izquierdo
                if(auxiliar != null){
                    //  Devuelve el resultado
                    resultado = auxiliar;
                } else {
                    //  Si no fue encontrado, repite el proceso con el hijo derecho
                    auxiliar = padreEnSubarbol(elemento, nodo.getDerecho(), nodo.getElem());
                    if(auxiliar != null){
                        resultado = auxiliar;
                    } else {
                        //  Si en el subarbol del hijo derecho tampoco se lo encuentra, se devuelve null, indicando que  
                        //      el elemento buscado no se encuentra en este subarbol.
                        resultado = null;
                    }
                }
            }
        }
        
        return resultado;
    }
    
    // Devuelve una lista con los elementos del árbol binario en el recorrido en preorden
    public Lista listarPreorden(){
        //  Crea la nueva lista para devolver
        Lista resultado = new Lista();
        //  Y mientras no este vacio el arbol, llama a una funcion privada para que llene la lista
        if(!this.esVacio()) listarPreordenAux(raiz, resultado);
        return resultado;
    }
    
    //  Funciona privada para llenar la lista en Preorden
    private void listarPreordenAux(NodoArbol nodo, Lista list){
        
        //  Si el nodo existe
        if(nodo != null){
            //  Llena la lista en Preorden
            list.insertar(nodo.getElem(), list.longitud() + 1);
            listarPreordenAux(nodo.getIzquierdo(), list);
            listarPreordenAux(nodo.getDerecho(), list);
        } 
    }
    
    // Devuelve una lista con los elementos del árbol binario en el recorrido en inorden
    public Lista listarInorden(){
        Lista resultado = new Lista();
        if(!this.esVacio()) listarInordenAux(raiz, resultado);
        return resultado;
    }
    
    //  Funciona privada para llenar la lista en Inorden
    private void listarInordenAux(NodoArbol nodo, Lista list){
        
        //  Si el nodo existe
        if(nodo != null){
            //  Llena la lista en Inorden
            listarInordenAux(nodo.getIzquierdo(), list);
            list.insertar(nodo.getElem(), list.longitud() + 1);
            listarInordenAux(nodo.getDerecho(), list);
        } 
    }
    
    // Devuelve una lista con los elementos del árbol binario en el recorrido en posorden
    public Lista listarPosorden(){
        Lista resultado = new Lista();
        if(!this.esVacio()) listarPosordenAux(raiz, resultado);
        return resultado;
    }
    
    //  Funciona privada para llenar la lista en Posorden
    private void listarPosordenAux(NodoArbol nodo, Lista list){
        
        //  Si el nodo existe
        if(nodo != null){
            //  Llena la lista en Posorden
            listarPosordenAux(nodo.getIzquierdo(), list);
            listarPosordenAux(nodo.getDerecho(), list);
            list.insertar(nodo.getElem(), list.longitud() + 1);
        } 
    }
    
    // Devuelve una lista con los elementos del árbol binario en el recorrido por niveles
    public Lista listarNiveles(){
        
        Lista resultado = new Lista();
        
        //  Si no esta vacía
        if(!this.esVacio()){
            
            //  Cola para ir almacenando los nodos
            Cola colaAuxiliar = new Cola();
            //  auxiliar para recorrer la estructura
            NodoArbol nodoAux;

            //  Empiezo poniendo la raiz en la cola
            colaAuxiliar.poner(raiz);

            //  Mientras la cola no este vacía
            while(!colaAuxiliar.esVacia()){
                //  Consigo el frente de la cola y lo saco
                nodoAux = (NodoArbol) colaAuxiliar.obtenerFrente();
                colaAuxiliar.sacar();
                //  Luego inserto ese elemento al final de la lista.
                resultado.insertar(nodoAux.getElem(), resultado.longitud() + 1);
                //  Y en caso de tener hijos los pongo en la cola
                if(nodoAux.getIzquierdo() != null) colaAuxiliar.poner(nodoAux.getIzquierdo());
                if(nodoAux.getDerecho() != null) colaAuxiliar.poner(nodoAux.getDerecho());
            }
        }
        
        return resultado;
    }
    
    // Genera y devuelve un árbol binario que es equivalente (igual estructura y contenido de los nodos) que
    //      el árbol original.
    @Override
    public ArbolBin clone(){

        ArbolBin resultado = new ArbolBin();
        
        //  Mientras no este vacio, ingreso la raiz al clon, y llamo a una funcion privada auxiliar
        if(!this.esVacio()){
            resultado.insertar(raiz.getElem(), null, 'I');
            cloneAuxiliar(raiz, resultado);
        }
        
        return resultado;
    }
    
    
    //  Funcion privada exclusivamente hecha para clone()
    //  Precondicion: Nodo existe
    private void cloneAuxiliar(NodoArbol nodo, ArbolBin arbol){
        
        //  Si tiene hijo izquierdo
        if(nodo.getIzquierdo() != null){
            //  Lo inserta en el clon y se llama la funcion recursiva con el hijo
            arbol.insertar(nodo.getIzquierdo().getElem(), nodo.getElem(), 'I');
            cloneAuxiliar(nodo.getIzquierdo(), arbol);
        }
        //  Misma logica para el hijo derecho
        if(nodo.getDerecho() != null){
            arbol.insertar(nodo.getDerecho().getElem(), nodo.getElem(), 'D');
            cloneAuxiliar(nodo.getDerecho(), arbol);
        }
    }
    
    // Quita todos los elementos de la estructura. El manejo de memoria es similar al explicado anteriormente
    //      para estructuras lineales dinámicas.
    public void vaciar(){
        raiz = null;
    }
    
    // Genera y devuelve una cadena de caracteres que indica cuál es la raíz del árbol y quienes son los hijos
    //      de cada nodo
    @Override
    public String toString(){
        return toStringAux(raiz);
    }
    
    //  Funcion privada realizada exclusivamente para la funcion toString()
    private String toStringAux(NodoArbol nodo){
        
        //  Caso base, no existe el nodo, devuelve el String vacio
        String resultado = "";
        
        //  Si existe el nodo
        if(nodo != null){
            //  Agrega el elemento del nodo seguido de la flecha para agregar sus hijos
            resultado += nodo.getElem().toString() + " -> ";
            
            //  Variables para referencias mas facilmente a los hijos, ademas de no tener que llamar a la funcion multiples veces
            NodoArbol izquierdo = nodo.getIzquierdo();
            NodoArbol derecho = nodo.getDerecho(); 
            //  Si el izquierdo existe
            if(izquierdo != null){
                //  Agrega su elemento al String
                resultado += izquierdo.getElem().toString();
                //  Si ambos existen, agrega una coma para separarlos
                if(derecho != null){
                    resultado += ", ";
                }
            }
            //  Si el derecho existe, lo agrega
            if(derecho != null){
                resultado += derecho.getElem().toString();
            }
            
            //  Luego si existe alguno de los hijos agrega sus Strings con sus hijos luego de un salto de linea
            if(izquierdo != null)resultado += "\n" + toStringAux(izquierdo);
            if(derecho != null)resultado += "\n" + toStringAux(derecho);
        }
        
        return resultado;
    }
}
