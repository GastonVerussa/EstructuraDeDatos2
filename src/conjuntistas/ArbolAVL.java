package conjuntistas;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Pila;
import lineales.dinamicas.Cola;

public class ArbolAVL {
    
    private NodoAVL raiz;
    
    //  Crea un árbol sin elementos.
    public ArbolAVL(){
        raiz = null;
    }
    
    //  Recibe un elemento y lo agrega en el árbol de manera ordenada. Si el elemento ya se encuentra
    //      en el árbol no se realiza la inserción. Devuelve verdadero si el elemento se agrega a la estructura y
    //      falso en caso contrario.
    public boolean insertar(Comparable elemento){
        boolean exito;
        if(this.esVacio()){
            this.raiz = new NodoAVL(elemento);
            exito = true;
        } else {
            exito = insertarAux(elemento, this.raiz);
            if(exito){
                this.raiz = verificarBalance(this.raiz);
            }
        }
        return exito;
    }
    
    private boolean insertarAux(Comparable elemento, NodoAVL nodoActual){
        
        boolean exito;

        Comparable elementoActual = nodoActual.getElem();
        if(elementoActual.compareTo(elemento) == 0){
            exito = false;
        } else {
            if(elemento.compareTo(elementoActual) < 0){
                if(nodoActual.getIzquierdo() != null){
                    exito = insertarAux(elemento, nodoActual.getIzquierdo());
                    if(exito){
                        nodoActual.setIzquierdo(verificarBalance(nodoActual.getIzquierdo()));
                    }
                    nodoActual.recalcularAltura();
                } else {
                    nodoActual.setIzquierdo(new NodoAVL(elemento));
                    nodoActual.recalcularAltura();
                    exito = true;
                }
            } else {
                if(nodoActual.getDerecho()!= null){
                    exito = insertarAux(elemento, nodoActual.getDerecho());
                    if(exito){
                        nodoActual.setDerecho(verificarBalance(nodoActual.getDerecho()));
                    }
                    nodoActual.recalcularAltura();
                } else {
                    nodoActual.setDerecho(new NodoAVL(elemento));
                    nodoActual.recalcularAltura();
                    exito = true;
                }
            }
        }
        
        return exito;
    }
    
    //  Recibe el elemento que se desea eliminar y se procede a removerlo del árbol. Si no se encuentra
    //      el elemento no se puede realizar la eliminación. Devuelve verdadero si el elemento se elimina de la
    //      estructura y falso en caso contrario.
    public boolean eliminar(Comparable elemento){
        
        boolean exito;
        
        if(this.esVacio()){
            exito = false;
        } else {
            if(elemento.compareTo(raiz.getElem()) == 0){
                raiz = eliminarNodo(raiz);
                raiz = verificarBalance(raiz);
                exito = true;
            } else {
                exito = eliminarAux(elemento, raiz);
                if(exito){
                    raiz = verificarBalance(raiz);
                }
            }
        }
        
        return exito;
    }
    
    private boolean eliminarAux(Comparable elemento, NodoAVL nodoActual){
        
        boolean exito;
        
        if(elemento.compareTo(nodoActual.getElem()) < 0){
            if(nodoActual.getIzquierdo()== null){
                exito = false;
            } else {
                if(elemento.compareTo(nodoActual.getIzquierdo().getElem()) == 0){
                    nodoActual.setIzquierdo(eliminarNodo(nodoActual.getIzquierdo()));
                    nodoActual.setIzquierdo(verificarBalance(nodoActual.getIzquierdo()));
                    exito = true;
                } else {
                    exito = eliminarAux(elemento, nodoActual.getIzquierdo());
                    if(exito){
                        nodoActual.setIzquierdo(verificarBalance(nodoActual.getIzquierdo()));
                    }
                }
            }
        } else {
            if(nodoActual.getDerecho() == null){
                exito = false;
            } else {
                if(elemento.compareTo(nodoActual.getDerecho().getElem()) == 0){
                    nodoActual.setDerecho(eliminarNodo(nodoActual.getDerecho()));
                    nodoActual.setDerecho(verificarBalance(nodoActual.getDerecho()));
                    exito = true;
                } else {
                    exito = eliminarAux(elemento, nodoActual.getDerecho());
                    if(exito){
                        nodoActual.setDerecho(verificarBalance(nodoActual.getDerecho()));
                    }
                }
            }
        }
        
        return exito;
        
    }
    
