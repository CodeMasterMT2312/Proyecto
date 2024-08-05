import pantallasPrincipales.*;

/**
 * La clase {@code Main} contiene el método principal que inicia la aplicación.
 * <p>
 * Este es el punto de entrada de la aplicación que crea una instancia de la clase {@code Login}
 * y llama al método {@code iniciar()} para comenzar el proceso de autenticación.
 * </p>
 *
 * @author Mathias Teran
 * @version 2.1
 */
public class Main {
    public static void main(String[] args) {
        /**
         * El método principal de la aplicación.
         * <p>
         * Este método crea una instancia de la clase {@code Login} y llama a su método {@code iniciar()}.
         * Es el punto de entrada para la ejecución de la aplicación.
         * </p>
         *
         * @param args argumentos de la línea de comandos proporcionados al iniciar la aplicación.
         */
        Login log = new Login();
        log.iniciar();
    }
}