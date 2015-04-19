/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import Modelos.Envio;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author nico
 */
public class EmailThread extends Thread {

    public void run() {
        
        EnvioEmailControlador enviar = new EnvioEmailControlador();
         Base.openTransaction();
        LazyList<Envio> list = Envio.findAll();
        Base.commitTransaction();
        if (!list.isEmpty()) {
             Base.openTransaction();
            Envio fechaUltEnvio = (Envio) Envio.findAll().get(0);
             Base.commitTransaction();
            Date fechaEnviado = fechaUltEnvio.getDate("fecha");
            Calendar fechaActualMenosMes = Calendar.getInstance();
            fechaActualMenosMes.add(Calendar.MONTH, -1);
            java.sql.Date sqlFecha = new java.sql.Date(fechaActualMenosMes.getTime().getTime());
            java.sql.Date.valueOf(sqlFecha.toString());
            if (sqlFecha.toString().equals(fechaEnviado.toString()) || sqlFecha.after(fechaEnviado)) {
                Modulo moduloBackUp = new Modulo();
                moduloBackUp.CrearBackupSilencioso();
                try {
                    enviar.enviarMail("", "");
                } catch (MessagingException ex) {
                    Logger.getLogger(EmailThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                
            }
        } else {
            Modulo moduloBackUp = new Modulo();
            moduloBackUp.CrearBackupSilencioso();
            try {
                enviar.enviarMail("", "");
            } catch (MessagingException ex) {
                Logger.getLogger(EmailThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }



 
}
