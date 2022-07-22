package jerarquicas.dinamicas;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;
import lineales.dinamicas.Pila;

public class ArbolGen {
    
    //  Variable que siempre apunta a la raiz
    NodoGen raiz;
    
    //  Crea un arbol generico vacio
    public ArbolGen(){
        raiz = null;
    }
    
    //  Dado un elemento elemNuevo y un elemento elemPadre, agrega elemNuevo como hijo de la primer
    //      aparición de elemPadre. Para que la operación termine con éxito debe existir un nodo en el árbol con
    //      elemento = elemPadre. No se establece ninguna preferencia respecto a la posición del hijo respecto a sus
    //      posibles hermanos. Esta operación devuelve verdadero cuando se pudo agregar elemNuevo a la estructura
    //      y falso en caso contrario.
    public boolean insertar(Object elemNuevo, Object elemPadre){
        
        boolean exito = true;
        
        //  Si esta vacía, inserta como nueva raiz
        if(this.esVacio()){
            raiz = new NodoGen(elemNuevo, null, null);
        } else {
            //  Si no esta vacía, obtiene el nodo padre
            NodoGen nodo = ObtenerNodo(raiz, elemPadre);
            //  Si no existe, entonces no hay exito
            if(nodo == null){
                exito = false;
            } else {
                //  Si no, se lo asigna como hijo
                nodo.setHijoIzquierdo(new NodoGen(elemNuevo, null, null));
            }
        }
        
        return exito;
    }
    
    //  Funcion privada utilizada por insertar() y pertenece(), se utiliza para encontrar un nodo en un subarbol dado por la raiz nodo
    private NodoGen ObtenerNodo(NodoGen nodo, Object elemento){
        
        //  Caso default, si no se encuentra retorna null
        NodoGen resultado = null;
        
        //  Si el nodo existe
        if(nodo != null){
            //  Si es el que buscamos
            if(nodo.getElem().equals(elemento)){
                //  Se retorna el nodo
                resultado = nodo;
            } else {
                //  Si no lo es, se busca recursivamente por todos sus hijos
                NodoGen auxiliar;
                auxiliar = nodo.getHijoIzquierdo();
                
                //  Mientras hayan mas hermanos, y el resultado sea nulo (No se haya encontrado el elemento)
                while(auxiliar != null && resultado == null){
                    //  Guarda el resultado y consigue el hermano derecho
                    resultado = ObtenerNodo(auxiliar, elemento);
                    auxiliar = auxiliar.getHermanoDerecho();
                }
            }
        }
        return resultado;
    }
    
    //  Devuelve verdadero si el elemento pasado por parámetro está en el árbol, y falso en caso contrario.
    public boolean pertenece(Object elemento){
        return ObtenerNodo(raiz, elemento) != null;
    }
    
    //  Si el elemento se encuentra en el árbol, devuelve una lista con el camino desde la raíz hasta dicho
    //      elemento (es decir, con los ancestros del elemento). Si el elemento no está en el árbol devuelve la lista
    //      vacía.
    public Lista ancestros(Object elemento){
        
        Lista resultado = new Lista();
        
        //  Llama a un proceso privado para que llene la lista.
        ancestrosAux(elemento, raiz, resultado);
        
        return resultado;
    }
    
    //  Funcion privada realizada para ancestros(). Llena la lista de resultado, devuelve boolean para control explicado adelante
    private boolean ancestrosAux(Object elemento, NodoGen nodo, Lista resultado){
        
        //  Variable de control, si el nodo no existe devuelve falso.
        boolean exito = false;
        
        //  Si el nodo existe
        if(nodo != null){
            //  Si es el nodo que se busca, devuelve verdadero (Para avisar que fue encontrado) y se inserta primero en la lista
            if(nodo.getElem().equals(elemento)){
                exito = true;
                resultado.insertar(elemento, 1);
            } else {
                //  Si no lo es, busca en sus hijos
                NodoGen auxiliar;
                auxiliar = nodo.getHijoIzquierdo();
                        
                //  Mientras el auxiliar no sea nulo
                while(auxiliar != null)
                    //  Si el elemento fue encontrado en el subarbol de auxiliar
                    if(ancestrosAux(elemento, auxiliar, resultado)){
                        //  Insertamos el elemento del padre en el primer lugar de la lista (Esto hará
                        //      que siempre los padres se inserten primeros, formando el recorrido desde la raiz hasta el elemento)
                        resultado.insertar(nodo.getElem(), 1);
                        //  Setea en true el exito para avisarle a sus padres que se encontro el elemento
                        exito = true;
                        //  Y hace a auxiliar igual a nulo para salir del while, ya que ya fue encontrado el elemento
                        auxiliar = null;
                    } else {
                        //  Si no fue encontrado, entonces se busca en el hermano derecho.
                        auxiliar = auxiliar.getHermanoDerecho();
                    }
            }
        }
        
        return exito;
    }
    
