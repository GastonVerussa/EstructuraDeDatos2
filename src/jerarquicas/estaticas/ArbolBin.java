package jerarquicas.estaticas;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;

public class ArbolBin {
    
    //  Constante compartida por los arboles para definir el tamanio maximo
    private static final int TAMANIO = 20;
    //  Arreglo con todos los nodos
    private CeldaBin[] arreglo;
    //  Entero que marca la posicion de la raiz
    private int raiz;
    
    // Crea un árbol binario vacío
    public ArbolBin(){
        //  Crea el arreglo y las celdas binarias para cada celda del arreglo
        arreglo = new CeldaBin[TAMANIO];
        for(int i = 0; i < TAMANIO; i++){
            this.arreglo[i] = new CeldaBin();
        }
        raiz = -1;
    }
    
    /*  Dado un elemento elemNuevo y un elemento elemPadre, inserta elemNuevo como hijo izquierdo o
        derecho de la primer aparición de elemPadre, según lo indique el parámetro posicion. Para que la operación
        termine con éxito debe existir un nodo en el árbol con elemento = elemPadre y ese nodo debe tener libre
        su hijo posicion. Si puede realizar la inserción devuelve verdadero, en caso contrario devuelve falso. */

    public boolean insertar(Object elemNuevo,Object elemPadre,char lugar){
        
        boolean exito = true;
        
        //  Si esta vacía, inserta como nueva raiz
        if(this.esVacio()){
            this.raiz = 0;
            arreglo[0].setAtributos(elemNuevo, -1, -1);
        } else {
            //  Si no esta vacía, obtiene la posicion del nodo padre
            int aux = ObtenerPosicionNodo(raiz, elemPadre);
            //  Si no existe, entonces no hay exito
            if(aux == -1){
                exito = false;
            } else {
                //  Si pudo encontrarlo, consigue el nodo
                CeldaBin nodo = arreglo[aux];
                //  Y busca el primer lugar libre del arreglo (Sabemos que al menos hay uno, ya que no esta vacia)
                int lugarLibre = 0;
                while(arreglo[lugarLibre].getEnUso()){
                    lugarLibre++;
                }
                //  Si se desea poner a la izquierda y esta libre
                if(lugar == 'I' && nodo.getIzquierdo() == -1){
                    //  Se lo crea en el lugar libre y se enlaza al padre
                    arreglo[lugarLibre].setAtributos(elemNuevo, -1, -1);
                    nodo.setIzquierdo(lugarLibre);
                } else {
                    //  Si se desea poner a la derecha y esta libre
                    if(lugar == 'D' && nodo.getDerecho() == -1){
                    //  Se lo crea en el lugar libre y se enlaza al padre
                        arreglo[lugarLibre].setAtributos(elemNuevo, -1, -1);
                        nodo.setDerecho(lugarLibre);
                    } else {
                        //  Si no estaba libre el lugar deseado, no hay exito
                        exito = false;
                    }
                }
            }
        }
        
        return exito;
    }
        
    //  Funcion privada utilizada por insertar(), se utiliza para encontrar la posicion de un nodo
    private int ObtenerPosicionNodo(int posicion, Object elemento){
        
        //  Caso default, si no se encuentra retorna -1
        int resultado = -1;
        
        //  Si la posicion es valida
        if(posicion != -1){
            //  Se consigue el nodo de la posicion
            CeldaBin nodo = arreglo[posicion];
            //  Si es el que buscamos
            if(nodo.getElem().equals(elemento)){
                //  Se retorna la posicion del nodo
                resultado = posicion;
            } else {
                //  Si no lo es, se busca recursivamente por el subarbol hijo izquierdo
                int auxiliar = ObtenerPosicionNodo(nodo.getIzquierdo(), elemento);
                //  Si está alli, lo asigna a resultado
                if(auxiliar != -1){
                    resultado = auxiliar;
                } else {
                    //  Si no esta alli, busca por el subarbol del hijo derecho
                    auxiliar = ObtenerPosicionNodo(nodo.getDerecho(), elemento);
                    //  Si esta allí, lo asigna a resultado
                    if(auxiliar != -1){
                        resultado = auxiliar;
                    }
                }
            }
        }
        
        return resultado;
    }
    
