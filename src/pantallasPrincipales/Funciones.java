package pantallasPrincipales;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Funciones extends JFrame {
    private JTextArea textArea1;
    private JButton button1;
    private JButton reservarButton;
    private JLabel Funciones;
    private JPanel FuncionPanel;

    public Funciones() {
        super("Funciones");
        setContentPane(FuncionPanel);
        reservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reserva reser = new Reserva();
                reser.iniciar();
                dispose();
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cartelera cart = new Cartelera();
                cart.iniciar();
                dispose();
                try {
                    cart.MostrarCartelera();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,700);
        setVisible(true);
    }
}