    private NodoAVL eliminarNodo(NodoAVL nodoObjetivo){
        
        NodoAVL nuevoHijo;
        
        if(nodoObjetivo.getIzquierdo() != null){
            if(nodoObjetivo.getDerecho() != null){
                //  Caso 3
                nuevoHijo = nodoObjetivo;
                if(nodoObjetivo.getIzquierdo().getDerecho() == null){
                    nodoObjetivo.setElem(nodoObjetivo.getIzquierdo().getElem());
                    nodoObjetivo.setIzquierdo(nodoObjetivo.getIzquierdo().getIzquierdo());
                } else {
                    nodoObjetivo.setElem(buscarSucesor(nodoObjetivo.getIzquierdo()));
                    nodoObjetivo.setIzquierdo(verificarBalance(nodoObjetivo.getIzquierdo()));
                }
                nodoObjetivo.recalcularAltura();
            } else {
                //  Caso 2
                nuevoHijo = nodoObjetivo.getIzquierdo();
            }
        } else {
            if(nodoObjetivo.getDerecho() != null){
                //  Caso 2
                nuevoHijo = nodoObjetivo.getDerecho();
            } else {
                //  Caso 1
                nuevoHijo = null;
            }
        }
        
        return nuevoHijo;
    }
    
    //  Busca el nuevo sucesor para el caso 3. Busca el mayor de los menores.
    private Comparable buscarSucesor(NodoAVL nodoPadre){
        
        Comparable sucesor;
        NodoAVL nodoHijo = nodoPadre.getDerecho();
        
        if(nodoHijo.getDerecho() != null){
            sucesor = buscarSucesor(nodoHijo);
            nodoPadre.setDerecho(verificarBalance(nodoHijo));
        } else {
            sucesor = nodoHijo.getElem();
            nodoPadre.setDerecho(nodoHijo.getIzquierdo());
            nodoPadre.recalcularAltura();
        }
        
        return sucesor;
    }
    
    //  Funcion privada que verifica el balance de un nodo y hace los cambios necesarios. Ademas devuelve
    //      la nueva raiz del sub-arbol
    private NodoAVL verificarBalance(NodoAVL nodo){
        
        NodoAVL nuevaRaiz = nodo;
        
        if(nodo == null){
            nuevaRaiz = null;
        } else {
            int balance = getBalance(nodo);

            if(balance > 1){
                //  Nodo desbalanceado a izquierda
                if(getBalance(nodo.getIzquierdo()) == -1){
                    //  Hijo desbalanceado en sentido contrario, se necesita rotacion a izquierda con hijo
                    //      izquierdo de pivot
                    nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
                }
                //  Compartan o no sentido de desbalance nodo e hijo, se debe rotar a derecha con nodo de pivot
                nuevaRaiz = rotarDerecha(nodo);
            } else {
                if(balance < -1){
                    //  Nodo desbalanceado a derecha
                    if(getBalance(nodo.getDerecho()) == 1){
                        //  Hijo desbalanceado en sentido contrario, se necesita rotacion a derecha con
                        //      hijo derecho como pivot
                        nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
                    }
                    //  Compartan o no sentido de desbalance nodo e hijo, se debe rotar a izquierda con nodo de pivot
                    nuevaRaiz = rotarIzquierda(nodo);
                }
            }   
        }
        //  Si no esta desbalanceado, no se hace nada.
        return nuevaRaiz;
    }
    
    private int getBalance(NodoAVL nodo){
        
        int alturaIzq, alturaDer;
        
        if(nodo.getIzquierdo() == null){
            alturaIzq = -1;
        } else {
            alturaIzq = nodo.getIzquierdo().getAltura();
        }
        
        if(nodo.getDerecho() == null){
            alturaDer = -1;
        } else {
            alturaDer = nodo.getDerecho().getAltura();
        }
        
        return alturaIzq - alturaDer;
    }
    
    private NodoAVL rotarDerecha(NodoAVL pivot){
        
        NodoAVL hijo = pivot.getIzquierdo();
        NodoAVL aux = hijo.getDerecho();
        hijo.setDerecho(pivot);
        pivot.setIzquierdo(aux);
        pivot.recalcularAltura();
        hijo.recalcularAltura();
        return hijo;
    }
    
