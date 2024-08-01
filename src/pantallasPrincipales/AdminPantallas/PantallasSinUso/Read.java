package pantallasPrincipales.AdminPantallas.PantallasSinUso;

import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class Read extends JFrame {
    private JTextArea VisuArea;
    private JButton button1;
    private JPanel ReadPanel;

    public Read() {
        super("Leer");
        setContentPane(ReadPanel);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin menAd = new MenuAdmin();
                menAd.iniciar();
                dispose();
            }
        });
    }

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }

    public void VisualizarBDD() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        try(conn2) {
            String[] queries = {
                    "SELECT id_pelicula,titulo,director,genero,duracion,fecha_estreno,clasificacion,reparto,descripcion FROM `Peliculas`;",
                    "SELECT * FROM `Funciones`;",
                    "SELECT * FROM `Salas`;",
                    "SELECT * FROM `Usuarios`;"
            };
            Statement stmt = conn2.createStatement();
            StringBuilder sb = new StringBuilder();

            for (String query : queries) {
                ResultSet rs = stmt.executeQuery(query);
                int columnCount = rs.getMetaData().getColumnCount();

                sb.append("Tabla: ").append(rs.getMetaData().getTableName(1)).append("\n");

                // Obtener los nombres de las columnas
                for (int i = 1; i <= columnCount; i++) {
                    sb.append(rs.getMetaData().getColumnName(i)).append("\t");
                }
                sb.append("\n");

                // Obtener los datos de las filas
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        sb.append(rs.getString(i)).append("\t");
                    }
                    sb.append("\n");
                }
                sb.append("\n");
            }
            // Mostrar el contenido en el JTextArea
            VisuArea.setText(sb.toString());
        }
    }
}
