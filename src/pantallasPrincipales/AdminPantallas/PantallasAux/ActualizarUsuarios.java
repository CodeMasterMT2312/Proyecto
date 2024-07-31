package pantallasPrincipales.AdminPantallas.PantallasAux;

import pantallasPrincipales.AdminPantallas.MenuAdmin;
import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class ActualizarUsuarios extends JFrame {
    private JButton button1;
    private JTextField CedulaText;
    private JComboBox CampoBox;
    private JButton actualizarButton;
    private JTextField ValorCambiado;
    private JPanel ActUsPanel;
    private JComboBox RolBox;

    public ActualizarUsuarios() {
        super("Actualizar Usuario");
        setContentPane(ActUsPanel);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin user = new MenuAdmin();
                user.iniciar();
                dispose();
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ActualizarUser();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el usuario: " + ex.getMessage());
                }
            }
        });
    }

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }

    public void ActualizarUser() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        try (conn2) {
            String cedula = CedulaText.getText();
            String campo = (String) CampoBox.getSelectedItem();
            String valor = ValorCambiado.getText();
            String rol = (String) RolBox.getSelectedItem();

            if (cedula.isEmpty() || campo.isEmpty() || valor.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos");
                return;
            }

            // Validar campo
            if (!campo.equals("nombre") && !campo.equals("apellido") && !campo.equals("correo_electronico") && !campo.equals("contraseña") && !campo.equals("tipo_usuario")) {
                JOptionPane.showMessageDialog(null, "Campo no válido");
                return;
            }

            String query;
            PreparedStatement guardar;

            // Determinar el tipo de dato y preparar la consulta
            switch (campo) {
                case "contraseña":
                    query = "UPDATE Usuarios SET " + campo + " = md5(?) WHERE Cedula = ?";
                    guardar = conn2.prepareStatement(query);
                    guardar.setString(1, valor);
                    break;
                case "tipo_usuario":
                    query = "UPDATE Usuarios SET " + campo + " = ? WHERE Cedula = ?";
                    guardar = conn2.prepareStatement(query);
                    guardar.setString(1, rol);
                    break;
                default:
                    query = "UPDATE Usuarios SET " + campo + " = ? WHERE Cedula = ?";
                    guardar = conn2.prepareStatement(query);
                    guardar.setString(1, valor);
            }

            guardar.setInt(2, Integer.parseInt(cedula));
            int filasAfectadas = guardar.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente");
                CedulaText.setText("");
                ValorCambiado.setText("");
                CampoBox.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el usuario con esa cédula");
            }

            guardar.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el usuario");
        }
    }
}
