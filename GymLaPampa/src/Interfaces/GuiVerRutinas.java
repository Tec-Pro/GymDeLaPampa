/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Controladores.ControladorJReport;
import Controladores.EnvioEmailControlador;
import Interfaces.GuiVerRutinas;
import Interfaces.VerDietaGui;
import Modelos.Dia;
import Modelos.DiasEjercicios;
import Modelos.Ejercicio;
import Modelos.Rutina;
import Modelos.Socio;
import com.toedter.calendar.JDateChooser;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author A
 */
public class GuiVerRutinas extends javax.swing.JDialog {

    DefaultTableModel tablaRutinasDefault;
    ControladorJReport reporte;
    
    DefaultTableModel tablaAerobicoLunesDefault;
    DefaultTableModel tablaAerobicoMartesDefault;
    DefaultTableModel tablaAerobicoMiercolesDefault;
    DefaultTableModel tablaAerobicoJuevesDefault;
    DefaultTableModel tablaAerobicoViernesDefault;
    DefaultTableModel tablaAerobicoSabadoDefault;
    DefaultTableModel tablaAerobicoDomingoDefault;
    DefaultTableModel tablaMusculacionLunesDefault;
    DefaultTableModel tablaMusculacionMartesDefault;
    DefaultTableModel tablaMusculacionMiercolesDefault;
    DefaultTableModel tablaMusculacionJuevesDefault;
    DefaultTableModel tablaMusculacionViernesDefault;
    DefaultTableModel tablaMusculacionSabadoDefault;
    DefaultTableModel tablaMusculacionDomingoDefault;
    
