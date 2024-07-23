package pantallasPrincipales;

import java.sql.*;


public class CONEXION {
    public Connection conexion() throws SQLException {
        String URL = "jdbc:mysql://ukmzrc1ceqxavkuv:2gpmdXEDwsX2lvojXRRD@bkz2ynwtxdmc5bl5d3wj-mysql.services.clever-cloud.com:3306/bkz2ynwtxdmc5bl5d3wj"; // Nombre de la base de datos
        String userDB = "ukmzrc1ceqxavkuv";
        String password = "2gpmdXEDwsX2lvojXRRD";
        return DriverManager.getConnection(URL, userDB, password);
    }
}
