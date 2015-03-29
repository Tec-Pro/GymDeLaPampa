package Controladores;



import ABMs.ABMGastos;
import Interfaces.AddCategoriaGui;
import Interfaces.AreaGui;
import Interfaces.GastosGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Modelos.Categoria;
import Modelos.Dato;
import Modelos.Gasto;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;


public class ControladorGasto implements ActionListener {

    private GastosGui gastosGui;
    private AddCategoriaGui addCat;
    private AreaGui areaGui;
    private DefaultTableModel tablaDefault;
    private JTable tabla;
    private ABMGastos abmGastos;
    private boolean nuevoApretado = false;
    private boolean modApretado = false;
    private File archivoBackup;
    private int selecEnviarBack = 0;
    private boolean nuevoAreaApretado = false;
    private boolean modAreaApretado = false;
    private String nombreArea;

    public ControladorGasto(GastosGui p) {
        this.gastosGui = p;
        this.gastosGui.setActionListener(this);

        abmGastos = new ABMGastos();

        tabla = gastosGui.getTablaGastos();
        tablaDefault = gastosGui.getTablaMovDefault();
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        gastosGui.getBoxCategoria().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gastosGui.getBoxArea().removeAllItems();

                changeValue();
                if (gastosGui.getBoxArea().getItemCount() <= 0) {
                    JOptionPane.showMessageDialog(gastosGui, "No posee servicios en esta categoria, cargue uno");
                }

            }
        });
        gastosGui.getBoxArea().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
               Dato d= Dato.findFirst("descripcion = ?", gastosGui.getBoxArea().getSelectedItem());
               if(d!=null)
                    gastosGui.getBoxTipo().setSelectedItem(d.get("ingreso_egreso"));
            }
        });
   
    }
    

    private void changeValue() {
        abrirBase();
        Categoria c = Categoria.first("nombre = ?", gastosGui.getBoxCategoria().getSelectedItem());
        LazyList<Dato> datos = Dato.where("categoria_id = ?", c.get("id"));
        Iterator<Dato> i = datos.iterator();
        while (i.hasNext()) {
            Dato d = i.next();
            gastosGui.getBoxArea().addItem(d.get("descripcion"));
        }
    }

    private void tablaMouseClicked(MouseEvent evt) {
        int r = tabla.getSelectedRow();
        abrirBase();
        Gasto gasto = Gasto.first("id = ?", tabla.getValueAt(r, 5));
        Dato dato = Dato.first("id = ?", gasto.get("dato_id"));
        Categoria categ = Categoria.first("id = ?", dato.get("categoria_id"));
        gastosGui.getBoxCategoria().setSelectedItem(categ.get("nombre"));
        gastosGui.getBoxArea().setSelectedItem(dato.get("descripcion"));
        gastosGui.getBoxTipo().setSelectedItem(tabla.getValueAt(r, 3));
        gastosGui.getTextMonto().setText(BigDecimal.valueOf(Double.valueOf(tabla.getValueAt(r, 2).toString())).abs().toString());
        gastosGui.getFecha().setDate(gasto.getDate("fecha"));
        gastosGui.getDesc().setText(gasto.getString("descrip"));
        gastosGui.getBotMod().setEnabled(true);
        gastosGui.getBotEliminar().setEnabled(true);
        nuevoApretado = false;
                modApretado = false;
                
                
                
                gastosGui.BloquearCampos(false);
                gastosGui.BotonesNuevo(true);
                
                
                gastosGui.getBotNuevo().setEnabled(true);
                
                
    }

    private void tablaMouseClickedArea(MouseEvent evt) {
        int r = areaGui.getTablaAreas().getSelectedRow();
        areaGui.getTextNombre().setText(areaGui.getTablaAreas().getValueAt(r, 0).toString());
        areaGui.getBoxCategoria().setSelectedItem(areaGui.getTablaAreas().getValueAt(r, 1).toString());
        areaGui.getBoxTipo().setSelectedItem(areaGui.getTablaAreas().getValueAt(r, 2).toString());
        areaGui.getBotModificar().setEnabled(true);
        areaGui.getBoxCategoria().setEnabled(false);
        areaGui.getBoxTipo().setEnabled(false);
        areaGui.getTextNombre().setEnabled(false);
        areaGui.getBoxCategoria().setEnabled(false);
        areaGui.getBoxTipo().setEnabled(false);
        areaGui.getTextNombre().setEnabled(false);
        areaGui.getBotModificar().setText("Modificar");
        areaGui.getBotNuevo().setText("Nuevo");
        modAreaApretado = false;
        nuevoAreaApretado = false;
        
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gastosGui.getBotAgregarCat()) {
            addCat = new AddCategoriaGui(null, true);
            this.addCat.setActionListener(this);
            addCat.setLocationRelativeTo(gastosGui);
            addCat.setVisible(true);
        }
        if (e.getSource() == gastosGui.getBotGestionarAreas()) {
            areaGui = new AreaGui(null, true);
            areaGui.setActionListener(this);
            areaGui.getTableAreas().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    tablaMouseClickedArea(evt);
                }
            });
            abrirBase();
            LazyList<Dato> areas = Dato.findAll();
            areaGui.getTablaDefault().setRowCount(0);
            if (!areas.isEmpty()) {
                Iterator<Dato> it = areas.iterator();
                while (it.hasNext()) {
                    Dato d = it.next();
                    Object row[] = new Object[3];
                    row[0] = d.getString("descripcion");
                    row[2] = d.getString("ingreso_egreso");
                    Categoria c = Categoria.first("id = ?", d.get("categoria_id"));
                    row[1] = c.getString("nombre");
                    areaGui.getTablaDefault().addRow(row);
                }
            }
            LazyList<Categoria> cats = Categoria.findAll();
            Iterator<Categoria> i = cats.iterator();
            while (i.hasNext()) {
                Categoria c = i.next();
                areaGui.getBoxCategoria().addItem(c.get("nombre"));
            }
            areaGui.setLocationRelativeTo(gastosGui);
            areaGui.setVisible(true);
             gastosGui.BloquearCampos(false);
                gastosGui.BotonesNuevo(true);
                gastosGui.getBotEliminar().setEnabled(false);
                gastosGui.getBotMod().setEnabled(false);
                gastosGui.getBotNuevo().setEnabled(true);
                gastosGui.getBoxCategoria().setSelectedIndex(0);
                gastosGui.getBoxArea().setSelectedIndex(0);
                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                // date.setDate(cal.getActualMinimum(Calendar.DAY_OF_MONTH));;
                gastosGui.getFecha().setDate(date);
                gastosGui.getTextMonto().setText("");
                gastosGui.getDesc().setText("");
                nuevoApretado = false;
                modApretado = false;
            
        }
        if (e.getSource() == gastosGui.getBotNuevo()) {
            if (!nuevoApretado && !modApretado) {
                gastosGui.BotonesNuevo(false);
                gastosGui.BloquearCampos(true);
                gastosGui.getBotMod().setEnabled(false);
                gastosGui.getBotEliminar().setEnabled(true);
                nuevoApretado = true;
                modApretado = false;
                           
                gastosGui.getBoxCategoria().setSelectedIndex(0);
                gastosGui.getBoxArea().setSelectedIndex(0);
                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                //date.setDate(cal.getActualMinimum(Calendar.DAY_OF_MONTH));;
                gastosGui.getFecha().setDate(date);
                gastosGui.getTextMonto().setText("");
                gastosGui.getDesc().setText("");
            } else {
                if (nuevoApretado) {
                    boolean ret = true;
                    Gasto g = new Gasto();
                    try {
                        Double monto = Double.valueOf(gastosGui.getTextMonto().getText());
                        if (gastosGui.getBoxTipo().getSelectedItem().toString().equals("egreso")) {
                            g.set("monto", BigDecimal.valueOf(monto).setScale(2, RoundingMode.CEILING).abs().negate());

                        } else {
                            g.set("monto", BigDecimal.valueOf(monto).setScale(2, RoundingMode.CEILING).abs());
                        }
                    } catch (NumberFormatException | ClassCastException er) {
                        ret = false;
                        JOptionPane.showMessageDialog(gastosGui, "Error en el monto", "Error!", JOptionPane.ERROR_MESSAGE);
                    }

                    if (gastosGui.getFecha().getCalendar() != null) {
                        g.set("fecha", dateToMySQLDate(gastosGui.getFecha().getCalendar().getTime(), false));
                    } else {
                        ret = false;
                    }
                    g.set("descrip", gastosGui.getDesc().getText());
                    if (gastosGui.getBoxArea().getSelectedItem() != null) {
                        Dato d = Dato.first("descripcion = ?", gastosGui.getBoxArea().getSelectedItem());
                        g.set("dato_id", d.get("id"));
                    } else {
                        ret = false;
                    }

                    if (!ret) {
                        JOptionPane.showMessageDialog(gastosGui, "Hubo un error, revise los datos");
                    } else {
                        if (abmGastos.Alta(g)) {
                            JOptionPane.showMessageDialog(gastosGui, "Movimiento registrado exitosamente.");
                            gastosGui.BotonesNuevo(true);
                            gastosGui.BloquearCampos(false);
                            gastosGui.getBotMod().setEnabled(false);
                            gastosGui.getBotEliminar().setEnabled(false);
                        } else {
                            JOptionPane.showMessageDialog(gastosGui, "Ocurrio un error, no se pudo registrar el movimiento.");
                        }
                        gastosGui.cargarGastos();
                        nuevoApretado = false;
                    }
                }
            }
            if (modApretado) {
                boolean retBol = true;

                try {
                    Double monto = Double.valueOf(gastosGui.getTextMonto().getText());

                } catch (NumberFormatException | ClassCastException er) {
                    retBol = false;
                    JOptionPane.showMessageDialog(gastosGui, "Error en el monto", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                if (retBol) {
                    int ret = JOptionPane.showConfirmDialog(gastosGui, "¿Desea modificar el movimiento?", null, JOptionPane.YES_NO_OPTION);
                    if (ret == JOptionPane.YES_OPTION) {
                        int r = gastosGui.getTablaGastos().getSelectedRow();
                        abrirBase();
                        Gasto g = Gasto.first("id = ?", gastosGui.getTablaGastos().getValueAt(r, 5));
                        if (gastosGui.getBoxTipo().getSelectedItem().toString().equals("egreso")) {
                            g.set("monto", BigDecimal.valueOf(Double.valueOf(gastosGui.getTextMonto().getText())).setScale(2, RoundingMode.CEILING).abs().negate());

                        } else {
                            g.set("monto", BigDecimal.valueOf(Double.valueOf(gastosGui.getTextMonto().getText())).setScale(2, RoundingMode.CEILING).abs());
                        }
                        if (gastosGui.getFecha().getCalendar() != null) {
                            g.set("fecha", dateToMySQLDate(gastosGui.getFecha().getCalendar().getTime(), false));
                        } else {
                            retBol = false;
                        }
                        g.set("descrip", gastosGui.getDesc().getText());

                        if (gastosGui.getBoxArea().getSelectedItem() != null) {
                            Dato d = Dato.first("descripcion = ?", gastosGui.getBoxArea().getSelectedItem());
                            g.set("dato_id", d.get("id"));
                        } else {
                            retBol = false;
                        }
                        if (retBol) {
                            if (abmGastos.Modificar(g)) {
                                JOptionPane.showMessageDialog(gastosGui, "Movimiento modificado exitosamente.");
                                gastosGui.BotonesNuevo(true);
                                gastosGui.BloquearCampos(false);
                                gastosGui.getBotMod().setEnabled(false);
                                gastosGui.getBotEliminar().setEnabled(false);
                                gastosGui.cargarGastos();

                            } else {
                                JOptionPane.showMessageDialog(gastosGui, "Ocurrio un error, no se pudo modificar el movimiento.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(gastosGui, "Ocurrio un error, no se pudo modificar el movimiento.");
                        }
                    }

                }
            }
        }
        if (e.getSource() == gastosGui.getBotEliminar()) {
            if (nuevoApretado || modApretado) {
                gastosGui.BloquearCampos(false);
                gastosGui.BotonesNuevo(true);
                gastosGui.getBotEliminar().setEnabled(false);
                gastosGui.getBotMod().setEnabled(false);
                gastosGui.getBotNuevo().setEnabled(true);
                gastosGui.getBoxCategoria().setSelectedIndex(0);
                gastosGui.getBoxArea().setSelectedIndex(0);
                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                // date.setDate(cal.getActualMinimum(Calendar.DAY_OF_MONTH));;
                gastosGui.getFecha().setDate(date);
                gastosGui.getTextMonto().setText("");
                gastosGui.getDesc().setText("");
                nuevoApretado = false;
                modApretado = false;
            } else {
                int ret = JOptionPane.showConfirmDialog(gastosGui, "¿Desea eliminar el movimiento?", null, JOptionPane.YES_NO_OPTION);
                if (ret == JOptionPane.YES_OPTION) {
                    int r = gastosGui.getTablaGastos().getSelectedRow();
                    abrirBase();
                    Gasto d = Gasto.first("id = ?", gastosGui.getTablaGastos().getValueAt(r, 5));
                    if (d != null) {
                        if (abmGastos.Baja(d)) {
                            JOptionPane.showMessageDialog(gastosGui, "Movimiento eliminado exitosamente.");
                            gastosGui.cargarGastos();
                        } else {
                            JOptionPane.showMessageDialog(gastosGui, "Ocurrio un error intente nuevamente.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(gastosGui, "Ocurrio un error intente nuevamente.");
                    }

                }
                gastosGui.getBotEliminar().setEnabled(false);
                gastosGui.getBotMod().setEnabled(false);
                gastosGui.getBoxCategoria().setSelectedIndex(0);
                gastosGui.getBoxArea().setSelectedIndex(0);
                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                // date.setDate(cal.getActualMinimum(Calendar.DAY_OF_MONTH));;
                gastosGui.getFecha().setDate(date);
                gastosGui.getTextMonto().setText("");
                gastosGui.getDesc().setText("");
            }

        }

        if (e.getSource() == gastosGui.getBotMod()) {
            gastosGui.BloquearCampos(true);
            gastosGui.BotonesNuevo(false);
            gastosGui.getBotNuevo().setEnabled(true);
            gastosGui.getBotMod().setEnabled(false);
            gastosGui.getBotEliminar().setEnabled(true);
            modApretado = true;
            nuevoApretado = false;
        }

        ///categoria gui controlador
        if (addCat != null) {
            if (e.getSource() == addCat.getjButton1()) { //boton agregar
                int ret = JOptionPane.showConfirmDialog(gastosGui, "¿Desea agregar la categoria " + addCat.getjTextField1().getText() + "?", null, JOptionPane.YES_NO_OPTION);
                if (ret == JOptionPane.YES_OPTION) {
                    Categoria c = new Categoria();
                    c.set("nombre", addCat.getjTextField1().getText().toUpperCase());
                    c.saveIt();
                    c = Categoria.first("nombre = ?", addCat.getjTextField1().getText().toUpperCase());
                    if (c != null) {
                        JOptionPane.showMessageDialog(gastosGui, "Categoria creada exitosamente.");
                        addCat.dispose();
                        LazyList<Categoria> cats = Categoria.findAll();
                        Iterator<Categoria> i = cats.iterator();
                        gastosGui.getBoxCategoria().removeAllItems();
                        while (i.hasNext()) {
                            Categoria ca = i.next();
                            gastosGui.getBoxCategoria().addItem(ca.get("nombre"));
                        }
                        
                         LazyList<Categoria> categoriasBase = Categoria.findAll();
        Iterator<Categoria> it = categoriasBase.iterator();
        String selec=gastosGui.getCategorias().getSelectedItem().toString();
        gastosGui.getCategorias().removeAllItems();
        gastosGui.getCategorias().addItem("Todos");
        while (it.hasNext()) {
            Categoria cate = it.next();
            gastosGui.getCategorias().addItem(cate.get("nombre"));
        }
        gastosGui.getCategorias().setSelectedItem(selec);
                        
                    } else {
                        JOptionPane.showMessageDialog(gastosGui, "Ocurrio un error intente nuevamente.");
                    }
                }
            }
        }
        ////
        ///AreaGui CONTROLADOR
        if (areaGui != null) {
            if (e.getSource() == areaGui.getBotNuevo()) {
                if (!nuevoAreaApretado && !modAreaApretado) {
                    areaGui.getTextNombre().setText("");
                    areaGui.getBoxCategoria().setSelectedIndex(0);
                    areaGui.getBoxTipo().setSelectedIndex(0);
                    areaGui.getTextNombre().setEnabled(true);
                    areaGui.getBoxCategoria().setEnabled(true);
                    areaGui.getBoxTipo().setEnabled(true);
                    areaGui.getBotModificar().setText("Cancelar");
                    areaGui.getBotModificar().setEnabled(true);
                    areaGui.getBotNuevo().setText("Guardar");
                    nuevoAreaApretado = true;
                    modAreaApretado = false;
                } else {
                    if (nuevoAreaApretado) {
                        int ret = JOptionPane.showConfirmDialog(gastosGui, "¿Desea agregar el servicio " + areaGui.getTextNombre().getText() + "?", null, JOptionPane.YES_NO_OPTION);
                        if (ret == JOptionPane.YES_OPTION) {
                            Dato d = new Dato();
                            d.set("descripcion", areaGui.getTextNombre().getText());
                            d.set("ingreso_egreso", areaGui.getBoxTipo().getSelectedItem());
                            abrirBase();
                            Categoria c = Categoria.first("nombre = ?", areaGui.getBoxCategoria().getSelectedItem());
                            d.set("categoria_id", c.get("id"));
                            d.saveIt();
                            Dato dato = new Dato();
                            dato = Dato.first("descripcion = ?", areaGui.getTextNombre().getText());
                            if (dato != null) {
                                JOptionPane.showMessageDialog(gastosGui, "Servicio creado exitosamente.");
                                LazyList<Dato> areas = Dato.findAll();
                                areaGui.getTablaDefault().setRowCount(0);
                                if (!areas.isEmpty()) {
                                    Iterator<Dato> it = areas.iterator();
                                    while (it.hasNext()) {
                                        Dato dat = it.next();
                                        Object row[] = new Object[3];
                                        row[0] = dat.getString("descripcion");
                                        row[2] = dat.getString("ingreso_egreso");
                                        Categoria ca = Categoria.first("id = ?", dat.get("categoria_id"));
                                        row[1] = ca.getString("nombre");
                                        areaGui.getTablaDefault().addRow(row);
                                    }
                                    areaGui.getTextNombre().setEnabled(false);
                                    areaGui.getBoxCategoria().setEnabled(false);
                                    areaGui.getBoxTipo().setEnabled(false);
                                    areaGui.getBotModificar().setText("Modificar");
                                    areaGui.getBotNuevo().setText("Nuevo");
                                    areaGui.getBotModificar().setEnabled(false);
                                    nuevoAreaApretado = false;
                                    modAreaApretado = false;
                                }

                            } else {
                                JOptionPane.showMessageDialog(gastosGui, "Ocurrio un error intente nuevamente.");
                            }
                        }
                    }

                    if (modAreaApretado) {
                        if (areaGui.getTextNombre().getText().equals("")) {
                            JOptionPane.showMessageDialog(gastosGui, "Servicio invalido.");
                        } else {
                            // Dato dato = Dato.first("descripcion = ?", areaGui.getTextNombre().getText());
                            // if (dato == null) {
                            Dato d = Dato.first("descripcion = ?", nombreArea);
                            d.set("descripcion", areaGui.getTextNombre().getText());
                            d.set("ingreso_egreso", areaGui.getBoxTipo().getSelectedItem());
                            Categoria ca = Categoria.first("nombre = ?", areaGui.getBoxCategoria().getSelectedItem());
                            d.set("categoria_id", ca.get("id"));
                            d.saveIt();
                            JOptionPane.showMessageDialog(gastosGui, "Servicio modificado exitosamente.");
                            LazyList<Dato> areas = Dato.findAll();
                            Iterator<Dato> it = areas.iterator();
                            areaGui.getTablaDefault().setRowCount(0);
                            while (it.hasNext()) {
                                Dato dat = it.next();
                                Object row[] = new Object[3];
                                row[0] = dat.getString("descripcion");
                                row[2] = dat.getString("ingreso_egreso");
                                Categoria cat = Categoria.first("id = ?", dat.get("categoria_id"));
                                row[1] = cat.getString("nombre");
                                areaGui.getTablaDefault().addRow(row);
                            }
                            areaGui.getBotModificar().setEnabled(false);
                            areaGui.getBoxCategoria().setEnabled(false);
                            areaGui.getBoxTipo().setEnabled(false);
                            areaGui.getTextNombre().setEnabled(false);
                            areaGui.getBotModificar().setText("Modificar");
                            areaGui.getBotNuevo().setText("Nuevo");
                            modAreaApretado = false;
                            // } else {
                            //   JOptionPane.showMessageDialog(gastosGui, "Servicio ya existente.");
                            //}
                        }

                    }
                }
            }
            if (e.getSource() == areaGui.getBotModificar()) {
                if (nuevoAreaApretado || modAreaApretado) {
                    areaGui.getTextNombre().setText("");
                    areaGui.getBoxCategoria().setSelectedIndex(0);
                    areaGui.getBoxTipo().setSelectedIndex(0);
                    areaGui.getTextNombre().setEnabled(false);
                    areaGui.getBoxCategoria().setEnabled(false);
                    areaGui.getBoxTipo().setEnabled(false);
                    areaGui.getBotModificar().setText("Modificar");
                    areaGui.getBotModificar().setEnabled(false);
                    areaGui.getBotNuevo().setText("Nuevo");
                    areaGui.getBotNuevo().setEnabled(true);
                    nuevoAreaApretado = false;
                    modAreaApretado = false;
                } else {
                    areaGui.getTextNombre().setEnabled(true);
                    areaGui.getBoxCategoria().setEnabled(true);
                    areaGui.getBoxTipo().setEnabled(true);
                    areaGui.getBotModificar().setText("Cancelar");
                    areaGui.getBotNuevo().setText("Guardar");
                    areaGui.getBotModificar().setEnabled(true);
                    areaGui.getBotNuevo().setEnabled(true);
                    modAreaApretado = true;
                    nuevoAreaApretado = false;
                    nombreArea = areaGui.getTextNombre().getText();
                }
            }
        }
        ////



       
 

    }

    public String dateToMySQLDate(Date fecha, boolean paraMostrar) {
        if (fecha != null) {
            if (paraMostrar) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                return sdf.format(fecha);
            } else {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(fecha);
            }
        } else {
            return "";
        }
    }

    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gym", "root", "root");
        }
    }



}
