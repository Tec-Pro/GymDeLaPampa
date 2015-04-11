/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import Modelos.DiasEjercicios;
import Modelos.Ejercicio;
import Modelos.Dia;
import java.util.LinkedList;
import org.javalite.activejdbc.Base;

/**
 *
 * @author A
 */
public class ABMDia {
    Object idDia;
    
    public boolean Alta(Dia v){
        boolean result = true;
        abrirBase();
        Base.openTransaction();
        Dia dia = Dia.create("dia", v.get("dia"), "rutina_id", v.get("rutina_id"));
        if(dia.saveIt()){
            idDia = dia.get("id");
            result = result && cargarDiasEjercicios(v.getEjercicios());
            Base.commitTransaction();
            return result;
        }
        Base.commitTransaction();
        return false;
    }
    
    public boolean Modificar(Dia v){
        abrirBase();
        Base.openTransaction();
        boolean result = true;
        result = v.saveIt() && result;
        Base.commitTransaction();
        return result;
    }
    
    public boolean Eliminar(Dia v){
        abrirBase();
        Base.openTransaction();
        int id = v.getInteger("id");
        boolean result = true;
        result = v.delete() && result;
        DiasEjercicios.delete("dia_id", id);
        return result;
    }
    
    
    public boolean cargarDiasEjercicios(LinkedList<Ejercicio> listaEjercicios){
        abrirBase();
        Base.openTransaction();
        boolean result = true;
        for(Ejercicio t: listaEjercicios){
            DiasEjercicios diasEjercicios = DiasEjercicios.create("dia_id", idDia , "ejercicio_id", t.get("id"), "series", t.getSeries(), "repeticiones", t.getRepeticiones(), "tiempo", t.getTiempo());
            result = result && diasEjercicios.saveIt();
        }
        Base.commitTransaction();
        return result;
    }
   
    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gym", "root", "root");
        }
    }
}
