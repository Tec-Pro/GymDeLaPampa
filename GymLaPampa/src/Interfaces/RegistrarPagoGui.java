/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import ABMs.ABMSocios;
import Controladores.CobroACuentaGui;
import Modelos.Arancel;
import Modelos.Asistencia;
import Modelos.Pago;
import Modelos.Socio;
import Modelos.Socioarancel;
import java.awt.Frame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author nico
 */
public class RegistrarPagoGui extends javax.swing.JDialog {

    Socio socio;
    DefaultTableModel tablaDefault;

    /**
     * Creates new form RegistrarPagoGui
     */
    public RegistrarPagoGui(java.awt.Frame parent, boolean modal, Socio socio) {
        super(parent, modal);
        initComponents();
        tablaDefault = (DefaultTableModel) tablaActividades.getModel();
        this.socio = socio;
        fecha.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                calcularFechavence();
            }
        });
        fecha.setDate(Calendar.getInstance().getTime());
        nombreCliente.setText(socio.getString("NOMBRE") + " " + socio.getString("APELLIDO"));
        cargarActividades();
        calcularTotal();
        tablaActividades.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                calcularTotal();
            }
        });
        labelVencio.setText(dateToMySQLDate(socio.getDate("FECHA_PROX_PAGO"), true));
        LazyList<Asistencia> asistencias = Asistencia.where("ID_DATOS_PERS = ? and FECHA >= ?", socio.get("ID_DATOS_PERS"), socio.getDate("FECHA_ULT_PAGO"));
        asistencias.orderBy("FECHA");
        if (asistencias.size() > 0) {
            Asistencia ultAsistencia = asistencias.get(asistencias.size() - 1); //ultima asistencia
            labelAsistio.setText(dateToMySQLDate(ultAsistencia.getDate("fecha"), true));
        }
        asistencias = Asistencia.where("ID_DATOS_PERS = ? and FECHA > ?", socio.get("ID_DATOS_PERS"), socio.getDate("FECHA_PROX_PAGO"));
        asistencias.orderBy("FECHA");
        Iterator<Asistencia> it = asistencias.iterator();
        if (asistencias.size() > 0) {
            Asistencia primAsistencia = asistencias.get(0);//Es la primer fecha después del vencimiento
            primAsis.setText(dateToMySQLDate(primAsistencia.getDate("fecha"), true));
        }
    }

    private void calcularFechavence() {
        LazyList<Asistencia> asistencias = Asistencia.where("ID_DATOS_PERS = ? and FECHA > ?", socio.get("ID_DATOS_PERS"), socio.getDate("FECHA_PROX_PAGO"));
        asistencias.orderBy("FECHA");
        Iterator<Asistencia> it = asistencias.iterator();
        if (asistencias.size() > 0) {
            Asistencia primAsistencia = asistencias.get(0);//Es la primer fecha después del vencimiento
            Asistencia ultAsistencia = asistencias.get(asistencias.size() - 1); //ultima asistencia
            Date fechaVencimiento = socio.getDate("FECHA_PROX_PAGO");
            boolean sePasoPeroNoTanto = ultAsistencia.getDate("fecha").before(Calendar.getInstance().getTime()) && ultAsistencia.getDate("fecha").after(fechaVencimiento);
            boolean sePasoYActivo = sePasoPeroNoTanto && socio.getBoolean("activo");
            if (sePasoYActivo) {
                int i = cantidadMembresia.getSelectedIndex();
                Calendar cal = fecha.getCalendar();
                switch (i) {
                    case 0:

                        cal.setTime(primAsistencia.getDate("fecha"));
                        cal.add(Calendar.DATE, 1);
                        fechaVence.setCalendar(cal);
                        break;
                    case 1:
                        cal.setTime(primAsistencia.getDate("fecha"));
                        cal.add(Calendar.DATE, 2);
                        fechaVence.setCalendar(cal);
                        break;
                    case 2:
                        cal.setTime(primAsistencia.getDate("fecha"));
                        cal.add(Calendar.DATE, 3);
                        fechaVence.setCalendar(cal);
                        break;
                    case 3:
                        cal.setTime(primAsistencia.getDate("fecha"));
                        cal.add(Calendar.DATE, 4);
                        fechaVence.setCalendar(cal);
                        break;
                    case 4:
                        cal.setTime(primAsistencia.getDate("fecha"));
                        cal.add(Calendar.DATE, 5);
                        fechaVence.setCalendar(cal);
                        break;
                    case 5:
                        cal.setTime(primAsistencia.getDate("fecha"));
                        cal.add(Calendar.DATE, 6);
                        fechaVence.setCalendar(cal);
                        break;
                    case 6:
                        cal.setTime(primAsistencia.getDate("fecha"));
                        cal.add(Calendar.DATE, 7);
                        fechaVence.setCalendar(cal);
                        break;
                    case 7:
                        cal.setTime(primAsistencia.getDate("fecha"));
                        cal.add(Calendar.DATE, 10);
                        fechaVence.setCalendar(cal);
                        break;
                    case 8:
                        cal.setTime(primAsistencia.getDate("fecha"));
                        cal.add(Calendar.DATE, 15);
                        fechaVence.setCalendar(cal);
                        break;
                    case 9:
                        cal.setTime(primAsistencia.getDate("fecha"));
                        cal.add(Calendar.MONTH, 1);
                        fechaVence.setCalendar(cal);
                        break;
                    case 10:
                        cal.setTime(primAsistencia.getDate("fecha"));
                        cal.add(Calendar.MONTH, 2);
                        fechaVence.setCalendar(cal);
                        break;
                    case 11:
                        cal.setTime(primAsistencia.getDate("fecha"));
                        cal.add(Calendar.MONTH, 3);
                        fechaVence.setCalendar(cal);
                        break;
                    case 12:
                        cal.setTime(primAsistencia.getDate("fecha"));
                        cal.add(Calendar.MONTH, 6);
                        fechaVence.setCalendar(cal);
                        break;
                    case 13:
                        cal.setTime(primAsistencia.getDate("fecha"));
                        cal.add(Calendar.YEAR, 1);
                        fechaVence.setCalendar(cal);
                        break;
                }

            }

        } else {
            int i = cantidadMembresia.getSelectedIndex();
            Calendar cal = fecha.getCalendar();
            switch (i) {
                case 0:

                    cal.setTime(fecha.getDate());
                    cal.add(Calendar.DATE, 1);
                    fechaVence.setCalendar(cal);
                    break;
                case 1:
                    cal.setTime(fecha.getDate());
                    cal.add(Calendar.DATE, 2);
                    fechaVence.setCalendar(cal);
                    break;
                case 2:
                    cal.setTime(fecha.getDate());
                    cal.add(Calendar.DATE, 3);
                    fechaVence.setCalendar(cal);
                    break;
                case 3:
                    cal.setTime(fecha.getDate());
                    cal.add(Calendar.DATE, 4);
                    fechaVence.setCalendar(cal);
                    break;
                case 4:
                    cal.setTime(fecha.getDate());
                    cal.add(Calendar.DATE, 5);
                    fechaVence.setCalendar(cal);
                    break;
                case 5:
                    cal.setTime(fecha.getDate());
                    cal.add(Calendar.DATE, 6);
                    fechaVence.setCalendar(cal);
                    break;
                case 6:
                    cal.setTime(fecha.getDate());
                    cal.add(Calendar.DATE, 7);
                    fechaVence.setCalendar(cal);
                    break;
                case 7:
                    cal.setTime(fecha.getDate());
                    cal.add(Calendar.DATE, 10);
                    fechaVence.setCalendar(cal);
                    break;
                case 8:
                    cal.setTime(fecha.getDate());
                    cal.add(Calendar.DATE, 15);
                    fechaVence.setCalendar(cal);
                    break;
                case 9:
                    cal.setTime(fecha.getDate());
                    cal.add(Calendar.MONTH, 1);
                    fechaVence.setCalendar(cal);
                    break;
                case 10:
                    cal.setTime(fecha.getDate());
                    cal.add(Calendar.MONTH, 2);
                    fechaVence.setCalendar(cal);
                    break;
                case 11:
                    cal.setTime(fecha.getDate());
                    cal.add(Calendar.MONTH, 3);
                    fechaVence.setCalendar(cal);
                    break;
                case 12:
                    cal.setTime(fecha.getDate());
                    cal.add(Calendar.MONTH, 6);
                    fechaVence.setCalendar(cal);
                    break;
                case 13:
                    cal.setTime(fecha.getDate());
                    cal.add(Calendar.YEAR, 1);
                    fechaVence.setCalendar(cal);
                    break;
            }

        }

    }

    private void calcularTotal() {
        BigDecimal totalB = new BigDecimal(0);
        if (tablaDefault.getRowCount() > -1) {
            int rows = tablaDefault.getRowCount();
            for (int i = 0; i < rows; i++) {
                if (tablaActividades.getValueAt(i, 2) != null) {
                    if ((boolean) tablaActividades.getValueAt(i, 2) == true) {
                        BigDecimal precio = (BigDecimal) tablaActividades.getValueAt(i, 1);

                        totalB = totalB.add(BigDecimal.valueOf(precio.doubleValue()));
                    }
                }
            }
            int i = cantidadMembresia.getSelectedIndex();
            switch (i) {
                case 0:
                    total.setText(totalB.divide(new BigDecimal(30)).setScale(2, RoundingMode.CEILING).toString());
                    break;
                case 1:
                    total.setText(totalB.divide(new BigDecimal(30)).multiply(new BigDecimal(2)).setScale(2, RoundingMode.CEILING).toString());
                    break;
                case 2:
                    total.setText(totalB.divide(new BigDecimal(30)).multiply(new BigDecimal(3)).setScale(2, RoundingMode.CEILING).toString());
                    break;
                case 3:
                    total.setText(totalB.divide(new BigDecimal(30)).multiply(new BigDecimal(4)).setScale(2, RoundingMode.CEILING).toString());
                    break;
                case 4:
                    total.setText(totalB.divide(new BigDecimal(30)).multiply(new BigDecimal(5)).setScale(2, RoundingMode.CEILING).toString());
                    break;
                case 5:
                    total.setText(totalB.divide(new BigDecimal(30)).multiply(new BigDecimal(6)).setScale(2, RoundingMode.CEILING).toString());
                    break;
                case 6:
                    total.setText(totalB.divide(new BigDecimal(30)).multiply(new BigDecimal(7)).setScale(2, RoundingMode.CEILING).toString());
                    break;
                case 7:
                    total.setText(totalB.divide(new BigDecimal(3)).setScale(2, RoundingMode.CEILING).toString());
                    break;
                case 8:
                    total.setText(totalB.divide(new BigDecimal(2)).setScale(2, RoundingMode.CEILING).toString());
                    break;
                case 9:
                    total.setText(totalB.setScale(2, RoundingMode.CEILING).toString());

                    break;
                case 10:
                    total.setText(totalB.multiply(new BigDecimal(2)).setScale(2, RoundingMode.CEILING).toString());

                    break;
                case 11:
                    total.setText(totalB.multiply(new BigDecimal(3)).setScale(2, RoundingMode.CEILING).toString());

                    break;
                case 12:
                    total.setText(totalB.multiply(new BigDecimal(6)).setScale(2, RoundingMode.CEILING).toString());

                    break;
                case 13:
                    total.setText(totalB.multiply(new BigDecimal(12)).setScale(2, RoundingMode.CEILING).toString());

                    break;
            }

        }
    }

    private void cargarActividades() {
        tablaDefault.setRowCount(0);
        LazyList<Socioarancel> socaran = Socioarancel.where("id_socio = ?", socio.get("ID_DATOS_PERS"));
        Iterator<Socioarancel> iter = socaran.iterator();
        LinkedList<String> tieneAran = new LinkedList<>();
        while (iter.hasNext()) {
            Socioarancel socioArancel = iter.next();
            Arancel ar = Arancel.first("id = ?", socioArancel.get("id_arancel"));
            tieneAran.add(ar.getString("id"));
            String nombre = ar.getString("nombre");
            BigDecimal precio = ar.getBigDecimal("precio");
            Object row[] = new Object[3];
            row[0] = nombre;
            row[1] = precio;
            row[2] = true;
            tablaDefault.addRow(row);
        }

        /////////////////////////////////////////////////////
        ////////////////////////////////////////////////////
        LazyList<Arancel> listArancel = Arancel.findAll();
        LinkedList<String> aranceles = new LinkedList();
        Iterator<Arancel> it = listArancel.iterator();
        while (it.hasNext()) {
            Arancel a = it.next();
            aranceles.add(a.getString("id"));
        }
        aranceles.removeAll(tieneAran);
        Iterator<String> itt = aranceles.iterator();
        while (itt.hasNext()) {
            String id = itt.next();
            Arancel ar = Arancel.findById(id);
            String nombre = ar.getString("nombre");
            BigDecimal precio = ar.getBigDecimal("precio");
            Object[] fil = new Object[3];
            fil[0] = nombre;
            fil[1] = precio;
            //fil[2] = false;
            tablaDefault.addRow(fil);
        }
    }

    /*va true si se quiere usar para mostrarla por pantalla es decir 12/12/2014 y false si va 
     para la base de datos, es decir 2014/12/12*/
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

    public JLabel getLabelAsistio() {
        return labelAsistio;
    }

    public JLabel getLabelVencio() {
        return labelVencio;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaActividades = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        nombreCliente = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        cancelar = new javax.swing.JButton();
        realizar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        fechaVence = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        labelVencio = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        labelAsistio = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        primAsis = new javax.swing.JLabel();
        cantidadMembresia = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Realizar cobro");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Actividades que realiza"));
        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        tablaActividades.setAutoCreateRowSorter(true);
        tablaActividades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Actividad", "Precio", "Realiza"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.math.BigDecimal.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaActividades.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tablaActividades.setShowHorizontalLines(false);
        tablaActividades.setShowVerticalLines(false);
        tablaActividades.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablaActividadesPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(tablaActividades);
        if (tablaActividades.getColumnModel().getColumnCount() > 0) {
            tablaActividades.getColumnModel().getColumn(0).setPreferredWidth(200);
            tablaActividades.getColumnModel().getColumn(1).setPreferredWidth(30);
            tablaActividades.getColumnModel().getColumn(2).setResizable(false);
            tablaActividades.getColumnModel().getColumn(2).setPreferredWidth(1);
        }

        jPanel4.add(jScrollPane1);

        jLabel1.setText("Pago realizado por:");

        nombreCliente.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        nombreCliente.setText("Pepe");

        jLabel2.setText("TOTAL:");

        total.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        total.setForeground(new java.awt.Color(118, 209, 69));
        total.setText("X");

        cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cross.png"))); // NOI18N
        cancelar.setText("Cancelar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        realizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/money.png"))); // NOI18N
        realizar.setText("Realizar cobro");
        realizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                realizarActionPerformed(evt);
            }
        });

        jLabel3.setText("Fecha");

        jLabel4.setText("Vence");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Información adicional"));

        jLabel5.setText("Próximo vencimiento:");

        labelVencio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelVencio.setText("ocurrió un error");

        jLabel7.setText("Última asistencia");

        labelAsistio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelAsistio.setText("Aún no asistió");

        jLabel6.setText("Primera asistencia después de vencido:");

        primAsis.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        primAsis.setText("no asistió despues del vencimiento");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelAsistio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelVencio, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(primAsis, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(labelVencio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(labelAsistio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(primAsis)))
        );

        cantidadMembresia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 Día", "2 Días", "3 Días", "4 Días", "5 Días", "6 Días", "7 Días", "10 Días", "15 Días", "1 Mes", "2 Meses", "3 Meses", "6 Meses", "1 Año" }));
        cantidadMembresia.setSelectedIndex(9);
        cantidadMembresia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cantidadMembresiaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(realizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelar))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombreCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cantidadMembresia, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fechaVence, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelar, realizar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(fechaVence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cantidadMembresia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelar)
                    .addComponent(realizar)
                    .addComponent(total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cancelar, realizar});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        int res = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea cancelar el cobro?", null, JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }//GEN-LAST:event_cancelarActionPerformed

    private void realizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_realizarActionPerformed
        FormaDePagoGui formaDePagoGui = new FormaDePagoGui((Frame) this.getParent(), true,false);
        formaDePagoGui.setLocationRelativeTo(null);
        formaDePagoGui.setVisible(true);
        int opcionFormaPago = formaDePagoGui.getReturnStatus();
        BigDecimal totalB = new BigDecimal(0);
        int rows = tablaDefault.getRowCount();
        LinkedList listaran = new LinkedList();
        for (int i = 0; i < rows; i++) {
            if (tablaActividades.getValueAt(i, 2) != null) {
                if ((boolean) tablaActividades.getValueAt(i, 2) == true) {
                    Arancel a = Arancel.first("nombre = ?", tablaActividades.getValueAt(i, 0));
                    listaran.add(a);
                    BigDecimal precio = (BigDecimal) tablaActividades.getValueAt(i, 1);
                    totalB = totalB.add(BigDecimal.valueOf(precio.doubleValue()));
                }
            }
        }
        int i = cantidadMembresia.getSelectedIndex();
        switch (i) {
            case 0:
                totalB = totalB.divide(new BigDecimal(30)).setScale(2, RoundingMode.CEILING);
                break;
            case 1:
                totalB = totalB.divide(new BigDecimal(30)).multiply(new BigDecimal(2)).setScale(2, RoundingMode.CEILING);
                break;
            case 2:
                totalB = totalB.divide(new BigDecimal(30)).multiply(new BigDecimal(3)).setScale(2, RoundingMode.CEILING);
                break;
            case 3:
                totalB = totalB.divide(new BigDecimal(30)).multiply(new BigDecimal(4)).setScale(2, RoundingMode.CEILING);
                break;
            case 4:
                totalB = totalB.divide(new BigDecimal(30)).multiply(new BigDecimal(5)).setScale(2, RoundingMode.CEILING);
                break;
            case 5:
                totalB = totalB.divide(new BigDecimal(30)).multiply(new BigDecimal(6)).setScale(2, RoundingMode.CEILING);
                break;
            case 6:
                totalB = totalB.divide(new BigDecimal(30)).multiply(new BigDecimal(7)).setScale(2, RoundingMode.CEILING);
                break;
            case 7:
                totalB = totalB.divide(new BigDecimal(3)).setScale(2, RoundingMode.CEILING);
                break;
            case 8:
                totalB = totalB.divide(new BigDecimal(2)).setScale(2, RoundingMode.CEILING);
                break;
            case 9:
                totalB = totalB.setScale(2, RoundingMode.CEILING);

                break;
            case 10:
                totalB = totalB.multiply(new BigDecimal(2)).setScale(2, RoundingMode.CEILING);

                break;
            case 11:
                totalB = totalB.multiply(new BigDecimal(3)).setScale(2, RoundingMode.CEILING);

                break;
            case 12:
                totalB = totalB.multiply(new BigDecimal(6)).setScale(2, RoundingMode.CEILING);

                break;
            case 13:
                totalB = totalB.multiply(new BigDecimal(12)).setScale(2, RoundingMode.CEILING);

                break;

        }
        ABMSocios abmsocio = new ABMSocios();
        switch (opcionFormaPago) {
            case (FormaDePagoGui.RET_EFECTIVO):
                CalcularVueltoGui calVueltoGui = new CalcularVueltoGui(null, true, totalB.setScale(2, RoundingMode.CEILING));
                calVueltoGui.setLocationRelativeTo(null);
                calVueltoGui.setVisible(true);
                int retCalVuelto = calVueltoGui.getReturnStatus();
                if (retCalVuelto == CalcularVueltoGui.RET_OK) {
                    Base.openTransaction();
                    Pago.createIt("ID_DATOS_PERS", socio.getString("ID_DATOS_PERS"), "FECHA", dateToMySQLDate(fecha.getDate(), false), "MONTO", totalB.setScale(2, RoundingMode.CEILING),"MODO","MEMBRESIA PAGADA EN EFECTIVO");

                    socio.set("FECHA_ULT_PAGO", dateToMySQLDate(fecha.getDate(), false));
                    socio.set("FECHA_PROX_PAGO", dateToMySQLDate(fechaVence.getDate(), false));

                    socio.setBoolean("ACTIVO", true);
                    socio.saveIt();
                    Base.commitTransaction();
                    if (abmsocio.modificar(socio, listaran, socio.getString("DNI"))) {
                        JOptionPane.showMessageDialog(this, "Socio dado de alta correctamente!");
                        this.dispose();
                    }
                }
                break;
            case FormaDePagoGui.RET_CUENTA:
                CobroACuentaGui cobroAcuentaGui= new CobroACuentaGui(null, true,socio.getBigDecimal("cuenta_corriente").setScale(2, RoundingMode.CEILING) ,totalB.setScale(2, RoundingMode.CEILING));
                 cobroAcuentaGui.setLocationRelativeTo(null);
                cobroAcuentaGui.setVisible(true);
                retCalVuelto = cobroAcuentaGui.getReturnStatus();
                if (retCalVuelto == CobroACuentaGui.RET_OK) {
                    Base.openTransaction();
                    Pago.createIt("ID_DATOS_PERS", socio.getString("ID_DATOS_PERS"), "FECHA", dateToMySQLDate(fecha.getDate(), false), "MONTO", totalB.setScale(2, RoundingMode.CEILING),"MODO", "MEMBRESIA AGREGADA A CUENTA");

                    socio.set("FECHA_ULT_PAGO", dateToMySQLDate(fecha.getDate(), false));
                    socio.set("FECHA_PROX_PAGO", dateToMySQLDate(fechaVence.getDate(), false));

                    socio.setBoolean("ACTIVO", true);
                    socio.setBigDecimal("cuenta_corriente", socio.getBigDecimal("cuenta_corriente").subtract(totalB).setScale(2, RoundingMode.CEILING));
                    socio.saveIt();
                    Base.commitTransaction();
                    if (abmsocio.modificar(socio, listaran, socio.getString("DNI"))) {
                        JOptionPane.showMessageDialog(this, "Socio dado de alta correctamente!");
                        this.dispose();
                    }
                }
                    break;
        }
    }//GEN-LAST:event_realizarActionPerformed

    private void tablaActividadesPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablaActividadesPropertyChange


    }//GEN-LAST:event_tablaActividadesPropertyChange

    private void cantidadMembresiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cantidadMembresiaActionPerformed
        calcularTotal();
        calcularFechavence();
    }//GEN-LAST:event_cantidadMembresiaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelar;
    private javax.swing.JComboBox cantidadMembresia;
    private com.toedter.calendar.JDateChooser fecha;
    private com.toedter.calendar.JDateChooser fechaVence;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAsistio;
    private javax.swing.JLabel labelVencio;
    private javax.swing.JLabel nombreCliente;
    private javax.swing.JLabel primAsis;
    private javax.swing.JButton realizar;
    private javax.swing.JTable tablaActividades;
    private javax.swing.JLabel total;
    // End of variables declaration//GEN-END:variables
}
