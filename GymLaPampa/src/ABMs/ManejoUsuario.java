/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import java.util.Arrays;
import java.util.Calendar;
import javax.swing.JOptionPane;
import Modelos.Demo;
import Modelos.User;
import org.javalite.activejdbc.Base;

/**
 *
 * @author jacinto
 */
public class ManejoUsuario {

    public boolean crearUser() {
        boolean ret=true;
        if (User.findAll().isEmpty()) {
            User.createIt("USUARIO","admin","PASSWD","tecpro","ADMINIS",1);
            Demo demo = new Demo();
            demo = Demo.createIt("fecha", Calendar.getInstance().getTime(), "activado", false);
            JOptionPane.showMessageDialog(null, "Se a creado usuario por defecto,nombre de usuario: admin contraseña: tecpro Quedan "+demo.getString("dias")+" días de prueba");
            ret= true;

        } else {
            Base.openTransaction();
            Demo demo = (Demo) Demo.findAll().get(0);
            if (!demo.getBoolean("activado")) {

                
                String cod=JOptionPane.showInputDialog(null,"Ingrese codigo de activación","");
                
                if (cod.equals("t3cpr0")) {
                    demo.setBoolean("activado", true);
                    demo.saveIt();
                    ret= true;
                } else {
                    JOptionPane.showMessageDialog(null, "codigo incorrecto, se seguirá con la demo");
                    if (demo.getInteger("dias") == 0) {
                        int opt = JOptionPane.showConfirmDialog(null, "Demo finalizada, debe activar el programa, se cerrará el programa", "Aviso", JOptionPane.CLOSED_OPTION);
                        System.exit(0);
                        ret= false;
                    } else {
                      java.sql.Date fechaHoy= new java.sql.Date( Calendar.getInstance().getTime().getTime());
                      java.sql.Date fechaUlt= demo.getDate("fecha");
                        if (fechaUlt.before(fechaHoy) && !fechaUlt.toString().equals(fechaHoy.toString())) {
                            demo.set("dias", demo.getInteger("dias") - 1);
                            demo.set("fecha", Calendar.getInstance().getTime());
                            ret=true;
                        } else {
                            if (fechaUlt.after(fechaHoy)) {
                                int opt = JOptionPane.showConfirmDialog(null, "Demo finalizada, debe activar el programa, se cerrará el programa", "Aviso", JOptionPane.CLOSED_OPTION);
                                 demo.set("dias",0);
                                 demo.saveIt();
                                 Base.commitTransaction();
                                System.exit(0);
                                ret= false;
                            }
                        }
                    }
                                                            JOptionPane.showMessageDialog(null, "Quedan "+ demo.get("dias")+" días de prueba");

                }
            }else{
                ret= true;
            }
            demo.saveIt();
                                Base.commitTransaction();

        }
        return ret;
    }
//
//    public void modificarNombre(String nombre) {
//        User u = User.findById(1);
//        Base.openTransaction();
//        u.set("nombre", nombre);
//        u.save();
//        Base.commitTransaction();
//    }
//
//    public void modificarPass(String pass) {
//        User u = User.findById(1);
//        Base.openTransaction();
//        u.set("pass", pass);
//        u.save();
//        Base.commitTransaction();
//    }
//
//    public boolean modificarDatos(String user, String pass) {
//        User u = User.findById(1);
//        Base.openTransaction();
//        u.set("nombre", user, "pass", pass);
//        boolean ret = u.save();
//        Base.commitTransaction();
//        return ret;
//    }
//
//    public boolean login(String user, char[] pass) {
//        User u = User.first("nombre = ?", user);
//        if (u != null) {
//            char[] correct = u.getString("pass").toCharArray();
//            if (user.equals(u.getString("nombre")) && Arrays.equals(pass, correct)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
