/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ABMs.ABMVentas;
import Interfaces.PrincipalGui;
import Interfaces.CargarVentaGUI;
import Modelos.Articulo;
import Modelos.Socio;
import Modelos.Venta;
import Utiles.Triple;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author alan
 */
public class ControladorCargarVentaGUI implements ActionListener, CellEditorListener {

    CargarVentaGUI cargarVentaGUI;
    ABMVentas abmVentas;
    PrincipalGui aplicacionGUI;

    public ControladorCargarVentaGUI(CargarVentaGUI cv, PrincipalGui ap) throws JRException, ClassNotFoundException, SQLException {
        aplicacionGUI = ap;
        cargarVentaGUI = cv;
        cargarVentaGUI.setActionListener(this);
        abmVentas = new ABMVentas();

        cargarVentaGUI.getBusquedaNombreTxt().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaKeyReleased(evt);
            }
        });

        cargarVentaGUI.getTablaSocios().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaSocioMouseClicked(evt);
            }
        });

        cargarVentaGUI.getTablaArticulos().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaArticulosMouseClicked(evt);
            }
        });
    }

    private void busquedaKeyReleased(KeyEvent evt) {
        /*   abrirBase();
         List<Socio> listaSocios = busqueda.BuscarSocios(cargarVentaGUI.getBusquedaNombreTxt().getText(), "NOMBRE");
         cargarVentaGUI.getTablaSociosDefault().setRowCount(0);
         for (Socio socio : listaSocios) {
         Object row[] = new String[3];
         row[0] = socio.getString("id");
         row[1] =  socio.getString("nombre");
         row[2] = socio.getString("direccion") + " - " + socio.getString("ciudad");
         cargarVentaGUI.getTablaSociosDefault().addRow(row);
         }
          
         */
    }

    private void tablaSocioMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            int r = cargarVentaGUI.getTablaSocios().getSelectedRow();
            cargarVentaGUI.getNombreSocioTxt().setText(String.valueOf(cargarVentaGUI.getTablaSociosDefault().getValueAt(r, 1)));
            cargarVentaGUI.getIdSocioTxt().setText(String.valueOf(cargarVentaGUI.getTablaSociosDefault().getValueAt(r, 0)));
        }
    }

    private void tablaArticulosMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            int r = cargarVentaGUI.getTablaArticulos().getSelectedRow();
            int lineaArticulo = articuloYaCargado(String.valueOf(cargarVentaGUI.getTablaArticulosDefault().getValueAt(r, 0)));
            // si el articulo no estaba en el carrito se agrega el articulo si no se le suma uno a la cantidad
            if (lineaArticulo == -1) {
                abrirBase();
                Articulo articulo = Articulo.first("codigo = ?", String.valueOf(cargarVentaGUI.getTablaArticulosDefault().getValueAt(r, 0)));
                Object row[] = new Object[6];
                row[0] = articulo.getString("codigo");
                row[1] = articulo.getString("articulo");
                row[2] = BigDecimal.valueOf(1.00);
                row[3] = articulo.getBigDecimal("precio").setScale(2, RoundingMode.CEILING).toString();
                row[4] = articulo.getBigDecimal("precio").setScale(2, RoundingMode.CEILING).toString();
                cargarVentaGUI.getTablaVentaDefault().addRow(row);
                 
            } else {
                // Lo que se hace dentro de este else es sumar en uno a la cantidad del articulo si ya estaba en el carrito.
                Double viejaCantidad = new Double(String.valueOf(cargarVentaGUI.getTablaVentaDefault().getValueAt(lineaArticulo, 2)));
                BigDecimal viejaCantidadBD = BigDecimal.valueOf(viejaCantidad);
                BigDecimal uno = new BigDecimal(1);
                BigDecimal nuevaCantidad = viejaCantidadBD.add(uno);
                cargarVentaGUI.getTablaVentaDefault().setValueAt(nuevaCantidad, lineaArticulo, 2);
            }
            setCellEditor();
            actualizarMonto();

        }
    }

    private int articuloYaCargado(String id) {
        for (int i = 0; i < cargarVentaGUI.getTablaVenta().getRowCount(); i++) {
            if (String.valueOf(cargarVentaGUI.getTablaVentaDefault().getValueAt(i, 0)).equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public Venta ObtenerDatosVenta() {
        abrirBase();
        LinkedList<Triple> listaArticulos = new LinkedList();
        for (int i = 0; i < cargarVentaGUI.getTablaVentaDefault().getRowCount(); i++) {
            Articulo ar = Articulo.first("codigo = ?", cargarVentaGUI.getTablaVentaDefault().getValueAt(i, 0));
            Object idArticulo = ar.get("id");
            Double doubleCantidad = Double.valueOf(String.valueOf(cargarVentaGUI.getTablaVentaDefault().getValueAt(i, 2)));
            BigDecimal cantidad = BigDecimal.valueOf(doubleCantidad);
            Double doublePrecioFinal = Double.valueOf(String.valueOf(cargarVentaGUI.getTablaVentaDefault().getValueAt(i, 3)));
            BigDecimal precioFinal = BigDecimal.valueOf(doublePrecioFinal);
            listaArticulos.add(new Triple(idArticulo, cantidad, precioFinal));
        }
        Venta v = new Venta(listaArticulos);
        v.set("socio_id", cargarVentaGUI.getIdSocioTxt().getText());
        v.set("fecha", dateToMySQLDate(cargarVentaGUI.getCalendario().getDate(), false));
        v.setBigDecimal("monto", cargarVentaGUI.getTotalTxt().getText());
        v.set("forma_pago", cargarVentaGUI.getFormaPagoBox().getSelectedItem());
         
        return v;
    }

    public boolean DatosOK() {
        if ((cargarVentaGUI.getNombreSocioTxt().getText().equals("")) || (cargarVentaGUI.getTablaVenta().getRowCount() == 0)) {
            return false;
        }
        return true;
    }

    public void actualizarMonto() {
        BigDecimal importe;
        BigDecimal total = new BigDecimal(0);
        for (int i = 0; i < cargarVentaGUI.getTablaVenta().getRowCount(); i++) {
            BigDecimal precio_unit = new BigDecimal(String.valueOf(cargarVentaGUI.getTablaVenta().getValueAt(i, 3)));
            importe = ((BigDecimal) cargarVentaGUI.getTablaVenta().getValueAt(i, 2)).multiply(precio_unit).setScale(2, RoundingMode.CEILING);
            cargarVentaGUI.getTablaVentaDefault().setValueAt(importe, i, 4);
            total = total.add((BigDecimal) cargarVentaGUI.getTablaVentaDefault().getValueAt(i, 4)).setScale(2, RoundingMode.CEILING);
        }
        cargarVentaGUI.getTotalTxt().setText(total.toString());

    }

    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gym", "root", "root");
        }
    }

    public void setCellEditor() {
        for (int i = 0; i < cargarVentaGUI.getTablaVenta().getRowCount(); i++) {
            cargarVentaGUI.getTablaVenta().getCellEditor(i, 2).addCellEditorListener(this);
            cargarVentaGUI.getTablaVenta().getCellEditor(i, 3).addCellEditorListener(this);
        }
    }
    
    public void ActualizarListaProductos(){
        cargarVentaGUI.getTablaArticulosDefault().setRowCount(0);
        abrirBase();
        LazyList<Articulo> articulos = Articulo.findAll();
        for(Articulo a: articulos){
            Object[] row = new Object[4];
            row[0] = a.getString("codigo");
            row[1] = a.getString("articulo");
            row[2] = a.getString("stock");
            row[3] = a.getBigDecimal("precio").setScale(2, RoundingMode.CEILING);
            cargarVentaGUI.getTablaArticulosDefault().addRow(row);
        }
         
    }
    
    public void ActualizarListaSocios(){
        cargarVentaGUI.getTablaSociosDefault().setRowCount(0);
        abrirBase();
        LazyList<Socio> socios = Socio.findAll();
        for(Socio a: socios){
            Object[] row = new Object[3];
            row[0] = a.getString("ID_DATOS_PERS");
            row[1] = a.getString("NOMBRE");
            row[2] = a.getString("DIR");
            cargarVentaGUI.getTablaSociosDefault().addRow(row);
        }
         
    }

    /* private void VerCargarCuotasGUI() {
     cargarCobrosGUI.getCuotasTableDefault().setRowCount(0);
     cargarCobrosGUI.getLblNombre().setText(cargarVentaGUI.getNombreSocioTxt().getText());
     cargarCobrosGUI.getLblFormaDePago().setText((String) cargarVentaGUI.getFormaPagoBox().getSelectedItem());
     cargarCobrosGUI.setVisible(true);
     }*/
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cargarVentaGUI.getQuitarBtn())) {
            if (cargarVentaGUI.getTablaVenta().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                cargarVentaGUI.getTablaVentaDefault().removeRow(cargarVentaGUI.getTablaVenta().getSelectedRow());
                actualizarMonto();
            }
        }
        if (e.getSource().equals(cargarVentaGUI.getRegistrarVentaBtn())) {
            if (DatosOK()) {
                if (abmVentas.Alta(ObtenerDatosVenta())) {
                    JOptionPane.showMessageDialog(cargarVentaGUI, "Venta registrada exitosamente!");
                    // VerCargarCuotasGUI();
                } else {
                    JOptionPane.showMessageDialog(cargarVentaGUI, "Ocurrio un error, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(cargarVentaGUI, "No se selecciono un socio o la lista de productos esta vacia.", "Atencion!", JOptionPane.WARNING_MESSAGE);
            }
        }
        ///////////Controlador CargarCobrosGUI//////////////////

        /////////////Fin Controlador CargarCobrosGUI/////////////////
    }

    private boolean FormatoOK() {
        /* try {
         Double monto = Double.valueOf(cargarCobrosGUI.getTxtMonto().getText());
         } catch (NumberFormatException | ClassCastException e) {
         JOptionPane.showMessageDialog(cargarCobrosGUI, "Error en el monto de la cuota. Solo se admiten numeros. Los decimales se escriben despues de un . (punto)", "Error de formato", JOptionPane.ERROR_MESSAGE);
         return false;
         }*/

        return true;
    }

    /*paraMostrar == true: retorna la fecha en formato dd/mm/yyyy (formato pantalla)
     * paraMostrar == false: retorna la fecha en formato yyyy/mm/dd (formato SQL)
     */
    public String dateToMySQLDate(Date fecha, boolean paraMostrar) {
        if (paraMostrar) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(fecha);
        } else {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(fecha);
        }
    }

    @Override
    public void editingStopped(ChangeEvent e) {
        actualizarMonto();
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
    }
}