    // Devuelve falso si hay al menos un elemento cargado en el árbol y verdadero en caso contrario.
    public boolean esVacio(){
        return raiz == -1;
    }
    
    // Devuelve la altura del árbol, es decir la longitud del camino más largo desde la raíz hasta una hoja
    //      (Nota: un árbol vacío tiene altura -1 y una hoja tiene altura 0).
    public int altura(){
        //  Llama a la funcion privada
        return alturaSubArbol(raiz);
    }
    
    //  Funciona privada hecha exclusivamente para la funcion altura(), busca la altura de un nodo en un subarbol
    private int alturaSubArbol(int posNodo){
        
        int resultado;
        
        //  Caso base, si el nodo no existe (acabamos de pasar una hoja), retornamos -1
        if(posNodo == -1){
            resultado = -1;
        } else {
            //  Caso recursivo, si el nodo existe, se le asigna el valor maximo entre la altura de los dos subarboles formados por sus hijos, mas uno.
            resultado = Math.max(alturaSubArbol(arreglo[posNodo].getIzquierdo()), alturaSubArbol(arreglo[posNodo].getDerecho())) + 1;
        }
        
        return resultado;
    }
    
    // Devuelve el nivel de un elemento en el árbol. Si el elemento no existe en el árbol devuelve -1.
    public int nivel(Object elemento){
        //  En este caso devuelve la mayor altura entre las apariciones del elemento.
        return nivelEnSubarbol(elemento, raiz);
    } 
    
