package pantallasPrincipales.AdminPantallas.PantallasAux;

import pantallasPrincipales.AdminPantallas.MenuAdministrador;
import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


/**
 * La clase {@code ActualizarUsuarios} proporciona una interfaz gráfica para actualizar registros de usuarios en la base de datos.
 * Extiende {@code JFrame} y utiliza un panel de contenido {@code ActUsPanel} con campos de texto, un combo box y botones para actualizar usuarios.
 */
public class ActualizarUsuarios extends JFrame {
    private JButton button1;
    private JTextField CedulaText;
    private JComboBox CampoBox;
    private JButton actualizarButton;
    private JTextField ValorCambiado;
    private JPanel ActUsPanel;
    private JComboBox RolBox;

    /**
     * Constructor de la clase {@code ActualizarUsuarios}.
     * Inicializa la ventana con el título "Actualizar Usuario" y configura los manejadores de eventos para los botones.
     */
    public ActualizarUsuarios() {
        super("Actualizar Usuario");
        setContentPane(ActUsPanel);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdministrador user = new MenuAdministrador();
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

    /**
     * Configura la ventana para su visualización.
     * Establece el tamaño, la operación de cierre y la visibilidad de la ventana.
     */
    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }

    /**
     * Actualiza un usuario en la base de datos según los valores ingresados en los campos de texto.
     * Verifica que todos los campos estén llenos y actualiza el registro correspondiente en la tabla {@code Usuarios}.
     *
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
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
