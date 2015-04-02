/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.util.LinkedList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author A
 */
public class Rutina extends Model{
    
    private LinkedList<Ejercicio> articulos;
    
    public Rutina(){
        articulos = null;
    }
    
    public Rutina(LinkedList<Ejercicio> l){
        articulos = l;
    }
    
    public LinkedList<Ejercicio> getEjercicios() {
        return articulos;
    }

    public void setEjercicios(LinkedList<Ejercicio> articulos) {
        this.articulos = articulos;
    }
}
