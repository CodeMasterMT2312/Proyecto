package pantallasPrincipales.AdminPantallas.PantallasAux;

import pantallasPrincipales.AdminPantallas.MenuAdmin;
import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BorrarImagen extends JFrame {
    private JButton button1;
    private JTextField IDImagen;
    private JButton borrarButton;
    private JPanel BorarIMGPanel;

    public BorrarImagen() {
        super("Borrar Imagen");
        setContentPane(BorarIMGPanel);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin menuAdmin = new MenuAdmin();
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

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }

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
