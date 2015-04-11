/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import Modelos.Rutina;

import java.util.LinkedList;
import org.javalite.activejdbc.Base;

/**
 *
 * @author A
 */
public class ABMRutinas {
    
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
        Base.commitTransaction();
         
        return res;

    }

    public boolean Modificar(Rutina a) {
        boolean res = true;
        abrirBase();
        Base.openTransaction();
        res = a.saveIt() && res;
        Base.commitTransaction();
         
        return res;

    }

    public boolean Eliminar(Rutina a) {
        boolean res = true;
        abrirBase();
        Base.openTransaction();
        res = a.delete() && res;
        Base.commitTransaction();
         
        return res;
    }
}
