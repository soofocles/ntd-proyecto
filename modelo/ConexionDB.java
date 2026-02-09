package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Esta clase se encarga de conectar la aplicación con la base de datos MySQL
// Usa el patrón Singleton para asegurar que solo existe una conexión activa
public class ConexionDB {
    // Constantes de configuración para conectarse a la base de datos
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  // Driver de MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/articulos_fintech?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";  // Dirección y nombre de la BD
    private static final String USUARIO = "root";  // Usuario de MySQL
    private static final String PASSWORD = "";  // Contraseña de MySQL

    private static Connection conexion = null;  // Variable para guardar la conexión activa

    // Constructor privado - evita que se creen instancias de esta clase
    private ConexionDB() {}

    // Método estático para obtener la conexión a la base de datos
    // Si la conexión está cerrada o no existe, la abre
    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                // Cargamos el driver de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Establecemos la conexión con la base de datos
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("✓ Conexión exitosa a la base de datos (XAMPP)");
            } catch (ClassNotFoundException e) {
                // Error si el driver de MySQL no está disponible
                System.err.println("Error: Driver de MySQL no encontrado");
                e.printStackTrace();
                throw new SQLException("Driver no encontrado", e);
            }
        }
        return conexion;  // Retorna la conexión activa
    }

    // Método main - se ejecuta para probar que la conexión a la BD funciona correctamente
    public static void main(String[] args) throws Exception {
        getConexion();  // Intenta conectarse a la base de datos
    }
}
