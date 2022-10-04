package propositoEspecifico;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Pila;

public class MapeoAMuchosAVL {
    
    private NodoAVLMapeoM raiz;
    
    //  Constructor vacio
    public MapeoAMuchosAVL(){
        raiz = null;
    }
    
    /*
    
    public boolean asociar (Comparable valorDominio, Object valorRango){
        
        boolean exito = true;
        
        if(this.esVacio()){
            raiz = new NodoAVLMapeoM(valorDominio, valorRango);        
        } else {
            if(valorDominio.compareTo(raiz.getDominio()) > 0){
                
            }
        }
    }*/
    
    //  Recibe un valor que representa a un elemento del dominio y un segundo valor 
    //      que representa a un elemento del rango. Si no existe otro par que contenga 
    //      a valorDominio, se agrega en el mapeo el par (valorDominio, {valorRango}), 
    //      donde el segundo término es un conjunto de rangos con un solo valor. 
    //      Si ya existe un par con valorDominio, se agrega valorRango al conjunto de rangos existente.
    //      Si la operación termina con éxito devuelve verdadero y falso en caso contrario.
    public boolean asociar(Comparable valorDominio, Object valorRango){
        
        boolean exito = true;
        //  Variable para saber si se necesita revisar el arbol por balanceo (cuando se agrega
        //      un elemento)
        boolean balancear = false;
        
        //  Si el arbol esta vacio
        if(this.esVacio()){
            //  Pone el nuevo elemento en la nueva raiz
            raiz = new NodoAVLMapeoM(valorDominio, valorRango);
        } else {
            //  Variable para salir del while
            boolean salir = false;
            //  Variable para recorrer la estructura
            NodoAVLMapeoM aux = raiz;
            //  Pila donde se guardaran los nodos para verificar el balance y altura a la vuelta
            Pila pilaAuxiliar = new Pila();
            //  En caso de que no este vacio, se ingresa a un while para buscar la posicion adecuada
            while(!salir){
                //  Si el valorDominio del nodo actual es igual al que se desea ingresar
                if(aux.getDominio().compareTo(valorDominio) == 0){
                    salir = true;
                    //  Agrega el rango, en caso de que ya exista la dupla dominio rango
                    if(!aux.agregarRango(valorRango)){
                        //  Da error, no acepta duplas de dominio y rango duplicados
                        exito = false;
                    }
                } else {
                    //  Si es menor el buscado al del nodo actual
                    if(valorDominio.compareTo(aux.getDominio()) < 0){
                        if(aux.getIzquierdo() != null){
                            //  Si existe, se apila el nodo y se busca por su hijo izquierdo
                            pilaAuxiliar.apilar(aux);
                            aux = aux.getIzquierdo();
                        } else {
                            //  Si no existe, se crea un nuevo nodo con el elemento como hijo derecho
                            aux.setIzquierdo(new NodoAVLMapeoM(valorDominio, valorRango));
                            //  Recalcula la altura con el nuevo hijo
                            aux.recalcularAltura();
                            salir = true;
                            balancear = true;
                        }
                    } else {
                        if(aux.getDerecho() != null){
                            //  Si existe, se apila el nodo y se busca por su hijo derecho
                            pilaAuxiliar.apilar(aux);
                            aux = aux.getDerecho();
                        } else {
                            //  Si no existe, se crea un nuevo nodo con el elemento como hijo derecho
                            aux.setDerecho(new NodoAVLMapeoM(valorDominio, valorRango));
                            //  Recalcula la altura con el nuevo hijo
                            aux.recalcularAltura();
                            salir = true;
                            balancear = true;
                        }
                    }
                }
            }
            if(balancear){
                NodoAVLMapeoM padreAux;
                NodoAVLMapeoM nuevaRaizSubarbol;
                while(!pilaAuxiliar.esVacia()){
                    padreAux = (NodoAVLMapeoM) pilaAuxiliar.obtenerTope();
                    pilaAuxiliar.desapilar();
                    nuevaRaizSubarbol = verificarBalance(aux);
                    if(nuevaRaizSubarbol != null){
                        if(nuevaRaizSubarbol.getDominio().compareTo(padreAux.getDominio()) > 0){
                            padreAux.setDerecho(nuevaRaizSubarbol);
                        } else {
                            padreAux.setIzquierdo(nuevaRaizSubarbol);
                        }
                    }
                    padreAux.recalcularAltura();
                    aux = padreAux;
                }
                
                nuevaRaizSubarbol = verificarBalance(raiz);
                if(nuevaRaizSubarbol != null){
                    raiz = nuevaRaizSubarbol;
                }
            }
        }
        
        
        
        return exito;
    }
    
