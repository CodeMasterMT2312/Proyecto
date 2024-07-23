package pantallasPrincipales.AdminPantallas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Read extends JFrame {
    private JTextArea textArea1;
    private JButton button1;
    private JPanel ReadPanel;

    public Read() {
        super("Leer");
        setContentPane(ReadPanel);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin menAd = new MenuAdmin();
                menAd.iniciar();
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
