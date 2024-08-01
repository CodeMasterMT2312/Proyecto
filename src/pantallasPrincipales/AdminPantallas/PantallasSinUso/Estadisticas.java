package pantallasPrincipales.AdminPantallas;

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
}
