/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import Modelos.Arancel;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Modelos.Socio;
import Modelos.Socioarancel;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author nico
 */
public class CumpleaniosGui extends javax.swing.JInternalFrame {

    DefaultTableModel cumpleHoyDefault;
 DefaultTableModel cumpleSemanaDefault;  
    AbmClienteGui clienteGui;
    Controladores.ControladorAbmCliente controladorAbmCliente;
    /**
     * 
     * Creates new form CumpleaniosGui
     */
    public CumpleaniosGui(AbmClienteGui clienteGui,Controladores.ControladorAbmCliente controladorAbmCliente) {
        initComponents();
        this.clienteGui= clienteGui;
        cumpleHoyDefault= (DefaultTableModel) cumpleHoy.getModel();
        cumpleSemanaDefault= (DefaultTableModel) cumpleSemana.getModel();
        this.controladorAbmCliente= controladorAbmCliente;
        cumpleHoy.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaHoy(evt);
            }
        });
                cumpleSemana.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaSemana(evt);
            }
        });
                abrirBase();
          cargarCumple();

    }
    
    public void abrirBase(){
         if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/GYM", "root", "root");
        }
        }
        

                
    public void cargarCumple(){
        cumpleHoyDefault.setRowCount(0);
        cumpleSemanaDefault.setRowCount(0);
         Base.openTransaction();
             LazyList<Socio> clientes= Socio.findBySQL("select * FROM socios WHERE DATE_FORMAT(fecha_nac, '%m%d') = DATE_FORMAT(?,'%m%d')",dateToMySQLDate(Calendar.getInstance().getTime(),false));
             Base.commitTransaction();
             Iterator<Socio> it= clientes.iterator();
                while(it.hasNext()){
                    Socio cli= it.next();
                    String cols[] = new String[7];
                    cols[0] = cli.getString("nombre");
                    cols[1] = cli.getString("apellido");
                    cols[2] = dateToMySQLDate(cli.getDate("fecha_nac"), true);
                    cols[3] = cli.getString("tel");
                    cols[4] = cli.getString("mail");
                    cols[5] = cli.getString("facebook");
                    cols[6] = cli.getString("id");
                    cumpleHoyDefault.addRow(cols);
                    
                }
                Date date= Calendar.getInstance().getTime();
                date.setDate(date.getDate()+7);
                clientes=null;
                 Base.openTransaction();
                 clientes= Socio.findBySQL("select * from socios where DATE_FORMAT(fecha_nac,'%m %d') >= DATE_FORMAT( ?,'%m %d') AND DATE_FORMAT(fecha_nac, '%m%d') < DATE_FORMAT(?,'%m%d')", dateToMySQLDate(Calendar.getInstance().getTime(),false),dateToMySQLDate(date,false));
                Base.commitTransaction();
                                it= clientes.iterator();
                while(it.hasNext()){
                    Socio cli= it.next();
                    String cols[] = new String[7];
                    cols[0] = cli.getString("nombre");
                    cols[1] = cli.getString("apellido");
                    cols[2] = dateToMySQLDate(cli.getDate("fecha_nac"), true);
                    cols[3] = cli.getString("tel");
                    cols[4] = cli.getString("mail");
                    cols[5] = cli.getString("facebook");
                    cols[6] = cli.getString("id");
                     cumpleSemanaDefault.addRow(cols);
                    
                } 
    }
            /*va true si se quiere usar para mostrarla por pantalla es decir 12/12/2014 y false si va 
     para la base de datos, es decir 2014/12/12*/
    public String dateToMySQLDate(Date fecha, boolean paraMostrar) {
        if(fecha!=null){
        if (paraMostrar) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(fecha);
        } else {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(fecha);
        }
        }else{
            return "";
        }
    }
    
    private void tablaHoy(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
             Base.openTransaction();
            Socio s= Socio.findById(cumpleHoy.getValueAt(cumpleHoy.getSelectedRow(), 6));
                 Base.commitTransaction();
            
                 clienteGui.setTitle(s.getString("APELLIDO") + " " + s.getString("NOMBRE"));
            clienteGui.setVisible(true);
            clienteGui.toFront();
            controladorAbmCliente.setSocio(s);
            clienteGui.getNombre().setText(s.getString("NOMBRE"));
            clienteGui.getApellido().setText(s.getString("APELLIDO"));
            clienteGui.getTelefono().setText(s.getString("TEL"));
            clienteGui.getDni().setText(s.getString("DNI"));
            clienteGui.getDireccion().setText(s.getString("DIR"));
            if (s.getString("SEXO").equals("M")) {
                clienteGui.getSexo().setSelectedIndex(1);
            } else {
                clienteGui.getSexo().setSelectedIndex(0);
            }
            clienteGui.getFechaNacimJDate().setDate(s.getDate("FECHA_NAC"));;
            clienteGui.getLabelFechaIngreso().setText(dateToMySQLDate(s.getDate("FECHA_ING"), true));
            clienteGui.getLabelFechaVenci().setText(dateToMySQLDate(s.getDate("FECHA_PROX_PAGO"), true));
            clienteGui.getSaldoCorriente().setText(s.getBigDecimal("cuenta_corriente").toString());
            clienteGui.getTablaActivDefault().setRowCount(0);
            LazyList<Socioarancel> ListSocAran = Socioarancel.where("id_socio = ?", s.get("ID_DATOS_PERS"));
            Iterator<Socioarancel> ite = ListSocAran.iterator();
            while (ite.hasNext()) {
                Socioarancel sa = ite.next();
                Arancel a = Arancel.first("id = ?", sa.get("id_arancel"));
                Object row1[] = new Object[2];
                row1[0] = a.getString("nombre");
                row1[1] = true;
                clienteGui.getTablaActivDefault().addRow(row1);
                /*Se debe abrir la ventana de clientes para permitir el alta de giles*/
            }
                 
                 
                 
            clienteGui.setVisible(true);
            clienteGui.toFront();
            this.dispose();
        }
    }
    
        private void tablaSemana(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
             Base.openTransaction();
            Socio s= Socio.findById(cumpleSemana.getValueAt(cumpleSemana.getSelectedRow(), 6));
                   Base.commitTransaction();
                   
                   clienteGui.setTitle(s.getString("APELLIDO") + " " + s.getString("NOMBRE"));
            clienteGui.setVisible(true);
            clienteGui.toFront();
            controladorAbmCliente.setSocio(s);
            clienteGui.getNombre().setText(s.getString("NOMBRE"));
            clienteGui.getApellido().setText(s.getString("APELLIDO"));
            clienteGui.getTelefono().setText(s.getString("TEL"));
            clienteGui.getDni().setText(s.getString("DNI"));
            clienteGui.getDireccion().setText(s.getString("DIR"));
            if (s.getString("SEXO").equals("M")) {
                clienteGui.getSexo().setSelectedIndex(1);
            } else {
                clienteGui.getSexo().setSelectedIndex(0);
            }
            clienteGui.getFechaNacimJDate().setDate(s.getDate("FECHA_NAC"));;
            clienteGui.getLabelFechaIngreso().setText(dateToMySQLDate(s.getDate("FECHA_ING"), true));
            clienteGui.getLabelFechaVenci().setText(dateToMySQLDate(s.getDate("FECHA_PROX_PAGO"), true));
            clienteGui.getSaldoCorriente().setText(s.getBigDecimal("cuenta_corriente").toString());
            clienteGui.getTablaActivDefault().setRowCount(0);
            LazyList<Socioarancel> ListSocAran = Socioarancel.where("id_socio = ?", s.get("ID_DATOS_PERS"));
            Iterator<Socioarancel> ite = ListSocAran.iterator();
            while (ite.hasNext()) {
                Socioarancel sa = ite.next();
                Arancel a = Arancel.first("id = ?", sa.get("id_arancel"));
                Object row1[] = new Object[2];
                row1[0] = a.getString("nombre");
                row1[1] = true;
                clienteGui.getTablaActivDefault().addRow(row1);
                /*Se debe abrir la ventana de clientes para permitir el alta de giles*/
            }
                   
                   
                   
                   clienteGui.setVisible(true);
            clienteGui.toFront();
            this.dispose();
        }
    }

    public DefaultTableModel getCumpleHoyDefault() {
        return cumpleHoyDefault;
    }

    public DefaultTableModel getCumpleSemanaDefault() {
        return cumpleSemanaDefault;
    }

    public JTable getCumpleHoy() {
        return cumpleHoy;
    }

    public JTable getCumpleSemana() {
        return cumpleSemana;
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
        cumpleHoy = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        cumpleSemana = new javax.swing.JTable();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cumpleaños");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hoy cumple años", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 1, 18))); // NOI18N

        cumpleHoy.setAutoCreateRowSorter(true);
        cumpleHoy.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Apellido", "Fecha nacimiento", "Telefono", "Email", "Facebook", "Id interno"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        cumpleHoy.setName("Cumple"); // NOI18N
        cumpleHoy.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(cumpleHoy);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "En la semana cumple años", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Schoolbook L", 1, 18))); // NOI18N

        cumpleSemana.setAutoCreateRowSorter(true);
        cumpleSemana.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Apellido", "Fecha nacimiento", "Telefono", "Email", "Facebook", "Id interno"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        cumpleSemana.setName("Cumple"); // NOI18N
        cumpleSemana.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(cumpleSemana);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable cumpleHoy;
    private javax.swing.JTable cumpleSemana;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
