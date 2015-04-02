/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ABMs.ABMRutinas;
import Interfaces.GuiCrearRutina;
import Interfaces.PrincipalGui;
import Modelos.Ejercicio;
import Modelos.Socio;
import Modelos.Rutina;
import Utiles.Triple;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author A
 */
public class ControladorGuiCrearRutina implements ActionListener, CellEditorListener{
    GuiCrearRutina guiCrearRutina;
    ABMRutinas abmRutinas;
    PrincipalGui aplicacionGUI;

    public ControladorGuiCrearRutina(GuiCrearRutina cv, PrincipalGui ap) throws JRException, ClassNotFoundException, SQLException {
        aplicacionGUI = ap;
        guiCrearRutina = cv;
        guiCrearRutina.setActionListener(this);
        abmRutinas = new ABMRutinas();

        guiCrearRutina.getTxtBuscarSocio().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarSocio();
            }
        });
        guiCrearRutina.getTxtBuscarEjercicio().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarEjercicio();
            }
        });

        guiCrearRutina.getTablaSocios().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaSocioMouseClicked(evt);
            }
        });

        guiCrearRutina.getTablaEjercicios().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaEjerciciosMouseClicked(evt);
            }
        });
        
        guiCrearRutina.getFechaInicio().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                Calendar cal = guiCrearRutina.getFechaInicio().getCalendar();
                cal.setTime(guiCrearRutina.getFechaInicio().getDate());
                cal.add(Calendar.MONTH, 1);
                guiCrearRutina.getFechaFin().setCalendar(cal);
            }
        });
    }

    private void buscarSocio() {
        abrirBase();
        guiCrearRutina.getTablaSociosDefault().setRowCount(0);
        List<Socio> resul;
        resul = Socio.where("APELLIDO like ? or NOMBRE like ?", "%" + guiCrearRutina.getTxtBuscarSocio().getText() + "%", "%" + guiCrearRutina.getTxtBuscarSocio().getText() + "%");
        for(Socio e : resul){
            Object[] row = new Object[2];
            row[0] = e.getString("NOMBRE")+" "+e.getString("APELLIDO");
            row[1] = e.getString("DNI");
            guiCrearRutina.getTablaSociosDefault().addRow(row);
        }
    }
    
    private void buscarEjercicio(){
        abrirBase();
        guiCrearRutina.getTablaEjerciciosDefault().setRowCount(0);
        List<Ejercicio> resul;
        resul = Ejercicio.where("ejercicio like ? or grupo like ?", "%" + guiCrearRutina.getTxtBuscarEjercicio().getText() + "%", "%" + guiCrearRutina.getTxtBuscarEjercicio().getText() + "%");
        for(Ejercicio e : resul){
            Object[] row = new Object[3];
            row[0] = e.getString("id");
            row[1] = e.getString("grupo");
            row[2] = e.getString("ejercicio");
            guiCrearRutina.getTablaEjerciciosDefault().addRow(row);
        }
    }

    private void tablaSocioMouseClicked(MouseEvent evt) {
        int r = guiCrearRutina.getTablaSocios().getSelectedRow();
        guiCrearRutina.getTxtSocio().setText(String.valueOf(guiCrearRutina.getTablaSociosDefault().getValueAt(r, 0)));
        guiCrearRutina.getTxtIdSocio().setText(String.valueOf(guiCrearRutina.getTablaSociosDefault().getValueAt(r, 1)));
    }

    private void tablaEjerciciosMouseClicked(MouseEvent evt) {
        abrirBase();
        int r = guiCrearRutina.getTablaEjercicios().getSelectedRow();
        Ejercicio ejercicio = Ejercicio.first("id = ?", String.valueOf(guiCrearRutina.getTablaEjerciciosDefault().getValueAt(r, 0)));
        if(ejercicio.getString("grupo").equals("AEROBICO")){
            if(!ejercicioYaCargado(String.valueOf(guiCrearRutina.getTablaEjerciciosDefault().getValueAt(r, 0)),false)){
                Object row[] = new Object[3];
                row[0] = ejercicio.getString("id");
                row[1] = ejercicio.getString("ejercicio");
                row[2] = "";
                guiCrearRutina.getTablaAerobicoDefault().addRow(row);
            }else{
                JOptionPane.showMessageDialog(aplicacionGUI, "Ejercicio ya cargado!");
            }
        }else{
            if(!ejercicioYaCargado(String.valueOf(guiCrearRutina.getTablaEjerciciosDefault().getValueAt(r, 0)),true)){
                Object row[] = new Object[5];
                row[0] = ejercicio.getString("id");
                row[1] = ejercicio.getString("grupo");
                row[2] = ejercicio.getString("ejercicio");
                row[3] = "3";
                row[4] = "15";
                guiCrearRutina.getTablaMusculacionDefault().addRow(row);
            }else{
                JOptionPane.showMessageDialog(aplicacionGUI, "Ejercicio ya cargado!");
            }
        }    
    }

    private boolean ejercicioYaCargado(String id, boolean musculacion) {
        if(musculacion){
           for (int i = 0; i < guiCrearRutina.getTablaMusculacion().getRowCount(); i++) {
                if (String.valueOf(guiCrearRutina.getTablaMusculacionDefault().getValueAt(i, 0)).equals(id)) {
                    return true;
                }
            } 
        }else{
           for (int i = 0; i < guiCrearRutina.getTablaAerobico().getRowCount(); i++) {
                if (String.valueOf(guiCrearRutina.getTablaAerobicoDefault().getValueAt(i, 0)).equals(id)) {
                    return true;
                }
            } 
        }
        
        return false;
    }

    public Rutina ObtenerDatosRutina() {
        abrirBase();
        LinkedList<Ejercicio> listaEjercicios = new LinkedList();
        for (int i = 0; i < guiCrearRutina.getTablaMusculacionDefault().getRowCount(); i++) {
            Ejercicio ar = Ejercicio.first("id = ?", guiCrearRutina.getTablaMusculacionDefault().getValueAt(i, 0));
            ar.setRepeticiones(Integer.valueOf(String.valueOf(guiCrearRutina.getTablaMusculacionDefault().getValueAt(i, 4))));
            ar.setSeries(Integer.valueOf(String.valueOf(guiCrearRutina.getTablaMusculacionDefault().getValueAt(i, 3))));
            listaEjercicios.add(ar);
        }
        for (int i = 0; i < guiCrearRutina.getTablaAerobicoDefault().getRowCount(); i++) {
            Ejercicio ar = Ejercicio.first("id = ?", guiCrearRutina.getTablaAerobicoDefault().getValueAt(i, 0));
            ar.setTiempo(String.valueOf(guiCrearRutina.getTablaAerobicoDefault().getValueAt(i, 2)));
            listaEjercicios.add(ar);
        }
        Rutina v = new Rutina(listaEjercicios);
        v.set("socio_id", guiCrearRutina.getTxtIdSocio().getText());
        v.set("fecha_inicio", dateToMySQLDate(guiCrearRutina.getFechaInicio().getDate(), false));
        v.set("fecha_fin", dateToMySQLDate(guiCrearRutina.getFechaFin().getDate(), false));
        v.set("dia", guiCrearRutina.getBoxDia().getSelectedItem());
        //v.set("objetivo", guiCrearRutina.getAreaObjetivo.getText());
        return v;
    }

    public boolean DatosOK() {
        if ((!guiCrearRutina.getTxtSocio().getText().equals("")) 
                && (!guiCrearRutina.getBoxDia().getSelectedItem().equals("Seleccionar"))
                && ((!(guiCrearRutina.getTablaAerobicoDefault().getRowCount() == 0))
                || (!(guiCrearRutina.getTablaMusculacionDefault().getRowCount() == 0)))
                && (!guiCrearRutina.getFechaInicio().equals(""))
                && (!guiCrearRutina.getFechaFin().equals(""))) {
            return true;
        }
        return false;
    }

   /* public void actualizarMonto() {
        BigDecimal importe;
        BigDecimal total = new BigDecimal(0);
        for (int i = 0; i < guiCrearRutina.getTablaRutina().getRowCount(); i++) {
            BigDecimal precio_unit = new BigDecimal(String.valueOf(guiCrearRutina.getTablaRutina().getValueAt(i, 3)));
            importe = ((BigDecimal) guiCrearRutina.getTablaRutina().getValueAt(i, 2)).multiply(precio_unit).setScale(2, RoundingMode.CEILING);
            guiCrearRutina.getTablaRutinaDefault().setValueAt(importe, i, 4);
            total = total.add((BigDecimal) guiCrearRutina.getTablaRutinaDefault().getValueAt(i, 4)).setScale(2, RoundingMode.CEILING);
        }
        guiCrearRutina.getTotalTxt().setText(total.toString());

    }*/

    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gym", "root", "root");
        }
    }
    
    public void ActualizarListaEjercicios(){
        guiCrearRutina.getTablaEjerciciosDefault().setRowCount(0);
        abrirBase();
        LazyList<Ejercicio> ejercicios = Ejercicio.findAll();
        for(Ejercicio a: ejercicios){
            Object[] row = new Object[3];
            row[0] = a.getString("id");
            row[1] = a.getString("grupo");
            row[2] = a.getString("ejercicio");
            guiCrearRutina.getTablaEjerciciosDefault().addRow(row);
        }
         
    }
    
    public void ActualizarListaSocios(){
        guiCrearRutina.getTablaSociosDefault().setRowCount(0);
        abrirBase();
        LazyList<Socio> socios = Socio.findAll();
        for(Socio a: socios){
            Object[] row = new Object[2];
            row[0] = a.getString("NOMBRE")+" "+a.getString("APELLIDO");
            row[1] = a.getString("DNI");
            guiCrearRutina.getTablaSociosDefault().addRow(row);
        }
         
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       /* if (e.getSource().equals(guiCrearRutina.getQuitarBtn())) {
            if (guiCrearRutina.getTablaRutina().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                guiCrearRutina.getTablaRutinaDefault().removeRow(guiCrearRutina.getTablaRutina().getSelectedRow());
                actualizarMonto();
            }
        }*/
        if (e.getSource().equals(guiCrearRutina.getBtnRegistrarRutina())) {
            if (DatosOK()) {
                if (abmRutinas.Alta(ObtenerDatosRutina())) {
                    JOptionPane.showMessageDialog(guiCrearRutina, "Rutina registrada exitosamente!");
                    // VerCargarCuotasGUI();
                } else {
                    JOptionPane.showMessageDialog(guiCrearRutina, "Ocurrio un error, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(guiCrearRutina, "No se selecciono un socio o no se ingresaron ejercicios a la rutina.", "Atencion!", JOptionPane.WARNING_MESSAGE);
            }
        }
        ///////////Controlador CargarCobrosGUI//////////////////

        /////////////Fin Controlador CargarCobrosGUI/////////////////
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
    public void editingStopped(ChangeEvent e) {
        
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
    }
}
