package controlador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Busqueda;
import modelo.ConexionDB;

// Esta clase controla todas las operaciones con búsquedas en la base de datos
// Permite obtener búsquedas y calcular estadísticas de ellas

public class BusquedaControlador {

    // Método para obtener todas las búsquedas almacenadas en la base de datos
public List<Busqueda> obtenerTodasLasBusquedas(){
    List<Busqueda> busquedas = new ArrayList<>();
    String sql = "SELECT * FROM busquedas ORDER BY id";

            try (Connection conn = ConexionDB.getConexion();
             Statement stmt = conn.createStatement(); // Crea un objeto para ejecutar SQL
             ResultSet rs = stmt.executeQuery(sql)) { // Ejecuta la consulta y obtiene los resultados
            
            // Recorremos los resultados y creamos objetos Busqueda con los datos obtenidos
            while (rs.next()) {
                Busqueda busqueda = new Busqueda();
                busqueda.setId(rs.getInt("id"));
                busqueda.setNombreEstudiante(rs.getString("nombre_estudiante"));
                busqueda.setBaseDatos(rs.getString("base_datos"));
                busqueda.setCadenaBusqueda(rs.getString("cadena_busqueda"));
                busqueda.setCantidadDocumentos(rs.getInt("cantidad_documentos"));
                busqueda.setFechaBusqueda(rs.getTimestamp("fecha_busqueda"));
                
                busquedas.add(busqueda); // Agrega la búsqueda a la lista de resultados
            }
            
        } catch (SQLException e) { // Manejo de errores en caso de problemas con la base de datos
            System.err.println("Error al obtener búsquedas: " + e.getMessage());
            e.printStackTrace();
        }
    return busquedas;

        // Método para obtener una búsqueda específica por su ID
    public Busqueda obtenerBusquedaPorId(int id) {
        String sql = "SELECT * FROM busquedas WHERE id = ?";
        Busqueda busqueda = null;

        try (Connection conn = ConexionDB.getConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Buscamos la búsqueda con el ID especificado
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery(); // Ejecuta la consulta y obtiene los resultados

            // Si la búsqueda existe, creamos un objeto con sus datos
            if (rs.next()) {
                busqueda = new Busqueda();
                busqueda.setId(rs.getInt("id"));
                busqueda.setNombreEstudiante(rs.getString("nombre_estudiante"));
                busqueda.setBaseDatos(rs.getString("base_datos"));
                busqueda.setCadenaBusqueda(rs.getString("cadena_busqueda"));
                busqueda.setCantidadDocumentos(rs.getInt("cantidad_documentos"));
                busqueda.setFechaBusqueda(rs.getTimestamp("fecha_busqueda"));
            }

        } catch (SQLException e) { // Manejo de errores en caso de problemas con la base de datos
            System.err.println("Error al obtener búsqueda: " + e.getMessage());
        }

        return busqueda;
    }

    // Método para obtener estadísticas de las búsquedas
    // Retorna un array con: [0] = total de búsquedas, [1] = suma total de
    // documentos encontrados
    public int[] obtenerEstadisticas() {
        String sql = "SELECT COUNT(*) as total, SUM(cantidad_documentos) as suma FROM busquedas";
        int[] stats = new int[2];

        try (Connection conn = ConexionDB.getConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // Extraemos los datos de estadísticas de la base de datos
            if (rs.next()) {
                stats[0] = rs.getInt("total"); // Total de búsquedas realizadas
                stats[1] = rs.getInt("suma"); // Suma de documentos encontrados
            }

        } catch (SQLException e) { // Manejo de errores en caso de problemas con la base de datos
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
        }

        return stats;
    }
}
