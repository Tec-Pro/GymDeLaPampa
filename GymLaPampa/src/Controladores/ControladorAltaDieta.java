/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ABMs.ABMAlimento;
import ABMs.ABMAlimentosDieta;
import Interfaces.DietaGui;
import Modelos.Alimento;
import Modelos.AlimentosDietas;
import Modelos.Articulo;
import Modelos.Dieta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.util.Pair;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author NicoOrcasitas
 */
public class ControladorAltaDieta implements ActionListener, CellEditorListener {

    private DietaGui dietaGui;
    private ABMAlimentosDieta abmAlimentosDieta;
    private ABMAlimento abmAlimentos;
    private Alimento alimento;
    private JTable tblAlimentos;
    private DefaultTableModel tblDefaultAlimentos;
    private JTable tblAlimentosDieta;
    private DefaultTableModel tblDefaultAlimentosDieta;
    private DefaultTableModel tblDefaultDietas;
    private JTable tblDietas;
    private Integer idModificar;

    private float aguaG=0;
    private float proteinasG=0;
    private float hcG=0;
    private float lipidosG=0;
    private float aguaL=0;
    private float proteinasKcal=0;
    private float hcKcal=0;
    private float lipidosKcal=0;
    private float calorias=0;

    private boolean isNuevo;

