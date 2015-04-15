/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Interfaces.AbmAlimentosGui;
import Interfaces.ActividadesGui;
import Interfaces.BusquedaGui;
import Interfaces.IngresoGui;
import Interfaces.PrincipalGui;
import Interfaces.UsuarioGui;
import Modelos.Arancel;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.javalite.activejdbc.LazyList;
import Interfaces.ArticulosGUI;
import Interfaces.CargarVentaGUI;
import Interfaces.CompraGui;
import Interfaces.CumpleaniosGui;
import Interfaces.DietaGui;
import Interfaces.EstadisticaConocioGui;
import Interfaces.EstadisticaGui;
import Interfaces.GastosGui;
import Interfaces.GuiCrearRutinas;
import Interfaces.GuiEjercicios;
import Interfaces.MovimientosDelDiaGui;
import Interfaces.ProveedorGui;
import Modelos.User;
import com.jtattoo.plaf.aero.AeroLookAndFeel;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;
import org.javalite.activejdbc.Base;
import java.util.Properties;

/**
 *
 * @author nico
 */
public class ControladorPrincipalGui implements ActionListener {

    private ControladorPrincipalGui controladorPrincipal;
    private PrincipalGui principalGui;
    private ControladorLogin controladorLogin;
    private BusquedaGui socios;
    private ControladorClientes controladorClientes;
    private ActividadesGui actividadesGui;
    private ControladorActividades controladorActividades;
    private UsuarioGui usuarioGui;
    private ControladorUsuario controladorUsuario;
    private ControladorIngreso controladorIngreso;
    private IngresoGui ingresoGui;
    private ActualizarDatos actualizarDatos;
    private ControladorJReport impresionArancel;
    private ArticulosGUI articulosGUI;
    private ControladorArticulosGUI controladorArticulosGUI;
    private CargarVentaGUI cargarVentaGUI;
    private ControladorCargarVentaGUI controladorCargarVentaGUI;
    private CumpleaniosGui cumpleGui;

    private ControladorProveedor controladorProveedor;
    private ProveedorGui proveedorGui;
    private ControladorCompra controladorCompra;
    private CompraGui compraGui;
    private GastosGui gastosGui;
    ControladorGasto controladorGastos;
    private MovimientosDelDiaGui movimientosGui;

    private GuiEjercicios guiEjercicios;
    private ControladorEjercicios controladorEjercicios;

    private AbmAlimentosGui alimentosGui;
    private ControladorAlimentos controladorAlimentos;

    private GuiCrearRutinas guiCrearRutina;
    private ControladorGuiCrearRutinas controladorGuiCrearRutina;

    private DietaGui dietaGui;
    private ControladorAltaDieta controladorDietas;

