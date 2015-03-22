/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.LinkedList;
import Modelos.Articulo;
import Modelos.ArticulosCompras;
import Modelos.Compra;
import net.sf.jasperreports.engine.util.Pair;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author jacinto
 */
public class ABMCompra {

    public static int idCompraAlta;//chanchada que no se debe hacer nunca

    public ABMCompra() {
    }

    public boolean alta(Compra c) {
        boolean resultOp = true;
        if (c == null) {
            resultOp = false;
        } else {
            Base.openTransaction();
            Integer idProveedor = (Integer) c.get("proveedor_id");
            Compra compra = Compra.create("monto", c.get("monto"), "proveedor_id", idProveedor, "fecha", c.get("fecha"), "pago", c.get("pago"));
            resultOp = resultOp && compra.saveIt();//guardo la venta
            int idCompra = compra.getInteger("id");
            idCompraAlta = idCompra;//Agregada para solucionar el problema
            resultOp = resultOp && cargarProductosComprass(idCompra, c.getProductos());//guardo los productos vendidos
            resultOp = resultOp && actualizarStock(c.getProductos());//actualizo el stock de productos vendidos
            Base.commitTransaction();
        }
        return resultOp;
    }

    //FUNCIONA CORRECTAMENTE
    //Carga los productos y cantidades en la tabla productos_comprados
    private boolean cargarProductosComprass(int idCompra, LinkedList<Pair> productos) {
        boolean resultOp = true;
        Iterator itr = productos.iterator();
        Articulo prod;
        Pair par;
        BigDecimal cant;
        while (itr.hasNext()) {
            par = (Pair) itr.next(); //saco el par de la lista
            prod = (Articulo) par.first(); //saco el producto del par
            cant = ((BigDecimal) par.second()).setScale(2, RoundingMode.CEILING);;//saco la cantidad del par
            ArticulosCompras prodComprado = ArticulosCompras.create("compra_id", idCompra, "articulo_id", prod.get("id"), "cantidad", cant, "precio_final", prod.get("precio_compra"));
            resultOp = resultOp && prodComprado.saveIt();

        }
        return resultOp;
    }

   
    //FUNCIONA CORRECTAMENTE
    public boolean bajaConDevolucion(Compra c) {
        boolean resultOp = true;
        Integer idCompra = c.getInteger("id");//saco el idCompra
        Compra compra = Compra.findById(idCompra);//la busco en BD y la traigo
        Base.openTransaction();
        if (compra == null) {
            resultOp = false;
        } else {
            LinkedList<Pair> viejosProductos = buscarProductosComprass(idCompra); //saco los viejos productos de la venta
            resultOp = resultOp && devolucionStock(viejosProductos);//actualizo el stock por haber sacado los viejos productos
            ArticulosCompras.delete("compra_id = ?", idCompra);//elimino todos los productosvendidos
            resultOp = resultOp && compra.delete(); //elimino la venta
        }
        Base.commitTransaction();
        return resultOp;
    }

    //FUNCIONA CORRECTAMENTE
    /*Retorna una lista de pares producto-cantidad de una compra(la busca en
     * productos_comprados
     */
    private LinkedList<Pair> buscarProductosComprass(int idCompra) {
        BigDecimal cant;
        ArticulosCompras prodComprado;
        Articulo prod;
        LinkedList<Pair> listaDePares = new LinkedList<Pair>();
        LazyList<ArticulosCompras> productos = ArticulosCompras.find("compra_id = ?", idCompra);
        Iterator itr = productos.iterator();
        while (itr.hasNext()) {
            prodComprado = (ArticulosCompras) itr.next(); //saco el modelo de la lista
            prod = Articulo.findFirst("id = ?", prodComprado.getInteger("id"));//saco el producto del modelo
            cant = ((BigDecimal) prodComprado.get("cantidad")).setScale(2, RoundingMode.CEILING);;//saco la cantidad del modelo
            Pair par = new Pair(prod, cant); //creo el par producto-cantidad
            listaDePares.add(par);//agrego el par a la lista de pares
        }
        return listaDePares;
    }
   
        //FUNCIONA CORRECTAMENTE
    //Actualiza el stock de los productos comprados (incrementandolo)
    private boolean actualizarStock(LinkedList<Pair> productos) {
        boolean resultOp = true;
        Iterator itr = productos.iterator();
        Articulo prodViejo;
        Pair par;
        BigDecimal cant;
        ABMArticulos abmProd = new ABMArticulos();
        while (itr.hasNext()) {
            par = (Pair) itr.next(); //saco el par de la lista
            prodViejo = (Articulo) par.first(); //saco el producto del par
            cant = (BigDecimal) par.second();//saco la cantidad del par
            cant = prodViejo.getBigDecimal("stock").add(cant);//asigno a cant el valor nuevo del stock
            resultOp = resultOp && prodViejo.setInteger("stock", cant).saveIt();//actualizo el stock del producto
//            Proveedor.findById(prodViejo.get("proveedor_id")).add(prodViejo);//creo la relacion
        }
        return resultOp;
    }

    //FUNCIONA CORRECTAMENTE
    //actualiza el stock (decrementandolo) cuando se elimina una venta o se modifica los productos comprados
    private boolean devolucionStock(LinkedList<Pair> productos) {
        boolean resultOp = true;
        Iterator itr = productos.iterator();
        Articulo prodViejo;
        Pair par;
        BigDecimal cant;
        while (itr.hasNext()) {
            par = null;
            par = (Pair) itr.next(); //saco el par de la lista
            prodViejo = (Articulo) par.first(); //saco el producto del par
            cant = (BigDecimal) par.second();//saco la cantidad del par
            cant = prodViejo.getBigDecimal("stock").add(cant);//devuelvo el stock anterior a la venta del producto
            resultOp = resultOp && prodViejo.setInteger("stock", cant).saveIt();//actualizo el stock del producto
           // Proveedor.findById(prodViejo.get("proveedor_id")).add(prodViejo);//creo la relacion
        }
        return resultOp;
    }
    
}
