/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Interfaces.GuiRutinas;
import Modelos.Ejercicio;
import Modelos.Rutina;
import Modelos.RutinasEjercicios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author A
 */
public class ControladorGuiRutinas implements ActionListener {

    GuiRutinas guiRutinas;

    public ControladorGuiRutinas(GuiRutinas gr) {
        guiRutinas = gr;
        guiRutinas.setActionListener(this);
        guiRutinas.getTablaRutinas().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (guiRutinas.getTablaRutinas().getSelectedRow() != -1) {
                    int r = guiRutinas.getTablaRutinas().getSelectedRow();
                    CargarEjercicios(guiRutinas.getTablaRutinasDefault().getValueAt(r, 0));
                } else {
                    guiRutinas.getTablaAerobicoDefault().setRowCount(0);
                    guiRutinas.getTablaMusculacionDefault().setRowCount(0);
                }
            }
        });
        guiRutinas.getBoxBusqueda().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guiRutinas.getTablaRutinasDefault().setRowCount(0);
                LazyList<Rutina> listaRutinas;
                if (guiRutinas.getBoxBusqueda().getSelectedItem().equals("TODAS")) {
                    listaRutinas = Rutina.where("socio_id = ?", guiRutinas.getTxtSocioID().getText());
                } else {
                    listaRutinas = Rutina.where("socio_id = ? and fecha_fin > ?", guiRutinas.getTxtSocioID().getText(), Calendar.getInstance().getTime());
                }
                for (Rutina r : listaRutinas) {
                    Object[] row = new Object[4];
                    row[0] = r.get("id");
                    row[1] = r.get("dia");
                    row[2] = dateToMySQLDate(r.getDate("fecha_inicio"), true);
                    row[3] = dateToMySQLDate(r.getDate("fecha_fin"), true);
                    guiRutinas.getTablaRutinasDefault().addRow(row);
                }
            }
        });
    }

    public String dateToMySQLDate(Date fecha, boolean paraMostrar) {
        if (paraMostrar) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(fecha);
        } else {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(fecha);
        }
    }

    private void CargarEjercicios(Object id) {
        guiRutinas.getTablaMusculacionDefault().setRowCount(0);
        guiRutinas.getTablaAerobicoDefault().setRowCount(0);
        LazyList<RutinasEjercicios> listaEjercicios = RutinasEjercicios.where("rutina_id = ?", id);
        for (RutinasEjercicios re : listaEjercicios) {
            Ejercicio e = Ejercicio.first("id = ?", re.get("ejercicio_id"));
            if (e.getString("grupo").equals("AEROBICO")) {
                Object[] row = new Object[3];
                row[0] = e.get("id");
                row[1] = e.get("ejercicio");
                row[2] = re.get("tiempo");
                guiRutinas.getTablaAerobicoDefault().addRow(row);
            } else {
                Object[] row = new Object[5];
                row[0] = e.get("id");
                row[1] = e.get("grupo");
                row[2] = e.get("ejercicio");
                row[3] = re.get("series");
                row[4] = re.get("repeticiones");
                guiRutinas.getTablaMusculacionDefault().addRow(row);
            }
        }
        guiRutinas.getTablaMusculacion().getRowSorter().toggleSortOrder(1); //Ordeno por grupo
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
