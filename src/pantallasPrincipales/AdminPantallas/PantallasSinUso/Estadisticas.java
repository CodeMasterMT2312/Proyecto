package pantallasPrincipales.AdminPantallas.PantallasSinUso;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.*;
import pantallasPrincipales.CONEXION;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Estadisticas extends  JFrame {
    private JButton Regresar;
    private JButton examinarButton;
    private JTextField ExamPath;
    private JButton generarEstadisticasButton;
    private JPanel EstadisticasPanel;

    public Estadisticas() {
        super("Estadisticas");
        setContentPane(EstadisticasPanel);
        Regresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin menu = new MenuAdmin();
                menu.iniciar();
                dispose();
            }
        });
        generarEstadisticasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    GenPDF();
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
    }

    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }

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


    public static void main(String[] args) {
        Estadisticas estadisticas = new Estadisticas();
        estadisticas.iniciar();
    }
}
