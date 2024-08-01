package pantallasPrincipales.AdminPantallas;

import pantallasPrincipales.AdminPantallas.PantallasAux.*;
import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MenuAdministrador extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel AdminPanel;
    private JButton agregarPeliculaButton;
    private JButton agregarImagenButton;
    private JButton agregarFuncionButton;
    private JButton actualizarPeliculasButton;
    private JButton actualizarFuncionButton;
    private JButton actualizarImagenButton;
    private JButton actualizarUsuariosButton;
    private JButton borrarUsuarioButton;
    private JButton eliminarFuncionButton;
    private JButton eliminarImagenButton;
    private JButton eliminarPeliculaButton;
    private JButton verBDDButton;
    private JTextArea VisuArea;
    private JButton examinarButton;
    private JTextField ExamPath;
    private JButton generarEstadisticasButton;

    public MenuAdministrador() {
        super("Menu Administrador");
        setContentPane(AdminPanel);
        verBDDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    VisualizarBDD();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        generarEstadisticasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    GenEstadisticas();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        examinarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    ExamPath.setText(selectedFile.getAbsolutePath());
                }
            }
        });
        agregarPeliculaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Create Crear = new Create();
                Crear.iniciar();
                dispose();
            }
        });
        agregarImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarImagen AgIMG = new AgregarImagen();
                AgIMG.iniciar();
                dispose();
            }
        });
        agregarFuncionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearFunciones CrFun = new CrearFunciones();
                CrFun.iniciar();
                dispose();
            }
        });
        actualizarPeliculasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActualizarPeliculas ActPeli = new ActualizarPeliculas();
                ActPeli.iniciar();
                dispose();
            }
        });
        actualizarFuncionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActualizarFunciones ActFun = new ActualizarFunciones();
                ActFun.iniciar();
                dispose();
            }
        });
        actualizarImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActualizarImagenes ActImg = new ActualizarImagenes();
                ActImg.iniciar();
                dispose();
            }
        });
        actualizarUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActualizarUsuarios ActUser = new ActualizarUsuarios();
                ActUser.iniciar();
                dispose();
            }
        });
        borrarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DelUser delUser = new DelUser();
                delUser.iniciar();
                dispose();
            }
        });
        eliminarFuncionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BorrarFuncion delFuncion = new BorrarFuncion();
                delFuncion.iniciar();
                dispose();
            }
        });
        eliminarImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BorrarImagen DelImg = new BorrarImagen();
                DelImg.iniciar();
                dispose();
            }
        });
        eliminarPeliculaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Delete DelPel = new Delete();
                DelPel.iniciar();
                dispose();
            }
        });
    }
    // Metodo de Visulizacion de la Base de Datos
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

    // Metodo para Generar Estadisticas
    public void GenEstadisticas() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();

        try (conn2) {
            // Obtener la ruta del archivo del campo ExamPath
            String filePath = ExamPath.getText();

            if (filePath.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione una ruta de archivo.");
                return;
            }

            String Query = "SELECT * FROM Estadisticas;";
            Statement stmt = conn2.createStatement();
            ResultSet rs = stmt.executeQuery(Query);

            // Crear el FileWriter con la ruta del archivo seleccionada por el usuario
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);

            // Escribir los nombres de las columnas en el archivo
            pw.println(String.format("%-15s %-15s %-20s %-20s %-10s",
                    "id_estadistica", "id_funcion", "asientos_totales", "asientos_reservados", "fecha"));

            // Escribir los datos en el archivo en formato tabular
            while (rs.next()) {
                int idEstadistica = rs.getInt("id_estadistica");
                int idFuncion = rs.getInt("id_funcion");
                int asientosTotales = rs.getInt("asientos_totales");
                int asientosReservados = rs.getInt("asientos_reservados");
                java.sql.Date fecha = rs.getDate("fecha");

                // Ajustar el formato de los datos para alinear las columnas
                pw.println(String.format("%-15d %-15d %-20d %-20d %-10s",
                        idEstadistica, idFuncion, asientosTotales, asientosReservados, fecha));
            }

            pw.close();
            JOptionPane.showMessageDialog(null, "Estadísticas exportadas con éxito.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar estadísticas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Metodo de Inicio
    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,400);
        setVisible(true);
    }
}
