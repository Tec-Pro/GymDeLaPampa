/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import Modelos.Dia;
import Modelos.DiasEjercicios;
import Modelos.Ejercicio;
import Modelos.Rutina;

import java.util.LinkedList;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import static org.javalite.test.jspec.JSpec.a;

/**
 *
 * @author A
 */
public class ABMRutinas {

    String idRutina = null;
    int idDia;

    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gym", "root", "root");
        }
    }

    public boolean Alta(Rutina a) {
        boolean res = true;
        abrirBase();
        Base.openTransaction();
        res = a.saveIt() && res;
        idRutina = a.getString("id");
        
        Base.commitTransaction();

        return res;

    }
    public boolean AltaDias(LinkedList<Dia> dias){
        boolean res = true;
        for (Dia d : dias) {
            Dia dia = Dia.create("dia", d.get("dia"), "rutina_id", d.get("rutina_id"));
            res = res && dia.saveIt();
            idDia = dia.getInteger("id");
            res = res && cargarDiasEjercicios(d.getEjercicios());
        }
        return res;
    }

    public boolean cargarDiasEjercicios(LinkedList<Ejercicio> listaEjercicios) {
        abrirBase();
        Base.openTransaction();
        boolean result = true;
        for (Ejercicio t : listaEjercicios) {
            DiasEjercicios diasEjercicios = DiasEjercicios.create("dia_id", idDia, "ejercicio_id", t.get("id"), "series", t.getSeries(), "repeticiones", t.getRepeticiones(), "tiempo", t.getTiempo());
            result = result && diasEjercicios.saveIt();
        }
        Base.commitTransaction();
        return result;
    }

    public boolean Modificar(Rutina r, LinkedList<Dia> dias) {
        boolean res = true;
        abrirBase();
        Base.openTransaction();
        Rutina ru = Rutina.first("id = ?", idRutina);
        ru.set("fecha_inicio",r.get("fecha_inicio"));
        ru.set("fecha_fin",r.get("fecha_fin"));
        ru.set("descrip",r.get("descrip"));
        res = res && ru.saveIt();
        LazyList<Dia> diasViejos = Dia.where("rutina_id = ?", idRutina);
        for(Dia d : diasViejos){
            LazyList<DiasEjercicios> diasEjercicios = DiasEjercicios.where("dia_id = ?", d.get("id"));
            for(DiasEjercicios de : diasEjercicios){
               res = res && de.delete();
            }
            res = res && d.delete();
        }
        res = res && AltaDias(dias);
        Base.commitTransaction();
        return res;

    }

    public boolean Eliminar(Rutina a) {
        boolean res = true;
        abrirBase();
        Base.openTransaction();
        LazyList<Dia> dias = Dia.where("rutina_id = ?", a.get("id"));
        for(Dia d : dias){
            LazyList<DiasEjercicios> diasEjercicios = DiasEjercicios.where("dia_id = ?", d.get("id"));
            for(DiasEjercicios de : diasEjercicios){
               res = res && de.delete();
            }
            res = res && d.delete();
        }
        res = a.delete() && res;
        
        Base.commitTransaction();

        return res;
    }

    public String getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(String idRutina) {
        this.idRutina = idRutina;
    }

}
