package pantallasPrincipales;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * La clase {@code Registro} extiende {@code JFrame} y representa la ventana de registro de nuevos usuarios.
 * <p>
 * Permite a los usuarios registrar una nueva cuenta proporcionando su cédula, nombre, apellido, correo electrónico,
 * contraseña y rol. También incluye validaciones como verificar el código de autorización para el rol de Administrador
 * y validar la dirección de correo electrónico ingresada.
 * </p>
 */
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

    /**
     * Constructor de la clase {@code Registro}.
     * <p>
     * Inicializa la ventana de registro y configura los botones. El botón para volver al login abre la ventana de login,
     * y el botón para crear cuenta registra el usuario al hacer clic.
     * </p>
     */
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


    /**
     * Registra un nuevo usuario en la base de datos.
     * <p>
     * Este método valida la información del usuario, verifica el código de autorización para el rol de Administrador,
     * valida el correo electrónico, y luego inserta los datos del usuario en la base de datos.
     * </p>
     *
     * @throws SQLException Si ocurre un error al conectar o consultar la base de datos.
     */
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

            // Validar el correo electrónico
            String correo = CorreoText.getText();
            if (!esCorreoValido(correo)) {
                JOptionPane.showMessageDialog(null, "El correo electrónico ingresado no es válido.");
                return; // Salir del método si el correo no es válido
            }

            // Preparar la consulta para insertar el registro
            String query = "INSERT INTO Usuarios (Cedula, nombre, apellido, correo_electronico, contraseña, tipo_usuario) VALUES (?,?,?,?,md5(?),?);";
            PreparedStatement guardar = con.prepareStatement(query);
            guardar.setInt(1, Integer.parseInt(CedulaText.getText()));
            guardar.setString(2, NombreText.getText());
            guardar.setString(3, ApellidoText.getText());
            guardar.setString(4, correo);
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

    /**
     * Valida la dirección de correo electrónico utilizando una expresión regular.
     * <p>
     * Este método verifica si la dirección de correo electrónico ingresada cumple con el formato estándar de correos electrónicos.
     * </p>
     *
     * @param correo La dirección de correo electrónico a validar.
     * @return {@code true} si la dirección de correo electrónico es válida; {@code false} en caso contrario.
     */
    private boolean esCorreoValido(String correo) {
        // Expresión regular para validar correos electrónicos
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    /**
     * Inicializa y configura la ventana de registro.
     * <p>
     * Configura el {@code JFrame} para que se cierre al hacer clic en el botón de cierre, establece el tamaño de la ventana
     * y la hace visible.
     * </p>
     */
    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }
}