    //  Elimina valorRango del conjunto de rangos asociado a valorDominio. Caso 
    //      especial: si al eliminar valorRango del conjunto este quedara vacío,
    //      debe eliminar el par (valorDominio,{}) del mapeo. Si encuentra el par 
    //      y la operación de eliminación termina con éxito devuelve verdadero y 
    //      falso en caso contrario.
    public boolean desasociar(Comparable valorDominio, Object valorRango){
        
        boolean exito = true;
        
        //  Si esta vacio, no hay nada que eliminar, se devuelve que no hubo exito
        if(this.esVacio()){
            exito = false;
        } else {
            //  Si la raiz no tiene el elemento que buscamos
            if(raiz.getDominio().compareTo(valorDominio) != 0){
                //  Llamamos a la funcion privada auxiliar elminarAux
                exito = eliminarAux(valorDominio, valorRango, raiz);
                if(exito == true){
                    NodoAVLMapeoM nuevaRaiz = verificarBalance(raiz);
                    if(nuevaRaiz != null){
                        raiz = nuevaRaiz;
                    }
                }
            } else {
                //  Si la raiz tiene el elemento le elimina el rango
                exito = raiz.eliminarRango(valorRango);
                //  Si al eliminarlo, el dominio queda sin ningun elemento en su rango
                //      debe eliminar el nodo
                if(raiz.rangoVacio()){
                    //  Revisa que caso de eliminacion es
                    if(raiz.getIzquierdo() != null){
                        if(raiz.getDerecho() != null){
                            //  Caso 3, tiene ambos hijos
                            //  Para este caso existe una funcion privada llamada elminarCaso3
                            eliminarCaso3(raiz);
                            NodoAVLMapeoM nuevaRaiz = verificarBalance(raiz);
                            if(nuevaRaiz != null)raiz = nuevaRaiz;
                        } else {
                            //  Caso 2, tiene un hijo izquierdo nomas
                            raiz = raiz.getIzquierdo();
                        }
                    } else {
                        if(raiz.getDerecho() != null){
                            //  Caso 2, tiene un hijo derecho nomas
                            raiz = raiz.getDerecho();
                        } else {
                            //  Caso 1, no tiene hijos, arbol de un solo elemento, lo vacía
                            raiz = null;
                        }
                    }
                }
            } 
        }
        
        return exito;
    }
    
    //  Si en el mapeo se encuentra almacenado algún par cuyo dominio es valorDominio, 
    //      devuelve el conjunto de valores de rango asociado a él. Precondición: 
    //      valorDominio está en el mapeo (si no existe, no se puede asegurar el 
    //      funcionamiento de la operación).
    public Lista obtenerValores(Comparable valorDominio){
        return obtenerValoresAux(valorDominio, raiz);
    }

    //  Funcion privada para obtenerValores()
    private Lista obtenerValoresAux(Comparable valorDominio, NodoAVLMapeoM nodo){
        
        Lista rango;
        
        //  Si el nodo no existe
        if(nodo == null){
            //  Significa que el elemento no esta en el arbol.
            rango = null;
        } else {
            //  Si el nodo contiene el elemento buscado
            if(nodo.getDominio().compareTo(valorDominio) == 0){
                //  Devolvemos su rango
                rango = nodo.getRango();
            } else {
                //  En caso que no lo contenga, revisamos el nodo izquierdo o derecho,
                //      dependiendo de si es mayor o menor.
                if(nodo.getDominio().compareTo(valorDominio) < 0){
                    rango = obtenerValoresAux(valorDominio, nodo.getDerecho());
                } else {
                    rango = obtenerValoresAux(valorDominio, nodo.getIzquierdo());
                }
            }
        }
        
        return rango;
    }
    
    //  Devuelve un conjunto con todos los valores de tipo dominio almacenados en el mapeo.
    public Lista obtenerConjuntoDominio(){
        Lista resultado = new Lista();
        obtenerDominiosAux(raiz, resultado);
        return resultado;
    }

