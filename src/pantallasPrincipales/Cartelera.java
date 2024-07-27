package pantallasPrincipales;
import pantallasPrincipales.AdminPantallas.MenuAdmin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class Cartelera extends JFrame {
    private JButton adquirirBoletosButton;
    private JTextArea CarteleraText;
    private JPanel CarteleraPanel;

    public Cartelera() {
        super("Cartelera");
        setContentPane(CarteleraPanel);
        adquirirBoletosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Funciones fun = new Funciones();
                fun.iniciar();
                try {
                    fun.MostrarFunciones();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Error al mostrar la cartelera");
                }
                dispose();
            }
        });
    }
    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,700);
        setVisible(true);
    }

    public void MostrarCartelera() throws SQLException {
        CONEXION c = new CONEXION();
        Connection conn = c.conexion();
        try{
            if(conn != null){
                String query = "Select id_pelicula,titulo,descripcion,genero,duracion,fecha_estreno,clasificacion from Peliculas;";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                CarteleraText.setText("");
                while(rs.next()){
                    CarteleraText.append("--------------------\n");
                    CarteleraText.append("ID Funcion: " + rs.getInt("id_pelicula") + "\n");
                    CarteleraText.append("Título: " + rs.getString("titulo") + "\n");
                    CarteleraText.append("Género: " + rs.getString("genero") + "\n");
                    CarteleraText.append("Duración: " + rs.getString("duracion") + " minutos\n");
                    CarteleraText.append("Fecha de estreno: " + rs.getString("fecha_estreno") + "\n");
                    CarteleraText.append("Clasificación: " + rs.getString("clasificacion") + "\n\n");
                    // Formatear la sinopsis para que se divida en varias líneas
                    String sinopsis = rs.getString("descripcion");
                    String formattedSinopsis = formatTextToMultiline(sinopsis, 80); // 80 es el número máximo de caracteres por línea
                    CarteleraText.append("Sinopsis:\n" + formattedSinopsis + "\n");
                }
            }
            conn.close();
        }catch(SQLException ex){
            System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Método para formatear texto en varias líneas
    private String formatTextToMultiline(String text, int maxLineLength) {
        StringBuilder formattedText = new StringBuilder();
        String[] words = text.split(" ");
        int currentLineLength = 0;

        for (String word : words) {
            // Si agregar la siguiente palabra excede la longitud máxima de línea, agrega un salto de línea
            if (currentLineLength + word.length() > maxLineLength) {
                formattedText.append("\n");
                currentLineLength = 0;
            }
            // Agrega la palabra al texto formateado
            formattedText.append(word).append(" ");
            currentLineLength += word.length() + 1; // +1 para el espacio
        }

        return formattedText.toString().trim();
    }
}
