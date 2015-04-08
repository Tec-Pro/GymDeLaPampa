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
public class Dieta extends Model{
    
    private LinkedList<Alimento> alimentos;
    
    public Dieta(){
        alimentos = null;
    }
    
    public Dieta(LinkedList<Alimento> l){
        alimentos = l;
    }
    
    public LinkedList<Alimento> getAlimentos() {
        return alimentos;
    }

    public void setAlimentos(LinkedList<Alimento> alimentos) {
        this.alimentos = alimentos;
    }
}
