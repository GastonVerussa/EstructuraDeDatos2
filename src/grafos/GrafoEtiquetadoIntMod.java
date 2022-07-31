package grafos;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;
import propositoEspecifico.Conjunto;
import utiles.Par;

public class GrafoEtiquetadoIntMod {
    
    //  Variable para mantener puntero al inicio
    private NodoVert inicio;
    
    //  Constructor, lo crea vacio
    public GrafoEtiquetadoIntMod(){
        this.inicio = null;
        Camino camino; 
    }
    
    //  Dado un elemento de TipoVertice se lo agrega a la estructura controlando 
    //      que no se inserten vértices repetidos. Si puede realizar la inserción
    //      devuelve verdadero, en caso contrario devuelve falso.
    public boolean insertarVertice(Object vertice){
        
        //  Variable para guardar el exito
        boolean exito = true;
        
        //  Si esta vacio
        if(this.esVacio()){
            //  Lo crea como el primer y nuevo vertice
            inicio = new NodoVert(vertice, null);
        } else {
            
            /*
            //  En caso de que no este vacio
            //  Variable para recorrer el arreglo
            NodoVert aux = inicio.getSigVertice();
            
            //  Hasta que haya terminado de recorder todo o haya encontrado un vertice igual
            while(aux != null && exito){
                //  Si es el vertice que queremos ingresar
                if(aux.getElem().equals(vertice)){
                    //  Da error, ya que no acepta repetidos
                    exito = false;
                } else {
                    //  Si no es, se va al siguiente
                    aux = aux.getSigVertice();
                }
            }
            
            //  Una vez que termina, si hubo exito (O sea no hubo fallo)
            */
            
            //  Si el vertice no existe
            if(ubicarVertice(vertice) == null){
                inicio = new NodoVert(vertice, inicio);
            } else {
                exito = false;
            }
        }
        
        return exito;
    }
    

    //  Dado un elemento de TipoVertice se lo quita de la estructura. Si se encuentra el vértice, también
    //      deben eliminarse todos los arcos que lo tengan como origen o destino. Si se puede realizar la
    //      eliminación con éxito devuelve verdadero, en caso contrario devuelve falso.
    public boolean eliminarVertice(Object vertice){
        
        //  Variable para guardar el exito
        boolean exito = false;
        
        //  Si esta vacio, no se hace nada, exito es falso
        if(!this.esVacio()){
            //  Si no esta vacio
            //  Aux sirve para recorrer la estructura buscando el vertice correcto
            NodoVert aux = inicio;
            //  Variable para tener referencia al padre de aux
            NodoVert padreAux = null;

            //  Mientras no haya recorrido toda la estructura ni haya encontrado el elemento
            while(aux != null && !exito){
                //  Si aux es el vertice que buscamos
                if(aux.getElem().equals(vertice)){
                    //  Seteamos el exito en true, saliendo del while
                    exito = true;
                    //  Antes de borrarlo se deben borrar los arcos
                } else {
                    //  Si no es el vertice que buscamos
                    //  Pasamos al siguiente, manteniendo padreAux como el nuevo padre
                    padreAux = aux;
                    aux = aux.getSigVertice();
                }
            }

            if(exito){
                NodoAdy auxAdy = aux.getPrimerAdy();

                while(auxAdy != null){

                    NodoVert auxVert2 = auxAdy.getVertice();

                    NodoAdy auxAdy2 = auxVert2.getPrimerAdy();

                    if(auxAdy2.getVertice().getElem().equals(vertice)){
                        auxVert2.setPrimerAdy(auxAdy2.getSigAdyacente());
                    } else {
                        boolean encontrado = false;
                        NodoAdy padreAuxAdy2 = auxAdy2;
                        auxAdy2 = auxAdy2.getSigAdyacente();

                        while(!encontrado){
                            if(auxAdy2.getVertice().getElem().equals(vertice)){
                                padreAuxAdy2.setSigAdyacente(auxAdy2.getSigAdyacente());
                                encontrado = true;
                            } else {
                                padreAuxAdy2 = auxAdy2;
                                auxAdy2 = auxAdy2.getSigAdyacente();
                            }
                        }
                    }

                    auxAdy = auxAdy.getSigAdyacente();
                }

                if(aux == inicio){
                    inicio = inicio.getSigVertice();
                } else {
                    padreAux.setSigVert(aux.getSigVertice());
                }
            }
        }
        
        return exito;
    }
    
