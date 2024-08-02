package pantallasPrincipales.AdminPantallas.PantallasAux;

import pantallasPrincipales.AdminPantallas.MenuAdministrador;
import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


/**
 * La clase {@code ActualizarPeliculas} proporciona una interfaz gráfica para actualizar registros de películas en la base de datos.
 * Extiende {@code JFrame} y utiliza un panel de contenido {@code ActuPeliPanel} con campos de texto, un combo box y botones para actualizar películas.
 */
public class ActualizarPeliculas extends JFrame {
    private JButton Regresar;
    private JTextField ID_Pelicula;
    private JButton actualizarButton;
    private JTextField CambiadoText;
    private JComboBox CampoCombo;
    private JPanel ActuPeliPanel;

    /**
     * Constructor de la clase {@code ActualizarPeliculas}.
     * Inicializa la ventana con el título "Actualizar Pelicula" y configura los manejadores de eventos para los botones.
     */
    public ActualizarPeliculas() {
        super("Actualizar Pelicula");
        setContentPane(ActuPeliPanel);
        Regresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdministrador user = new MenuAdministrador();
                user.iniciar();
                dispose();
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ActualizarPelicula();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Configura la ventana para su visualización.
     * Establece el tamaño, la operación de cierre y la visibilidad de la ventana.
     */
    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }

    /**
     * Actualiza una película en la base de datos según los valores ingresados en los campos de texto.
     * Verifica que todos los campos estén llenos y actualiza el registro correspondiente en la tabla {@code Peliculas}.
     *
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void ActualizarPelicula() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection conn2  = conn.conexion();
        try(conn2){
            String id = ID_Pelicula.getText();
            String campo = (String) CampoCombo.getSelectedItem();
            String valor = CambiadoText.getText();
            if (id.isEmpty() || campo.isEmpty() || valor.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos");
            }
            String query="UPDATE Peliculas SET " + campo + " = ? WHERE id_pelicula = ?";

            try (PreparedStatement guardar = conn2.prepareStatement(query)) {
                if (campo.equals("duracion")) {
                    // Para campos tipo int
                    guardar.setInt(1, Integer.parseInt(valor));
                } else {
                    // Para campos tipo String
                    guardar.setString(1, valor);
                }
                guardar.setInt(2, Integer.parseInt(id));
                int filasAfectadas = guardar.executeUpdate();
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Película actualizada exitosamente");
                    CambiadoText.setText("");
                    ID_Pelicula.setText("");
                    CampoCombo.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró la película con ese ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar la película");
        }
    }
}
