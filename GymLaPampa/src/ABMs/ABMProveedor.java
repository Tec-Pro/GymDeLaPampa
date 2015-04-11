/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import Modelos.Categoria;
import Modelos.Dato;
import Modelos.Proveedor;
import org.javalite.activejdbc.Base;

/**
 *
 * @author nico
 */
public class ABMProveedor {

    public boolean findProveedor(Proveedor p) {
        return (Proveedor.first("nombre = ?", p.get("nombre")) != null);
    }

    public Proveedor getProveedor(Proveedor c) {
        return Proveedor.first("nombre = ?", c.get("nombre"));
    }

    public boolean alta(Proveedor p) {
        boolean ret = false;
        if (!findProveedor(p)) {
            Base.openTransaction();
            Proveedor nuevo = Proveedor.create("nombre", p.get("nombre"), "telefono", p.get("telefono"), "cuenta_corriente", p.get("cuenta_corriente"), "email", p.get("email"), "cuit", p.get("cuit"), "direccion", p.get("direccion"), "celular", p.get("celular"), "forma_de_pago", p.get("forma_de_pago"));
            ret = nuevo.saveIt();
                        
            Dato.createIt("descripcion", nuevo.getId() +"-"+nuevo.getString("nombre"),"categoria_id", Categoria.findFirst("nombre =?", "COMPRAS").getId(),"ingreso_egreso","egreso");

            Base.commitTransaction();
        }
        return ret;
    }

    public boolean baja(Proveedor p) {
        boolean ret = false;
        Proveedor viejo = getProveedor(p);
        if (viejo != null) {
            Base.openTransaction();
            ret = viejo.delete();
            Base.commitTransaction();
        }
        return ret;
    }

    public boolean modificar(Proveedor p) {
        boolean ret = false;
        Proveedor viejo = Proveedor.findById(p.getId());
        if (viejo != null) {
            Base.openTransaction();
            Dato datoViejo= Dato.findFirst("descripcion =? ", viejo.getId() +"-"+viejo.getString("nombre"));
            ret = viejo.set("nombre", p.get("nombre"), "telefono", p.get("telefono"), "cuenta_corriente", p.get("cuenta_corriente"), "email", p.get("email"), "cuit", p.get("cuit"), "direccion", p.get("direccion"), "celular", p.get("celular"), "forma_de_pago", p.get("forma_de_pago")).saveIt();
                        viejo.saveIt();
            datoViejo.setString("descripcion", viejo.getId() +"-"+viejo.getString("nombre"));
            datoViejo.saveIt();
            Base.commitTransaction();
        }
        return ret;
    }
    /* public boolean registrarPago(Proveedor prov, Pago pago){
     boolean ret= false;
     Proveedor viejo = Proveedor.findFirst("nombre = ?", prov.get("nombre"));
     if(viejo!=null){
     Base.openTransaction();
     viejo.set("cuenta_corriente", viejo.getFloat("cuenta_corriente")+ pago.getFloat("monto"));
     viejo.add(pago);
     Base.commitTransaction();
     ret=true;
     }
     return ret;
     }*/
}
