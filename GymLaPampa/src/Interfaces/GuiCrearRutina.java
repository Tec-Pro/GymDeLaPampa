/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author A
 */
public class GuiCrearRutina extends javax.swing.JInternalFrame {

    DefaultTableModel tablaSociosDefault;
    DefaultTableModel tablaEjerciciosDefault;
    DefaultTableModel tablaRutinasSocioDefault;
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

    boolean apreteModificar = false;
    boolean apreteNuevoModificar = false;

    public GuiCrearRutina() {
        initComponents();
        btnQuitarMusculacion.setText("Quitar ejercicio");
        btnQuitarAerobico.setText("Quitar ejercicio");
        tablaSociosDefault = (DefaultTableModel) tablaSocios.getModel();
        tablaEjerciciosDefault = (DefaultTableModel) tablaEjercicio.getModel();
        tablaRutinasSocioDefault = (DefaultTableModel) tablaRutinasSocio.getModel();
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

        fechaInicio.setDateFormatString("dd/MM/yyyy");
        fechaFin.setDateFormatString("dd/MM/yyyy");
        fechaInicio.getDateEditor().setEnabled(false);
        fechaFin.getDateEditor().setEnabled(false);
        fechaInicio.setDate(Calendar.getInstance().getTime());

        tablaEjercicio.getColumnModel().getColumn(0).setPreferredWidth(20);
        tablaEjercicio.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaEjercicio.getColumnModel().getColumn(2).setPreferredWidth(300);

        //Esto me sirve para despues poder ordenar los ejercicios por grupo
        //RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tablaEjerciciosDefault);
        //tablaEjercicio.setRowSorter(sorter);
        //
    }

    public void setActionListener(ActionListener al) {
        botEliminarCancelar.addActionListener(al);
        botGuardar.addActionListener(al);
        botModif.addActionListener(al);
        botNuevo.addActionListener(al);
        btnQuitarMusculacion.addActionListener(al);
        btnQuitarAerobico.addActionListener(al);
    }
    
    public void ApreteBotonNuevoModificar(){
        botNuevo.setEnabled(false);
        botModif.setEnabled(false);
        txtBuscarEjercicio1.setEnabled(true);
        botEliminarCancelar.setEnabled(true);
        botEliminarCancelar.setText("Cancelar");
        botGuardar.setEnabled(true);
        tablaEjercicio.setEnabled(true);
        fechaInicio.setEnabled(true);
        fechaFin.setEnabled(true);
        areaObjetivo.setEnabled(true);
        tablaAerobicoLunes.setEnabled(true);
        tablaMusculacionLunes2.setEnabled(true);
        tablaAerobicoMartes.setEnabled(true);
        tablaMusculacionMartes.setEnabled(true);
        tablaAerobicoMiercoles.setEnabled(true);
        tablaMusculacionMiercoles.setEnabled(true);
        tablaAerobicoJueves.setEnabled(true);
        tablaMusculacionJueves.setEnabled(true);
        tablaAerobicoViernes.setEnabled(true);
        tablaMusculacionViernes.setEnabled(true);
        tablaAerobicoSabado.setEnabled(true);
        tablaMusculacionSabado.setEnabled(true);
        tablaAerobicoDomingo.setEnabled(true);
        tablaMusculacionDomingo.setEnabled(true);
    }

    public void EstadoInicial(){
        botNuevo.setEnabled(true);
        botModif.setEnabled(false);
        txtBuscarEjercicio1.setEnabled(false);
        botEliminarCancelar.setEnabled(false);
        botEliminarCancelar.setText("Eliminar");
        botGuardar.setEnabled(false);
       tablaEjerciciosDefault.setRowCount(0);
        fechaInicio.setEnabled(false);
        fechaFin.setEnabled(false);
        areaObjetivo.setEnabled(false);
        tablaAerobicoLunes.setEnabled(false);
        tablaMusculacionLunes2.setEnabled(false);
        tablaAerobicoMartes.setEnabled(false);
        tablaMusculacionMartes.setEnabled(false);
        tablaAerobicoMiercoles.setEnabled(false);
        tablaMusculacionMiercoles.setEnabled(false);
        tablaAerobicoJueves.setEnabled(false);
        tablaMusculacionJueves.setEnabled(false);
        tablaAerobicoViernes.setEnabled(false);
        tablaMusculacionViernes.setEnabled(false);
        tablaAerobicoSabado.setEnabled(false);
        tablaMusculacionSabado.setEnabled(false);
        tablaAerobicoDomingo.setEnabled(false);
        tablaMusculacionDomingo.setEnabled(false);
    }
    
