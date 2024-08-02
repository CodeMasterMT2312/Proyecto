package pantallasPrincipales.AdminPantallas;

import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * La clase {@code DelUser} proporciona una interfaz gráfica para eliminar un usuario de la base de datos.
 * Extiende {@code JFrame} y utiliza un panel de contenido {@code DelUserPanel} con botones y un campo de texto.
 */

public class DelUser extends JFrame {
    private JButton borrarButton;
    private JButton button3;
    private JTextField CedulaText;
    private JPanel DelUserPanel;

    /**
     * Constructor de la clase {@code DelUser}.
     * Inicializa la ventana con el título "Borrar Usuario" y configura los manejadores de eventos para los botones.
     */
    public DelUser() {
        super("Borrar Usuario");
        setContentPane(DelUserPanel);
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdministrador DelMenu = new MenuAdministrador();
                DelMenu.iniciar();
                dispose();
            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BorrarUser();
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
     * Elimina un usuario de la base de datos según el número de cédula ingresado en el campo de texto.
     *
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void BorrarUser() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        try(conn2){
            String cedula = CedulaText.getText();
            String query = "DELETE FROM Usuarios WHERE cedula =?;";
            PreparedStatement stmt = conn2.prepareStatement(query);
            stmt.setString(1, cedula);
            int rs = stmt.executeUpdate();
            if(rs > 0){
                JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");
            }else{
                JOptionPane.showMessageDialog(null, "Usuario no encontrado");
            }
            stmt.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos");
        }
        conn2.close();
    }
}
