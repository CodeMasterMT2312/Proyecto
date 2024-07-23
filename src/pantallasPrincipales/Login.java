package pantallasPrincipales;


import pantallasPrincipales.AdminPantallas.MenuAdmin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        try{
            if(conn != null){
                String query = "Select correo_electronico,contraseña,tipo_usuario from Usuarios;";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                String usernameInput = UserText.getText();
                String passwordInput = new String(passText.getPassword());
                while (rs.next()) {
                    String correo = rs.getString("correo_electronico");
                    String passBD = rs.getString("contraseña");
                    String TipoUser = rs.getString("tipo_usuario");

                    if (correo.equals(usernameInput) && passBD.equals(passwordInput)) {
                        credencialesCorrectas = true;
                        if (TipoUser.equals("cliente")) {
                            Cartelera cart = new Cartelera();
                            cart.iniciar();
                        } else if (TipoUser.equals("administrador")) {
                            MenuAdmin menu = new MenuAdmin();
                            menu.iniciar();
                        }
                        dispose();
                        break;
                    }
                }
                if (!credencialesCorrectas) {
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
                }
            }
                conn.close();
            }catch(SQLException ex){
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
