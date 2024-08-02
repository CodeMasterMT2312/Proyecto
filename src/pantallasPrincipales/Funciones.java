package pantallasPrincipales;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Funciones extends JFrame {
    private JTextArea FuncionesArea;
    private JButton button1;
    private JButton reservarButton;
    private JLabel Funciones;
    private JPanel FuncionPanel;

    public Funciones() {
        super("Funciones");
        setContentPane(FuncionPanel);
        reservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reserva reser = new Reserva();
                reser.iniciar();
                dispose();
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cartelera cart = new Cartelera();
                cart.iniciar();
                dispose();
                try {
                    cart.MostrarCartelera();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al mostrar la cartelera");
                }
            }
        });
    }

    public void MostrarFunciones() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        try (conn2) {
            String queryFunciones = "SELECT * FROM Funciones;";
            Statement stmtFunciones = conn2.createStatement();
            ResultSet rsFunciones = stmtFunciones.executeQuery(queryFunciones);

            // Limpiar el área de texto antes de mostrar la información
            FuncionesArea.setText("");

            while (rsFunciones.next()) {
                int idPelicula = rsFunciones.getInt("id_pelicula");

                // Consulta adicional para obtener el título de la película
                String queryTitulo = "SELECT titulo FROM Peliculas WHERE id_pelicula = ?";
                PreparedStatement stmtTitulo = conn2.prepareStatement(queryTitulo);
                stmtTitulo.setInt(1, idPelicula);
                ResultSet rsTitulo = stmtTitulo.executeQuery();

                String tituloPelicula = "";
                if (rsTitulo.next()) {
                    tituloPelicula = rsTitulo.getString("titulo");
                }
                rsTitulo.close();

                // Mostrar los datos en el área de texto
                FuncionesArea.append("--------------------\n");
                FuncionesArea.append("ID: " + rsFunciones.getInt("id_funcion") + "\n");
                FuncionesArea.append("Pelicula: " + tituloPelicula + "\n");
                FuncionesArea.append("Sala: " + rsFunciones.getInt("id_sala") + "\n");
                FuncionesArea.append("Fecha: " + rsFunciones.getString("fecha") + "\n");
                FuncionesArea.append("Hora: " + rsFunciones.getString("hora") + "\n");
                FuncionesArea.append("Idioma: " + rsFunciones.getString("idioma") + "\n");
                FuncionesArea.append("Subtitulos: " + rsFunciones.getBoolean("subtitulos") + "\n");
                FuncionesArea.append("--------------------\n\n");
            }
            rsFunciones.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,700);
        setVisible(true);
    }
}
