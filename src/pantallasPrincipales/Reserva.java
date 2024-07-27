package pantallasPrincipales;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Reserva extends JFrame {
    private JTextField NumAsiento;
    private JButton siguienteButton;
    private JPanel ReservaPanel;
    private JTextField BoletosCantidad;
    private JButton button1;
    private JTextField IDFuncion;
    private JTextField CedulaText;

    public Reserva() {
        super("Reservar Butacas");
        setContentPane(ReservaPanel);
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Funciones funci = new Funciones();
                funci.iniciar();
                dispose();
            }
        });
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    GuardarReserva();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void GuardarReserva() throws SQLException {
        CONEXION conn = new CONEXION();
        Connection conn2 = conn.conexion();
        boolean success = false;
        int idReserva = -1;

        try {
            conn2.setAutoCommit(false);

            // Obtener los datos de la interfaz de usuario
            int idUsuario = Integer.parseInt(CedulaText.getText());
            int idFuncion = Integer.parseInt(IDFuncion.getText());
            int cantidadBoletos = Integer.parseInt(BoletosCantidad.getText());
            String[] asientos = NumAsiento.getText().split("\\s*,\\s*"); // Eliminar espacios alrededor de las comas

            if (asientos.length < cantidadBoletos) {
                throw new IllegalArgumentException("Número de asientos proporcionados es menor que la cantidad de boletos.");
            }

            // Preparar la consulta para validar los asientos
            String validateQuery = "SELECT id_asiento FROM Asientos WHERE fila = ? AND numero = ? AND id_sala = (SELECT id_sala FROM Funciones WHERE id_funcion = ?)";
            PreparedStatement validateStmt = conn2.prepareStatement(validateQuery);

            // Verificar cada asiento
            for (String asiento : asientos) {
                String fila = asiento.substring(0, 1).toUpperCase(); // Obtener la fila (carácter inicial)
                int numero = Integer.parseInt(asiento.substring(1)); // Obtener el número de asiento

                validateStmt.setString(1, fila);
                validateStmt.setInt(2, numero);
                validateStmt.setInt(3, idFuncion);
                ResultSet rs = validateStmt.executeQuery();

                if (!rs.next()) {
                    throw new IllegalArgumentException("Uno o más asientos proporcionados no son válidos para esta función.");
                }
            }

            // Verificar si los asientos ya están reservados
            String checkReservedQuery = "SELECT id_asiento FROM Asientos_Reservas WHERE id_asiento IN (SELECT id_asiento FROM Asientos WHERE fila = ? AND numero = ? AND id_sala = (SELECT id_sala FROM Funciones WHERE id_funcion = ?))";
            PreparedStatement checkReservedStmt = conn2.prepareStatement(checkReservedQuery);

            for (String asiento : asientos) {
                String fila = asiento.substring(0, 1).toUpperCase(); // Obtener la fila (carácter inicial)
                int numero = Integer.parseInt(asiento.substring(1)); // Obtener el número de asiento

                checkReservedStmt.setString(1, fila);
                checkReservedStmt.setInt(2, numero);
                checkReservedStmt.setInt(3, idFuncion);
                ResultSet reservedRs = checkReservedStmt.executeQuery();

                if (reservedRs.next()) {
                    throw new IllegalArgumentException("Uno o más asientos ya están reservados.");
                }
            }

            // Crear una nueva reserva
            String createReservaQuery = "INSERT INTO Reservas(id_usuario, id_funcion) VALUES(?, ?)";
            PreparedStatement createReservaStmt = conn2.prepareStatement(createReservaQuery, Statement.RETURN_GENERATED_KEYS);
            createReservaStmt.setInt(1, idUsuario);
            createReservaStmt.setInt(2, idFuncion);
            createReservaStmt.executeUpdate();

            // Obtener el ID de la nueva reserva
            ResultSet generatedKeys = createReservaStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                idReserva = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Error al obtener el ID de la reserva.");
            }

            // Preparar la consulta para insertar en la tabla de relación
            String insertAsientosQuery = "INSERT INTO Asientos_Reservas(id_asiento, id_reserva) VALUES(?, ?)";
            PreparedStatement insertAsientosStmt = conn2.prepareStatement(insertAsientosQuery);

            // Insertar cada asiento reservado
            for (String asiento : asientos) {
                String fila = asiento.substring(0, 1).toUpperCase(); // Obtener la fila (carácter inicial)
                int numero = Integer.parseInt(asiento.substring(1)); // Obtener el número de asiento

                // Obtener el id_asiento basado en fila y número
                String getIdAsientoQuery = "SELECT id_asiento FROM Asientos WHERE fila = ? AND numero = ? AND id_sala = (SELECT id_sala FROM Funciones WHERE id_funcion = ?)";
                PreparedStatement getIdStmt = conn2.prepareStatement(getIdAsientoQuery);
                getIdStmt.setString(1, fila);
                getIdStmt.setInt(2, numero);
                getIdStmt.setInt(3, idFuncion);
                ResultSet idRs = getIdStmt.executeQuery();

                if (idRs.next()) {
                    int idAsiento = idRs.getInt("id_asiento");
                    insertAsientosStmt.setInt(1, idAsiento);
                    insertAsientosStmt.setInt(2, idReserva);
                    insertAsientosStmt.addBatch();
                } else {
                    throw new IllegalArgumentException("Uno o más asientos proporcionados no existen.");
                }
            }
            insertAsientosStmt.executeBatch();
            conn2.commit();
            success = true;
            JOptionPane.showMessageDialog(null, "Reserva realizada con éxito.");
            CedulaText.setText("");
            IDFuncion.setText("");
            BoletosCantidad.setText("");
            NumAsiento.setText("");
        } catch (Exception e) {
            if (conn2 != null) {
                try {
                    conn2.rollback();
                } catch (SQLException rollbackEx) {
                    JOptionPane.showMessageDialog(null, "Error al revertir la transacción: " + rollbackEx.getMessage());
                    rollbackEx.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn2 != null && !success) {
                try {
                    conn2.close();
                } catch (SQLException closeEx) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + closeEx.getMessage());
                    closeEx.printStackTrace();
                }
            }
        }
    }


    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650,600);
        setVisible(true);
    }
}