    public GuiVerRutinas(java.awt.Frame parent, boolean modal) throws JRException, ClassNotFoundException, SQLException {
        super(parent, modal);
        initComponents();
        tablaRutinasDefault = (DefaultTableModel) tablaRutinas.getModel();
        reporte = new ControladorJReport("rutina.jasper");
        
        tablaAerobicoLunesDefault = (DefaultTableModel) tablaAerobicoLunes.getModel();
         tablaAerobicoMartesDefault = (DefaultTableModel) tablaAerobicoMartes.getModel();
         tablaAerobicoMiercolesDefault = (DefaultTableModel) tablaAerobicoMiercoles.getModel();
         tablaAerobicoJuevesDefault = (DefaultTableModel) tablaAerobicoJueves.getModel();
         tablaAerobicoViernesDefault = (DefaultTableModel) tablaAerobicoViernes.getModel();
         tablaAerobicoSabadoDefault = (DefaultTableModel) tablaAerobicoSabado.getModel();
         tablaAerobicoDomingoDefault = (DefaultTableModel) tablaAerobicoDomingo.getModel();
         tablaMusculacionLunesDefault = (DefaultTableModel) tablaMusculacionLunes2.getModel();
         tablaMusculacionMartesDefault = (DefaultTableModel) tablaMusculacionMartes.getModel();
         tablaMusculacionMiercolesDefault = (DefaultTableModel) tablaMusculacionMiercoles.getModel();
         tablaMusculacionJuevesDefault = (DefaultTableModel) tablaMusculacionJueves.getModel();
         tablaMusculacionViernesDefault = (DefaultTableModel) tablaMusculacionViernes.getModel();
         tablaMusculacionSabadoDefault = (DefaultTableModel) tablaMusculacionSabado.getModel();
         tablaMusculacionDomingoDefault = (DefaultTableModel) tablaMusculacionDomingo.getModel();
         
         tablaRutinas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (tablaRutinas.getSelectedRow() != -1) {
                    limpiarTablas();
                    rutinaSeleccionada();
                    btnImprimir.setEnabled(true);
                    btnMail.setEnabled(true);
                } else {
                    limpiarTablas();
                    LimpiarCampos();
                    btnImprimir.setEnabled(false);
                    btnMail.setEnabled(false);
                }
            }
        });
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
    private void rutinaSeleccionada() {
        int r = tablaRutinas.getSelectedRow();
        Rutina ru = Rutina.first("id = ?", tablaRutinas.getValueAt(r, 0));
        //guiCrearRutina.getFechaInicio().setDate(dateToMySQLDate(ru.getDate("fecha_inicio"), true));
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        String strFecha = dateToMySQLDate(ru.getDate("fecha_inicio"), true);
        Date fecha = null;
        try {
            fecha = formatoDelTexto.parse(strFecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        fechaInicio.setDate(fecha);
        SimpleDateFormat formatoDelTexto2 = new SimpleDateFormat("dd/MM/yyyy");
        String strFecha2 = dateToMySQLDate(ru.getDate("fecha_fin"), true);
        Date fecha2 = null;
        try {
            fecha2 = formatoDelTexto2.parse(strFecha2);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        fechaFin.setDate(fecha2);
        areaObjetivo.setText(ru.getString("descrip"));
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
                                tablaAerobicoLunesDefault.addRow(row);
                            } else {
                                Object[] row = new Object[5];
                                row[0] = e.get("id");
                                row[1] = e.get("grupo");
                                row[2] = e.get("ejercicio");
                                row[3] = de.get("series");
                                row[4] = de.get("repeticiones");
                                tablaMusculacionLunesDefault.addRow(row);
                            }
                            break;
                        case "MARTES":
                            if (e.getString("grupo").equals("AEROBICO")) {
                                Object[] row = new Object[3];
                                row[0] = e.get("id");
                                row[1] = e.get("ejercicio");
                                row[2] = de.get("tiempo");
                                tablaAerobicoMartesDefault.addRow(row);
                            } else {
                                Object[] row = new Object[5];
                                row[0] = e.get("id");
                                row[1] = e.get("grupo");
                                row[2] = e.get("ejercicio");
                                row[3] = de.get("series");
                                row[4] = de.get("repeticiones");
                                tablaMusculacionMartesDefault.addRow(row);
                            }
                            break;
                        case "MIERCOLES":
                            if (e.getString("grupo").equals("AEROBICO")) {
                                Object[] row = new Object[3];
                                row[0] = e.get("id");
                                row[1] = e.get("ejercicio");
                                row[2] = de.get("tiempo");
                                tablaAerobicoMiercolesDefault.addRow(row);
                            } else {
                                Object[] row = new Object[5];
                                row[0] = e.get("id");
                                row[1] = e.get("grupo");
                                row[2] = e.get("ejercicio");
                                row[3] = de.get("series");
                                row[4] = de.get("repeticiones");
                                tablaMusculacionMiercolesDefault.addRow(row);
                            }
                            break;
                        case "JUEVES":
                            if (e.getString("grupo").equals("AEROBICO")) {
                                Object[] row = new Object[3];
                                row[0] = e.get("id");
                                row[1] = e.get("ejercicio");
                                row[2] = de.get("tiempo");
                                tablaAerobicoJuevesDefault.addRow(row);
                            } else {
                                Object[] row = new Object[5];
                                row[0] = e.get("id");
                                row[1] = e.get("grupo");
                                row[2] = e.get("ejercicio");
                                row[3] = de.get("series");
                                row[4] = de.get("repeticiones");
                                tablaMusculacionJuevesDefault.addRow(row);
                            }
                            break;
                        case "VIERNES":
                            if (e.getString("grupo").equals("AEROBICO")) {
                                Object[] row = new Object[3];
                                row[0] = e.get("id");
                                row[1] = e.get("ejercicio");
                                row[2] = de.get("tiempo");
                                tablaAerobicoViernesDefault.addRow(row);
                            } else {
                                Object[] row = new Object[5];
                                row[0] = e.get("id");
                                row[1] = e.get("grupo");
                                row[2] = e.get("ejercicio");
                                row[3] = de.get("series");
                                row[4] = de.get("repeticiones");
                                tablaMusculacionViernesDefault.addRow(row);
                            }
                            break;
                        case "SABADO":
                            if (e.getString("grupo").equals("AEROBICO")) {
                                Object[] row = new Object[3];
                                row[0] = e.get("id");
                                row[1] = e.get("ejercicio");
                                row[2] = de.get("tiempo");
                                tablaAerobicoSabadoDefault.addRow(row);
                            } else {
                                Object[] row = new Object[5];
                                row[0] = e.get("id");
                                row[1] = e.get("grupo");
                                row[2] = e.get("ejercicio");
                                row[3] = de.get("series");
                                row[4] = de.get("repeticiones");
                                tablaMusculacionSabadoDefault.addRow(row);
                            }
                            break;
                        case "DOMINGO":
                            if (e.getString("grupo").equals("AEROBICO")) {
                                Object[] row = new Object[3];
                                row[0] = e.get("id");
                                row[1] = e.get("ejercicio");
                                row[2] = de.get("tiempo");
                                tablaAerobicoDomingoDefault.addRow(row);
                            } else {
                                Object[] row = new Object[5];
                                row[0] = e.get("id");
                                row[1] = e.get("grupo");
                                row[2] = e.get("ejercicio");
                                row[3] = de.get("series");
                                row[4] = de.get("repeticiones");
                                tablaMusculacionDomingoDefault.addRow(row);
                            }
                            break;
                    }
                }
            }
        }

    }


    public void LimpiarCampos(){
        fechaInicio.setDate(Calendar.getInstance().getTime());
        areaObjetivo.setText("");
    }
    
    public void limpiarTablas(){
        tablaAerobicoLunesDefault.setRowCount(0);
        tablaMusculacionLunesDefault.setRowCount(0);
        tablaAerobicoMartesDefault.setRowCount(0);
        tablaMusculacionMartesDefault.setRowCount(0);
        tablaAerobicoMiercolesDefault.setRowCount(0);
        tablaMusculacionMiercolesDefault.setRowCount(0);
        tablaAerobicoJuevesDefault.setRowCount(0);
        tablaMusculacionJuevesDefault.setRowCount(0);
        tablaAerobicoViernesDefault.setRowCount(0);
        tablaMusculacionViernesDefault.setRowCount(0);
        tablaAerobicoSabadoDefault.setRowCount(0);
        tablaMusculacionSabadoDefault.setRowCount(0);
        tablaAerobicoDomingoDefault.setRowCount(0);
        tablaMusculacionDomingoDefault.setRowCount(0);
    }
    
    public DefaultTableModel getTablaRutinasDefault() {
        return tablaRutinasDefault;
    }

    public JTextArea getAreaObjetivo() {
        return areaObjetivo;
    }

    public JDateChooser getFechaFin() {
        return fechaFin;
    }

    public JDateChooser getFechaInicio() {
        return fechaInicio;
    }

    public JTable getTablaRutinas() {
        return tablaRutinas;
    }

    public JTextField getTxtSocio() {
        return txtSocio;
    }

    public JTable getTablaAerobicoDomingo() {
        return tablaAerobicoDomingo;
    }

    public JTable getTablaAerobicoJueves() {
        return tablaAerobicoJueves;
    }

    public JTable getTablaAerobicoLunes() {
        return tablaAerobicoLunes;
    }

    public JTable getTablaAerobicoMartes() {
        return tablaAerobicoMartes;
    }

    public JTable getTablaAerobicoMiercoles() {
        return tablaAerobicoMiercoles;
    }

    public JTable getTablaAerobicoSabado() {
        return tablaAerobicoSabado;
    }

    public JTable getTablaAerobicoViernes() {
        return tablaAerobicoViernes;
    }

    public JTable getTablaMusculacionDomingo() {
        return tablaMusculacionDomingo;
    }

    public JTable getTablaMusculacionJueves() {
        return tablaMusculacionJueves;
    }

    public JTable getTablaMusculacionLunes2() {
        return tablaMusculacionLunes2;
    }

    public JTable getTablaMusculacionMartes() {
        return tablaMusculacionMartes;
    }

    public JTable getTablaMusculacionMiercoles() {
        return tablaMusculacionMiercoles;
    }

    public JTable getTablaMusculacionSabado() {
        return tablaMusculacionSabado;
    }

    public JTable getTablaMusculacionViernes() {
        return tablaMusculacionViernes;
    }

    public JTabbedPane getTabsPanel() {
        return tabsPanel;
    }

    public JTextField getTxtID() {
        return txtID;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txtSocio = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRutinas = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        fechaInicio = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        fechaFin = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        areaObjetivo = new javax.swing.JTextArea();
        tabsPanel = new javax.swing.JTabbedPane();
        panelLunes = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        tablaMusculacionLunes2 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaAerobicoLunes = new javax.swing.JTable();
        panelMartes = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaMusculacionMartes = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablaAerobicoMartes = new javax.swing.JTable();
        panelMiercoles = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tablaMusculacionMiercoles = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tablaAerobicoMiercoles = new javax.swing.JTable();
        panelJueves = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tablaMusculacionJueves = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tablaAerobicoJueves = new javax.swing.JTable();
        panelViernes = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tablaMusculacionViernes = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tablaAerobicoViernes = new javax.swing.JTable();
        panelSabado = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tablaMusculacionSabado = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        tablaAerobicoSabado = new javax.swing.JTable();
        panelDomingo = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        tablaMusculacionDomingo = new javax.swing.JTable();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane21 = new javax.swing.JScrollPane();
        tablaAerobicoDomingo = new javax.swing.JTable();
        btnMail = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtID = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setText("Socio");

        txtSocio.setEditable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Rutinas"));

        tablaRutinas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Inicio", "Fin"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaRutinas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Rutina"));

        jLabel4.setText("Fecha de inicio");

        fechaInicio.setDateFormatString("dd-MM-yyyy");
        fechaInicio.setEnabled(false);

        jLabel5.setText("Hasta");

        fechaFin.setDateFormatString("dd-MM-yyyy");
        fechaFin.setEnabled(false);

        jLabel3.setText("Objetivo");

        areaObjetivo.setColumns(20);
        areaObjetivo.setRows(5);
        areaObjetivo.setEnabled(false);
        jScrollPane4.setViewportView(areaObjetivo);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Musculacion"));

        tablaMusculacionLunes2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Grupo", "Ejercicio", "Series", "Repeticiones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaMusculacionLunes2.setEnabled(false);
        jScrollPane22.setViewportView(tablaMusculacionLunes2);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Aerobico"));

        tablaAerobicoLunes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Ejercicio", "Tiempo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaAerobicoLunes.setEnabled(false);
        jScrollPane7.setViewportView(tablaAerobicoLunes);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelLunesLayout = new javax.swing.GroupLayout(panelLunes);
        panelLunes.setLayout(panelLunesLayout);
        panelLunesLayout.setHorizontalGroup(
            panelLunesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelLunesLayout.setVerticalGroup(
            panelLunesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLunesLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabsPanel.addTab("Lunes", panelLunes);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Musculacion"));

        tablaMusculacionMartes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Grupo", "Ejercicio", "Series", "Repeticiones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaMusculacionMartes.setEnabled(false);
        jScrollPane8.setViewportView(tablaMusculacionMartes);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Aerobico"));

        tablaAerobicoMartes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Ejercicio", "Tiempo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaAerobicoMartes.setEnabled(false);
        jScrollPane9.setViewportView(tablaAerobicoMartes);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelMartesLayout = new javax.swing.GroupLayout(panelMartes);
        panelMartes.setLayout(panelMartesLayout);
        panelMartesLayout.setHorizontalGroup(
            panelMartesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelMartesLayout.setVerticalGroup(
            panelMartesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMartesLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabsPanel.addTab("Martes", panelMartes);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Musculacion"));

        tablaMusculacionMiercoles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Grupo", "Ejercicio", "Series", "Repeticiones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaMusculacionMiercoles.setEnabled(false);
        jScrollPane10.setViewportView(tablaMusculacionMiercoles);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Aerobico"));

        tablaAerobicoMiercoles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Ejercicio", "Tiempo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaAerobicoMiercoles.setEnabled(false);
        jScrollPane11.setViewportView(tablaAerobicoMiercoles);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelMiercolesLayout = new javax.swing.GroupLayout(panelMiercoles);
        panelMiercoles.setLayout(panelMiercolesLayout);
        panelMiercolesLayout.setHorizontalGroup(
            panelMiercolesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelMiercolesLayout.setVerticalGroup(
            panelMiercolesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMiercolesLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabsPanel.addTab("Miercoles", panelMiercoles);

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Musculacion"));

        tablaMusculacionJueves.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Grupo", "Ejercicio", "Series", "Repeticiones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaMusculacionJueves.setEnabled(false);
        jScrollPane12.setViewportView(tablaMusculacionJueves);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Aerobico"));

        tablaAerobicoJueves.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Ejercicio", "Tiempo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaAerobicoJueves.setEnabled(false);
        jScrollPane13.setViewportView(tablaAerobicoJueves);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelJuevesLayout = new javax.swing.GroupLayout(panelJueves);
        panelJueves.setLayout(panelJuevesLayout);
        panelJuevesLayout.setHorizontalGroup(
            panelJuevesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelJuevesLayout.setVerticalGroup(
            panelJuevesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelJuevesLayout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabsPanel.addTab("Jueves", panelJueves);

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("Musculacion"));

        tablaMusculacionViernes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Grupo", "Ejercicio", "Series", "Repeticiones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaMusculacionViernes.setEnabled(false);
        jScrollPane14.setViewportView(tablaMusculacionViernes);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder("Aerobico"));

        tablaAerobicoViernes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Ejercicio", "Tiempo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaAerobicoViernes.setEnabled(false);
        jScrollPane15.setViewportView(tablaAerobicoViernes);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelViernesLayout = new javax.swing.GroupLayout(panelViernes);
        panelViernes.setLayout(panelViernesLayout);
        panelViernesLayout.setHorizontalGroup(
            panelViernesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelViernesLayout.setVerticalGroup(
            panelViernesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelViernesLayout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabsPanel.addTab("Viernes", panelViernes);

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder("Musculacion"));

        tablaMusculacionSabado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Grupo", "Ejercicio", "Series", "Repeticiones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaMusculacionSabado.setEnabled(false);
        jScrollPane16.setViewportView(tablaMusculacionSabado);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
        );

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder("Aerobico"));

        tablaAerobicoSabado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Ejercicio", "Tiempo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaAerobicoSabado.setEnabled(false);
        jScrollPane17.setViewportView(tablaAerobicoSabado);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelSabadoLayout = new javax.swing.GroupLayout(panelSabado);
        panelSabado.setLayout(panelSabadoLayout);
        panelSabadoLayout.setHorizontalGroup(
            panelSabadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelSabadoLayout.setVerticalGroup(
            panelSabadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSabadoLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabsPanel.addTab("Sabado", panelSabado);

        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder("Musculacion"));

        tablaMusculacionDomingo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Grupo", "Ejercicio", "Series", "Repeticiones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaMusculacionDomingo.setEnabled(false);
        jScrollPane20.setViewportView(tablaMusculacionDomingo);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
        );

        jPanel28.setBorder(javax.swing.BorderFactory.createTitledBorder("Aerobico"));

        tablaAerobicoDomingo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Ejercicio", "Tiempo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaAerobicoDomingo.setEnabled(false);
        jScrollPane21.setViewportView(tablaAerobicoDomingo);

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelDomingoLayout = new javax.swing.GroupLayout(panelDomingo);
        panelDomingo.setLayout(panelDomingoLayout);
        panelDomingoLayout.setHorizontalGroup(
            panelDomingoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelDomingoLayout.setVerticalGroup(
            panelDomingoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDomingoLayout.createSequentialGroup()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabsPanel.addTab("Domingo", panelDomingo);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)))
                        .addGap(18, 18, 18)
                        .addComponent(fechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4)
                    .addComponent(tabsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(fechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addGap(26, 26, 26)
                .addComponent(tabsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnMail.setText("Enviar por mail");
        btnMail.setEnabled(false);
        btnMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMailActionPerformed(evt);
            }
        });

        btnImprimir.setText("Imprimir");
        btnImprimir.setEnabled(false);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtID.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSocio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnMail, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtSocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnMail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMailActionPerformed
        try {
            int r = tablaRutinas.getSelectedRow();
            Integer idRutina = Integer.parseInt(String.valueOf(tablaRutinas.getValueAt(r, 0)));
            String ruta = reporte.obtenerRutina(idRutina);
            boolean res = EnvioEmailControlador.enviarMailManualDieta(ruta, Socio.findFirst("ID_DATOS_PERS = ?",txtID.getText() ).getInteger("ID_DATOS_PERS"), "rutina");
            if (res) {
                JOptionPane.showMessageDialog(this, "Email enviado exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Falló el envio, revise la conexión", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
            File archivo = new File(ruta);
            archivo.delete();
        } catch (ClassNotFoundException | SQLException | JRException ex) {
            Logger.getLogger(VerDietaGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnMailActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        int r = tablaRutinas.getSelectedRow();
        Integer id = Integer.parseInt(String.valueOf(tablaRutinas.getValueAt(r, 0)));
        try {
            reporte.mostrarRutina(id);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GuiVerRutinas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GuiVerRutinas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(GuiVerRutinas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GuiVerRutinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiVerRutinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiVerRutinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiVerRutinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GuiVerRutinas dialog = null;
                try {
                    dialog = new GuiVerRutinas(new javax.swing.JFrame(), true);
                } catch (JRException ex) {
                    Logger.getLogger(GuiVerRutinas.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GuiVerRutinas.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(GuiVerRutinas.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaObjetivo;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnMail;
    private com.toedter.calendar.JDateChooser fechaFin;
    private com.toedter.calendar.JDateChooser fechaInicio;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPanel panelDomingo;
    private javax.swing.JPanel panelJueves;
    private javax.swing.JPanel panelLunes;
    private javax.swing.JPanel panelMartes;
    private javax.swing.JPanel panelMiercoles;
    private javax.swing.JPanel panelSabado;
    private javax.swing.JPanel panelViernes;
    private javax.swing.JTable tablaAerobicoDomingo;
    private javax.swing.JTable tablaAerobicoJueves;
    private javax.swing.JTable tablaAerobicoLunes;
    private javax.swing.JTable tablaAerobicoMartes;
    private javax.swing.JTable tablaAerobicoMiercoles;
    private javax.swing.JTable tablaAerobicoSabado;
    private javax.swing.JTable tablaAerobicoViernes;
    private javax.swing.JTable tablaMusculacionDomingo;
    private javax.swing.JTable tablaMusculacionJueves;
    private javax.swing.JTable tablaMusculacionLunes2;
    private javax.swing.JTable tablaMusculacionMartes;
    private javax.swing.JTable tablaMusculacionMiercoles;
    private javax.swing.JTable tablaMusculacionSabado;
    private javax.swing.JTable tablaMusculacionViernes;
    private javax.swing.JTable tablaRutinas;
    private javax.swing.JTabbedPane tabsPanel;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtSocio;
    // End of variables declaration//GEN-END:variables
}