    // Devuelve falso si hay al menos un elemento cargado en el árbol y verdadero en caso contrario.
    public boolean esVacio(){
        return raiz == null;
    }
    
    //  Devuelve la altura del árbol, es decir la longitud del camino más largo desde la raíz hasta una hoja
    //      (Nota: un árbol vacío tiene altura -1 y una hoja tiene altura 0).
    public int altura(){
        return alturaSubArbol(raiz);
    }
    
    //  Funciona privada hecha exclusivamente para la funcion altura(), busca la altura de un nodo en un subarbol
    private int alturaSubArbol(NodoGen nodo){
        
        //  Caso base, si el nodo no existe (acabamos de pasar una hoja), retornamos -1
        int resultado = -1;
        
        if(nodo != null){
            //  Caso recursivo, si el nodo existe, busca la altura mas alta entre los subarboles de los hijos y le suma 1
            
            //  Variables para recorrer los hijos
            NodoGen auxiliar;
            auxiliar = nodo.getHijoIzquierdo();
            int enteroAuxiliar;
            
            //  Se recorre todo los hijos
            while(auxiliar != null){
                //  Guarda la altura del subarbol del hijo en entero auxiliar
                enteroAuxiliar = alturaSubArbol(auxiliar);
                //  Si es mas alta que resultado
                if(enteroAuxiliar > resultado){
                    //  Lo asigna como nuevo resultado
                    resultado = enteroAuxiliar;
                }
            }
            resultado++;
        }
        
        return resultado;
    }
    
    //  Devuelve el nivel de un elemento en el árbol. Si el elemento no existe en el árbol devuelve -1.
    public int nivel(Object elemento){
        //  Usa la funcion de ancestros para conseguir el camino hacia el elemento, luego entonces consigue la longitud del mismo.
        return ancestros(elemento).longitud() - 1;
    }
    
    //  Dado un elemento devuelve el valor almacenado en su nodo padre (busca la primera aparición de
    //      elemento).
    public Object padre(Object elemento){
        
        //  De nuevo utilizamos la funcion de ancestros para sacar el camino hacia el elemento
        Lista auxiliar = ancestros(elemento);
        //      Y luego simplemente recuperamos el anteultimo elemente (el padre del elemento buscado)
        return auxiliar.recuperar(ancestros(elemento).longitud() - 1);
    }   //Se podría hacer un poco mas eficiente, haciendo su propia funcion recursiva que solo busque el padre.

    //  Devuelve una lista con los elementos del árbol en el recorrido en preorden
    public Lista listarPreorden(){
        
        Lista resultado = new Lista();
        
        listarPreordenAux(raiz, resultado);
        
        return resultado;
    }
    
    private void listarPreordenAux(NodoGen nodo, Lista lista){
        
        if(nodo != null){
            lista.insertar(nodo.getElem(), lista.longitud() + 1);
            
            NodoGen auxiliar = nodo.getHijoIzquierdo();
            
            while(auxiliar != null){
                listarPreordenAux(auxiliar, lista);
                auxiliar = auxiliar.getHermanoDerecho();
            }
        }
    }

    //  Devuelve una lista con los elementos del árbol en el recorrido en inorden
    public Lista listarInorden(){
        
        Lista resultado = new Lista();
        
        listarInordenAux(raiz, resultado);
        
        return resultado;
    }
    
    private void listarInordenAux(NodoGen nodo, Lista lista){
        
        if(nodo != null){
            
            NodoGen auxiliar = nodo.getHijoIzquierdo();
            
            if(auxiliar != null){
                listarInordenAux(auxiliar, lista);
                auxiliar = auxiliar.getHermanoDerecho();
            }
            
            lista.insertar(nodo.getElem(), lista.longitud() + 1);
            
            while(auxiliar != null){
                listarInordenAux(auxiliar, lista);
                auxiliar = auxiliar.getHermanoDerecho();
            }
        }
    }

    //  Devuelve una lista con los elementos del árbol en el recorrido en posorden
    public Lista listarPosorden(){
        
        Lista resultado = new Lista();
        
        listarPosordenAux(raiz, resultado);
        
        return resultado;
    }

