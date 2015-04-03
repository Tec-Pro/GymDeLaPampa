/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import Modelos.Ejercicio;
import Modelos.RutinasEjercicios;
import Modelos.Rutina;
import java.util.LinkedList;
import org.javalite.activejdbc.Base;

/**
 *
 * @author A
 */
public class ABMRutinas {
    
    Object idRutina;
    
    public boolean Alta(Rutina v){
        boolean result = true;
        abrirBase();
        Base.openTransaction();
        Rutina rutina = Rutina.create("socio_id", v.get("socio_id"), "fecha_inicio", v.get("fecha_inicio"), "fecha_fin", v.get("fecha_fin"), "dia", v.get("dia")/*"objetivo", v.get("objetivo")*/);
        if(rutina.saveIt()){
            idRutina = rutina.get("id");
            result = result && cargarRutinasEjercicios(v.getEjercicios());
            Base.commitTransaction();
            return result;
        }
        Base.commitTransaction();
        return false;
    }
    
    public boolean Modificar(Rutina v){
        abrirBase();
        Base.openTransaction();
        boolean result = true;
        result = v.saveIt() && result;
        Base.commitTransaction();
        return result;
    }
    
    public boolean Eliminar(Rutina v){
        abrirBase();
        Base.openTransaction();
        int id = v.getInteger("id");
        boolean result = true;
        result = v.delete() && result;
        RutinasEjercicios.delete("id_rutina", id);
        return result;
    }
    
    
    public boolean cargarRutinasEjercicios(LinkedList<Ejercicio> listaEjercicios){
        abrirBase();
        Base.openTransaction();
        boolean result = true;
        for(Ejercicio t: listaEjercicios){
            RutinasEjercicios rutinasEjercicios = RutinasEjercicios.create("rutina_id", idRutina , "ejercicio_id", t.get("id"), "series", t.getSeries(), "repeticiones", t.getRepeticiones(), "tiempo", t.getTiempo());
            result = result && rutinasEjercicios.saveIt();
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
