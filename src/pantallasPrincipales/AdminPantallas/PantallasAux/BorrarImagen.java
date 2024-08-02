package pantallasPrincipales.AdminPantallas.PantallasAux;

import pantallasPrincipales.AdminPantallas.MenuAdministrador;
import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * La clase {@code BorrarImagen} proporciona una interfaz gráfica para eliminar registros de imágenes en la base de datos.
 * Extiende {@code JFrame} y utiliza un panel de contenido {@code BorarIMGPanel} con un campo de texto y botones para borrar una imagen.
 */

public class BorrarImagen extends JFrame {
    private JButton button1;
    private JTextField IDImagen;
    private JButton borrarButton;
    private JPanel BorarIMGPanel;

    /**
     * Constructor de la clase {@code BorrarImagen}.
     * Inicializa la ventana con el título "Borrar Imagen" y configura los manejadores de eventos para los botones.
     */
    public BorrarImagen() {
        super("Borrar Imagen");
        setContentPane(BorarIMGPanel);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdministrador menuAdmin = new MenuAdministrador();
                menuAdmin.iniciar();
                dispose();
            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BorrarImagen();
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
     * Elimina una imagen de la base de datos según el ID ingresado en el campo de texto.
     * Verifica que el ID sea válido y realiza la operación de eliminación en la tabla {@code ImgPeliculas}.
     *
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void BorrarImagen() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        try (conn2) {
            String id = IDImagen.getText();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un ID");
                return;
            }
            String query = "DELETE FROM ImgPeliculas WHERE id_imagen =?;";
            PreparedStatement stmt = conn2.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(id));
            int rs = stmt.executeUpdate();
            if(rs > 0){
                JOptionPane.showMessageDialog(null, "Imagen eliminada correctamente");
            }else{
                JOptionPane.showMessageDialog(null, "Imagen no encontrada");
            }
            stmt.close();
            JOptionPane.showMessageDialog(null, "Imagen eliminada correctamente");
            IDImagen.setText("");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
            e.printStackTrace();
        }
        conn2.close();
    }
}