    //private String usuario;
    public ControladorPrincipalGui() throws Exception {
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            Properties props = new Properties();
            props.put("logoString", "Tec-Pro");
            com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme("Gold");
            AeroLookAndFeel.setCurrentTheme(props);
            com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme("Gold");

            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        abrirBase();
        principalGui = new PrincipalGui();
        ingresoGui = new IngresoGui();
        controladorIngreso = new ControladorIngreso(ingresoGui);
        controladorLogin = new ControladorLogin(principalGui, ingresoGui);
        controladorLogin.start();//inicio el thread para la pantalla login asií se carga todo mientras inicias sesion
        principalGui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ingresoGui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        principalGui.setCursor(Cursor.WAIT_CURSOR); //cambio el cursor por si se inicia sesión antes de cargar las cosas

        socios = new BusquedaGui();
        this.actualizarDatos = new ActualizarDatos(socios);
        controladorClientes = new ControladorClientes(socios, principalGui, actualizarDatos);
        principalGui.setActionListener(this);
        principalGui.getDesktop().add(socios);
        actividadesGui = new ActividadesGui();
        controladorActividades = new ControladorActividades(actividadesGui);
        principalGui.getDesktop().add(actividadesGui);
        usuarioGui = new UsuarioGui();
        controladorUsuario = new ControladorUsuario(usuarioGui);
        impresionArancel = new ControladorJReport("precio.jasper");
        principalGui.getDesktop().add(usuarioGui);
        articulosGUI = new ArticulosGUI();
        controladorArticulosGUI = new ControladorArticulosGUI(articulosGUI);
        principalGui.getDesktop().add(articulosGUI);
        cargarVentaGUI = new CargarVentaGUI();
        controladorCargarVentaGUI = new ControladorCargarVentaGUI(cargarVentaGUI, principalGui);
        principalGui.getDesktop().add(cargarVentaGUI);
        proveedorGui = new ProveedorGui();
        compraGui = new CompraGui();
        controladorCompra = new ControladorCompra(compraGui, principalGui);
        principalGui.getDesktop().add(proveedorGui);
        principalGui.getDesktop().add(compraGui);
        controladorProveedor = new ControladorProveedor(proveedorGui, principalGui, articulosGUI, compraGui);
        gastosGui = new GastosGui();
        principalGui.getDesktop().add(gastosGui);
        controladorGastos = new ControladorGasto(gastosGui);
        movimientosGui = new MovimientosDelDiaGui();
        principalGui.getDesktop().add(movimientosGui);

        guiEjercicios = new GuiEjercicios();
        controladorEjercicios = new ControladorEjercicios(guiEjercicios);
        principalGui.getDesktop().add(guiEjercicios);
        alimentosGui = new AbmAlimentosGui();
        controladorAlimentos = new ControladorAlimentos(alimentosGui);
        principalGui.getDesktop().add(alimentosGui);

        guiCrearRutina = new GuiCrearRutinas();
        controladorGuiCrearRutina = new ControladorGuiCrearRutinas(guiCrearRutina, principalGui);
        principalGui.getDesktop().add(guiCrearRutina);

        dietaGui = new DietaGui();
        controladorDietas = new ControladorAltaDieta(dietaGui);
        principalGui.getDesktop().add(dietaGui);
                cumpleGui = new CumpleaniosGui(controladorClientes.getAltaClienteGui(), controladorClientes.getControladorAbmCliente());

        principalGui.getDesktop().add(cumpleGui);
        principalGui.setCursor(Cursor.DEFAULT_CURSOR);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == principalGui.getBotDesconectar()) {
            int r = JOptionPane.showConfirmDialog(principalGui, "¿Desea cerrar la sesion?", "Cerrar sesion", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                principalGui.dispose();
                ingresoGui.dispose();
                actividadesGui.setVisible(false);
                socios.setVisible(false);
                usuarioGui.setVisible(false);
                controladorLogin.getLog().setVisible(true);
                controladorLogin.getLog().getTextUsuario().setText("");
                controladorLogin.getLog().getTextPass().setText("");
            }

            //Aca debe ir para que se cierre la sesión
        }
        if (ae.getSource() == principalGui.getBotSalir()) {
            int r = JOptionPane.showConfirmDialog(principalGui, "¿Desea cerrar la aplicación?", "Salir", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
        if (ae.getSource() == principalGui.getBotSocios()) {
            socios.setVisible(true);
            socios.toFront();
            //controladorClientes.cargarSocios();
            actualizarDatos.cargarSocios();
            /*
             * ESTO SE EJECUTA UNA VEZ!
             */
            //  abm.modbase();
        }
        if (ae.getSource() == principalGui.getBotActividades()) {
            controladorActividades.bloquearNoAdmin();
            actividadesGui.setVisible(true);
            actividadesGui.toFront();
            actividadesGui.getTablaActividadesDefault().setRowCount(0);
            LazyList ListAranceles = Arancel.where("activo = ?", 1);
            Iterator<Arancel> it = ListAranceles.iterator();
            while (it.hasNext()) {
                Arancel ar = it.next();
                Object row[] = new Object[3];
                row[0] = ar.getInteger("id");
                row[1] = ar.getString("nombre");
                row[2] = ar.getFloat("precio");
                actividadesGui.getTablaActividadesDefault().addRow(row);
            }
        }
        if (ae.getSource() == principalGui.getBotUsuario()) {
            LazyList listUsuarios = User.findAll();
            usuarioGui.getTablaUsuarioDefault().setRowCount(0);
            Iterator<User> itusu = listUsuarios.iterator();
            while (itusu.hasNext()) {
                User u = itusu.next();
                Object row[] = new Object[1];
                row[0] = u.get("USUARIO");
                usuarioGui.getTablaUsuarioDefault().addRow(row);
            }

            usuarioGui.setVisible(true);
            usuarioGui.toFront();
        }
        if (ae.getSource() == principalGui.getIngreso()) {
            ingresoGui.setVisible(true);
            ingresoGui.toFront();
            ingresoGui.setLocationRelativeTo(null);
        }
        if (ae.getSource() == principalGui.getBotCumple()) {
            cumpleGui.cargarCumple();
            cumpleGui.setVisible(true);
            cumpleGui.toFront();
        }
        if (ae.getSource() == principalGui.getDeclaracion()) {

            try {
                File path;
                try {
                    path = new File(getClass().getResource("/Documentos/declaracion.pdf").toURI());
                    Desktop.getDesktop().open(path);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(ControladorPrincipalGui.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(principalGui, "Corrobore si tiene instalado un lector PDF \n " + ex, "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (ae.getSource() == principalGui.getImpresionAranceles()) {
            try {
                impresionArancel.mostrarReporte();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorPrincipalGui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorPrincipalGui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JRException ex) {
                Logger.getLogger(ControladorPrincipalGui.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (ae.getSource() == principalGui.getDepurar()) {
            int ret = JOptionPane.showConfirmDialog(principalGui, "¿Desea continuar con la depuración de la base de datos?, se borraran los usuarios inactivos, y todos sus datos relacionados", "Depuración de datos", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                Base.openTransaction();
                Base.exec("DELETE FROM `gym`.`socios`\n" + "WHERE activo=0;");
                Base.commitTransaction();
                JOptionPane.showMessageDialog(principalGui, "Se ha depurado correctamente");
            }
        }

        if (ae.getSource().equals(principalGui.getBtnArticulos())) {
            articulosGUI.setVisible(true);
            articulosGUI.toFront();
            articulosGUI.reClick();
            controladorArticulosGUI.ActualizarLista();
        }
        if (ae.getSource().equals(principalGui.getBtnCargarVenta())) {
            cargarVentaGUI.setVisible(true);
            cargarVentaGUI.toFront();
            cargarVentaGUI.reClick();
            controladorCargarVentaGUI.ActualizarListaProductos();
            controladorCargarVentaGUI.ActualizarListaSocios();
        }
        if (ae.getSource() == principalGui.getProveedores()) {
            controladorProveedor.cargarTodos();
            proveedorGui.setVisible(true);
            proveedorGui.toFront();
        }
        if (ae.getSource() == principalGui.getRegistrarCompra()) {
            controladorCompra.cargarTodos();
            compraGui.setVisible(true);
            compraGui.toFront();
        }
        if (ae.getSource() == principalGui.getBtnGastos()) {
            gastosGui.setVisible(true);
            gastosGui.toFront();
        }
        if (ae.getSource() == principalGui.getBtnCaja()) {
            movimientosGui.setVisible(true);
            movimientosGui.toFront();
        }
        if (ae.getSource().equals(principalGui.getBtnGestionEjercicios())) {
            controladorEjercicios.ActualizarLista();
            guiEjercicios.setVisible(true);
            guiEjercicios.toFront();
            guiEjercicios.reClick();
        }
        if (ae.getSource().equals(principalGui.getBtnAlimentos())) {
            controladorAlimentos.busqueda();
            alimentosGui.setVisible(true);
            alimentosGui.toFront();
        }
        if (ae.getSource().equals(principalGui.getBtnCrearRutina())) {
            controladorGuiCrearRutina.ActualizarListaSocios();
            guiCrearRutina.setVisible(true);
            guiCrearRutina.toFront();
        }
        if (ae.getSource().equals(principalGui.getBtnDietas())) {
            controladorDietas.busquedaDietas();
            controladorDietas.busqueda();
            dietaGui.setVisible(true);
            dietaGui.toFront();
        }

        if (ae.getSource().equals(principalGui.getBtnEstadisticasIngreso())) {
            EstadisticaGui es = new EstadisticaGui(principalGui, true);
            es.setLocationRelativeTo(principalGui);
            es.setVisible(true);
        }
        if (ae.getSource().equals(principalGui.getBtnEstadisticasSocios())) {
            EstadisticaConocioGui es = new EstadisticaConocioGui(principalGui, true);
            es.setLocationRelativeTo(principalGui);
            es.setVisible(true);
        }
    }
    
            public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gym", "root", "root");
        }
    }

    public static void main(String[] args) throws InterruptedException, Exception {
        ControladorPrincipalGui appl = new ControladorPrincipalGui();

    }
}
