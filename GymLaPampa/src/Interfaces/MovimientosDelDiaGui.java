/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Modelos.Pago;
import Modelos.Pagoproveedor;
import com.toedter.calendar.JDateChooser;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JLabel;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author NicoOrcasitas
 */
public class MovimientosDelDiaGui extends javax.swing.JInternalFrame {

    /**
     * Creates new form MovimientosDelDiaGui
     */
    public MovimientosDelDiaGui() {
        initComponents();
            fecha.getDateEditor().setEnabled(false);
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            date.setDate(cal.getActualMinimum(Calendar.DAY_OF_MONTH));;
            Date dateH = cal.getTime();
            dateH.setDate(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            fecha.setDate(dateH);
            cargarMovimientos();
            
    }
        private void cargarMovimientos(){
            abrirBase();
            LazyList<Pago> pagos=Pago.where(" fecha = ? ", dateToMySQLDate(fecha.getDate(), false));
            BigDecimal totalPagoEfectivo= new BigDecimal(BigInteger.ZERO);
            BigDecimal totalPagoTarjeta= new BigDecimal(BigInteger.ZERO);
            BigDecimal totalPagoAnotado= new BigDecimal(BigInteger.ZERO);

            BigDecimal totalPagoEfectivoAnterior= new BigDecimal(BigInteger.ZERO);
            BigDecimal totalPagoTarjetaAnterior= new BigDecimal(BigInteger.ZERO);
            Iterator<Pago> it= pagos.iterator();
            String modo;
            Pago auxPago= new Pago();
            while(it.hasNext()){
                auxPago= it.next();
                modo= auxPago.getString("modo");
                switch (modo){
                    case "EFECTIVO":
                                    totalPagoEfectivo=totalPagoEfectivo.add(auxPago.getBigDecimal("monto"));
                                    break;
                
                    case "TARJETA":
                                    totalPagoTarjeta=totalPagoTarjeta.add(auxPago.getBigDecimal("monto"));
                                    break;
                    case "EFECTIVO ANTERIOR":
                                    totalPagoEfectivoAnterior=totalPagoEfectivoAnterior.add(auxPago.getBigDecimal("monto"));
                                    break; 
                    case "TARJETA ANTERIOR":
                                    totalPagoTarjetaAnterior=totalPagoTarjetaAnterior.add(auxPago.getBigDecimal("monto"));
                                    break;                        
                    case "CUENTA":
                                    totalPagoAnotado=totalPagoAnotado.add(auxPago.getBigDecimal("monto"));
                                    break;                      
                }                
            }
            LazyList<Pagoproveedor> pagoProveedores=  Pagoproveedor.where("fecha = ?  ",  dateToMySQLDate(fecha.getDate(), false));
            BigDecimal totalPagoProveedor= new BigDecimal(BigInteger.ZERO);
            Iterator<Pagoproveedor> itPagoProv= pagoProveedores.iterator();
            Pagoproveedor auxPagoproveedor= new Pagoproveedor();
            while(itPagoProv.hasNext()){
                auxPagoproveedor= itPagoProv.next();
                totalPagoProveedor= totalPagoProveedor.add(auxPagoproveedor.getBigDecimal("monto"));
                
            }
            lblTarjetaDia.setText(totalPagoTarjeta.toString());
            lblEfecDia.setText(totalPagoEfectivo.toString());
            lblEfecAnterior.setText(totalPagoEfectivoAnterior.toPlainString());
            lblTarjetaAnterior.setText(totalPagoTarjetaAnterior.toString());
            lblTotalEfec.setText(totalPagoEfectivo.add(totalPagoEfectivoAnterior).toString());
            lblTotalTarjeta.setText(totalPagoTarjeta.add(totalPagoTarjetaAnterior).toString());
            lblTotalSinAnotado.setText(totalPagoEfectivo.add(totalPagoEfectivoAnterior).add((totalPagoTarjeta.add(totalPagoTarjetaAnterior))).toString());
            lblAnotadoHoy.setText(totalPagoAnotado.toString());
            lblAnotadoTotal.setText(totalPagoAnotado.toString());
            lblEfecDiaProv.setText(totalPagoProveedor.negate().toString());
            lblTotalEfecProv.setText(totalPagoProveedor.negate().toString());
            lblTotal.setText(totalPagoProveedor.negate().add(totalPagoEfectivo.add(totalPagoEfectivoAnterior).add((totalPagoTarjeta.add(totalPagoTarjetaAnterior))).add(totalPagoAnotado)).toString());
            lblTotalGanancias.setText(totalPagoEfectivo.add(totalPagoEfectivoAnterior).add((totalPagoTarjeta.add(totalPagoTarjetaAnterior))).add(totalPagoAnotado).toString());
            lblTotalConProvsSinAnota.setText(totalPagoEfectivo.add(totalPagoEfectivoAnterior).add((totalPagoTarjeta.add(totalPagoTarjetaAnterior))).add(totalPagoProveedor.negate()).toString());
        }
    
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
        public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gestionGym", "root", "root");
        }
    }



    public JDateChooser getHasta() {
        return fecha;
    }



    public JLabel getLblEfecAnterior() {
        return lblEfecAnterior;
    }

    public JLabel getLblEfecDia() {
        return lblEfecDia;
    }

    public JLabel getLblTarjetaDia() {
        return lblTarjetaDia;
    }

    public JLabel getLblTotal() {
        return lblTotalSinAnotado;
    }



    public JLabel getLblTotalEfec() {
        return lblTotalEfec;
    }

    public JLabel getLblTotalTarjeta() {
        return lblTotalTarjeta;
    }
    

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fecha = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblEfecDia = new javax.swing.JLabel();
        lblEfecAnterior = new javax.swing.JLabel();
        lblTotalEfec = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblTarjetaDia = new javax.swing.JLabel();
        lblTarjetaAnterior = new javax.swing.JLabel();
        lblTotalTarjeta = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        lblTotalSinAnotado = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        lblAnotadoHoy = new javax.swing.JLabel();
        lblAnotadoTotal = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblTotalGanancias = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblEfecDiaProv = new javax.swing.JLabel();
        lblTotalEfecProv = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lblTotalConProvsSinAnota = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setTitle("Ganancias ");

        fecha.setToolTipText("Ver movimientos hasta la fecha");

        jLabel2.setFont(new java.awt.Font("Century Schoolbook L", 1, 14)); // NOI18N
        jLabel2.setText("Hasta");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("DEL DÍA");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("PAGOS ANTERIORES");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("TOTAL");

        lblEfecDia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEfecDia.setForeground(new java.awt.Color(255, 102, 0));
        lblEfecDia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEfecDia.setText("0.00");

        lblEfecAnterior.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEfecAnterior.setForeground(new java.awt.Color(255, 102, 0));
        lblEfecAnterior.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEfecAnterior.setText("0.00");

        lblTotalEfec.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotalEfec.setForeground(new java.awt.Color(255, 102, 0));
        lblTotalEfec.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalEfec.setText("0.00");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("TARJETA");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("EFECTIVO");

        lblTarjetaDia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTarjetaDia.setForeground(new java.awt.Color(255, 102, 0));
        lblTarjetaDia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTarjetaDia.setText("0.00");

        lblTarjetaAnterior.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTarjetaAnterior.setForeground(new java.awt.Color(255, 102, 0));
        lblTarjetaAnterior.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTarjetaAnterior.setText("0.00");

        lblTotalTarjeta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotalTarjeta.setForeground(new java.awt.Color(255, 102, 0));
        lblTotalTarjeta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalTarjeta.setText("0.00");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jLabel18.setText("(SIN CONTAR LO QUE SE ANOTO)");

        lblTotalSinAnotado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotalSinAnotado.setForeground(new java.awt.Color(255, 102, 0));
        lblTotalSinAnotado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalSinAnotado.setText("0.00");

        jButton1.setText("Cargar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setText("SE ANOTÓ");

        lblAnotadoHoy.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblAnotadoHoy.setForeground(new java.awt.Color(255, 102, 0));
        lblAnotadoHoy.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnotadoHoy.setText("0.00");

        lblAnotadoTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblAnotadoTotal.setForeground(new java.awt.Color(255, 102, 0));
        lblAnotadoTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnotadoTotal.setText("0.00");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setText("TOTAL GANANCIAS");

        lblTotalGanancias.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotalGanancias.setForeground(new java.awt.Color(255, 102, 0));
        lblTotalGanancias.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalGanancias.setText("0.00");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setText("TOTAL GANANCIAS");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("PAGOS A PROVS.");

        lblEfecDiaProv.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEfecDiaProv.setForeground(new java.awt.Color(255, 102, 0));
        lblEfecDiaProv.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEfecDiaProv.setText("0.00");

        lblTotalEfecProv.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotalEfecProv.setForeground(new java.awt.Color(255, 102, 0));
        lblTotalEfecProv.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalEfecProv.setText("0.00");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel21.setText("TOTAL EN LA CAJA ");

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 102, 0));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal.setText("0.00");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel23.setText("TOTAL ");

        lblTotalConProvsSinAnota.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotalConProvsSinAnota.setForeground(new java.awt.Color(255, 102, 0));
        lblTotalConProvsSinAnota.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalConProvsSinAnota.setText("0.00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblEfecDiaProv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAnotadoHoy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTarjetaDia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(lblEfecDia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotalEfecProv, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTarjetaAnterior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTotalTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                                    .addComponent(lblEfecAnterior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblTotalEfec, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblAnotadoTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(jSeparator1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTotalSinAnotado, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTotalGanancias, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel21))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTotalConProvsSinAnota, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEfecDia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEfecAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalEfec, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTarjetaDia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTarjetaAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lblAnotadoHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAnotadoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEfecDiaProv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalEfecProv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(23, 23, 23)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalGanancias, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lblTotalSinAnotado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(lblTotalConProvsSinAnota, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       cargarMovimientos();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAnotadoHoy;
    private javax.swing.JLabel lblAnotadoTotal;
    private javax.swing.JLabel lblEfecAnterior;
    private javax.swing.JLabel lblEfecDia;
    private javax.swing.JLabel lblEfecDiaProv;
    private javax.swing.JLabel lblTarjetaAnterior;
    private javax.swing.JLabel lblTarjetaDia;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalConProvsSinAnota;
    private javax.swing.JLabel lblTotalEfec;
    private javax.swing.JLabel lblTotalEfecProv;
    private javax.swing.JLabel lblTotalGanancias;
    private javax.swing.JLabel lblTotalSinAnotado;
    private javax.swing.JLabel lblTotalTarjeta;
    // End of variables declaration//GEN-END:variables
}
