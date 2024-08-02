package pantallasPrincipales.AdminPantallas.PantallasAux;

import pantallasPrincipales.AdminPantallas.MenuAdministrador;
import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

/**
 * La clase {@code ActualizarImagenes} proporciona una interfaz gráfica para actualizar registros de imágenes en la base de datos.
 * Extiende {@code JFrame} y utiliza un panel de contenido {@code ActImgPanel} con campos de texto, un combo box, botones para examinar y actualizar imágenes, y un archivo de imagen.
 */
public class ActualizarImagenes extends  JFrame {
    private JButton button1;
    private JTextField textField1;
    private JComboBox CampoBox;
    private JTextField CambioText;
    private JButton examinarButton;
    private JButton actualizarButton;
    private JPanel ActImgPanel;
    private File imagenFile;

    /**
     * Constructor de la clase {@code ActualizarImagenes}.
     * Inicializa la ventana con el título "Actualizar Imágenes" y configura los manejadores de eventos para los botones.
     */
    public ActualizarImagenes() {
        super("Actualizar Imagenes");
        setContentPane(ActImgPanel);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdministrador MenAd = new MenuAdministrador();
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

    /**
     * Configura la ventana para su visualización.
     * Establece el tamaño, la operación de cierre y la visibilidad de la ventana.
     */
    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,700);
        setVisible(true);
    }

    /**
     * Actualiza una imagen en la base de datos según los valores ingresados en los campos de texto y el archivo de imagen seleccionado.
     * Verifica que todos los campos estén llenos y actualiza el registro correspondiente en la tabla {@code ImgPeliculas}.
     *
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
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

    /**
     * Abre un cuadro de diálogo para seleccionar un archivo de imagen.
     * Muestra el nombre del archivo seleccionado en el campo de texto {@code CambioText}.
     */
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
