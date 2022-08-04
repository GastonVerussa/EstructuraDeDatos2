package grafos;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;
import propositoEspecifico.Conjunto;

public class GrafoEtiquetadoInt {
    
    //  Variable para mantener puntero al inicio
    private NodoVertInt inicio;
    
    //  Constructor, lo crea vacio
    public GrafoEtiquetadoInt(){
        this.inicio = null;
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
            inicio = new NodoVertInt(vertice, null);
        } else {
            //  En caso de que no este vacio
            //  Variable para recorrer el arreglo
            NodoVertInt aux = inicio.getSigVertice();
            
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
            if(exito) inicio = new NodoVertInt(vertice, inicio);
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
            if(inicio.getElem().equals(vertice)){
                //  Si el elemento buscado es el primero, lo eliminamos simplemente seteando 
                //      el nuevo inicio como el siguiente al viejo inicio
                inicio = inicio.getSigVertice();
                //  Pasamos el exito a true
                exito = true;
            } else {
                //  Si no es el primer vertice, entonces creamos dos variables
                //  Aux sirve para recorrer la estructura buscando el vertice correcto
                NodoVertInt aux = inicio.getSigVertice();
                //  Y padreAux va guardando el padre de aux que servira mas adelante
                NodoVertInt padreAux = inicio;
                
                //  Mientras no haya recorrido toda la estructura ni haya encontrado el elemento
                while(aux != null && !exito){
                    //  Si aux es el vertice que buscamos
                    if(aux.getElem().equals(vertice)){
                        //  Entonces lo eliminamos simplemente conectando el enlace del padre
                        //      al siguiente del aux
                        padreAux.setSigVert(aux.getSigVertice());
                        //  Seteamos el exito en true, saliendo del while
                        exito = true;
                    } else {
                        //  Si no es el vertice que buscamos
                        //  Pasamos al siguiente, manteniendo padreAux como el nuevo padre
                        padreAux = aux;
                        aux = aux.getSigVertice();
                    }
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
        if(!this.esVacio()){
            //  Variables para guardar el lugar de los vertices
            NodoVertInt verticeOrigen = null;
            NodoVertInt verticeDestino = null;
            
            //  Variable para recorrer la estructura
            NodoVertInt aux = inicio;
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
            if(encontrados){
                //  Si fueron encontrados los vertices, entonces 
                //      crea una variable para recorrer los adyacentes al vertice origen
                NodoAdyInt auxAdy = verticeOrigen.getPrimerAdy();
                boolean existeArco = false;
                
                while(auxAdy != null && !existeArco){
                    if(auxAdy.getVertice().equals(verticeOrigen)){
                        existeArco = true;
                    } else {
                        auxAdy = auxAdy.getSigAdyacente();
                    }
                }
                
                if(!existeArco){
                    verticeOrigen.setPrimerAdy(new NodoAdyInt(verticeDestino, etiqueta, verticeOrigen.getPrimerAdy()));
                    verticeDestino.setPrimerAdy(new NodoAdyInt(verticeOrigen, etiqueta, verticeDestino.getPrimerAdy()));
                    exito = true;
                }
            }
        }
        
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
            //  Variables para guardar el lugar de los vertices
            NodoVertInt verticeOrigen = null;
            NodoVertInt verticeDestino = null;
            
            //  Variable para recorrer la estructura
            NodoVertInt aux = inicio;
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
            if(encontrados){
                
                //  Variable que se usa para recorrer la estructura de adyacentes a ambos vertices
                NodoAdyInt auxAdy = verticeOrigen.getPrimerAdy();
                
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
                        NodoAdyInt padreAuxAdy = auxAdy;
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
                        NodoAdyInt padreAuxAdy = auxAdy;
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
        
        //  Variable para guardar si existe
        boolean existe = false;
        
        //  Si esta vacio entonces no existe
        if(!this.esVacio()){
            //  Si no esta vacio, se lo debe buscar
            //  Variable para recorrer el arreglo
            NodoVertInt aux = inicio;
            
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
        
        return existe;
    }
    
    //  Dados dos elementos de TipoVertice (origen y destino), devuelve verdadero 
    //      si existe un arco en la estructura que los une y falso en caso contrario.
    public boolean existeArco(Object origen, Object destino){
        
        //  Variable que guarda si existe
        boolean existe = false;
        
        //  Si esta vacio claramente no existen los vertices, devuelve el existe falso
        if(!this.esVacio()){
            //  Variables para guardar el lugar de los vertices
            NodoVertInt verticeOrigen = null;
            NodoVertInt verticeDestino = null;
            
            //  Variable para recorrer la estructura
            NodoVertInt aux = inicio;
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
            if(encontrados){
                //  Variable que se usa para recorrer la estructura de adyacentes al origen
                NodoAdyInt auxAdy = verticeOrigen.getPrimerAdy();
                
                //  Si el vertice de origen no tiene adyacentes, no existe el arco
                if(auxAdy != null){
                    //  Si si tiene, se busca el arco entre sus adyacentes
                    
                    //  Mientras queden adyacentes por revisar y no se haya encontrado el
                    //      nodo adyacente buscado
                    while(auxAdy != null && !existe){
                        //  Si es el adyacente buscado
                        if(auxAdy.getVertice().equals(verticeDestino)){
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
            //  Variables para guardar el lugar de los vertices
            NodoVertInt verticeOrigen = null;
            NodoVertInt verticeDestino = null;
            
            //  Variable para recorrer la estructura
            NodoVertInt aux = inicio;
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
            if(encontrados){
                //  Variable que se usa para recorrer la estructura de adyacentes al origen
                NodoAdyInt auxAdy = verticeOrigen.getPrimerAdy();
                
                //  Si el vertice de origen no tiene adyacentes, no existe el arco
                if(auxAdy != null){
                    //  Si si tiene, se busca el arco entre sus adyacentes
                    
                    //  Mientras queden adyacentes por revisar y no se haya encontrado el
                    //      nodo adyacente buscado
                    while(auxAdy != null && etiqueta == -1){
                        //  Si es el adyacente buscado
                        if(auxAdy.getVertice().equals(verticeDestino)){
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
            //  Variables para guardar el lugar de los vertices
            NodoVertInt verticeOrigen = null;
            NodoVertInt verticeDestino = null;
            
            //  Variable para recorrer la estructura
            NodoVertInt aux = inicio;
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
            if(encontrados){
                //  De ser encontrados, crea una variable conjunto que guardara que
                //      vertices ya fueron visitados para evitar bucles.
                Conjunto verticesRecorridos = new Conjunto();
                //  Llama a la funcion auxiliar para saber si existe un camino
                existe = existeCaminoAux(verticeOrigen, verticeDestino, verticesRecorridos);
            }
        }
        
        return existe;
    }
        
    //  Funcion privada auxiliar para la funcion de existe camino. Recorre recursivamente todos sus
    //      adyacentes que no hayan sido visitados
    private boolean existeCaminoAux(NodoVertInt vertice, NodoVertInt destino, Conjunto verticesRecorridos){
        
        //  Variable para guardar el exito
        boolean existe = false;
        
        //  Auxiliar para recorrer los nodos adyacentes del nodo vertice
        NodoAdyInt aux = vertice.getPrimerAdy();
        
        //  Si no tiene adyacentes devolvemos false, que no existe en este camino
        if(aux != null){
            //  Se agrega el vertice en el que estamos para no visitarlo dos veces
            verticesRecorridos.agregar(vertice);
            
            do{
                //  Variable para referenciar mas facilmente el vertice del adyacente actual
                NodoVertInt auxVert = aux.getVertice();
                
                //  Si es el que buscamos, entonces existe un camino
                if(auxVert.equals(destino)){
                    existe = true;
                } else {
                    //  Si no lo es, nos fijamos si ya fue visitado para evitar bucles
                    if(!verticesRecorridos.pertenece(auxVert)){
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
            NodoVertInt verticeOrigen = null;
            NodoVertInt verticeDestino = null;
            
            //  Variable para recorrer la estructura
            NodoVertInt aux = inicio;
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
    private Lista caminoMasCortoAux(NodoVertInt vertice, Object destino, Lista caminoRecorrido){
        
        //  Variable resultado para guardar el camino correcto mas corto, o lista vacia
        //      de no encontrarlo
        Lista resultado = new Lista();
        
        //  Se agrega al camino recorrido
        caminoRecorrido.insertar(vertice.getElem(), caminoRecorrido.longitud() + 1);
        
        //  Variable para recorrer el arreglo
        NodoAdyInt aux = vertice.getPrimerAdy();
        
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
            NodoVertInt verticeOrigen = null;
            NodoVertInt verticeDestino = null;
            
            //  Variable para recorrer la estructura
            NodoVertInt aux = inicio;
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
    
    private Lista caminoMasLargoAux(NodoVertInt vertice, Object destino, Lista caminoRecorrido){
        
        //  Variable resultado para guardar el camino correcto mas largo, o lista vacia
        //      de no encontrarlo
        Lista resultado = new Lista();
        
        //  Se agrega al camino recorrido
        caminoRecorrido.insertar(vertice.getElem(), caminoRecorrido.longitud() + 1);
        
        //  Variable para recorrer el arreglo
        NodoAdyInt aux = vertice.getPrimerAdy();
        
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
    
    //  Devuelve una lista con los vértices del grafo visitados según el recorrido 
    //      en profundidad explicado en la sección anterior.
    public Lista listarEnProfundidad(){
        
        Lista resultado = new Lista();
        
        NodoVertInt aux = inicio;
        
        while(aux != null){
            if(resultado.localizar(aux.getElem()) < 0){
                profundidadDesde(aux, resultado);
            }
            aux = aux.getSigVertice();
        }
        
        return resultado;
    }

    //  Funcion privada auxiliar para listarEnProfundidad
    private void profundidadDesde(NodoVertInt vertice, Lista visitados){
        
        visitados.insertar(vertice.getElem(), visitados.longitud() + 1);
        
        NodoAdyInt aux = vertice.getPrimerAdy();
        
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
        
        NodoVertInt aux = inicio;
        
        while(aux != null){
            if(resultado.localizar(aux.getElem()) < 0){
                anchuraDesde(aux, resultado);
            }
            aux = aux.getSigVertice();
        }
        
        return resultado;
    }
        
    //  Funcion privada auxiliar para listarEnAnchura
    private void anchuraDesde(NodoVertInt vertice, Lista visitados){
        
        Cola colaAux = new Cola();
        
        visitados.insertar(vertice.getElem(), visitados.longitud() + 1);
        
        colaAux.poner(vertice);
        NodoVertInt auxVert;
        NodoAdyInt auxAdy;
        
        while(!colaAux.esVacia()){
            auxVert = (NodoVertInt) colaAux.obtenerFrente();
            colaAux.sacar();
            auxAdy = auxVert.getPrimerAdy();
            while(auxAdy != null){
                if(visitados.localizar(auxAdy.getVertice().getElem()) < 0){
                    visitados.insertar(auxAdy.getVertice().getElem(), visitados.longitud() + 1);
                    colaAux.poner(auxAdy.getVertice());
                }
            }
        }
        
    }
    
    public boolean esVacio(){
        return this.inicio == null;
    }
           
    //  Genera y devuelve un grafo que es equivalente (igual estructura y contenido 
    //      de los nodos) al original. 
    @Override
    public GrafoEtiquetadoInt clone(){
        
        //  Nuevo grafo que se pasara como resultado final
        GrafoEtiquetadoInt resultado = new GrafoEtiquetadoInt();
        
        //  Nodo vertice auxiliar que usaremos para recorrer la estructura
        NodoVertInt aux = inicio;
        
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
        NodoAdyInt auxAdy;
        
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
            NodoVertInt aux = inicio;

            while(aux != null){
                
                resultado += "\n " + aux.getElem().toString();
                
                NodoAdyInt auxAdy = aux.getPrimerAdy();
                
                while(auxAdy != null){
                    resultado += " { " + auxAdy.getVertice().getElem().toString() + ", " + auxAdy.getEtiqueta() + " } -";
                    auxAdy = auxAdy.getSigAdyacente();
                }
            }
        }
        
        return resultado;
    }
}