    //  Funciona privada para llenar la lista de los elementos del dominio, la 
    //      llena en Inorden, ya que de esta forma quedan de menor a mayor.
    private void obtenerDominiosAux(NodoAVLMapeoM nodo, Lista list){
        
        //  Si el nodo existe
        if(nodo != null){
            //  Llena la lista en Inorden
            obtenerDominiosAux(nodo.getIzquierdo(), list);
            list.insertar(nodo.getDominio(), list.longitud() + 1);
            obtenerDominiosAux(nodo.getDerecho(), list);
        } 
    }
    
    //  Devuelve un conjunto con la unión de todos los valores de tipo rango almacenados en el mapeo.
    public Lista obtenerConjuntoRango(){
        Lista resultado = new Lista();
        obtenerRangosAux(raiz, resultado);
        return resultado;
    }

    //  Funciona privada para llenar la lista de los elementos del rango, la 
    //      llena en Inorden, ya que de esta forma quedan de menor a mayor.
    private void obtenerRangosAux(NodoAVLMapeoM nodo, Lista list){
        
        //  Si el nodo existe
        if(nodo != null){
            //  Llena la lista en Inorden
            obtenerRangosAux(nodo.getIzquierdo(), list);
            //  Debe ingresar todos los elementos del rango
            Lista rango = nodo.getRango().clone();
            while(!rango.esVacia()){
                Object valorRango = rango.recuperar(1);
                //  Revisa que no este para que no haya duplicados
                if(list.localizar(valorRango) < 1){
                    //  Si no esta, lo agrega.
                    list.insertar(valorRango, list.longitud() + 1);
                    //  Si ya esta, lo ignora.
                }
                rango.eliminar(1);
            }
            obtenerRangosAux(nodo.getDerecho(), list);
        } 
    }
    
    //  Devuelve falso si hay al menos un par cargado en el mapeo y verdadero en caso contrario.
    public boolean esVacio(){
        return raiz == null;
    }

    public void vaciar(){
        raiz = null;
    }
    
    @Override
    public MapeoAMuchosAVL clone(){
        
        MapeoAMuchosAVL resultado = new MapeoAMuchosAVL();
        
        //  Si no esta vacía
        if(!this.esVacio()){
            
            //  Cola para ir almacenando los nodos
            Cola colaAuxiliar = new Cola();
            //  auxiliar para recorrer la estructura
            NodoAVLMapeoM nodoAux;

            //  Empiezo poniendo la raiz en la cola
            colaAuxiliar.poner(raiz);

            //  Mientras la cola no este vacía
            while(!colaAuxiliar.esVacia()){
                //  Consigo el frente de la cola y lo saco
                nodoAux = (NodoAVLMapeoM) colaAuxiliar.obtenerFrente();
                colaAuxiliar.sacar();
                //  Luego inserto ese elemento al final de la lista.
                Lista rango = nodoAux.getRango().clone();
                Comparable valorDominio = nodoAux.getDominio();
                Pila pilaAux = new Pila();
                while(!rango.esVacia()){
                    pilaAux.apilar(rango.recuperar(1));
                    rango.eliminar(1);
                }
                while(!pilaAux.esVacia()){
                    resultado.asociar(valorDominio, pilaAux.obtenerTope());
                    pilaAux.desapilar();
                }
                //  Y en caso de tener hijos los pongo en la cola
                if(nodoAux.getIzquierdo() != null) colaAuxiliar.poner(nodoAux.getIzquierdo());
                if(nodoAux.getDerecho() != null) colaAuxiliar.poner(nodoAux.getDerecho());
            }
        }
        
        return resultado;
    }
    
    @Override
    public String toString(){
        
        String resultado = "Mapeo vacio";
        
        if(!this.esVacio()){
            resultado = toStringAux(raiz);
        }
        
        return resultado;
    }
        
    //  Funciona privada para llenar la lista de los elementos del dominio, la 
    //      llena en Inorden, ya que de esta forma quedan de menor a mayor.
    private String toStringAux(NodoAVLMapeoM nodo){
        
        String resultado = "";
        
        //  Si el nodo existe
        if(nodo != null){
            //  Llena la lista en Inorden
            resultado = toStringAux(nodo.getIzquierdo());
            
            //  Luego agrego el elemento al String
            resultado += "\n" + nodo.getDominio().toString() + ": { ";

            Lista rango = nodo.getRango().clone();

            while(!rango.esVacia()){
                Object valorRango = rango.recuperar(1);
                resultado += valorRango.toString() + ", ";
                rango.eliminar(1);
            }
            resultado = resultado.substring(0, resultado.length() - 2);
            resultado += " }";
            
            resultado += toStringAux(nodo.getDerecho());
        } 
        
        return resultado;
    }
    
