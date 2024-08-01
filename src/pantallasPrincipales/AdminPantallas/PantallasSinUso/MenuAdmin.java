package pantallasPrincipales.AdminPantallas.PantallasSinUso;

import pantallasPrincipales.AdminPantallas.*;
import pantallasPrincipales.AdminPantallas.PantallasAux.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MenuAdmin extends JFrame {
    private JButton createButton;
    private JButton readButton;
    private JButton ActualizarPeliButton;
    private JButton deleteButton;
    private JPanel adminMenuPanel;
    private JButton borrarUsuarioButton;
    private JButton agregarImagenButton;
    private JButton agregarFuncionButton;
    private JButton borrarImagenButton;
    private JButton borrarFuncionButton;
    private JButton actualizarImagenesButton;
    private JButton actualizarUsuariosButton;
    private JButton actualizarFuncionesButton;
    private JButton generarEstadisticasButton;

    public MenuAdmin() {
        super("Menu Administrador");
        setContentPane(adminMenuPanel);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Create crear = new Create();
                crear.iniciar();
                dispose();
            }
        });
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Read leer = new Read();
                leer.iniciar();
                try {
                    leer.VisualizarBDD();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Delete del = new Delete();
                del.iniciar();
                dispose();
            }
        });
        agregarImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarImagen AGIMG= new AgregarImagen();
                AGIMG.iniciar();
                dispose();
            }
        });
        agregarFuncionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearFunciones CF = new CrearFunciones();
                CF.iniciar();
                dispose();
            }
        });
        ActualizarPeliButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ActualizarPeliculas ActPeli = new ActualizarPeliculas();
                ActPeli.iniciar();
                dispose();
            }
        });
        actualizarFuncionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActualizarFunciones ActFun = new ActualizarFunciones();
                ActFun.iniciar();
                dispose();
            }
        });
        actualizarImagenesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActualizarImagenes ACTIMG = new ActualizarImagenes();
                ACTIMG.iniciar();
                dispose();
            }
        });
        actualizarUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActualizarUsuarios ActUser= new ActualizarUsuarios();
                ActUser.iniciar();
                dispose();
            }
        });
        borrarFuncionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BorrarFuncion boFun = new BorrarFuncion();
                boFun.iniciar();
                dispose();
            }
        });
        borrarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DelUser del = new DelUser();
                del.iniciar();
                dispose();
            }
        });
        borrarImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BorrarImagen bor = new BorrarImagen();
                bor.iniciar();
                dispose();
            }
        });
        generarEstadisticasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Estadisticas genen = new Estadisticas();
                genen.iniciar();
                dispose();
            }
        });
    }

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850,700);
        setVisible(true);
    }

}
