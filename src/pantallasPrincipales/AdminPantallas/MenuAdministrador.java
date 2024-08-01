package pantallasPrincipales.AdminPantallas;

import pantallasPrincipales.AdminPantallas.PantallasAux.*;
import pantallasPrincipales.CONEXION;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;

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
    private JButton examinarButton;
    private JTextField ExamPath;
    private JButton generarEstadisticasButton;
    private JTabbedPane tabbedPane2;
    private JComboBox CompletoBox;
    private JComboBox IDCombo;
    private JTextField IdTabla;
    private JButton visualizarRegistroButton;
    private JTable IDVisu;
    private JTable VisuArea;

    public MenuAdministrador() {
        super("Menu Administrador");
        setContentPane(AdminPanel);
        IDVisu.setAutoCreateRowSorter(true);
        JTableHeader header = IDVisu.getTableHeader();
        header.setVisible(true);
        IDVisu.setTableHeader(header);
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
        visualizarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    VisualizarRegistro();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    // Metodo de Visulizacion de la Base de Datos
    public void VisualizarBDD() throws SQLException {
        // Obtener la opción seleccionada en el ComboBox
        String seleccion = (String) CompletoBox.getSelectedItem();
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        try (conn2) {
            String query = "";
            // Seleccionar la consulta SQL según la opción seleccionada
            switch (seleccion) {
                case "Usuarios":
                    query = "SELECT * FROM `Usuarios`;";
                    break;
                case "Peliculas":
                    query = "SELECT id_pelicula, titulo, director, genero, duracion, fecha_estreno, clasificacion, reparto, descripcion FROM `Peliculas`;";
                    break;
                case "Reservas":
                    query = "SELECT * FROM `Reservas`;";
                    break;
                case "Salas":
                    query = "SELECT * FROM `Salas`;";
                    break;
                case "Funciones":
                    query = "SELECT * FROM `Funciones`;";
                    break;
                case "Estadisticas":
                    query = "SELECT * FROM `Estadisticas`;";
                    break;
                case "Asientos_Reservas":
                    query = "SELECT * FROM `Asientos_Reservas`;";
                    break;
                case "Asientos":
                    query = "SELECT * FROM `Asientos`;";
                    break;
                default:
                    return; // Si no se selecciona nada válido, salir del método
            }

            Statement stmt = conn2.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int columnCount = rs.getMetaData().getColumnCount();

            // Crear modelo de tabla
            DefaultTableModel model = new DefaultTableModel();

            // Obtener los nombres de las columnas y añadirlos al modelo
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(rs.getMetaData().getColumnName(i));
            }

            // Obtener los datos de las filas y añadirlos al modelo
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                model.addRow(rowData);
            }
            // Establecer el modelo en el JTable (VisuArea)
            VisuArea.setModel(model);
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

    // Metodo para ver registro por ID
    public void VisualizarRegistro() throws SQLException {
        String seleccion = (String) IDCombo.getSelectedItem();
        String idStr = IdTabla.getText();

        if (idStr == null || idStr.isEmpty()) {
            System.out.println("ID no proporcionado o está vacío");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("ID no es un número válido");
            return;
        }

        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();

        if (conn2 == null) {
            System.out.println("Conexión a la base de datos fallida");
            return;
        }

        try (conn2) {
            String query = "";
            switch (seleccion) {
                case "Usuarios":
                    query = "SELECT * FROM `Usuarios` WHERE Cedula = ?;";
                    break;
                case "Peliculas":
                    query = "SELECT id_pelicula, titulo, director, genero, duracion, fecha_estreno, clasificacion, reparto, descripcion FROM `Peliculas` WHERE id_pelicula = ?;";
                    break;
                case "Salas":
                    query = "SELECT * FROM `Salas` WHERE id_sala = ?;";
                    break;
                case "Reservas":
                    query = "SELECT * FROM `Reservas` WHERE id_reserva = ?;";
                    break;
                case "Funciones":
                    query = "SELECT * FROM `Funciones` WHERE id_funcion = ?;";
                    break;
                case "Estadisticas":
                    query = "SELECT * FROM `Estadisticas` WHERE id_estadistica = ?;";
                    break;
                case "Asientos_Reservas":
                    query = "SELECT * FROM `Asientos_Reservas` WHERE id_reserva = ?;";
                    break;
                case "Asientos":
                    query = "SELECT * FROM `Asientos` WHERE id_asiento = ?;";
                    break;
                default:
                    System.out.println("Selección no válida");
                    return;
            }

            PreparedStatement pstmt = conn2.prepareStatement(query);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No se encontraron resultados");
                return;
            }

            DefaultTableModel model = new DefaultTableModel();

            try {
                int columnCount = rs.getMetaData().getColumnCount();

                // Obtener los nombres de las columnas y añadirlos al modelo
                for (int i = 1; i <= columnCount; i++) {
                    model.addColumn(rs.getMetaData().getColumnName(i));
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener los nombres de las columnas: " + e.getMessage());
                e.printStackTrace();
                return;
            }

            try {
                // Obtener los datos de las filas y añadirlos al modelo
                while (rs.next()) {
                    Object[] rowData = new Object[rs.getMetaData().getColumnCount()];
                    for (int i = 1; i <= rowData.length; i++) {
                        rowData[i - 1] = rs.getObject(i);
                    }
                    model.addRow(rowData);
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener los datos de las filas: " + e.getMessage());
                e.printStackTrace();
            }

            // Establecer el modelo en el JTable
            IDVisu.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al ejecutar la consulta SQL");
        }
    }

    // Metodo de Inicio
    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,400);
        setVisible(true);
    }
}
