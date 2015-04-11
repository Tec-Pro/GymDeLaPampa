/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ABMs.ABMDia;
import Interfaces.GuiCargarDias;
import Interfaces.PrincipalGui;
import Modelos.Dia;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author A
 */
public class ControladorGuiCargarDias implements ActionListener, CellEditorListener{
    GuiCargarDias guiCargarDias;
    ABMDia abmDias;
    PrincipalGui aplicacionGUI;
    public Object socioId = null;

    public ControladorGuiCargarDias(GuiCargarDias cv, PrincipalGui ap) throws JRException, ClassNotFoundException, SQLException {
        aplicacionGUI = ap;
        guiCargarDias = cv;
        guiCargarDias.setActionListener(this);
        abmDias = new ABMDia();

        /*guiCargarDias.getTxtBuscarSocio().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarSocio();
            }
        });*/
        guiCargarDias.getTxtBuscarEjercicio().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarEjercicio();
            }
        });

        /*guiCargarDias.getTablaSocios().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaSocioMouseClicked(evt);
            }
        });*/

        guiCargarDias.getTablaEjercicios().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaEjerciciosMouseClicked(evt);
            }
        });
        
        guiCargarDias.getTablaRutinas().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (guiCargarDias.getTablaRutinas().getSelectedRow() != -1) {
                    guiCargarDias.getBtnRegistrarRutina().setEnabled(true);
                } else {
                    guiCargarDias.getBtnRegistrarRutina().setEnabled(false);
                }
            }
        });
        
        /*guiCargarDias.getFechaInicio().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                Calendar cal = guiCargarDias.getFechaInicio().getCalendar();
                cal.setTime(guiCargarDias.getFechaInicio().getDate());
                cal.add(Calendar.MONTH, 1);
                guiCargarDias.getFechaFin().setCalendar(cal);
            }
        });*/
    }

    /*private void buscarSocio() {
        abrirBase();
        guiCargarDias.getTablaSociosDefault().setRowCount(0);
        List<Socio> resul;
        resul = Socio.where("APELLIDO like ? or NOMBRE like ?", "%" + guiCargarDias.getTxtBuscarSocio().getText() + "%", "%" + guiCargarDias.getTxtBuscarSocio().getText() + "%");
        for(Socio e : resul){
            Object[] row = new Object[2];
            row[0] = e.getString("NOMBRE")+" "+e.getString("APELLIDO");
            row[1] = e.getString("DNI");
            guiCargarDias.getTablaSociosDefault().addRow(row);
        }
    }*/
    
    private void buscarEjercicio(){
        abrirBase();
        guiCargarDias.getTablaEjerciciosDefault().setRowCount(0);
        List<Ejercicio> resul;
        resul = Ejercicio.where("ejercicio like ? or grupo like ?", "%" + guiCargarDias.getTxtBuscarEjercicio().getText() + "%", "%" + guiCargarDias.getTxtBuscarEjercicio().getText() + "%");
        for(Ejercicio e : resul){
            Object[] row = new Object[3];
            row[0] = e.getString("id");
            row[1] = e.getString("grupo");
            row[2] = e.getString("ejercicio");
            guiCargarDias.getTablaEjerciciosDefault().addRow(row);
        }
        guiCargarDias.getTablaEjercicios().getRowSorter().toggleSortOrder(1);
    }

   /* private void tablaSocioMouseClicked(MouseEvent evt) {
        int r = guiCargarDias.getTablaSocios().getSelectedRow();
        guiCargarDias.getTxtSocio().setText(String.valueOf(guiCargarDias.getTablaSociosDefault().getValueAt(r, 0)));
        guiCargarDias.getTxtIdSocio().setText(String.valueOf(guiCargarDias.getTablaSociosDefault().getValueAt(r, 1)));
    }*/

    private void tablaEjerciciosMouseClicked(MouseEvent evt) {
        abrirBase();
        int r = guiCargarDias.getTablaEjercicios().getSelectedRow();
        Ejercicio ejercicio = Ejercicio.first("id = ?", String.valueOf(guiCargarDias.getTablaEjerciciosDefault().getValueAt(r, 0)));
        if(ejercicio.getString("grupo").equals("AEROBICO")){
            if(!ejercicioYaCargado(String.valueOf(guiCargarDias.getTablaEjerciciosDefault().getValueAt(r, 0)),false)){
                Object row[] = new Object[3];
                row[0] = ejercicio.getString("id");
                row[1] = ejercicio.getString("ejercicio");
                row[2] = "";
                guiCargarDias.getTablaAerobicoDefault().addRow(row);
            }else{
                JOptionPane.showMessageDialog(aplicacionGUI, "Ejercicio ya cargado!");
            }
        }else{
            if(!ejercicioYaCargado(String.valueOf(guiCargarDias.getTablaEjerciciosDefault().getValueAt(r, 0)),true)){
                Object row[] = new Object[5];
                row[0] = ejercicio.getString("id");
                row[1] = ejercicio.getString("grupo");
                row[2] = ejercicio.getString("ejercicio");
                row[3] = "3";
                row[4] = "15";
                guiCargarDias.getTablaMusculacionDefault().addRow(row);
            }else{
                JOptionPane.showMessageDialog(aplicacionGUI, "Ejercicio ya cargado!");
            }
        }    
    }

    private boolean ejercicioYaCargado(String id, boolean musculacion) {
        if(musculacion){
           for (int i = 0; i < guiCargarDias.getTablaMusculacion().getRowCount(); i++) {
                if (String.valueOf(guiCargarDias.getTablaMusculacionDefault().getValueAt(i, 0)).equals(id)) {
                    return true;
                }
            } 
        }else{
           for (int i = 0; i < guiCargarDias.getTablaAerobico().getRowCount(); i++) {
                if (String.valueOf(guiCargarDias.getTablaAerobicoDefault().getValueAt(i, 0)).equals(id)) {
                    return true;
                }
            } 
        }
        
        return false;
    }

    public Dia ObtenerDatosDia() {
        abrirBase();
        LinkedList<Ejercicio> listaEjercicios = new LinkedList();
        for (int i = 0; i < guiCargarDias.getTablaMusculacionDefault().getRowCount(); i++) {
            Ejercicio ar = Ejercicio.first("id = ?", guiCargarDias.getTablaMusculacionDefault().getValueAt(i, 0));
            ar.setRepeticiones(Integer.valueOf(String.valueOf(guiCargarDias.getTablaMusculacionDefault().getValueAt(i, 4))));
            ar.setSeries(Integer.valueOf(String.valueOf(guiCargarDias.getTablaMusculacionDefault().getValueAt(i, 3))));
            listaEjercicios.add(ar);
        }
        for (int i = 0; i < guiCargarDias.getTablaAerobicoDefault().getRowCount(); i++) {
            Ejercicio ar = Ejercicio.first("id = ?", guiCargarDias.getTablaAerobicoDefault().getValueAt(i, 0));
            ar.setTiempo(String.valueOf(guiCargarDias.getTablaAerobicoDefault().getValueAt(i, 2)));
            listaEjercicios.add(ar);
        }
        Dia v = new Dia(listaEjercicios);
        int r = guiCargarDias.getTablaRutinas().getSelectedRow();
        v.set("rutina_id", guiCargarDias.getTablaRutinasDefault().getValueAt(r, 0));
       // v.set("fecha_inicio", dateToMySQLDate(guiCargarDias.getFechaInicio().getDate(), false));
       // v.set("fecha_fin", dateToMySQLDate(guiCargarDias.getFechaFin().getDate(), false));
        v.set("dia", guiCargarDias.getBoxDia().getSelectedItem());
        //v.set("objetivo", guiCargarDias.getAreaObjetivo.getText());
        return v;
    }

    public boolean DatosOK() {
        if ((!guiCargarDias.getBoxDia().getSelectedItem().equals("Seleccionar"))
                && ((!(guiCargarDias.getTablaAerobicoDefault().getRowCount() == 0))
                || (!(guiCargarDias.getTablaMusculacionDefault().getRowCount() == 0)))
                ) {
            return true;
        }
        return false;
    }

    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gym", "root", "root");
        }
    }
    
    public void ActualizarListaEjercicios(){
        guiCargarDias.getTablaEjerciciosDefault().setRowCount(0);
        abrirBase();
        LazyList<Ejercicio> ejercicios = Ejercicio.findAll();
        for(Ejercicio a: ejercicios){
            Object[] row = new Object[3];
            row[0] = a.getString("id");
            row[1] = a.getString("grupo");
            row[2] = a.getString("ejercicio");
            guiCargarDias.getTablaEjerciciosDefault().addRow(row);
        }
         guiCargarDias.getTablaEjercicios().getRowSorter().toggleSortOrder(1);
    }
    
    public void ActualizarListaRutinas(Object idSocio){
        guiCargarDias.getTablaRutinasDefault().setRowCount(0);
        abrirBase();
        LazyList<Rutina> rutinas = Rutina.where("socio_id = ?", idSocio);
        for(Rutina a: rutinas){
            Object[] row = new Object[4];
            row[0] = a.get("id");
            row[1] = dateToMySQLDate(a.getDate("fecha_inicio"), true);
            row[2] = dateToMySQLDate(a.getDate("fecha_fin"), true);
            LazyList<Dia> dias = Dia.where("rutina_id = ?", a.get("id"));
            if(dias.isEmpty())
                row[3] = 0;
            else
                row[3] = dias.size();
            guiCargarDias.getTablaRutinasDefault().addRow(row);
        }
         
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       /* if (e.getSource().equals(guiCargarDias.getQuitarBtn())) {
            if (guiCargarDias.getTablaRutina().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                guiCargarDias.getTablaRutinaDefault().removeRow(guiCargarDias.getTablaRutina().getSelectedRow());
                actualizarMonto();
            }
        }*/
        if (e.getSource().equals(guiCargarDias.getBtnRegistrarRutina())) {
            if (DatosOK()) {
                if (abmDias.Alta(ObtenerDatosDia())) {
                    int ret =JOptionPane.showConfirmDialog(guiCargarDias, "Dia cargado exitosamente, decea cargar otro dia a la rutina?","Operacion exitosa",JOptionPane.YES_NO_OPTION);
                if(ret== JOptionPane.YES_OPTION){
                    ActualizarListaRutinas(socioId);
                    guiCargarDias.getTablaAerobicoDefault().setRowCount(0);
                    guiCargarDias.getTablaMusculacionDefault().setRowCount(0);
                    guiCargarDias.getBoxDia().setSelectedIndex(0);
                }else{
                    guiCargarDias.setVisible(false);
                }
                } else {
                    JOptionPane.showMessageDialog(guiCargarDias, "Ya existe una rutina para el dia seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(guiCargarDias, "No se ingresaron ejercicios o falta seleccionar dia.", "Atencion!", JOptionPane.WARNING_MESSAGE);
            }
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
    public void editingStopped(ChangeEvent e) {
        
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
    }
}
