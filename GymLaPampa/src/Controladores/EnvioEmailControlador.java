/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelos.Arancel;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.swing.JOptionPane;
import Modelos.Email;
import Modelos.Envio;
import Modelos.Socio;
import Modelos.Socioarancel;
import java.util.Date;
import java.util.Iterator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author nico
 */
public class EnvioEmailControlador {

    public static String bd = "gym";
    public static String login = "root";
    public static String password = "root";
    public String url = "jdbc:mysql://localhost/" + bd;
    public String urlcero = "jdbc:mysql://localhost/";
    public Connection conn = null;
    static String mail = "";
    static String passwo = "";
    public static int conectadoMySQL = 0;
    private static Email emailModel;

    public void conectarMySQL() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                conectadoMySQL = 1;
            }
        } catch (SQLException ex) {
            conectadoMySQL = 0;
        } catch (ClassNotFoundException ex) {
            conectadoMySQL = 0;
        }
    }

    public boolean guardarDatos(String email, String pass) throws SQLException {
        emailModel = new Email();
        emailModel.set("email", email, "password", pass);
        Base.openTransaction();
        Email.deleteAll();
        boolean guardado = emailModel.saveIt();
        Base.commitTransaction();
        if (guardado) {
            JOptionPane.showMessageDialog(null, "Los datos han sido Guardados Correctamente!");
        } else {
            JOptionPane.showMessageDialog(null, "No se han realizado los cambios, ocurrió un error");
        }
        return guardado;
    }

    public boolean enviarMail(String email, String passw) throws MessagingException {
        boolean ret = false;
        Base.openTransaction();
        LazyList<Email> emailsModel = Email.findAll();
        Base.commitTransaction();
        if (!emailsModel.isEmpty()) {
            emailModel = emailsModel.get(0);
            this.mail = emailModel.getString("email");
            String contraEncrip = emailModel.getString("password");
            char arrayD[] = contraEncrip.toCharArray();
            for (int i = 0; i < arrayD.length; i++) {
                arrayD[i] = (char) (arrayD[i] - (char) 5);
            }
            this.passwo = String.valueOf(arrayD);
            try {
                // se obtiene el objeto Session. La configuración es para
                // una cuenta de gmail.
                Properties props = new Properties();
                if (mail.contains("gmail")) {
                    props.put("mail.smtp.host", "smtp.gmail.com");
                } else {
                    props.put("mail.smtp.host", "smtp.live.com");
                }
                props.setProperty("mail.smtp.starttls.enable", "true");
                props.setProperty("mail.smtp.port", "587");
                props.setProperty("mail.smtp.user", mail);
                props.setProperty("mail.smtp.auth", "true");
                Session session = Session.getDefaultInstance(props, null);
                // session.setDebug(true);
                // Se compone la parte del texto
                BodyPart texto = new MimeBodyPart();
                texto.setText("Se adjunta backup mensual. ");
                // Se compone el adjunto con la imagen
                BodyPart adjunto = new MimeBodyPart();
                String dir = (new File(System.getProperty("user.dir")).getAbsolutePath());
                adjunto.setDataHandler(
                        new DataHandler(new FileDataSource(dir + "/backupEmail.sql")));
                adjunto.setFileName("backUpGymPitbulls.sql");
                // Una MultiParte para agrupar texto e imagen.
                MimeMultipart multiParte = new MimeMultipart();
                multiParte.addBodyPart(texto);
                multiParte.addBodyPart(adjunto);
                // Se compone el correo, dando to, from, subject y el
                // contenido.
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(mail));
                message.addRecipient(
                        Message.RecipientType.TO,
                        new InternetAddress(mail));
                message.setSubject("Backup mensual Gimnasio Personalizado Pitbulls");
                message.setContent(multiParte);
                // Se envia el correo.
                Transport t = session.getTransport("smtp");
                try {
                    t.connect(mail, passwo);
                } catch (javax.mail.AuthenticationFailedException ex) {
                    JOptionPane.showMessageDialog(null, "¡Datos incorrectos, no se ha establecido la conexión!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    t.sendMessage(message, message.getAllRecipients());
                    ret = t.isConnected();
                } catch (javax.mail.MessagingException ex) {
                    ret = false;
                }
                if (ret) {
                    Envio enviarModel = new Envio();
                    Base.openTransaction();
                    Envio.deleteAll();
                    enviarModel.set("fecha", convertirFechaString());
                    enviarModel.setBoolean("enviado", true);
                    enviarModel.saveIt();
                    Base.commitTransaction();
                }
                t.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay cofiguración de email guardada, por favor cargue los datos para habilitar el backup mensual");
        }
        return ret;
    }

    public boolean probarConexion(String email, String passw) throws MessagingException {
        Properties props = new Properties();
        boolean ret = false;
        if (email.contains("gmail")) {
            props.put("mail.smtp.host", "smtp.gmail.com");
        } else {
            props.put("mail.smtp.host", "smtp.live.com");
        }
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", email);
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, null);
        // Se compone el correo, dando to, from, subject y el
        // contenido.
        MimeMessage message = new MimeMessage(session);
        // Se envia el correo.
        Transport t = session.getTransport("smtp");
        try {
            t.connect(email, passw);
            ret = true;
        } catch (javax.mail.AuthenticationFailedException ex) {
            ret = false;
        }
        return ret;
    }

    
    public static String convertirFechaString() {
        String fechaString;
        Calendar fechaCalendar = Calendar.getInstance();
        String anio = Integer.toString(fechaCalendar.get(Calendar.YEAR));
        String mes = Integer.toString(fechaCalendar.get(Calendar.MONTH) + 1);
        String dia = Integer.toString(fechaCalendar.get(Calendar.DAY_OF_MONTH));
        fechaString = anio + "/" + mes + "/" + dia;
        return fechaString;
    }

    //dietaRutina es para decir que es, así se pone esa palabra
    public static boolean enviarMailManualDieta(String fileName, Integer idSocio, String dietaRutina) {
        boolean ret = false;
        Base.openTransaction();
        LazyList<Email> emailsModel = Email.findAll();
        Base.commitTransaction();
        if (!emailsModel.isEmpty()) {
            emailModel = emailsModel.get(0);
            mail = emailModel.getString("email");
            String contraEncrip = emailModel.getString("password");
            char arrayD[] = contraEncrip.toCharArray();
            for (int i = 0; i < arrayD.length; i++) {
                arrayD[i] = (char) (arrayD[i] - (char) 5);
            }
            passwo = String.valueOf(arrayD);
            try {
                Socio s= Socio.findFirst("ID_DATOS_PERS = ?", idSocio);
                String para= s.getString("mail");
                LazyList l = Socioarancel.where("id_socio = ?", idSocio);
            Iterator<Socioarancel> i = l.iterator();
            String act="";
            while(i.hasNext()){
                Socioarancel soar = i.next();
                Arancel ar=Arancel.findById(soar.get("id_arancel"));
                act=act.concat("-"+ar.getString("nombre")+" ($"+ar.getString("precio")+")\n");
                
            }
                String datosPersonales="Además te recordamos que \n"
                        + " tu membresia vence el: "+ dateToMySQLDate(s.getDate("FECHA_PROX_PAGO"),true)+"\n"
                        + "las actividades que realizas son: \n"
                        + act
                        +"Tu cuenta corriente es de : $"+s.getString("cuenta_corriente")+"\n";
                // se obtiene el objeto Session. La configuración es para
                // una cuenta de gmail.
                Properties props = new Properties();
                if (mail.contains("gmail")) {
                    props.put("mail.smtp.host", "smtp.gmail.com");
                } else {
                    props.put("mail.smtp.host", "smtp.live.com");
                }
                props.setProperty("mail.smtp.starttls.enable", "true");
                props.setProperty("mail.smtp.port", "587");
                props.setProperty("mail.smtp.user", mail);
                props.setProperty("mail.smtp.auth", "true");

                Session session = Session.getDefaultInstance(props, null);
            // session.setDebug(true);

                // Se compone la parte del texto
                BodyPart texto = new MimeBodyPart();
                texto.setText("Te enviamos la "+dietaRutina+" personalizada. \n "
                        +datosPersonales
                        + "Saludos, \n Gimnasio personalizado Pitbull's");

                // Se compone el adjunto con la imagen
                BodyPart adjunto = new MimeBodyPart();
                adjunto.setDataHandler(
                        new DataHandler(new FileDataSource(fileName)));
                adjunto.setFileName( dietaRutina+".pdf");

                // Una MultiParte para agrupar texto e imagen.
                MimeMultipart multiParte = new MimeMultipart();
                multiParte.addBodyPart(texto);
                multiParte.addBodyPart(adjunto);

                // Se compone el correo, dando to, from, subject y el
                // contenido.
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(mail));
                message.addRecipient(
                        Message.RecipientType.TO,
                        new InternetAddress(para));
                message.setSubject(dietaRutina+" personalizada de Gimnasio Personalizado Pitbulls");
                message.setContent(multiParte);

                // Se envia el correo.
                Transport t = session.getTransport("smtp");
                try {
                    t.connect(mail, passwo);
                    ret = true;
                } catch (javax.mail.AuthenticationFailedException ex) {
                    JOptionPane.showMessageDialog(null, "¡Datos incorrectos, no se ha establecido la conexión!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                t.sendMessage(message, message.getAllRecipients());
                t.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay cofiguración de email guardada, por favor cargue los datos para habilitar el backup mensual");
        }
        return ret;
    }
    
        /*va true si se quiere usar para mostrarla por pantalla es decir 12/12/2014 y false si va 
     para la base de datos, es decir 2014/12/12*/
    public static String dateToMySQLDate(Date fecha, boolean paraMostrar) {
        if (fecha != null) {
            if (paraMostrar) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                return sdf.format(fecha);
            } else {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(fecha);
            }
        } else {
            return "";
        }

    }
}
