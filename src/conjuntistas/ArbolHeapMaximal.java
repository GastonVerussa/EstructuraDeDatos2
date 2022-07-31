package conjuntistas;

import lineales.dinamicas.Cola;

public class ArbolHeapMaximal{
    
    private final int TAMANIO; 
    private Comparable[] heap;
    private int ultimo;
    
    //  Crea un arbol sin elementos.
    public ArbolHeapMaximal(){
        TAMANIO = 31;
        heap = new Comparable[TAMANIO];
        ultimo = 0;
    }
    
    //  Crea un arbol sin elementos.
    public ArbolHeapMaximal(int tamanio){
        TAMANIO = tamanio + 1;
        heap = new Comparable[TAMANIO];
        ultimo = 0;
    }
    
    //  Recibe un elemento y lo inserta en el árbol según el algoritmo que se explicará en la siguiente
    //      sección. Si la operación termina con éxito devuelve verdadero y falso en caso contrario.
    //      Nota: Los árboles heap aceptan elementos repetidos.
    public boolean insertar(Comparable elemento){
        
        boolean exito = true;
        
        if(ultimo + 1 == TAMANIO){
            //  Arbol lleno
            exito = false;
        } else {
            //  Hay espacio
            ultimo++;
            int posElemento = ultimo;
            int posPadre;
            
            boolean salir = false;
            while(!salir){
                if(posElemento == 1){
                    //  Esta en la raiz
                    salir = true;
                } else {
                    //  Sigue revisando con el padre
                    posPadre = posElemento / 2;
                    if(elemento.compareTo(heap[posPadre]) > 0){
                        //  Es mas grande que su padre, intercambian lugar
                        heap[posElemento] = heap[posPadre];
                        posElemento = posPadre;
                    } else {
                        //  Es mas chico p igual que su padre, esta en la posicion correcta
                        salir = true;
                    }
                }
            }
            
            heap[posElemento] = elemento;
        }
        
        return exito;
    }
    
    //  Elimina el elemento de la raíz (o cima del montículo) según el algoritmo que se explicará en la
    //      siguiente sección. Si la operación termina con éxito devuelve verdadero y falso si el árbol estaba
    //      vacío.
    public boolean eliminarCima(){
        
        boolean exito;
        
        if(ultimo == 0){
            exito = false;
        } else {
            exito = true;
            heap[1] = heap[ultimo];
            heap[ultimo] = null;
            ultimo--;
            hacerBajar(1);
        }
        
        return exito;
    }
    
    private void hacerBajar(int posPadre){
        
        int posHijo;
        Comparable aux = heap[posPadre];
        boolean salir = false;
        System.out.println("Elemento a eliminar " + aux.toString() + " esta en: " + posPadre);
        
        while(!salir){
            //  Calcula la posicion del hijo izquierdo
            posHijo = posPadre * 2;
            if(posHijo > ultimo){
                //  No tiene hijo izquierdo, entonces es hoja, esta bien ubicado.
                salir = true;
            } else {
                //  Si es menor o igual, entonces es un elemento que existe, tiene hijo izquierdo
                if(posHijo + 1 <= ultimo){
                    //  Si la posicion del hijo derecho es menor o igual a ultimo, tiene hijo derecho tambien
                    if(heap[posHijo + 1].compareTo(heap[posHijo]) > 0){
                        //  Si el hijo derecho es mayor al izquierdo, pasa posHijo a la posicion del derecho
                        posHijo++;
                    }
                }
                
                //  Compara el hijo mayor con el padre
                if(heap[posHijo].compareTo(aux) > 0){
                    System.out.println(heap[posHijo].toString() + " mayor que: " + aux.toString());
                    //  Si es mayor, los intercambia
                    heap[posPadre] = heap[posHijo];
                    posPadre = posHijo;
                } else {
                    //  El padre es mayor que sus hijos, esta bien ubicado.
                    salir = true;
                }
            }
            System.out.println(aux.toString() + " esta en: " + posPadre);
        }
        
        heap[posPadre] = aux;
    }
    
    //  Devuelve el elemento que está en la raíz (cima del montículo). Precondición: el árbol no está
    //      vacío (si está vacío no se puede asegurar el funcionamiento de la operación).
    public Comparable recuperarCima(){
        return heap[1];
    }
    
    // devuelve falso si hay al menos un elemento cargado en la tabla y verdadero en caso contrario.
    public boolean esVacio(){
        return ultimo == 0;
    }
    
    public void vaciar(){
        ultimo = 0;
    }
    
    @Override
    public ArbolHeapMaximal clone(){
        
        ArbolHeapMaximal resultado = new ArbolHeapMaximal(TAMANIO);
        
        for(int i = 1; i <= ultimo; i++){
            resultado.insertar(heap[i]);
        }
        
        return resultado;
    }

    @Override
    public String toString(){
        String resultado;
        
        if(this.esVacio()){
            resultado = "Arbol vacio";
        } else {
            
            resultado = "";
            
            Cola colaAuxiliar = new Cola();
            
            int pos;
            
            colaAuxiliar.poner(1);
            
            //  Mientras la cola no este vacía
            while(!colaAuxiliar.esVacia()){
                //  Consigo el frente de la cola y lo saco
                pos = (int) colaAuxiliar.obtenerFrente();
                colaAuxiliar.sacar();
                
                //  Agrega un salto de linea para hacer mas simple la lectura
                resultado += "\n ";
                //  Consigue el elemento
                Comparable elemento = heap[pos];

                //  Agrega el elemento seguido de la flecha para agregar sus hijos
                resultado += elemento.toString() + " -> ";

                //  Variables para referencias mas facilmente la posicion de los hijos
                int posIzquierdo = pos * 2;
                int posDerecho = posIzquierdo + 1;
                //  Si tiene hijo izquierdo
                if(posIzquierdo <= ultimo){
                    //  Lo suma al String
                    resultado += heap[posIzquierdo].toString();
                    colaAuxiliar.poner(posIzquierdo);
                    //  Si tiene hijo derecho
                    if(posDerecho <= ultimo){
                        //  Lo suma al String
                        resultado += ", " + heap[posDerecho].toString();
                        colaAuxiliar.poner(posDerecho);
                    }
                }
            }
        }
        return resultado;
    }

    /*
    //  Funcion privada realizada exclusivamente para la funcion toString()
    private String toStringAux(int pos){
        
        //  Caso base, no existe el elemento, devuelve el String vacio
        String resultado = "";
        
        
        //  Si la posicion es mayor a ultimo, entonces no existe
        if(pos <= ultimo){
            //  Si es menor o igual, entonces existe
            //  Agrega un salto de linea para hacer mas simple la lectura
            resultado = "\n ";
            //  Consigue el elemento
            Comparable elemento = heap[pos];

            //  Agrega el elemento seguido de la flecha para agregar sus hijos
            resultado += elemento.toString() + " -> ";
            
            //  Variables para referencias mas facilmente la posicion de los hijos
            int posIzquierdo = pos * 2;
            int posDerecho = posIzquierdo + 1;
            //  Si tiene hijo izquierdo
            if(posIzquierdo <= ultimo){
                //  Lo suma al String
                resultado += heap[posIzquierdo].toString();
                //  Si tiene hijo derecho
                if(posDerecho <= ultimo){
                    //  Lo suma al String
                    resultado += ", " + heap[posDerecho].toString();
                }
            }
            
            //  Luego agrega a los hijos al String
            resultado += toStringAux(posIzquierdo);
            resultado += toStringAux(posDerecho);
        }
        
        return resultado;
    }
    */

}
