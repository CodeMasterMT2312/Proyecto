package pantallasPrincipales;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Registro extends JFrame {
    private JTextField CedulaText;
    private JTextField NombreText;
    private JTextField ApellidoText;
    private JTextField CorreoText;
    private JTextField ContrasenaText;
    private JComboBox RolArea;
    private JButton crearCuentaButton;
    private JButton button2;
    private JPanel RegistroPanel;

    public Registro(){
        super("Registrar");
        setContentPane(RegistroPanel);

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login Log2 = new Login();
                Log2.iniciar();
                dispose();
            }
        });
        crearCuentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    registrarUsuario();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void registrarUsuario() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection con = conn.conexion();
        try (con) {
            // Verificar el rol del usuario
            String rolSeleccionado = (String) RolArea.getSelectedItem();
            if ("Administrador".equals(rolSeleccionado)) {
                // Solicitar el código si el rol es Administrador
                String codigoIngresado = JOptionPane.showInputDialog("Ingrese el código de autorización:");
                if (codigoIngresado == null || !codigoIngresado.equals("EPN24A")) {
                    JOptionPane.showMessageDialog(null, "Código incorrecto. No se puede registrar el usuario.");
                    return; // Salir del método si el código es incorrecto
                }
            }

            // Preparar la consulta para insertar el registro
            String query = "INSERT INTO Usuarios (Cedula, nombre, apellido, correo_electronico, contraseña, tipo_usuario) VALUES (?,?,?,?,md5(?),?);";
            PreparedStatement guardar = con.prepareStatement(query);
            guardar.setInt(1, Integer.parseInt(CedulaText.getText()));
            guardar.setString(2, NombreText.getText());
            guardar.setString(3, ApellidoText.getText());
            guardar.setString(4, CorreoText.getText());
            guardar.setString(5, ContrasenaText.getText());
            guardar.setString(6, rolSeleccionado);
            int RegistroInsertado = guardar.executeUpdate();

            if (RegistroInsertado > 0) {
                JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                // Limpiar los campos
                CedulaText.setText("");
                NombreText.setText("");
                ApellidoText.setText("");
                CorreoText.setText("");
                ContrasenaText.setText("");
                RolArea.setSelectedIndex(0);
                // Iniciar login y cerrar la ventana actual
                Login log = new Login();
                log.iniciar();
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar el registro");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar al usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }
}
