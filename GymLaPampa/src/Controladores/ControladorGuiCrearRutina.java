/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ABMs.ABMDia;
import ABMs.ABMRutinas;
import Interfaces.GuiCrearRutina;
import Modelos.Dia;
import Modelos.DiasEjercicios;
import Modelos.Ejercicio;
import Modelos.Rutina;
import Modelos.Socio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author Alan Gonzalez
 */
public class ControladorGuiCrearRutina implements ActionListener {

    GuiCrearRutina guiCrearRutina;
    ABMDia abmDia;
    ABMRutinas abmRutinas;

    public ControladorGuiCrearRutina(GuiCrearRutina r) {
        guiCrearRutina = r;
        guiCrearRutina.setActionListener(this);
        abmDia = new ABMDia();
        abmRutinas = new ABMRutinas();

        guiCrearRutina.getFechaInicio().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                Calendar cal = guiCrearRutina.getFechaInicio().getCalendar();
                cal.setTime(guiCrearRutina.getFechaInicio().getDate());
                cal.add(Calendar.MONTH, 1);
                guiCrearRutina.getFechaFin().setCalendar(cal);
            }
        });

        guiCrearRutina.getTxtBuscarEjercicio().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarEjercicio();
            }
        });

        guiCrearRutina.getTxtBuscarSocio().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarSocio();
            }
        });

        guiCrearRutina.getTablaEjercicio().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaEjerciciosMouseClicked(evt);
            }
        });

        guiCrearRutina.getTablaRutinasSocio().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (guiCrearRutina.getTablaRutinasSocio().getSelectedRow() != -1) {
                    guiCrearRutina.limpiarTablas();
                    rutinaSeleccionada();
                    guiCrearRutina.EstadoRutinaSeleccionada();
                } else {
                    guiCrearRutina.limpiarTablas();
                    guiCrearRutina.LimpiarCampos();
                    guiCrearRutina.EstadoInicial();
                }
            }
        });

        guiCrearRutina.getTablaSocios().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (guiCrearRutina.getTablaSocios().getSelectedRow() != -1) {
                    ActualizarListaRutinas();
                } else {
                    guiCrearRutina.getTablaRutinasSocioDefault().setRowCount(0);
                }
            }
        });

    }

    private void rutinaSeleccionada() {
        int r = guiCrearRutina.getTablaRutinasSocio().getSelectedRow();
        Rutina ru = Rutina.first("id = ?", guiCrearRutina.getTablaRutinasSocio().getValueAt(r, 0));
        //guiCrearRutina.getFechaInicio().setDate(dateToMySQLDate(ru.getDate("fecha_inicio"), true));
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        String strFecha = dateToMySQLDate(ru.getDate("fecha_inicio"), true);
        Date fecha = null;
        try {
            fecha = formatoDelTexto.parse(strFecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        guiCrearRutina.getFechaInicio().setDate(fecha);
        SimpleDateFormat formatoDelTexto2 = new SimpleDateFormat("dd/MM/yyyy");
        String strFecha2 = dateToMySQLDate(ru.getDate("fecha_fin"), true);
        Date fecha2 = null;
        try {
            fecha2 = formatoDelTexto2.parse(strFecha2);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        guiCrearRutina.getFechaFin().setDate(fecha2);
        guiCrearRutina.getAreaObjetivo().setText(ru.getString("descrip"));
        LazyList<Dia> dias = Dia.where("rutina_id = ?", ru.get("id"));
        if (!dias.isEmpty()) {
            for (Dia d : dias) {
                LazyList<DiasEjercicios> diasEjercicios = DiasEjercicios.where("dia_id = ?", d.get("id"));
                for (DiasEjercicios de : diasEjercicios) {
                    Ejercicio e = Ejercicio.first("id = ?", de.get("ejercicio_id"));
                    switch (d.getString("dia")) {
                        case "LUNES":
                            if (e.getString("grupo").equals("AEROBICO")) {
                                Object[] row = new Object[3];
                                row[0] = e.get("id");
                                row[1] = e.get("ejercicio");
                                row[2] = de.get("tiempo");
                                guiCrearRutina.getTablaAerobicoLunesDefault().addRow(row);
                            } else {
                                Object[] row = new Object[5];
                                row[0] = e.get("id");
                                row[1] = e.get("grupo");
                                row[2] = e.get("ejercicio");
                                row[3] = de.get("series");
                                row[4] = de.get("repeticiones");
                                guiCrearRutina.getTablaMusculacionLunesDefault().addRow(row);
                            }
                            break;
                        case "MARTES":
                            if (e.getString("grupo").equals("AEROBICO")) {
                                Object[] row = new Object[3];
                                row[0] = e.get("id");
                                row[1] = e.get("ejercicio");
                                row[2] = de.get("tiempo");
                                guiCrearRutina.getTablaAerobicoMartesDefault().addRow(row);
                            } else {
                                Object[] row = new Object[5];
                                row[0] = e.get("id");
                                row[1] = e.get("grupo");
                                row[2] = e.get("ejercicio");
                                row[3] = de.get("series");
                                row[4] = de.get("repeticiones");
                                guiCrearRutina.getTablaMusculacionMartesDefault().addRow(row);
                            }
                            break;
                        case "MIERCOLES":
                            if (e.getString("grupo").equals("AEROBICO")) {
                                Object[] row = new Object[3];
                                row[0] = e.get("id");
                                row[1] = e.get("ejercicio");
                                row[2] = de.get("tiempo");
                                guiCrearRutina.getTablaAerobicoMiercolesDefault().addRow(row);
                            } else {
                                Object[] row = new Object[5];
                                row[0] = e.get("id");
                                row[1] = e.get("grupo");
                                row[2] = e.get("ejercicio");
                                row[3] = de.get("series");
                                row[4] = de.get("repeticiones");
                                guiCrearRutina.getTablaMusculacionMiercolesDefault().addRow(row);
                            }
                            break;
                        case "JUEVES":
                            if (e.getString("grupo").equals("AEROBICO")) {
                                Object[] row = new Object[3];
                                row[0] = e.get("id");
                                row[1] = e.get("ejercicio");
                                row[2] = de.get("tiempo");
                                guiCrearRutina.getTablaAerobicoJuevesDefault().addRow(row);
                            } else {
                                Object[] row = new Object[5];
                                row[0] = e.get("id");
                                row[1] = e.get("grupo");
                                row[2] = e.get("ejercicio");
                                row[3] = de.get("series");
                                row[4] = de.get("repeticiones");
                                guiCrearRutina.getTablaMusculacionJuevesDefault().addRow(row);
                            }
                            break;
                        case "VIERNES":
                            if (e.getString("grupo").equals("AEROBICO")) {
                                Object[] row = new Object[3];
                                row[0] = e.get("id");
                                row[1] = e.get("ejercicio");
                                row[2] = de.get("tiempo");
                                guiCrearRutina.getTablaAerobicoViernesDefault().addRow(row);
                            } else {
                                Object[] row = new Object[5];
                                row[0] = e.get("id");
                                row[1] = e.get("grupo");
                                row[2] = e.get("ejercicio");
                                row[3] = de.get("series");
                                row[4] = de.get("repeticiones");
                                guiCrearRutina.getTablaMusculacionViernesDefault().addRow(row);
                            }
                            break;
                        case "SABADO":
                            if (e.getString("grupo").equals("AEROBICO")) {
                                Object[] row = new Object[3];
                                row[0] = e.get("id");
                                row[1] = e.get("ejercicio");
                                row[2] = de.get("tiempo");
                                guiCrearRutina.getTablaAerobicoSabadoDefault().addRow(row);
                            } else {
                                Object[] row = new Object[5];
                                row[0] = e.get("id");
                                row[1] = e.get("grupo");
                                row[2] = e.get("ejercicio");
                                row[3] = de.get("series");
                                row[4] = de.get("repeticiones");
                                guiCrearRutina.getTablaMusculacionSabadoDefault().addRow(row);
                            }
                            break;
                        case "DOMINGO":
                            if (e.getString("grupo").equals("AEROBICO")) {
                                Object[] row = new Object[3];
                                row[0] = e.get("id");
                                row[1] = e.get("ejercicio");
                                row[2] = de.get("tiempo");
                                guiCrearRutina.getTablaAerobicoDomingoDefault().addRow(row);
                            } else {
                                Object[] row = new Object[5];
                                row[0] = e.get("id");
                                row[1] = e.get("grupo");
                                row[2] = e.get("ejercicio");
                                row[3] = de.get("series");
                                row[4] = de.get("repeticiones");
                                guiCrearRutina.getTablaMusculacionDomingoDefault().addRow(row);
                            }
                            break;
                    }
                }
            }
        }

    }

    private void tablaEjerciciosMouseClicked(MouseEvent evt) {
        abrirBase();
        int r = guiCrearRutina.getTablaEjercicio().getSelectedRow();
        Ejercicio ejercicio = Ejercicio.first("id = ?", String.valueOf(guiCrearRutina.getTablaEjerciciosDefault().getValueAt(r, 0)));
        if (ejercicio.getString("grupo").equals("AEROBICO")) {
            if (!ejercicioYaCargado(String.valueOf(guiCrearRutina.getTablaEjerciciosDefault().getValueAt(r, 0)), false)) {
                Object row[] = new Object[3];
                row[0] = ejercicio.getString("id");
                row[1] = ejercicio.getString("ejercicio");
                row[2] = "";
                int dia = guiCrearRutina.getTabsPanel().getSelectedIndex();
                switch (dia) {
                    case 0:
                        guiCrearRutina.getTablaAerobicoLunesDefault().addRow(row);
                        break;
                    case 1:
                        guiCrearRutina.getTablaAerobicoMartesDefault().addRow(row);
                        break;
                    case 2:
                        guiCrearRutina.getTablaAerobicoMiercolesDefault().addRow(row);
                        break;
                    case 3:
                        guiCrearRutina.getTablaAerobicoJuevesDefault().addRow(row);
                        break;
                    case 4:
                        guiCrearRutina.getTablaAerobicoViernesDefault().addRow(row);
                        break;
                    case 5:
                        guiCrearRutina.getTablaAerobicoSabadoDefault().addRow(row);
                        break;
                    case 6:
                        guiCrearRutina.getTablaAerobicoDomingoDefault().addRow(row);
                        break;
                }

            } else {
                JOptionPane.showMessageDialog(guiCrearRutina, "Ejercicio ya cargado!");
            }
        } else {
            if (!ejercicioYaCargado(String.valueOf(guiCrearRutina.getTablaEjerciciosDefault().getValueAt(r, 0)), true)) {
                Object row[] = new Object[5];
                row[0] = ejercicio.getString("id");
                row[1] = ejercicio.getString("grupo");
                row[2] = ejercicio.getString("ejercicio");
                row[3] = "3";
                row[4] = "15";
                int dia = guiCrearRutina.getTabsPanel().getSelectedIndex();
                switch (dia) {
                    case 0:
                        guiCrearRutina.getTablaMusculacionLunesDefault().addRow(row);
                        break;
                    case 1:
                        guiCrearRutina.getTablaMusculacionMartesDefault().addRow(row);
                        break;
                    case 2:
                        guiCrearRutina.getTablaMusculacionMiercolesDefault().addRow(row);
                        break;
                    case 3:
                        guiCrearRutina.getTablaMusculacionJuevesDefault().addRow(row);
                        break;
                    case 4:
                        guiCrearRutina.getTablaMusculacionViernesDefault().addRow(row);
                        break;
                    case 5:
                        guiCrearRutina.getTablaMusculacionSabadoDefault().addRow(row);
                        break;
                    case 6:
                        guiCrearRutina.getTablaMusculacionDomingoDefault().addRow(row);
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(guiCrearRutina, "Ejercicio ya cargado!");
            }
        }
    }

    private boolean ejercicioYaCargado(String id, boolean musculacion) {
        int dia = guiCrearRutina.getTabsPanel().getSelectedIndex();
        switch (dia) {
            case 0:
                if (musculacion) {
                    for (int i = 0; i < guiCrearRutina.getTablaMusculacionLunes().getRowCount(); i++) {
                        if (String.valueOf(guiCrearRutina.getTablaMusculacionLunes().getValueAt(i, 0)).equals(id)) {
                            return true;
                        }
                    }
                } else {
                    for (int i = 0; i < guiCrearRutina.getTablaAerobicoLunes().getRowCount(); i++) {
                        if (String.valueOf(guiCrearRutina.getTablaAerobicoLunes().getValueAt(i, 0)).equals(id)) {
                            return true;
                        }
                    }
                }
                break;
            case 1:
                if (musculacion) {
                    for (int i = 0; i < guiCrearRutina.getTablaMusculacionMartes().getRowCount(); i++) {
                        if (String.valueOf(guiCrearRutina.getTablaMusculacionMartes().getValueAt(i, 0)).equals(id)) {
                            return true;
                        }
                    }
                } else {
                    for (int i = 0; i < guiCrearRutina.getTablaAerobicoMartes().getRowCount(); i++) {
                        if (String.valueOf(guiCrearRutina.getTablaAerobicoMartes().getValueAt(i, 0)).equals(id)) {
                            return true;
                        }
                    }
                }
                break;
            case 2:
                if (musculacion) {
                    for (int i = 0; i < guiCrearRutina.getTablaMusculacionMiercoles().getRowCount(); i++) {
                        if (String.valueOf(guiCrearRutina.getTablaMusculacionMiercoles().getValueAt(i, 0)).equals(id)) {
                            return true;
                        }
                    }
                } else {
                    for (int i = 0; i < guiCrearRutina.getTablaAerobicoMiercoles().getRowCount(); i++) {
                        if (String.valueOf(guiCrearRutina.getTablaAerobicoMiercoles().getValueAt(i, 0)).equals(id)) {
                            return true;
                        }
                    }
                }
                break;
            case 3:
                if (musculacion) {
                    for (int i = 0; i < guiCrearRutina.getTablaMusculacionJueves().getRowCount(); i++) {
                        if (String.valueOf(guiCrearRutina.getTablaMusculacionJueves().getValueAt(i, 0)).equals(id)) {
                            return true;
                        }
                    }
                } else {
                    for (int i = 0; i < guiCrearRutina.getTablaAerobicoJueves().getRowCount(); i++) {
                        if (String.valueOf(guiCrearRutina.getTablaAerobicoJueves().getValueAt(i, 0)).equals(id)) {
                            return true;
                        }
                    }
                }
                break;
            case 4:
                if (musculacion) {
                    for (int i = 0; i < guiCrearRutina.getTablaMusculacionViernes().getRowCount(); i++) {
                        if (String.valueOf(guiCrearRutina.getTablaMusculacionViernes().getValueAt(i, 0)).equals(id)) {
                            return true;
                        }
                    }
                } else {
                    for (int i = 0; i < guiCrearRutina.getTablaAerobicoViernes().getRowCount(); i++) {
                        if (String.valueOf(guiCrearRutina.getTablaAerobicoViernes().getValueAt(i, 0)).equals(id)) {
                            return true;
                        }
                    }
                }
                break;
            case 5:
                if (musculacion) {
                    for (int i = 0; i < guiCrearRutina.getTablaMusculacionSabado().getRowCount(); i++) {
                        if (String.valueOf(guiCrearRutina.getTablaMusculacionSabado().getValueAt(i, 0)).equals(id)) {
                            return true;
                        }
                    }
                } else {
                    for (int i = 0; i < guiCrearRutina.getTablaAerobicoSabado().getRowCount(); i++) {
                        if (String.valueOf(guiCrearRutina.getTablaAerobicoSabado().getValueAt(i, 0)).equals(id)) {
                            return true;
                        }
                    }
                }
                break;
            case 6:
                if (musculacion) {
                    for (int i = 0; i < guiCrearRutina.getTablaMusculacionDomingo().getRowCount(); i++) {
                        if (String.valueOf(guiCrearRutina.getTablaMusculacionDomingo().getValueAt(i, 0)).equals(id)) {
                            return true;
                        }
                    }
                } else {
                    for (int i = 0; i < guiCrearRutina.getTablaAerobicoDomingo().getRowCount(); i++) {
                        if (String.valueOf(guiCrearRutina.getTablaAerobicoDomingo().getValueAt(i, 0)).equals(id)) {
                            return true;
                        }
                    }
                }
                break;
        }
        return false;
    }

    private void buscarEjercicio() {
        abrirBase();
        guiCrearRutina.getTablaEjerciciosDefault().setRowCount(0);
        List<Ejercicio> resul;
        resul = Ejercicio.where("ejercicio like ? or grupo like ?", "%" + guiCrearRutina.getTxtBuscarEjercicio().getText() + "%", "%" + guiCrearRutina.getTxtBuscarEjercicio().getText() + "%");
        for (Ejercicio e : resul) {
            Object[] row = new Object[3];
            row[0] = e.getString("id");
            row[1] = e.getString("grupo");
            row[2] = e.getString("ejercicio");
            guiCrearRutina.getTablaEjerciciosDefault().addRow(row);
        }
        guiCrearRutina.getTablaEjercicio().getRowSorter().toggleSortOrder(1);
    }

    private void buscarSocio() {
        abrirBase();
        guiCrearRutina.getTablaSociosDefault().setRowCount(0);
        List<Socio> resul;
        resul = Socio.where("APELLIDO like ? or NOMBRE like ?", "%" + guiCrearRutina.getTxtBuscarSocio().getText() + "%", "%" + guiCrearRutina.getTxtBuscarSocio().getText() + "%");
        for (Socio e : resul) {
            Object[] row = new Object[2];
            row[0] = e.getString("NOMBRE") + " " + e.getString("APELLIDO");
            row[1] = e.getString("DNI");
            guiCrearRutina.getTablaSociosDefault().addRow(row);
        }
    }

    public void ActualizarListaSocios() {
        abrirBase();
        LazyList<Socio> lista = Socio.findAll();
        for (Socio s : lista) {
            Object[] row = new Object[2];
            row[0] = s.get("NOMBRE") + " " + s.get("APELLIDO");
            row[1] = s.get("ID_DATOS_PERS");
            guiCrearRutina.getTablaSociosDefault().addRow(row);
        }
    }

    public void ActualizarListaEjercicios() {
        guiCrearRutina.getTablaEjerciciosDefault().setRowCount(0);
        abrirBase();
        LazyList<Ejercicio> ejercicios = Ejercicio.findAll();
        for (Ejercicio a : ejercicios) {
            Object[] row = new Object[3];
            row[0] = a.getString("id");
            row[1] = a.getString("grupo");
            row[2] = a.getString("ejercicio");
            guiCrearRutina.getTablaEjerciciosDefault().addRow(row);
        }
        guiCrearRutina.getTablaEjercicio().getRowSorter().toggleSortOrder(1);
    }

    public void ActualizarListaRutinas() {
        guiCrearRutina.getTablaRutinasSocioDefault().setRowCount(0);
        abrirBase();
        int row = guiCrearRutina.getTablaSocios().getSelectedRow();
        LazyList<Rutina> rutinas = Rutina.where("socio_id = ?", guiCrearRutina.getTablaSocios().getValueAt(row, 1));
        for (Rutina r : rutinas) {
            Object[] fila = new Object[3];
            fila[0] = r.get("id");
            fila[1] = dateToMySQLDate(r.getDate("fecha_inicio"), true);
            fila[2] = dateToMySQLDate(r.getDate("fecha_fin"), true);
            guiCrearRutina.getTablaRutinasSocioDefault().addRow(fila);
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

    private Rutina obtenerDatosRutina() {
        abrirBase();
        int row = guiCrearRutina.getTablaSocios().getSelectedRow();
        Rutina r = new Rutina();
        r.set("descrip", guiCrearRutina.getAreaObjetivo().getText());
        r.setDate("fecha_inicio", dateToMySQLDate(guiCrearRutina.getFechaInicio().getDate(), false));
        r.setDate("fecha_fin", dateToMySQLDate(guiCrearRutina.getFechaFin().getDate(), false));
        r.set("socio_id", guiCrearRutina.getTablaSociosDefault().getValueAt(row, 1));
        return r;
    }

    private LinkedList<Dia> obtenerDatosDias() {
        abrirBase();
        LinkedList<Dia> dias = new LinkedList();
        if (guiCrearRutina.getTablaMusculacionLunesDefault().getRowCount() != -1 || guiCrearRutina.getTablaAerobicoLunesDefault().getRowCount() != -1) {
            LinkedList<Ejercicio> listaEjerciciosLunes = new LinkedList();
            for (int i = 0; i < guiCrearRutina.getTablaMusculacionLunesDefault().getRowCount(); i++) {
                Ejercicio ar = Ejercicio.first("id = ?", guiCrearRutina.getTablaMusculacionLunesDefault().getValueAt(i, 0));
                ar.setRepeticiones(Integer.valueOf(String.valueOf(guiCrearRutina.getTablaMusculacionLunesDefault().getValueAt(i, 4))));
                ar.setSeries(Integer.valueOf(String.valueOf(guiCrearRutina.getTablaMusculacionLunesDefault().getValueAt(i, 3))));
                listaEjerciciosLunes.add(ar);
            }
            for (int i = 0; i < guiCrearRutina.getTablaAerobicoLunesDefault().getRowCount(); i++) {
                Ejercicio ar = Ejercicio.first("id = ?", guiCrearRutina.getTablaAerobicoLunesDefault().getValueAt(i, 0));
                ar.setTiempo(String.valueOf(guiCrearRutina.getTablaAerobicoLunesDefault().getValueAt(i, 2)));
                listaEjerciciosLunes.add(ar);
            }
            Dia v = new Dia(listaEjerciciosLunes);
            v.set("rutina_id", abmRutinas.getIdRutina());
            v.set("dia", "LUNES");
            dias.add(v);
        }
        if (guiCrearRutina.getTablaMusculacionMartesDefault().getRowCount() != -1 || guiCrearRutina.getTablaAerobicoMartesDefault().getRowCount() != -1) {
            LinkedList<Ejercicio> listaEjerciciosMartes = new LinkedList();
            for (int i = 0; i < guiCrearRutina.getTablaMusculacionMartesDefault().getRowCount(); i++) {
                Ejercicio ar = Ejercicio.first("id = ?", guiCrearRutina.getTablaMusculacionMartesDefault().getValueAt(i, 0));
                ar.setRepeticiones(Integer.valueOf(String.valueOf(guiCrearRutina.getTablaMusculacionMartesDefault().getValueAt(i, 4))));
                ar.setSeries(Integer.valueOf(String.valueOf(guiCrearRutina.getTablaMusculacionMartesDefault().getValueAt(i, 3))));
                listaEjerciciosMartes.add(ar);
            }
            for (int i = 0; i < guiCrearRutina.getTablaAerobicoMartesDefault().getRowCount(); i++) {
                Ejercicio ar = Ejercicio.first("id = ?", guiCrearRutina.getTablaAerobicoMartesDefault().getValueAt(i, 0));
                ar.setTiempo(String.valueOf(guiCrearRutina.getTablaAerobicoMartesDefault().getValueAt(i, 2)));
                listaEjerciciosMartes.add(ar);
            }
            Dia v = new Dia(listaEjerciciosMartes);
            v.set("rutina_id", abmRutinas.getIdRutina());
            v.set("dia", "MARTES");
            dias.add(v);
        }
        if (guiCrearRutina.getTablaMusculacionMiercolesDefault().getRowCount() != -1 || guiCrearRutina.getTablaAerobicoMiercolesDefault().getRowCount() != -1) {
            LinkedList<Ejercicio> listaEjerciciosMiercoles = new LinkedList();
            for (int i = 0; i < guiCrearRutina.getTablaMusculacionMiercolesDefault().getRowCount(); i++) {
                Ejercicio ar = Ejercicio.first("id = ?", guiCrearRutina.getTablaMusculacionMiercolesDefault().getValueAt(i, 0));
                ar.setRepeticiones(Integer.valueOf(String.valueOf(guiCrearRutina.getTablaMusculacionMiercolesDefault().getValueAt(i, 4))));
                ar.setSeries(Integer.valueOf(String.valueOf(guiCrearRutina.getTablaMusculacionMiercolesDefault().getValueAt(i, 3))));
                listaEjerciciosMiercoles.add(ar);
            }
            for (int i = 0; i < guiCrearRutina.getTablaAerobicoMiercolesDefault().getRowCount(); i++) {
                Ejercicio ar = Ejercicio.first("id = ?", guiCrearRutina.getTablaAerobicoMiercolesDefault().getValueAt(i, 0));
                ar.setTiempo(String.valueOf(guiCrearRutina.getTablaAerobicoMiercolesDefault().getValueAt(i, 2)));
                listaEjerciciosMiercoles.add(ar);
            }
            Dia v = new Dia(listaEjerciciosMiercoles);
            v.set("rutina_id", abmRutinas.getIdRutina());
            v.set("dia", "MIERCOLES");
            dias.add(v);
        }
        if (guiCrearRutina.getTablaMusculacionJuevesDefault().getRowCount() != -1 || guiCrearRutina.getTablaAerobicoJuevesDefault().getRowCount() != -1) {
            LinkedList<Ejercicio> listaEjerciciosJueves = new LinkedList();
            for (int i = 0; i < guiCrearRutina.getTablaMusculacionJuevesDefault().getRowCount(); i++) {
                Ejercicio ar = Ejercicio.first("id = ?", guiCrearRutina.getTablaMusculacionJuevesDefault().getValueAt(i, 0));
                ar.setRepeticiones(Integer.valueOf(String.valueOf(guiCrearRutina.getTablaMusculacionJuevesDefault().getValueAt(i, 4))));
                ar.setSeries(Integer.valueOf(String.valueOf(guiCrearRutina.getTablaMusculacionJuevesDefault().getValueAt(i, 3))));
                listaEjerciciosJueves.add(ar);
            }
            for (int i = 0; i < guiCrearRutina.getTablaAerobicoJuevesDefault().getRowCount(); i++) {
                Ejercicio ar = Ejercicio.first("id = ?", guiCrearRutina.getTablaAerobicoJuevesDefault().getValueAt(i, 0));
                ar.setTiempo(String.valueOf(guiCrearRutina.getTablaAerobicoJuevesDefault().getValueAt(i, 2)));
                listaEjerciciosJueves.add(ar);
            }
            Dia v = new Dia(listaEjerciciosJueves);
            v.set("rutina_id", abmRutinas.getIdRutina());
            v.set("dia", "JUEVES");
            dias.add(v);
        }
        if (guiCrearRutina.getTablaMusculacionViernesDefault().getRowCount() != -1 || guiCrearRutina.getTablaAerobicoViernesDefault().getRowCount() != -1) {
            LinkedList<Ejercicio> listaEjerciciosViernes = new LinkedList();
            for (int i = 0; i < guiCrearRutina.getTablaMusculacionViernesDefault().getRowCount(); i++) {
                Ejercicio ar = Ejercicio.first("id = ?", guiCrearRutina.getTablaMusculacionViernesDefault().getValueAt(i, 0));
                ar.setRepeticiones(Integer.valueOf(String.valueOf(guiCrearRutina.getTablaMusculacionViernesDefault().getValueAt(i, 4))));
                ar.setSeries(Integer.valueOf(String.valueOf(guiCrearRutina.getTablaMusculacionViernesDefault().getValueAt(i, 3))));
                listaEjerciciosViernes.add(ar);
            }
            for (int i = 0; i < guiCrearRutina.getTablaAerobicoViernesDefault().getRowCount(); i++) {
                Ejercicio ar = Ejercicio.first("id = ?", guiCrearRutina.getTablaAerobicoViernesDefault().getValueAt(i, 0));
                ar.setTiempo(String.valueOf(guiCrearRutina.getTablaAerobicoViernesDefault().getValueAt(i, 2)));
                listaEjerciciosViernes.add(ar);
            }
            Dia v = new Dia(listaEjerciciosViernes);
            v.set("rutina_id", abmRutinas.getIdRutina());
            v.set("dia", "VIERNES");
            dias.add(v);
        }
        if (guiCrearRutina.getTablaMusculacionSabadoDefault().getRowCount() != -1 || guiCrearRutina.getTablaAerobicoSabadoDefault().getRowCount() != -1) {
            LinkedList<Ejercicio> listaEjerciciosSabado = new LinkedList();
            for (int i = 0; i < guiCrearRutina.getTablaMusculacionSabadoDefault().getRowCount(); i++) {
                Ejercicio ar = Ejercicio.first("id = ?", guiCrearRutina.getTablaMusculacionSabadoDefault().getValueAt(i, 0));
                ar.setRepeticiones(Integer.valueOf(String.valueOf(guiCrearRutina.getTablaMusculacionSabadoDefault().getValueAt(i, 4))));
                ar.setSeries(Integer.valueOf(String.valueOf(guiCrearRutina.getTablaMusculacionSabadoDefault().getValueAt(i, 3))));
                listaEjerciciosSabado.add(ar);
            }
            for (int i = 0; i < guiCrearRutina.getTablaAerobicoSabadoDefault().getRowCount(); i++) {
                Ejercicio ar = Ejercicio.first("id = ?", guiCrearRutina.getTablaAerobicoSabadoDefault().getValueAt(i, 0));
                ar.setTiempo(String.valueOf(guiCrearRutina.getTablaAerobicoSabadoDefault().getValueAt(i, 2)));
                listaEjerciciosSabado.add(ar);
            }
            Dia v = new Dia(listaEjerciciosSabado);
            v.set("rutina_id", abmRutinas.getIdRutina());
            v.set("dia", "SABADO");
            dias.add(v);
        }
        if (guiCrearRutina.getTablaMusculacionDomingoDefault().getRowCount() != -1 || guiCrearRutina.getTablaAerobicoDomingoDefault().getRowCount() != -1) {
            LinkedList<Ejercicio> listaEjerciciosDomingo = new LinkedList();
            for (int i = 0; i < guiCrearRutina.getTablaMusculacionDomingoDefault().getRowCount(); i++) {
                Ejercicio ar = Ejercicio.first("id = ?", guiCrearRutina.getTablaMusculacionDomingoDefault().getValueAt(i, 0));
                ar.setRepeticiones(Integer.valueOf(String.valueOf(guiCrearRutina.getTablaMusculacionDomingoDefault().getValueAt(i, 4))));
                ar.setSeries(Integer.valueOf(String.valueOf(guiCrearRutina.getTablaMusculacionDomingoDefault().getValueAt(i, 3))));
                listaEjerciciosDomingo.add(ar);
            }
            for (int i = 0; i < guiCrearRutina.getTablaAerobicoDomingoDefault().getRowCount(); i++) {
                Ejercicio ar = Ejercicio.first("id = ?", guiCrearRutina.getTablaAerobicoDomingoDefault().getValueAt(i, 0));
                ar.setTiempo(String.valueOf(guiCrearRutina.getTablaAerobicoDomingoDefault().getValueAt(i, 2)));
                listaEjerciciosDomingo.add(ar);
            }
            Dia v = new Dia(listaEjerciciosDomingo);
            v.set("rutina_id", abmRutinas.getIdRutina());
            v.set("dia", "DOMINGO");
            dias.add(v);
        }
        return dias;
    }

    private boolean datosOK() {
        return ((guiCrearRutina.getTablaSocios().getSelectedRow() != -1)
                && ((guiCrearRutina.getTablaAerobicoLunes().getSelectedRow() != -1 || guiCrearRutina.getTablaMusculacionLunes().getSelectedRow() != -1)
                || (guiCrearRutina.getTablaAerobicoMartes().getSelectedRow() != -1 || guiCrearRutina.getTablaMusculacionMartes().getSelectedRow() != -1)
                || (guiCrearRutina.getTablaAerobicoMiercoles().getSelectedRow() != -1 || guiCrearRutina.getTablaMusculacionMiercoles().getSelectedRow() != -1)
                || (guiCrearRutina.getTablaAerobicoJueves().getSelectedRow() != -1 || guiCrearRutina.getTablaMusculacionJueves().getSelectedRow() != -1)
                || (guiCrearRutina.getTablaAerobicoViernes().getSelectedRow() != -1 || guiCrearRutina.getTablaMusculacionViernes().getSelectedRow() != -1)
                || (guiCrearRutina.getTablaAerobicoSabado().getSelectedRow() != -1 || guiCrearRutina.getTablaMusculacionSabado().getSelectedRow() != -1)
                || (guiCrearRutina.getTablaAerobicoDomingo().getSelectedRow() != -1 || guiCrearRutina.getTablaMusculacionDomingo().getSelectedRow() != -1)));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(guiCrearRutina.getBotNuevo())) {
            guiCrearRutina.getTablaRutinasSocioDefault().setRowCount(0);
            guiCrearRutina.ApreteBotonNuevoModificar();
            guiCrearRutina.setApreteNuevoModificar(true);
            guiCrearRutina.limpiarTablas();
            guiCrearRutina.LimpiarCampos();
            ActualizarListaEjercicios();
            guiCrearRutina.getBotEliminarCancelar().setText("Cancelar");
        }
        if (ae.getSource().equals(guiCrearRutina.getBotEliminarCancelar())) {
            if (guiCrearRutina.getBotEliminarCancelar().getText().equals("Cancelar")) {
                guiCrearRutina.EstadoInicial();
                guiCrearRutina.limpiarTablas();
                guiCrearRutina.LimpiarCampos();
                guiCrearRutina.getTablaEjerciciosDefault().setRowCount(0);
                guiCrearRutina.setApreteModificar(false);
                guiCrearRutina.setApreteNuevoModificar(false);
            } else {
                if (guiCrearRutina.getBotEliminarCancelar().getText().equals("Eliminar")) {
                    int r = JOptionPane.showConfirmDialog(guiCrearRutina, "Decea eliminar la rutina?", "Confirmar operacion", JOptionPane.YES_NO_OPTION);
                    if (r == JOptionPane.YES_OPTION) {
                        int row = guiCrearRutina.getTablaRutinasSocio().getSelectedRow();
                        Rutina ru = Rutina.first("id = ?", guiCrearRutina.getTablaRutinasSocio().getValueAt(row, 0));
                        if (ru != null) {
                            if (abmRutinas.Eliminar(ru)) {
                                JOptionPane.showMessageDialog(guiCrearRutina, "Rutina eliminada correctamente", "Operacion exitosa", JOptionPane.INFORMATION_MESSAGE);
                                guiCrearRutina.EstadoInicial();
                                guiCrearRutina.LimpiarCampos();
                                guiCrearRutina.limpiarTablas();
                                ActualizarListaRutinas();
                            } else {
                                JOptionPane.showMessageDialog(guiCrearRutina, "ERROR, intente nuevamente", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(guiCrearRutina, "ERROR, intente nuevamente", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        }
        if (ae.getSource().equals(guiCrearRutina.getBotGuardar()) && !guiCrearRutina.isApreteModificar()) {
            //if(!datosOK())
            //   JOptionPane.showMessageDialog(guiCrearRutina, "Falta seleccionar cliente o no se han agregado ejercicios.", "Faltan datos obligatorios", JOptionPane.WARNING_MESSAGE);
            //else{
            if (abmRutinas.Alta(obtenerDatosRutina()) && abmRutinas.AltaDias(obtenerDatosDias())) {
                JOptionPane.showMessageDialog(guiCrearRutina, "Rutina registrada exitosamente!.", "Operacion exitosa", JOptionPane.INFORMATION_MESSAGE);
                guiCrearRutina.setApreteNuevoModificar(false);
                guiCrearRutina.EstadoInicial();
                ActualizarListaRutinas();
                guiCrearRutina.limpiarTablas();
                guiCrearRutina.getBotEliminarCancelar().setText("Eliminar");
            } else {
                JOptionPane.showMessageDialog(guiCrearRutina, "Ocurrio un error!.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            //}
        } else {
            if (ae.getSource().equals(guiCrearRutina.getBotGuardar()) && guiCrearRutina.isApreteModificar()) {
                int row = guiCrearRutina.getTablaRutinasSocio().getSelectedRow();
                abmRutinas.setIdRutina(String.valueOf(guiCrearRutina.getTablaRutinasSocio().getValueAt(row, 0)));
                if (abmRutinas.Modificar(obtenerDatosRutina(), obtenerDatosDias())) {
                    JOptionPane.showMessageDialog(guiCrearRutina, "Rutina modificada exitosamente!.", "Operacion exitosa", JOptionPane.INFORMATION_MESSAGE);
                    guiCrearRutina.EstadoLuegoDeModificar();
                    guiCrearRutina.setApreteModificar(false);
                    guiCrearRutina.getTablaEjerciciosDefault().setRowCount(0);
                }
            }
        }
        if (ae.getSource().equals(guiCrearRutina.getBotModif())) {
            guiCrearRutina.ApreteBotonNuevoModificar();
            guiCrearRutina.setApreteModificar(true);
            guiCrearRutina.setApreteNuevoModificar(true);
            ActualizarListaEjercicios();
        }
        if (ae.getSource().equals(guiCrearRutina.getBtnQuitarMusculacion())) {
            int t = guiCrearRutina.getTabsPanel().getSelectedIndex();
            switch (t) {
                case 0:
                    if (guiCrearRutina.getTablaMusculacionLunes().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                        guiCrearRutina.getTablaMusculacionLunesDefault().removeRow(guiCrearRutina.getTablaMusculacionLunes().getSelectedRow());
                    }else{
                        JOptionPane.showMessageDialog(guiCrearRutina, "Primero seleccione el ejercicio a quitar");
                    }
                    break;
                case 1:
                    if (guiCrearRutina.getTablaMusculacionMartes().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                        guiCrearRutina.getTablaMusculacionMartesDefault().removeRow(guiCrearRutina.getTablaMusculacionMartes().getSelectedRow());
                    }else{
                        JOptionPane.showMessageDialog(guiCrearRutina, "Primero seleccione el ejercicio a quitar");
                    }
                    break;
                case 2:
                    if (guiCrearRutina.getTablaMusculacionMiercoles().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                        guiCrearRutina.getTablaMusculacionMiercolesDefault().removeRow(guiCrearRutina.getTablaMusculacionMiercoles().getSelectedRow());
                    }else{
                        JOptionPane.showMessageDialog(guiCrearRutina, "Primero seleccione el ejercicio a quitar");
                    }
                    break;
                case 3:
                    if (guiCrearRutina.getTablaMusculacionJueves().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                        guiCrearRutina.getTablaMusculacionJuevesDefault().removeRow(guiCrearRutina.getTablaMusculacionJueves().getSelectedRow());
                    }else{
                        JOptionPane.showMessageDialog(guiCrearRutina, "Primero seleccione el ejercicio a quitar");
                    }
                    break;
                case 4:
                    if (guiCrearRutina.getTablaMusculacionViernes().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                        guiCrearRutina.getTablaMusculacionViernesDefault().removeRow(guiCrearRutina.getTablaMusculacionViernes().getSelectedRow());
                    }else{
                        JOptionPane.showMessageDialog(guiCrearRutina, "Primero seleccione el ejercicio a quitar");
                    }
                    break;
                case 5:
                    if (guiCrearRutina.getTablaMusculacionSabado().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                        guiCrearRutina.getTablaMusculacionSabadoDefault().removeRow(guiCrearRutina.getTablaMusculacionSabado().getSelectedRow());
                    }else{
                        JOptionPane.showMessageDialog(guiCrearRutina, "Primero seleccione el ejercicio a quitar");
                    }
                    break;
                case 6:
                    if (guiCrearRutina.getTablaMusculacionDomingo().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                        guiCrearRutina.getTablaMusculacionDomingoDefault().removeRow(guiCrearRutina.getTablaMusculacionDomingo().getSelectedRow());
                    }else{
                        JOptionPane.showMessageDialog(guiCrearRutina, "Primero seleccione el ejercicio a quitar");
                    }
                    break;
            }
        }
        if (ae.getSource().equals(guiCrearRutina.getBtnQuitarAerobico())) {
            int t = guiCrearRutina.getTabsPanel().getSelectedIndex();
            switch (t) {
                case 0:
                    if (guiCrearRutina.getTablaAerobicoLunes().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                        guiCrearRutina.getTablaAerobicoLunesDefault().removeRow(guiCrearRutina.getTablaAerobicoLunes().getSelectedRow());
                    }else{
                        JOptionPane.showMessageDialog(guiCrearRutina, "Primero seleccione el ejercicio a quitar");
                    }
                    break;
                case 1:
                    if (guiCrearRutina.getTablaAerobicoMartes().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                        guiCrearRutina.getTablaAerobicoMartesDefault().removeRow(guiCrearRutina.getTablaAerobicoMartes().getSelectedRow());
                    }else{
                        JOptionPane.showMessageDialog(guiCrearRutina, "Primero seleccione el ejercicio a quitar");
                    }
                    break;
                case 2:
                    if (guiCrearRutina.getTablaAerobicoMiercoles().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                        guiCrearRutina.getTablaAerobicoMiercolesDefault().removeRow(guiCrearRutina.getTablaAerobicoMiercoles().getSelectedRow());
                    }else{
                        JOptionPane.showMessageDialog(guiCrearRutina, "Primero seleccione el ejercicio a quitar");
                    }
                    break;
                case 3:
                    if (guiCrearRutina.getTablaAerobicoJueves().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                        guiCrearRutina.getTablaAerobicoJuevesDefault().removeRow(guiCrearRutina.getTablaAerobicoJueves().getSelectedRow());
                    }else{
                        JOptionPane.showMessageDialog(guiCrearRutina, "Primero seleccione el ejercicio a quitar");
                    }
                    break;
                case 4:
                    if (guiCrearRutina.getTablaAerobicoViernes().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                        guiCrearRutina.getTablaAerobicoViernesDefault().removeRow(guiCrearRutina.getTablaAerobicoViernes().getSelectedRow());
                    }else{
                        JOptionPane.showMessageDialog(guiCrearRutina, "Primero seleccione el ejercicio a quitar");
                    }
                    break;
                case 5:
                    if (guiCrearRutina.getTablaAerobicoSabado().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                        guiCrearRutina.getTablaAerobicoSabadoDefault().removeRow(guiCrearRutina.getTablaAerobicoSabado().getSelectedRow());
                    }else{
                        JOptionPane.showMessageDialog(guiCrearRutina, "Primero seleccione el ejercicio a quitar");
                    }
                    break;
                case 6:
                    if (guiCrearRutina.getTablaAerobicoDomingo().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                        guiCrearRutina.getTablaAerobicoDomingoDefault().removeRow(guiCrearRutina.getTablaAerobicoDomingo().getSelectedRow());
                    }else{
                        JOptionPane.showMessageDialog(guiCrearRutina, "Primero seleccione el ejercicio a quitar");
                    }
                    break;
            }
        }
    }

    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gym", "root", "root");
        }
    }

}
