package pantallasPrincipales;

import pantallasPrincipales.AdminPantallas.MenuAdmin;
import pantallasPrincipales.AdminPantallas.MenuAdministrador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {
    private JTextField UserText;
    private JPasswordField passText;
    private JButton iniciarSesionButton;
    private JButton crearCuentaButton;
    private JPanel loginPanel;

    public Login() {
        super("Inicio de Sesion");
        setContentPane(loginPanel);
        crearCuentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registro regis = new Registro();
                regis.iniciar();
                dispose();
            }
        });
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loguearse();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }

    public void loguearse() throws SQLException {
        boolean credencialesCorrectas = false;
        CONEXION c = new CONEXION();
        Connection conn = c.conexion();
        try {
            if (conn != null) {
                String query = "SELECT correo_electronico, tipo_usuario FROM Usuarios WHERE correo_electronico = ? AND contraseña = MD5(?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                String usernameInput = UserText.getText();
                String passwordInput = new String(passText.getPassword());

                pstmt.setString(1, usernameInput);
                pstmt.setString(2, passwordInput);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    credencialesCorrectas = true;
                    String TipoUser = rs.getString("tipo_usuario");

                    if (TipoUser.equals("cliente")) {
                        Cartelera cart = new Cartelera();
                        cart.iniciar();
                        cart.MostrarCartelera();
                    } else if (TipoUser.equals("administrador")) {
                        MenuAdministrador menu = new MenuAdministrador();
                        menu.iniciar();
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
                }
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }
}
