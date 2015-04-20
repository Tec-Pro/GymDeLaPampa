/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Controladores.ControladorJReport;
import Modelos.Categoria;
import Modelos.Dato;
import Modelos.Gasto;
import Modelos.Proveedor;
import Modelos.Socio;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author NicoOrcasitas
 */
public class GastosGui extends javax.swing.JInternalFrame {
    
    DefaultTableModel tablaMovDefault;
    ControladorJReport reporte ;
    /**
     * Creates new form GastosGui
     */
    public GastosGui() {
        try {
            initComponents();
            BotEliminar.setEnabled(false);
            BotMod.setEnabled(false);
            BoxTipo.setEnabled(false);
            BloquearCampos(false);
            BotonesNuevo(true);
            desde.getDateEditor().setEnabled(false);
            hasta.getDateEditor().setEnabled(false);
            fecha.getDateEditor().setEnabled(false);
            
            tablaMovDefault = (DefaultTableModel) tablaGastos.getModel();
            
            abrirBase();
            LazyList<Categoria> categoriasBase = Categoria.findAll();
            Iterator<Categoria> it = categoriasBase.iterator();
            categorias.addItem("Todos");
            while (it.hasNext()) {
                Categoria cate = it.next();
                categorias.addItem(cate.get("nombre"));
                BoxCategoria.addItem(cate.get("nombre"));
            }
            categorias.setSelectedItem("Todos");
            

            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            date.setDate(cal.getActualMinimum(Calendar.DAY_OF_MONTH));;
            desde.setDate(date);
            Date dateH = cal.getTime();
            dateH.setDate(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            hasta.setDate(dateH);
            reporte = new ControladorJReport(("contadora.jasper"));
        } catch (JRException ex) {
            Logger.getLogger(GastosGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GastosGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GastosGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

        public void BloquearCampos(boolean b){
        BoxArea.setEnabled(b);
        BoxCategoria.setEnabled(b);
        //BoxTipo.setEnabled(b);
        TextMonto.setEnabled(b);
        desc.setEnabled(b);
        fecha.setEnabled(b);
    }
    public void BotonesNuevo(boolean b){
        if(b){
            BotNuevo.setText("Nuevo");
            BotMod.setText("Modificar");
            BotEliminar.setText("Eliminar");
        }else{
            BotNuevo.setText("Guardar");
            BotMod.setText("Modificar");
            BotEliminar.setText("Cancelar");
        }
    }

    public JComboBox getCategorias() {
        return categorias;
    }

    
       public JButton getBotAgregarCat() {
        return BotAgregarCat;
    }

    public JButton getBotGestionarAreas() {
        return BotGestionarAreas;
    }

    public JComboBox getBoxArea() {
        return BoxArea;
    }

    public JComboBox getBoxCategoria() {
        return BoxCategoria;
    }

    public JComboBox getBoxTipo() {
        return BoxTipo;
    }

    public JTextArea getDesc() {
        return desc;
    }
    

    public DefaultTableModel getTablaMovDefault() {
        return tablaMovDefault;
    }

    public JTable getTablaGastos() {
        return tablaGastos;
    }

    public JTextField getTextMonto() {
        return TextMonto;
    }

    public JDateChooser getFecha() {
        return fecha;
    }

    public JButton getBotEliminar() {
        return BotEliminar;
    }

    public JButton getBotMod() {
        return BotMod;
    }

    public JButton getBotNuevo() {
        return BotNuevo;
    }

    public void habilitarCampos(boolean si){
        BoxCategoria.setEnabled(si);
        fecha.setEnabled(si);
        TextMonto.setEnabled(si);
        BoxArea.setEnabled(si);
        BoxTipo.setEnabled(si);
        desc.setEnabled(si);
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desde = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        hasta = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        categorias = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cargar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaGastos = new javax.swing.JTable();
        total = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        imprimir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        BoxCategoria = new javax.swing.JComboBox();
        BotAgregarCat = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        BoxArea = new javax.swing.JComboBox();
        BotGestionarAreas = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        TextMonto = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        desc = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        BoxTipo = new javax.swing.JComboBox();
        BotNuevo = new javax.swing.JButton();
        BotMod = new javax.swing.JButton();
        BotEliminar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblCuenta = new javax.swing.JFormattedTextField();
        lblCuentaProv = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gastos");
        setToolTipText("");

        desde.setToolTipText("Ver movimientos desde la fecha");

        jLabel1.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        jLabel1.setText("Desde");

        hasta.setToolTipText("Ver movimientos hasta la fecha");

        jLabel2.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        jLabel2.setText("Hasta");

        categorias.setMaximumRowCount(20);

        jLabel3.setText("Ver movimientos de ");

        cargar.setText("Cargar gastos");
        cargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarActionPerformed(evt);
            }
        });

        tablaGastos.setAutoCreateRowSorter(true);
        tablaGastos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Categoria", "Descripcion", "Monto", "Tipo de movimiento", "Fecha", "Id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaGastos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tablaGastos);

        total.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        total.setForeground(new java.awt.Color(0, 87, 218));

        jLabel11.setFont(new java.awt.Font("Century Schoolbook L", 1, 24)); // NOI18N
        jLabel11.setText("TOTAL");

        imprimir.setText("Imprimir movimientos");
        imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imprimirActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Carga - Informacion completa"));

        jLabel5.setText("Categoria");

        BotAgregarCat.setText("Agregar");

        jLabel6.setText("Servicio");

        BotGestionarAreas.setText("Gestionar");

        jLabel7.setText("Fecha");

        jLabel8.setText("Monto");

        jLabel9.setText("Descripcion");

        desc.setColumns(20);
        desc.setRows(5);
        jScrollPane2.setViewportView(desc);

        jLabel10.setText("Tipo");

        BoxTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ingreso", "egreso" }));

        BotNuevo.setText("Nuevo");

        BotMod.setText("Modificar");

        BotEliminar.setText("Eliminar");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos historicos cuentas corrientes"));

        jLabel4.setText("CUENTAS CORRIENTE A COBRAR:");

        lblCuenta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        lblCuenta.setEnabled(false);

        lblCuentaProv.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        lblCuentaProv.setEnabled(false);

        jLabel12.setText("CUENTAS CORRIENTE A PAGAR:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCuentaProv, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel12)
                    .addComponent(lblCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCuentaProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BoxCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BoxArea, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BotAgregarCat)
                                    .addComponent(BotGestionarAreas))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(40, 40, 40))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BotNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BotMod, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                            .addComponent(BotEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(BoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotAgregarCat)
                            .addComponent(jLabel7))
                        .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(BotNuevo)
                        .addGap(2, 2, 2)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(BoxArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotGestionarAreas)
                            .addComponent(jLabel8)
                            .addComponent(TextMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(BoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(BotMod)
                        .addGap(36, 36, 36)
                        .addComponent(BotEliminar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(desde, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(93, 93, 93)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(categorias, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(cargar)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(41, 41, 41)
                .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(imprimir)
                .addGap(124, 124, 124))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(hasta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(desde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(categorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cargar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imprimir)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarActionPerformed
        cargarGastos();
    }//GEN-LAST:event_cargarActionPerformed

    private void imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirActionPerformed
        try {
            reporte.mostrarReporteGastos(desde.getDate(), hasta.getDate());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GastosGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GastosGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(GastosGui.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_imprimirActionPerformed

    public String dateToMySQLDate(Date fecha, boolean paraMostrar) {
        if (fecha != null) {
            if (paraMostrar) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                return sdf.format(fecha);
            } else {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(fecha);
            }
        } else {
            return "";
        }
    }

    private double sumar(){
        int i =0;
        double ret=0;
        while(i<tablaGastos.getRowCount()){
           ret= ret+Double.valueOf((String)tablaGastos.getValueAt(i, 2));
           i++;
        }
        return ret;
    }
    
    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gestionGym", "root", "root");
        }
    }
   public void cargarGastos(){
        abrirBase();
        LazyList<Gasto> gastos = null;
        tablaMovDefault.setRowCount(0);
        LinkedList<Gasto> gastosLinked = new LinkedList<>();
        if (categorias.getSelectedItem().toString().equals("Todos")) {
            gastos = Gasto.where("fecha >= ? and fecha <= ? ", dateToMySQLDate(desde.getDate(), false), dateToMySQLDate(hasta.getDate(), false));
            Iterator<Gasto> it = gastos.iterator();
            Gasto gasto;
            while (it.hasNext()) {
                gasto = it.next();
                Object row[] = new Object[6];
                Dato padre = gasto.parent(Dato.class);
                Categoria papaDato = padre.parent(Categoria.class);
                row[0] = papaDato.getString("nombre");
                row[1] = padre.getString("descripcion");
                
                row[2] = gasto.getString("monto");
                row[3] = padre.getString("ingreso_egreso");
                row[4] = dateToMySQLDate(gasto.getDate("fecha"), true);
                row[5]= gasto.getId().toString();
                tablaMovDefault.addRow(row);
            }
        } else {
            Categoria cat = Categoria.findFirst("nombre =?", categorias.getSelectedItem().toString());
            LazyList<Dato> datos = Dato.where("categoria_id =?", cat.getId());
            Iterator<Dato> itDatos = datos.iterator();
            while (itDatos.hasNext()) {
                Dato dato = itDatos.next();
                gastosLinked.addAll(dato.getAll(Gasto.class));
            }
            Iterator<Gasto> it = gastosLinked.iterator();
            Gasto gasto;
            while (it.hasNext()) {
                gasto = it.next();
                if (gasto.getDate("fecha").after(desde.getDate()) && gasto.getDate("fecha").before(hasta.getDate())) {
                    Object row[] = new Object[6];
                    Dato padre = gasto.parent(Dato.class);
                    Categoria papaDato = padre.parent(Categoria.class);
                    row[0] = papaDato.getString("nombre");
                    row[1] = padre.getString("descripcion");
                    row[2] = gasto.getString("monto");
                    row[3] = padre.getString("ingreso_egreso");
                    row[4] = dateToMySQLDate(gasto.getDate("fecha"), true);
                    row[5]= gasto.getId().toString();
                    tablaMovDefault.addRow(row);
                }
            }

        }
        DecimalFormat df = new DecimalFormat("0.00########");
        String result = df.format(sumar());
        total.setText(result);
    }
    public void setActionListener(ActionListener lis){
        BotAgregarCat.addActionListener(lis);
        BotGestionarAreas.addActionListener(lis);
        BotNuevo.addActionListener(lis);
        BotEliminar.addActionListener(lis);
        BotMod.addActionListener(lis);

    }
    
    public void calcularCuentasCorrientes() throws ParseException{
        LazyList<Socio> socios= Socio.findAll();
        Iterator<Socio> itS= socios.iterator();
        float cuentasSocios=0;
        float cuentasProvs=0;
        while(itS.hasNext()){
            float aux= itS.next().getFloat("cuenta_corriente");
            if(aux<0)
                cuentasSocios+=(aux*-1);
        }
        LazyList<Proveedor> provs= Proveedor.findAll();
        Iterator<Proveedor> itP= provs.iterator();
        while(itP.hasNext()){
            float aux=itP.next().getFloat("cuenta_corriente");
            if(aux<0)
                cuentasProvs+=(aux*-1);
        }
        lblCuenta.setText(String.valueOf(cuentasSocios));
        lblCuentaProv.setText(String.valueOf(cuentasProvs));
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotAgregarCat;
    private javax.swing.JButton BotEliminar;
    private javax.swing.JButton BotGestionarAreas;
    private javax.swing.JButton BotMod;
    private javax.swing.JButton BotNuevo;
    private javax.swing.JComboBox BoxArea;
    private javax.swing.JComboBox BoxCategoria;
    private javax.swing.JComboBox BoxTipo;
    private javax.swing.JTextField TextMonto;
    private javax.swing.JButton cargar;
    private javax.swing.JComboBox categorias;
    private javax.swing.JTextArea desc;
    private com.toedter.calendar.JDateChooser desde;
    private com.toedter.calendar.JDateChooser fecha;
    private com.toedter.calendar.JDateChooser hasta;
    private javax.swing.JButton imprimir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JFormattedTextField lblCuenta;
    private javax.swing.JFormattedTextField lblCuentaProv;
    private javax.swing.JTable tablaGastos;
    private javax.swing.JLabel total;
    // End of variables declaration//GEN-END:variables
}
