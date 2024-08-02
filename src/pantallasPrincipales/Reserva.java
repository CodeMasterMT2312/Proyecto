package pantallasPrincipales;


import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La clase {@code Reserva} representa una ventana para reservar butacas en una función.
 * Esta clase permite a los usuarios ingresar información sobre la reserva y guardar los datos en la base de datos.
 */
public class Reserva extends JFrame {
    private JTextField NumAsiento;
    private JButton siguienteButton;
    private JPanel ReservaPanel;
    private JTextField BoletosCantidad;
    private JButton Regresar;
    private JTextField IDFuncion;
    private JTextField CedulaText;

    /**
     * Constructor de la clase {@code Reserva}.
     * Inicializa la ventana y configura los eventos de los botones.
     */

    public Reserva() {
        super("Reservar Butacas");
        setContentPane(ReservaPanel);
        Regresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Funciones funci = new Funciones();
                funci.iniciar();
                try {
                    funci.MostrarFunciones();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al mostrar la cartelera");
                }
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
    /**
     * Guarda la reserva en la base de datos.
     * Valida los asientos proporcionados, verifica si están reservados,
     * crea una nueva reserva y actualiza las estadísticas.
     *
     * @throws SQLException Si ocurre un error al conectar o realizar operaciones en la base de datos.
     */
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

            // Actualizar estadísticas
            ActualizarEstadisticas(conn2, idFuncion);

            conn2.commit();
            success = true;
            JOptionPane.showMessageDialog(null, "Reserva realizada con éxito.");
            CedulaText.setText("");
            IDFuncion.setText("");
            BoletosCantidad.setText("");
            NumAsiento.setText("");

            // Mostrar la información de la reserva
            String query = """
                SELECT
                    r.id_reserva,
                    r.id_usuario,
                    r.id_funcion,
                    r.fecha_reserva,
                    f.fecha AS fecha_funcion,
                    f.hora AS hora_funcion,
                    a.fila,
                    a.numero
                FROM
                    Reservas r
                JOIN
                    Asientos_Reservas ar ON r.id_reserva = ar.id_reserva
                JOIN
                    Asientos a ON ar.id_asiento = a.id_asiento
                JOIN
                    Funciones f ON r.id_funcion = f.id_funcion
                WHERE
                    r.id_reserva = ?;
            """;
            PreparedStatement stmt = conn2.prepareStatement(query);
            stmt.setInt(1, idReserva);
            ResultSet rs = stmt.executeQuery();

            // Construir el mensaje a mostrar en el JOptionPane
            StringBuilder mensaje = new StringBuilder();
            boolean datosEncontrados = false;
            while (rs.next()) {
                // Recuperar los datos del ResultSet
                int idReservaDb = rs.getInt("id_reserva");
                Timestamp fechaReserva = rs.getTimestamp("fecha_reserva");
                Date fechaFuncion = rs.getDate("fecha_funcion");
                Time horaFuncion = rs.getTime("hora_funcion");
                String fila = rs.getString("fila");
                int numero = rs.getInt("numero");

                // Añadir la información al mensaje
                mensaje.append("Reserva ID: ").append(idReservaDb).append("\n")
                        .append("Usuario ID: ").append(idUsuario).append("\n")
                        .append("Función ID: ").append(idFuncion).append("\n")
                        .append("Fecha Reserva: ").append(fechaReserva).append("\n")
                        .append("Fecha Función: ").append(fechaFuncion).append("\n")
                        .append("Hora Función: ").append(horaFuncion).append("\n")
                        .append("Fila: ").append(fila).append("\n")
                        .append("Número: ").append(numero).append("\n");

                datosEncontrados = true;
            }


            if (mensaje.length() == 0) {
                mensaje.append("No se encontró información para la reserva ID: ").append(idReserva);
            }
            // Mostrar la información en un JOptionPane
            JOptionPane.showMessageDialog(null, mensaje.toString());

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
            if (conn2 != null) {
                try {
                    conn2.close();
                } catch (SQLException closeEx) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + closeEx.getMessage());
                    closeEx.printStackTrace();
                }
            }
        }
    }

    /**
     * Inicia la ventana de reserva.
     */
    public void iniciar(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650,700);
        setVisible(true);
    }

    /**
     * Actualiza las estadísticas de la función reservada en la base de datos.
     *
     * @param conn2 La conexión a la base de datos.
     * @param idFuncion El ID de la función cuya estadística se actualizará.
     * @throws SQLException Si ocurre un error al conectar o realizar operaciones en la base de datos.
     */
    public void ActualizarEstadisticas(Connection conn2, int idFuncion) throws SQLException {
        // Contar el número total de asientos para la función
        String totalAsientosQuery = "SELECT COUNT(*) FROM Asientos WHERE id_sala = (SELECT id_sala FROM Funciones WHERE id_funcion = ?)";
        PreparedStatement totalAsientosStmt = conn2.prepareStatement(totalAsientosQuery);
        totalAsientosStmt.setInt(1, idFuncion);
        ResultSet totalAsientosRs = totalAsientosStmt.executeQuery();
        int totalAsientos = 0;
        if (totalAsientosRs.next()) {
            totalAsientos = totalAsientosRs.getInt(1);
        }

        // Contar el número de asientos reservados para la función
        String asientosReservadosQuery = "SELECT COUNT(*) FROM Asientos_Reservas WHERE id_asiento IN (SELECT id_asiento FROM Asientos WHERE id_sala = (SELECT id_sala FROM Funciones WHERE id_funcion = ?))";
        PreparedStatement asientosReservadosStmt = conn2.prepareStatement(asientosReservadosQuery);
        asientosReservadosStmt.setInt(1, idFuncion);
        ResultSet asientosReservadosRs = asientosReservadosStmt.executeQuery();
        int asientosReservados = 0;
        if (asientosReservadosRs.next()) {
            asientosReservados = asientosReservadosRs.getInt(1);
        }

        // Verificar si ya existe una entrada en la tabla Estadisticas para esta función
        String checkEstadisticasQuery = "SELECT COUNT(*) FROM Estadisticas WHERE id_funcion = ?";
        PreparedStatement checkEstadisticasStmt = conn2.prepareStatement(checkEstadisticasQuery);
        checkEstadisticasStmt.setInt(1, idFuncion);
        ResultSet checkEstadisticasRs = checkEstadisticasStmt.executeQuery();
        boolean existeEstadistica = false;
        if (checkEstadisticasRs.next()) {
            existeEstadistica = checkEstadisticasRs.getInt(1) > 0;
        }

        // Actualizar o insertar los datos en la tabla Estadisticas
        String estadisticasQuery;
        if (existeEstadistica) {
            // Actualizar la estadística existente
            estadisticasQuery = "UPDATE Estadisticas SET asientos_totales = ?, asientos_reservados = ?, fecha = CURDATE() WHERE id_funcion = ?";
            PreparedStatement estadisticasStmt = conn2.prepareStatement(estadisticasQuery);
            estadisticasStmt.setInt(1, totalAsientos);
            estadisticasStmt.setInt(2, asientosReservados);
            estadisticasStmt.setInt(3, idFuncion);
            estadisticasStmt.executeUpdate();
        } else {
            // Insertar una nueva estadística
            estadisticasQuery = "INSERT INTO Estadisticas (id_funcion, asientos_totales, asientos_reservados, fecha) VALUES (?, ?, ?, CURDATE())";
            PreparedStatement estadisticasStmt = conn2.prepareStatement(estadisticasQuery);
            estadisticasStmt.setInt(1, idFuncion);
            estadisticasStmt.setInt(2, totalAsientos);
            estadisticasStmt.setInt(3, asientosReservados);
            estadisticasStmt.executeUpdate();
        }
    }
}
