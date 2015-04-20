/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ABMs.ABMArticulos;
import Interfaces.ArticulosGUI;
import Modelos.Articulo;
import Modelos.Proveedor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author alan
 */
public class ControladorArticuloGUI implements ActionListener {

    ArticulosGUI articulosGUI;
    ABMArticulos abmArticulos;
    boolean apreteModificar = false;
    int valoresCorrectos = 1;

    public ControladorArticuloGUI(ArticulosGUI ag) {
        articulosGUI = ag;
        articulosGUI.setActionListener(this);
        abmArticulos = new ABMArticulos();
        cargarBoxProveedores();
        articulosGUI.getTablaArticulos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (articulosGUI.getTablaArticulos().getSelectedRow() != -1) {
                    tablaArticulosMouseClicked(null);
                } else {
                    articulosGUI.EstadoInicial();
                    articulosGUI.LimpiarCampos();
                }
                apreteModificar = false;
            }
        });
    }

    private void tablaArticulosMouseClicked(MouseEvent evt) {
        int r = articulosGUI.getTablaArticulos().getSelectedRow();
        CargarDatosArticuloSeleccionado(String.valueOf(articulosGUI.getTablaArticulosDefault().getValueAt(r, 0)));
        articulosGUI.EstadoArticuloSeleccionado();
    }

    /* private void BusquedaDeArticulos(){
     abrirBase();
     articulosGUI.getTablaArticulosDefault().setRowCount(0);
     int stock = (int) articulosGUI.getBusquedaStockSp().getValue();
     String vehiculo= (String) articulosGUI.getBusquedaVehiculoBox().getSelectedItem();
     String medida = articulosGUI.getBusquedaMedidaTxt().getText();
     String marca = (String) articulosGUI.getBusquedaMarcaBox().getSelectedItem();
     String tipo = (String) articulosGUI.getBusquedaArticuloBox().getSelectedItem();
     List<Articulo> result = busquedaArticulos.BuscarArticulos(vehiculo, marca, medida, tipo,stock);
     for(Articulo articulo: result){
     Object row[] = new String[6];
     row[0] = articulo.getString("id");
     row[1] = articulo.getString("marca");
     row[2] = articulo.getString("disenio");
     row[3] = articulo.getString("medida");
     row[4] = articulo.getString("stock");
     row[5] = articulo.getBigDecimal("precio_venta").setScale(2, RoundingMode.CEILING).toString();
     articulosGUI.getTablaArticulosDefault().addRow(row);
     }
      
     }*/
    //Trae de la base de datos todos los datos del articulo seleccionado en la tabla y los pone en la interfaz
    public void CargarDatosArticuloSeleccionado(String idArticulo) {
        abrirBase();
        Articulo articulo = Articulo.first("codigo = ?", idArticulo);
        if (articulo != null) {
            articulosGUI.getTxtArticulo().setText(articulo.getString("articulo"));
            articulosGUI.getTxtCodigo().setText(articulo.getString("codigo"));
            articulosGUI.getTxtPrecio().setText(articulo.getBigDecimal("precio").setScale(2, RoundingMode.CEILING).toString());
            articulosGUI.getTxtPrecioCompra().setText(articulo.getBigDecimal("precio_compra").setScale(2, RoundingMode.CEILING).toString());
            articulosGUI.getAreaDesc().setText(articulo.getString("descripcion"));
            articulosGUI.getTxtStock().setText(articulo.getInteger("stock").toString());
            Proveedor p = Proveedor.first("id = ?", articulo.get("proveedor_id"));
            if (p != null) {
                articulosGUI.getBoxProveedor().setSelectedItem(p.getString("id") + "-" + p.getString("nombre"));
            }else{
                articulosGUI.getBoxProveedor().setSelectedItem("Seleccionar");
            }
        } else {
            JOptionPane.showMessageDialog(articulosGUI, "Ocurrio un error inesperado, intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void cargarBoxProveedores() {
        abrirBase();
        articulosGUI.getBoxProveedor().removeAllItems();
        articulosGUI.getBoxProveedor().addItem("Seleccionar");
        LazyList<Proveedor> provs = Proveedor.findAll();
        for (Proveedor p : provs) {
            articulosGUI.getBoxProveedor().addItem(p.getString("id") + "-" + p.getString("nombre"));
        }
    }

    public boolean DatosObligatoriosOK() {
        if ((articulosGUI.getTxtArticulo().equals(""))
                || (articulosGUI.getTxtCodigo().equals(""))
                || (articulosGUI.getTxtPrecio().equals(""))
                || (articulosGUI.getTxtPrecioCompra().equals(""))
                || (articulosGUI.getBoxProveedor().getSelectedItem().equals("Seleccionar"))) {
            return false;
        }
        return true;
    }

    private Articulo ObtenerDatosArticulo(String id) {
        abrirBase();
        Articulo articulo;
        if (id == null) {
            articulo = new Articulo();
        } else {
            articulo = Articulo.first("codigo = ?", id);
        }
        articulo.set("articulo", articulosGUI.getTxtArticulo().getText().toUpperCase());
        articulo.set("codigo", articulosGUI.getTxtCodigo().getText());
        Double precio = Double.valueOf(articulosGUI.getTxtPrecio().getText());
        articulo.set("precio", BigDecimal.valueOf(precio).setScale(2, RoundingMode.CEILING));
        Double precioCompra = Double.valueOf(articulosGUI.getTxtPrecioCompra().getText());
        articulo.set("precio_compra", BigDecimal.valueOf(precioCompra).setScale(2, RoundingMode.CEILING));
        articulo.set("stock", articulosGUI.getTxtStock().getText());
        articulo.set("descripcion", articulosGUI.getAreaDesc().getText().toUpperCase());
        String[] idProv = String.valueOf(articulosGUI.getBoxProveedor().getSelectedItem()).split("-");
        articulo.set("proveedor_id", idProv[0]);

        return articulo;
    }

    private boolean FormatoOK() {
        try {
            Double precio = Double.valueOf(articulosGUI.getTxtPrecio().getText());
            Double precioCompra = Double.valueOf(articulosGUI.getTxtPrecioCompra().getText());
        } catch (NumberFormatException | ClassCastException e) {
            JOptionPane.showMessageDialog(articulosGUI, "Error en precio de venta o compra. Solo se admiten numeros. Los decimales se escriben despues de un . (punto)", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Integer stock = Integer.valueOf(articulosGUI.getTxtStock().getText());
        } catch (NumberFormatException | ClassCastException e) {
            JOptionPane.showMessageDialog(articulosGUI, "Error en el numero de stock. Solo se admiten numeros.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void ActualizarLista() {
        abrirBase();
        articulosGUI.getTablaArticulosDefault().setRowCount(0);
        LazyList<Articulo> listaArticulos = Articulo.findAll();
        articulosGUI.getEncontradosLbl().setText(String.valueOf(listaArticulos.size()));
        for (Articulo articulo : listaArticulos) {
            Object row[] = new String[5];
            row[0] = articulo.getString("codigo");
            row[1] = articulo.getString("articulo");
            row[2] = articulo.getString("stock");
            row[3] = articulo.getBigDecimal("precio").setScale(2, RoundingMode.CEILING).toString();
            row[4] = articulo.getBigDecimal("precio_compra").setScale(2, RoundingMode.CEILING).toString();
            articulosGUI.getTablaArticulosDefault().addRow(row);
        }

    }

    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gym", "root", "root");
        }
    }

    private boolean CodigoOK() {
        abrirBase();
        Articulo a = Articulo.first("codigo = ?", articulosGUI.getTxtCodigo().getText());

        if (a != null) {
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == articulosGUI.getNuevoBtn()) {
            if (articulosGUI.getNuevoBtn().getText().equals("Nuevo")) { //Si el nombre del btn es "nuevo" entonces solo habilito los campos
                articulosGUI.ApreteBtnNuevoModificar();
                articulosGUI.LimpiarCampos();
                cargarBoxProveedores();
            } else {
                if (articulosGUI.getNuevoBtn().getText().equals("Guardar") && !apreteModificar) {
                    if (DatosObligatoriosOK()) {
                        if (FormatoOK()) {
                            if (CodigoOK()) {
                                if (abmArticulos.Alta(ObtenerDatosArticulo(null))) {
                                    JOptionPane.showMessageDialog(articulosGUI, "Articulo registrado exitosamente!");
                                    articulosGUI.LimpiarCampos();
                                    articulosGUI.EstadoInicial();
                                    ActualizarLista();
                                } else {
                                    JOptionPane.showMessageDialog(articulosGUI, "Ocurrio un error, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(articulosGUI, "Codigo ya exitente. Ingreso otro codigo.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(articulosGUI, "Hay campos obligatorios sin completar.", "Atencion!", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    if (articulosGUI.getNuevoBtn().getText().equals("Guardar") && apreteModificar) {
                        if (DatosObligatoriosOK()) {
                            if (FormatoOK()) {
                                int r = articulosGUI.getTablaArticulos().getSelectedRow();
                                if (String.valueOf(articulosGUI.getTablaArticulosDefault().getValueAt(r, 0)).equals(articulosGUI.getTxtCodigo().getText())) {
                                    if (abmArticulos.Modificar(ObtenerDatosArticulo(String.valueOf(articulosGUI.getTablaArticulosDefault().getValueAt(r, 0))))) {
                                        JOptionPane.showMessageDialog(articulosGUI, "Articulo modificado exitosamente!");
                                        articulosGUI.EstadoLuegoDeModificar();
                                        ActualizarLista();
                                        apreteModificar = false;
                                    } else {
                                        JOptionPane.showMessageDialog(articulosGUI, "Ocurrio un error, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } else {
                                    if (CodigoOK()) {

                                        if (abmArticulos.Modificar(ObtenerDatosArticulo(String.valueOf(articulosGUI.getTablaArticulosDefault().getValueAt(r, 0))))) {
                                            JOptionPane.showMessageDialog(articulosGUI, "Articulo modificado exitosamente!");
                                            articulosGUI.EstadoLuegoDeModificar();
                                            ActualizarLista();
                                            apreteModificar = false;
                                        } else {
                                            JOptionPane.showMessageDialog(articulosGUI, "Ocurrio un error, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
                                        }

                                    } else {
                                        JOptionPane.showMessageDialog(articulosGUI, "Codigo ya exitente. Ingreso otro codigo.", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(articulosGUI, "Hay campos obligatorios sin completar.", "Atencion!", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        }
        if (e.getSource() == articulosGUI.getModificarBtn()) {
            apreteModificar = true;
            articulosGUI.ApreteBtnNuevoModificar();
        }
        if (e.getSource() == articulosGUI.getEliminarBtn()) {
            if (articulosGUI.getEliminarBtn().getText().equals("Eliminar")) {
                Integer resp = JOptionPane.showConfirmDialog(articulosGUI, "¿Desea borrar el articulo " + articulosGUI.getTxtArticulo().getText() + "?", "Confirmar borrado", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    int r = articulosGUI.getTablaArticulos().getSelectedRow();
                    if (abmArticulos.Eliminar(ObtenerDatosArticulo(String.valueOf(articulosGUI.getTablaArticulosDefault().getValueAt(r, 0))))) {
                        JOptionPane.showMessageDialog(articulosGUI, "Articulo eliminado correctamente!");
                        articulosGUI.EstadoInicial();
                        articulosGUI.LimpiarCampos();
                        ActualizarLista();
                    } else {
                        JOptionPane.showMessageDialog(articulosGUI, "Ocurrio un error, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                if (articulosGUI.getEliminarBtn().getText().equals("Cancelar")) {
                    articulosGUI.EstadoInicial();
                    articulosGUI.LimpiarCampos();
                }
            }
        }

    }

}
