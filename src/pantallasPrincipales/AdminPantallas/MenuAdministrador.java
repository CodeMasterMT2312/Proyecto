package pantallasPrincipales.AdminPantallas;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.*;
import pantallasPrincipales.AdminPantallas.PantallasAux.*;
import pantallasPrincipales.CONEXION;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;

/**
 * La clase {@code MenuAdministrador} es una interfaz gráfica de usuario (GUI) para el menú de administración del sistema.
 * Permite a los administradores realizar diversas operaciones como agregar, actualizar, eliminar y visualizar datos relacionados con
 * películas, funciones, imágenes, usuarios y estadísticas.
 */
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

    /**
     * Constructor de la clase {@code MenuAdministrador}. Inicializa el panel y los componentes de la interfaz gráfica de usuario,
     * y asigna los manejadores de eventos para los botones.
     */
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
        examinarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    GenPDF();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
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

    /**
     * Visualiza los datos de la base de datos en una tabla según la opción seleccionada en el {@code JComboBox} {@code CompletoBox}.
     *
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
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

    /**
     * Genera un reporte de estadísticas en formato PDF y lo guarda en el directorio de descargas del usuario.
     *
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    // Metodo para Generar Estadisticas
    public void GenPDF() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        try (conn2) {
            String ruta = System.getProperty("user.home");
            PdfReader reader = new PdfReader("Formatos/FormatoCine.pdf"); // Ruta al PDF de plantilla
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(ruta + "/Downloads/ReporteEstadisticas.pdf"));

            // Obtener el contenido de la primera página
            PdfContentByte canvas = stamper.getOverContent(1);

            // Crear una tabla PDF y configurarla
            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("ID Estadistica");
            tabla.addCell("ID Funcion");
            tabla.addCell("Asientos Totales");
            tabla.addCell("Asientos reservados");
            tabla.addCell("Fecha");

            String Query = "SELECT * FROM Estadisticas;";
            Statement stmt = conn2.createStatement();
            ResultSet rs = stmt.executeQuery(Query);

            while (rs.next()) {
                tabla.addCell(String.valueOf(rs.getInt("id_estadistica")));
                tabla.addCell(String.valueOf(rs.getInt("id_funcion")));
                tabla.addCell(String.valueOf(rs.getInt("asientos_totales")));
                tabla.addCell(String.valueOf(rs.getInt("asientos_reservados")));
                tabla.addCell(String.valueOf(rs.getDate("fecha")));
            }

            // Determinar el espacio ocupado por el formato
            // Ajustar la posición y el tamaño de la tabla en la página

            // Aquí puedes ajustar los valores para que se alineen con tu formato
            float marginLeft = 50;
            float marginRight = 50;
            float marginTop = 225; // Espacio ocupado por el formato (ajustar según sea necesario)
            float marginBottom = 50;
            float pageWidth = PageSize.A4.getWidth();
            float pageHeight = PageSize.A4.getHeight();
            float contentWidth = pageWidth - marginLeft - marginRight;
            float contentHeight = pageHeight - marginTop - marginBottom;

            // Ajustar la posición de la tabla
            ColumnText ct = new ColumnText(canvas);
            ct.setSimpleColumn(marginLeft, marginBottom, marginLeft + contentWidth, marginBottom + contentHeight);
            ct.addElement(tabla);
            ct.go();

            stamper.close();
            reader.close();
            JOptionPane.showMessageDialog(null, "Reporte generado con éxito en PDF.");
        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar PDF: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conn2.close();
        }
    }

    /**
     * Visualiza el registro en la tabla {@code IDVisu} según el ID proporcionado en {@code IdTabla}.
     *
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
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

    /**
     * Inicializa el Jframe
     */
    // Metodo de Inicio
    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,400);
        setVisible(true);
    }
}
