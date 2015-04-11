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
public class Dia extends Model{
    
     private LinkedList<Ejercicio> ejercicios;
    
    public Dia(){
        ejercicios = null;
    }
    
    public Dia(LinkedList<Ejercicio> l){
        ejercicios = l;
    }
    
    public LinkedList<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(LinkedList<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }
    
}