    public void reClick(){
        EstadoInicial();
        LimpiarCampos();
    }
    
    public void EstadoLuegoDeModificar(){
        botNuevo.setEnabled(true);
        botModif.setEnabled(true);
        txtBuscarEjercicio1.setEnabled(false);
        botEliminarCancelar.setEnabled(true);
        botGuardar.setEnabled(false);
        tablaEjercicio.setEnabled(false);
        fechaInicio.setEnabled(false);
        fechaFin.setEnabled(false);
        areaObjetivo.setEnabled(false);
        tablaAerobicoLunes.setEnabled(false);
        tablaMusculacionLunes2.setEnabled(false);
        tablaAerobicoMartes.setEnabled(false);
        tablaMusculacionMartes.setEnabled(false);
        tablaAerobicoMiercoles.setEnabled(false);
        tablaMusculacionMiercoles.setEnabled(false);
        tablaAerobicoJueves.setEnabled(false);
        tablaMusculacionJueves.setEnabled(false);
        tablaAerobicoViernes.setEnabled(false);
        tablaMusculacionViernes.setEnabled(false);
        tablaAerobicoSabado.setEnabled(false);
        tablaMusculacionSabado.setEnabled(false);
        tablaAerobicoDomingo.setEnabled(false);
        tablaMusculacionDomingo.setEnabled(false);
    }
    
