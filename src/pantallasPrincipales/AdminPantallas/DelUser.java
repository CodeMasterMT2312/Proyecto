package pantallasPrincipales.AdminPantallas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DelUser extends JFrame {
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JTextField textField1;
    private JPanel DelUserPanel;

    public DelUser() {
        super("Borrar Usuario");
        setContentPane(DelUserPanel);
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin DelMenu = new MenuAdmin();
                DelMenu.iniciar();
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
