/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ABMs.ABMAlimento;
import Interfaces.AbmAlimentosGui;
import Modelos.Alimento;
import Modelos.Arancel;
import Modelos.Proveedor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author NicoOrcasitas
 */
public class ControladorAlimentos implements ActionListener {

    private AbmAlimentosGui alimentosGui;
    private ABMAlimento abmAlimentos;
    private Alimento alimento;
    private JTable tblAlimentos;
    private DefaultTableModel tblDefaultAlimentos;
    private boolean isNuevo;

    public ControladorAlimentos(AbmAlimentosGui alimentosGui) {
        this.alimentosGui = alimentosGui;
        abmAlimentos = new ABMAlimento();
        tblAlimentos = alimentosGui.getTblAlimentos();
        tblDefaultAlimentos = alimentosGui.getTblDefaultAlimento();
        tblAlimentos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });

        this.alimentosGui.getTxtBusqueda().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busqueda();
            }
        });
        this.alimentosGui.setActionListener(this);
    }

    public void busqueda() {
        Base.openTransaction();
        cargarEnTabla(Alimento.where("id like ? or nombre like ?  ", "%" + alimentosGui.getTxtBusqueda().getText() + "%", "%" + alimentosGui.getTxtBusqueda().getText() + "%"));
        Base.commitTransaction();
    }

    private void cargarEnTabla(LazyList<Alimento> lista) {
        tblDefaultAlimentos.setRowCount(0);
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

    private void cargarEnCampos(Alimento a) {
        alimentosGui.getTxtAGM().setText(a.getFloat("mono").toString());
        alimentosGui.getTxtAGP().setText(a.getFloat("poli").toString());
        alimentosGui.getTxtAGS().setText(a.getFloat("satur").toString());
        alimentosGui.getTxtAgua().setText(a.getFloat("agua").toString());
        alimentosGui.getTxtCH().setText(a.getFloat("hc").toString());
        alimentosGui.getTxtCalcio().setText(a.getFloat("calcio").toString());
        alimentosGui.getTxtColesterol().setText(a.getFloat("colesterol").toString());
        alimentosGui.getTxtFibra().setText(a.getFloat("fibra").toString());
        alimentosGui.getTxtFosforo().setText(a.getFloat("fosforo").toString());
        alimentosGui.getTxtIG().setText(a.getFloat("indiceglucemico").toString());
        alimentosGui.getTxtLipido().setText(a.getFloat("grasa").toString());
        alimentosGui.getTxtMagnesio().setText(a.getFloat("magnesio").toString());
        alimentosGui.getTxtNombre().setText(a.getString("nombre"));
        alimentosGui.getTxtPC().setText(a.getFloat("pc").toString());
        alimentosGui.getTxtPotasio().setText(a.getFloat("potasio").toString());
        alimentosGui.getTxtProteina().setText(a.getFloat("prot").toString());
        alimentosGui.getTxtSodio().setText(a.getFloat("sodio").toString());
        alimentosGui.getTxtHierro().setText(a.getFloat("hierro").toString());
        alimentosGui.getLblKCal().setText(String.valueOf(a.getFloat("prot") * 4 + a.getFloat("hc") * 4 + a.getFloat("grasa") * 9));

    }

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            int row = tblAlimentos.getSelectedRow();
            alimento = abmAlimentos.getAlimento((Integer) tblAlimentos.getValueAt(row, 0));
            cargarEnCampos(alimento);
            alimentosGui.setBotonesClickTabla();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == alimentosGui.getBotNuevo()) {
            isNuevo = true;
            alimentosGui.setBotonesNuevo();
        }
        if (e.getSource() == alimentosGui.getBotGuardar()) {
            if (isNuevo) {
                Alimento al = cargarDatosDesdeVentana();
                if (al.getString("nombre") != null) {
                    boolean aux = abmAlimentos.alta(cargarDatosDesdeVentana());
                    if (aux) {
                        JOptionPane.showMessageDialog(alimentosGui, "alimento guardado exitosamente!");
                        busqueda();
                        alimentosGui.setBotonesInicial();
                    } else {
                        JOptionPane.showMessageDialog(alimentosGui, "Ocurrio un error!");
                    }
                }
            } else {
                Alimento al = cargarDatosDesdeVentana();
                al.set("id", alimento.getId());
                boolean res = abmAlimentos.modificar(al);
                if (res) {
                    busqueda();
                    JOptionPane.showMessageDialog(alimentosGui, "alimento modificado existosamente!");
                    alimentosGui.setBotonesInicial();
                } else {
                    JOptionPane.showMessageDialog(alimentosGui, "Ocurrio un error!");
                }
            }
        }
        if (e.getSource() == alimentosGui.getBotModif()) {
            isNuevo = false;
            alimentosGui.setBotonesModificar();
        }
        if (e.getSource() == alimentosGui.getBotEliminarCancelar()) {

            if (alimentosGui.getBotEliminarCancelar().getText().equals("Eliminar")) {
                if (JOptionPane.showConfirmDialog(alimentosGui, "¿Desea borrar el alimento?", "¿cancelar?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                    int row = tblAlimentos.getSelectedRow();
                    boolean res = abmAlimentos.baja((Integer) tblAlimentos.getValueAt(row, 0));
                    if (res) {
                        JOptionPane.showMessageDialog(alimentosGui, "Borrado existosamente!");
                        busqueda();
                    } else {
                        JOptionPane.showMessageDialog(alimentosGui, "Ocurrio un error!");
                    }
                }
            } else {
                if (JOptionPane.showConfirmDialog(alimentosGui, "¿Desea cancelar la edición?", "¿cancelar?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    alimentosGui.setBotonesInicial();
                }
            }
        }
    }

    private Alimento cargarDatosDesdeVentana() {
        boolean error = false;
        Alimento al = new Alimento();
        try {
            String nombre = alimentosGui.getTxtNombre().getText();
            Float pc = Float.valueOf(replace(alimentosGui.getTxtPC().getText()));
            Float agua = Float.valueOf(replace(alimentosGui.getTxtAgua().getText()));
            Float prot = Float.valueOf(replace(alimentosGui.getTxtProteina().getText()));
            Float hc = Float.valueOf(replace(alimentosGui.getTxtCH().getText()));
            Float grasa = Float.valueOf(replace(alimentosGui.getTxtLipido().getText()));
            Float satur = Float.valueOf(replace(alimentosGui.getTxtAGS().getText()));
            Float mono = Float.valueOf(replace(alimentosGui.getTxtAGM().getText()));
            Float poli = Float.valueOf(replace(alimentosGui.getTxtAGP().getText()));
            Float colesterol = Float.valueOf(replace(alimentosGui.getTxtColesterol().getText()));
            Float fibra = Float.valueOf(replace(alimentosGui.getTxtFibra().getText()));
            Float sodio = Float.valueOf(replace(alimentosGui.getTxtSodio().getText()));
            Float potasio = Float.valueOf(replace(alimentosGui.getTxtPotasio().getText()));
            Float magnesio = Float.valueOf(replace(alimentosGui.getTxtMagnesio().getText()));
            Float calcio = Float.valueOf(replace(alimentosGui.getTxtCalcio().getText()));
            Float fosforo = Float.valueOf(replace(alimentosGui.getTxtFosforo().getText()));
            Float hierro = Float.valueOf(replace(alimentosGui.getTxtHierro().getText()));
            Float indiceglucemico = Float.valueOf(replace(alimentosGui.getTxtIG().getText()));
            al = new Alimento();
            al.set(
                    "nombre", nombre,
                    "pc", pc,
                    "agua", agua,
                    "prot", prot,
                    "hc", hc,
                    "grasa", grasa,
                    "satur", satur,
                    "mono", mono,
                    "poli", poli,
                    "colesterol", colesterol,
                    "fibra", fibra,
                    "sodio", sodio,
                    "potasio", potasio,
                    "magnesio", magnesio,
                    "calcio", calcio,
                    "fosforo", fosforo,
                    "hierro", hierro,
                    "indiceglucemico", indiceglucemico
            );
        } catch (java.lang.NumberFormatException ex) {
            JOptionPane.showMessageDialog(alimentosGui, "Error! Revise los campos");
        }
        return al;
    }

    private String replace(String s) {
        return s.replace(",", ".");
    }

}
