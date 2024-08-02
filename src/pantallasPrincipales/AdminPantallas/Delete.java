package pantallasPrincipales.AdminPantallas;

import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * La clase {@code Delete} proporciona una interfaz gráfica para eliminar una película de la base de datos.
 * Extiende {@code JFrame} y utiliza un panel de contenido {@code DelPanel} con botones y un campo de texto.
 */
public class Delete extends JFrame {
    private JTextField ID_Pelicula;
    private JButton borrarUsuarioButton;
    private JButton Regresar;
    private JPanel DelPanel;

    /**
     * Constructor de la clase {@code Delete}.
     * Inicializa la ventana con el título "Delete" y configura los manejadores de eventos para los botones.
     */
    public Delete() {
        super("Delete");
        setContentPane(DelPanel);
        Regresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdministrador mAdmin = new MenuAdministrador();
                mAdmin.iniciar();
                dispose();
            }
        });
        borrarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BorrarPelicula();
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
     * Elimina una película de la base de datos según el ID ingresado en el campo de texto.
     *
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void BorrarPelicula() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        try(conn2){
            String id = ID_Pelicula.getText();
            String query = "DELETE FROM Peliculas WHERE id_pelicula =?;";
            PreparedStatement pstmt = conn2.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(id));
            int rs = pstmt.executeUpdate();
            if(rs > 0){
                JOptionPane.showMessageDialog(null, "Pelicula eliminada correctamente");
            }else{
                JOptionPane.showMessageDialog(null, "Pelicula no encontrada");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos");
            e.printStackTrace();
        }
    }
}