    private NodoAVL rotarIzquierda(NodoAVL pivot){
        
        NodoAVL hijo = pivot.getDerecho();
        NodoAVL aux = hijo.getIzquierdo();
        hijo.setIzquierdo(pivot);
        pivot.setDerecho(aux);
        pivot.recalcularAltura();
        hijo.recalcularAltura();
        return hijo;
    }

    //  Devuelve verdadero si el elemento recibido por parámetro está en el árbol y falso en caso contrario.
    public boolean pertenece(Comparable elemento){
        return perteneceAux(elemento, raiz);
    }
    
    //  Funcion privada para pertenece()
    private boolean perteneceAux(Comparable elemento, NodoAVL nodo){
        
        boolean esta;
        
        //  Si el nodo no existe
        if(nodo == null){
            //  Significa que el elemento no esta en el arbol.
            esta = false;
        } else {
            //  Si el nodo contiene el elemento buscado
            if(nodo.getElem().compareTo(elemento) == 0){
                //  Devolvemos que pertenece
                esta = true;
            } else {
                //  En caso que no lo contenga, revisamos el nodo izquierdo o derecho,
                //      dependiendo de si es mayor o menor.
                if(nodo.getElem().compareTo(elemento) < 0){
                    esta = perteneceAux(elemento, nodo.getDerecho());
                } else {
                    esta = perteneceAux(elemento, nodo.getIzquierdo());
                }
            }
        }
        
        return esta;
    }

    //  Devuelve falso si hay al menos un elemento en el árbol y verdadero en caso contrario.
    public boolean esVacio(){
        return raiz == null;
    }
        
    //  Recorre el árbol completo y devuelve una lista ordenada con los elementos que se encuentran
    //      almacenados en él.
    public Lista listar(){
        Lista resultado = new Lista();
        listarAux(raiz, resultado);
        return resultado;
    }

    //  Funciona privada para llenar la lista, la llena en Inorden, ya que de esta forma quedan de menor a mayor.
    private void listarAux(NodoAVL nodo, Lista list){
        
        //  Si el nodo existe
        if(nodo != null){
            //  Llena la lista en Inorden
            listarAux(nodo.getIzquierdo(), list);
            list.insertar(nodo.getElem(), list.longitud() + 1);
            listarAux(nodo.getDerecho(), list);
        } 
    }
    
    //  Recorre parte del árbol (sólo lo necesario) y devuelve una lista ordenada con los elementos que
    //      se encuentran en el intervalo [elemMinimo, elemMaximo].
    public Lista listarRango(Comparable elemMinimo, Comparable elemMaximo){
        Lista resultado = new Lista();
        listarRangoAux(elemMinimo, elemMaximo, raiz, resultado);
        return resultado;
    }
    
    //  Funcion privada para listarRango(), inserta los elementos que se encuentran en el intervalo en inorden (De menor a mayor)
    private void listarRangoAux(Comparable elemMinimo, Comparable elemMaximo, NodoAVL nodo, Lista list){
        
        if(nodo != null){
            //  Variable para referenciar al elemento del nodo facilmente
            Comparable aux = nodo.getElem();
            //  Si el elemento del nodo es menor o igual al limite inferior del intervalo, no 
            //      revisamos el hijo izquierdo, ya que todos sus elementos serán menores.
            if(aux.compareTo(elemMinimo) > 0){
                //  De no ser menor, revisamos el subarbol del hijo izquierdo
                listarRangoAux(elemMinimo, elemMaximo, nodo.getIzquierdo(), list);
            }
            //  Si el elemento del nodo se encuentra dentro del intervalo
            if(aux.compareTo(elemMinimo) >= 0 && aux.compareTo(elemMaximo) <= 0){
                //  Lo insertamos al final de la lista
                list.insertar(aux, list.longitud() + 1);
            }
            //  Si el elemento del nodo es mayor o igual al limite superior del intervalo, no 
            //      revisamos el hijo derecho, ya que todos sus elementos serán mayores.
            if(aux.compareTo(elemMaximo) < 0){
                //  De no ser mayor, revisamos el subarbol del hijo derecho
                listarRangoAux(elemMinimo, elemMaximo, nodo.getDerecho(), list);
            }
        }
    }

