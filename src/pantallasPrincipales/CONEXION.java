package pantallasPrincipales;

import java.sql.*;

/**
 * La clase {@code CONEXION} proporciona una conexión a una base de datos MySQL.
 * Esta clase contiene un método para establecer una conexión con la base de datos
 * utilizando las credenciales y la URL proporcionadas.
 */
public class CONEXION {
    /**
     * Establece y devuelve una conexión a la base de datos MySQL utilizando las
     * credenciales y la URL predefinidas.
     *
     * @return Una instancia de {@link Connection} que representa la conexión a la base de datos.
     * @throws SQLException Si ocurre un error al conectar con la base de datos.
     */
    public Connection conexion() throws SQLException {
        String URL = "jdbc:mysql://ukmzrc1ceqxavkuv:2gpmdXEDwsX2lvojXRRD@bkz2ynwtxdmc5bl5d3wj-mysql.services.clever-cloud.com:3306/bkz2ynwtxdmc5bl5d3wj"; // Nombre de la base de datos
        String userDB = "ukmzrc1ceqxavkuv";
        String password = "2gpmdXEDwsX2lvojXRRD";
        return DriverManager.getConnection(URL, userDB, password);
    }
}