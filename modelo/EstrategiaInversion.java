package modelo;

import java.sql.Timestamp;

// Esta clase representa una estrategia de inversión
// Contiene información sobre el tipo de estrategia, nivel de riesgo, retorno esperado, etc.
public class EstrategiaInversion {
    
    // Propiedades (atributos) de la estrategia de inversión
    private int id;                        // Identificador único de la estrategia
    private String nombre;                 // Nombre de la estrategia
    private String descripcion;            // Descripción detallada de la estrategia
    private String tipoEstrategia;         // Tipo de estrategia (ej: Conservadora, Agresiva)
    private String nivelRiesgo;            // Nivel de riesgo (ej: Bajo, Medio, Alto)
    private String tecnologiasUtilizadas;  // Tecnologías o herramientas utilizadas
    private double retornoEsperado;        // Porcentaje de retorno esperado
    private Integer articuloRelacionadoId; // ID del artículo asociado a la estrategia
    private Timestamp fechaCreacion;       // Fecha y hora en que se creó
    private Timestamp fechaActualizacion;  // Fecha y hora de la última actualización
   
    // Constructor vacío - para una estrategia sin datos iniciales
    public EstrategiaInversion() {
    }
    
    // Constructor con parámetros - para cuando tenemos los datos principales de la estrategia
    public EstrategiaInversion(String nombre, String descripcion, String tipoEstrategia, 
                              String nivelRiesgo, String tecnologiasUtilizadas, 
                              double retornoEsperado, Integer articuloRelacionadoId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoEstrategia = tipoEstrategia;
        this.nivelRiesgo = nivelRiesgo;
        this.tecnologiasUtilizadas = tecnologiasUtilizadas;
        this.retornoEsperado = retornoEsperado;
        this.articuloRelacionadoId = articuloRelacionadoId;
    }

    // GETTERS Y SETTERS
    
    public int getId() {
        return id;  // Devuelve el identificador de la estrategia
    }
    
    public void setId(int id) {
        this.id = id;  // Asigna un identificador a la estrategia
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getTipoEstrategia() {
        return tipoEstrategia;
    }
    
    public void setTipoEstrategia(String tipoEstrategia) {
        this.tipoEstrategia = tipoEstrategia;
    }
    
    public String getNivelRiesgo() {
        return nivelRiesgo;
    }
    
    public void setNivelRiesgo(String nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }
    
    public String getTecnologiasUtilizadas() {
        return tecnologiasUtilizadas;
    }
    
    public void setTecnologiasUtilizadas(String tecnologiasUtilizadas) {
        this.tecnologiasUtilizadas = tecnologiasUtilizadas;
    }
    
    public double getRetornoEsperado() {
        return retornoEsperado;
    }
    
    public void setRetornoEsperado(double retornoEsperado) {
        this.retornoEsperado = retornoEsperado;
    }
    
    public Integer getArticuloRelacionadoId() {
        return articuloRelacionadoId;
    }
    
    public void setArticuloRelacionadoId(Integer articuloRelacionadoId) {
        this.articuloRelacionadoId = articuloRelacionadoId;
    }
    
    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Timestamp getFechaActualizacion() {
        return fechaActualizacion;
    }
    
    public void setFechaActualizacion(Timestamp fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
    // Método toString - convierte la estrategia a texto para mostrarla en la interfaz
    @Override
    public String toString() {
        return nombre + " - " + tipoEstrategia + " (Riesgo: " + nivelRiesgo + ")";  // Muestra nombre, tipo y riesgo
    }
}