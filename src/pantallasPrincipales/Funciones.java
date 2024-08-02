package pantallasPrincipales;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * La clase {@code Funciones} extiende {@code JFrame} y representa la ventana que muestra las funciones de las películas disponibles.
 * <p>
 * Esta ventana permite a los usuarios ver información sobre las funciones disponibles para las películas y realizar reservas.
 * </p>
 */
public class Funciones extends JFrame {
    private JTextArea FuncionesArea;
    private JButton button1;
    private JButton reservarButton;
    private JLabel Funciones;
    private JPanel FuncionPanel;

    /**
     * Constructor de la clase {@code Funciones}.
     * <p>
     * Inicializa la ventana de funciones y configura los botones. El botón de reserva abre la ventana de reserva al hacer clic,
     * y el otro botón regresa a la ventana de cartelera.
     * </p>
     */
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
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al mostrar la cartelera");
                }
            }
        });
    }

    /**
     * Muestra la información sobre las funciones de las películas en el área de texto.
     * <p>
     * Este método consulta la base de datos para obtener la información de las funciones y sus películas correspondientes,
     * y actualiza el área de texto con los detalles de cada función.
     * </p>
     *
     * @throws SQLException Si ocurre un error al conectar o consultar la base de datos.
     */
    public void MostrarFunciones() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        try (conn2) {
            String queryFunciones = "SELECT * FROM Funciones;";
            Statement stmtFunciones = conn2.createStatement();
            ResultSet rsFunciones = stmtFunciones.executeQuery(queryFunciones);

            // Limpiar el área de texto antes de mostrar la información
            FuncionesArea.setText("");

            while (rsFunciones.next()) {
                int idPelicula = rsFunciones.getInt("id_pelicula");

                // Consulta adicional para obtener el título de la película
                String queryTitulo = "SELECT titulo FROM Peliculas WHERE id_pelicula = ?";
                PreparedStatement stmtTitulo = conn2.prepareStatement(queryTitulo);
                stmtTitulo.setInt(1, idPelicula);
                ResultSet rsTitulo = stmtTitulo.executeQuery();

                String tituloPelicula = "";
                if (rsTitulo.next()) {
                    tituloPelicula = rsTitulo.getString("titulo");
                }
                rsTitulo.close();

                // Mostrar los datos en el área de texto
                FuncionesArea.append("--------------------\n");
                FuncionesArea.append("ID: " + rsFunciones.getInt("id_funcion") + "\n");
                FuncionesArea.append("Pelicula: " + tituloPelicula + "\n");
                FuncionesArea.append("Sala: " + rsFunciones.getInt("id_sala") + "\n");
                FuncionesArea.append("Fecha: " + rsFunciones.getString("fecha") + "\n");
                FuncionesArea.append("Hora: " + rsFunciones.getString("hora") + "\n");
                FuncionesArea.append("Idioma: " + rsFunciones.getString("idioma") + "\n");
                FuncionesArea.append("Subtitulos: " + rsFunciones.getBoolean("subtitulos") + "\n");
                FuncionesArea.append("--------------------\n\n");
            }
            rsFunciones.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Inicializa y configura la ventana de funciones.
     * <p>
     * Configura el {@code JFrame} para que se cierre al hacer clic en el botón de cierre, establece el tamaño de la ventana
     * y la hace visible.
     * </p>
     */
    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,700);
        setVisible(true);
    }
}
