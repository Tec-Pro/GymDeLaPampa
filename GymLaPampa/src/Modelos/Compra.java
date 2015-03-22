/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.math.BigDecimal;
import java.util.LinkedList;
import org.javalite.activejdbc.Model;
import net.sf.jasperreports.engine.util.Pair;

/**
 *
 * @author jacinto
 */
public class Compra extends Model {

    public Compra() {
        this.productos = null;
        this.preciosFinales = null;
    }
    //Lista de pares <Producto,cantidad>
    private LinkedList<Pair> productos;
    private LinkedList<BigDecimal> preciosFinales;

    public LinkedList<Pair> getProductos() {
        return productos;
    }

    public void setProductos(LinkedList<Pair> productos) {
        this.productos = productos;
    }

    public LinkedList<BigDecimal> getPreciosFinales() {
        return preciosFinales;
    }

    public void setPreciosFinales(LinkedList<BigDecimal> preciosFinales) {
        this.preciosFinales = preciosFinales;
    }
}
