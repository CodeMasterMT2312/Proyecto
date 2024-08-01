package pantallasPrincipales.AdminPantallas;

import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Delete extends JFrame {
    private JTextField ID_Pelicula;
    private JButton borrarUsuarioButton;
    private JButton Regresar;
    private JPanel DelPanel;

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

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }

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
