package controlador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.ConexionDB;
import modelo.EstrategiaInversion;

// Esta clase controla todas las operaciones con estrategias de inversión en la base de datos
// Permite crear, actualizar, eliminar y buscar estrategias de inversión
public class EstrategiaControlador {
    
    // Método para crear una nueva estrategia de inversión en la base de datos
    // Retorna true si se creó exitosamente, false si hubo error
    public boolean crearEstrategia(EstrategiaInversion estrategia) {
        String sql = "INSERT INTO estrategias_inversion " +
                    "(nombre, descripcion, tipo_estrategia, nivel_riesgo, " +
                    "tecnologias_utilizadas, retorno_esperado, articulo_relacionado_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Establecemos los valores de la estrategia en la consulta SQL
            pstmt.setString(1, estrategia.getNombre());
            pstmt.setString(2, estrategia.getDescripcion());
            pstmt.setString(3, estrategia.getTipoEstrategia());
            pstmt.setString(4, estrategia.getNivelRiesgo());
            pstmt.setString(5, estrategia.getTecnologiasUtilizadas());
            pstmt.setDouble(6, estrategia.getRetornoEsperado());
            
            // Si hay un artículo relacionado, lo asignamos; si no, dejamos el campo vacío
            if (estrategia.getArticuloRelacionadoId() != null) {
                pstmt.setInt(7, estrategia.getArticuloRelacionadoId());
            } else {
                pstmt.setNull(7, Types.INTEGER);
            }
            
            // Ejecutamos la inserción
            int filasAfectadas = pstmt.executeUpdate();
            
            // Si la inserción fue exitosa, obtenemos el ID generado
            if (filasAfectadas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    estrategia.setId(rs.getInt(1));
                }
                System.out.println("✓ Estrategia creada exitosamente");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al crear estrategia: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Método para obtener todas las estrategias de inversión ordenadas por fecha de creación (más recientes primero)
    public List<EstrategiaInversion> obtenerTodasLasEstrategias() {
        List<EstrategiaInversion> estrategias = new ArrayList<>();
        String sql = "SELECT * FROM estrategias_inversion ORDER BY fecha_creacion DESC";
        
        try (Connection conn = ConexionDB.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Recorremos cada resultado y lo convertimos en un objeto EstrategiaInversion
            while (rs.next()) {
                estrategias.add(mapearEstrategia(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener estrategias: " + e.getMessage());
            e.printStackTrace();
        }
        
        return estrategias;
    }
    
    // Método para obtener una estrategia específica por su identificador (ID)
    public EstrategiaInversion obtenerEstrategiaPorId(int id) {
        String sql = "SELECT * FROM estrategias_inversion WHERE id = ?";
        EstrategiaInversion estrategia = null;
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Buscamos la estrategia con el ID especificado
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            // Si existe, la mapeamos a un objeto
            if (rs.next()) {
                estrategia = mapearEstrategia(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener estrategia: " + e.getMessage());
        }
        
        return estrategia;
    }
    
    // Método para actualizar una estrategia existente en la base de datos
    // Retorna true si se actualizó exitosamente, false si hubo error
    public boolean actualizarEstrategia(EstrategiaInversion estrategia) {
        String sql = "UPDATE estrategias_inversion SET " +
                    "nombre = ?, descripcion = ?, tipo_estrategia = ?, " +
                    "nivel_riesgo = ?, tecnologias_utilizadas = ?, " +
                    "retorno_esperado = ?, articulo_relacionado_id = ? " +
                    "WHERE id = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Establecemos los nuevos valores de la estrategia
            pstmt.setString(1, estrategia.getNombre());
            pstmt.setString(2, estrategia.getDescripcion());
            pstmt.setString(3, estrategia.getTipoEstrategia());
            pstmt.setString(4, estrategia.getNivelRiesgo());
            pstmt.setString(5, estrategia.getTecnologiasUtilizadas());
            pstmt.setDouble(6, estrategia.getRetornoEsperado());
            
            // Asignamos el artículo relacionado o dejamos el campo vacío
            if (estrategia.getArticuloRelacionadoId() != null) {
                pstmt.setInt(7, estrategia.getArticuloRelacionadoId());
            } else {
                pstmt.setNull(7, Types.INTEGER);
            }
            
            // Especificamos qué estrategia queremos actualizar
            pstmt.setInt(8, estrategia.getId());
            
            // Ejecutamos la actualización
            int filasAfectadas = pstmt.executeUpdate();
            
            // Verificamos que se haya actualizado algo
            if (filasAfectadas > 0) {
                System.out.println("✓ Estrategia actualizada exitosamente");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar estrategia: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Método para eliminar una estrategia de la base de datos por su ID
    // Retorna true si se eliminó exitosamente, false si hubo error
    public boolean eliminarEstrategia(int id) {
        String sql = "DELETE FROM estrategias_inversion WHERE id = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Especificamos el ID de la estrategia a eliminar
            pstmt.setInt(1, id);
            
            // Ejecutamos la eliminación
            int filasAfectadas = pstmt.executeUpdate();
            
            // Verificamos que se haya eliminado algo
            if (filasAfectadas > 0) {
                System.out.println("✓ Estrategia eliminada exitosamente");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar estrategia: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Método para buscar estrategias por término de búsqueda
    // Busca en nombre, tipo y descripción de las estrategias
    public List<EstrategiaInversion> buscarEstrategias(String termino) {
        List<EstrategiaInversion> estrategias = new ArrayList<>();
        String sql = "SELECT * FROM estrategias_inversion WHERE " +
                    "nombre LIKE ? OR tipo_estrategia LIKE ? OR descripcion LIKE ? " +
                    "ORDER BY fecha_creacion DESC";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Creamos un patrón de búsqueda que encuentra coincidencias parciales
            String patron = "%" + termino + "%";
            pstmt.setString(1, patron);
            pstmt.setString(2, patron);
            pstmt.setString(3, patron);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                estrategias.add(mapearEstrategia(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar estrategias: " + e.getMessage());
        }
        
        return estrategias;
    }

    // Método para obtener estrategias filtradas por nivel de riesgo
    // Retorna las estrategias ordenadas por retorno esperado (mayor primero)
    public List<EstrategiaInversion> obtenerEstrategiasPorRiesgo(String nivelRiesgo) {
        List<EstrategiaInversion> estrategias = new ArrayList<>();
        String sql = "SELECT * FROM estrategias_inversion WHERE nivel_riesgo = ? " +
                    "ORDER BY retorno_esperado DESC";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Filtramos por el nivel de riesgo especificado
            pstmt.setString(1, nivelRiesgo);
            ResultSet rs = pstmt.executeQuery();
            
            // Convertimos cada resultado en un objeto EstrategiaInversion
            while (rs.next()) {
                estrategias.add(mapearEstrategia(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener estrategias por riesgo: " + e.getMessage());
        }
        
        return estrategias;
    }
    
    // Método privado auxiliar que convierte una fila de la base de datos en un objeto EstrategiaInversion
    // Este método extrae todos los datos y los asigna a los atributos del objeto
    private EstrategiaInversion mapearEstrategia(ResultSet rs) throws SQLException {
        EstrategiaInversion estrategia = new EstrategiaInversion();
        // Extraemos los datos de cada columna y los asignamos al objeto
        estrategia.setId(rs.getInt("id"));
        estrategia.setNombre(rs.getString("nombre"));
        estrategia.setDescripcion(rs.getString("descripcion"));
        estrategia.setTipoEstrategia(rs.getString("tipo_estrategia"));
        estrategia.setNivelRiesgo(rs.getString("nivel_riesgo"));
        estrategia.setTecnologiasUtilizadas(rs.getString("tecnologias_utilizadas"));
        estrategia.setRetornoEsperado(rs.getDouble("retorno_esperado"));
        
        // Si hay un artículo relacionado, lo asignamos al objeto
        int articuloId = rs.getInt("articulo_relacionado_id");
        if (!rs.wasNull()) {
            estrategia.setArticuloRelacionadoId(articuloId);
        }
        
        estrategia.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        estrategia.setFechaActualizacion(rs.getTimestamp("fecha_actualizacion"));
        
        return estrategia;
    }
}