    //  Dados dos elementos de TipoVertice (origen y destino) agrega el arco en la estructura, 
    //      sólo si ambos vértices ya existen en el grafo. Si puede realizar la inserción 
    //      devuelve verdadero, en caso contrario devuelve falso.
    public boolean insertarArco(Object origen, Object destino, int etiqueta){
        
        //  Variable que guarda el exito
        boolean exito = false;
        
        //  Si esta vacio claramente no existen los vertices, devuelve el exito falso
        //if(!this.esVacio()){
            
            /*
            //  Variables para guardar el lugar de los vertices
            NodoVert verticeOrigen = null;
            NodoVert verticeDestino = null;
            
            //  Variable para recorrer la estructura
            NodoVert aux = inicio;
            //  Variable para saber cuando fueron encontrados ambos vertices y no recorrer
            //      de mas la estructura
            boolean encontrados = false;
            
            //  Mientras no haya recorrido toda la estructura o no haya encontrado ambos vertices
            while(aux != null && !encontrados){
                //  Si es el vertice origen que se buscaba
                if(aux.getElem().equals(origen)){
                    //  Lo guardamos en su variable
                    verticeOrigen = aux;
                    //  Y si ya se encontro el vertice de destino
                    if(verticeDestino != null){
                        //  Se pone en true encontrados
                        encontrados = true;
                    }
                } else {
                    //  Si no es el origen, revisamos si es el destino
                    if(aux.getElem().equals(destino)){
                        //  Si lo es, entonces lo guardamos en su variable
                        verticeDestino = aux;
                        //  Y si tambien se encontro el vertice de origen
                        if(verticeOrigen != null){
                            //  Entonces se pone en true encontrados
                            encontrados = true;
                        }
                    }
                }
                //  Luego seguimos al siguiente vertice
                aux = aux.getSigVertice();
            }
            
            //  Si luego del while no fueron encontrados, entonces no existen
            //      ambos vertices, por lo que no hace nada y entrega false.
            */
            
        Par<NodoVert, NodoVert> vertices = ubicarVertices(origen, destino);
        
        if(vertices.getValor1() != null && vertices.getValor2() != null){
            //  Si fueron encontrados los vertices, entonces 
            //      crea una variable para recorrer los adyacentes al vertice origen
            NodoAdy auxAdy = vertices.getValor1().getPrimerAdy();
            boolean existeArco = false;

            while(auxAdy != null && !existeArco){
                if(auxAdy.getVertice().getElem().equals(destino)){
                    existeArco = true;
                } else {
                    auxAdy = auxAdy.getSigAdyacente();
                }
            }

            if(!existeArco){
                vertices.getValor1().setPrimerAdy(new NodoAdy(vertices.getValor2(), etiqueta, vertices.getValor1().getPrimerAdy()));
                vertices.getValor2().setPrimerAdy(new NodoAdy(vertices.getValor1(), etiqueta, vertices.getValor2().getPrimerAdy()));
                exito = true;
            }
        }
        //}
        
        return exito;
    }
    
    
    //  Dados dos elementos de TipoVertice (origen y destino) se quita de la estructura el arco que une
    //      ambos vértices. Si el arco existe y se puede realizar la eliminación con éxito devuelve verdadero, en
    //      caso contrario devuelve falso.
    public boolean eliminarArco(Object origen, Object destino){
        
        //  Variable que guarda el exito
        boolean exito = false;
        
        //  Si esta vacio claramente no existen los vertices, devuelve el exito falso
        if(!this.esVacio()){
            
            Par<NodoVert, NodoVert> vertices = ubicarVertices(origen, destino);
            //  Variables para guardar el lugar de los vertices
            NodoVert verticeOrigen = vertices.getValor1();
            NodoVert verticeDestino = vertices.getValor2();
            
            /*
            //  Variable para recorrer la estructura
            NodoVert aux = inicio;
            //  Variable para saber cuando fueron encontrados ambos vertices y no recorrer
            //      de mas la estructura
            boolean encontrados = false;
            
            //  Mientras no haya recorrido toda la estructura o no haya encontrado ambos vertices
            while(aux != null && !encontrados){
                //  Si es el vertice origen que se buscaba
                if(aux.getElem().equals(origen)){
                    //  Lo guardamos en su variable
                    verticeOrigen = aux;
                    //  Y si ya se encontro el vertice de destino
                    if(verticeDestino != null){
                        //  Se pone en true encontrados
                        encontrados = true;
                    }
                } else {
                    //  Si no es el origen, revisamos si es el destino
                    if(aux.getElem().equals(destino)){
                        //  Si lo es, entonces lo guardamos en su variable
                        verticeDestino = aux;
                        //  Y si tambien se encontro el vertice de origen
                        if(verticeOrigen != null){
                            //  Entonces se pone en true encontrados
                            encontrados = true;
                        }
                    }
                }
                //  Luego seguimos al siguiente vertice
                aux = aux.getSigVertice();
            }
            
            //  Si luego del while no fueron encontrados, entonces no existen
            //      ambos vertices, por lo que no hace nada y entrega false.
            */
                    
                    
            if(verticeOrigen != null && verticeDestino != null){
                
                //  Variable que se usa para recorrer la estructura de adyacentes a ambos vertices
                NodoAdy auxAdy = verticeOrigen.getPrimerAdy();
                
                //  Variable de control para saber si hubo exito en encontrar 
                //      y borrer el arco desde origen a destino
                boolean exitoOrigen = false;
                
                //  Si el vertice de origen no tiene adyacentes, se ignora lo siguiente
                if(auxAdy != null){
                    //  Si si tiene, se revisa si el primer adyacente es el buscado
                    if(auxAdy.getVertice().equals(verticeDestino)){
                        //  En caso de serlo, se borra cambiando al ser el nuevo primer
                        //      adyacente el siguiente del anterior adyacente
                        verticeOrigen.setPrimerAdy(auxAdy.getSigAdyacente());
                        //  Se cambia esta variable de control a true
                        exitoOrigen = true;
                    } else {
                        //  En caso de que no sea el primero
                        //  Se crea esta nueva variable para tener un puntero al auxiliar
                        NodoAdy padreAuxAdy = auxAdy;
                        //  Se va al siguiente nodo adyacente
                        auxAdy = auxAdy.getSigAdyacente();
                        
                        //  Mientras queden adyacentes por revisar y no se haya encontrado el
                        //      nodo adyacente buscado
                        while(auxAdy != null && !exitoOrigen){
                            //  Si es el adyacente buscado
                            if(auxAdy.getVertice().equals(verticeDestino)){
                                //  Se borra enlazando el padre al siguiente adyacente
                                padreAuxAdy.setSigAdyacente(auxAdy.getSigAdyacente());
                                //  Se cambia exitoOrigen a true
                                exitoOrigen = true;
                            } else {
                                //  Si no es el buscado, se pasa al siguiente, actualizando
                                //      tambien al padre
                                padreAuxAdy = auxAdy;
                                auxAdy = auxAdy.getSigAdyacente();
                            }
                        }
                    }
                }
                
                //  Si no hubo exito en encontrar el arco de origen a destino, tampoco
                //      tendra exito encontrar de destino a origen, por lo que se ignoraria
                //      lo siguiente
                if(exitoOrigen){
                    //  Si si hubo exito, entonces debemos buscar el arco de destino
                    //      a origen para borrarlo tambien, ya que es un grafo y no
                    //      digrafo
                    
                    //  Se pasa el auxiliar al primero del vertice destino
                    auxAdy = verticeDestino.getPrimerAdy();
                    
                    //  No necesitamos checkear si no tiene adyacentes, ya que el origen
                    //      tiene un adyacente a este destino, por lo que este debe tener
                    //      al menos ese arco de estar bien implementado
                    
                    //  Si el primer adyacente va al vertice de origen
                    if(auxAdy.getVertice().equals(verticeOrigen)){
                        //  Entonces se borra simplemente seteando como nuevo primer adyacente
                        //      a su siguiente adyacente
                        verticeDestino.setPrimerAdy(auxAdy.getSigAdyacente());
                        //  Una vez borrados ambos arcos, decimos que tuvo exito el metodo
                        exito = true;
                    } else {
                        //  Variable para tener puntero siempre al padre del auxiliar
                        NodoAdy padreAuxAdy = auxAdy;
                        //  El auxiliar pasa al siguiente
                        auxAdy = auxAdy.getSigAdyacente();

                        //  Mientras queden adyacentes por revisar y no se haya 
                        //      encontrados y borrado ambos arcos
                        while(auxAdy != null && !exito){
                            //  Si es el adyacente buscado
                            if(auxAdy.getVertice().equals(verticeOrigen)){
                                //  Se borra enlazando el padre al siguiente adyacente
                                padreAuxAdy.setSigAdyacente(auxAdy.getSigAdyacente());
                                //  Ambos arcos fueron encontrados y borrados, se setea exito a true
                                exito = true;
                            } else {
                                //  Si no es el buscado, se pasa al siguiente, actualizando
                                //      tambien al padre
                                padreAuxAdy = auxAdy;
                                auxAdy = auxAdy.getSigAdyacente();
                            }
                        }
                    }
                }
            }
        }
        
        return exito;
    }

    //  Dado un elemento, devuelve verdadero si está en la estructura y falso en caso contrario.
    public boolean existeVertice(Object vertice){
        /*
        //  Variable para guardar si existe
        boolean existe = false;
        
        //  Si esta vacio entonces no existe
        if(!this.esVacio()){
            /*
            //  Si no esta vacio, se lo debe buscar
            //  Variable para recorrer el arreglo
            NodoVert aux = inicio;
            
            //  Hasta que haya terminado de recorder todo o haya encontrado el vertice
            while(aux != null && !existe){
                //  Si es el vertice que buscamos
                if(aux.getElem().equals(vertice)){
                    //  Entonces existe, exito true
                    existe = true;
                } else {
                    //  Si no es, va al siguiente
                    aux = aux.getSigVertice();
                }
            }
        }
        */
        return ubicarVertice(vertice) != null;
    }
    
