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
    private JTextArea SinopsisArea;
    private JTextArea RepartoArea;

    public Create() {
        super("Agregar Peliculas");
        setContentPane(CrearPanel);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdministrador menuAd = new MenuAdministrador();
                menuAd.iniciar();
                dispose();
            }
        });
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AgregarPelicula();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void AgregarPelicula() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        try(conn2) {
            String sql = "INSERT INTO Peliculas (titulo, director, genero, duracion, fecha_estreno, clasificacion, descripcion,reparto) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement guardar = conn2.prepareStatement(sql);
            guardar.setString(1, TituloText.getText());
            guardar.setString(2, DirectorText.getText());
            guardar.setString(3, GeneroText.getText());
            guardar.setInt(4, Integer.parseInt(DuracionText.getText()));
            guardar.setDate(5, Date.valueOf(FechaText.getText()));
            guardar.setString(6, ClasificaionText.getText());
            guardar.setString(7, SinopsisArea.getText());
            guardar.setString(8, RepartoArea.getText());
            int RegistroInsertado = guardar.executeUpdate();
            if(RegistroInsertado > 0){
                JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                TituloText.setText("");
                DirectorText.setText("");
                GeneroText.setText("");
                DuracionText.setText("");
                FechaText.setText("");
                ClasificaionText.setText("");
                SinopsisArea.setText("");
            }else{
                JOptionPane.showMessageDialog(null, "Error al insertar el registro");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al agregar la pelicula: " + e.getMessage());
            e.printStackTrace();
        }

    }
    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }
}
