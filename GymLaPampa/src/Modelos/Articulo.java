/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import org.javalite.activejdbc.Model;

/**
 *
 * @author alan
 */
public class Articulo extends Model{
            private String nombreProveedor;


    public String getNombreProv() {
        return nombreProveedor;
    }


    public void setNombreProv(String nombreProv) {
        this.nombreProveedor =nombreProv;
    }
    
}