    //  Recorre la rama correspondiente y devuelve el elemento más pequeño almacenado en el árbol.
    public Comparable minimoElem(){
        
        Comparable resultado = null;
        
        if(!this.esVacio()){
            NodoAVL aux = raiz;
            while(aux.getIzquierdo() != null){
                aux = aux.getIzquierdo();
            }
            resultado = aux.getElem();
        }
        
        return resultado;
    }

    //  Recorre la rama correspondiente y devuelve el elemento más grande almacenado en el árbol
    public Comparable maximoElem(){
        
        Comparable resultado = null;
        
        if(!this.esVacio()){
            NodoAVL aux = raiz;
            while(aux.getDerecho() != null){
                aux = aux.getDerecho();
            }
            resultado = aux.getElem();
        }
        
        return resultado;
    }
    
    //      EXTRAS
    
    public void vaciar(){
        raiz = null;
    }
    
    //  Devuelve una copia del arbol, para clonarlo recorre la estructura en orden 
    //      por niveles, de esta forma, al ir insertando nodos en el clon, no será
    //      necesario hacer un rebalance, siendo la manera mas efectiva.
    @Override
    public ArbolAVL clone(){
        
        ArbolAVL resultado = new ArbolAVL();
        
        //  Si no esta vacía
        if(!this.esVacio()){
            
            //  Cola para ir almacenando los nodos
            Cola colaAuxiliar = new Cola();
            //  auxiliar para recorrer la estructura
            NodoAVL nodoAux;

            //  Empiezo poniendo la raiz en la cola
            colaAuxiliar.poner(raiz);

            //  Mientras la cola no este vacía
            while(!colaAuxiliar.esVacia()){
                //  Consigo el frente de la cola y lo saco
                nodoAux = (NodoAVL) colaAuxiliar.obtenerFrente();
                colaAuxiliar.sacar();
                //  Luego inserto ese elemento al final de la lista.
                resultado.insertar(nodoAux.getElem());
                //  Y en caso de tener hijos los pongo en la cola
                if(nodoAux.getIzquierdo() != null) colaAuxiliar.poner(nodoAux.getIzquierdo());
                if(nodoAux.getDerecho() != null) colaAuxiliar.poner(nodoAux.getDerecho());
            }
        }
        
        return resultado;
    }
    
    //  Devuelve un String con los elementos del arbol, en orden por niveles para mas facil lectura.
    @Override
    public String toString(){  
        
        String resultado = "Arbol Vacio";
        
        if(!this.esVacio()){
            resultado = "";
            //  Cola para ir almacenando los nodos
            Cola colaAuxiliar = new Cola();
            //  auxiliar para recorrer la estructura
            NodoAVL nodoAux;

            //  Empiezo poniendo la raiz en la cola
            colaAuxiliar.poner(raiz);

            //  Mientras la cola no este vacía
            while(!colaAuxiliar.esVacia()){
                //  Consigo el frente de la cola y lo saco
                nodoAux = (NodoAVL) colaAuxiliar.obtenerFrente();
                colaAuxiliar.sacar();
                
                //  Variables para referencias mas facilmente a los hijos, ademas de no tener que llamar a la funcion multiples veces
                NodoAVL izquierdo = nodoAux.getIzquierdo();
                NodoAVL derecho = nodoAux.getDerecho();
                
                //  Luego agrego el elemento al String
                resultado += "\n" + nodoAux.getElem().toString() + " --> ";
            
                //  Si el izquierdo existe
                if(izquierdo != null){
                    //  Agrega su elemento al String
                    resultado += izquierdo.getElem().toString();
                }
                
                //  Coma para separar elementos o saber cual es hijo izquierdo y derecho
                resultado += " , ";

                //  Si el derecho existe, lo agrega
                if(derecho != null){
                    resultado += derecho.getElem().toString();
                }
                
                //  Y en caso de tener hijos los pongo en la cola
                if(izquierdo != null) colaAuxiliar.poner(nodoAux.getIzquierdo());
                if(derecho != null) colaAuxiliar.poner(nodoAux.getDerecho());
            }
        }
        
        return resultado;
    }
}
