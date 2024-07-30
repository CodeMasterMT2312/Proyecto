package pantallasPrincipales.AdminPantallas;

import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CrearFunciones extends JFrame {
    private JTextField ID_Pelicula;
    private JTextField ID_Sala;
    private JTextField Fecha;
    private JTextField Hora;
    private JTextField Idioma;
    private JTextField Subtitulos;
    private JButton guardarButton;
    private JButton button1;
    private JPanel CrearFuncionPanel;

    public CrearFunciones() {
        super("Agregar Funciones");
        setContentPane(CrearFuncionPanel);
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AgregarFunciones();
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error al agregar la función: " + e1.getMessage());
                }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin MAD= new MenuAdmin();
                MAD.iniciar();
                dispose();
            }
        });
    }

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650,600);
        setVisible(true);
    }

    public void AgregarFunciones() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        try(conn2){
            String query="INSERT INTO Funciones (id_pelicula, id_sala, fecha, hora, idioma, subtitulos) VALUES (?,?,?,?,?,?);";
            PreparedStatement guardar = conn2.prepareStatement(query);
            guardar.setInt(1, Integer.parseInt(ID_Pelicula.getText()));
            guardar.setInt(2, Integer.parseInt(ID_Sala.getText()));
            guardar.setDate(3, java.sql.Date.valueOf(Fecha.getText()));
            guardar.setString(4, Hora.getText());
            guardar.setString(5, Idioma.getText());
            guardar.setBoolean(6, Boolean.parseBoolean(Subtitulos.getText()));
            int RegistroInsertado = guardar.executeUpdate();
            if(RegistroInsertado > 0){
                JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                ID_Pelicula.setText("");
                ID_Sala.setText("");
                Fecha.setText("");
                Hora.setText("");
                Idioma.setText("");
                Subtitulos.setText("");
            }else{
                JOptionPane.showMessageDialog(null, "Error al insertar el registro");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al agregar la función: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