    //  Dados dos elementos de TipoVertice (origen y destino), devuelve verdadero 
    //      si existe un arco en la estructura que los une y falso en caso contrario.
    public boolean existeArco(Object origen, Object destino){
        
        //  Variable que guarda si existe
        boolean existe = false;
        
        //  Si esta vacio claramente no existen los vertices, devuelve el existe falso
        if(!this.esVacio()){
            
            Par<NodoVert, NodoVert> vertices = ubicarVertices(origen, destino);
            /* 
            //  Variables para guardar el lugar de los vertices
            NodoVert verticeOrigen = null;
            NodoVert verticeDestino = null;
            
            //  Variable para recorrer la estructura
            NodoVert aux = inicio;
            //  Variable para saber cuando fueron encontrados ambos vertices y no recorrer
            //      de mas la estructura
            boolean encontrados = false;
            
            //  Mientras no haya recorrido toda la estructura o no haya encontrado ambos vertices
            while(aux != null && !encontrados){
                //  Si es el vertice origen que se buscaba
                if(aux.getElem().equals(origen)){
                    //  Lo guardamos en su variable
                    verticeOrigen = aux;
                    //  Y si ya se encontro el vertice de destino
                    if(verticeDestino != null){
                        //  Se pone en true encontrados
                        encontrados = true;
                    }
                } else {
                    //  Si no es el origen, revisamos si es el destino
                    if(aux.getElem().equals(destino)){
                        //  Si lo es, entonces lo guardamos en su variable
                        verticeDestino = aux;
                        //  Y si tambien se encontro el vertice de origen
                        if(verticeOrigen != null){
                            //  Entonces se pone en true encontrados
                            encontrados = true;
                        }
                    }
                }
                //  Luego seguimos al siguiente vertice
                aux = aux.getSigVertice();
            }
            */
            
            //  Si luego del while no fueron encontrados, entonces no existen
            //      ambos vertices, por lo que no hace nada y entrega false.
            if(vertices.getValor1() != null && vertices.getValor2() != null){
                //  Variable que se usa para recorrer la estructura de adyacentes al origen
                NodoAdy auxAdy = vertices.getValor1().getPrimerAdy();
                
                //  Si el vertice de origen no tiene adyacentes, no existe el arco
                if(auxAdy != null){
                    //  Si si tiene, se busca el arco entre sus adyacentes
                    
                    //  Mientras queden adyacentes por revisar y no se haya encontrado el
                    //      nodo adyacente buscado
                    while(auxAdy != null && !existe){
                        //  Si es el adyacente buscado
                        if(auxAdy.getVertice().getElem().equals(destino)){
                            //  Fue encontrado, por lo que existe es verdadero
                            existe = true;
                        } else {
                            //  Si no lo es, va al siguiente
                            auxAdy = auxAdy.getSigAdyacente();
                        }
                    }
                }
            }
        }
        
        return existe;
    } 

    //  Dados dos elementos de TipoVertice (origen y destino), devuelve el valor 
    //      de la etiqueta del arco en la estructura que los une. De no existir
    //      tal arco, devuleve -1.
    public int recuperarEtiqueta(Object origen, Object destino){
        
        //  Variable que guarda si existe
        int etiqueta = -1;
        
        //  Si esta vacio claramente no existen los vertices, devuelve -1
        if(!this.esVacio()){
            
            Par<NodoVert, NodoVert> vertices = ubicarVertices(origen, destino);
            /*
            //  Variables para guardar el lugar de los vertices
            NodoVert verticeOrigen = null;
            NodoVert verticeDestino = null;
            
            //  Variable para recorrer la estructura
            NodoVert aux = inicio;
            //  Variable para saber cuando fueron encontrados ambos vertices y no recorrer
            //      de mas la estructura
            boolean encontrados = false;
            
            //  Mientras no haya recorrido toda la estructura o no haya encontrado ambos vertices
            while(aux != null && !encontrados){
                //  Si es el vertice origen que se buscaba
                if(aux.getElem().equals(origen)){
                    //  Lo guardamos en su variable
                    verticeOrigen = aux;
                    //  Y si ya se encontro el vertice de destino
                    if(verticeDestino != null){
                        //  Se pone en true encontrados
                        encontrados = true;
                    }
                } else {
                    //  Si no es el origen, revisamos si es el destino
                    if(aux.getElem().equals(destino)){
                        //  Si lo es, entonces lo guardamos en su variable
                        verticeDestino = aux;
                        //  Y si tambien se encontro el vertice de origen
                        if(verticeOrigen != null){
                            //  Entonces se pone en true encontrados
                            encontrados = true;
                        }
                    }
                }
                //  Luego seguimos al siguiente vertice
                aux = aux.getSigVertice();
            }
            */
            
            //  Si luego del while no fueron encontrados, entonces no existen
            //      ambos vertices, por lo que no hace nada y entrega false.
            if(vertices.getValor1() != null && vertices.getValor2() != null){
                //  Variable que se usa para recorrer la estructura de adyacentes al origen
                NodoAdy auxAdy = vertices.getValor1().getPrimerAdy();
                
                //  Si el vertice de origen no tiene adyacentes, no existe el arco
                if(auxAdy != null){
                    //  Si si tiene, se busca el arco entre sus adyacentes
                    
                    //  Mientras queden adyacentes por revisar y no se haya encontrado el
                    //      nodo adyacente buscado
                    while(auxAdy != null && etiqueta == -1){
                        //  Si es el adyacente buscado
                        if(auxAdy.getVertice().getElem().equals(destino)){
                            //  Fue encontrado, por lo que existe es verdadero
                            etiqueta = auxAdy.getEtiqueta();
                        } else {
                            //  Si no lo es, va al siguiente
                            auxAdy = auxAdy.getSigAdyacente();
                        }
                    }
                }
            }
        }
        
        return etiqueta;
    } 
    
    //  Dados dos elementos de TipoVertice (origen y destino), devuelve verdadero si existe al menos
    //      un camino que permite llegar del vértice origen al vértice destino y falso en caso contrario.
    public boolean existeCamino(Object origen, Object destino){
        
        //  Variable que guarda si existe
        boolean existe = false;
        
        //  Si esta vacio claramente no existen los vertices, devuelve el existe falso
        if(!this.esVacio()){
            
            Par<NodoVert, NodoVert> vertices = ubicarVertices(origen, destino);
            
            /*
            //  Variables para guardar el lugar de los vertices
            NodoVert verticeOrigen = null;
            NodoVert verticeDestino = null;
            
            //  Variable para recorrer la estructura
            NodoVert aux = inicio;
            //  Variable para saber cuando fueron encontrados ambos vertices y no recorrer
            //      de mas la estructura
            boolean encontrados = false;
            
            //  Mientras no haya recorrido toda la estructura o no haya encontrado ambos vertices
            while(aux != null && !encontrados){
                //  Si es el vertice origen que se buscaba
                if(aux.getElem().equals(origen)){
                    //  Lo guardamos en su variable
                    verticeOrigen = aux;
                    //  Y si ya se encontro el vertice de destino
                    if(verticeDestino != null){
                        //  Se pone en true encontrados
                        encontrados = true;
                    }
                } else {
                    //  Si no es el origen, revisamos si es el destino
                    if(aux.getElem().equals(destino)){
                        //  Si lo es, entonces lo guardamos en su variable
                        verticeDestino = aux;
                        //  Y si tambien se encontro el vertice de origen
                        if(verticeOrigen != null){
                            //  Entonces se pone en true encontrados
                            encontrados = true;
                        }
                    }
                }
                //  Luego seguimos al siguiente vertice
                aux = aux.getSigVertice();
            }
            */
            
            //  Si luego del while no fueron encontrados, entonces no existen
            //      ambos vertices, por lo que no hace nada y entrega false.
            if(vertices.getValor1() != null && vertices.getValor2() != null){
                //  De ser encontrados, crea una variable conjunto que guardara que
                //      vertices ya fueron visitados para evitar bucles.
                Conjunto verticesRecorridos = new Conjunto();
                //  Llama a la funcion auxiliar para saber si existe un camino
                existe = existeCaminoAux(vertices.getValor1(), destino, verticesRecorridos);
            }
        }
        
        return existe;
    }
        
