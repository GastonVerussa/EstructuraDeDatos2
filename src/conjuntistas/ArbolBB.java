package conjuntistas;
import lineales.dinamicas.Lista;

public class ArbolBB {
    
    private NodoABB raiz;
    
    //  Crea un árbol sin elementos.
    public ArbolBB(){
        raiz = null;
    }
    
    //  Recibe un elemento y lo agrega en el árbol de manera ordenada. Si el elemento ya se encuentra
    //      en el árbol no se realiza la inserción. Devuelve verdadero si el elemento se agrega a la estructura y
    //      falso en caso contrario.
    public boolean insertar(Comparable elemento){
        
        boolean exito = true;
        
        boolean salir = false;
        NodoABB aux = raiz;
        
        if(raiz == null){
            raiz = new NodoABB(elemento);
            salir = true;
        }
        
        while(!salir){
            if(aux.getElem().compareTo(elemento) == 0){
                exito = false;
                salir = true;
            } else {
                if(elemento.compareTo(aux.getElem()) < 0){
                    if(aux.getIzquierdo() != null){
                        aux = aux.getIzquierdo();
                    } else {
                        aux.setIzquierdo(new NodoABB(elemento));
                        salir = true;
                    }
                } else {
                    aux = aux.getDerecho();
                    if(aux.getDerecho() != null){
                        aux = aux.getDerecho();
                    } else {
                        aux.setDerecho(new NodoABB(elemento));
                        salir = true;
                    }
                }
            }
        }
        
        return exito;
    }

