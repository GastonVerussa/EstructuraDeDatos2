/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests.propositoEspecifico;

/**
 *
 * @author gasto
 */
public class Prueba {
        
    private final String clave;
    private final int dato;

    public Prueba(String clave, int dato){
        this.clave = clave;
        this.dato = dato;
    }
    
    public String getClave(){
        return this.clave;
    }
    
    public int getDato(){
        return this.dato;
    }
    
    @Override
    public String toString(){
        return "{" + this.clave + ", " + this.dato + "}";
    }
}