    //  Funcion privada auxiliar para la funcion de existe camino. Recorre recursivamente todos sus
    //      adyacentes que no hayan sido visitados
    private boolean existeCaminoAux(NodoVert vertice, Object destino, Conjunto verticesRecorridos){
        
        //  Variable para guardar el exito
        boolean existe = false;
        
        //  Auxiliar para recorrer los nodos adyacentes del nodo vertice
        NodoAdy aux = vertice.getPrimerAdy();
        
        //  Si no tiene adyacentes devolvemos false, que no existe en este camino
        if(aux != null){
            //  Se agrega el vertice en el que estamos para no visitarlo dos veces
            verticesRecorridos.agregar(vertice.getElem());
            
            do{
                //  Variable para referenciar mas facilmente el vertice del adyacente actual
                NodoVert auxVert = aux.getVertice();
                
                //  Si es el que buscamos, entonces existe un camino
                if(auxVert.getElem().equals(destino)){
                    existe = true;
                } else {
                    //  Si no lo es, nos fijamos si ya fue visitado para evitar bucles
                    if(!verticesRecorridos.pertenece(auxVert.getElem())){
                        //  Si no esta entre los vertices viistados, entonces se llama
                        //      recursivamente para checkear este posible caminno, con el
                        //      nuevo vertice y un clon del conjunto, para que las llamadas
                        //      recursivas no modifique este clon. 
                        existe = existeCaminoAux(auxVert, destino, verticesRecorridos.clone());
                    }
                }
                
                //  Luego avanza al siguiente adyacente al auxiliar
                aux = aux.getSigAdyacente();
                //  Repite mientras no se haya revisado todos los camino o haya
                //      sido encontrado el camino.
            } while(aux != null && !existe);
        }
        
        return existe;
    } 
    
    /*
    //  Dados dos elementos de TipoVertice (origen y destino), devuelve un camino 
    //      (lista de vértices) que indique el camino que pasa por menos vértices 
    //      que permite llegar del vértice origen al vérticedestino. Si hay más 
    //      de un camino con igual cantidad de vértices, devuelve cualquiera de ellos. Si
    //      alguno de los vértices no existe o no hay camino posible entre ellos devuelve la lista vacía.
    public Lista caminoMasCorto(Object origen, Object destino){
        
        Lista resultado = new Lista();
        
        //  Si esta vacio claramente no existen los vertices, devuelve la lista vacia
        if(!this.esVacio()){
            //  Variables para guardar el lugar de los vertices
            NodoVert verticeOrigen = null;
            NodoVert verticeDestino = null;
            
            //  Variable para recorrer la estructura
            NodoVert aux = inicio;
            //  Variable para saber cuando fueron encontrados ambos vertices y no recorrer
            //      de mas la estructura
            boolean encontrados = false;
            
            //  Mientras no haya recorrido toda la estructura o no haya encontrado ambos vertices
            while(aux != null && !encontrados){
                //  Si es el vertice origen que se buscaba
                if(aux.getElem().equals(origen)){
                    //  Lo guardamos en su variable
                    verticeOrigen = aux;
                    //  Y si ya se encontro el vertice de destino
                    if(verticeDestino != null){
                        //  Se pone en true encontrados
                        encontrados = true;
                    }
                } else {
                    //  Si no es el origen, revisamos si es el destino
                    if(aux.getElem().equals(destino)){
                        //  Si lo es, entonces lo guardamos en su variable
                        verticeDestino = aux;
                        //  Y si tambien se encontro el vertice de origen
                        if(verticeOrigen != null){
                            //  Entonces se pone en true encontrados
                            encontrados = true;
                        }
                    }
                }
                //  Luego seguimos al siguiente vertice
                aux = aux.getSigVertice();
            }
            
            //  Si luego del while no fueron encontrados, entonces no existen
            //      ambos vertices, por lo que no hace nada y entrega la lista vacia.
            if(encontrados){
                Lista caminoRecorrido = new Lista();
                resultado = caminoMasCortoAux(verticeOrigen, destino, caminoRecorrido);
            }
        }
        return resultado;
    }
       
    //  Funcion privada auxiliar de caminoMasCorto, devuleve el camino mas corto
    private Lista caminoMasCortoAux(NodoVert vertice, Object destino, Lista caminoRecorrido){
        
        //  Variable resultado para guardar el camino correcto mas corto, o lista vacia
        //      de no encontrarlo
        Lista resultado = new Lista();
        
        //  Se agrega al camino recorrido
        caminoRecorrido.insertar(vertice.getElem(), caminoRecorrido.longitud() + 1);
        
        //  Variable para recorrer el arreglo
        NodoAdy aux = vertice.getPrimerAdy();
        
        //  Variable de control para saber si el destino esta adyacente a vertice, ya
        //      que si esta adyacente, el camino mas corto es el recto y no es necesario
        //      revisar los demas adyacentes
        boolean destinoAdyacente = false;
        
        //  Mientras no se hayan recorrido todos los adyacentes y no este adyacente el destino
        while(aux!=null && !destinoAdyacente){
            //  Si es el que buscamos, entonces existe un camino
            if(aux.getVertice().getElem().equals(destino)){
                //  Sumamos el destino al camino
                caminoRecorrido.insertar(destino, caminoRecorrido.longitud() + 1);
                //  Lo guardamos en el resultado
                resultado = caminoRecorrido;
                //  Y avisamos que esta adyacente
                destinoAdyacente = true;
            } else {
                //  Si no lo es, nos fijamos si ya fue visitado para evitar bucles
                if(caminoRecorrido.localizar(aux.getVertice().getElem()) == -1){
                    //  Si no esta entre los vertices viistados, entonces se llama
                    //      recursivamente para checkear este posible caminno, con el
                    //      nuevo vertice y un clon de la lista, para que las llamadas
                    //      recursivas no modifique este clon. 
                    Lista listaAux = caminoMasCortoAux(aux.getVertice(), destino, caminoRecorrido.clone());
                    //  Si devuleve una lista vacia significa que no encontro camino por ese adyacente
                    if(!listaAux.esVacia()){
                        //  Si no es vacia, encontro algun camino
                        if(resultado.esVacia()){
                            //  Si el resultado es vacia significa que es el primer camino encontrado
                            //      y lo guarda en el resultado
                            resultado = listaAux;
                        } else {
                            //  Si no, ya se encontró un camino pero se debe comparar cual es el mas corto
                            if(listaAux.longitud() < resultado.longitud()){
                                //  Si el nuevo camino encontrado es mas corto que el ya guardado, se
                                //      actualiza el resultado
                                resultado = listaAux;
                            }
                        }
                    }
                }
            }
        }
        
        return resultado;
    }
  
    //  Dados dos elementos de TipoVertice (origen y destino), devuelve un camino 
    //      (lista de vértices) que indique el camino que pasa por más vértices (sin ciclos) 
    //      que permite llegar del vértice origen al vértice destino. Si hay más de un camino 
    //      con igual cantidad de vértices, devuelve cualquiera de ellos. Si alguno de los 
    //      vértices no existe o no hay camino posible entre ellos devuelve la lista vacía.
    public Lista caminoMasLargo(Object origen, Object destino){
        
        Lista resultado = new Lista();
        
        //  Si esta vacio claramente no existen los vertices, devuelve la lista vacia
        if(!this.esVacio()){
            //  Variables para guardar el lugar de los vertices
            NodoVert verticeOrigen = null;
            NodoVert verticeDestino = null;
            
            //  Variable para recorrer la estructura
            NodoVert aux = inicio;
            //  Variable para saber cuando fueron encontrados ambos vertices y no recorrer
            //      de mas la estructura
            boolean encontrados = false;
            
            //  Mientras no haya recorrido toda la estructura o no haya encontrado ambos vertices
            while(aux != null && !encontrados){
                //  Si es el vertice origen que se buscaba
                if(aux.getElem().equals(origen)){
                    //  Lo guardamos en su variable
                    verticeOrigen = aux;
                    //  Y si ya se encontro el vertice de destino
                    if(verticeDestino != null){
                        //  Se pone en true encontrados
                        encontrados = true;
                    }
                } else {
                    //  Si no es el origen, revisamos si es el destino
                    if(aux.getElem().equals(destino)){
                        //  Si lo es, entonces lo guardamos en su variable
                        verticeDestino = aux;
                        //  Y si tambien se encontro el vertice de origen
                        if(verticeOrigen != null){
                            //  Entonces se pone en true encontrados
                            encontrados = true;
                        }
                    }
                }
                //  Luego seguimos al siguiente vertice
                aux = aux.getSigVertice();
            }
            
            //  Si luego del while no fueron encontrados, entonces no existen
            //      ambos vertices, por lo que no hace nada y entrega la lista vacia.
            if(encontrados){
                //  Lista para guardar el camino recorrido, tanto para la devolucion final
                //      como para saber por cuales vertices pasamos
                Lista caminoRecorrido = new Lista();
                //  Guarda el resultado de llamar a su funcion auxiliar del origen al destino
                resultado = caminoMasLargoAux(verticeOrigen, destino, caminoRecorrido);
            }
        }
        return resultado;
    }
    
    private Lista caminoMasLargoAux(NodoVert vertice, Object destino, Lista caminoRecorrido){
        
        //  Variable resultado para guardar el camino correcto mas largo, o lista vacia
        //      de no encontrarlo
        Lista resultado = new Lista();
        
        //  Se agrega al camino recorrido
        caminoRecorrido.insertar(vertice.getElem(), caminoRecorrido.longitud() + 1);
        
        //  Variable para recorrer el arreglo
        NodoAdy aux = vertice.getPrimerAdy();
        
        //  Mientras no se hayan recorrido todos los adyacentes
        while(aux!=null){
            //  Si es el que buscamos, entonces existe un camino
            if(aux.getVertice().getElem().equals(destino)){
                //  Sumamos el destino al camino
                caminoRecorrido.insertar(destino, caminoRecorrido.longitud() + 1);
                //  Lo guardamos en el resultado
                resultado = caminoRecorrido;
            } else {
                //  Si no lo es, nos fijamos si ya fue visitado para evitar bucles
                if(caminoRecorrido.localizar(aux.getVertice().getElem()) == -1){
                    //  Si no esta entre los vertices viistados, entonces se llama
                    //      recursivamente para checkear este posible caminno, con el
                    //      nuevo vertice y un clon de la lista, para que las llamadas
                    //      recursivas no modifiquen este clon. 
                    Lista listaAux = caminoMasLargoAux(aux.getVertice(), destino, caminoRecorrido.clone());
                    
                    //  No necesito checkear si alguna de las listas esta vacia
                    if(listaAux.longitud() > resultado.longitud()){
                        //  Si el nuevo camino encontrado es mas largo que el ya guardado, se
                        //      actualiza el resultado
                        resultado = listaAux;
                    }
                }
            }
        }
        
        return resultado;
    }
    */
    //  Devuelve una lista con los vértices del grafo visitados según el recorrido 
    //      en profundidad explicado en la sección anterior.
    public Lista listarEnProfundidad(){
        
        Lista resultado = new Lista();
        
        NodoVert aux = inicio;
        
        while(aux != null){
            if(resultado.localizar(aux.getElem()) < 0){
                profundidadDesde(aux, resultado);
            }
            aux = aux.getSigVertice();
        }
        
        return resultado;
    }

