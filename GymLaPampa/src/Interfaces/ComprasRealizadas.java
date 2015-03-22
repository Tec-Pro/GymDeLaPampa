/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import ABMs.ABMCompra;
import java.awt.Frame;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Modelos.Articulo;
import Modelos.ArticulosCompras;
import Modelos.Compra;
import Modelos.Pago;
import Modelos.Proveedor;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author nico
 */
public class ComprasRealizadas extends javax.swing.JDialog {
private DefaultTableModel tablaComprasDefault;
    private RealizarPagoGui realizarPagoGui;
    Proveedor proveedor;
    Frame papa;
    private CompraGui compraGui;
    private java.util.List<Compra> listCompras;

    /**
     * Creates new form ComprasRealizadas
     */
    public ComprasRealizadas(java.awt.Frame parent, boolean modal,Proveedor proveedor,CompraGui compraGui) {
        
        super(parent, modal);
        initComponents();
        papa=parent;
        this.setLocationByPlatform(true);
        this.proveedor=proveedor;
        tablaComprasDefault=(DefaultTableModel) tablaCompras.getModel();
                this.compraGui = compraGui;
                        listCompras = new LinkedList();
                        cargarCompras();

        tablaCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaComprasClicked(evt);
            }
        });
    }




    private void cargarCompras() {
        
        listCompras = proveedor.getAll(Compra.class);
        tablaComprasDefault.setRowCount(0);
        Iterator<Compra> it = listCompras.iterator();
        while (it.hasNext()) {
            Compra compra = it.next();
            Object row[] = new Object[6];
            Date sqlFecha = compra.getDate("fecha");
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            row[0] = compra.getId();
            row[1] = sdf.format(sqlFecha);
            row[2] =(compra.getBigDecimal("monto").setScale(2, RoundingMode.CEILING)).toString();
            row[3] = compra.getBoolean("pago");
            row[4]= compra.getBigDecimal("monto").subtract(compra.getBigDecimal("descuento").multiply(compra.getBigDecimal("monto").divide(new BigDecimal(100)))).setScale(2, RoundingMode.CEILING).toString();
            row[5]= compra.get("fecha_pago");
            tablaComprasDefault.addRow(row);
        }
        
    }
    
    
    private void tablaComprasClicked(java.awt.event.MouseEvent evt) {
        borrarCompra.setEnabled(true);
        if((boolean)tablaCompras.getValueAt(tablaCompras.getSelectedRow(),3)){
            pagarFac.setEnabled(false);
        }
        else{
            pagarFac.setEnabled(true);
        }
        if (evt.getClickCount() == 2) {
            
            compraGui.limpiarVentana();
            compraGui.paraVerCompra(true);
             Base.openTransaction();
            Compra compra = Compra.findById(tablaCompras.getValueAt(tablaCompras.getSelectedRow(), 0));
            Base.commitTransaction();
            //PODRÍA HACERSE UNA FUNCION EN COMPRAGUI PARA CARGAR LA COMPRA
            compraGui.getProveedorCompra().setText(proveedor.getString("nombre"));
             Base.openTransaction();
            LazyList<ArticulosCompras> artCom = ArticulosCompras.find("compra_id = ?", compra.getId());
            Base.commitTransaction();
            Iterator<ArticulosCompras> it = artCom.iterator();
            while (it.hasNext()) {
                ArticulosCompras prodCom = it.next();
                 Base.openTransaction();
                Articulo art = Articulo.findById(prodCom.get("articulo_id"));
                Base.commitTransaction();
                if (art != null) {
                    Integer numeroProducto = art.getInteger("id");
                    String codigo = art.getString("codigo");
                    BigDecimal precio = prodCom.getBigDecimal("precio_final").setScale(2, RoundingMode.CEILING);
                    BigDecimal cantidad = prodCom.getBigDecimal("cantidad").setScale(2, RoundingMode.CEILING);
                    Object cols[] = new Object[7];
                    cols[0] = numeroProducto;
                    cols[1] = cantidad;
                    cols[2] = codigo;
                    cols[3]=    art.getString("descripcion");
                    cols[4] = precio;
                    
                    cols[6] = (precio.multiply(cantidad)).setScale(2, RoundingMode.CEILING);
                    compraGui.getTablaCompraDefault().addRow(cols);
                }
            }
            compraGui.getTotalCompra().setText(String.valueOf(compra.getBigDecimal("monto").setScale(2, RoundingMode.CEILING)));
            compraGui.getDescuento().setVisible(true);
            compraGui.getLabelTotalConDes().setVisible(true);
            compraGui.getDescuento().setText(compra.getBigDecimal("monto").subtract(compra.getBigDecimal("descuento").multiply(compra.getBigDecimal("monto").divide(new BigDecimal(100)))).setScale(2, RoundingMode.CEILING)+" ("+compra.getString("descuento")+" %)");
            this.dispose();
            compraGui.setVisible(true);
            compraGui.toFront();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaCompras = new javax.swing.JTable();
        borrarCompra = new javax.swing.JButton();
        pagarFac = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Compras realizadas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 3, 14))); // NOI18N

        tablaCompras.setAutoCreateRowSorter(true);
        tablaCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Fecha", "Monto", "Pagada", "Total con Descuento", "Fecha Pago"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.String.class, java.lang.String.class
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
        tablaCompras.setToolTipText("Realice doble click sobre la compra para verla en datalle");
        tablaCompras.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(tablaCompras);

        borrarCompra.setText("Borrar");
        borrarCompra.setToolTipText("Borrar compra seleccionada");
        borrarCompra.setEnabled(false);
        borrarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarCompraActionPerformed(evt);
            }
        });

        pagarFac.setText("Pagar Fac");
        pagarFac.setEnabled(false);
        pagarFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagarFacActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(borrarCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(pagarFac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(borrarCompra)
                    .addComponent(pagarFac)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 563, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pagarFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagarFacActionPerformed
System.out.println("realizar pago pulsado");
             Base.openTransaction();
            realizarPagoGui = new RealizarPagoGui(papa, true, proveedor, (Compra)Compra.findById(tablaCompras.getValueAt(tablaCompras.getSelectedRow(), 0)));
            realizarPagoGui.setLocationRelativeTo(this);
            realizarPagoGui.setVisible(true);
            Base.commitTransaction();
            
            cargarCompras();
            pagarFac.setEnabled(false);
                }//GEN-LAST:event_pagarFacActionPerformed

    private void borrarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarCompraActionPerformed
        int row = tablaCompras.getSelectedRow();
            if (row > -1) {
                 Base.openTransaction();
               Object id =  tablaCompras.getValueAt(row, 0);
                Compra comp = Compra.findById(id);
                ABMCompra abmC = new ABMCompra();
                Base.commitTransaction();
//                if (abmC.baja(comp)) { anular después jeeee   
//                    JOptionPane.showMessageDialog(this, "¡Compra eliminada exitosamente!");
//                    cargarCompras();
//                } else {
//                    JOptionPane.showMessageDialog(this, "Ocurrió un error, la compra no ha sido eliminada", "Error!", JOptionPane.ERROR_MESSAGE);
//
//                }
                
            }
    }//GEN-LAST:event_borrarCompraActionPerformed
    public JTable getComprasRealizadas() {
        return tablaCompras;
    }

    public void setComprasRealizadas(JTable comprasRealizadas) {
        this.tablaCompras = comprasRealizadas;
    }
    public JButton getBorrarCompra() {
        return borrarCompra;
    }    public JButton getPagarFac() {
        return pagarFac;
    }
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton borrarCompra;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JButton pagarFac;
    private javax.swing.JTable tablaCompras;
    // End of variables declaration//GEN-END:variables
}
