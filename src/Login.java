import javax.swing.*;

public class Login extends JFrame {
    private JTextField UserText;
    private JPasswordField passText;
    private JButton iniciarSesionButton;
    private JButton crearCuentaButton;
    private JPanel loginPanel;

    public Login() {
        super("Inicio de Sesion");
        setContentPane(loginPanel);
    }

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }
}