    private void listarPosordenAux(NodoGen nodo, Lista lista){
        
        if(nodo != null){
            
            NodoGen auxiliar = nodo.getHijoIzquierdo();
            while(auxiliar != null){
                listarPosordenAux(auxiliar, lista);
                auxiliar = auxiliar.getHermanoDerecho();
            }
            
            lista.insertar(nodo.getElem(), lista.longitud() + 1);
            
        }
    }
    
    //  Devuelve una lista con los elementos del árbol en el recorrido por niveles
    public Lista listarNiveles(){
        
        Lista resultado = new Lista();
        
        //  Si no esta vacía
        if(!this.esVacio()){
            
            //  Cola para ir almacenando los nodos
            Cola colaAuxiliar = new Cola();
            //  auxiliar para recorrer la estructura
            NodoGen nodoAux;

            //  Empiezo poniendo la raiz en la cola
            colaAuxiliar.poner(raiz);

            //  Mientras la cola no este vacía
            while(!colaAuxiliar.esVacia()){
                //  Consigo el frente de la cola y lo saco
                nodoAux = (NodoGen) colaAuxiliar.obtenerFrente();
                colaAuxiliar.sacar();
                //  Luego inserto ese elemento al final de la lista.
                resultado.insertar(nodoAux.getElem(), resultado.longitud() + 1);
                
                //  Y en caso de tener hijos los pongo en la cola
                nodoAux = nodoAux.getHijoIzquierdo();
                while(nodoAux != null){
                    colaAuxiliar.poner(nodoAux);
                    nodoAux.getHermanoDerecho();
                }
            }
        }
        
        return resultado;
    }

    //  Genera y devuelve un árbol genérico que es equivalente (igual estructura y contenido de los nodos) que
    //      el árbol original.
    @Override
    public ArbolGen clone(){
        
        ArbolGen resultado = new ArbolGen();
        
        //  Mientras no este vacio, ingreso la raiz al clon, y llamo a una funcion privada auxiliar
        if(!this.esVacio()){
            resultado.insertar(raiz.getElem(), null);
            cloneAuxiliar(raiz, resultado);
        }
        
        return resultado;
    }

    //  Funcion privada exclusivamente hecha para clone()
    //  Precondicion: Nodo existe
    private void cloneAuxiliar(NodoGen nodo, ArbolGen arbol){
        
        //  Consigo el hijo
        NodoGen auxiliar = nodo.getHijoIzquierdo();
        //  Creo una pila
        Pila pilaAuxiliar = new Pila();
        
        //  Apilo todos los hijos en una pila
        while(auxiliar != null){
            pilaAuxiliar.apilar(auxiliar);
            auxiliar = auxiliar.getHermanoDerecho();
        }
        
        //  Y luego los inserto en el nuevo arbol, de esta manera se ingresan en orden inverso, 
        //      resultando en el orden original.
        while(!pilaAuxiliar.esVacia()){
            auxiliar = (NodoGen) pilaAuxiliar.obtenerTope();
            pilaAuxiliar.desapilar();
            arbol.insertar(auxiliar.getElem(), nodo.getElem());
        }
    }
    
    //  Quita todos los elementos de la estructura. El manejo de memoria es similar al explicado anteriormente
    //      para estructuras lineales dinámicas.
    public void vaciar (){
        this.raiz = null;
    }

    //  Genera y devuelve una cadena de caracteres que indica cuál es la raíz del árbol y quienes son los hijos
    //      de cada nodo.
    @Override
    public String toString(){
        return toStringAux(raiz);
    }
    
    //  Funcion privada que se usa para toString()
    private String toStringAux(NodoGen nodo){
        
        //  Caso base cuando el nodo no existe
        String resultado = "";
        
        if(nodo != null){
            //  Si existe, agrega su elemento y una flecha indicando que a continuacion van sus hijos
            resultado += nodo.getElem().toString() + " --> ";
            
            NodoGen aux = nodo.getHijoIzquierdo();
            
            //  Agrega todos sus hijos
            while(aux != null){
                resultado += aux.getElem().toString();
                aux =  aux.getHermanoDerecho();
                //  Poniendo las comas, excepto en el ultimo
                if(aux != null)resultado += ", ";
            }
            
            aux = nodo.getHijoIzquierdo();
            
            //  Luego consigue llama a la funcion para que sume el String completo de todos sus hijos
            while(aux != null){
                resultado += "\n" + toStringAux(aux);
                aux = aux.getHermanoDerecho();
            }
        }
        
        return resultado;
    }

}
