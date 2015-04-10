/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NicoOrcasitas
 */
public class DietaGui extends javax.swing.JInternalFrame {

    /**
     * Creates new form DietaGui
     */
    DefaultTableModel tblDefaultAlimento;
    DefaultTableModel tblDefaultDietas;

    DefaultTableModel tblDefaultAlimentoDietaLunes;
    DefaultTableModel tblDefaultAlimentoDietaMartes;
    DefaultTableModel tblDefaultAlimentoDietaMiercoles;
    DefaultTableModel tblDefaultAlimentoDietaJueves;
    DefaultTableModel tblDefaultAlimentoDietaViernes;
    DefaultTableModel tblDefaultAlimentoDietaSabado;
    DefaultTableModel tblDefaultAlimentoDietaDomingo;

    public DietaGui() {
        initComponents();
        bloquearCampos(true);
        tblDefaultAlimento = (DefaultTableModel) tblAlimentos.getModel();
        tblDefaultDietas = (DefaultTableModel) tblDietas.getModel();
        tblDefaultAlimentoDietaLunes = (DefaultTableModel) tblAlimentosDietaLunes.getModel();
        tblDefaultAlimentoDietaMartes = (DefaultTableModel) tblAlimentosDietaMartes.getModel();
        tblDefaultAlimentoDietaMiercoles = (DefaultTableModel) tblAlimentosDietaMiercoles.getModel();
        tblDefaultAlimentoDietaJueves = (DefaultTableModel) tblAlimentosDietaJueves.getModel();
        tblDefaultAlimentoDietaViernes = (DefaultTableModel) tblAlimentosDietaViernes.getModel();
        tblDefaultAlimentoDietaSabado = (DefaultTableModel) tblAlimentosDietaSabado.getModel();
        tblDefaultAlimentoDietaDomingo = (DefaultTableModel) tblAlimentosDietaDomingo.getModel();

        setBotonesInicial();

    }
//si hago click en en nuevo

    public void setBotonesNuevo() {
        this.botModif.setEnabled(false);
        this.botGuardar.setEnabled(true);
        this.botEliminarCancelar.setEnabled(true);
        this.botEliminarCancelar.setText("Cancelar");
        this.botNuevo.setEnabled(true);
        limpiarCampos();
        bloquearCampos(false);

    }

    //si hago click en modificar
    public void setBotonesModificar() {
        this.botModif.setEnabled(false);
        this.botGuardar.setEnabled(true);
        this.botEliminarCancelar.setEnabled(true);
        this.botEliminarCancelar.setText("Cancelar");
        this.botNuevo.setEnabled(true);
        bloquearCampos(false);
    }

    //cuando no hice click en ningun lado
    public void setBotonesInicial() {
        this.botModif.setEnabled(false);
        this.botGuardar.setEnabled(false);
        this.botEliminarCancelar.setEnabled(false);
        this.botEliminarCancelar.setText("Eliminar");
        this.botNuevo.setEnabled(true);
        limpiarCampos();
        bloquearCampos(true);
    }

    //cuando no hice click en ningun lado
    public void setBotonesClickTabla() {
        this.botModif.setEnabled(true);
        this.botGuardar.setEnabled(false);
        this.botEliminarCancelar.setEnabled(true);
        this.botEliminarCancelar.setText("Eliminar");
        this.botNuevo.setEnabled(true);
        this.bloquearCampos(true);
    }

    private void bloquearCampos(boolean si) {
        this.tblAlimentos.setEnabled(!si);
        this.tblAlimentosDietaLunes.setEnabled(!si);
        this.tblAlimentosDietaMartes.setEnabled(!si);
        this.tblAlimentosDietaMiercoles.setEnabled(!si);
        this.tblAlimentosDietaJueves.setEnabled(!si);
        this.tblAlimentosDietaViernes.setEnabled(!si);
        this.tblAlimentosDietaSabado.setEnabled(!si);
        this.tblAlimentosDietaDomingo.setEnabled(!si);
        this.txtNombre.setEnabled(!si);
        this.txtDescripcion.setEnabled(!si);
    }

    public void limpiarCampos() {
        this.lblAguaL.setText("");
        this.lblAguag.setText("");
        this.lblCalorias.setText("");
        this.lblHCG.setText("");
        this.lblHCK.setText("");
        this.lblLipidoG.setText("");
        this.lblLipidoK.setText("");
        this.lblProtG.setText("");
        this.lblProtK.setText("");
        this.txtNombre.setText("");
        this.txtDescripcion.setText("");
        this.tblDefaultAlimentoDietaLunes.setRowCount(0);
        this.tblDefaultAlimentoDietaMartes.setRowCount(0);
        this.tblDefaultAlimentoDietaMiercoles.setRowCount(0);
        this.tblDefaultAlimentoDietaJueves.setRowCount(0);
        this.tblDefaultAlimentoDietaViernes.setRowCount(0);
        this.tblDefaultAlimentoDietaSabado.setRowCount(0);
        this.tblDefaultAlimentoDietaDomingo.setRowCount(0);

    }

    public JTextField getTxtBusquedaDietas() {
        return txtBusquedaDietas;
    }