    public ControladorAltaDieta(DietaGui dietaGui) {
        this.dietaGui = dietaGui;
        abmAlimentosDieta = new ABMAlimentosDieta();
        abmAlimentos = new ABMAlimento();
        tblAlimentos = dietaGui.getTblAlimentos();
        tblDietas = dietaGui.getTblDietas();
        tblDefaultAlimentos = dietaGui.getTblDefaultAlimento();
        tblDefaultDietas = dietaGui.getTblDefaultDietas();
        tblDefaultAlimentosDieta = dietaGui.getTblDefaultAlimentoDieta();
        tblAlimentosDieta = dietaGui.getTblAlimentosDieta();
        tblAlimentos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });

        tblDietas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClickedDietas(evt);
            }
        });

        this.dietaGui.getTxtBusqueda().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busqueda();
            }
        });
        this.dietaGui.setActionListener(this);
        this.dietaGui.getTxtBusquedaDietas().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaDietas();
            }
        });
    }

    public void busqueda() {
        Base.openTransaction();
        cargarEnTabla(Alimento.where("id like ? or nombre like ?  ", "%" + dietaGui.getTxtBusqueda().getText() + "%", "%" + dietaGui.getTxtBusqueda().getText() + "%"));
        Base.commitTransaction();
    }

    public void busquedaDietas() {
        Base.openTransaction();
        cargarEnTablaDieta(Dieta.where("descripcion like ? or nombre like ?  ", "%" + dietaGui.getTxtBusqueda().getText() + "%", "%" + dietaGui.getTxtBusqueda().getText() + "%"));
        Base.commitTransaction();
    }

    private void tablaMouseClickedDietas(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            int row = tblDietas.getSelectedRow();
            Dieta dieta = abmAlimentosDieta.getDieta((Integer) tblDietas.getValueAt(row, 2));
            cargarEnTablaDietaAliemtos(dieta.getAll(AlimentosDietas.class));
            dietaGui.setBotonesClickTabla();
        }
    }

    private void cargarEnTabla(LazyList<Alimento> lista) {
        tblDefaultAlimentos.setRowCount(0);
        if (!lista.isEmpty()) {
            Iterator<Alimento> it = lista.iterator();
            while (it.hasNext()) {
                Alimento a = it.next();
                Object row[] = new Object[15];
                row[0] = a.getInteger("id");
                row[1] = a.getString("nombre");
                row[2] = a.getFloat("pc");
                row[3] = a.getFloat("agua");
                row[4] = a.getFloat("prot");
                row[5] = a.getFloat("hc");
                row[6] = a.getFloat("grasa");
                row[7] = a.getFloat("colesterol");
                row[8] = a.getFloat("fibra");
                row[9] = a.getFloat("sodio");
                row[10] = a.getFloat("potasio");
                row[11] = a.getFloat("magnesio");
                row[12] = a.getFloat("calcio");
                row[13] = a.getFloat("fosforo");
                row[14] = a.getFloat("hierro");
                tblDefaultAlimentos.addRow(row);
            }
        }
    }

    private void cargarEnTablaDietaAliemtos(Alimento a) {
        Object row[] = new Object[9];
        row[0] = "8:00";
        row[1] = a.getString("nombre");
        row[2] = a.getFloat("agua");
        row[3] = a.getFloat("prot");
        row[4] = a.getFloat("hc");
        row[5] = a.getFloat("grasa");
        row[6] = a.getFloat("prot") * 4 + a.getFloat("hc") * 4 + a.getFloat("grasa") * 9;
        row[7] = "1";
        row[8] = a.getInteger("id");
        tblDefaultAlimentosDieta.addRow(row);
        this.setCellEditor();
        aguaG+=a.getFloat("agua");
    proteinasG+=a.getFloat("prot");
    hcG+=a.getFloat("hc");
    lipidosG+=a.getFloat("grasa");
    aguaL+=0;
    hcKcal+=hcG*4;
    lipidosKcal+=lipidosG*9;
    proteinasKcal+=proteinasG*4;
    calorias+=hcKcal+lipidosKcal+proteinasKcal;
    dietaGui.getLblAguaL().setText(String.valueOf(aguaL));
        dietaGui.getLblAguag().setText(String.valueOf(aguaG));
            dietaGui.getLblCalorias().setText(String.valueOf(calorias));
    dietaGui.getLblHCG().setText(String.valueOf(hcG));
        dietaGui.getLblHCK().setText(String.valueOf(hcKcal));
    dietaGui.getLblLipidoG().setText(String.valueOf(lipidosG));
    dietaGui.getLblLipidoK().setText(String.valueOf(lipidosKcal));
    dietaGui.getLblProtG().setText(String.valueOf(proteinasG));
    dietaGui.getLblProtK().setText(String.valueOf(proteinasKcal));



    }

    public void setCellEditor() {
        for (int i = 0; i < tblAlimentosDieta.getRowCount(); i++) {
            tblAlimentosDieta.getCellEditor(i, 7).addCellEditorListener(this);
        }
    }

    private void cargarEnTablaDietaAliemtos(LazyList<AlimentosDietas> lista) {
        tblDefaultAlimentosDieta.setRowCount(0);
                     aguaG=0;
    proteinasG=0;
    hcG=0;
    lipidosG=0;
    aguaL=0;
    hcKcal=0;
    lipidosKcal=0;
    proteinasKcal=0;
    calorias=0;
        Iterator<AlimentosDietas> it = lista.iterator();
        while (it.hasNext()) {

            AlimentosDietas a = it.next();
            Alimento alim = abmAlimentos.getAlimento(a.getInteger("alimento_id"));
            Object row[] = new Object[9];
            row[0] = a.getString("hora");
            row[1] = alim.getString("nombre");
            row[2] = alim.getFloat("agua");
            row[3] = alim.getFloat("prot");
            row[4] = alim.getFloat("hc");
            row[5] = alim.getFloat("grasa");
            row[6] = alim.getFloat("prot") * 4 + alim.getFloat("hc") * 4 + alim.getFloat("grasa") * 9;
            row[7] = a.getFloat("porcion").toString();
            row[8] = alim.getInteger("id");
            tblDefaultAlimentosDieta.addRow(row);
             aguaG+=alim.getFloat("agua");
    proteinasG+=alim.getFloat("prot");
    hcG+=alim.getFloat("hc");
    lipidosG+=alim.getFloat("grasa");
    aguaL+=0;
    hcKcal+=hcG*4*a.getFloat("porcion");
    lipidosKcal+=lipidosG*9*a.getFloat("porcion");
    proteinasKcal+=proteinasG*4*a.getFloat("porcion");
    calorias+=hcKcal+lipidosKcal+proteinasKcal;
            
        }
    dietaGui.getLblAguaL().setText(String.valueOf(aguaL));
        dietaGui.getLblAguag().setText(String.valueOf(aguaG));
            dietaGui.getLblCalorias().setText(String.valueOf(calorias));
    dietaGui.getLblHCG().setText(String.valueOf(hcG));
        dietaGui.getLblHCK().setText(String.valueOf(hcKcal));
    dietaGui.getLblLipidoG().setText(String.valueOf(lipidosG));
    dietaGui.getLblLipidoK().setText(String.valueOf(lipidosKcal));
    dietaGui.getLblProtG().setText(String.valueOf(proteinasG));
    dietaGui.getLblProtK().setText(String.valueOf(proteinasKcal));
    }

    private void cargarEnTablaDieta(LazyList<Dieta> lista) {
        tblDefaultDietas.setRowCount(0);
        Iterator<Dieta> it = lista.iterator();
        while (it.hasNext()) {
            Dieta a = it.next();
            Object row[] = new Object[3];
            row[0] = a.getString("nombre");
            row[1] = a.getString("descripcion");
            row[2] = a.getInteger("id");
            tblDefaultDietas.addRow(row);

        }
    }

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            int row = tblAlimentos.getSelectedRow();
            alimento = abmAlimentos.getAlimento((Integer) tblAlimentos.getValueAt(row, 0));
            cargarEnTablaDietaAliemtos(alimento);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dietaGui.getBotNuevo()) {
            isNuevo = true;
            dietaGui.setBotonesNuevo();
        }
        if (e.getSource() == dietaGui.getBotGuardar()) {
            if (isNuevo) {
                LinkedList<Pair<Integer, Pair<String, Float>>> alimentoHora = new LinkedList<Pair<Integer, Pair<String, Float>>>();
                for (int i = 0; i < tblDefaultAlimentosDieta.getRowCount(); i++) {

                    alimentoHora.add(new Pair<Integer, Pair<String, Float>>((Integer) tblAlimentosDieta.getValueAt(i, 8), new Pair<String, Float>((String) tblAlimentosDieta.getValueAt(i, 0), Float.valueOf((String) tblAlimentosDieta.getValueAt(i, 7)))));

                }

                if (!alimentoHora.isEmpty() && !dietaGui.getTxtNombre().getText().isEmpty()) {
                    boolean aux = abmAlimentosDieta.Alta(dietaGui.getTxtNombre().getText(), dietaGui.getTxtDescripcion().getText(), alimentoHora);
                    if (aux) {
                        JOptionPane.showMessageDialog(dietaGui, "dieta guardada exitosamente!");
                        dietaGui.setBotonesInicial();
                        busquedaDietas();
                    } else {
                        JOptionPane.showMessageDialog(dietaGui, "Ocurrio un error!");
                    }
                }
            } else {
                LinkedList<Pair<Integer, Pair<String, Float>>> alimentoHora = new LinkedList<Pair<Integer, Pair<String, Float>>>();
                for (int i = 0; i < tblDefaultAlimentosDieta.getRowCount(); i++) {
                    alimentoHora.add(new Pair<Integer, Pair<String, Float>>((Integer) tblAlimentosDieta.getValueAt(i, 8), new Pair<String, Float>((String) tblAlimentosDieta.getValueAt(i, 0), (Float) tblAlimentosDieta.getValueAt(i, 7))));
                }
                boolean res = abmAlimentosDieta.Modificar(idModificar, dietaGui.getTxtNombre().getText(), dietaGui.getTxtDescripcion().getText(), alimentoHora);
                if (res) {
                    JOptionPane.showMessageDialog(dietaGui, "dieta guardada exitosamente!");
                    dietaGui.setBotonesInicial();
                    busquedaDietas();

                } else {
                    JOptionPane.showMessageDialog(dietaGui, "Ocurrio un error!");
                }
            }
        }

    }

    private String replace(String s) {
        return s.replace(",", ".");
    }

    @Override
    public void editingStopped(ChangeEvent e) {
        for (int i = 0; i < tblAlimentosDieta.getRowCount(); i++) {
            if (tblAlimentosDieta.getValueAt(i, 0) != null) {

            }
        }
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
