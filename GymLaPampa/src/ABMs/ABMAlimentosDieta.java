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
                int numDia=0;
                switch ((String)t[3]){
                    case "LUNES":
                        numDia=1;
                        break;
                    case "MARTES":
                        numDia=2;
                        break;
                    case "MIERCOLES":
                        numDia=3;
                        break;  
                    case "JUEVES":
                        numDia=4;
                        break;
                    case "VIERNES":
                        numDia=5;
                        break;
                    case "SABADO":
                        numDia=6;
                        break;
                    case "DOMINGO":
                        numDia=7;
                        break;
                }
                String hora=(String) t[1];
                if(hora.length()<5)
                    hora="0"+hora;
                AlimentosDietas alimentosDieta= AlimentosDietas.createIt("alimento_id",t[0], "dieta_id",idDieta,"hora",hora,"porcion",t[2],"dia",t[3],"numero_dia",numDia);
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
                                int numDia=0;
                switch ((String)t[3]){
                    case "LUNES":
                        numDia=1;
                        break;
                    case "MARTES":
                        numDia=2;
                        break;
                    case "MIERCOLES":
                        numDia=3;
                        break;  
                    case "JUEVES":
                        numDia=4;
                        break;
                    case "VIERNES":
                        numDia=5;
                        break;
                    case "SABADO":
                        numDia=6;
                        break;
                    case "DOMINGO":
                        numDia=7;
                        break;
                }
                                String hora=(String) t[1];
       
                if(hora.length()<5)
                    hora="0"+hora;
                AlimentosDietas alimentosDieta= AlimentosDietas.createIt("alimento_id",t[0], "dieta_id",idDieta,"hora",hora,"porcion",t[2],"dia",t[3],"numero_dia",numDia);
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
