/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import Modelos.Arancel;
import Modelos.Categoria;
import Modelos.Dato;
import Modelos.Socioarancel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author alan
 */
public class ABMAranceles {
    public Object idAlta;
    public Arancel getArancel(Arancel s) {
        return Arancel.first("nombre = ?", s.get("nombre"));
    }
    public boolean findArancel(Arancel s) {
        return (Arancel.first("nombre = ?", s.get("nombre")) != null);
    }
    
    public boolean alta(Arancel s) {
        if (!findArancel(s)) {
            Base.openTransaction();
            Arancel nuevo = Arancel.create("nombre",s.get("nombre"), "precio", s.get("precio"), "fecha", s.get("fecha"), "activo", s.get("activo"),"categoria", s.get("categoria"), "dias", s.getInteger("dias"));
            nuevo.saveIt();
            Dato.createIt("descripcion","MEMBRESIA "+nuevo.getString("nombre"),"ingreso_egreso","ingreso","categoria_id",Categoria.findFirst("nombre = ?", nuevo.getString("categoria")).getId());
            idAlta= nuevo.get("id");
            Base.commitTransaction();
            return true;
        } else {
            return false;
        }
    }

    public boolean baja(Arancel s) {
        Arancel viejo = Arancel.first("id = ?", s.getString("id"));
        if (viejo != null) {
            Base.openTransaction();
                LazyList<Socioarancel> listAran = Socioarancel.where("id_arancel = ?", viejo.get("id"));
                Iterator<Socioarancel> it = listAran.iterator();
                while(it.hasNext()){
                    Socioarancel soar = it.next();
                    soar.delete();
                    
                }
                viejo.delete();
                
                
            Base.commitTransaction();
            return true;
        }
        return false;
    }

    public boolean modificar(Arancel s) {
        Arancel viejo = Arancel.first("id = ?", s.getString("id"));
        if (viejo != null) {
            Base.openTransaction();
                        Dato datoViejo= Dato.findFirst("descripcion =? ", "MEMBRESIA "+viejo.getString("nombre"));

            viejo.set("nombre", s.get("nombre"),"fecha", s.get("fecha"), "precio", s.get("precio"), "categoria", s.get("categoria"), "dias", s.getInteger("dias"));
                        viejo.saveIt();
            datoViejo.setString("descripcion", "MEMBRESIA "+viejo.getString("nombre"));
            datoViejo.saveIt();
            Base.commitTransaction();
            return true;
        }
        return false;
    }
    
}
