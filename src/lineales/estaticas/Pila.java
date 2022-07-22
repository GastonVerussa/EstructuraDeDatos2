package lineales.estaticas;

public class Pila {
    
    //Variables para determinar atributos de la pila
    private final Object[] arreglo;
    private int tope;
    
    //Constante para determinar el tamaño máximo de las pilas
    private static final int TAMANIO = 20;
    
    // Crea y devuelve la pila vacía
    public Pila(){
        arreglo = new Object[TAMANIO];
        tope = -1;
    }
    
    // Pone el elemento nuevoElem en el tope de la pila. Devuelve verdadero si el elemento se pudo apilar y
    //      falso en caso contrario.
    public boolean apilar(Object nuevoElem){
        
        boolean exito;
        
        if(tope + 1  >= TAMANIO){
            //Error: Pila llena
            exito = false;
        } else {
            // pone el elemento en el tope de la pila e incrementa el tope
            tope++;
            arreglo[tope] = nuevoElem;
            exito = true;
        }
        
        return exito;
    }
    
    // Saca el elemento del tope de la pila. Devuelve verdadero si la pila no estaba vacía al momento de
    //      desapilar (es decir que se pudo desapilar) y falso en caso contrario.
    public boolean desapilar(){
        
        boolean exito;
        
        if(this.esVacia()){
            //Error: Pila vacía
            exito = false;
        } else {
            // saca el elemento del tope y decrementa tope
            arreglo[tope] = null;
            tope--;
            exito = true;
        }
        
        return exito;
    }
    
    // Devuelve el elemento en el tope de la pila. Precondición: la pila no está vacía.
    public Object obtenerTope(){
        return arreglo[tope];
    }
    
    // Devuelve verdadero si la pila no tiene elementos y falso en caso contrario.
    public boolean esVacia(){
        //Retorn si la pila está vacía
        return tope == -1;
    }
    
    // Saca todos los elementos de la pila.
    public void vaciar(){
        //Mientras la pila no sea vacía, la va desapilando.
        while(!this.esVacia()){
            this.desapilar();
        }
    }
    
    // Devuelve una copia exacta de los datos en la estructura original, y respetando el orden de los mismos,
    //      en otra estructura del mismo tipo
    @Override
    public Pila clone(){
        
        // Nueva pila que se retornara al final
        Pila resultado = new Pila();
        
        // Recorre el arreglo de manera inversa apilando los elementos uno por uno
        for(int i = tope; i >= 0; i--)
            resultado.apilar(this.arreglo[i]);
        
        return resultado;
    }
    
    // Devuelve una cadena de caracteres formada por todos los elementos de la pila para poder mostrarla
    //      por pantalla. Es recomendable utilizar este método únicamente en la etapa de prueba y 
    //      luego comentar el código.
    @Override
    public String toString(){
        
        // String que se retornara al final
        String resultado;
        
        if(this.esVacia()){
            // Si la pila está vacía, devuelve este mensaje
            resultado = "Pila vacía";
        } else {
            // Si no está vacía, abre el corchete
            resultado = "[";
            
            // For con Auxiliar que comienza en el tope y decrece hasta 0
            for(int aux = tope; aux >= 0; aux--){
                // Agrega el elemento al String y decrementa aux para ir al siguiente
                resultado += arreglo[aux].toString();
                aux--;
                // Verifica para no agregar ", " al ultimo elemento
                if(aux != -1)resultado += ", ";
            }
            // Cierra el corchete
            resultado += "]";
        }
        
        return resultado;
    }    
}
