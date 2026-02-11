package controlador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Articulo;
import modelo.ConexionDB;

// Esta clase controla todas las operaciones con artículos en la base de datos
// Maneja búsquedas, obtención de datos y conteo de artículos
public class ArticuloControlador {

    // Método para obtener todos los artículos ordenados por año de publicación (más recientes primero)
    public List<Articulo> obtenerTodosLosArticulos() {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM articulos ORDER BY anio_publicacion DESC, id";
        
        try (Connection conn = ConexionDB.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Recorremos cada fila de resultados y la convertimos en un objeto Articulo
            while (rs.next()) {
                articulos.add(mapearArticulo(rs));
            }
            
        } catch (SQLException e) {
        } catch (SQLException e) { // Manejo de errores en caso de problemas con la base de datos
            System.err.println("Error al obtener artículos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return articulos;
    }
    

    // Método para buscar un artículo específico por su identificador (ID)
    public Articulo obtenerArticuloPorId(int id) {
        String sql = "SELECT * FROM articulos WHERE id = ?";
        Articulo articulo = null;
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Establecemos el ID que queremos buscar
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            // Si existe el artículo, lo mapeamos
            // Si existe el artículo, lo convertimos en un objeto Articulo y lo devolvemos
            if (rs.next()) {
                articulo = mapearArticulo(rs);
            }
            
        } catch (SQLException e) {
        } catch (SQLException e) { // Manejo de errores en caso de problemas con la base de datos
            System.err.println("Error al obtener artículo: " + e.getMessage());
        }
        
        return articulo;
    }
    
    // Método para obtener todos los artículos que pertenecen a una búsqueda específica
    public List<Articulo> obtenerArticulosPorBusqueda(int busquedaId) {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM articulos WHERE busqueda_id = ? ORDER BY id";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, busquedaId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                articulos.add(mapearArticulo(rs));
            }
            
        } catch (SQLException e) {
        } catch (SQLException e) { // Manejo de errores en caso de problemas con la base de datos
            System.err.println("Error al obtener artículos por búsqueda: " + e.getMessage());
        }
        
        return articulos;
    }
    

    // Método para buscar artículos por palabra clave
    // Busca en el título, autores y palabras clave de los artículos
    public List<Articulo> buscarArticulos(String palabraClave) {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM articulos WHERE " +
                    "titulo LIKE ? OR autores LIKE ? OR palabras_clave LIKE ? " +
                    "ORDER BY anio_publicacion DESC";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Agregamos % para buscar coincidencias parciales (cualquier parte de la palabra)
            // Agregamos % para buscar coincidencias parciales en el título, autores y palabras clave
            String patron = "%" + palabraClave + "%";
            pstmt.setString(1, patron);
            pstmt.setString(2, patron);
            pstmt.setString(3, patron);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                articulos.add(mapearArticulo(rs));
            }
            
        } catch (SQLException e) {
        } catch (SQLException e) { // Manejo de errores en caso de problemas con la base de datos
            System.err.println("Error al buscar artículos: " + e.getMessage());
        }
        
        return articulos;
    }
    
    // Método para contar el total de artículos almacenados en la base de datos
    public int contarArticulos() {
        String sql = "SELECT COUNT(*) as total FROM articulos";
        int total = 0;
        
        try (Connection conn = ConexionDB.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                total = rs.getInt("total");
            }
            
        } catch (SQLException e) {
                total = rs.getInt("total"); 
            }
            
        } catch (SQLException e) { // Manejo de errores en caso de problemas con la base de datos
            System.err.println("Error al contar artículos: " + e.getMessage());
        }
        
        return total;
    }
    
    // Método privado auxiliar que convierte una fila de la base de datos en un objeto Articulo
    // Este método rellena todos los atributos del artículo con los datos de la base de datos
    private Articulo mapearArticulo(ResultSet rs) throws SQLException {
        Articulo articulo = new Articulo();
        articulo.setId(rs.getInt("id"));
        articulo.setBusquedaId(rs.getInt("busqueda_id"));
        articulo.setTitulo(rs.getString("titulo"));
        articulo.setAutores(rs.getString("autores"));
        articulo.setAnioPublicacion(rs.getInt("anio_publicacion"));
        articulo.setFuente(rs.getString("fuente"));
        articulo.setDoi(rs.getString("doi"));
        articulo.setResumen(rs.getString("resumen"));
        articulo.setPalabrasClave(rs.getString("palabras_clave"));
        articulo.setCitaAPA(rs.getString("cita_apa"));
        return articulo;
    }
}