    //  Recibe el elemento que se desea eliminar y se procede a removerlo del árbol. Si no se encuentra
    //      el elemento no se puede realizar la eliminación. Devuelve verdadero si el elemento se elimina de la
    //      estructura y falso en caso contrario.
    public boolean eliminar(Comparable elemento){
        
        boolean exito = true;
        
        //  Si esta vacio, no hay nada que eliminar, se devuelve que no hubo exito
        if(this.esVacio()){
            exito = false;
        } else {
            //  Si la raiz no tiene el elemento que buscamos
            if(raiz.getElem().compareTo(elemento) != 0){
                //  Llamamos a la funcion privada auxiliar elminarAux
                exito = eliminarAux(elemento, raiz);
            } else {
                //  Si es el elemento que buscamos, se fija que caso es
                if(raiz.getIzquierdo() != null){
                    if(raiz.getDerecho() != null){
                        //  Caso 3, tiene ambos hijos
                        //  Para este caso existe una funcion privada llamada elminarCaso3
                        eliminarCaso3(raiz);
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
        
        return exito;
    }
    
    //  Funcion privada auxiliar para el metodo eliminar()
    //  Precondicion: Que el nodo pasado como paramentro no tenga el elemento buscado, ni sea nulo
    private boolean eliminarAux(Comparable elemento, NodoABB nodo){
        
        boolean exito = true;
        
        //  Si el valor del elemento que buscamos es menor al elemenoto del nodo por parametro
        if(elemento.compareTo(nodo.getElem()) < 0){
            //  Significa que debemos buscar por la izquierda
            NodoABB aux = nodo.getIzquierdo();
            //  Si no hay nada a la izquierda, no existe el elemento en el arbol, se devuelve que no hubo exito
            if(aux == null){
                exito = false;
            } else {
                //  Si hay algo a la izquierda, verifica si es el elemento buscado
                if(aux.getElem().compareTo(elemento) != 0){
                    //  De no serlo, busca por izquierda con el mismo metodo recursivo
                    exito = eliminarAux(elemento, aux); 
                } else {
                    //  Si lo es, verifica en que caso de eliminacion se encuentra y actua acorde
                    if(aux.getIzquierdo() != null){
                        if(aux.getDerecho() != null){
                            //  Caso 3, tiene ambos hijos, consigue candidato izquierdo, el mayor de los menores
                            eliminarCaso3(aux);
                        } else {
                            //  Caso 2, hijo izquierdo
                            nodo.setIzquierdo(aux.getIzquierdo());
                        }
                    } else {
                        if(aux.getDerecho() != null){
                            //  Caso 2, hijo derecho
                            nodo.setIzquierdo(aux.getDerecho());
                        } else {
                            //  Caso 1, sin hijos
                            nodo.setIzquierdo(null);
                        }
                    }
                }
            } 
        } else {
            //  Si no es menor, sabemos que no es mayor (Ya que la precondicion es que no sea igual), usa la misma logica 
            //      que para revisar por izquierda
            NodoABB aux = nodo.getDerecho();
            if(aux == null){
                exito = false;
            } else {
                if(aux.getElem().compareTo(elemento) != 0){
                    exito = eliminarAux(elemento, aux); 
                } else {
                    if(aux.getIzquierdo() != null){
                        if(aux.getDerecho() != null){
                            //  Caso 3, tiene ambos hijos, consigue candidato izquierdo, el mayor de los menores
                            eliminarCaso3(aux);
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
        
        return exito;
    }

    //  Funcion privada para realizar el algoritmo de eliminacion de caso 3, el caso en el que
    //      el nodo a ser eliminado tiene hijo izquierdo y derecho.
    private void eliminarCaso3(NodoABB nodo){
        //  Caso 3, tiene ambos hijos, consigue candidato izquierdo, el mayor de los menores
        NodoABB aux = nodo.getIzquierdo();
        //  Si es el primero a la izquierda
        if(aux.getDerecho() == null){
            //  Copia su elemento
            nodo.setElem(aux.getElem());
            //  En caso de tener hijo izquierdo
            if(aux.getIzquierdo() != null){
                //  Caso 2, hijo izquierdo
                nodo.setIzquierdo(aux.getIzquierdo());
            } else {
                //  Caso 1, sin hijos
                nodo.setIzquierdo(null);
            }
        } else {
            //  Si no es el primero a la izquierda, busca el padre del mayor de los menores
            while(aux.getDerecho().getDerecho() != null){
                aux = aux.getDerecho();
            }
            //  Copia el elemento del mayor
            nodo.setElem(aux.getDerecho().getElem());
            //  En caso de tener hijo izquierdo
            if(aux.getDerecho().getIzquierdo() != null){
                //  Caso 2, hijo izquierdo
                aux.setDerecho(aux.getDerecho().getIzquierdo());
            } else {
                //  Caso 1, sin hijos
                aux.setDerecho(null);
            }
        }
        
    }
    //  Devuelve verdadero si el elemento recibido por parámetro está en el árbol y falso en caso contrario.
    public boolean pertenece(Comparable elemento){
        return perteneceAux(elemento, raiz);
    }
    
    //  Funcion privada para pertenece()
    private boolean perteneceAux(Comparable elemento, NodoABB nodo){
        
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
    private void listarAux(NodoABB nodo, Lista list){
        
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
    private void listarRangoAux(Comparable elemMinimo, Comparable elemMaximo, NodoABB nodo, Lista list){
        
        if(nodo != null){
            //  Variable para referenciar al elemento del nodo facilmente
            Comparable aux = nodo.getElem();
            //  Si el elemento del nodo es menor al limite inferior del intervalo, no hacemos nada, ya que
            //      todos los elementos del subarbol del hijo izquierdo serán menores.
            if(aux.compareTo(elemMinimo) > 0){
                //  De no ser menor, revisamos el subarbol del hijo izquierdo
                listarRangoAux(elemMinimo, elemMaximo, nodo.getIzquierdo(), list);
            }
            //  Si el elemento del nodo se encuentra dentro del intervalo
            if(aux.compareTo(elemMinimo) >= 0 && aux.compareTo(elemMaximo) <= 0){
                //  Lo insertamos al final de la lista
                list.insertar(aux, list.longitud() + 1);
            }
            //  Si el elemento del nodo es mayor al limite superior del intervalo, no hacemos nada, ya que
            //      todos los elementos del subarbol del hijo derecho serán mayores.
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
            NodoABB aux = raiz;
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
            NodoABB aux = raiz;
            while(aux.getDerecho() != null){
                aux = aux.getDerecho();
            }
            resultado = aux.getElem();
        }
        
        return resultado;
    }
}
