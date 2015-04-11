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
import Modelos.Dieta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.CellEditor;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.util.Pair;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author NicoOrcasitas
 */
public class ControladorAltaDieta implements ActionListener, CellEditorListener,ChangeListener {

    private DietaGui dietaGui;
    private ABMAlimentosDieta abmAlimentosDieta;
    private ABMAlimento abmAlimentos;
    private Alimento alimento;
    private JTable tblAlimentos;
    private DefaultTableModel tblDefaultAlimentos;
    private JTable tblAlimentosDieta;
    private DefaultTableModel tblDefaultDietas;
    private JTable tblDietas;
    private Integer idModificar;

    private DefaultTableModel tblDefaultAlimentosDietaLunes;
    private DefaultTableModel tblDefaultAlimentosDietaMartes;
    private DefaultTableModel tblDefaultAlimentosDietaMiercoles;
    private DefaultTableModel tblDefaultAlimentosDietaJueves;
    private DefaultTableModel tblDefaultAlimentosDietaViernes;
    private DefaultTableModel tblDefaultAlimentosDietaSabado;
    private DefaultTableModel tblDefaultAlimentosDietaDomingo;

    private JTable tblAlimentosDietaLunes;
    private JTable tblAlimentosDietaMartes;
    private JTable tblAlimentosDietaMiercoles;
    private JTable tblAlimentosDietaJueves;
    private JTable tblAlimentosDietaViernes;
    private JTable tblAlimentosDietaSabado;
    private JTable tblAlimentosDietaDomingo;

    private float aguaG = 0;
    private float proteinasG = 0;
    private float hcG = 0;
    private float lipidosG = 0;
    private float aguaL = 0;
    private float proteinasKcal = 0;
    private float hcKcal = 0;
    private float lipidosKcal = 0;
    private float calorias = 0;

    private boolean isNuevo;