    //  Funcion privada auxiliar para listarEnProfundidad
    private void profundidadDesde(NodoVert vertice, Lista visitados){
        
        visitados.insertar(vertice.getElem(), visitados.longitud() + 1);
        
        NodoAdy aux = vertice.getPrimerAdy();
        
        while(aux != null){
            if(visitados.localizar(aux.getVertice().getElem()) < 0){
                profundidadDesde(aux.getVertice(), visitados);
            }
            aux = aux.getSigAdyacente();
        }
    }
    
    //  Devuelve una lista con los vértices del grafo visitados según el recorrido 
    //      en anchura explicado en la sección anterior.
    public Lista listarEnAnchura(){
        
        Lista resultado = new Lista();
        
        NodoVert aux = inicio;
        
        while(aux != null){
            if(resultado.localizar(aux.getElem()) < 0){
                anchuraDesde(aux, resultado);
            }
            aux = aux.getSigVertice();
        }
        
        return resultado;
    }
        
    //  Funcion privada auxiliar para listarEnAnchura
    private void anchuraDesde(NodoVert vertice, Lista visitados){
        
        Cola colaAux = new Cola();
        
        visitados.insertar(vertice.getElem(), visitados.longitud() + 1);
        
        colaAux.poner(vertice);
        NodoVert auxVert;
        NodoAdy auxAdy;
        
        while(!colaAux.esVacia()){
            auxVert = (NodoVert) colaAux.obtenerFrente();
            colaAux.sacar();
            auxAdy = auxVert.getPrimerAdy();
            while(auxAdy != null){
                if(visitados.localizar(auxAdy.getVertice().getElem()) < 0){
                    visitados.insertar(auxAdy.getVertice().getElem(), visitados.longitud() + 1);
                    colaAux.poner(auxAdy.getVertice());
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
        
    }
    
    public boolean esVacio(){
        return this.inicio == null;
    }
    
    public void vaciar(){
        this.inicio = null;
    }
           
    //  Genera y devuelve un grafo que es equivalente (igual estructura y contenido 
    //      de los nodos) al original. 
    @Override
    public GrafoEtiquetadoIntMod clone(){
        
        //  Nuevo grafo que se pasara como resultado final
        GrafoEtiquetadoIntMod resultado = new GrafoEtiquetadoIntMod();
        
        //  Nodo vertice auxiliar que usaremos para recorrer la estructura
        NodoVert aux = inicio;
        
        //  Primero inserta todos los vertices, ya que si intentamos insertar algun arco
        //      a un vertice que no exista no funcionara
        while(aux != null){
            //  Insertarmos el vertice
            resultado.insertarVertice(aux.getElem());
            //  Y pasamos al siguiente
            aux = aux.getSigVertice();
        }
        
        //  Conjunto que servira para saber por que elemenots ya pasamos
        Conjunto visitados = new Conjunto();
        //  Volvemos al inicio
        aux = inicio;
        //  Nodo adyacente auxiliar que nos servira para recorrer todos los adyacentes
        //      de un nodo vertice dado, es decir, sus arcos
        NodoAdy auxAdy;
        
        //  Recorremos toda la estructura nuevamente
        while(aux != null){
            
            //  Agregamos el elemento al conjunto de visitados
            visitados.agregar(aux.getElem());
            //  Conseguimos su primer adyacente o arco
            auxAdy = aux.getPrimerAdy();
            
            //  Recorremos todos sus arcos
            while(auxAdy != null){
                //  Si el elemento del nodo al que va el arco ya fue visitado, esto significa
                //      que el arco desde alla hasta este vertice ya fue creado y por ende
                //      tambien el de este vertice hacia alla, ya que es un grafo y no digrafo
                
                //  Se podria evitar este if y este while y simplemente poner que intente insertar 
                //      todos los arcos en el primer while del metodo. Pero de esta forma
                //      intentariamos tantos insertar arco fallidos que no seria eficiente.
                if(!visitados.pertenece(auxAdy.getVertice().getElem())){
                    //  Pero si no fue visitado, entonces agrega el arco. 
                    resultado.insertarArco(aux.getElem(), auxAdy.getVertice().getElem(), auxAdy.getEtiqueta());
                }
                //  Avanza al siguiente arco
                auxAdy = auxAdy.getSigAdyacente();
            }
            //  Avanza al siguiente vertice
            aux = aux.getSigVertice();
        }
        
        return resultado;
    }

    //  Con fines de debugging, este método genera y devuelve una cadena String que muestra los
    //      vértices almacenados en el grafo y qué adyacentes tiene cada uno de ellos.
    @Override
    public String toString(){
        
        String resultado;
        
        if(this.esVacio()){
            resultado = "Grafo vacio";
        } else {
            
            resultado = "Formato de grafo. <elementoVertice> : {<destinoArco>, <etiquetaArco>} -  ... \n";
            NodoVert aux = inicio;

            while(aux != null){
                
                resultado += "\n " + aux.getElem().toString() + ": ";
                
                NodoAdy auxAdy = aux.getPrimerAdy();
                
                while(auxAdy != null){
                    resultado += " { " + auxAdy.getVertice().getElem().toString() + ", " + auxAdy.getEtiqueta() + " } -";
                    auxAdy = auxAdy.getSigAdyacente();
                }
                
                aux = aux.getSigVertice();
            }
        }
        
        return resultado;
    }
    
    //  Funcion que dado el elemento de un vertice, devuelve los elementos de todos sus
    //      vertices adyacentes
    public Lista conseguirAdyacentes(Object vertice){
        
        //  Lista que se devolvera como resultado
        Lista resultado = new Lista();
        
        //  Nodo vertice auxiliar que guarda la posicion del vertice buscado
        NodoVert nodoVertice = ubicarVertice(vertice);
        
        //  Si existe
        if(nodoVertice != null){
            //  Nodo adyacente auxiliar para recorrer todos los adyacentes del vertice
            NodoAdy auxAdy = nodoVertice.getPrimerAdy();
            
            //  Mientras queden adyacentes por recorrer
            while(auxAdy != null){
                //  Inserta el elemento del vertice adyacente en la lista
                resultado.insertar(auxAdy.getVertice().getElem(), 1);
                //  Y pasa al siguiente adyacente
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
        
        return resultado;
    }
    
    public Lista caminoMasCortoEtiqueta(Object origen, Object destino){
        
        //  Variable que guarda el resultado
        Lista resultado = new Lista();
        //  Consigue todos los caminos posibles del origen al destino
        Lista todosCaminos = caminosPosibles(origen, destino);
        
        //  Si no hay caminos posibles, devuelve la lista vacia
        if(!todosCaminos.esVacia()){
            //  Si hay al menos un camino posible
            //  Variable para recorrer los caminos posibles
            Camino caminoAux;
            //  Variable para ir guardando la distancia del recorrido mas corto
            int menorDistancia;
            
            //  Consigue el primer camino
            caminoAux = (Camino) todosCaminos.recuperar(1);
            //  Lo guarda en el resultado
            resultado = caminoAux.getRecorrido();
            //  Y guarda su distancia
            menorDistancia = caminoAux.getDistancia();
            
            //  Recorre toda la lista de caminos posibles, empezando por el segundo
            //      ya que el primero ya fue visitado
            for(int i = 2; i <= todosCaminos.longitud(); i++){
                //  Recupera el camino en la posicion correspondiente
                caminoAux = (Camino) todosCaminos.recuperar(i);
                //  Si su distancia es menor a la mas corta entre las encontradas
                if(caminoAux.getDistancia() < menorDistancia){
                    //  Lo guarda como el nuevo camino mas corto
                    resultado = caminoAux.getRecorrido();
                    //  Y guarda la nueva distancia mas corta
                    menorDistancia = caminoAux.getDistancia();
                }
            }
        }
            
        return resultado;
    }
    
    public Lista caminoMasCorto(Object origen, Object destino){
        
        //  Variable que guarda el resultado
        Lista resultado = new Lista();
        //  Consigue todos los caminos posibles del origen al destino
        Lista todosCaminos = caminosPosibles(origen, destino);
        
        //  Si no hay caminos posibles, devuelve la lista vacia
        if(!todosCaminos.esVacia()){
            //  Si hay al menos un camino posible
            //  Variable para recorrer los caminos posibles
            Camino caminoAux;
            
            //  Consigue el primer camino
            caminoAux = (Camino) todosCaminos.recuperar(1);
            //  Lo guarda en el resultado
            resultado = caminoAux.getRecorrido();
            
            //  Recorre toda la lista de caminos posibles, empezando por el segundo
            //      ya que el primero ya fue visitado
            for(int i = 2; i <= todosCaminos.longitud(); i++){
                //  Recupera el camino en la posicion correspondiente
                caminoAux = (Camino) todosCaminos.recuperar(i);
                //  Si su distancia es menor a la mas corta entre las encontradas
                if(caminoAux.getRecorrido().longitud() < resultado.longitud()){
                    //  Lo guarda como el nuevo camino mas corto
                    resultado = caminoAux.getRecorrido();
                }
            }
        }
            
        return resultado;
    }
    
    public Lista caminoMasLargo(Object origen, Object destino){
        
        //  Variable que guarda el resultado
        Lista resultado = new Lista();
        //  Consigue todos los caminos posibles del origen al destino
        Lista todosCaminos = caminosPosibles(origen, destino);
        
        //  Si no hay caminos posibles, devuelve la lista vacia
        if(!todosCaminos.esVacia()){
            //  Si hay al menos un camino posible
            //  Variable para recorrer los caminos posibles
            Camino caminoAux;
            
            //  Consigue el primer camino
            caminoAux = (Camino) todosCaminos.recuperar(1);
            //  Lo guarda en el resultado
            resultado = caminoAux.getRecorrido();
            
            //  Recorre toda la lista de caminos posibles, empezando por el segundo
            //      ya que el primero ya fue visitado
            for(int i = 2; i <= todosCaminos.longitud(); i++){
                //  Recupera el camino en la posicion correspondiente
                caminoAux = (Camino) todosCaminos.recuperar(i);
                //  Si su distancia es menor a la mas corta entre las encontradas
                if(caminoAux.getRecorrido().longitud() > resultado.longitud()){
                    //  Lo guarda como el nuevo camino mas corto
                    resultado = caminoAux.getRecorrido();
                }
            }
        }
            
        return resultado;
    }
    
    /*
    public Lista caminosMenoresKm(Object origen, Object destino, int limiteKm){
        
        //  Variable que guarda el resultado
        Lista resultado = new Lista();
        //  Consigue todos los caminos posibles del origen al destino
        Lista todosCaminos = caminosPosibles(origen, destino);
        
        //  Si no hay caminos posibles, devuelve la lista vacia
        if(!todosCaminos.esVacia()){
            //  Si hay al menos un camino posible
            //  Variable para recorrer los caminos posibles
            Camino caminoAux;
            
            //  Consigue el primer camino
            caminoAux = (Camino) todosCaminos.recuperar(1);
            //  Lo guarda en el resultado
            resultado = caminoAux.getRecorrido();
            
            //  Recorre toda la lista de caminos posibles, empezando por el segundo
            //      ya que el primero ya fue visitado
            for(int i = 2; i <= todosCaminos.longitud(); i++){
                //  Recupera el camino en la posicion correspondiente
                caminoAux = (Camino) todosCaminos.recuperar(i);
                //  Si su distancia es menor a la mas corta entre las encontradas
                if(caminoAux.getRecorrido().longitud() > resultado.longitud()){
                    //  Lo guarda como el nuevo camino mas corto
                    resultado = caminoAux.getRecorrido();
                }
            }
        }
            
        return resultado;
    }
    */
    
    
    
    /*
    public Lista caminoMasCortoEtiqueta(Object origen, Object destino){
        
        Lista resultado = new Lista();
        
        //  Si esta vacio claramente no existen los vertices, devuelve la lista vacia
        if(!this.esVacio()){
            //  Variables para guardar el lugar de los vertices
            NodoVert verticeOrigen = null;
            NodoVert verticeDestino = null;
            
            //  Variable para recorrer la estructura
            NodoVert aux = inicio;
            //  Variable para saber cuando fueron encontrados ambos vertices y no recorrer
            //      de mas la estructura
            boolean encontrados = false;
            
            //  Mientras no haya recorrido toda la estructura o no haya encontrado ambos vertices
            while(aux != null && !encontrados){
                //  Si es el vertice origen que se buscaba
                if(aux.getElem().equals(origen)){
                    //  Lo guardamos en su variable
                    verticeOrigen = aux;
                    //  Y si ya se encontro el vertice de destino
                    if(verticeDestino != null){
                        //  Se pone en true encontrados
                        encontrados = true;
                    }
                } else {
                    //  Si no es el origen, revisamos si es el destino
                    if(aux.getElem().equals(destino)){
                        //  Si lo es, entonces lo guardamos en su variable
                        verticeDestino = aux;
                        //  Y si tambien se encontro el vertice de origen
                        if(verticeOrigen != null){
                            //  Entonces se pone en true encontrados
                            encontrados = true;
                        }
                    }
                }
                //  Luego seguimos al siguiente vertice
                aux = aux.getSigVertice();
            }
            
            //  Si luego del while no fueron encontrados, entonces no existen
            //      ambos vertices, por lo que no hace nada y entrega la lista vacia.
            if(encontrados){
                //  Lista que guardara el camino recorrido. Ademas guardara la distancia
                //      total recorrida como el primer elemento
                Lista caminoRecorrido = new Lista();
                //  La inicia con una distancia de 0
                caminoRecorrido.insertar(0, 1);
                //  Llama a la funcion privada para conseguir el camino mas corto
                resultado = caminoMasCortoEtiquetaAux(verticeOrigen, destino, caminoRecorrido);
                //  Si no esta vacia, osea que se encontro un camino posible
                if(!resultado.esVacia()){
                    //  Entonces borra la distancia total antes de devolver la lista
                    resultado.eliminar(1);
                }
            }
        }
        
        return resultado;
    }
       
    //  Funcion privada auxiliar de caminoMasCorto, devuleve el camino mas corto
    private Lista caminoMasCortoEtiquetaAux(NodoVert vertice, Object destino, Lista caminoRecorrido){
        
        //  Variable resultado para guardar el camino correcto mas corto, o lista vacia
        //      de no encontrarlo
        Lista resultado = new Lista();
        
        //  Se agrega al camino recorrido
        caminoRecorrido.insertar(vertice.getElem(), caminoRecorrido.longitud() + 1);
        
        //  Variable para recorrer el arreglo
        NodoAdy aux = vertice.getPrimerAdy();
        Lista listaAux;
        
        //  Mientras no se hayan recorrido todos los adyacentes
        while(aux!=null){
            //  Si es el que buscamos, entonces existe un camino
            if(aux.getVertice().getElem().equals(destino)){
                //  Sumamos el destino al camino
                caminoRecorrido.insertar(destino, caminoRecorrido.longitud() + 1);
                //  Actualizamos la distancia
                actualizarDistancia(caminoRecorrido, aux.getEtiqueta());
                //  Lo guardamos en el resultado
                resultado = caminoRecorrido;
            } else {
                //  Si no lo es, nos fijamos si ya fue visitado para evitar bucles
                if(caminoRecorrido.localizar(aux.getVertice().getElem()) == -1){
                    //  Si no esta entre los vertices viistados, entonces se llama
                    //      recursivamente para checkear este posible caminno, con el
                    //      nuevo vertice y un clon de la lista, para que las llamadas
                    //      recursivas no modifique este clon. 
                    listaAux = caminoRecorrido.clone();
                    actualizarDistancia(listaAux, aux.getEtiqueta());
                    caminoMasCortoEtiquetaAux(aux.getVertice(), destino, listaAux);
                    //  Si devuleve una lista vacia significa que no encontro camino por ese adyacente
                    if(!listaAux.esVacia()){
                        //  Si no es vacia, encontro algun camino
                        if(resultado.esVacia()){
                            //  Si el resultado es vacia significa que es el primer camino encontrado
                            //      y lo guarda en el resultado
                            resultado = listaAux;
                        } else {
                            //  Si no, ya se encontró un camino pero se debe comparar cual es el mas corto
                            if(listaAux.longitud() < resultado.longitud()){
                                //  Si el nuevo camino encontrado es mas corto que el ya guardado, se
                                //      actualiza el resultado
                                resultado = listaAux;
                            }
                        }
                    }
                }
            }
        }
        
        return resultado;
    }
    */
    
    //  Funcion que devuelve todos los caminos posibles entre dos elementos, sin que superen un limite de distancia
    public Lista caminosMenoresKm(Object origen, Object destino, int limiteKm){
        
        Lista resultado = new Lista();
        
        if(!this.esVacio()){
            
            //  Consigue ambos vertices
            Par<NodoVert, NodoVert> vertices = ubicarVertices(origen, destino);
            
            //  Si luego del while no fueron encontrados, entonces no existen
            //      ambos vertices, por lo que no hace nada y entrega la lista vacia.
            if(vertices.getValor1() != null && vertices.getValor2() != null){
                //  Instancia de Camino, una clase definida dentro de esta clase
                //      sirve para guardar un recorrido en una lista y su distancia
                //      en un int
                Camino caminoRecorrido = new Camino(new Lista(), 0);
                caminoRecorrido.getRecorrido().insertar(origen, 1);
                //  Llama a la funcion privada para conseguir el camino mas corto
                caminosMenoresKmAux(vertices.getValor1(), destino, limiteKm, caminoRecorrido, resultado);
            }
        }
        
        return resultado;
    }
    
    private void caminosMenoresKmAux(NodoVert vertice, Object destino, int limiteKm, Camino caminoRecorrido, Lista resultado){
        //  Variable para referenciar al recorrido mas facil
        Lista recorrido = caminoRecorrido.getRecorrido();
        
        //  Variable para recorrer el arreglo
        NodoAdy aux = vertice.getPrimerAdy();
        Camino caminoAux;
        
        //  Mientras no se hayan recorrido todos los adyacentes
        while(aux!=null){
            if(aux.getEtiqueta() + caminoRecorrido.getDistancia() < limiteKm){
                //  Nos fijamos si ya fue visitado para evitar bucles
                if(recorrido.localizar(aux.getVertice().getElem()) < 0){
                    //  Si no esta entre los vertices viistados

                    //  Crea un clon del camino recorrido para no afectar al original
                    caminoAux = caminoRecorrido.clone();
                    //  Sumamos el destino al camino
                    caminoAux.getRecorrido().insertar(aux.getVertice().getElem(), caminoAux.getRecorrido().longitud() + 1);
                    //  Actualizamos la distancia
                    caminoAux.aumentarDistancia(aux.getEtiqueta());

                    //  Si el arco va al destino
                    if(aux.getVertice().getElem().equals(destino)){
                        //  Guardamos el camino en el resultado
                        resultado.insertar(caminoAux, 1);
                    } else {
                        //  Si no va al destino buscado, se llama a si misma
                        //      recursivamente para checkear este posible caminno, con el
                        //      nuevo vertice y el clon del camino, para que las llamadas
                        //      recursivas no modifique el camino original. 
                        caminosMenoresKmAux(aux.getVertice(), destino, limiteKm, caminoAux, resultado);
                    }
                }
            }
            //  Va al siguiente adyacente
            aux = aux.getSigAdyacente();
        }
    }
    
    //  Funcion que devuleve todos una lista con todo
    public Lista caminosPosibles(Object origen, Object destino){
        
        Lista resultado = new Lista();
        
        if(!this.esVacio()){
            
            //  Consigue ambos vertices
            Par<NodoVert, NodoVert> vertices = ubicarVertices(origen, destino);
            
            //  Si luego del while no fueron encontrados, entonces no existen
            //      ambos vertices, por lo que no hace nada y entrega la lista vacia.
            if(vertices.getValor1() != null && vertices.getValor2() != null){
                //  Instancia de Camino, una clase definida dentro de esta clase
                //      sirve para guardar un recorrido en una lista y su distancia
                //      en un int
                Camino caminoRecorrido = new Camino(new Lista(), 0);
                caminoRecorrido.getRecorrido().insertar(origen, 1);
                //  Llama a la funcion privada para conseguir el camino mas corto
                caminosPosiblesAux(vertices.getValor1(), destino, caminoRecorrido, resultado);
            }
        }
        
        return resultado;
    }
    
    private void caminosPosiblesAux(NodoVert vertice, Object destino, Camino caminoRecorrido, Lista resultado){
        
        //  Variable para referenciar al recorrido mas facil
        Lista recorrido = caminoRecorrido.getRecorrido();
        
        //  Variable para recorrer el arreglo
        NodoAdy aux = vertice.getPrimerAdy();
        Camino caminoAux;
        
        //  Mientras no se hayan recorrido todos los adyacentes
        while(aux!=null){
            //  Nos fijamos si ya fue visitado para evitar bucles
            if(recorrido.localizar(aux.getVertice().getElem()) < 0){
                //  Si no esta entre los vertices viistados
                
                //  Crea un clon del camino recorrido para no afectar al original
                caminoAux = caminoRecorrido.clone();
                //  Sumamos el destino al camino
                caminoAux.getRecorrido().insertar(aux.getVertice().getElem(), caminoAux.getRecorrido().longitud() + 1);
                //  Actualizamos la distancia
                caminoAux.aumentarDistancia(aux.getEtiqueta());
                
                //  Si el arco va al destino
                if(aux.getVertice().getElem().equals(destino)){
                    //  Guardamos el camino en el resultado
                    resultado.insertar(caminoAux, 1);
                } else {
                    //  Si no va al destino buscado, se llama a si misma
                    //      recursivamente para checkear este posible caminno, con el
                    //      nuevo vertice y el clon del camino, para que las llamadas
                    //      recursivas no modifique el camino original. 
                    caminosPosiblesAux(aux.getVertice(), destino, caminoAux, resultado);
                }
            }
            //  Va al siguiente adyacente
            aux = aux.getSigAdyacente();
        }
    }
    
    //  Funcion que devuleve todos los posibles caminos entre dos elementos, sin pasar por un tercer elemento
    public Lista caminosPosiblesSinVertice(Object origen, Object destino, Object verticeIgnorada){
        
        Lista resultado = new Lista();
        
        if(!this.esVacio()){
            
            //  Consigue ambos vertices
            Par<NodoVert, NodoVert> vertices = ubicarVertices(origen, destino);
            
            //  Si luego del while no fueron encontrados, entonces no existen
            //      ambos vertices, por lo que no hace nada y entrega la lista vacia.
            if(vertices.getValor1() != null && vertices.getValor2() != null){
                //  Instancia de Camino, una clase definida dentro de esta clase
                //      sirve para guardar un recorrido en una lista y su distancia
                //      en un int
                Camino caminoRecorrido = new Camino(new Lista(), 0);
                caminoRecorrido.getRecorrido().insertar(origen, 1);
                //  Llama a la funcion privada para conseguir el camino mas corto
                caminosPosiblesSinVerticeAux(vertices.getValor1(), destino, verticeIgnorada, caminoRecorrido, resultado);
            }
        }
        
        return resultado;
    }
    
    private void caminosPosiblesSinVerticeAux(NodoVert vertice, Object destino, Object verticeIgnorada, Camino caminoRecorrido, Lista resultado){
        
        //  Variable para referenciar al recorrido mas facil
        Lista recorrido = caminoRecorrido.getRecorrido();
        
        //  Variable para recorrer el arreglo
        NodoAdy aux = vertice.getPrimerAdy();
        Camino caminoAux;
        
        //  Mientras no se hayan recorrido todos los adyacentes
        while(aux!=null){
            //  Si es el vertice que hay que ignorar, entonces pasamos al siguiente
            if(!aux.getVertice().getElem().equals(verticeIgnorada)){
                //  Nos fijamos si ya fue visitado para evitar bucles
                if(recorrido.localizar(aux.getVertice().getElem()) < 0){
                    //  Si no esta entre los vertices viistados

                    //  Crea un clon del camino recorrido para no afectar al original
                    caminoAux = caminoRecorrido.clone();
                    //  Sumamos el destino al camino
                    caminoAux.getRecorrido().insertar(destino, caminoAux.getRecorrido().longitud() + 1);
                    //  Actualizamos la distancia
                    caminoAux.aumentarDistancia(aux.getEtiqueta());

                    //  Si el arco va al destino
                    if(aux.getVertice().getElem().equals(destino)){
                        //  Guardamos el camino en el resultado
                        resultado.insertar(caminoAux, 1);
                    } else {
                        //  Si no va al destino buscado, se llama a si misma
                        //      recursivamente para checkear este posible caminno, con el
                        //      nuevo vertice y el clon del camino, para que las llamadas
                        //      recursivas no modifique el camino original. 
                        caminosPosiblesAux(aux.getVertice(), destino, caminoAux, resultado);
                    }
                }
            }
            //  Va al siguiente adyacente
            aux = aux.getSigAdyacente();
        }
    }
    
    private void actualizarDistancia(Lista caminoRecorrido, int etiqueta){
        
        //  Se consigue la distancia recorrida hasta el momento
        int distanciaRecorrida = (int) caminoRecorrido.recuperar(1);   
        
        //  Luego se elimina
        caminoRecorrido.eliminar(1);
        
        //  Y se ingresa la nueva distancia total
        caminoRecorrido.insertar(distanciaRecorrida + etiqueta, 1);
        
    }
    
    private NodoVert ubicarVertice(Object vertice){
        
        NodoVert resultado = null;
        
        //  Si esta vacio, no se hace nada, devuelve null
        if(!this.esVacio()){
            //  Aux sirve para recorrer la estructura buscando el vertice correcto
            NodoVert aux = inicio;

            //  Mientras no haya recorrido toda la estructura ni haya encontrado el elemento
            while(aux != null && resultado == null){
                //  Si aux es el vertice que buscamos
                if(aux.getElem().equals(vertice)){
                    //  Lo devolvemos como resultado
                    resultado = aux;
                } else {
                    //  Si no es el vertice que buscamos, pasamos al siguiente
                    aux = aux.getSigVertice();
                }
            }
        }
        
        return resultado;
    }
    
    private Par<NodoVert, NodoVert> ubicarVertices(Object origen, Object destino){
        
        Par<NodoVert, NodoVert> resultado = new Par(null, null);

        //  Variable para recorrer la estructura
        NodoVert aux = inicio;
        //  Variable para saber cuando fueron encontrados ambos vertices y no recorrer
        //      de mas la estructura
        boolean encontrados = false;

        //  Mientras no haya recorrido toda la estructura o no haya encontrado ambos vertices
        while(aux != null && !encontrados){
            //  Si es el vertice origen que se buscaba
            if(aux.getElem().equals(origen)){
                //  Lo guardamos en su variable
                resultado.setValor1(aux);
                //  Y si ya se encontro el vertice de destino
                if(resultado.getValor2() != null){
                    //  Se pone en true encontrados
                    encontrados = true;
                }
            } else {
                //  Si no es el origen, revisamos si es el destino
                if(aux.getElem().equals(destino)){
                    //  Si lo es, entonces lo guardamos en su variable
                    resultado.setValor2(aux);
                    //  Y si tambien se encontro el vertice de origen
                    if(resultado.getValor1() != null){
                        //  Entonces se pone en true encontrados
                        encontrados = true;
                    }
                }
            }
            //  Luego seguimos al siguiente vertice
            aux = aux.getSigVertice();
        }
        
        return resultado;
    }
}
