/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import Modelos.Ejercicio;
import org.javalite.activejdbc.Base;

/**
 *
 * @author A
 */
public class ABMEjercicios {
    
    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gym", "root", "root");
        }
    }

    public boolean Alta(Ejercicio a) {
        boolean res = true;
        abrirBase();
        Base.openTransaction();
        res = a.saveIt() && res;
        Base.commitTransaction();
         
        return res;

    }

    public boolean Modificar(Ejercicio a) {
        boolean res = true;
        abrirBase();
        Base.openTransaction();
        res = a.saveIt() && res;
        Base.commitTransaction();
         
        return res;

    }

    public boolean Eliminar(Ejercicio a) {
        boolean res = true;
        abrirBase();
        Base.openTransaction();
        res = a.delete() && res;
        Base.commitTransaction();
         
        return res;
    }
}
