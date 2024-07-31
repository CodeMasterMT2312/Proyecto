package pantallasPrincipales.AdminPantallas.PantallasAux;

import pantallasPrincipales.AdminPantallas.MenuAdmin;
import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;


public class ActualizarImagenes extends  JFrame {
    private JButton button1;
    private JTextField textField1;
    private JComboBox CampoBox;
    private JTextField CambioText;
    private JButton examinarButton;
    private JButton actualizarButton;
    private JPanel ActImgPanel;
    private File imagenFile;

    public ActualizarImagenes() {
        super("Actualizar Imagenes");
        setContentPane(ActImgPanel);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin MenAd = new MenuAdmin();
                MenAd.iniciar();
                dispose();
            }
        });
        examinarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                examinarImagen();
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ActualizarImagen();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,700);
        setVisible(true);
    }

    public void ActualizarImagen() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        try (conn2) {
            String id = textField1.getText();
            String campo = (String) CampoBox.getSelectedItem();
            String valor = CambioText.getText();

            if (id.isEmpty() || campo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos");
                return;
            }

            String query;
            PreparedStatement guardar;

            // Determinar el tipo de dato y preparar la consulta
            switch (campo) {
                case "Nombre_imagen":
                    query = "UPDATE ImgPeliculas SET " + campo + " = ? WHERE id_imagen = ?";
                    guardar = conn2.prepareStatement(query);
                    guardar.setString(1, valor);
                    break;
                case "imagen":
                    if (imagenFile == null) {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar un archivo de imagen");
                        return;
                    }
                    query = "UPDATE ImgPeliculas SET " + campo + " = ? WHERE id_imagen = ?";
                    guardar = conn2.prepareStatement(query);
                    FileInputStream fis = new FileInputStream(imagenFile);
                    guardar.setBinaryStream(1, fis, (int) imagenFile.length());
                    break;
                case "id_pelicula":
                    query = "UPDATE ImgPeliculas SET " + campo + " = ? WHERE id_imagen = ?";
                    guardar = conn2.prepareStatement(query);
                    guardar.setInt(1, Integer.parseInt(valor));
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Campo no válido");
                    return;
            }

            guardar.setInt(2, Integer.parseInt(id));
            int filasAfectadas = guardar.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Imagen actualizada exitosamente");
                textField1.setText("");
                CambioText.setText("");
                CampoBox.setSelectedIndex(0);
                imagenFile = null;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la imagen con ese ID");
            }
            guardar.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar la imagen");
        }
    }

    public void examinarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            imagenFile = fileChooser.getSelectedFile();
            CambioText.setText(imagenFile.getName()); // Muestra el nombre del archivo en el JTextField
        }
    }
}