    public ControladorAltaDieta(DietaGui dietaGui) {
        this.dietaGui = dietaGui;
        abmAlimentosDieta = new ABMAlimentosDieta();
        abmAlimentos = new ABMAlimento();
        tblAlimentos = dietaGui.getTblAlimentos();
        tblDietas = dietaGui.getTblDietas();
        tblDefaultAlimentos = dietaGui.getTblDefaultAlimento();
        tblDefaultDietas = dietaGui.getTblDefaultDietas();
        tblDefaultAlimentosDietaLunes = dietaGui.getTblDefaultAlimentoDietaLunes();
        tblDefaultAlimentosDietaMartes = dietaGui.getTblDefaultAlimentoDietaMartes();
        tblDefaultAlimentosDietaMiercoles = dietaGui.getTblDefaultAlimentoDietaMiercoles();
        tblDefaultAlimentosDietaJueves = dietaGui.getTblDefaultAlimentoDietaJueves();
        tblDefaultAlimentosDietaViernes = dietaGui.getTblDefaultAlimentoDietaViernes();
        tblDefaultAlimentosDietaSabado = dietaGui.getTblDefaultAlimentoDietaSabado();
        tblDefaultAlimentosDietaDomingo = dietaGui.getTblDefaultAlimentoDietaDomingo();

        tblAlimentosDietaLunes = dietaGui.getTblAlimentosDietaLunes();
        tblAlimentosDietaMartes = dietaGui.getTblAlimentosDietaMartes();
        tblAlimentosDietaMiercoles = dietaGui.getTblAlimentosDietaMiercoles();
        tblAlimentosDietaJueves = dietaGui.getTblAlimentosDietaJueves();
        tblAlimentosDietaViernes = dietaGui.getTblAlimentosDietaViernes();
        tblAlimentosDietaSabado = dietaGui.getTblAlimentosDietaSabado();
        tblAlimentosDietaDomingo = dietaGui.getTblAlimentosDietaDomingo();

        tblAlimentos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });

        tblDietas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                dietaSeleccionada();

            }
        });

        this.dietaGui.getTxtBusqueda().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busqueda();
            }
        });
        this.dietaGui.getTxtBusquedaDietas().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaDietas();
            }
        });
        this.dietaGui.setActionListener(this);
        setSuprimirRenglon();

    }

    private void setSuprimirRenglon() {
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = tblAlimentosDietaLunes.getInputMap(condition);
        ActionMap actionMap = tblAlimentosDietaLunes.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete_row");
        actionMap.put("delete_row", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tblDefaultAlimentosDietaLunes.removeRow(tblAlimentosDietaLunes.getSelectedRow());
                calcularMacros();
            }
        });
        inputMap = tblAlimentosDietaMartes.getInputMap(condition);
        actionMap = tblAlimentosDietaMartes.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete_row");
        actionMap.put("delete_row", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tblDefaultAlimentosDietaMartes.removeRow(tblAlimentosDietaMartes.getSelectedRow());
                calcularMacros();
            }
        });
        inputMap = tblAlimentosDietaMiercoles.getInputMap(condition);
        actionMap = tblAlimentosDietaMiercoles.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete_row");
        actionMap.put("delete_row", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tblDefaultAlimentosDietaMiercoles.removeRow(tblAlimentosDietaMiercoles.getSelectedRow());
                calcularMacros();
            }
        });
        inputMap = tblAlimentosDietaJueves.getInputMap(condition);
        actionMap = tblAlimentosDietaJueves.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete_row");
        actionMap.put("delete_row", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tblDefaultAlimentosDietaJueves.removeRow(tblAlimentosDietaJueves.getSelectedRow());
                calcularMacros();
            }
        });
        inputMap = tblAlimentosDietaViernes.getInputMap(condition);
        actionMap = tblAlimentosDietaViernes.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete_row");
        actionMap.put("delete_row", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tblDefaultAlimentosDietaViernes.removeRow(tblAlimentosDietaViernes.getSelectedRow());
                calcularMacros();
            }
        });
        inputMap = tblAlimentosDietaSabado.getInputMap(condition);
        actionMap = tblAlimentosDietaSabado.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete_row");
        actionMap.put("delete_row", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tblDefaultAlimentosDietaSabado.removeRow(tblAlimentosDietaSabado.getSelectedRow());
                calcularMacros();
            }
        });
        inputMap = tblAlimentosDietaDomingo.getInputMap(condition);
        actionMap = tblAlimentosDietaDomingo.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete_row");
        actionMap.put("delete_row", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tblDefaultAlimentosDietaDomingo.removeRow(tblAlimentosDietaDomingo.getSelectedRow());
                calcularMacros();
            }
        });
        dietaGui.getPnlTab().addChangeListener(this);
    }

    public void busqueda() {
        Base.openTransaction();
        cargarEnTabla(Alimento.where("id like ? or nombre like ?  ", "%" + dietaGui.getTxtBusqueda().getText() + "%", "%" + dietaGui.getTxtBusqueda().getText() + "%"));
        Base.commitTransaction();
    }

    public void busquedaDietas() {
        Base.openTransaction();
        cargarEnTablaDieta(Dieta.where("descripcion like ? or nombre like ?  ", "%" + dietaGui.getTxtBusquedaDietas().getText() + "%", "%" + dietaGui.getTxtBusquedaDietas().getText() + "%"));
        Base.commitTransaction();
    }

    private void dietaSeleccionada() {
        if (dietaGui.getTblDietas().getSelectedRow() != -1) {
            int row = tblDietas.getSelectedRow();
            Dieta dieta = abmAlimentosDieta.getDieta((Integer) tblDietas.getValueAt(row, 2));
            idModificar = dieta.getInteger("id");
            dietaGui.getTxtNombre().setText(dieta.getString("nombre"));
            dietaGui.getTxtDescripcion().setText(dieta.getString("descripcion"));
            cargarEnTablaDietaAliemtos(dieta.getAll(AlimentosDietas.class));
            dietaGui.setBotonesClickTabla();
        } else {
            dietaGui.setBotonesInicial();
            dietaGui.limpiarCampos();
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
        row[7] = (float) 1;
        row[8] = a.getInteger("id");
        int dia = dietaGui.getPnlTab().getSelectedIndex();
        switch (dia) {
            case 0:
                tblDefaultAlimentosDietaLunes.addRow(row);
                setCellEditor(tblDefaultAlimentosDietaLunes.getRowCount() - 1, 6, 0);
                break;
            case 1:
                tblDefaultAlimentosDietaMartes.addRow(row);
                setCellEditor(tblDefaultAlimentosDietaMartes.getRowCount() - 1, 6, 1);
                break;
            case 2:
                tblDefaultAlimentosDietaMiercoles.addRow(row);
                setCellEditor(tblDefaultAlimentosDietaMiercoles.getRowCount() - 1, 6, 2);
                break;
            case 3:
                tblDefaultAlimentosDietaJueves.addRow(row);
                setCellEditor(tblDefaultAlimentosDietaJueves.getRowCount() - 1, 6, 3);
                break;
            case 4:
                tblDefaultAlimentosDietaViernes.addRow(row);
                setCellEditor(tblDefaultAlimentosDietaViernes.getRowCount() - 1, 6, 4);
                break;
            case 5:
                tblDefaultAlimentosDietaSabado.addRow(row);
                setCellEditor(tblDefaultAlimentosDietaSabado.getRowCount() - 1, 6, 5);
                break;
            case 6:
                tblDefaultAlimentosDietaDomingo.addRow(row);
                setCellEditor(tblDefaultAlimentosDietaDomingo.getRowCount() - 1, 6, 6);
                break;
        }
        //this.setCellEditor();
        aguaG += a.getFloat("agua");
        proteinasG += a.getFloat("prot");
        hcG += a.getFloat("hc");
        lipidosG += a.getFloat("grasa");
        aguaL += 0;
        hcKcal = hcG * 4;
        lipidosKcal = lipidosG * 9;
        proteinasKcal = proteinasG * 4;
        calorias = hcKcal + lipidosKcal + proteinasKcal;
        dietaGui.getLblAguaL().setText(String.valueOf(aguaL) + " lts");
        dietaGui.getLblAguag().setText(String.valueOf(aguaG) + " grs");
        dietaGui.getLblCalorias().setText(String.valueOf(calorias) + " Kcal");
        dietaGui.getLblHCG().setText(String.valueOf(hcG) + " grs");
        dietaGui.getLblHCK().setText(String.valueOf(hcKcal) + " Kcal");
        dietaGui.getLblLipidoG().setText(String.valueOf(lipidosG) + " grs");
        dietaGui.getLblLipidoK().setText(String.valueOf(lipidosKcal) + " Kcal");
        dietaGui.getLblProtG().setText(String.valueOf(proteinasG) + " grs");
        dietaGui.getLblProtK().setText(String.valueOf(proteinasKcal) + " Kcal");

    }

    public void setCellEditor() {
        for (int i = 0; i < tblAlimentosDieta.getRowCount(); i++) {
            tblAlimentosDieta.getCellEditor(i, 7).addCellEditorListener(this);
        }
    }

    private void cargarEnTablaDietaAliemtos(LazyList<AlimentosDietas> lista) {
        tblDefaultAlimentosDietaLunes.setRowCount(0);
        tblDefaultAlimentosDietaMartes.setRowCount(0);
        tblDefaultAlimentosDietaMiercoles.setRowCount(0);
        tblDefaultAlimentosDietaJueves.setRowCount(0);
        tblDefaultAlimentosDietaViernes.setRowCount(0);
        tblDefaultAlimentosDietaSabado.setRowCount(0);
        tblDefaultAlimentosDietaDomingo.setRowCount(0);


        Iterator<AlimentosDietas> it = lista.iterator();
        while (it.hasNext()) {

            AlimentosDietas a = it.next();
            Alimento alim = abmAlimentos.getAlimento(a.getInteger("alimento_id"));
            Object row[] = new Object[9];
            row[0] = a.getString("hora");
            row[1] = alim.getString("nombre");
            row[2] = alim.getFloat("agua") * a.getFloat("porcion");
            row[3] = alim.getFloat("prot") * a.getFloat("porcion");
            row[4] = alim.getFloat("hc") * a.getFloat("porcion");
            row[5] = alim.getFloat("grasa") * a.getFloat("porcion");
            row[6] = (alim.getFloat("prot") * a.getFloat("porcion") * 4) + (alim.getFloat("hc") * a.getFloat("porcion") * 4) + (alim.getFloat("grasa") * a.getFloat("porcion") * 9);
            row[7] = a.getFloat("porcion");
            row[8] = alim.getInteger("id");
            String dia = a.getString("dia");
            switch (dia) {
                case "LUNES":
                    tblDefaultAlimentosDietaLunes.addRow(row);
                    setCellEditor(tblDefaultAlimentosDietaLunes.getRowCount() - 1, 6, 0);
                    break;
                case "MARTES":
                    tblDefaultAlimentosDietaMartes.addRow(row);
                    setCellEditor(tblDefaultAlimentosDietaMartes.getRowCount() - 1, 6, 1);
                    break;
                case "MIERCOLES":
                    tblDefaultAlimentosDietaMiercoles.addRow(row);
                    setCellEditor(tblDefaultAlimentosDietaMiercoles.getRowCount() - 1, 6, 2);
                    break;
                case "JUEVES":
                    tblDefaultAlimentosDietaJueves.addRow(row);
                    setCellEditor(tblDefaultAlimentosDietaJueves.getRowCount() - 1, 6, 3);
                    break;
                case "VIERNES":
                    tblDefaultAlimentosDietaViernes.addRow(row);
                    setCellEditor(tblDefaultAlimentosDietaViernes.getRowCount() - 1, 6, 4);
                    break;
                case "SABADO":
                    tblDefaultAlimentosDietaSabado.addRow(row);
                    setCellEditor(tblDefaultAlimentosDietaSabado.getRowCount() - 1, 6, 5);
                    break;
                case "DOMINGO":
                    tblDefaultAlimentosDietaDomingo.addRow(row);
                    setCellEditor(tblDefaultAlimentosDietaDomingo.getRowCount() - 1, 6, 6);
                    break;
            }

        }
        calcularMacros();
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

    public void setCellEditor(int row, int col, int dia) {
        switch (dia) {
            case 0:
                tblAlimentosDietaLunes.getCellEditor(row, col).addCellEditorListener(this);
                break;
            case 1:
                tblAlimentosDietaMartes.getCellEditor(row, col).addCellEditorListener(this);
                break;
            case 2:
                tblAlimentosDietaMiercoles.getCellEditor(row, col).addCellEditorListener(this);
                break;
            case 3:
                tblAlimentosDietaJueves.getCellEditor(row, col).addCellEditorListener(this);
                break;
            case 4:
                tblAlimentosDietaViernes.getCellEditor(row, col).addCellEditorListener(this);
                break;
            case 5:
                tblAlimentosDietaSabado.getCellEditor(row, col).addCellEditorListener(this);
                break;
            case 6:
                tblAlimentosDietaDomingo.getCellEditor(row, col).addCellEditorListener(this);
                break;
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
            aguaG = 0;
            proteinasG = 0;
            hcG = 0;
            lipidosG = 0;
            aguaL = 0;
            hcKcal = 0;
            lipidosKcal = 0;
            proteinasKcal = 0;
            calorias = 0;
            dietaGui.setBotonesNuevo();
        }
        if (e.getSource() == dietaGui.getBotGuardar()) {
            if (isNuevo) {
                LinkedList<Object[]> alimentoHora = new LinkedList<Object[]>();
                //Ciclo lunes
                for (int i = 0; i < tblDefaultAlimentosDietaLunes.getRowCount(); i++) {
                    Object[] array = {tblAlimentosDietaLunes.getValueAt(i, 8), tblAlimentosDietaLunes.getValueAt(i, 0), tblAlimentosDietaLunes.getValueAt(i, 7), "LUNES"};
                    alimentoHora.add(array);
                }
                //Ciclo martes
                for (int i = 0; i < tblDefaultAlimentosDietaMartes.getRowCount(); i++) {
                    Object[] array = {tblAlimentosDietaMartes.getValueAt(i, 8), tblAlimentosDietaMartes.getValueAt(i, 0), tblAlimentosDietaMartes.getValueAt(i, 7), "MARTES"};
                    alimentoHora.add(array);
                }
                //Ciclo miercoles
                for (int i = 0; i < tblDefaultAlimentosDietaMiercoles.getRowCount(); i++) {
                    Object[] array = {tblAlimentosDietaMiercoles.getValueAt(i, 8), tblAlimentosDietaMiercoles.getValueAt(i, 0), tblAlimentosDietaMiercoles.getValueAt(i, 7), "MIERCOLES"};
                    alimentoHora.add(array);
                }
                //Ciclo jueves
                for (int i = 0; i < tblDefaultAlimentosDietaJueves.getRowCount(); i++) {
                    Object[] array = {tblAlimentosDietaJueves.getValueAt(i, 8), tblAlimentosDietaJueves.getValueAt(i, 0), tblAlimentosDietaJueves.getValueAt(i, 7), "JUEVES"};
                    alimentoHora.add(array);
                }
                //Ciclo viernes
                for (int i = 0; i < tblDefaultAlimentosDietaViernes.getRowCount(); i++) {
                    Object[] array = {tblAlimentosDietaViernes.getValueAt(i, 8), tblAlimentosDietaViernes.getValueAt(i, 0), tblAlimentosDietaViernes.getValueAt(i, 7), "VIERNES"};
                    alimentoHora.add(array);
                }
                //Ciclo sabado
                for (int i = 0; i < tblDefaultAlimentosDietaSabado.getRowCount(); i++) {
                    Object[] array = {tblAlimentosDietaSabado.getValueAt(i, 8), tblAlimentosDietaSabado.getValueAt(i, 0), tblAlimentosDietaSabado.getValueAt(i, 7), "SABADO"};
                    alimentoHora.add(array);
                }
                //Ciclo domingo
                for (int i = 0; i < tblDefaultAlimentosDietaDomingo.getRowCount(); i++) {
                    Object[] array = {tblAlimentosDietaDomingo.getValueAt(i, 8), tblAlimentosDietaDomingo.getValueAt(i, 0), tblAlimentosDietaDomingo.getValueAt(i, 7), "DOMINGO"};
                    alimentoHora.add(array);
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
                LinkedList<Object[]> alimentoHora = new LinkedList<Object[]>();
                //Ciclo lunes
                for (int i = 0; i < tblDefaultAlimentosDietaLunes.getRowCount(); i++) {
                    Object[] array = {tblAlimentosDietaLunes.getValueAt(i, 8), tblAlimentosDietaLunes.getValueAt(i, 0), tblAlimentosDietaLunes.getValueAt(i, 7), "LUNES"};
                    alimentoHora.add(array);
                }
                //Ciclo martes
                for (int i = 0; i < tblDefaultAlimentosDietaMartes.getRowCount(); i++) {
                    Object[] array = {tblAlimentosDietaMartes.getValueAt(i, 8), tblAlimentosDietaMartes.getValueAt(i, 0), tblAlimentosDietaMartes.getValueAt(i, 7), "MARTES"};
                    alimentoHora.add(array);
                }
                //Ciclo miercoles
                for (int i = 0; i < tblDefaultAlimentosDietaMiercoles.getRowCount(); i++) {
                    Object[] array = {tblAlimentosDietaMiercoles.getValueAt(i, 8), tblAlimentosDietaMiercoles.getValueAt(i, 0), tblAlimentosDietaMiercoles.getValueAt(i, 7), "MIERCOLES"};
                    alimentoHora.add(array);
                }
                //Ciclo jueves
                for (int i = 0; i < tblDefaultAlimentosDietaJueves.getRowCount(); i++) {
                    Object[] array = {tblAlimentosDietaJueves.getValueAt(i, 8), tblAlimentosDietaJueves.getValueAt(i, 0), tblAlimentosDietaJueves.getValueAt(i, 7), "JUEVES"};
                    alimentoHora.add(array);
                }
                //Ciclo viernes
                for (int i = 0; i < tblDefaultAlimentosDietaViernes.getRowCount(); i++) {
                    Object[] array = {tblAlimentosDietaViernes.getValueAt(i, 8), tblAlimentosDietaViernes.getValueAt(i, 0), tblAlimentosDietaViernes.getValueAt(i, 7), "VIERNES"};
                    alimentoHora.add(array);
                }
                //Ciclo sabado
                for (int i = 0; i < tblDefaultAlimentosDietaSabado.getRowCount(); i++) {
                    Object[] array = {tblAlimentosDietaSabado.getValueAt(i, 8), tblAlimentosDietaSabado.getValueAt(i, 0), tblAlimentosDietaSabado.getValueAt(i, 7), "SABADO"};
                    alimentoHora.add(array);
                }
                //Ciclo domingo
                for (int i = 0; i < tblDefaultAlimentosDietaDomingo.getRowCount(); i++) {
                    Object[] array = {tblAlimentosDietaDomingo.getValueAt(i, 8), tblAlimentosDietaDomingo.getValueAt(i, 0), tblAlimentosDietaDomingo.getValueAt(i, 7), "DOMINGO"};
                    alimentoHora.add(array);
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
        if (e.getSource() == dietaGui.getBotModif()) {
            isNuevo = false;
            dietaGui.setBotonesModificar();
        }
        if (e.getSource() == dietaGui.getBotEliminarCancelar()) {

            if (dietaGui.getBotEliminarCancelar().getText().equals("Eliminar")) {
                if (JOptionPane.showConfirmDialog(dietaGui, "¿Desea borrar la dieta?", "¿cancelar?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                    int row = tblDietas.getSelectedRow();
                    boolean res = abmAlimentosDieta.Eliminar((Integer) tblDietas.getValueAt(row, 2));
                    if (res) {
                        JOptionPane.showMessageDialog(dietaGui, "Borrado existosamente!");
                        busquedaDietas();
                    } else {
                        JOptionPane.showMessageDialog(dietaGui, "Ocurrio un error!");
                    }
                }
            } else {
                if (JOptionPane.showConfirmDialog(dietaGui, "¿Desea cancelar la edición?", "¿cancelar?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    dietaGui.setBotonesInicial();
                }
            }
        }

    }

    private String replace(String s) {
        return s.replace(",", ".");
    }

    @Override
    public void editingStopped(ChangeEvent e) {
        calcularMacros();
    }

    private void calcularMacros() {
        aguaG = 0;
        proteinasG = 0;
        hcG = 0;
        lipidosG = 0;
        aguaL = 0;
        hcKcal = 0;
        lipidosKcal = 0;
        proteinasKcal = 0;
        calorias = 0;
        Alimento al;
        switch (dietaGui.getPnlTab().getSelectedIndex()){
        case 0:
        for (int i = 0; i < tblDefaultAlimentosDietaLunes.getRowCount(); i++) {
            float porcion;
            try {
                porcion = (float) tblAlimentosDietaLunes.getValueAt(i, 7);
            } catch (java.lang.ClassCastException ex) {
                porcion = Float.valueOf((Integer) tblAlimentosDietaLunes.getValueAt(i, 7));
            }
            al = Alimento.findById(tblAlimentosDietaLunes.getValueAt(i, 8));
            float hcGAux = al.getFloat("hc") * porcion;
            float protGAux = al.getFloat("prot") * porcion;
            float lipGAux = al.getFloat("grasa") * porcion;

            aguaG += al.getFloat("agua") * porcion;
            proteinasG += protGAux;
            hcG += hcGAux;
            lipidosG += lipGAux;
            tblAlimentosDietaLunes.setValueAt(al.getFloat("agua") * porcion, i, 2);
            tblAlimentosDietaLunes.setValueAt(al.getFloat("prot") * porcion, i, 3);
            tblAlimentosDietaLunes.setValueAt(al.getFloat("hc") * porcion, i, 4);
            tblAlimentosDietaLunes.setValueAt(al.getFloat("grasa") * porcion, i, 5);
            tblAlimentosDietaLunes.setValueAt(hcGAux * 4 + protGAux * 4 + lipGAux * 9, i, 6);
            //aguaL -=(float) tblAlimentosDietaLunes.getValueAt(row, 2)*(float) tblAlimentosDietaLunes.getValueAt(row, 7) ;                

        }
        break;
        case 1://Ciclo martes
        for (int i = 0; i < tblDefaultAlimentosDietaMartes.getRowCount(); i++) {
            float porcion;
            try {
                porcion = (float) tblAlimentosDietaMartes.getValueAt(i, 7);
            } catch (java.lang.ClassCastException ex) {
                porcion = Float.valueOf((Integer) tblAlimentosDietaMartes.getValueAt(i, 7));
            }
            al = Alimento.findById(tblAlimentosDietaMartes.getValueAt(i, 8));
            float hcGAux = al.getFloat("hc") * porcion;
            float protGAux = al.getFloat("prot") * porcion;
            float lipGAux = al.getFloat("grasa") * porcion;

            aguaG += al.getFloat("agua") * porcion;
            proteinasG += protGAux;
            hcG += hcGAux;
            lipidosG += lipGAux;
            tblAlimentosDietaMartes.setValueAt(al.getFloat("agua") * porcion, i, 2);
            tblAlimentosDietaMartes.setValueAt(al.getFloat("prot") * porcion, i, 3);
            tblAlimentosDietaMartes.setValueAt(al.getFloat("hc") * porcion, i, 4);
            tblAlimentosDietaMartes.setValueAt(al.getFloat("grasa") * porcion, i, 5);
            tblAlimentosDietaMartes.setValueAt(hcGAux * 4 + protGAux * 4 + lipGAux * 9, i, 6);            //aguaL -=(float) tblAlimentosDietaLunes.getValueAt(row, 2)*(float) tblAlimentosDietaLunes.getValueAt(row, 7) ;

        }
        break;
        case 2://Ciclo miercoles
        for (int i = 0; i < tblDefaultAlimentosDietaMiercoles.getRowCount(); i++) {
            float porcion;
            try {
                porcion = (float) tblAlimentosDietaMiercoles.getValueAt(i, 7);
            } catch (java.lang.ClassCastException ex) {
                porcion = Float.valueOf((Integer) tblAlimentosDietaMiercoles.getValueAt(i, 7));
            }
            al = Alimento.findById(tblAlimentosDietaMiercoles.getValueAt(i, 8));
            float hcGAux = al.getFloat("hc") * porcion;
            float protGAux = al.getFloat("prot") * porcion;
            float lipGAux = al.getFloat("grasa") * porcion;

            aguaG += al.getFloat("agua") * porcion;
            proteinasG += protGAux;
            hcG += hcGAux;
            lipidosG += lipGAux;
            tblAlimentosDietaMiercoles.setValueAt(al.getFloat("agua") * porcion, i, 2);
            tblAlimentosDietaMiercoles.setValueAt(al.getFloat("prot") * porcion, i, 3);
            tblAlimentosDietaMiercoles.setValueAt(al.getFloat("hc") * porcion, i, 4);
            tblAlimentosDietaMiercoles.setValueAt(al.getFloat("grasa") * porcion, i, 5);
            tblAlimentosDietaMiercoles.setValueAt(hcGAux * 4 + protGAux * 4 + lipGAux * 9, i, 6);            //aguaL -=(float) tblAlimentosDietaLunes.getValueAt(row, 2)*(float) tblAlimentosDietaLunes.getValueAt(row, 7) ;

        }
        break;
        case 3://Ciclo jueves
        for (int i = 0; i < tblDefaultAlimentosDietaJueves.getRowCount(); i++) {
            float porcion;
            try {
                porcion = (float) tblAlimentosDietaJueves.getValueAt(i, 7);
            } catch (java.lang.ClassCastException ex) {
                porcion = Float.valueOf((Integer) tblAlimentosDietaJueves.getValueAt(i, 7));
            }
            al = Alimento.findById(tblAlimentosDietaJueves.getValueAt(i, 8));
            float hcGAux = al.getFloat("hc") * porcion;
            float protGAux = al.getFloat("prot") * porcion;
            float lipGAux = al.getFloat("grasa") * porcion;

            aguaG += al.getFloat("agua") * porcion;
            proteinasG += protGAux;
            hcG += hcGAux;
            lipidosG += lipGAux;
            tblAlimentosDietaJueves.setValueAt(al.getFloat("agua") * porcion, i, 2);
            tblAlimentosDietaJueves.setValueAt(al.getFloat("prot") * porcion, i, 3);
            tblAlimentosDietaJueves.setValueAt(al.getFloat("hc") * porcion, i, 4);
            tblAlimentosDietaJueves.setValueAt(al.getFloat("grasa") * porcion, i, 5);
            tblAlimentosDietaJueves.setValueAt(hcGAux * 4 + protGAux * 4 + lipGAux * 9, i, 6);            //aguaL -=(float) tblAlimentosDietaLunes.getValueAt(row, 2)*(float) tblAlimentosDietaLunes.getValueAt(row, 7) ;

        }
        break;
        case 4://Ciclo viernes
        for (int i = 0; i < tblDefaultAlimentosDietaViernes.getRowCount(); i++) {
            float porcion;
            try {
                porcion = (float) tblAlimentosDietaViernes.getValueAt(i, 7);
            } catch (java.lang.ClassCastException ex) {
                porcion = Float.valueOf((Integer) tblAlimentosDietaViernes.getValueAt(i, 7));
            }
            al = Alimento.findById(tblAlimentosDietaViernes.getValueAt(i, 8));
            float hcGAux = al.getFloat("hc") * porcion;
            float protGAux = al.getFloat("prot") * porcion;
            float lipGAux = al.getFloat("grasa") * porcion;

            aguaG += al.getFloat("agua") * porcion;
            proteinasG += protGAux;
            hcG += hcGAux;
            lipidosG += lipGAux;
            tblAlimentosDietaViernes.setValueAt(al.getFloat("agua") * porcion, i, 2);
            tblAlimentosDietaViernes.setValueAt(al.getFloat("prot") * porcion, i, 3);
            tblAlimentosDietaViernes.setValueAt(al.getFloat("hc") * porcion, i, 4);
            tblAlimentosDietaViernes.setValueAt(al.getFloat("grasa") * porcion, i, 5);
            tblAlimentosDietaViernes.setValueAt(hcGAux * 4 + protGAux * 4 + lipGAux * 9, i, 6);            //aguaL -=(float) tblAlimentosDietaLunes.getValueAt(row, 2)*(float) tblAlimentosDietaLunes.getValueAt(row, 7) ;

        }
        break;
        case 5://Ciclo sabado
        for (int i = 0; i < tblDefaultAlimentosDietaSabado.getRowCount(); i++) {
            float porcion;
            try {
                porcion = (float) tblAlimentosDietaSabado.getValueAt(i, 7);
            } catch (java.lang.ClassCastException ex) {
                porcion = Float.valueOf((Integer) tblAlimentosDietaSabado.getValueAt(i, 7));
            }
            al = Alimento.findById(tblAlimentosDietaSabado.getValueAt(i, 8));
            float hcGAux = al.getFloat("hc") * porcion;
            float protGAux = al.getFloat("prot") * porcion;
            float lipGAux = al.getFloat("grasa") * porcion;

            aguaG += al.getFloat("agua") * porcion;
            proteinasG += protGAux;
            hcG += hcGAux;
            lipidosG += lipGAux;
            tblAlimentosDietaSabado.setValueAt(al.getFloat("agua") * porcion, i, 2);
            tblAlimentosDietaSabado.setValueAt(al.getFloat("prot") * porcion, i, 3);
            tblAlimentosDietaSabado.setValueAt(al.getFloat("hc") * porcion, i, 4);
            tblAlimentosDietaSabado.setValueAt(al.getFloat("grasa") * porcion, i, 5);
            tblAlimentosDietaSabado.setValueAt(hcGAux * 4 + protGAux * 4 + lipGAux * 9, i, 6);            //aguaL -=(float) tblAlimentosDietaLunes.getValueAt(row, 2)*(float) tblAlimentosDietaLunes.getValueAt(row, 7) ;

        }
        break;
        case 6://Ciclo domingo
        for (int i = 0; i < tblDefaultAlimentosDietaDomingo.getRowCount(); i++) {
            float porcion;
            try {
                porcion = (float) tblAlimentosDietaDomingo.getValueAt(i, 7);
            } catch (java.lang.ClassCastException ex) {
                porcion = Float.valueOf((Integer) tblAlimentosDietaDomingo.getValueAt(i, 7));
            }
            al = Alimento.findById(tblAlimentosDietaDomingo.getValueAt(i, 8));
            float hcGAux = al.getFloat("hc") * porcion;
            float protGAux = al.getFloat("prot") * porcion;
            float lipGAux = al.getFloat("grasa") * porcion;

            aguaG += al.getFloat("agua") * porcion;
            proteinasG += protGAux;
            hcG += hcGAux;
            lipidosG += lipGAux;
            tblAlimentosDietaDomingo.setValueAt(al.getFloat("agua") * porcion, i, 2);
            tblAlimentosDietaDomingo.setValueAt(al.getFloat("prot") * porcion, i, 3);
            tblAlimentosDietaDomingo.setValueAt(al.getFloat("hc") * porcion, i, 4);
            tblAlimentosDietaDomingo.setValueAt(al.getFloat("grasa") * porcion, i, 5);
            tblAlimentosDietaDomingo.setValueAt(hcGAux * 4 + protGAux * 4 + lipGAux * 9, i, 6);
        }
    }
        hcKcal = hcG * 4;
        lipidosKcal = lipidosG * 9;
        proteinasKcal = proteinasG * 4;
        calorias = hcKcal + lipidosKcal + proteinasKcal;
        dietaGui.getLblAguaL().setText(String.valueOf(aguaL) + " lts");
        dietaGui.getLblAguag().setText(String.valueOf(aguaG) + " grs");
        dietaGui.getLblCalorias().setText(String.valueOf(calorias) + " Kcal");
        dietaGui.getLblHCG().setText(String.valueOf(hcG) + " grs");
        dietaGui.getLblHCK().setText(String.valueOf(hcKcal) + " Kcal");
        dietaGui.getLblLipidoG().setText(String.valueOf(lipidosG) + " grs");
        dietaGui.getLblLipidoK().setText(String.valueOf(lipidosKcal) + " Kcal");
        dietaGui.getLblProtG().setText(String.valueOf(proteinasG) + " grs");
        dietaGui.getLblProtK().setText(String.valueOf(proteinasKcal) + " Kcal");
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        calcularMacros();
    }

}
