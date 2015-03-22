/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import Modelos.Articulo;
import Modelos.ArticulosVentas;
import Modelos.Venta;
import Utiles.Triple;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author alan
 */
public class ABMVentas {
    private int idVenta;
    
    public int getIdVenta(){
        return idVenta;
    }
    
    public boolean Alta(Venta v){
        boolean result = true;
        abrirBase();
        Base.openTransaction();
        Venta venta = Venta.create("socio_id", v.get("socio_id"), "fecha", v.get("fecha"), "monto", v.get("monto"), "forma_pago", v.get("forma_pago"));
        if(venta.saveIt()){
            idVenta = venta.getInteger("id");
            Base.commitTransaction();
            Base.close();
            result = result && cargarArticulosVentas(v.getArticulos());
            result = result && actualizarStock(v.getArticulos());
           // result = result && crearCobros(v);
            return result;
        }
        Base.commitTransaction();
        Base.close();
        return false;
    }
    
    public boolean Modificar(Venta v){
        abrirBase();
        Base.openTransaction();
        if(v.saveIt()){
            Base.commitTransaction();
            Base.close();
            return true;
        }
        Base.commitTransaction();
        Base.close();
        return false;
    }
    
    public boolean Eliminar(Venta v){
        abrirBase();
        Base.openTransaction();
        int id = v.getInteger("id");
        if(v.delete()){
            eliminarArticulosVentas(id);
            //Cobro.delete("venta_id = ?", id);
            Base.commitTransaction();
            Base.close();
            return true;
        }
        Base.commitTransaction();
        Base.close();
        return false;
    }
    
    public void eliminarArticulosVentas(int ventaId){
        LazyList<ArticulosVentas> lista = ArticulosVentas.where("venta_id = ?", ventaId);
        for(ArticulosVentas av : lista){
            Articulo a = Articulo.first("id = ?", av.get("articulo_id"));
            if(a!= null){
                int nuevoStock = a.getInteger("stock") + av.getInteger("cantidad");
                a.setInteger("stock", nuevoStock);
                a.saveIt();
            }
        }
        ArticulosVentas.delete("venta_id = ?", ventaId);
    }
    
    public boolean cargarArticulosVentas(LinkedList<Triple> listaArticulos){
        abrirBase();
        Base.openTransaction();
        boolean result = true;
        for(Triple t: listaArticulos){
            ArticulosVentas articulosVentas = ArticulosVentas.create("venta_id", idVenta, "articulo_id", t.getIdArticulo(), "cantidad", t.getCantidad(), "precio_final", t.getPrecioFinal());
            result = result && articulosVentas.saveIt();
        }
        Base.commitTransaction();
        Base.close();
        return result;
    }
    
    public boolean actualizarStock(LinkedList<Triple> listaArticulos){
        abrirBase();
        Base.openTransaction();
        boolean result = true;
        BigDecimal newStock;
        for(Triple t: listaArticulos){
            Articulo a = Articulo.first("id = ?", t.getIdArticulo());
            if(a != null){
                System.out.println(a.get("id"));
                newStock = a.getBigDecimal("stock").subtract(t.getCantidad());
                a.set("stock",newStock);
                result = result && a.saveIt();
                System.out.println(newStock);
            }else{
                Base.commitTransaction();
                Base.close();
                return false;
            } 
        }
        Base.commitTransaction();
        Base.close();
        return result;
    }
    
    
    
    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gym", "root", "root");
        }
    }
}