    public void setActionListener(ActionListener lis) {
        this.botEliminarCancelar.addActionListener(lis);
        this.botGuardar.addActionListener(lis);
        this.botModif.addActionListener(lis);
        this.botNuevo.addActionListener(lis);
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

    public JLabel getLblAguaL() {
        return lblAguaL;
    }

    public JLabel getLblAguag() {
        return lblAguag;
    }

    public JLabel getLblCalorias() {
        return lblCalorias;
    }

    public JLabel getLblHCG() {
        return lblHCG;
    }

    public JLabel getLblHCK() {
        return lblHCK;
    }

    public JLabel getLblLipidoG() {
        return lblLipidoG;
    }

    public JLabel getLblLipidoK() {
        return lblLipidoK;
    }

    public JLabel getLblProtG() {
        return lblProtG;
    }

    public JLabel getLblProtK() {
        return lblProtK;
    }

    public JTable getTblAlimentos() {
        return tblAlimentos;
    }

    public JTable getTblAlimentosDieta() {
        return tblAlimentosDietaLunes;
    }

    public JTextField getTxtBusqueda() {
        return txtBusqueda;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public DefaultTableModel getTblDefaultAlimento() {
        return tblDefaultAlimento;
    }

 

    public DefaultTableModel getTblDefaultDietas() {
        return tblDefaultDietas;
    }

    public JTable getTblDietas() {
        return tblDietas;
    }

    public JTextArea getTxtDescripcion() {
        return txtDescripcion;
    }

    public DefaultTableModel getTblDefaultAlimentoDietaLunes() {
        return tblDefaultAlimentoDietaLunes;
    }

    public DefaultTableModel getTblDefaultAlimentoDietaMartes() {
        return tblDefaultAlimentoDietaMartes;
    }

    public DefaultTableModel getTblDefaultAlimentoDietaMiercoles() {
        return tblDefaultAlimentoDietaMiercoles;
    }

    public DefaultTableModel getTblDefaultAlimentoDietaJueves() {
        return tblDefaultAlimentoDietaJueves;
    }

    public DefaultTableModel getTblDefaultAlimentoDietaViernes() {
        return tblDefaultAlimentoDietaViernes;
    }

    public DefaultTableModel getTblDefaultAlimentoDietaSabado() {
        return tblDefaultAlimentoDietaSabado;
    }

    public DefaultTableModel getTblDefaultAlimentoDietaDomingo() {
        return tblDefaultAlimentoDietaDomingo;
    }

    public JTable getTblAlimentosDietaDomingo() {
        return tblAlimentosDietaDomingo;
    }

    public JTable getTblAlimentosDietaJueves() {
        return tblAlimentosDietaJueves;
    }

    public JTable getTblAlimentosDietaLunes() {
        return tblAlimentosDietaLunes;
    }

    public JTable getTblAlimentosDietaMartes() {
        return tblAlimentosDietaMartes;
    }

    public JTable getTblAlimentosDietaMiercoles() {
        return tblAlimentosDietaMiercoles;
    }

    public JTable getTblAlimentosDietaSabado() {
        return tblAlimentosDietaSabado;
    }

    public JTable getTblAlimentosDietaViernes() {
        return tblAlimentosDietaViernes;
    }

    public JTabbedPane getPnlTab() {
        return pnlTab;
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
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAlimentos = new javax.swing.JTable();
        txtBusqueda = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        botNuevo = new javax.swing.JButton();
        botModif = new javax.swing.JButton();
        botEliminarCancelar = new javax.swing.JButton();
        botGuardar = new javax.swing.JButton();
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
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDietas = new javax.swing.JTable();
        txtBusquedaDietas = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
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

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Dietas");

        jLabel1.setText("Nombre");

        jLabel8.setText("BUSQUEDA");

        tblAlimentos.setAutoCreateRowSorter(true);
        tblAlimentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Alimento", "% PC", "Agua", "Proteina", "CH", "Lipidos", "Colesterol", "Fibra", "Sodio", "Potasio", "Magnesio", "Calcio", "Fosforo", "Hierro"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAlimentos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblAlimentos);

        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        botNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/agregar.png"))); // NOI18N
        jPanel6.add(botNuevo);

        botModif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/modificar.png"))); // NOI18N
        jPanel6.add(botModif);

        botEliminarCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/borrar.png"))); // NOI18N
        jPanel6.add(botEliminarCancelar);

        botGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar.png"))); // NOI18N
        jPanel6.add(botGuardar);

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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dietas"));

        tblDietas.setAutoCreateRowSorter(true);
        tblDietas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Descripcion", "ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDietas.setRowSorter(null);
        tblDietas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(tblDietas);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
        );

        jLabel9.setText("BUSQUEDA DE DIETAS");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(txtBusquedaDietas)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBusqueda)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTab)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10))
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBusquedaDietas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTab, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jScrollPane1.setViewportView(jPanel1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botEliminarCancelar;
    private javax.swing.JButton botGuardar;
    private javax.swing.JButton botModif;
    private javax.swing.JButton botNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
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
    private javax.swing.JTable tblAlimentos;
    private javax.swing.JTable tblAlimentosDietaDomingo;
    private javax.swing.JTable tblAlimentosDietaJueves;
    private javax.swing.JTable tblAlimentosDietaLunes;
    private javax.swing.JTable tblAlimentosDietaMartes;
    private javax.swing.JTable tblAlimentosDietaMiercoles;
    private javax.swing.JTable tblAlimentosDietaSabado;
    private javax.swing.JTable tblAlimentosDietaViernes;
    private javax.swing.JTable tblDietas;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtBusquedaDietas;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
