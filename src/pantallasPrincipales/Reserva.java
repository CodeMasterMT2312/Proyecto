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
                Cartelera cartelera = new Cartelera();
                cartelera.iniciar();
                dispose();
                try {
                    cartelera.MostrarCartelera();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }
    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,550);
        setVisible(true);
    }
}
