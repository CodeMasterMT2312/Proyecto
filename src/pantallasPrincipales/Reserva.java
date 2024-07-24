package pantallasPrincipales;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Reserva extends JFrame {
    private JTextField NumAsiento;
    private JButton siguienteButton;
    private JPanel ReservaPanel;
    private JTextField BoletosCantidad;
    private JButton button1;
    private JTextField IDPelicula;

    public Reserva() {
        super("Reservar Butacas");
        setContentPane(ReservaPanel);
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Funciones funci = new Funciones();
                funci.iniciar();
                dispose();
            }
        });
    }
    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650,600);
        setVisible(true);
    }
}
