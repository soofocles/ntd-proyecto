package modelo;

// Esta clase representa un artículo científico almacenado en la base de datos.
// Contiene información como título, autores, año de publicación, resumen, etc.
public class Articulo {
    
    // Propiedades (atributos) del artículo
    private int id;                      // Identificador único del artículo
    private int busquedaId;              // ID de la búsqueda a la que pertenece
    private String titulo;               // Título del artículo
    private String autores;              // Autores del artículo
    private int anioPublicacion;         // Año en que se publicó
    private String fuente;               // Revista o fuente de publicación
    private String doi;                  // Identificador digital (DOI)
    private String resumen;              // Resumen del contenido
    private String palabrasClave;        // Palabras clave del artículo
    private String citaAPA;              // Cita en formato APA

    // Constructor vacío - para un artículo sin datos iniciales
    public Articulo() {
    }
    
    // Constructor completo - se usa cuando tenemos todos los datos del artículo
    public Articulo(int id, int busquedaId, String titulo, String autores, 
                   int anioPublicacion, String fuente, String doi, 
                   String resumen, String palabrasClave, String citaAPA) {
        this.id = id;
        this.busquedaId = busquedaId;
        this.titulo = titulo;
        this.autores = autores;
        this.anioPublicacion = anioPublicacion;
        this.fuente = fuente;
        this.doi = doi;
        this.resumen = resumen;
        this.palabrasClave = palabrasClave;
        this.citaAPA = citaAPA;
    }
    
    // GETTERS Y SETTERS
    
    public int getId() {
        return id;  // Devuelve el identificador del artículo
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getBusquedaId() {
        return busquedaId;
    }
    
    public void setBusquedaId(int busquedaId) {
        this.busquedaId = busquedaId;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getAutores() {
        return autores;
    }
    
    public void setAutores(String autores) {
        this.autores = autores;
    }
    
    public int getAnioPublicacion() {
        return anioPublicacion;
    }
    
    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }
    
    public String getFuente() {
        return fuente;
    }
    
    public void setFuente(String fuente) {
        this.fuente = fuente;
    }
    
    public String getDoi() {
        return doi;
    }
    
    public void setDoi(String doi) {
        this.doi = doi;
    }
    
    public String getResumen() {
        return resumen;
    }
    
    public void setResumen(String resumen) {
        this.resumen = resumen;
    }
    
    public String getPalabrasClave() {
        return palabrasClave;
    }
    
    public void setPalabrasClave(String palabrasClave) {
        this.palabrasClave = palabrasClave;
    }
    
    public String getCitaAPA() {
        return citaAPA;
    }
    
    public void setCitaAPA(String citaAPA) {
        this.citaAPA = citaAPA;
    }
    
    // Método toString - convierte el artículo a texto para mostrarlo en la interfaz
    @Override
    public String toString() {
        return titulo + " (" + anioPublicacion + ")";
    }
}