package pantallasPrincipales.AdminPantallas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Delete extends JFrame {
    private JTextField textField1;
    private JButton button1;
    private JButton borrarUsuarioButton;
    private JButton button3;
    private JPanel DelPanel;

    public Delete() {
        super("Delete");
        setContentPane(DelPanel);
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin mAdmin = new MenuAdmin();
                mAdmin.iniciar();
                dispose();
            }
        });
        borrarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DelUser boUser = new DelUser();
                boUser.iniciar();
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