    public void EstadoRutinaSeleccionada(){
        botNuevo.setEnabled(true);
        botModif.setEnabled(true);
        botEliminarCancelar.setEnabled(true);
        botGuardar.setEnabled(false);
        tablaEjercicio.setEnabled(false);
        fechaInicio.setEnabled(false);
        fechaFin.setEnabled(false);
        areaObjetivo.setEnabled(false);
        tablaAerobicoLunes.setEnabled(false);
        tablaMusculacionLunes2.setEnabled(false);
        tablaAerobicoMartes.setEnabled(false);
        tablaMusculacionMartes.setEnabled(false);
        tablaAerobicoMiercoles.setEnabled(false);
        tablaMusculacionMiercoles.setEnabled(false);
        tablaAerobicoJueves.setEnabled(false);
        tablaMusculacionJueves.setEnabled(false);
        tablaAerobicoViernes.setEnabled(false);
        tablaMusculacionViernes.setEnabled(false);
        tablaAerobicoSabado.setEnabled(false);
        tablaMusculacionSabado.setEnabled(false);
        tablaAerobicoDomingo.setEnabled(false);
        tablaMusculacionDomingo.setEnabled(false);
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
    
    public DefaultTableModel getTablaAerobicoLunesDefault() {
        return tablaAerobicoLunesDefault;
    }

    public DefaultTableModel getTablaAerobicoMartesDefault() {
        return tablaAerobicoMartesDefault;
    }

    public boolean isApreteModificar() {
        return apreteModificar;
    }

    public void setApreteModificar(boolean apreteModificar) {
        this.apreteModificar = apreteModificar;
    }

    public void setApreteNuevoModificar(boolean apreteNuevoModificar) {
        this.apreteNuevoModificar = apreteNuevoModificar;
    }

    public JMenuItem getBtnQuitarAerobico() {
        return btnQuitarAerobico;
    }

    public JMenuItem getBtnQuitarMusculacion() {
        return btnQuitarMusculacion;
    }

    public boolean isApreteNuevoModificar() {
        return apreteNuevoModificar;
    }
   
    public DefaultTableModel getTablaAerobicoMiercolesDefault() {
        return tablaAerobicoMiercolesDefault;
    }

    public DefaultTableModel getTablaAerobicoJuevesDefault() {
        return tablaAerobicoJuevesDefault;
    }

    public DefaultTableModel getTablaAerobicoViernesDefault() {
        return tablaAerobicoViernesDefault;
    }

    public DefaultTableModel getTablaAerobicoSabadoDefault() {
        return tablaAerobicoSabadoDefault;
    }

    public DefaultTableModel getTablaAerobicoDomingoDefault() {
        return tablaAerobicoDomingoDefault;
    }

    public DefaultTableModel getTablaMusculacionLunesDefault() {
        return tablaMusculacionLunesDefault;
    }

    public DefaultTableModel getTablaMusculacionMartesDefault() {
        return tablaMusculacionMartesDefault;
    }

    public DefaultTableModel getTablaMusculacionMiercolesDefault() {
        return tablaMusculacionMiercolesDefault;
    }

    public DefaultTableModel getTablaMusculacionJuevesDefault() {
        return tablaMusculacionJuevesDefault;
    }

    public DefaultTableModel getTablaMusculacionViernesDefault() {
        return tablaMusculacionViernesDefault;
    }

    public DefaultTableModel getTablaMusculacionSabadoDefault() {
        return tablaMusculacionSabadoDefault;
    }

    public DefaultTableModel getTablaMusculacionDomingoDefault() {
        return tablaMusculacionDomingoDefault;
    }

    public DefaultTableModel getTablaSociosDefault() {
        return tablaSociosDefault;
    }

    public DefaultTableModel getTablaEjerciciosDefault() {
        return tablaEjerciciosDefault;
    }

    public DefaultTableModel getTablaRutinasSocioDefault() {
        return tablaRutinasSocioDefault;
    }

    public JTextArea getAreaObjetivo() {
        return areaObjetivo;
    }

    public JButton getBotEliminarCancelar() {
        return botEliminarCancelar;
    }

    public JButton getBotGuardar() {
        return botGuardar;
    }

    public JButton getBotModif() {
        return botModif;
    }

    public JButton getBotNuevo() {
        return botNuevo;
    }

    public JDateChooser getFechaFin() {
        return fechaFin;
    }

    public JDateChooser getFechaInicio() {
        return fechaInicio;
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

    public JTable getTablaEjercicio() {
        return tablaEjercicio;
    }

    public JTable getTablaMusculacionDomingo() {
        return tablaMusculacionDomingo;
    }

    public JTable getTablaMusculacionJueves() {
        return tablaMusculacionJueves;
    }

    public JTable getTablaMusculacionLunes() {
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

    public JTable getTablaRutinasSocio() {
        return tablaRutinasSocio;
    }

    public JTable getTablaSocios() {
        return tablaSocios;
    }

    public JTabbedPane getTabsPanel() {
        return tabsPanel;
    }

    public JTextField getTxtBuscarEjercicio() {
        return txtBuscarEjercicio1;
    }

    public JTextField getTxtBuscarEjercicio1() {
        return txtBuscarEjercicio1;
    }

    public JTextField getTxtBuscarSocio() {
        return txtBuscarSocio;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuMusculacion = new javax.swing.JPopupMenu();
        btnQuitarMusculacion = new javax.swing.JMenuItem();
        menuAerobico = new javax.swing.JPopupMenu();
        btnQuitarAerobico = new javax.swing.JMenuItem();
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
        botNuevo = new javax.swing.JButton();
        botModif = new javax.swing.JButton();
        botEliminarCancelar = new javax.swing.JButton();
        botGuardar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        txtBuscarSocio = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaSocios = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaEjercicio = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtBuscarEjercicio1 = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        tablaRutinasSocio = new javax.swing.JTable();

        btnQuitarMusculacion.setText("jMenuItem1");
        menuMusculacion.add(btnQuitarMusculacion);

        btnQuitarAerobico.setText("jMenuItem1");
        menuAerobico.add(btnQuitarAerobico);

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestion de rutinas");
        setPreferredSize(new java.awt.Dimension(976, 614));

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
        tablaMusculacionLunes2.setComponentPopupMenu(menuMusculacion);
        tablaMusculacionLunes2.setEnabled(false);
        jScrollPane22.setViewportView(tablaMusculacionLunes2);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
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
        tablaAerobicoLunes.setComponentPopupMenu(menuAerobico);
        tablaAerobicoLunes.setEnabled(false);
        jScrollPane7.setViewportView(tablaAerobicoLunes);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
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
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        tablaMusculacionMartes.setComponentPopupMenu(menuMusculacion);
        tablaMusculacionMartes.setEnabled(false);
        jScrollPane8.setViewportView(tablaMusculacionMartes);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
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
        tablaAerobicoMartes.setComponentPopupMenu(menuAerobico);
        tablaAerobicoMartes.setEnabled(false);
        jScrollPane9.setViewportView(tablaAerobicoMartes);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9)
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
        tablaMusculacionMiercoles.setComponentPopupMenu(menuMusculacion);
        tablaMusculacionMiercoles.setEnabled(false);
        jScrollPane10.setViewportView(tablaMusculacionMiercoles);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
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
        tablaAerobicoMiercoles.setComponentPopupMenu(menuAerobico);
        tablaAerobicoMiercoles.setEnabled(false);
        jScrollPane11.setViewportView(tablaAerobicoMiercoles);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11)
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
        tablaMusculacionJueves.setComponentPopupMenu(menuMusculacion);
        tablaMusculacionJueves.setEnabled(false);
        jScrollPane12.setViewportView(tablaMusculacionJueves);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
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
        tablaAerobicoJueves.setComponentPopupMenu(menuAerobico);
        tablaAerobicoJueves.setEnabled(false);
        jScrollPane13.setViewportView(tablaAerobicoJueves);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13)
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
        tablaMusculacionViernes.setComponentPopupMenu(menuMusculacion);
        tablaMusculacionViernes.setEnabled(false);
        jScrollPane14.setViewportView(tablaMusculacionViernes);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
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
        tablaAerobicoViernes.setComponentPopupMenu(menuAerobico);
        tablaAerobicoViernes.setEnabled(false);
        jScrollPane15.setViewportView(tablaAerobicoViernes);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15)
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
        tablaMusculacionSabado.setComponentPopupMenu(menuMusculacion);
        tablaMusculacionSabado.setEnabled(false);
        jScrollPane16.setViewportView(tablaMusculacionSabado);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
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
        tablaAerobicoSabado.setComponentPopupMenu(menuAerobico);
        tablaAerobicoSabado.setEnabled(false);
        jScrollPane17.setViewportView(tablaAerobicoSabado);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane17)
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
        tablaMusculacionDomingo.setComponentPopupMenu(menuMusculacion);
        tablaMusculacionDomingo.setEnabled(false);
        jScrollPane20.setViewportView(tablaMusculacionDomingo);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
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
        tablaAerobicoDomingo.setComponentPopupMenu(menuAerobico);
        tablaAerobicoDomingo.setEnabled(false);
        jScrollPane21.setViewportView(tablaAerobicoDomingo);

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane21)
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

        botNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/agregar.png"))); // NOI18N
        botNuevo.setText("Nuevo");

        botModif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/modificar.png"))); // NOI18N
        botModif.setText("Modificar");
        botModif.setEnabled(false);

        botEliminarCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/borrar.png"))); // NOI18N
        botEliminarCancelar.setText("Eliminar");
        botEliminarCancelar.setEnabled(false);

        botGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar.png"))); // NOI18N
        botGuardar.setText("Guardar");
        botGuardar.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tabsPanel)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
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
                            .addComponent(jScrollPane4))
                        .addGap(101, 101, 101))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(botNuevo)
                .addGap(0, 0, 0)
                .addComponent(botModif)
                .addGap(0, 0, 0)
                .addComponent(botEliminarCancelar)
                .addGap(0, 0, 0)
                .addComponent(botGuardar)
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(tabsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botNuevo)
                    .addComponent(botModif)
                    .addComponent(botEliminarCancelar)
                    .addComponent(botGuardar))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Socios"));

        jLabel1.setText("Buscar");

        tablaSocios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tablaSocios);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscarSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscarSocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Ejercicios"));

        tablaEjercicio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Grupo", "Ejercicio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaEjercicio.setEnabled(false);
        jScrollPane5.setViewportView(tablaEjercicio);

        jLabel7.setText("Buscar");

        txtBuscarEjercicio1.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscarEjercicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtBuscarEjercicio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
        );

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder("Rutinas del socio"));

        tablaRutinasSocio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Fecha inicio", "Fecha fin"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane18.setViewportView(tablaRutinasSocio);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaObjetivo;
    private javax.swing.JButton botEliminarCancelar;
    private javax.swing.JButton botGuardar;
    private javax.swing.JButton botModif;
    private javax.swing.JButton botNuevo;
    private javax.swing.JMenuItem btnQuitarAerobico;
    private javax.swing.JMenuItem btnQuitarMusculacion;
    private com.toedter.calendar.JDateChooser fechaFin;
    private com.toedter.calendar.JDateChooser fechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
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
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu menuAerobico;
    private javax.swing.JPopupMenu menuMusculacion;
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
    private javax.swing.JTable tablaEjercicio;
    private javax.swing.JTable tablaMusculacionDomingo;
    private javax.swing.JTable tablaMusculacionJueves;
    private javax.swing.JTable tablaMusculacionLunes2;
    private javax.swing.JTable tablaMusculacionMartes;
    private javax.swing.JTable tablaMusculacionMiercoles;
    private javax.swing.JTable tablaMusculacionSabado;
    private javax.swing.JTable tablaMusculacionViernes;
    private javax.swing.JTable tablaRutinasSocio;
    private javax.swing.JTable tablaSocios;
    private javax.swing.JTabbedPane tabsPanel;
    private javax.swing.JTextField txtBuscarEjercicio1;
    private javax.swing.JTextField txtBuscarSocio;
    // End of variables declaration//GEN-END:variables
}
