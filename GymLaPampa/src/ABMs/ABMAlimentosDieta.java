/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import Modelos.Alimento;
import Modelos.AlimentosDietas;
import Modelos.Dieta;
import java.util.Iterator;
import java.util.LinkedList;
import net.sf.jasperreports.engine.util.Pair;
import org.javalite.activejdbc.Base;

/**
 *
 * @author A
 */
public class ABMAlimentosDieta {
    
    Object idDieta;
    
    public boolean Alta(String nombre,String descripcion, LinkedList<Object[]> listaAlimentos){
        boolean result = false;
        abrirBase();
        Base.openTransaction();
        Dieta dieta = Dieta.createIt("nombre", nombre,"descripcion",descripcion);
        if(dieta!=null){
            idDieta = dieta.getId();
            for(Object[] t: listaAlimentos){
                AlimentosDietas alimentosDieta= AlimentosDietas.createIt("alimento_id",t[0], "dieta_id",idDieta,"hora",t[1],"porcion",t[2],"dia",t[3]);
        }
            result= true;
        }
        Base.commitTransaction();
        return result;
    }
    
    public boolean Modificar(Integer id,String nombre,String descripcion, LinkedList<Object[]> listaAlimentos){
        boolean result = false;
        abrirBase();
        Base.openTransaction();
        Dieta dieta = Dieta.findById(id);
        if(dieta!=null){
            idDieta = dieta.getId();
            Iterator<AlimentosDietas> it=dieta.getAll(AlimentosDietas.class).iterator();
            while(it.hasNext()){
                it.next().delete();
            }
            dieta.set("nombre",nombre);
            dieta.set("descripcion",descripcion);
            for(Object[] t: listaAlimentos){
                AlimentosDietas alimentosDieta= AlimentosDietas.createIt("alimento_id",t[0], "dieta_id",idDieta,"hora",t[1],"porcion",t[2],"dia",t[3]);
        }
            result=dieta.saveIt();
        }
        Base.commitTransaction();
        return result;
    }
    
    public boolean Eliminar(Integer id){
        abrirBase();
        Base.openTransaction();
        Dieta d= Dieta.findById(id);
        boolean result = true;
        result = d.delete() ;
        AlimentosDietas.delete("dieta_id = ?", id);
        return result;
    }
   
        public Dieta getDieta(Integer id){
        abrirBase();
        Base.openTransaction();
        Dieta dieta = Dieta.findById(id);
        
        return dieta;
    }

   
    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gym", "root", "root");
        }
    }
}
