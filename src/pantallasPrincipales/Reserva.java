package pantallasPrincipales;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reserva extends JFrame {
    private JTextField textField1;
    private JButton siguienteButton;
    private JPanel ReservaPanel;

    public Reserva() {
        super("Reservar Butacas");
        setContentPane(ReservaPanel);
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }
}
