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
    
    public boolean Alta(String nombre,String descripcion, LinkedList<Pair<Integer,Pair<String,Float>>> listaAlimentos){
        boolean result = false;
        abrirBase();
        Base.openTransaction();
        Dieta dieta = Dieta.createIt("nombre", nombre,"descripcion",descripcion);
        if(dieta!=null){
            idDieta = dieta.getId();
            for(Pair<Integer,Pair<String,Float>> t: listaAlimentos){
                AlimentosDietas alimentosDieta= AlimentosDietas.createIt("alimento_id",t.first(), "dieta_id",idDieta,"hora",t.second().first(),"porcion",t.second().second());
        }
            result= true;
        }
        Base.commitTransaction();
        return result;
    }
    
    public boolean Modificar(Integer id,String nombre,String descripcion, LinkedList<Pair<Integer,Pair<String,Float>>> listaAlimentos){
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
            for(Pair<Integer,Pair<String,Float>> t: listaAlimentos){
                AlimentosDietas alimentosDieta= AlimentosDietas.createIt("alimento_id",t.first(), "dieta_id",idDieta,"hora",t.second().first(),"porcion",t.second().second());
        }
            result=dieta.saveIt();
        }
        Base.commitTransaction();
        return result;
    }
    
    public boolean Eliminar(Dieta v){
        abrirBase();
        Base.openTransaction();
        int id = v.getInteger("id");
        boolean result = true;
        result = v.delete() && result;
        AlimentosDietas.delete("id_rutina", id);
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
