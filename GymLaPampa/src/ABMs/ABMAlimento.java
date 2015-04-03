/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import Modelos.Alimento;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author alan
 */
public class ABMAlimento {

    public Object idAlta;

    public Alimento getAlimento(Integer id) {
        return Alimento.first("id = ?", id);
    }

    public boolean existeAlimento(Integer id) {
        return (Alimento.first("id = ?", id) != null);
    }

    public Alimento getAlimento(String nombre) {
        return Alimento.first("nombre = ?", nombre);
    }

    public boolean existeAlimento(String nombre) {
        return (Alimento.first("nombre = ?", nombre) != null);
    }

    public boolean alta(Alimento s) {
        if (!existeAlimento(s.getString("nombre"))) {
            Base.openTransaction();
            Alimento nuevo = Alimento.createIt(
                    "nombre", s.getString("nombre"),
                    "pc", s.getString("pc"),
                    "agua", s.getString("agua"),
                    "prot", s.getString("prot"),
                    "hc", s.getString("hc"),
                    "grasa", s.getString("grasa"),
                    "satur", s.getString("satur"),
                    "mono", s.getString("mono"),
                    "poli", s.getString("poli"),
                    "colesterol", s.getString("colesterol"),
                    "fibra", s.getString("fibra"),
                    "sodio", s.getString("sodio"),
                    "potasio", s.getString("potasio"),
                    "magnesio", s.getString("magnesio"),
                    "calcio", s.getString("calcio"),
                    "fosforo", s.getString("fosforo"),
                    "hierro", s.getString("hierro"),
                    "indiceglucemico", s.getString("indiceglucemico")
            );
            idAlta = nuevo.get("id");
            Base.commitTransaction();
            return true;
        } else {
            return false;
        }
    }

    public boolean baja(Integer id) {
        Alimento viejo = Alimento.first("id = ?", id);
        if (viejo != null) {
            Base.openTransaction();  
            viejo.delete();
            Base.commitTransaction();
            return true;
        }
        return false;
    }

    public boolean modificar(Alimento s) {
        Alimento viejo = Alimento.first("id = ?", s.getString("id"));
        if (viejo != null) {
            Base.openTransaction();
            viejo.set(
                    "nombre", s.getString("nombre"),
                    "pc", s.getString("pc"),
                    "agua", s.getString("agua"),
                    "prot", s.getString("prot"),
                    "hc", s.getString("hc"),
                    "grasa", s.getString("grasa"),
                    "satur", s.getString("satur"),
                    "mono", s.getString("mono"),
                    "poli", s.getString("poli"),
                    "colesterol", s.getString("colesterol"),
                    "fibra", s.getString("fibra"),
                    "sodio", s.getString("sodio"),
                    "potasio", s.getString("potasio"),
                    "magnesio", s.getString("magnesio"),
                    "calcio", s.getString("calcio"),
                    "fosforo", s.getString("fosforo"),
                    "hierro", s.getString("hierro"),
                    "indiceglucemico", s.getString("indiceglucemico")
            );        
            viejo.saveIt();
            Base.commitTransaction();
            return true;
        }
        return false;
    }

}
