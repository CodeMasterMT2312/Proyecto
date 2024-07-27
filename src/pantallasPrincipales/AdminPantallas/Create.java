package pantallasPrincipales.AdminPantallas;

import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Create extends JFrame {
    private JButton button1;
    private JTextField TituloText;
    private JTextField DirectorText;
    private JTextField GeneroText;
    private JTextField DuracionText;
    private JTextField FechaText;
    private JTextField ClasificaionText;
    private JButton agregarButton;
    private JPanel CrearPanel;
    private JButton examinarButton;
    private JTextArea SinopsisArea;
    private JTextArea textArea1;

    public Create() {
        super("Agregar Peliculas");
        setContentPane(CrearPanel);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin menuAd = new MenuAdmin();
                menuAd.iniciar();
                dispose();
            }
        });
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void AgregarPelicula() throws SQLException {
        CONEXION conn = new CONEXION();
        conn.conexion();
        String sql = "INSERT INTO peliculas (Titulo, Director, Genero, Duracion, Fecha, Clasificacion, Sinopsis) VALUES (?,?,?,?,?,?,?)";

    }
    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }
}
