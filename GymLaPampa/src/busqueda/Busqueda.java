/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import Modelos.Articulo;
import Modelos.ArticulosCompras;
//import modelos.ArticulosVentas;
import Modelos.Socio;
//import modelos.ClientesArticulos;
import Modelos.Compra;
import Modelos.Proveedor;
//import modelos.Venta;
import org.javalite.activejdbc.Base;

/**
 *
 * @author jacinto
 */
public class Busqueda {

    /*
     * No hace falta distinguir entre mayúsculas y minúsculas.
     */
    /**
     *
     * @param id
     * @return Cliente asociado a esa id.
     */
//    public Cliente buscarCliente(Object id) {
//        Base.openTransaction();
//        Cliente result = Cliente.findById(id);
//        Base.commitTransaction();
//        return result;
//    }

//    public List<Cliente> buscarCliente(String nombre) {
//        Base.openTransaction();
//        List<Cliente> result;
//        result = Cliente.where("nombre like ?", "%" + nombre + "%");
//        Base.commitTransaction();
//        return result;
//    }

    /**
     * @param nombre,
     * @param apellido e
     * @param id del cliente. Filtra los que tienen nombre a los pasados y
     * @return lista filtrada de clientes.
     */
//    public List<Cliente> filtroCliente(String nombre, String codigo) {
//        List<Cliente> result;
//        Base.openTransaction();
//        result = Cliente.where("nombre like ? and id like ?", "%" + nombre + "%", codigo + "%");
//        Base.commitTransaction();
//        return result;
//    }
 

    
    /**
     * @param idproveedor,
     * @param fecha desde (Pasada como string),
     * @param fecha hasta (Pasada como String). Filtra las compras a los
     * proveedores que empiezan con idproveedores entre las fechas pasadas.
     * @return lista filtrada de compras.
     */
    public List<Compra> filtroCompra(String idproveedor, String desde, String hasta) {
        List<Compra> result;
        Base.openTransaction();
        result = Compra.where("proveedor_id like ? and fecha between ? and ?", idproveedor + "%", desde, hasta);
        Base.commitTransaction();
        return result;
        
    }

    /**
     * @param cuil,
     * @param nombre,
     * @param apellido. Filtra los proveedores que tienen lo pasado en nombre.
     * @return lista filtrada de proveedores.
     */
    public List<Proveedor> filtroProveedor(String nombre, String codigo) {
        Base.openTransaction();
        List<Proveedor> result;
        result = Proveedor.where("nombre like ? and id like ?", "%" + nombre + "%", "%" + codigo + "%");
        Base.commitTransaction();
        return result;
    }

    /**
     * @param idcompra,
     * @param idproducto. Filtra los productos comprados en idcompra o las
     * compras que contienen idproducto o ambas.
     * @returns lista filtrada de productos comprados.
     */
    public List<ArticulosCompras> filtroComprados(String idcompra, String idproducto) {
        Base.openTransaction();
        List<ArticulosCompras> result;
        result = ArticulosCompras.where("compra_id like ? and producto_id like ?", idcompra + "%", idproducto + "%");
        Base.commitTransaction();
        return result;
    }

    /**
     * @param idventa,
     * @param idproducto. Filtra los productos vendidos en idventa o las ventas
     * que contienen idproducto o ambas.
     * @returns lista filtrada de productos vendidos.
     */
//    public List<ArticulosVentas> filtroVendidos(String idventa, String idproducto) {
//        List<ArticulosVentas> result;
//        Base.openTransaction();
//        result = ArticulosVentas.where("venta_id like ? and producto_id like ?", idventa + "%", idproducto + "%");
//        Base.commitTransaction();
//        return result;
//    }
    
  


 

 

  
//
//    public List<Articulo> provee(String cuil) {
//        Base.openTransaction();
//        Proveedor p = Proveedor.findFirst("nombre= ?", cuil);
//        List<Articulo> ret=Articulo.where("es_articulo=1 and proveedor_id = ?", p.getId());
//        Base.commitTransaction();
//        return ret;
//    }

    /**
     * @param Devuelve todos los proveedores de la base de datos.
     * @returns lista de todos los proveedores.
     */
    public List<Proveedor> proveedores() {
        Base.openTransaction();
        List<Proveedor> ret=Proveedor.findAll();
        Base.commitTransaction();
        return ret;
    }


 
       
}

