package pantallasPrincipales.AdminPantallas.PantallasAux;

import pantallasPrincipales.AdminPantallas.MenuAdministrador;
import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class BorrarFuncion extends JFrame {
    private JButton button1;
    private JTextField FuncionID;
    private JButton borrarButton;
    private JPanel FuncionesDelPanel;

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

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }

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
