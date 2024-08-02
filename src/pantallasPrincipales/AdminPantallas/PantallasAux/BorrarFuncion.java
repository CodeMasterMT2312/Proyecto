package pantallasPrincipales.AdminPantallas.PantallasAux;

import pantallasPrincipales.AdminPantallas.MenuAdministrador;
import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * La clase {@code BorrarFuncion} proporciona una interfaz gráfica para eliminar registros de funciones en la base de datos.
 * Extiende {@code JFrame} y utiliza un panel de contenido {@code FuncionesDelPanel} con un campo de texto y botones para borrar una función.
 */
public class BorrarFuncion extends JFrame {
    private JButton button1;
    private JTextField FuncionID;
    private JButton borrarButton;
    private JPanel FuncionesDelPanel;

    /**
     * Constructor de la clase {@code BorrarFuncion}.
     * Inicializa la ventana con el título "Borrar Función" y configura los manejadores de eventos para los botones.
     */
    public BorrarFuncion() {
        super("Borrar Función");
        setContentPane(FuncionesDelPanel);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdministrador admin = new MenuAdministrador();
                admin.iniciar();
                dispose();
            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BorrarFuncion();
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
     * Elimina una función de la base de datos según el ID ingresado en el campo de texto.
     * Verifica que el ID sea válido y realiza la operación de eliminación en la tabla {@code Funciones}.
     *
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void BorrarFuncion() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        try(conn2){
            int ID = Integer.parseInt(FuncionID.getText());
            String query = "DELETE FROM Funciones WHERE id_funcion =?;";
            PreparedStatement pstmt = conn2.prepareStatement(query);
            pstmt.setInt(1, ID);
            int rs = pstmt.executeUpdate();
            if(rs > 0){
                JOptionPane.showMessageDialog(null, "Función eliminada correctamente");
            }else{
                JOptionPane.showMessageDialog(null, "Función no encontrada");
            }
            pstmt.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos");
        }
        conn2.close();
    }
}