    //  Funcion privada para encontrar el nivel de un elemento en un subarbol dado
    private int nivelEnSubarbol(Object elemento, int posNodo){
        
        //  Caso base, si no se encuentra el elemento, retorna -1
        int resultado = -1;
        
        //  Mientras el nodo de parametro exista
        if(posNodo != -1){
            //  Se crea una variable CeldaBin para una referencia mas simple
            CeldaBin aux = arreglo[posNodo];
            //  Si es el nodo que buscamos
            if(aux.getElem().equals(elemento)){
                //  Otro caso base, regresa cero
                resultado = 0;
            } else {
                //  Caso recursivo, si es un nodo que existe y no es el buscado, busca el mayor valor entre el nivel del elemento, 
                //      en los subarboles formados por los hijos del nodo.
                int temp = Math.max(nivelEnSubarbol(elemento, aux.getIzquierdo()), nivelEnSubarbol(elemento, aux.getDerecho()));
                //  Mientras valga diferente a -1 (Significa que encontraron el elemento)
                if(temp != -1){
                    //  Le suma uno y lo asigna al resultado.
                    resultado = temp + 1;
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
    private Object padreEnSubarbol(Object elemento, int posNodo, Object elemPadre){
        
        Object resultado;
        
        //  Si se llego al final, entonces el resultado es que no existe, por este camino.
        if(posNodo == -1){
            resultado = null;
        } else {
            //  CeldaBin auxiliar para mas facil referencia al nodo
            CeldaBin nodo = arreglo[posNodo];
            //  Si es el elemento buscado
            if(nodo.getElem().equals(elemento)){
                //  Lo devuelve como el padre
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
    private void listarPreordenAux(int posNodo, Lista list){
        
        //  Si el nodo tenga una posicion valida (Existe)
        if(posNodo != -1){
            //  Crea una variable para mas facil referencia
            CeldaBin nodo = arreglo[posNodo];
            //  Y llena la lista en Preorden
            
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
    private void listarInordenAux(int posNodo, Lista list){
        
        //  Si el nodo tenga una posicion valida (Existe)
        if(posNodo != -1){
            //  Crea una variable para mas facil referencia
            CeldaBin nodo = arreglo[posNodo];
            //  Y llena la lista en Inorden
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
    private void listarPosordenAux(int posNodo, Lista list){
        
        //  Si el nodo tenga una posicion valida (Existe)
        if(posNodo != -1){
            //  Crea una variable para mas facil referencia
            CeldaBin nodo = arreglo[posNodo];
            //  Y llena la lista en Posorden
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
            CeldaBin aux;

            //  Empiezo poniendo la raiz en la cola
            colaAuxiliar.poner(arreglo[raiz]);

            //  Mientras la cola no este vacía
            while(!colaAuxiliar.esVacia()){
                //  Consigo el frente de la cola y lo saco
                aux = (CeldaBin) colaAuxiliar.obtenerFrente();
                colaAuxiliar.sacar();
                //  Luego inserto ese elemento al final de la lista.
                resultado.insertar(aux, resultado.longitud() + 1);
                //  Y en caso de tener hijos los pongo en la cola
                if(aux.getIzquierdo() != -1) colaAuxiliar.poner(arreglo[aux.getIzquierdo()]);
                if(aux.getDerecho() != -1) colaAuxiliar.poner(arreglo[aux.getDerecho()]);
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
            resultado.insertar(arreglo[raiz].getElem(), null, 'I');
            cloneAuxiliar(raiz, resultado);
        }
        
        return resultado;
    }
    
    
    //  Funcion privada exclusivamente hecha para clone()
    //  Precondicion: Posicion válida
    private void cloneAuxiliar(int posNodo, ArbolBin arbol){
        
        CeldaBin nodo = arreglo[posNodo];
        
        //  Si tiene hijo izquierdo
        if(nodo.getIzquierdo() != -1){
            //  Lo inserta en el clon y se llama la funcion recursiva con el hijo
            arbol.insertar(arreglo[nodo.getIzquierdo()].getElem(), nodo.getElem(), 'I');
            cloneAuxiliar(nodo.getIzquierdo(), arbol);
        }
        //  Misma logica para el hijo derecho
        if(nodo.getDerecho() != -1){
            arbol.insertar(arreglo[nodo.getDerecho()].getElem(), nodo.getElem(), 'D');
            cloneAuxiliar(nodo.getDerecho(), arbol);
        }
    }
    
    // Quita todos los elementos de la estructura. 
    public void vaciar(){
        //  Si no esta vacio, lo vacia
        if(!this.esVacio())vaciarAux(raiz);
    }
    
    //  Funcion privada realizada exclusivamente para vaciar()
    private void vaciarAux(int posNodo){
        //  Si el nodo existe
        if(posNodo != -1){
            //  Auxiliar para mas simple referencia
            CeldaBin aux = arreglo[posNodo];
            //  Llama recursivamente con el hijo izquierdo y derecho
            vaciarAux(aux.getIzquierdo());
            vaciarAux(aux.getDerecho());
            //  Y luego se vacia (Podria simplemente setearse el "enUso" en falso tambien.
            aux.vaciar();
        }
    }
    
    // Genera y devuelve una cadena de caracteres que indica cuál es la raíz del árbol y quienes son los hijos
    //      de cada nodo
    @Override
    public String toString(){
        return toStringAux(raiz);
    }
    
    //  Funcion privada realizada exclusivamente para la funcion toString()
    private String toStringAux(int posNodo){
        
        //  Caso base, no existe el nodo, devuelve el String vacio
        String resultado = "";
        
        //  Si existe el nodo
        if(posNodo != -1){
            //  Variable para mejor referencia al nodo
            CeldaBin nodo = arreglo[posNodo];
            //  Agrega el elemento del nodo seguido de la flecha para agregar sus hijos
            resultado += nodo.getElem().toString() + " -> ";
            
            //  Variables para referencias mas facilmente a los hijos, ademas de no tener que llamar a la funcion multiples veces
            int izquierdo = nodo.getIzquierdo();
            int derecho = nodo.getDerecho(); 
            //  Si el izquierdo existe
            if(izquierdo != -1){
                //  Agrega su elemento al String
                resultado += arreglo[izquierdo].getElem().toString();
                //  Si ambos existen, agrega una coma para separarlos
                if(derecho != -1){
                    resultado += ", ";
                }
            }
            //  Si el derecho existe, lo agrega
            if(derecho != -1){
                resultado += arreglo[derecho].getElem().toString();
            }
            
            //  Luego si existe alguno de los hijos agrega sus Strings con sus hijos luego de un salto de linea
            if(izquierdo != -1)resultado += "\n" + toStringAux(izquierdo);
            if(derecho != -1)resultado += "\n" + toStringAux(derecho);
        }
        
        return resultado;
    }
}