    private NodoAVLMapeoM verificarBalance(NodoAVLMapeoM nodo){
        
        NodoAVLMapeoM nuevaRaiz = null;
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
        //  Si no esta desbalanceado, no se hace nada.
        return nuevaRaiz;
    }
    
    
    private int getBalance(NodoAVLMapeoM nodo){
        
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
    
    private NodoAVLMapeoM rotarDerecha(NodoAVLMapeoM pivot){
        
        NodoAVLMapeoM hijo = pivot.getIzquierdo();
        NodoAVLMapeoM aux = hijo.getDerecho();
        hijo.setDerecho(pivot);
        pivot.setIzquierdo(aux);
        pivot.recalcularAltura();
        hijo.recalcularAltura();
        return hijo;
    }
    
    private NodoAVLMapeoM rotarIzquierda(NodoAVLMapeoM pivot){
        
        NodoAVLMapeoM hijo = pivot.getDerecho();
        NodoAVLMapeoM aux = hijo.getIzquierdo();
        hijo.setIzquierdo(pivot);
        pivot.setDerecho(aux);
        pivot.recalcularAltura();
        hijo.recalcularAltura();
        return hijo;
    }
    
     //  Funcion privada auxiliar para el metodo eliminar()
    //  Precondicion: Que el nodo pasado como paramentro no tenga el elemento buscado, ni sea nulo
    private boolean eliminarAux(Comparable valorDominio, Object valorRango, NodoAVLMapeoM nodo){
        
        boolean exito = true;
        
        //  Si el valor del elemento que buscamos es menor al elemento del nodo por parametro
        if(valorDominio.compareTo(nodo.getDominio()) < 0){
            //  Significa que debemos buscar por la izquierda
            NodoAVLMapeoM aux = nodo.getIzquierdo();
            //  Si no hay nada a la izquierda, no existe el elemento en el arbol, se devuelve que no hubo exito
            if(aux == null){
                exito = false;
            } else {
                //  Si hay algo a la izquierda, verifica si es el elemento buscado
                if(aux.getDominio().compareTo(valorDominio) != 0){
                    //  De no serlo, busca por izquierda con el mismo metodo recursivo
                    exito = eliminarAux(valorDominio, valorRango, aux);
                    aux.recalcularAltura();
                    if(exito == true){
                        NodoAVLMapeoM nuevaRaizSubarbol = verificarBalance(aux);
                        if(nuevaRaizSubarbol != null){
                            nodo.setIzquierdo(nuevaRaizSubarbol);
                        }
                        nodo.recalcularAltura();
                    }
                } else {
                    //  Si lo es, verifica en que caso de eliminacion se encuentra y actua acorde
                    if(!aux.eliminarRango(valorRango)){
                        //  Si existe el dominio pero no tiene el valor en su rango
                        exito = false;
                    } else {
                        //  Si logra borrar el rango revisa si le queda algun rango
                        if(aux.rangoVacio()){
                            //  Si esta vacio, lo borra
                            if(aux.getIzquierdo() != null){
                                if(aux.getDerecho() != null){
                                    //  Caso 3, tiene ambos hijos, consigue candidato izquierdo, el mayor de los menores
                                    eliminarCaso3(aux);
                                    NodoAVLMapeoM nuevaRaizSubarbol = verificarBalance(aux);
                                    if(nuevaRaizSubarbol != null){
                                        nodo.setIzquierdo(nuevaRaizSubarbol);
                                    }
                                    nodo.recalcularAltura();
                                } else {
                                    //  Caso 2, hijo izquierdo
                                    nodo.setIzquierdo(aux.getIzquierdo());
                                    aux.getIzquierdo().recalcularAltura();
                                }
                            } else {
                                if(aux.getDerecho() != null){
                                    //  Caso 2, hijo derecho
                                    nodo.setIzquierdo(aux.getDerecho());
                                    aux.getDerecho().recalcularAltura();
                                } else {
                                    //  Caso 1, sin hijos
                                    nodo.setIzquierdo(null);
                                }
                            }
                        }
                    }
                }
            } 
        } else {
            //  Si no es menor, sabemos que no es mayor (Ya que la precondicion es que no sea igual), usa la misma logica 
            //      que para revisar por izquierda
            NodoAVLMapeoM aux = nodo.getDerecho();
            if(aux == null){
                exito = false;
            } else {
                if(aux.getDominio().compareTo(valorDominio) != 0){
                    exito = eliminarAux(valorDominio, valorRango, aux); 
                    if(exito == true){
                        NodoAVLMapeoM nuevaRaizSubarbol = verificarBalance(aux);
                        if(nuevaRaizSubarbol != null){
                            nodo.setDerecho(nuevaRaizSubarbol);
                        }
                        nodo.recalcularAltura();
                    }
                } else {
                    if(!aux.eliminarRango(valorRango)){
                        exito = false;
                    } else {
                        if(aux.rangoVacio()){
                            if(aux.getIzquierdo() != null){
                                if(aux.getDerecho() != null){
                                    //  Caso 3, tiene ambos hijos, consigue candidato izquierdo, el mayor de los menores
                                    eliminarCaso3(aux);
                                    NodoAVLMapeoM nuevaRaizSubarbol = verificarBalance(aux);
                                    if(nuevaRaizSubarbol != null){
                                        nodo.setDerecho(nuevaRaizSubarbol);
                                    }
                                    nodo.recalcularAltura();
                                } else {
                                    //  Caso 2, hijo izquierdo
                                    nodo.setDerecho(aux.getIzquierdo());
                                }
                            } else {
                                if(aux.getDerecho() != null){
                                    //  Caso 2, hijo derecho
                                    nodo.setDerecho(aux.getDerecho());
                                } else {
                                    //  Caso 1, sin hijos
                                    nodo.setDerecho(null);
                                }
                            }
                        }
                    }
                }
            } 
        }
        
        return exito;
    }
    
    //  Funcion privada para realizar el algoritmo de eliminacion de caso 3, el caso en el que
    //      el nodo a ser eliminado tiene hijo izquierdo y derecho.
    private void eliminarCaso3(NodoAVLMapeoM nodo){
        //  Caso 3, tiene ambos hijos, consigue candidato izquierdo, el mayor de los menores
        NodoAVLMapeoM aux = nodo.getIzquierdo();
        //  Si es el primero a la izquierda
        if(aux.getDerecho() == null){
            //  Copia su elemento
            nodo.setDominio(aux.getDominio());
            nodo.setRango(aux.getRango());
            //  En caso de tener hijo izquierdo
            if(aux.getIzquierdo() != null){
                //  Caso 2, hijo izquierdo
                nodo.setIzquierdo(aux.getIzquierdo());
            } else {
                //  Caso 1, sin hijos
                nodo.setIzquierdo(null);
            }
            nodo.recalcularAltura();
        } else {
            Pila pilaAuxiliar = new Pila();
            //  Si no es el primero a la izquierda, busca el padre del mayor de los menores
            while(aux.getDerecho().getDerecho() != null){
                pilaAuxiliar.apilar(aux);
                aux = aux.getDerecho();
            }
            //  Copia el elemento del mayor
            nodo.setDominio(aux.getDerecho().getDominio());
            nodo.setRango(aux.getDerecho().getRango());
            //  En caso de tener hijo izquierdo
            if(aux.getDerecho().getIzquierdo() != null){
                //  Caso 2, hijo izquierdo
                aux.setDerecho(aux.getDerecho().getIzquierdo());
            } else {
                //  Caso 1, sin hijos
                aux.setDerecho(null);
            }
            aux.recalcularAltura();
            NodoAVLMapeoM padreAuxiliar;
            NodoAVLMapeoM nuevaRaizSubarbol;
            while(!pilaAuxiliar.esVacia()){
                padreAuxiliar = (NodoAVLMapeoM) pilaAuxiliar.obtenerTope();
                nuevaRaizSubarbol = verificarBalance(aux);
                if(nuevaRaizSubarbol != null){
                    padreAuxiliar.setDerecho(nuevaRaizSubarbol);
                }
                padreAuxiliar.recalcularAltura();
                aux = padreAuxiliar;
                pilaAuxiliar.desapilar();
            }
            nuevaRaizSubarbol = verificarBalance(aux);
            if(nuevaRaizSubarbol != null){
                nodo.setDerecho(nuevaRaizSubarbol);
            }
            nodo.recalcularAltura();
        }
    }
}
