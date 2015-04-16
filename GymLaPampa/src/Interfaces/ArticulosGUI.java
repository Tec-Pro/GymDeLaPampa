/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Controladores.ControladorArticuloGUI;
import Modelos.Articulo;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alan
 */
public class ArticulosGUI extends javax.swing.JInternalFrame {

     private DefaultTableModel TablaArticulosDefault;
    /**
     * Creates new form ArticulosGUI
     */
    public ArticulosGUI() {
        initComponents();
        TablaArticulosDefault = (DefaultTableModel) TablaArticulos.getModel();
        
        TablaArticulos.getColumnModel().getColumn(0).setPreferredWidth(30);
        TablaArticulos.getColumnModel().getColumn(1).setPreferredWidth(300);
        TablaArticulos.getColumnModel().getColumn(2).setPreferredWidth(30);
        TablaArticulos.getColumnModel().getColumn(3).setPreferredWidth(30);
        TablaArticulos.getColumnModel().getColumn(4).setPreferredWidth(30);
    }

    public DefaultTableModel getTablaArticulosDefault() {
        return TablaArticulosDefault;
    }

    public JTable getTablaArticulos() {
        return TablaArticulos;
    }

    public JButton getEliminarBtn() {
        return eliminarBtn;
    }

    public JLabel getEncontradosLbl() {
        return encontradosLbl;
    }

    public JTextField getTxtPrecioCompra() {
        return txtPrecioCompra;
    }

    public JTextField getTxtStock() {
        return txtStock;
    }

   
    public JButton getModificarBtn() {
        return modificarBtn;
    }

    public JComboBox getBoxProveedor() {
        return boxProveedor;
    }

    public JButton getNuevoBtn() {
        return nuevoBtn;
    }

    public JTextArea getAreaDesc() {
        return areaDesc;
    }

    public JTextField getTxtArticulo() {
        return txtArticulo;
    }

    public JTextField getTxtBusqueda() {
        return txtBusqueda;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    

    public void ApreteBtnNuevoModificar(){
        txtArticulo.setEnabled(true);
        txtCodigo.setEnabled(true);
        txtPrecio.setEnabled(true);
        txtPrecioCompra.setEnabled(true);
        txtStock.setEnabled(true);
        areaDesc.setEnabled(true);
        modificarBtn.setEnabled(false);
        nuevoBtn.setText("Guardar");
        eliminarBtn.setText("Cancelar");
        eliminarBtn.setEnabled(true);
        boxProveedor.setEnabled(true);
    }
    
    public void EstadoInicial(){
        txtArticulo.setEnabled(false);
        txtCodigo.setEnabled(false);
        txtPrecio.setEnabled(false);
        txtPrecioCompra.setEnabled(false);
        txtStock.setEnabled(false);
        areaDesc.setEnabled(false);
        nuevoBtn.setText("Nuevo");
        eliminarBtn.setText("Eliminar");
        eliminarBtn.setEnabled(false);
        modificarBtn.setEnabled(false);
        boxProveedor.setEnabled(false);
    }
    
    public void reClick(){
        EstadoInicial();
        LimpiarCampos();
    }
    
    public void EstadoLuegoDeModificar(){
        txtArticulo.setEnabled(false);
        txtCodigo.setEnabled(false);
        txtPrecio.setEnabled(false);
        txtPrecioCompra.setEnabled(false);
        txtStock.setEnabled(false);
        areaDesc.setEnabled(false);
        nuevoBtn.setText("Nuevo");
        eliminarBtn.setText("Eliminar");
        eliminarBtn.setEnabled(true);
        modificarBtn.setEnabled(true);
        boxProveedor.setEnabled(false);
    }
    
    public void EstadoArticuloSeleccionado(){
        txtArticulo.setEnabled(false);
        txtCodigo.setEnabled(false);
        txtPrecio.setEnabled(false);
        txtPrecioCompra.setEnabled(false);
        txtStock.setEnabled(false);
        areaDesc.setEnabled(false);
        nuevoBtn.setText("Nuevo");
        eliminarBtn.setText("Eliminar");
        eliminarBtn.setEnabled(true);
        modificarBtn.setEnabled(true);
        boxProveedor.setEnabled(false);
    }
    
    public void LimpiarCampos(){
        txtPrecio.setText("");
        txtPrecioCompra.setText("");
        txtArticulo.setText("");
        txtCodigo.setText("");
        areaDesc.setText("");
        txtStock.setText("");
        boxProveedor.setSelectedIndex(0);
    }
    
    public void CargarCampos(Articulo art) {
        
        txtCodigo.setText(art.getString("codigo"));
        txtArticulo.setText(art.getString("articulo"));
        txtPrecioCompra.setText(art.getBigDecimal("precio_compra").setScale(2).toString());
        txtStock.setText(art.getString("stock"));
        txtPrecio.setText(art.getBigDecimal("precio").setScale(2).toString());

        areaDesc.setText(art.getString("descripcion"));
       
//        proveedores.removeAllItems();
//        Proveedor proveedor = art.parent(Proveedor.class);
//        if (proveedor != null) {
//            String nombreProv = proveedor.getString("nombre");
//            proveedores.addItem(nombreProv);
//            proveedores.setSelectedItem(nombreProv);
//        } else {
//            proveedores.addItem("");
//            proveedores.setSelectedItem("");
//        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaArticulos = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        encontradosLbl = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtBusqueda = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        nuevoBtn = new javax.swing.JButton();
        modificarBtn = new javax.swing.JButton();
        eliminarBtn = new javax.swing.JButton();
        txtArticulo = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaDesc = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPrecioCompra = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        boxProveedor = new javax.swing.JComboBox();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestion de articulos");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Articulos"));

        TablaArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Producto", "Stock", "Precio venta", "Precio compra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TablaArticulos);

        jLabel5.setText("Encontrados: ");

        encontradosLbl.setText("xxx");

        jLabel17.setText("Buscar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(encontradosLbl)))
                .addGap(13, 13, 13))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(encontradosLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del articulo"));

        jLabel6.setText("Articulo");

        jLabel11.setText("Precio de venta $");

        txtPrecio.setEnabled(false);
        txtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioActionPerformed(evt);
            }
        });

        nuevoBtn.setText("Nuevo");

        modificarBtn.setText("Modificar");
        modificarBtn.setEnabled(false);

        eliminarBtn.setText("Eliminar");
        eliminarBtn.setEnabled(false);

        txtArticulo.setEnabled(false);

        jLabel18.setText("Codigo");

        txtCodigo.setEnabled(false);

        jLabel1.setText("Descripcion");

        areaDesc.setColumns(20);
        areaDesc.setRows(5);
        areaDesc.setEnabled(false);
        jScrollPane2.setViewportView(areaDesc);

        jLabel2.setText("Stock");

        txtStock.setEnabled(false);

        jLabel3.setText("Precio de compra  $");

        txtPrecioCompra.setEnabled(false);

        jLabel4.setText("Proveedor");

        boxProveedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar" }));
        boxProveedor.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boxProveedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPrecio)
                            .addComponent(txtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nuevoBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modificarBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eliminarBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(nuevoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(modificarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(eliminarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(boxProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaArticulos;
    private javax.swing.JTextArea areaDesc;
    private javax.swing.JComboBox boxProveedor;
    private javax.swing.JButton eliminarBtn;
    private javax.swing.JLabel encontradosLbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton modificarBtn;
    private javax.swing.JButton nuevoBtn;
    private javax.swing.JTextField txtArticulo;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtPrecioCompra;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables

    public void setActionListener(ActionListener lis) {
        nuevoBtn.addActionListener(lis);
        modificarBtn.addActionListener(lis);
        eliminarBtn.addActionListener(lis);
    }
}