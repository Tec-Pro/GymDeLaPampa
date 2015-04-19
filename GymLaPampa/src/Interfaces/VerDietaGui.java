/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Controladores.ControladorJReport;
import Controladores.EnvioEmailControlador;
import Modelos.Alimento;
import Modelos.AlimentosDietas;
import Modelos.Socio;
import java.awt.Cursor;
import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author NicoOrcasitas
 */
public class VerDietaGui extends javax.swing.JDialog implements ChangeListener {

    private float aguaG = 0;
    private float proteinasG = 0;
    private float hcG = 0;
    private float lipidosG = 0;
    private float aguaL = 0;
    private float proteinasKcal = 0;
    private float hcKcal = 0;
    private float lipidosKcal = 0;
    private float calorias = 0;

    DefaultTableModel tblDefaultAlimentoDietaLunes;
    DefaultTableModel tblDefaultAlimentoDietaMartes;
    DefaultTableModel tblDefaultAlimentoDietaMiercoles;
    DefaultTableModel tblDefaultAlimentoDietaJueves;
    DefaultTableModel tblDefaultAlimentoDietaViernes;
    DefaultTableModel tblDefaultAlimentoDietaSabado;
    DefaultTableModel tblDefaultAlimentoDietaDomingo;
    LazyList<AlimentosDietas> lista;
    Integer idDieta = -1;
    Integer idSocio = -1;
    ControladorJReport reporte;
    private String email;

