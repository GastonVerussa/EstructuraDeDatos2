package propositoEspecifico;

// EN PROGRESO: NO USAR         EN PROGRESO: NO USAR         EN PROGRESO: NO USAR
// EN PROGRESO: NO USAR         EN PROGRESO: NO USAR         EN PROGRESO: NO USAR
// EN PROGRESO: NO USAR         EN PROGRESO: NO USAR         EN PROGRESO: NO USAR
// EN PROGRESO: NO USAR         EN PROGRESO: NO USAR         EN PROGRESO: NO USAR
// EN PROGRESO: NO USAR         EN PROGRESO: NO USAR         EN PROGRESO: NO USAR
// EN PROGRESO: NO USAR         EN PROGRESO: NO USAR         EN PROGRESO: NO USAR
// EN PROGRESO: NO USAR         EN PROGRESO: NO USAR         EN PROGRESO: NO USAR
public class ColaPrioridadMaximal {
    
    private final int TAMANIO;
    private final CeldaCP[] heap;
    private int ultimo;
    private int ordenLlegada;
    
    public ColaPrioridadMaximal(){
        TAMANIO = 31;
        heap = new CeldaCP[TAMANIO];
        ultimo = 0;
        ordenLlegada = 0;
    }
    
    public ColaPrioridadMaximal(int tamanio){
        TAMANIO = tamanio + 1;
        heap = new CeldaCP[TAMANIO];
        ultimo = 0;
        ordenLlegada = 0;
    }
    
    //  Recibe un elemento y lo inserta en el árbol según el algoritmo que se explicará en la siguiente
    //      sección. Si la operación termina con éxito devuelve verdadero y falso en caso contrario.
    //      Nota: Los árboles heap aceptan elementos repetidos.
    public boolean insertar(Object elemento, Comparable prioridad){
        
        boolean exito = true;
        
        if(ultimo == TAMANIO){
            //  Arbol lleno
            exito = false;
        } else {
            //  Hay espacio
            ultimo++;
            ordenLlegada++;
            int posElemento = ultimo;
            int posPadre;
            
            boolean salir = false;
            while(!salir){
                if(posElemento == 1){
                    //  Esta en al raiz
                    salir = true;
                } else {
                    //  Sigue revisando con el padre
                    posPadre = posElemento / 2;
                    //  Compara las prioridades
                    int comparar = prioridad.compareTo(heap[posPadre].getPrioridad());
                    
                    if(comparar > 0){
                        // Si tiene prioridad mas alta, como es maximal intercambian lugar
                        heap[posElemento] = heap[posPadre];
                        posElemento = posPadre;
                    } else {
                        //  Si tiene prioridad mas baja, esta en la posicion correcta
                        //  Y si tienen la misma prioridad, tambien esta en la posicion correcta
                        //      ya que claramente su orden de llegada sera mas tardio que el
                        //      del elemento que ya se encuentra en la estructura
                        salir = true;
                    }
                }
            }
            
            heap[posElemento] = new CeldaCP(elemento, prioridad, ordenLlegada);
            ordenLlegada++;
        }
        
        return exito;
    }
    
    //  Elimina el elemento de la raíz (o cima del montículo) según el algoritmo que se explicará en la
    //      siguiente sección. Si la operación termina con éxito devuelve verdadero y falso si el árbol estaba
    //      vacío.
    public boolean eliminarCima(){
        
        boolean exito = true;
        
        //  Si arbol vacio
        if(ultimo == 0){
            exito = false;
        } else {
            //  Si no es vacio, sube al tope el ultimo elemento
            heap[1] = heap[ultimo];
            //  Resta la cantidad de elementos total
            ultimo--;
            //  Y llama a la funcion auxiliar para acomodar el arbol
            hacerBajar(1);
        }
        
        return exito;
    }
    
    public Object obtenerFrente(){
        return heap[0].getElemento();
    }
    
    public boolean esVacia(){
        return ultimo == 0;
    }
    
    public void vaciar(){
        ultimo = 0;
    }
    
    @Override
    public ColaPrioridadMaximal clone(){
        
        ColaPrioridadMaximal resultado = new ColaPrioridadMaximal(TAMANIO);
        
        for(int i = 1; i <= ultimo; i++){
            resultado.insertar(heap[i].getElemento(), heap[i].getPrioridad());
        }
        
        return resultado;
    }
    
    private void hacerBajar(int posPadre){
        
        int posHijo;
        CeldaCP aux = heap[posPadre];
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
                    if(comparar(heap[posHijo + 1], heap[posHijo]) > 0){
                        //  Si el hijo derecho es mayor al izquierdo, pasa posHijo a la posiciond el derecho
                        posHijo++;
                    }
                }
                
                //  Compara el hijo mayor con el padre
                if(comparar(heap[posHijo], aux) > 0){
                    //  Si es mayor, los intercambia
                    heap[posPadre] = heap[posHijo];
                    posPadre = posHijo;
                } else {
                    //  El padre es mayor que sus hijos, esta bien ubicado.
                    salir = true;
                }
            }
            
            heap[posPadre] = aux;
        }
    }
    
    private int comparar(CeldaCP celda1, CeldaCP celda2){
        
        //  Compara la prioridad de ambos
        int resultado = celda1.getPrioridad().compareTo(celda2.getPrioridad());
        
        if(resultado == 0){
            //  Como es un arbol maximal en cuanto a prioridad, pero deseamos que el orden
            //      de llegada se mantenga de menor a mayor, restamos a celda2 el orden de 
            //      celda1.
            resultado = celda2.getOrdenLlegada() - celda1.getOrdenLlegada();
        }
        
        return resultado;
    }
}
