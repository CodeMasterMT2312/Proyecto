package pantallas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registro extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JComboBox comboBox1;
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
    }

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }
}
