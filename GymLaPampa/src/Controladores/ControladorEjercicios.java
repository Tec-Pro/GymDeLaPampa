/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ABMs.ABMEjercicios;
import Interfaces.GuiEjercicios;
import Modelos.Articulo;
import Modelos.Ejercicio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author A
 */
public class ControladorEjercicios implements ActionListener {

    GuiEjercicios guiEjercicios;
    ABMEjercicios abmEjercicios;
    boolean apreteModificar = false;

    public ControladorEjercicios(GuiEjercicios ag) {
        guiEjercicios = ag;
        guiEjercicios.setActionListener(this);
        abmEjercicios = new ABMEjercicios();

        guiEjercicios.getTablaEjercicios().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (guiEjercicios.getTablaEjercicios().getSelectedRow() != -1) {
                    tablaEjerciciosMouseClicked(null);
                } else {
                    guiEjercicios.EstadoInicial();
                    guiEjercicios.LimpiarCampos();
                }
                apreteModificar = false;
            }
        });
        guiEjercicios.getTablaEjercicios().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaEjerciciosMouseClicked(null);
            }
        });
        
        guiEjercicios.getTxtBuscar().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscar();
            }
        });
    }

    private void tablaEjerciciosMouseClicked(MouseEvent evt) {
        int r = guiEjercicios.getTablaEjercicios().getSelectedRow();
        CargarDatosEjercicioSeleccionado(String.valueOf(guiEjercicios.getTablaEjerciciosDefault().getValueAt(r, 0)));
        guiEjercicios.EstadoEjercicioSeleccionado();
    }
    
    private void buscar(){
        abrirBase();
        guiEjercicios.getTablaEjerciciosDefault().setRowCount(0);
        List<Ejercicio> resul;
        resul = Ejercicio.where("ejercicio like ?", "%" + guiEjercicios.getTxtBuscar().getText() + "%");
        for(Ejercicio e : resul){
            Object[] row = new Object[3];
            row[0] = e.getString("id");
            row[1] = e.getString("ejercicio");
            row[2] = e.getString("grupo");
            guiEjercicios.getTablaEjerciciosDefault().addRow(row);
        }
    }

    //Trae de la base de datos todos los datos del ejercicio seleccionado en la tabla y los pone en la interfaz
    public void CargarDatosEjercicioSeleccionado(String idEjercicio) {
        abrirBase();
        Ejercicio ejercicio = Ejercicio.first("id = ?", idEjercicio);
        if (ejercicio != null) {
            guiEjercicios.getTxtEjercicio().setText(ejercicio.getString("ejercicio"));
            guiEjercicios.getBoxGrupo().setSelectedItem(ejercicio.getString("grupo"));
        } else {
            JOptionPane.showMessageDialog(guiEjercicios, "Ocurrio un error inesperado, intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public boolean DatosObligatoriosOK() {
        if ((guiEjercicios.getTxtEjercicio().equals(""))
                || (guiEjercicios.getBoxGrupo().getSelectedItem().equals("Seleccionar"))) {
            return false;
        }
        return true;
    }

    private Ejercicio ObtenerDatosEjercicio(String id) {
        abrirBase();
        Ejercicio ejercicio;
        if (id == null) {
            ejercicio = new Ejercicio();
        } else {
            ejercicio = Ejercicio.first("id = ?", id);
        }
        ejercicio.set("ejercicio", guiEjercicios.getTxtEjercicio().getText().toUpperCase());
        ejercicio.set("grupo", guiEjercicios.getBoxGrupo().getSelectedItem());
        return ejercicio;
    }

    public void ActualizarLista() {
        abrirBase();
        guiEjercicios.getTablaEjerciciosDefault().setRowCount(0);
        LazyList<Ejercicio> listaEjercicios = Ejercicio.findAll().orderBy("grupo");
        for (Ejercicio ejercicio : listaEjercicios) {
            Object row[] = new String[3];
            row[0] = ejercicio.getString("id");
            row[1] = ejercicio.getString("ejercicio");
            row[2] = ejercicio.getString("grupo");
            guiEjercicios.getTablaEjerciciosDefault().addRow(row);
        }

    }

    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gym", "root", "root");
        }
    }

    private boolean EjercicioNoExiste() {
        abrirBase();
        Ejercicio a = Ejercicio.first("ejercicio = ?", guiEjercicios.getTxtEjercicio().getText());
        if (a != null) {
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guiEjercicios.getNuevoBtn()) {
            if (guiEjercicios.getNuevoBtn().getText().equals("Nuevo")) { //Si el nombre del btn es "nuevo" entonces solo habilito los campos
                guiEjercicios.ApreteBtnNuevoModificar();
                guiEjercicios.LimpiarCampos();
            } else {
                if (guiEjercicios.getNuevoBtn().getText().equals("Guardar") && !apreteModificar) {
                    if (DatosObligatoriosOK()) {
                        if (EjercicioNoExiste()) {
                            if (abmEjercicios.Alta(ObtenerDatosEjercicio(null))) {
                                JOptionPane.showMessageDialog(guiEjercicios, "Ejercicio registrado exitosamente!");
                                guiEjercicios.LimpiarCampos();
                                guiEjercicios.EstadoInicial();
                                ActualizarLista();
                            } else {
                                JOptionPane.showMessageDialog(guiEjercicios, "Ocurrio un error, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(guiEjercicios, "Ejercicio ya exitente.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(guiEjercicios, "Hay campos obligatorios sin completar.", "Atencion!", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    if (guiEjercicios.getNuevoBtn().getText().equals("Guardar") && apreteModificar) {
                        if (DatosObligatoriosOK()) {
                            int r = guiEjercicios.getTablaEjercicios().getSelectedRow();
                            if (String.valueOf(guiEjercicios.getTablaEjerciciosDefault().getValueAt(r, 1)).equals(guiEjercicios.getTxtEjercicio().getText())) {
                                if (abmEjercicios.Modificar(ObtenerDatosEjercicio(String.valueOf(guiEjercicios.getTablaEjerciciosDefault().getValueAt(r, 0))))) {
                                    JOptionPane.showMessageDialog(guiEjercicios, "Ejercicio modificado exitosamente!");
                                    guiEjercicios.EstadoLuegoDeModificar();
                                    ActualizarLista();
                                    apreteModificar = false;
                                } else {
                                    JOptionPane.showMessageDialog(guiEjercicios, "Ocurrio un error, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                if (EjercicioNoExiste()) {
                                    if (abmEjercicios.Modificar(ObtenerDatosEjercicio(String.valueOf(guiEjercicios.getTablaEjerciciosDefault().getValueAt(r, 0))))) {
                                        JOptionPane.showMessageDialog(guiEjercicios, "Ejercicio modificado exitosamente!");
                                        guiEjercicios.EstadoLuegoDeModificar();
                                        ActualizarLista();
                                        apreteModificar = false;
                                    } else {
                                        JOptionPane.showMessageDialog(guiEjercicios, "Ocurrio un error, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(guiEjercicios, "Hay campos obligatorios sin completar.", "Atencion!", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        }
        if (e.getSource() == guiEjercicios.getModificarBtn()) {
            apreteModificar = true;
            guiEjercicios.ApreteBtnNuevoModificar();
        }
        if (e.getSource() == guiEjercicios.getEliminarBtn()) {
            if (guiEjercicios.getEliminarBtn().getText().equals("Eliminar")) {
                Integer resp = JOptionPane.showConfirmDialog(guiEjercicios, "¿Desea borrar el ejercicio " + guiEjercicios.getTxtEjercicio().getText() + "?", "Confirmar borrado", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    int r = guiEjercicios.getTablaEjercicios().getSelectedRow();
                    if (abmEjercicios.Eliminar(ObtenerDatosEjercicio(String.valueOf(guiEjercicios.getTablaEjerciciosDefault().getValueAt(r, 0))))) {
                        JOptionPane.showMessageDialog(guiEjercicios, "Ejercicio eliminado correctamente!");
                        guiEjercicios.EstadoInicial();
                        guiEjercicios.LimpiarCampos();
                        ActualizarLista();
                    } else {
                        JOptionPane.showMessageDialog(guiEjercicios, "Ocurrio un error, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                if (guiEjercicios.getEliminarBtn().getText().equals("Cancelar")) {
                    guiEjercicios.EstadoInicial();
                    guiEjercicios.LimpiarCampos();
                }
            }
        }

    }

}
