package conjuntistas;

public class ArbolHeapMinimal{
    
    private static final int TAMANIO = 31;
    private Comparable[] heap;
    private int ultimo;
    
    //  Crea un arbol sin elementos.
    public ArbolHeapMinimal(){
        heap = new Comparable[TAMANIO];
        ultimo = 0;
    }
    
    //  Recibe un elemento y lo inserta en el árbol según el algoritmo que se explicará en la siguiente
    //      sección. Si la operación termina con éxito devuelve verdadero y falso en caso contrario.
    //      Nota: Los árboles heap aceptan elementos repetidos.
    public boolean insertar(Comparable elemento){
        
        boolean exito = true;
        
        if(ultimo == TAMANIO){
            //  Arbol lleno
            exito = false;
        } else {
            //  Hay espacio
            heap[ultimo + 1] = elemento;
            int posElemento = ultimo + 1;
            int posPadre;
            ultimo++;
            
            boolean salir = false;
            while(!salir){
                if(posElemento == 1){
                    //  Esta en al raiz
                    salir = true;
                } else {
                    //  Sigue revisando con el padre
                    posPadre = posElemento / 2;
                    if(heap[posElemento].compareTo(heap[posPadre]) < 0){
                        //  Es mas chico que su padre, intercambian lugar
                        heap[posElemento] = heap[posPadre];
                        heap[posPadre] = elemento;
                        posElemento = posPadre;
                    } else {
                        //  Es mas grande que su padre, esta en la posicion correcta
                        salir = true;
                    }
                }
            }
        }
        
        return exito;
    }
    
    //  Elimina el elemento de la raíz (o cima del montículo) según el algoritmo que se explicará en la
    //      siguiente sección. Si la operación termina con éxito devuelve verdadero y falso si el árbol estaba
    //      vacío.
    public boolean eliminarCima(){
        
        boolean exito = true;
        
        if(ultimo == 0){
            exito = false;
        } else {
            heap[1] = heap[ultimo];
            ultimo--;
            hacerBajar(1);
        }
        
        return exito;
    }
    
    private void hacerBajar(int posPadre){
        
        int posHijo;
        Comparable aux = heap[posPadre];
        boolean salir = false;
        
        while(!salir){
            //  Calcula la posicion del hijo izquierdo
            posHijo = posPadre * 2;
            if(posHijo > ultimo){
                //  No tiene hijo izquierdo, entonces es hoja, esta bien ubicado.
                salir = true;
            } else {
                //  Si es menor igual, entonces es un elemento que existe, tiene hijo izquierdo
                if(posHijo < ultimo){
                    //  Si es menor, tiene hijo derecho tambien
                    if(heap[posHijo + 1].compareTo(heap[posHijo]) < 0){
                        //  Si el hijo derecho es menor al izquierdo, pasa posHijo a la posiciond el derecho
                        posHijo++;
                    }
                }
                
                //  Compara el hijo menor con el padre
                if(heap[posHijo].compareTo(aux) < 0){
                    //  Si es menor, los intercambia
                    heap[posPadre] = heap[posHijo];
                    heap[posHijo] = aux;
                    posPadre = posHijo;
                } else {
                    //  El padre es menor que sus hijos, esta bien ubicado.
                    salir = true;
                }
            }
        }
    }
    
    //  Devuelve el elemento que está en la raíz (cima del montículo). Precondición: el árbol no está
    //      vacío (si está vacío no se puede asegurar el funcionamiento de la operación).
    public Comparable recuperarCima(){
        return heap[0];
    }
    
    // devuelve falso si hay al menos un elemento cargado en la tabla y verdadero en caso contrario.
    public boolean esVacio(){
        return ultimo == 0;
    }
}
