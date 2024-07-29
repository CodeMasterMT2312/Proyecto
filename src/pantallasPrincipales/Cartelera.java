package pantallasPrincipales;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Cartelera extends JFrame {
    private JButton adquirirBoletosButton;
    private JPanel CarteleraPanel;
    private JPanel PanelInformacion; // Solo modificaremos este panel
    private JScrollPane PanelScroll;

    public Cartelera() {
        super("Cartelera");
        adquirirBoletosButton = new JButton("Adquirir Boletos");
        adquirirBoletosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Funciones fun = new Funciones();
                fun.iniciar();
                try {
                    fun.MostrarFunciones();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al mostrar la cartelera");
                }
                dispose();
            }
        });
        iniciar();
    }

    public void iniciar() {
        // Configura el JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLayout(new BorderLayout());

        // Inicialización del panel principal y el panel de información
        CarteleraPanel = new JPanel(new BorderLayout());
        PanelInformacion = new JPanel();
        PanelInformacion.setLayout(new BoxLayout(PanelInformacion, BoxLayout.Y_AXIS));
        PanelScroll = new JScrollPane(PanelInformacion); // Agregar PanelInformacion al JScrollPane

        // Configuración del panel principal
        setContentPane(CarteleraPanel);
        CarteleraPanel.add(PanelScroll, BorderLayout.CENTER); // Usar el JScrollPane en lugar del panel directamente

        // Agrega el botón al panel principal
        CarteleraPanel.add(adquirirBoletosButton, BorderLayout.SOUTH);

        setVisible(true); // Muestra el JFrame
    }

    public void MostrarCartelera() throws SQLException {
        CONEXION c = new CONEXION();
        Connection conn = c.conexion();
        try {
            if (conn != null) {
                String query = "SELECT id_pelicula, titulo, descripcion, genero, duracion, fecha_estreno, clasificacion FROM Peliculas;";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                // Limpiar y configurar PanelInformacion
                PanelInformacion.removeAll();
                PanelInformacion.setLayout(new BoxLayout(PanelInformacion, BoxLayout.Y_AXIS));

                while (rs.next()) {
                    JPanel peliculaPanel = new JPanel();
                    peliculaPanel.setLayout(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre los componentes

                    // Información de la película
                    JTextArea infoArea = new JTextArea();
                    infoArea.setText("--------------------\n");
                    infoArea.append("ID Funcion: " + rs.getInt("id_pelicula") + "\n");
                    infoArea.append("Título: " + rs.getString("titulo") + "\n");
                    infoArea.append("Género: " + rs.getString("genero") + "\n");
                    infoArea.append("Duración: " + rs.getString("duracion") + " minutos\n");
                    infoArea.append("Fecha de estreno: " + rs.getString("fecha_estreno") + "\n");
                    infoArea.append("Clasificación: " + rs.getString("clasificacion") + "\n\n");

                    // Formatear la sinopsis
                    String sinopsis = rs.getString("descripcion");
                    String formattedSinopsis = formatTextToMultiline(sinopsis, 80);
                    infoArea.append("Sinopsis:\n" + formattedSinopsis + "\n");

                    infoArea.setEditable(false); // Hacer que el área de texto no sea editable

                    // Configura la imagen
                    JLabel imagenLabel = new JLabel();
                    mostrarImagen(rs.getInt("id_pelicula"), imagenLabel, conn);

                    // Añadir la imagen y la información al panel de película
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.weightx = 0.3;
                    gbc.fill = GridBagConstraints.VERTICAL;
                    peliculaPanel.add(imagenLabel, gbc);

                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    gbc.weightx = 0.7;
                    gbc.fill = GridBagConstraints.BOTH;
                    peliculaPanel.add(infoArea, gbc);

                    PanelInformacion.add(peliculaPanel);
                }

                // Actualiza el panel
                CarteleraPanel.revalidate();
                CarteleraPanel.repaint();
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void mostrarImagen(int idPelicula, JLabel imagenLabel, Connection conn) throws SQLException {
        String query2 = "SELECT imagen FROM ImgPeliculas WHERE id_pelicula = ?;";
        PreparedStatement pstmt = conn.prepareStatement(query2);
        pstmt.setInt(1, idPelicula);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            byte[] imgBytes = rs.getBytes("imagen");
            if (imgBytes != null) {
                try {
                    ImageIcon imageIcon = new ImageIcon(imgBytes);
                    Image image = imageIcon.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH); // Ajusta el tamaño si es necesario
                    imagenLabel.setIcon(new ImageIcon(image));
                } catch (Exception e) {
                    e.printStackTrace();
                    imagenLabel.setIcon(null); // Limpia la imagen si ocurre un error
                }
            } else {
                imagenLabel.setIcon(null); // Limpia la imagen si no hay datos
            }
        }
    }

    private String formatTextToMultiline(String text, int maxLineLength) {
        StringBuilder formattedText = new StringBuilder();
        String[] words = text.split(" ");
        int currentLineLength = 0;

        for (String word : words) {
            if (currentLineLength + word.length() > maxLineLength) {
                formattedText.append("\n");
                currentLineLength = 0;
            }
            formattedText.append(word).append(" ");
            currentLineLength += word.length() + 1;
        }

        return formattedText.toString().trim();
    }
}
