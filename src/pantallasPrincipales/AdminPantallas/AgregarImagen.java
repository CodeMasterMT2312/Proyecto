package pantallasPrincipales.AdminPantallas;

import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.sql.*;

public class AgregarImagen extends JFrame {
    private JTextField ID_Pelicula;
    private JButton examinarButton;
    private JButton guardarButton;
    private JButton button3;
    private JPanel ImagenPanel;
    private JTextField NomImagen;
    private JLabel ImagenVisualizar;


    public AgregarImagen() {
        super("Agregar Imagen");
        setContentPane(ImagenPanel);
        ImagenVisualizar.setPreferredSize(new Dimension(300, 200));
        examinarButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                examinarImagen();
            }
        });
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AgregarIMG();
                } catch (SQLException | FileNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin menuAdmin = new MenuAdmin();
                menuAdmin.iniciar();
                dispose();
            }
        });
    }

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,700);
        setVisible(true);
    }

    public void examinarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String rutaImagen = selectedFile.getAbsolutePath();
            NomImagen.setText(rutaImagen);
            mostrarImagenEnLabel(rutaImagen);
        }
    }

    private void mostrarImagenEnLabel(String rutaImagen) {
        final ImageIcon[] imageIcon = { new ImageIcon(rutaImagen) };
        final Image image = imageIcon[0].getImage();

        // Asegurarse de que el JLabel tenga un tamaño definido
        if (ImagenVisualizar.getWidth() > 0 && ImagenVisualizar.getHeight() > 0) {
            // Escalar la imagen para ajustarla al JLabel
            Image newimg = image.getScaledInstance(ImagenVisualizar.getWidth(), ImagenVisualizar.getHeight(), java.awt.Image.SCALE_SMOOTH);
            imageIcon[0] = new ImageIcon(newimg);
            ImagenVisualizar.setIcon(imageIcon[0]);
        } else {
            // Escuchar el cambio de tamaño del JLabel y ajustar la imagen
            ImagenVisualizar.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    Image newimg = image.getScaledInstance(ImagenVisualizar.getWidth(), ImagenVisualizar.getHeight(), java.awt.Image.SCALE_SMOOTH);
                    imageIcon[0].setImage(newimg);
                    ImagenVisualizar.setIcon(imageIcon[0]);
                    // Asegurarse de que el JLabel se repinte
                    ImagenVisualizar.revalidate();
                    ImagenVisualizar.repaint();
                }
            });
        }

        // Asegurarse de que el JLabel se repinte si ya tiene un tamaño definido
        ImagenVisualizar.revalidate();
        ImagenVisualizar.repaint();
    }

    public void AgregarIMG() throws SQLException, FileNotFoundException {
        String rutaImagen = NomImagen.getText();
        if (rutaImagen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una imagen primero.");
            return;
        }

        File selectedFile = new File(rutaImagen);
        String nombreImagen = selectedFile.getName();
        FileInputStream fis = new FileInputStream(selectedFile);

        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        try (conn2) {
            String query = "INSERT INTO ImgPeliculas(id_pelicula, Nombre_imagen, imagen) VALUES(?,?,?);";
            PreparedStatement ps = conn2.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(ID_Pelicula.getText()));
            ps.setString(2, nombreImagen);
            ps.setBinaryStream(3, fis, (int) selectedFile.length());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Imagen guardada exitosamente.");
        }
    }
}
