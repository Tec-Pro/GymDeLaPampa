/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Modelos.AlimentosDietas;
import Modelos.Dieta;
import Modelos.Socio;
import Modelos.SociosDietas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Iterator;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author NicoOrcasitas
 */
public class DietasSocioGui extends javax.swing.JDialog {

    /**
     * Creates new form DietasSocioGui
     */
    int idSocio;
        DefaultTableModel tblDefaultDietas;
            DefaultTableModel tblDefaultDietasSocio;
            Socio socio;
            javax.swing.JDialog estaVentana;

    public DietasSocioGui(java.awt.Frame parent, boolean modal, int idSocio) {
        super(parent, modal);
        initComponents();
        estaVentana= this;
        socio= Socio.findById(idSocio);
                tblDefaultDietas = (DefaultTableModel) tblDietas.getModel();
        tblDefaultDietasSocio = (DefaultTableModel) tblDietasSocio.getModel();
                int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;

        InputMap inputMap = tblDietasSocio.getInputMap(condition);
        ActionMap actionMap = tblDietasSocio.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete_row");
        this.idSocio= idSocio;
        actionMap.put("delete_row", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(parent, "¿Desea desligar esta dieta del socio?(la dieta no se borrara)", "¿cancelar?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    SociosDietas.delete("id = ?", tblDietasSocio.getValueAt(tblDietasSocio.getSelectedRow(), 4));
                    tblDefaultDietasSocio.removeRow(tblDietasSocio.getSelectedRow());

            }
            }
        });
        txtBusquedaDietas.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaDietas();
    
         }
        });
               tblDietas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                if(evt.getClickCount()==2){

                int row = tblDietas.getSelectedRow();
                if (row > -1) {
                   VerDietaEditarGui d= new VerDietaEditarGui(parent, true);
                   d.setLocationRelativeTo(estaVentana);
                   d.setVisible(true);
                   switch (d.getReturnStatus()){
                       case VerDietaEditarGui.RET_VER :
                           VerDietaGui verDietaGui=new VerDietaGui(parent, true,AlimentosDietas.where("dieta_id = ?", (Integer) tblDietas.getValueAt(tblDietas.getSelectedRow(), 2)),(String) tblDietas.getValueAt(tblDietas.getSelectedRow(), 0),(String) tblDietas.getValueAt(tblDietas.getSelectedRow(), 1),-1,-1);
                            verDietaGui.setLocationRelativeTo(estaVentana);
                           verDietaGui.setVisible(true);
                            break;
                       case VerDietaEditarGui.RET_AGREGAR:
                           AgregarDietaSocioGui agregarDieta= new AgregarDietaSocioGui(parent, true);
                           agregarDieta.setLocationRelativeTo(estaVentana);
                           agregarDieta.setVisible(true);
                           if(agregarDieta.getReturnStatus()==AgregarDietaSocioGui.RET_OK){
                                Base.openTransaction();
                                SociosDietas.createIt("socio_id",idSocio,"dieta_id",tblDietas.getValueAt(tblDietas.getSelectedRow(), 2),"desde",dateToMySQLDate(agregarDieta.getFechaDesde().getCalendar().getTime(),false),"hasta",dateToMySQLDate(agregarDieta.getFechaDesde().getCalendar().getTime(),false));
                                Base.commitTransaction();
                                cargarEnTablaDietaSocio(SociosDietas.where("socio_id = ?",idSocio));
                                
                           }
                           break;  
                   }                   
                }
            }
            }
                     
        });
              tblDietasSocio.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(evt.getClickCount()==2){
                VerDietaGui verDietaGui= new VerDietaGui(parent, false,AlimentosDietas.where("dieta_id = ?", (Integer) tblDietasSocio.getValueAt(tblDietasSocio.getSelectedRow(), 5)),(String) tblDietasSocio.getValueAt(tblDietasSocio.getSelectedRow(), 0),(String) tblDietasSocio.getValueAt(tblDietasSocio.getSelectedRow(), 1),(Integer) tblDietasSocio.getValueAt(tblDietasSocio.getSelectedRow(), 5),idSocio);
                    verDietaGui.setLocationRelativeTo(estaVentana);
                    verDietaGui.setVisible(true);
            }
            }
        });
              busquedaDietas();
              cargarEnTablaDietaSocio(SociosDietas.where("socio_id = ?",idSocio));
    }
    
    private void busquedaDietas(){
            Base.openTransaction();
            cargarEnTablaDieta(Dieta.where("descripcion like ? or nombre like ?  ", "%" + txtBusquedaDietas.getText() + "%", "%" + txtBusquedaDietas.getText() + "%"));
            Base.commitTransaction();
    }
        /*va true si se quiere usar para mostrarla por pantalla es decir 12/12/2014 y false si va 
    para la base de datos, es decir 2014/12/12*/
    public String dateToMySQLDate(Date fecha, boolean paraMostrar) {
        if(paraMostrar){
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fecha);
        }
        else{
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
        }
    }
        private void cargarEnTablaDieta(LazyList<Dieta> lista) {
        tblDefaultDietas.setRowCount(0);
        Iterator<Dieta> it = lista.iterator();
        while (it.hasNext()) {
            Dieta a = it.next();
            Object row[] = new Object[3];
            row[0] = a.getString("nombre");
            row[1] = a.getString("descripcion");
            row[2] = a.getInteger("id");
            tblDefaultDietas.addRow(row);

        }
    }
        private void cargarEnTablaDietaSocio(LazyList<SociosDietas> lista) {
        tblDefaultDietasSocio.setRowCount(0);
        Iterator<SociosDietas> it = lista.iterator();
        while (it.hasNext()) {
            SociosDietas a = it.next();
            Dieta di= a.parent(Dieta.class);
            Object row[] = new Object[6];
            row[0] = di.getString("nombre");
            row[1] = di.getString("descripcion");
            row[2] = a.getString("desde");
            row[3] = a.getString("hasta");
            row[4] = a.getInteger("id");
            row[5] = di.getInteger("id");
            tblDefaultDietasSocio.addRow(row);

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

        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDietas = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDietasSocio = new javax.swing.JTable();
        txtBusquedaDietas = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Todas las dietas"));

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
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Dietas del socio"));

        tblDietasSocio.setAutoCreateRowSorter(true);
        tblDietasSocio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Descripcion", "Desde", "Hasta", "ID", "ID dieta"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
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
        tblDietasSocio.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(tblDietasSocio);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtBusquedaDietas, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(txtBusquedaDietas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable tblDietas;
    private javax.swing.JTable tblDietasSocio;
    private javax.swing.JTextField txtBusquedaDietas;
    // End of variables declaration//GEN-END:variables
}