    /**
     * Creates new form VerRutinasGui
     */
    public VerDietaGui(java.awt.Frame parent, boolean modal, LazyList<AlimentosDietas> lista, String nombre, String descripcion, Integer idDieta, Integer idSocio) {
        super(parent, modal);
        initComponents();
        this.idDieta = idDieta;
        this.idSocio = idSocio;
        tblDefaultAlimentoDietaLunes = (DefaultTableModel) tblAlimentosDietaLunes.getModel();
        tblDefaultAlimentoDietaMartes = (DefaultTableModel) tblAlimentosDietaMartes.getModel();
        tblDefaultAlimentoDietaMiercoles = (DefaultTableModel) tblAlimentosDietaMiercoles.getModel();
        tblDefaultAlimentoDietaJueves = (DefaultTableModel) tblAlimentosDietaJueves.getModel();
        tblDefaultAlimentoDietaViernes = (DefaultTableModel) tblAlimentosDietaViernes.getModel();
        tblDefaultAlimentoDietaSabado = (DefaultTableModel) tblAlimentosDietaSabado.getModel();
        tblDefaultAlimentoDietaDomingo = (DefaultTableModel) tblAlimentosDietaDomingo.getModel();
        this.lista = lista;
        this.tblAlimentosDietaLunes.setEnabled(false);
        this.tblAlimentosDietaMartes.setEnabled(false);
        this.tblAlimentosDietaMiercoles.setEnabled(false);
        this.tblAlimentosDietaJueves.setEnabled(false);
        this.tblAlimentosDietaViernes.setEnabled(false);
        this.tblAlimentosDietaSabado.setEnabled(false);
        this.tblAlimentosDietaDomingo.setEnabled(false);
        this.txtNombre.setEnabled(false);
        this.txtDescripcion.setEnabled(false);
        cargarEnTablaDietaAliemtos(lista);
        txtNombre.setText(nombre);
        txtDescripcion.setText(descripcion);

        pnlTab.addChangeListener(this);
        try {
            reporte = new ControladorJReport("dieta.jasper");
        } catch (JRException ex) {
            Logger.getLogger(VerDietaGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VerDietaGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(VerDietaGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (idSocio == -1) {
            btnImprimir.setEnabled(false);
            btnEnviarEmail.setEnabled(false);
        }
            //else
        // email = Socio.findFirst("ID_DATOS_PERS = ?", idSocio).getString("email");

    }

    private void cargarEnTablaDietaAliemtos(LazyList<AlimentosDietas> lista) {
        tblDefaultAlimentoDietaLunes.setRowCount(0);
        tblDefaultAlimentoDietaMartes.setRowCount(0);
        tblDefaultAlimentoDietaMiercoles.setRowCount(0);
        tblDefaultAlimentoDietaJueves.setRowCount(0);
        tblDefaultAlimentoDietaViernes.setRowCount(0);
        tblDefaultAlimentoDietaSabado.setRowCount(0);
        tblDefaultAlimentoDietaDomingo.setRowCount(0);

        aguaG = 0;
        proteinasG = 0;
        hcG = 0;
        lipidosG = 0;
        aguaL = 0;
        hcKcal = 0;
        lipidosKcal = 0;
        proteinasKcal = 0;
        calorias = 0;
        Iterator<AlimentosDietas> it = lista.iterator();
        while (it.hasNext()) {

            AlimentosDietas a = it.next();
            Alimento alim = Alimento.findById(a.getInteger("alimento_id"));
            Object row[] = new Object[9];
            row[0] = a.getString("hora");
            row[1] = alim.getString("nombre");
            row[2] = alim.getFloat("agua") * a.getFloat("porcion");
            row[3] = alim.getFloat("prot") * a.getFloat("porcion");
            row[4] = alim.getFloat("hc") * a.getFloat("porcion");
            row[5] = alim.getFloat("grasa") * a.getFloat("porcion");
            row[6] = (alim.getFloat("prot") * a.getFloat("porcion") * 4) + (alim.getFloat("hc") * a.getFloat("porcion") * 4) + (alim.getFloat("grasa") * a.getFloat("porcion") * 9);
            row[7] = a.getFloat("porcion");
            row[8] = alim.getInteger("id");
            String dia = a.getString("dia");
            switch (dia) {
                case "LUNES":
                    tblDefaultAlimentoDietaLunes.addRow(row);
                    break;
                case "MARTES":
                    tblDefaultAlimentoDietaMartes.addRow(row);
                    break;
                case "MIERCOLES":
                    tblDefaultAlimentoDietaMiercoles.addRow(row);
                    break;
                case "JUEVES":
                    tblDefaultAlimentoDietaJueves.addRow(row);
                    break;
                case "VIERNES":
                    tblDefaultAlimentoDietaViernes.addRow(row);
                    break;
                case "SABADO":
                    tblDefaultAlimentoDietaSabado.addRow(row);
                    break;
                case "DOMINGO":
                    tblDefaultAlimentoDietaDomingo.addRow(row);
                    break;
            }

        }
        calcularMacros();

    }

    private void calcularMacros() {
        aguaG = 0;
        proteinasG = 0;
        hcG = 0;
        lipidosG = 0;
        aguaL = 0;
        hcKcal = 0;
        lipidosKcal = 0;
        proteinasKcal = 0;
        calorias = 0;
        Alimento al;
        switch (pnlTab.getSelectedIndex()) {
            case 0:
                for (int i = 0; i < tblDefaultAlimentoDietaLunes.getRowCount(); i++) {
                    float porcion;
                    try {
                        porcion = (float) tblAlimentosDietaLunes.getValueAt(i, 7);
                    } catch (java.lang.ClassCastException ex) {
                        porcion = Float.valueOf((Integer) tblAlimentosDietaLunes.getValueAt(i, 7));
                    }
                    al = Alimento.findById(tblAlimentosDietaLunes.getValueAt(i, 8));
                    float hcGAux = al.getFloat("hc") * porcion;
                    float protGAux = al.getFloat("prot") * porcion;
                    float lipGAux = al.getFloat("grasa") * porcion;

                    aguaG += al.getFloat("agua") * porcion;
                    proteinasG += protGAux;
                    hcG += hcGAux;
                    lipidosG += lipGAux;
                    tblAlimentosDietaLunes.setValueAt(al.getFloat("agua") * porcion, i, 2);
                    tblAlimentosDietaLunes.setValueAt(al.getFloat("prot") * porcion, i, 3);
                    tblAlimentosDietaLunes.setValueAt(al.getFloat("hc") * porcion, i, 4);
                    tblAlimentosDietaLunes.setValueAt(al.getFloat("grasa") * porcion, i, 5);
                    tblAlimentosDietaLunes.setValueAt(hcGAux * 4 + protGAux * 4 + lipGAux * 9, i, 6);
                    //aguaL -=(float) tblAlimentosDietaLunes.getValueAt(row, 2)*(float) tblAlimentosDietaLunes.getValueAt(row, 7) ;                

                }
                break;
            case 1://Ciclo martes
                for (int i = 0; i < tblDefaultAlimentoDietaMartes.getRowCount(); i++) {
                    float porcion;
                    try {
                        porcion = (float) tblAlimentosDietaMartes.getValueAt(i, 7);
                    } catch (java.lang.ClassCastException ex) {
                        porcion = Float.valueOf((Integer) tblAlimentosDietaMartes.getValueAt(i, 7));
                    }
                    al = Alimento.findById(tblAlimentosDietaMartes.getValueAt(i, 8));
                    float hcGAux = al.getFloat("hc") * porcion;
                    float protGAux = al.getFloat("prot") * porcion;
                    float lipGAux = al.getFloat("grasa") * porcion;

                    aguaG += al.getFloat("agua") * porcion;
                    proteinasG += protGAux;
                    hcG += hcGAux;
                    lipidosG += lipGAux;
                    tblAlimentosDietaMartes.setValueAt(al.getFloat("agua") * porcion, i, 2);
                    tblAlimentosDietaMartes.setValueAt(al.getFloat("prot") * porcion, i, 3);
                    tblAlimentosDietaMartes.setValueAt(al.getFloat("hc") * porcion, i, 4);
                    tblAlimentosDietaMartes.setValueAt(al.getFloat("grasa") * porcion, i, 5);
                    tblAlimentosDietaMartes.setValueAt(hcGAux * 4 + protGAux * 4 + lipGAux * 9, i, 6);            //aguaL -=(float) tblAlimentosDietaLunes.getValueAt(row, 2)*(float) tblAlimentosDietaLunes.getValueAt(row, 7) ;

                }
                break;
            case 2://Ciclo miercoles
                for (int i = 0; i < tblDefaultAlimentoDietaMiercoles.getRowCount(); i++) {
                    float porcion;
                    try {
                        porcion = (float) tblAlimentosDietaMiercoles.getValueAt(i, 7);
                    } catch (java.lang.ClassCastException ex) {
                        porcion = Float.valueOf((Integer) tblAlimentosDietaMiercoles.getValueAt(i, 7));
                    }
                    al = Alimento.findById(tblAlimentosDietaMiercoles.getValueAt(i, 8));
                    float hcGAux = al.getFloat("hc") * porcion;
                    float protGAux = al.getFloat("prot") * porcion;
                    float lipGAux = al.getFloat("grasa") * porcion;

                    aguaG += al.getFloat("agua") * porcion;
                    proteinasG += protGAux;
                    hcG += hcGAux;
                    lipidosG += lipGAux;
                    tblAlimentosDietaMiercoles.setValueAt(al.getFloat("agua") * porcion, i, 2);
                    tblAlimentosDietaMiercoles.setValueAt(al.getFloat("prot") * porcion, i, 3);
                    tblAlimentosDietaMiercoles.setValueAt(al.getFloat("hc") * porcion, i, 4);
                    tblAlimentosDietaMiercoles.setValueAt(al.getFloat("grasa") * porcion, i, 5);
                    tblAlimentosDietaMiercoles.setValueAt(hcGAux * 4 + protGAux * 4 + lipGAux * 9, i, 6);            //aguaL -=(float) tblAlimentosDietaLunes.getValueAt(row, 2)*(float) tblAlimentosDietaLunes.getValueAt(row, 7) ;

                }
                break;
            case 3://Ciclo jueves
                for (int i = 0; i < tblDefaultAlimentoDietaJueves.getRowCount(); i++) {
                    float porcion;
                    try {
                        porcion = (float) tblAlimentosDietaJueves.getValueAt(i, 7);
                    } catch (java.lang.ClassCastException ex) {
                        porcion = Float.valueOf((Integer) tblAlimentosDietaJueves.getValueAt(i, 7));
                    }
                    al = Alimento.findById(tblAlimentosDietaJueves.getValueAt(i, 8));
                    float hcGAux = al.getFloat("hc") * porcion;
                    float protGAux = al.getFloat("prot") * porcion;
                    float lipGAux = al.getFloat("grasa") * porcion;

                    aguaG += al.getFloat("agua") * porcion;
                    proteinasG += protGAux;
                    hcG += hcGAux;
                    lipidosG += lipGAux;
                    tblAlimentosDietaJueves.setValueAt(al.getFloat("agua") * porcion, i, 2);
                    tblAlimentosDietaJueves.setValueAt(al.getFloat("prot") * porcion, i, 3);
                    tblAlimentosDietaJueves.setValueAt(al.getFloat("hc") * porcion, i, 4);
                    tblAlimentosDietaJueves.setValueAt(al.getFloat("grasa") * porcion, i, 5);
                    tblAlimentosDietaJueves.setValueAt(hcGAux * 4 + protGAux * 4 + lipGAux * 9, i, 6);            //aguaL -=(float) tblAlimentosDietaLunes.getValueAt(row, 2)*(float) tblAlimentosDietaLunes.getValueAt(row, 7) ;

                }
                break;
            case 4://Ciclo viernes
                for (int i = 0; i < tblDefaultAlimentoDietaViernes.getRowCount(); i++) {
                    float porcion;
                    try {
                        porcion = (float) tblAlimentosDietaViernes.getValueAt(i, 7);
                    } catch (java.lang.ClassCastException ex) {
                        porcion = Float.valueOf((Integer) tblAlimentosDietaViernes.getValueAt(i, 7));
                    }
                    al = Alimento.findById(tblAlimentosDietaViernes.getValueAt(i, 8));
                    float hcGAux = al.getFloat("hc") * porcion;
                    float protGAux = al.getFloat("prot") * porcion;
                    float lipGAux = al.getFloat("grasa") * porcion;

                    aguaG += al.getFloat("agua") * porcion;
                    proteinasG += protGAux;
                    hcG += hcGAux;
                    lipidosG += lipGAux;
                    tblAlimentosDietaViernes.setValueAt(al.getFloat("agua") * porcion, i, 2);
                    tblAlimentosDietaViernes.setValueAt(al.getFloat("prot") * porcion, i, 3);
                    tblAlimentosDietaViernes.setValueAt(al.getFloat("hc") * porcion, i, 4);
                    tblAlimentosDietaViernes.setValueAt(al.getFloat("grasa") * porcion, i, 5);
                    tblAlimentosDietaViernes.setValueAt(hcGAux * 4 + protGAux * 4 + lipGAux * 9, i, 6);            //aguaL -=(float) tblAlimentosDietaLunes.getValueAt(row, 2)*(float) tblAlimentosDietaLunes.getValueAt(row, 7) ;

                }
                break;
            case 5://Ciclo sabado
                for (int i = 0; i < tblDefaultAlimentoDietaSabado.getRowCount(); i++) {
                    float porcion;
                    try {
                        porcion = (float) tblAlimentosDietaSabado.getValueAt(i, 7);
                    } catch (java.lang.ClassCastException ex) {
                        porcion = Float.valueOf((Integer) tblAlimentosDietaSabado.getValueAt(i, 7));
                    }
                    al = Alimento.findById(tblAlimentosDietaSabado.getValueAt(i, 8));
                    float hcGAux = al.getFloat("hc") * porcion;
                    float protGAux = al.getFloat("prot") * porcion;
                    float lipGAux = al.getFloat("grasa") * porcion;

                    aguaG += al.getFloat("agua") * porcion;
                    proteinasG += protGAux;
                    hcG += hcGAux;
                    lipidosG += lipGAux;
                    tblAlimentosDietaSabado.setValueAt(al.getFloat("agua") * porcion, i, 2);
                    tblAlimentosDietaSabado.setValueAt(al.getFloat("prot") * porcion, i, 3);
                    tblAlimentosDietaSabado.setValueAt(al.getFloat("hc") * porcion, i, 4);
                    tblAlimentosDietaSabado.setValueAt(al.getFloat("grasa") * porcion, i, 5);
                    tblAlimentosDietaSabado.setValueAt(hcGAux * 4 + protGAux * 4 + lipGAux * 9, i, 6);            //aguaL -=(float) tblAlimentosDietaLunes.getValueAt(row, 2)*(float) tblAlimentosDietaLunes.getValueAt(row, 7) ;

                }
                break;
            case 6://Ciclo domingo
                for (int i = 0; i < tblDefaultAlimentoDietaDomingo.getRowCount(); i++) {
                    float porcion;
                    try {
                        porcion = (float) tblAlimentosDietaDomingo.getValueAt(i, 7);
                    } catch (java.lang.ClassCastException ex) {
                        porcion = Float.valueOf((Integer) tblAlimentosDietaDomingo.getValueAt(i, 7));
                    }
                    al = Alimento.findById(tblAlimentosDietaDomingo.getValueAt(i, 8));
                    float hcGAux = al.getFloat("hc") * porcion;
                    float protGAux = al.getFloat("prot") * porcion;
                    float lipGAux = al.getFloat("grasa") * porcion;

                    aguaG += al.getFloat("agua") * porcion;
                    proteinasG += protGAux;
                    hcG += hcGAux;
                    lipidosG += lipGAux;
                    tblAlimentosDietaDomingo.setValueAt(al.getFloat("agua") * porcion, i, 2);
                    tblAlimentosDietaDomingo.setValueAt(al.getFloat("prot") * porcion, i, 3);
                    tblAlimentosDietaDomingo.setValueAt(al.getFloat("hc") * porcion, i, 4);
                    tblAlimentosDietaDomingo.setValueAt(al.getFloat("grasa") * porcion, i, 5);
                    tblAlimentosDietaDomingo.setValueAt(hcGAux * 4 + protGAux * 4 + lipGAux * 9, i, 6);
                }
        }
        hcKcal = hcG * 4;
        lipidosKcal = lipidosG * 9;
        proteinasKcal = proteinasG * 4;
        calorias = hcKcal + lipidosKcal + proteinasKcal;
        lblAguaL.setText(String.valueOf(aguaL) + " lts");
        lblAguag.setText(String.valueOf(aguaG) + " grs");
        lblCalorias.setText(String.valueOf(calorias) + " Kcal");
        lblHCG.setText(String.valueOf(hcG) + " grs");
        lblHCK.setText(String.valueOf(hcKcal) + " Kcal");
        lblLipidoG.setText(String.valueOf(lipidosG) + " grs");
        lblLipidoK.setText(String.valueOf(lipidosKcal) + " Kcal");
        lblProtG.setText(String.valueOf(proteinasG) + " grs");
        lblProtK.setText(String.valueOf(proteinasKcal) + " Kcal");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        lblAguag = new javax.swing.JLabel();
        lblProtG = new javax.swing.JLabel();
        lblHCG = new javax.swing.JLabel();
        lblLipidoG = new javax.swing.JLabel();
        lblLipidoK = new javax.swing.JLabel();
        lblCalorias = new javax.swing.JLabel();
        lblProtK = new javax.swing.JLabel();
        lblAguaL = new javax.swing.JLabel();
        lblHCK = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        pnlTab = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblAlimentosDietaLunes = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblAlimentosDietaMartes = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblAlimentosDietaMiercoles = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblAlimentosDietaJueves = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblAlimentosDietaViernes = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblAlimentosDietaSabado = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblAlimentosDietaDomingo = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnEnviarEmail = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(960, 480));
        setSize(new java.awt.Dimension(980, 500));
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setText("Nombre");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel5.setText("Lipidos (g)");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel2.setText("Agua (g)");

        jLabel3.setText("Proteinas (g)");

        jLabel6.setText("Calorias ");

        jLabel7.setText("Totales");

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel4.setText("H.C (g)");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblAguag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblAguaL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(16, 19, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(lblProtK, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(lblProtG, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblHCG, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(lblHCK, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblLipidoK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                    .addComponent(lblLipidoG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCalorias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblLipidoG, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblHCG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblProtG, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblAguag, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHCK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAguaL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblProtK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCalorias, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblLipidoK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );

        jLabel10.setText("descripcion");

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane5.setViewportView(txtDescripcion);

        tblAlimentosDietaLunes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hora", "Alimento", "Agua", "Proteina", "H.Cl", "Lípido", "Calorias", "Porcion", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAlimentosDietaLunes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(tblAlimentosDietaLunes);

        pnlTab.addTab("Lunes", jScrollPane3);

        tblAlimentosDietaMartes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hora", "Alimento", "Agua", "Proteina", "H.Cl", "Lípido", "Calorias", "Porcion", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAlimentosDietaMartes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane6.setViewportView(tblAlimentosDietaMartes);

        pnlTab.addTab("Martes", jScrollPane6);

        tblAlimentosDietaMiercoles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hora", "Alimento", "Agua", "Proteina", "H.Cl", "Lípido", "Calorias", "Porcion", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAlimentosDietaMiercoles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(tblAlimentosDietaMiercoles);

        pnlTab.addTab("Miercoles", jScrollPane7);

        tblAlimentosDietaJueves.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hora", "Alimento", "Agua", "Proteina", "H.Cl", "Lípido", "Calorias", "Porcion", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAlimentosDietaJueves.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane8.setViewportView(tblAlimentosDietaJueves);

        pnlTab.addTab("Jueves", jScrollPane8);

        tblAlimentosDietaViernes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hora", "Alimento", "Agua", "Proteina", "H.Cl", "Lípido", "Calorias", "Porcion", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAlimentosDietaViernes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane9.setViewportView(tblAlimentosDietaViernes);

        pnlTab.addTab("Viernes", jScrollPane9);

        tblAlimentosDietaSabado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hora", "Alimento", "Agua", "Proteina", "H.Cl", "Lípido", "Calorias", "Porcion", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAlimentosDietaSabado.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane10.setViewportView(tblAlimentosDietaSabado);

        pnlTab.addTab("Sábado", jScrollPane10);

        tblAlimentosDietaDomingo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hora", "Alimento", "Agua", "Proteina", "H.Cl", "Lípido", "Calorias", "Porcion", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAlimentosDietaDomingo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane11.setViewportView(tblAlimentosDietaDomingo);

        pnlTab.addTab("Domingo", jScrollPane11);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        btnEnviarEmail.setText("Enviarle la dieta al email ");
        btnEnviarEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarEmailActionPerformed(evt);
            }
        });
        jPanel3.add(btnEnviarEmail);

        btnImprimir.setText("IMPRIMIR DIETA");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        jPanel3.add(btnImprimir);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlTab)
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTab, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane1.setViewportView(jPanel1);

        getContentPane().add(jScrollPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        try {
            reporte.mostrarDieta(idSocio, idDieta);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VerDietaGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(VerDietaGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(VerDietaGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnEnviarEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarEmailActionPerformed
        try {
            
            String ruta = reporte.obtenerDieta(idSocio, idDieta);
            boolean res = EnvioEmailControlador.enviarMailManualDieta(ruta, Socio.findFirst("ID_DATOS_PERS = ?", idSocio).getInteger("ID_DATOS_PERS"), "dieta");
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
    }//GEN-LAST:event_btnEnviarEmailActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviarEmail;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblAguaL;
    private javax.swing.JLabel lblAguag;
    private javax.swing.JLabel lblCalorias;
    private javax.swing.JLabel lblHCG;
    private javax.swing.JLabel lblHCK;
    private javax.swing.JLabel lblLipidoG;
    private javax.swing.JLabel lblLipidoK;
    private javax.swing.JLabel lblProtG;
    private javax.swing.JLabel lblProtK;
    private javax.swing.JTabbedPane pnlTab;
    private javax.swing.JTable tblAlimentosDietaDomingo;
    private javax.swing.JTable tblAlimentosDietaJueves;
    private javax.swing.JTable tblAlimentosDietaLunes;
    private javax.swing.JTable tblAlimentosDietaMartes;
    private javax.swing.JTable tblAlimentosDietaMiercoles;
    private javax.swing.JTable tblAlimentosDietaSabado;
    private javax.swing.JTable tblAlimentosDietaViernes;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables

    @Override
    public void stateChanged(ChangeEvent e) {
        calcularMacros();
    }
}
