package grafos;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;
import propositoEspecifico.Conjunto;
import utiles.Par;
import utiles.Resultado;

public class GrafoEtiquetado {
    
    //  Variable para mantener puntero al inicio
    private NodoVert inicio;
    
    //  Constructor, lo crea vacio
    public GrafoEtiquetado(){
        this.inicio = null;
    }
    
    //  Dado un elemento de TipoVertice se lo agrega a la estructura controlando 
    //      que no se inserten vértices repetidos. Si puede realizar la inserción
    //      devuelve verdadero, en caso contrario devuelve falso.
    public boolean insertarVertice(Object vertice){
        
        //  Variable para guardar el exito
        boolean exito;
        
        //  Recupera el nodo con el elemento vertice
        NodoVert nodoVertice = ubicarVertice(vertice);

        //  Si ya existe el nodo
        if(nodoVertice != null){
            //  No acepta repetidos, falla
            exito = false;
        } else {
            //  Si no existe, lo crea y lo setea como nuevo inicio, enlazado al anterior
            exito = true;
            inicio = new NodoVert(vertice, inicio);
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
                NodoVert aux = inicio.getSigVertice();
                //  Y padreAux va guardando el padre de aux que servira mas adelante
                NodoVert padreAux = inicio;
                
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
    public boolean insertarArco(Object origen, Object destino, Object etiqueta){
        
        //  Variable que guarda el exito
        boolean exito = false;
        
        //  Recupera ambos vertices
        Par<NodoVert, NodoVert> vertices = ubicarVertices(origen, destino);
            
        NodoVert verticeOrigen = vertices.getValor1();
        NodoVert verticeDestino = vertices.getValor2();
        
        //  Si ambos vertices existen
        if(verticeOrigen != null && verticeDestino != null){
            //  Crea una variable para recorrer los adyacentes al vertice origen
            NodoAdy auxAdy = verticeOrigen.getPrimerAdy();
            boolean existeArco = false;

            //  Verifica que no exista ya el arco
            while(auxAdy != null && !existeArco){
                if(auxAdy.getVertice().equals(verticeOrigen)){
                    existeArco = true;
                } else {
                    auxAdy = auxAdy.getSigAdyacente();
                }
            }

            //  Si ya existe, no lo crea
            if(!existeArco){
                verticeOrigen.setPrimerAdy(new NodoAdy(verticeDestino, etiqueta, verticeOrigen.getPrimerAdy()));
                verticeDestino.setPrimerAdy(new NodoAdy(verticeOrigen, etiqueta, verticeDestino.getPrimerAdy()));
                exito = true;
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
        
        //  Recupera ambos vertices
        Par<NodoVert, NodoVert> vertices = ubicarVertices(origen, destino);
            
        NodoVert verticeOrigen = vertices.getValor1();
        NodoVert verticeDestino = vertices.getValor2();
        
        //  Si ambos vertices existen
        if(verticeOrigen != null && verticeDestino != null){
                
            //  Variable que se usa para recorrer la estructura de adyacentes a ambos vertices
            NodoAdy auxAdy = verticeOrigen.getPrimerAdy();

            //  Variable de control para saber si hubo exito en encontrar 
            //      y borrar el arco desde origen a destino
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
        
        return exito;
    }

    //  Dado un elemento, devuelve verdadero si está en la estructura y falso en caso contrario.
    public boolean existeVertice(Object vertice){ 
        return ubicarVertice(vertice) != null;
    }
    
    //  Dados dos elementos de TipoVertice (origen y destino), devuelve verdadero 
    //      si existe un arco en la estructura que los une y falso en caso contrario.
    public boolean existeArco(Object origen, Object destino){
        
        //  Variable que guarda si existe
        boolean existe = false;
        
        //  Recupera ambos vertices
        Par<NodoVert, NodoVert> vertices = ubicarVertices(origen, destino);
            
        NodoVert verticeOrigen = vertices.getValor1();
        NodoVert verticeDestino = vertices.getValor2();
        
        //  Si ambos vertices existen
        if(verticeOrigen != null && verticeDestino != null){
            
            //  Variable que se usa para recorrer la estructura de adyacentes al origen
            NodoAdy auxAdy = verticeOrigen.getPrimerAdy();

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
        
        return existe;
    } 

    //  Dados dos elementos de TipoVertice (origen y destino), devuelve el valor 
    //      de la etiqueta del arco en la estructura que los une. De no existir
    //      tal arco, devuleve -1.
    public Object recuperarEtiqueta(Object origen, Object destino){
        
        //  Variable que guarda si existe
        Object etiqueta = null;
        
        //  Recupera ambos vertices
        Par<NodoVert, NodoVert> vertices = ubicarVertices(origen, destino);
            
        NodoVert verticeOrigen = vertices.getValor1();
        NodoVert verticeDestino = vertices.getValor2();
        
        //  Si ambos vertices existen
        if(verticeOrigen != null && verticeDestino != null){
            
            //  Variable que se usa para recorrer la estructura de adyacentes al origen
            NodoAdy auxAdy = verticeOrigen.getPrimerAdy();

            //  Si el vertice de origen no tiene adyacentes, no existe el arco
            if(auxAdy != null){
                //  Si si tiene, se busca el arco entre sus adyacentes

                //  Mientras queden adyacentes por revisar y no se haya encontrado el
                //      nodo adyacente buscado
                while(auxAdy != null && etiqueta == null){
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
        
        return etiqueta;
    } 
    
    //  Dados dos elementos de TipoVertice (origen y destino), devuelve verdadero si existe al menos
    //      un camino que permite llegar del vértice origen al vértice destino y falso en caso contrario.
    public boolean existeCamino(Object origen, Object destino){
        
        //  Variable que guarda si existe
        boolean existe = false;
        
        //  Recupera ambos vertices
        Par<NodoVert, NodoVert> vertices = ubicarVertices(origen, destino);
            
        NodoVert verticeOrigen = vertices.getValor1();
        NodoVert verticeDestino = vertices.getValor2();
        
        //  Si ambos vertices existen
        if(verticeOrigen != null && verticeDestino != null){
            //  De ser encontrados, crea una variable conjunto que guardara que
            //      vertices ya fueron visitados para evitar bucles.
            Conjunto verticesRecorridos = new Conjunto();
            //  Llama a la funcion auxiliar para saber si existe un camino
            existe = existeCaminoAux(verticeOrigen, verticeDestino, verticesRecorridos);
        }
       
        return existe;
    }
        
    //  Funcion privada auxiliar para la funcion de existe camino. Recorre recursivamente todos sus
    //      adyacentes que no hayan sido visitados
    private boolean existeCaminoAux(NodoVert vertice, NodoVert destino, Conjunto verticesRecorridos){
        
        //  Variable para guardar el exito
        boolean existe = false;
        
        //  Auxiliar para recorrer los nodos adyacentes del nodo vertice
        NodoAdy aux = vertice.getPrimerAdy();
        
        //  Si no tiene adyacentes devolvemos false, que no existe en este camino
        if(aux != null){
            //  Se agrega el vertice en el que estamos para no visitarlo dos veces
            verticesRecorridos.agregar(vertice);
            
            do{
                //  Variable para referenciar mas facilmente el vertice del adyacente actual
                NodoVert auxVert = aux.getVertice();
                
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
                        existe = existeCaminoAux(auxVert, destino, verticesRecorridos);
                    }
                }
                
                //  Luego avanza al siguiente adyacente al auxiliar
                aux = aux.getSigAdyacente();
                //  Repite mientras no se haya revisado todos los camino o haya
                //      sido encontrado el camino.
            } while(aux != null && !existe);
            
            // Se elimina antes de terminar para no afectar otros llamados
            verticesRecorridos.quitar(vertice);
        }
        
        return existe;
    } 
    
    //  Dados dos elementos de TipoVertice (origen y destino), devuelve un camino 
    //      (lista de vértices) que indique el camino que pasa por menos vértices 
    //      que permite llegar del vértice origen al vérticedestino. Si hay más 
    //      de un camino con igual cantidad de vértices, devuelve cualquiera de ellos. Si
    //      alguno de los vértices no existe o no hay camino posible entre ellos devuelve la lista vacía.
    public Lista caminoMasCorto(Object origen, Object destino){
        
        Resultado<Lista> resultado = new Resultado(new Lista());
        
        //  Recupera ambos vertices
        Par<NodoVert, NodoVert> vertices = ubicarVertices(origen, destino);
            
        NodoVert verticeOrigen = vertices.getValor1();
        NodoVert verticeDestino = vertices.getValor2();
        
        //  Si ambos vertices existen
        if(verticeOrigen != null && verticeDestino != null){
            Lista caminoRecorrido = new Lista();
            caminoMasCortoAux(verticeOrigen, destino, caminoRecorrido, resultado);
        }
        
        return resultado.get();
    }
    
    //  Funcion privada auxiliar de caminoMasCorto, devuleve el camino mas corto
    private void caminoMasCortoAux(NodoVert vertice, Object destino, Lista caminoRecorrido, Resultado<Lista> resultado){
        
        //  Si ya se encontro un camino mas corto al que se esta investigando, no se hace nada
        if(resultado.get().esVacia() || caminoRecorrido.longitud() < resultado.get().longitud()){
            
            //  Si no se encontro ningun camino, o el camino actual es menor al encontrado, se sigue buscando
            
            //  Si el vertice ya fue visitado, no hace nada
            if(caminoRecorrido.localizar(vertice.getElem()) < 0){

                //  Agregamos el vertice al camino
                caminoRecorrido.insertar(vertice.getElem(), caminoRecorrido.longitud() + 1);

                if(vertice.getElem().equals(destino)){
                    //  Se encontro un camino posible

                    //  Si todavia no se encontro un camino anterior, o el nuevo camino es mas corto que el encontrado
                    if(resultado.get().esVacia() || caminoRecorrido.longitud() < resultado.get().longitud()){
                        
                        //  Guardamos un clon como resultado
                        resultado.set(caminoRecorrido.clone());
                    }
                } else {
                    //  Variable para recorrer el arreglo
                    NodoAdy aux = vertice.getPrimerAdy();

                    //  Mientras no se hayan recorrido todos los adyacentes
                    while(aux!=null){

                        //  Agregamos la etiqueta al final del camino
                        caminoRecorrido.insertar(aux.getEtiqueta(), caminoRecorrido.longitud() + 1);
                                                                    
                        //  Llamamos recursivamente a la funcion con el vertice
                        caminoMasCortoAux(aux.getVertice(), destino, caminoRecorrido, resultado);

                        //  Eliminamos la etiqueta
                        caminoRecorrido.eliminar(caminoRecorrido.longitud());
                        
                        aux = aux.getSigAdyacente();
                    }
                }

                //  Eliminamos el vertice del camino
                caminoRecorrido.eliminar(caminoRecorrido.longitud());
            }
        }
    }
  
    //  Dados dos elementos de TipoVertice (origen y destino), devuelve un camino 
    //      (lista de vértices) que indique el camino que pasa por más vértices (sin ciclos) 
    //      que permite llegar del vértice origen al vértice destino. Si hay más de un camino 
    //      con igual cantidad de vértices, devuelve cualquiera de ellos. Si alguno de los 
    //      vértices no existe o no hay camino posible entre ellos devuelve la lista vacía.
    public Lista caminoMasLargo(Object origen, Object destino){
        
        Resultado<Lista> resultado = new Resultado(new Lista());
        
        //  Recupera ambos vertices
        Par<NodoVert, NodoVert> vertices = ubicarVertices(origen, destino);
            
        NodoVert verticeOrigen = vertices.getValor1();
        NodoVert verticeDestino = vertices.getValor2();
        
        //  Si ambos vertices existen
        if(verticeOrigen != null && verticeDestino != null){
            Lista caminoRecorrido = new Lista();
            caminoMasLargoAux(verticeOrigen, destino, caminoRecorrido, resultado);
        }
        
        return resultado.get();
    }
    
    private void caminoMasLargoAux(NodoVert vertice, Object destino, Lista caminoRecorrido, Resultado<Lista> resultado){
        
        //  Si ya fue visitado, no hace nada
        if(caminoRecorrido.localizar(vertice.getElem()) < 0){
            
            caminoRecorrido.insertar(vertice.getElem(), caminoRecorrido.longitud() + 1);
            
            if(vertice.getElem().equals(destino)){
                //  Se encontro un camino posible
                
                //  Si todavia no se encontro un camino anterior, o el nuevo camino es mas largo que el encontrado
                if(resultado.get().esVacia() || caminoRecorrido.longitud() > resultado.get().longitud()){
                    //  Guardamos un clon del camino encontrado en el resultado
                    resultado.set(caminoRecorrido.clone());
                }
            } else {
                //  Variable para recorrer el arreglo
                NodoAdy aux = vertice.getPrimerAdy();

                //  Mientras no se hayan recorrido todos los adyacentes
                while(aux!=null){

                    //  Agregamos la etiqueta al final del camino
                    caminoRecorrido.insertar(aux.getEtiqueta(), caminoRecorrido.longitud() + 1);

                    //  Llamamos recursivamente a la funcion con el vertice
                    caminoMasLargoAux(aux.getVertice(), destino, caminoRecorrido, resultado);

                    //  Eliminamos la etiqueta
                    caminoRecorrido.eliminar(caminoRecorrido.longitud());

                    aux = aux.getSigAdyacente();
                }
            }
            
            //  Eliminamos el camino
            caminoRecorrido.eliminar(caminoRecorrido.longitud());
        }
    }
    
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
    
    public Lista obtenerAdyacentes(Object vertice){
        
        Lista resultado = new Lista();
        
        NodoVert nodoVertice = ubicarVertice(vertice);
        
        if(nodoVertice != null){
            
            NodoAdy aux = nodoVertice.getPrimerAdy();
            
            while(aux != null){
                
                resultado.insertar(aux.getVertice().getElem(), 1);
                aux = aux.getSigAdyacente();
            }
        }
        
        return resultado;
    }
    
    //  Funcion que devuleve una lista con todos los caminos posibles de una locacion a otra
    public Lista caminosPosibles(Object origen, Object destino){
        
        Lista resultado = new Lista();

        //  Recupera ambos vertices
        Par<NodoVert, NodoVert> vertices = ubicarVertices(origen, destino);
            
        NodoVert verticeOrigen = vertices.getValor1();
        NodoVert verticeDestino = vertices.getValor2();
        
        //  Si ambos vertices existen
        if(verticeOrigen != null && verticeDestino != null){
            //  Lista con el camino recorrido hasta el momento
            Lista caminoRecorrido = new Lista();
            //  Llama a la funcion privada para conseguir el camino mas corto
            caminosPosiblesAux(verticeOrigen, destino, caminoRecorrido, resultado);
        }
        
        return resultado;
    }
    
    private void caminosPosiblesAux(NodoVert vertice, Object destino, Lista caminoRecorrido, Lista resultado){

        if(caminoRecorrido.localizar(vertice.getElem()) < 0){
            //  Si el vertice todavia no fue visitado en el camino
            
            //  Agregamos el vertice al camino
            caminoRecorrido.insertar(vertice.getElem(), caminoRecorrido.longitud() + 1);

            if(vertice.getElem().equals(destino)){
                //  Se encontro un camino posible

                //  Se agrega un clon del camino a los caminos posibles
                resultado.insertar(caminoRecorrido.clone(), 1);
            } else {
                //  Variable para recorrer el arreglo
                NodoAdy aux = vertice.getPrimerAdy();

                //  Mientras no se hayan recorrido todos los adyacentes
                while(aux!=null){

                    //  Agregamos la etiqueta al final del camino
                    caminoRecorrido.insertar(aux.getEtiqueta(), caminoRecorrido.longitud() + 1);

                    //  Llamamos recursivamente a la funcion con el vertice
                    caminosPosiblesAux(aux.getVertice(), destino, caminoRecorrido, resultado);

                    //  Eliminamos la etiqueta
                    caminoRecorrido.eliminar(caminoRecorrido.longitud());

                    aux = aux.getSigAdyacente();
                }
            }

            //  Eliminamos el vertice del camino
            caminoRecorrido.eliminar(caminoRecorrido.longitud());
        }
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
            }
            //  Revisamos si es el destino
            if(aux.getElem().equals(destino)){
                //  Si lo es, entonces lo guardamos en su variable
                resultado.setValor2(aux);
                //  Y si tambien se encontro el vertice de origen
                if(resultado.getValor1() != null){
                    //  Entonces se pone en true encontrados
                    encontrados = true;
                }
            }
            //  Luego seguimos al siguiente vertice
            aux = aux.getSigVertice();
        }
        
        return resultado;
    }
    
    public void vaciar(){
        this.inicio = null;
    }
           
    //  Genera y devuelve un grafo que es equivalente (igual estructura y contenido 
    //      de los nodos) al original. 
    @Override
    public GrafoEtiquetado clone(){
        
        //  Nuevo grafo que se pasara como resultado final
        GrafoEtiquetado resultado = new GrafoEtiquetado();
        
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
                    resultado += " { " + auxAdy.getVertice().getElem().toString() + ", " + auxAdy.getEtiqueta().toString() + " } -";
                    auxAdy = auxAdy.getSigAdyacente();
                }
                
                aux = aux.getSigVertice();
            }
        }
        
        return resultado;
    }
}
