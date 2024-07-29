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

    public void MostrarFunciones() throws SQLException{
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        try(conn2) {
            String query = "Select * from Funciones;";
            Statement stmt = conn2.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            FuncionesArea.setText("");
            while(rs.next()){
                FuncionesArea.append("--------------------\n");
                FuncionesArea.append("ID: " + rs.getInt("id_funcion") + "\n");
                FuncionesArea.append("Pelicula: " + rs.getString("id_pelicula") + "\n");
                FuncionesArea.append("Sala: " + rs.getInt("id_sala") + "\n");
                FuncionesArea.append("Fecha: " + rs.getString("fecha") + "\n");
                FuncionesArea.append("Hora: " + rs.getString("hora") + "\n");
                FuncionesArea.append("Idioma: " + rs.getString("idioma") + "\n");
                FuncionesArea.append("Subtitulos: " + rs.getBoolean("subtitulos") + "\n");
                FuncionesArea.append("--------------------\n\n");
            }
            rs.close();

        }catch (Exception e) {
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
