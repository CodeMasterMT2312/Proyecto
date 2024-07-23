package pantallasPrincipales;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cartelera extends JFrame {
    private JPanel CarteleraPanel;
    private JButton LOGOUTButton;
    private JButton reservarButacasButton;
    private JTextArea SinopsisText1;
    private JButton reservarButacasButton1;
    private JTextArea SinopsisText2;
    private JButton reservarButacasButton2;
    private JTextArea SinopsisText3;
    private JButton reservarButacasButton3;
    private JButton reservarButacasButton4;
    private JButton reservarButacasButton5;
    private JButton reservarButacasButton6;
    private JButton reservarButacasButton7;
    private JTextArea SinopsisText4;
    private JTextArea SinopsisText5;
    private JTextArea SinopsisText6;
    private JTextArea SinopsisText7;
    private JTextArea SinopsisText8;
    private JLabel LabelTitulo1;
    private JLabel LabelTitulo2;
    private JLabel LabelTitulo3;
    private JLabel LabelTitulo4;
    private JLabel LabelTitulo5;
    private JLabel LabelTitulo6;
    private JLabel LabelTitulo7;
    private JLabel LabelTitulo8;
    private JLabel RestricLabel1;
    private JLabel RestricLabel2;
    private JLabel RestricLabel3;
    private JLabel RestricLabel4;
    private JLabel RestricLabel5;
    private JLabel RestricLabel6;
    private JLabel RestricLabel7;
    private JLabel RestricLabel8;
    private JLabel DuracionLabel1;
    private JLabel DuracionLabel2;
    private JLabel DuracionLabel3;
    private JLabel DuracionLabel4;
    private JLabel DuracionLabel5;
    private JLabel DuracionLabel6;
    private JLabel DuracionLabel7;
    private JLabel DuracionLabel8;

    public Cartelera() {
        super("Cartelera");
        setContentPane(CarteleraPanel);

        reservarButacasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reserva reserva1 = new Reserva();
                reserva1.iniciar();
                dispose();
            }
        });
    }
    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,700);
        setVisible(true);
    }
}
