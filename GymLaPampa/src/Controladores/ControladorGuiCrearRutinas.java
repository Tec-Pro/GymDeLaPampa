/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ABMs.ABMRutinas;
import Interfaces.GuiCargarDias;
import Interfaces.GuiCrearRutinas;
import Interfaces.PrincipalGui;
import Modelos.Rutina;
import Modelos.Socio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author A
 */
public class ControladorGuiCrearRutinas implements ActionListener {

    GuiCrearRutinas guiCrearRutinas;
    ABMRutinas abmRutinas;
    GuiCargarDias guiCargarDias;
    ControladorGuiCargarDias controladorGuiCargarDias;
    PrincipalGui principal;

    public ControladorGuiCrearRutinas(GuiCrearRutinas g, PrincipalGui p) throws JRException, ClassNotFoundException, SQLException {
        guiCrearRutinas = g;
        principal = p;
        abmRutinas = new ABMRutinas();
        guiCrearRutinas.setActionListener(this);
        
        guiCargarDias = new GuiCargarDias();
        controladorGuiCargarDias = new ControladorGuiCargarDias(guiCargarDias, principal);
        principal.getDesktop().add(guiCargarDias);

        guiCrearRutinas.getTablaSocios().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = guiCrearRutinas.getTablaSocios().getSelectedRow();
                if (row != -1) {
                    guiCrearRutinas.getTxtSocio().setText(guiCrearRutinas.getTablaSociosDefault().getValueAt(row, 0).toString());
                    guiCrearRutinas.getBtnCrearRutina().setEnabled(true);
                } else {
                    guiCrearRutinas.getTxtSocio().setText("");
                    guiCrearRutinas.getBtnCrearRutina().setEnabled(false);
                }
            }
        });
        
        guiCrearRutinas.getFechaInicio().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                Calendar cal = guiCrearRutinas.getFechaInicio().getCalendar();
                cal.setTime(guiCrearRutinas.getFechaInicio().getDate());
                cal.add(Calendar.MONTH, 1);
                guiCrearRutinas.getFechaFin().setCalendar(cal);
            }
        });
    }
    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gym", "root", "root");
        }
    }
    
    private Rutina obtenerDatosRutina(){
        abrirBase();
        int row = guiCrearRutinas.getTablaSocios().getSelectedRow();
        Rutina r = new Rutina();
        r.set("descrip",guiCrearRutinas.getAreaObjetivo().getText());
        r.setDate("fecha_inicio", dateToMySQLDate(guiCrearRutinas.getFechaInicio().getDate(), false));
        r.setDate("fecha_fin", dateToMySQLDate(guiCrearRutinas.getFechaFin().getDate(), false));
        r.set("socio_id",guiCrearRutinas.getTablaSociosDefault().getValueAt(row, 1));
        return r;
    }
    
    public void ActualizarListaSocios(){
        abrirBase();
        LazyList<Socio> lista = Socio.findAll();
        for(Socio s : lista){
            Object[] row = new Object[2];
            row[0] = s.get("NOMBRE")+" "+ s.get("APELLIDO");
            row[1] = s.get("ID_DATOS_PERS");
            guiCrearRutinas.getTablaSociosDefault().addRow(row);
        }
    }

     /*paraMostrar == true: retorna la fecha en formato dd/mm/yyyy (formato pantalla)
     * paraMostrar == false: retorna la fecha en formato yyyy/mm/dd (formato SQL)
     */
    public String dateToMySQLDate(Date fecha, boolean paraMostrar) {
        if (paraMostrar) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(fecha);
        } else {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(fecha);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(guiCrearRutinas.getBtnCrearRutina())){
            if(abmRutinas.Alta(obtenerDatosRutina())){
                int row = guiCrearRutinas.getTablaSocios().getSelectedRow();
                guiCrearRutinas.setVisible(false);
                guiCargarDias.setVisible(true);
                controladorGuiCargarDias.ActualizarListaEjercicios();
                controladorGuiCargarDias.socioId = guiCrearRutinas.getTablaSociosDefault().getValueAt(row, 1);
                controladorGuiCargarDias.ActualizarListaRutinas(controladorGuiCargarDias.socioId);
                guiCrearRutinas.limpiarCampos();
            }else{
                JOptionPane.showMessageDialog(guiCrearRutinas, "Ocurrio un error!", "ERROR!", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

}
