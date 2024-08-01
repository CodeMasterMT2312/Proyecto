package pantallasPrincipales.AdminPantallas.PantallasAux;

import pantallasPrincipales.AdminPantallas.MenuAdministrador;
import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ActualizarFunciones extends JFrame {
    private JButton button1;
    private JTextField IDFuncion;
    private JComboBox CampoBox;
    private JTextField CambiadoText;
    private JButton actualizarButton;
    private JPanel ActFunPanel;

    public ActualizarFunciones() {
        super("Actualizar Función");
        setContentPane(ActFunPanel);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdministrador MenusAdmin = new MenuAdministrador();
                MenusAdmin.iniciar();
                dispose();
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ActualizarFuncion();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }

    public void ActualizarFuncion() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        try (conn2) {
            String id = IDFuncion.getText();
            String campo = (String) CampoBox.getSelectedItem();
            String valor = CambiadoText.getText();

            if (id.isEmpty() || campo.isEmpty() || valor.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos");
                return;
            }

            String query;
            PreparedStatement guardar;

            // Determinar el tipo de dato y preparar la consulta
            switch (campo) {
                case "id_pelicula":
                    query = "UPDATE Funciones SET " + campo + " = ? WHERE id_funcion = ?";
                    guardar = conn2.prepareStatement(query);
                    guardar.setInt(1, Integer.parseInt(valor));
                    break;
                case "id_sala":
                    query = "UPDATE Funciones SET " + campo + " = ? WHERE id_funcion = ?";
                    guardar = conn2.prepareStatement(query);
                    guardar.setInt(1, Integer.parseInt(valor));
                    break;
                case "subtitulos":
                    query = "UPDATE Funciones SET " + campo + " = ? WHERE id_funcion = ?";
                    guardar = conn2.prepareStatement(query);
                    // Para Boolean, convertir el valor a 0 o 1
                    guardar.setBoolean(1, Boolean.parseBoolean(valor));
                    break;
                default:
                    query = "UPDATE Funciones SET " + campo + " = ? WHERE id_funcion = ?";
                    guardar = conn2.prepareStatement(query);
                    guardar.setString(1, valor);
                    break;
            }

            guardar.setInt(2, Integer.parseInt(id));
            int filasAfectadas = guardar.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Función actualizada exitosamente");
                IDFuncion.setText("");
                CambiadoText.setText("");
                CampoBox.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la función con ese ID");
            }

            guardar.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar la función");
        }
    }

}
