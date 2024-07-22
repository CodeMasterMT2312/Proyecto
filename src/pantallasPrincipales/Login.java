package pantallasPrincipales;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                Cartelera cart = new Cartelera();
                cart.iniciar();
                dispose();
            }
        });
    }

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }
}
