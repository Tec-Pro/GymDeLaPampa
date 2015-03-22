/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;


import ABMs.ABMProveedor;
import Interfaces.ArticulosGUI;
import Interfaces.ArticulosProvee;
import Interfaces.CompraGui;
import Interfaces.ComprasRealizadas;
import Interfaces.PrincipalGui;
import Interfaces.ProveedorGui;
import Interfaces.RealizarPagoGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Modelos.Pagoproveedor;
import Modelos.Proveedor;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;

/**
 *
 * @author nico
 */
public class ControladorProveedor implements ActionListener {

    private ProveedorGui proveedorGui;
    private DefaultTableModel tablaProvDefault;
    private DefaultTableModel tablaPagosDefault;
    private java.util.List<Proveedor> listProveedores;
    private java.util.List<Pagoproveedor> listPagos;
    
    private JTable tablaProveedor;
    private JTable tablaPagos;
    private ABMProveedor abmProveedor;
    private Boolean isNuevo;
    private Boolean editandoInfo;
    private Proveedor proveedor;
    private RealizarPagoGui realizarPagoGui;
    private PrincipalGui aplicacionGui;
    private ArticulosGUI articuloGui;
    private CompraGui compraGui;
    private ControladorJReport reporteProveedor;
    

    public ControladorProveedor(ProveedorGui proveedorGui, PrincipalGui aplicacionGui, ArticulosGUI articuloGui, CompraGui compraGui) throws JRException, ClassNotFoundException, SQLException {
        this.aplicacionGui = aplicacionGui;
        isNuevo = true;
        editandoInfo = false;
        this.proveedorGui = proveedorGui;
        this.compraGui= compraGui;
        this.proveedorGui.setActionListener(this);
        tablaProvDefault = proveedorGui.getProveedores();
        tablaPagosDefault = proveedorGui.getPagosDefault();
        tablaProveedor = proveedorGui.getTablaProveedores();
        tablaPagos = proveedorGui.getPagosRealizados();
        listProveedores = new LinkedList();
        listPagos = new LinkedList();
        abmProveedor = new ABMProveedor();
        proveedor = new Proveedor();
        this.articuloGui= articuloGui;
        //reporteProveedor = new ControladorJReport("listadoProveedores.jasper");
        Base.openTransaction();
        listProveedores = Proveedor.findAll();
        Base.commitTransaction();
        actualizarLista();
        proveedorGui.getBusqueda().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaKeyReleased(evt);
            }
        });
        proveedorGui.getTablaProveedores().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        proveedorGui.getPagosRealizados().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPagosClicked(evt);
            }
        });
  
  

    }

       public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/GYM", "root", "root");
        }
    }
    public void cargarTodos() {
        abrirBase();
        Base.openTransaction();
        listProveedores = Proveedor.findAll();
                Base.commitTransaction();

        if (!listProveedores.isEmpty() ) {
        realizarBusqueda();
        }
    }

   

    

    private void tablaPagosClicked(java.awt.event.MouseEvent evt) {
        proveedorGui.getBorrarPago().setEnabled(true);

    }

    public void busquedaKeyReleased(java.awt.event.KeyEvent evt) {
        System.out.println("apreté el caracter: " + evt.getKeyChar());
        realizarBusqueda();
    }

    private void realizarBusqueda() {
        Base.openTransaction();
        listProveedores = Proveedor.where("id like ? or nombre like ? or cuit like ? ", "%" + proveedorGui.getBusqueda().getText() + "%", "%" + proveedorGui.getBusqueda().getText() + "%", "%" + proveedorGui.getBusqueda().getText() + "%");
                Base.commitTransaction();

        actualizarLista();

    }

    public void tablaMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            editandoInfo = false;
            proveedorGui.habilitarCampos(false);
            proveedorGui.getBorrar().setEnabled(true);
            proveedorGui.getModificar().setEnabled(true);
            proveedorGui.getGuardar().setEnabled(false);
            proveedorGui.getNuevo().setEnabled(true);
            proveedorGui.getRealizarPago().setEnabled(true);
            proveedorGui.getBorrarPago().setEnabled(false);
            proveedorGui.getArticulosProvee().setEnabled(true);
            proveedorGui.getComprasRealizadas().setEnabled(true);
            System.out.println("hice doble click en un proveedor");
            proveedorGui.limpiarCampos();
            Base.openTransaction();
            proveedor = Proveedor.findFirst("id = ?", tablaProveedor.getValueAt(tablaProveedor.getSelectedRow(), 0));
                        Base.commitTransaction();

            proveedorGui.CargarCampos(proveedor);
            
            
            cargarPagos();
        }
    }

    private void actualizarLista() {
        
        tablaProvDefault.setRowCount(0);
        Iterator<Proveedor> it = listProveedores.iterator();
        while (it.hasNext()) {
            Proveedor prov = it.next();
            Object row[] = new String[6];
            row[0] = prov.getString("id");
            row[1] = prov.getString("nombre");
            row[2] = prov.getString("cuit");
            tablaProvDefault.addRow(row);
            
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == proveedorGui.getNuevo()) {
            System.out.println("Boton nuevo pulsado");
            proveedorGui.limpiarCampos();
            proveedorGui.habilitarCampos(true);
            proveedorGui.getArticulosProvee().setEnabled(false);
            proveedorGui.getComprasRealizadas().setEnabled(false);
            isNuevo = true;
            editandoInfo = true;
            proveedorGui.getBorrar().setEnabled(false);
            proveedorGui.getRealizarPago().setEnabled(false);
            proveedorGui.getModificar().setEnabled(false);
            proveedorGui.getGuardar().setEnabled(true);
            proveedorGui.getCuenta().setText("0.00");
        }
        if (e.getSource() == proveedorGui.getGuardar() && editandoInfo && isNuevo) {
            System.out.println("Boton guardar pulsado");
            Base.openTransaction();
            if (cargarDatosProv(proveedor)) {                
                if (abmProveedor.alta(proveedor)) {
                    proveedorGui.habilitarCampos(false);
                    proveedorGui.limpiarCampos();
                    editandoInfo = false;
                    JOptionPane.showMessageDialog(proveedorGui, "¡Proveedor guardado exitosamente!");
                    proveedorGui.getNuevo().setEnabled(true);
                    proveedorGui.getGuardar().setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(proveedorGui, "Ocurrió un error, revise los datos", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                
                realizarBusqueda();
            }
            Base.commitTransaction();
        }
        if (e.getSource() == proveedorGui.getBorrar()) {
            System.out.println("Boton borrar pulsado");
            Base.openTransaction();
            proveedorGui.habilitarCampos(false);
            if (proveedor.getId() != null && !editandoInfo) {
                Integer resp = JOptionPane.showConfirmDialog(proveedorGui, "¿Desea borrar el proveedor " + proveedorGui.getNombre().getText(), "Confirmar borrado", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    
                    Boolean seBorro = abmProveedor.baja(proveedor);
                    
                    if (seBorro) {
                        JOptionPane.showMessageDialog(proveedorGui, "¡Proveedor borrado exitosamente!");
                        proveedorGui.limpiarCampos();
                        realizarBusqueda();
                        proveedorGui.getBorrar().setEnabled(false);
                        proveedorGui.getModificar().setEnabled(false);
                        proveedorGui.getRealizarPago().setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(proveedorGui, "Ocurrió un error, no se borró el proveedor", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(proveedorGui, "No se seleccionó un proveedor");
            }
            Base.commitTransaction();
        }
        if (e.getSource() == proveedorGui.getModificar()) {
            System.out.println("Boton modificar pulsado");
            proveedorGui.habilitarCampos(true);
            editandoInfo = true;
            isNuevo = false;
            proveedorGui.getBorrar().setEnabled(false);
            proveedorGui.getGuardar().setEnabled(true);
            proveedorGui.getModificar().setEnabled(false);
            proveedorGui.getRealizarPago().setEnabled(false);
        }

        if (e.getSource() == proveedorGui.getGuardar() && editandoInfo && !isNuevo) {
            System.out.println("Boton guardar pulsado");
            Base.openTransaction();
            if (cargarDatosProv(proveedor)) {                
                if (abmProveedor.modificar(proveedor)) {
                    proveedorGui.habilitarCampos(false);
                    proveedorGui.limpiarCampos();
                    editandoInfo = false;
                    JOptionPane.showMessageDialog(proveedorGui, "¡Proveedor modificado exitosamente!");
                    proveedorGui.getNuevo().setEnabled(true);
                    proveedorGui.getGuardar().setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(proveedorGui, "Ocurrió un error,revise los datos", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                
                realizarBusqueda();
            }
            Base.commitTransaction();
        }
        if (e.getSource() == proveedorGui.getRealizarPago()) {
            System.out.println("realizar pago pulsado");
            realizarPagoGui = new RealizarPagoGui(aplicacionGui, true, proveedor);
            realizarPagoGui.setLocationRelativeTo(proveedorGui);
            realizarPagoGui.setVisible(true);            
            proveedor= abmProveedor.getProveedor(proveedor);
            proveedorGui.CargarCampos(proveedor);              
            cargarPagos();            
        }
        if (e.getSource() == proveedorGui.getBorrarPago()) {
            System.out.println("Borrar pago pulsado");
            Base.openTransaction();
            Integer resp = JOptionPane.showConfirmDialog(proveedorGui, "¿Desea borrar el pago seleccionado? ", "Confirmar borrado", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                String fecha = tablaPagos.getValueAt(tablaPagos.getSelectedRow(), 0).toString(); //Se le pasa la fecha a la que queremos darle formato
                String dia = fecha.substring(0, 2);
                String mes = fecha.substring(3, 5);
                String anio = fecha.substring(6, 10);
                String fechaSql = anio + mes + dia;
                Base.openTransaction();
                Pagoproveedor.findFirst("fecha = ? and monto = ? and proveedor_id = ?", fechaSql, tablaPagos.getValueAt(tablaPagos.getSelectedRow(), 1), proveedorGui.getId().getText()).delete();
                Base.commitTransaction();
                cargarPagos();

            }
            Base.commitTransaction();
        }

//        if (e.getSource() == proveedorGui.getExportar()) {
//            try {
//                reporteProveedor.mostrarReporte();
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(AplicacionGui.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (SQLException ex) {
//                Logger.getLogger(AplicacionGui.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (JRException ex) {
//                Logger.getLogger(AplicacionGui.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
        if (e.getSource() == proveedorGui.getModCuenta()) {
            if (proveedorGui.getModCuenta().isSelected()) {
                proveedorGui.getCuenta().setEnabled(true);
            } else {
                proveedorGui.getCuenta().setEnabled(false);
                proveedorGui.getCuenta().setText(String.valueOf(proveedor.getBigDecimal("cuenta_corriente").setScale(2, RoundingMode.CEILING)));

            }
        }
        if(e.getSource()==proveedorGui.getComprasRealizadas()){
            ComprasRealizadas comprasRealizadas= new ComprasRealizadas(aplicacionGui, true, proveedor,compraGui);
            comprasRealizadas.setVisible(true);
            proveedor= abmProveedor.getProveedor(proveedor);
            proveedorGui.CargarCampos(proveedor);
        }
                if(e.getSource()==proveedorGui.getArticulosProvee()){
            ArticulosProvee articulosProvee= new ArticulosProvee(aplicacionGui, true,proveedor, articuloGui);
            articulosProvee.setVisible(true);

        }
 
    }



 

    private boolean cargarDatosProv(Proveedor prov) {
        boolean ret = true;
        try {
            String nombre = TratamientoString.eliminarTildes(proveedorGui.getNombre().getText());
            System.out.println(nombre);
            prov.set("nombre", nombre);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(proveedorGui, "Error en el nombre", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String telefono = TratamientoString.eliminarTildes(proveedorGui.getTelefono().getText());
            prov.set("telefono", telefono);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(proveedorGui, "Error en el telefono", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String cuenta = TratamientoString.eliminarTildes(proveedorGui.getCuenta().getText());
            BigDecimal cuentaBig = BigDecimal.valueOf(Double.valueOf(cuenta));
            prov.set("cuenta_corriente",cuentaBig.setScale(2, RoundingMode.CEILING));
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(proveedorGui, "Error en la cuenta ", "Error!", JOptionPane.ERROR_MESSAGE);
        }
         try {
            String cuit = TratamientoString.eliminarTildes(proveedorGui.getCuit().getText());
            System.out.println(cuit);
            prov.set("cuit", cuit);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(proveedorGui, "Error en el cuit", "Error!", JOptionPane.ERROR_MESSAGE);
        }
          try {
            String direccion = TratamientoString.eliminarTildes(proveedorGui.getDireccion().getText());
            System.out.println(direccion);
            prov.set("direccion", direccion);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(proveedorGui, "Error en la dirección", "Error!", JOptionPane.ERROR_MESSAGE);
        }
           try {
            String celular = TratamientoString.eliminarTildes(proveedorGui.getCelular().getText());
            System.out.println(celular);
            prov.set("celular", celular);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(proveedorGui, "Error en el celular", "Error!", JOptionPane.ERROR_MESSAGE);
        }
  try {
            String email = TratamientoString.eliminarTildes(proveedorGui.getEmail().getText());
            System.out.println(email);
            prov.set("email", email);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(proveedorGui, "Error en el email", "Error!", JOptionPane.ERROR_MESSAGE);
        }
             try {
            String formaPago = TratamientoString.eliminarTildes(proveedorGui.getFormaPago().getText());
            System.out.println(formaPago);
            prov.set("forma_de_pago", formaPago);
        } catch (ClassCastException e) {
            ret = false;
            JOptionPane.showMessageDialog(proveedorGui, "Error en la forma de pago", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        return ret;
    }

    private void cargarPagos() {
        
        listPagos = proveedor.getAll(Pagoproveedor.class);
        tablaPagosDefault.setRowCount(0);
        Iterator<Pagoproveedor> it = listPagos.iterator();
        while (it.hasNext()) {
            Pagoproveedor pago = it.next();
            Object row[] = new String[3];
            Date sqlFecha = pago.getDate("fecha");
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            row[0] = sdf.format(sqlFecha);
            row[1] = pago.getBigDecimal("monto").setScale(2, RoundingMode.CEILING).toString();
            row[2]= pago.getString("descripcion");
            tablaPagosDefault.addRow(row);
        }
                    

    }

    
}
