/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Modelos.Arancel;
import Modelos.Socio;
import Modelos.Socioarancel;
import java.util.Iterator;
import java.util.LinkedList;
import net.sf.jasperreports.engine.util.Pair;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author NicoOrcasitas
 */
public class EstadisticaConocioGui extends javax.swing.JDialog {

    /**
     * Creates new form EstadisticaConocioGui
     */
    public EstadisticaConocioGui(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        abrirBase();
        LazyList<Socio> socios = Socio.findAll();
        Iterator<Socio> it = socios.iterator();
        Socio s;
        String nosConocio;
        Boolean activo;
        Integer[] nosConocioSuma = {0, 0, 0, 0, 0, 0};
        LinkedList<Pair<Integer,Integer>> actividadesRealizan= new LinkedList<>();
        LazyList<Arancel> aranceles= Arancel.findAll();
        Iterator<Arancel> itArancel= aranceles.iterator();
        while(itArancel.hasNext()){
            Pair<Integer,Integer> par= new Pair<>(itArancel.next().getInteger("id"),0);
            actividadesRealizan.add(par);
        }
        while (it.hasNext()) {
            s = it.next();
            nosConocio = s.getString("NOSCONOCIOPOR");
            activo = s.getBoolean("ACTIVO");
            switch (nosConocio) {
                case "NO ESPECIFICA":
                    nosConocioSuma[0]++;
                    break;
                case "VIO EL GIMNASIO":
                    nosConocioSuma[1]++;
                    break;
                case "POR RADIO":
                    nosConocioSuma[2]++;
                    break;
                case "POR TV":
                    nosConocioSuma[3]++;
                    break;
                case "REDES SOCIALES":
                    nosConocioSuma[4]++;
                    break;
                case "UN AMIGO/A":
                    nosConocioSuma[5]++;
                    break;
            }
            if(activo){
                LazyList<Socioarancel> arancelesSocio= Socioarancel.where("id_socio = ?", s.getId());
                Iterator<Socioarancel> itAr= arancelesSocio.iterator();
                while(itAr.hasNext()){
                    Socioarancel ar= itAr.next();
                    boolean sume=false;
                    int i= 0;
                    while(i<actividadesRealizan.size()&&  !sume){
                        if(actividadesRealizan.get(i).first()==ar.getInteger("id_arancel")){
                            Pair<Integer,Integer> aux=new Pair<>(actividadesRealizan.get(i).first(),actividadesRealizan.get(i).second()+1);
                            actividadesRealizan.remove(i);
                            actividadesRealizan.add(i, aux);
                            sume=true;
                        }
                        i++;
                    }
                }
            }
        }
        DefaultCategoryDataset datasetConocio = new DefaultCategoryDataset();
        datasetConocio.setValue(nosConocioSuma[0], "nos conocieron", "NO ESPECIFICA");
        datasetConocio.setValue(nosConocioSuma[1], "nos conocieron", "VIO EL GIMNASIO");
        datasetConocio.setValue(nosConocioSuma[2], "nos conocieron", "POR RADIO");
        datasetConocio.setValue(nosConocioSuma[3], "nos conocieron", "POR TV");
        datasetConocio.setValue(nosConocioSuma[4], "nos conocieron", "REDES SOCIALES");
        datasetConocio.setValue(nosConocioSuma[5], "nos conocieron", "UN AMIGO/A");
        JFreeChart chart = ChartFactory.createBarChart3D("Estadisticas por donde nos conocieron", "donde","cantidad de personas", datasetConocio, PlotOrientation.VERTICAL, true,true, false);
        // Creación del panel con el gráfico
        ChartPanel panelGrafico = new ChartPanel(chart);
        pnlConocio.add(panelGrafico);
        
                DefaultCategoryDataset datasetAct = new DefaultCategoryDataset();
                for (int i=0; i<actividadesRealizan.size();i++){
                    String nombreAct= Arancel.findById(actividadesRealizan.get(i).first()).getString("nombre");
                    datasetAct.setValue(actividadesRealizan.get(i).second(), "actividad", nombreAct);

                }
                        JFreeChart chartAct = ChartFactory.createBarChart3D("Estadisticas que actividad realizan", "actividad","cantidad de personas", datasetAct , PlotOrientation.VERTICAL, true,true, false);
        // Creación del panel con el gráfico
        ChartPanel panelGraficoAc = new ChartPanel(chartAct);
        pnlAct.add(panelGraficoAc);

    }

           public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gym", "root", "root");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlConocio = new javax.swing.JPanel();
        pnlAct = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Estadísticas sobre socios");
        setPreferredSize(new java.awt.Dimension(841, 496));
        getContentPane().setLayout(new java.awt.GridLayout(0, 1));

        pnlConocio.setBorder(javax.swing.BorderFactory.createTitledBorder("Estadisticas donde nos conocio"));
        pnlConocio.setLayout(new java.awt.GridLayout());
        getContentPane().add(pnlConocio);

        pnlAct.setBorder(javax.swing.BorderFactory.createTitledBorder("Estadisticas que actividades realizan"));
        pnlAct.setLayout(new java.awt.GridLayout());
        getContentPane().add(pnlAct);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EstadisticaConocioGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EstadisticaConocioGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EstadisticaConocioGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EstadisticaConocioGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EstadisticaConocioGui dialog = new EstadisticaConocioGui(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnlAct;
    private javax.swing.JPanel pnlConocio;
    // End of variables declaration//GEN-END:variables
}
