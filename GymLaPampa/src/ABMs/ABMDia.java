/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import Modelos.DiasEjercicios;
import Modelos.Ejercicio;
import Modelos.Dia;
import Modelos.Rutina;
import java.util.LinkedList;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author A
 */
public class ABMDia {
    Object idDia;
    
    private boolean diaExiste(Dia d){
        abrirBase();
        LazyList<Dia> dias = Dia.where("rutina_id = ?",d.get("rutina_id"));
        if(!dias.isEmpty()){
        for(Dia di : dias){
            if(di.getString("dia").equals(d.getString("dia"))){
                return true;
            }
        }
        }
        return false;
    }
    
    public boolean Alta(Dia v){
        boolean result = true;
        abrirBase();
        Base.openTransaction();
        if(diaExiste(v)){
            return false;
        }
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
