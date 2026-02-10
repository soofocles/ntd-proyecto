package vista;

import controlador.ArticuloControlador;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.Articulo;


// Panel para mostrar y buscar artículos científicos
// Se comunica con `ArticuloControlador` para obtener y buscar registros
public class PanelArticulos extends JPanel {
    
    private ArticuloControlador controlador;
    private JTable tablaArticulos;
    private DefaultTableModel modeloTabla;
    private JTextArea txtDetalles;
    private JTextField txtBuscar;
    private JLabel lblTotal;
    
    // Constructor: inicializa el controlador, la interfaz y carga los artículos
    public PanelArticulos() {
        controlador = new ArticuloControlador();
        inicializarComponentes();
        cargarArticulos();
    }
    
    // Crea y configura los componentes gráficos (buscador, tabla, detalles)
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar:"));
        
        txtBuscar = new JTextField(30);
        panelBusqueda.add(txtBuscar);
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 11));
        btnBuscar.addActionListener(e -> buscarArticulos());
        panelBusqueda.add(btnBuscar);
        
        JButton btnMostrarTodos = new JButton("Mostrar Todos");
        btnMostrarTodos.setFont(new Font("Arial", Font.BOLD, 11));
        btnMostrarTodos.addActionListener(e -> cargarArticulos());
        panelBusqueda.add(btnMostrarTodos);
        
        lblTotal = new JLabel();
        lblTotal.setFont(new Font("Arial", Font.BOLD, 12));
        
        panelSuperior.add(panelBusqueda, BorderLayout.WEST);
        panelSuperior.add(lblTotal, BorderLayout.EAST);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        String[] columnas = {"ID", "Título", "Autores", "Año"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaArticulos = new JTable(modeloTabla);
        tablaArticulos.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaArticulos.setRowHeight(30);
        tablaArticulos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaArticulos.getTableHeader().setBackground(new Color(39, 174, 96));
        tablaArticulos.getTableHeader().setForeground(Color.BLACK);
        tablaArticulos.getTableHeader().setReorderingAllowed(false);
        
        tablaArticulos.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaArticulos.getColumnModel().getColumn(1).setPreferredWidth(400);
        tablaArticulos.getColumnModel().getColumn(2).setPreferredWidth(250);
        tablaArticulos.getColumnModel().getColumn(3).setPreferredWidth(60);
        
        tablaArticulos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDetallesArticulo();
            }
        });
        
        JScrollPane scrollTabla = new JScrollPane(tablaArticulos);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Artículos Científicos"));
        
        JPanel panelDetalles = new JPanel(new BorderLayout());
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Información Completa del Artículo"));
        
        txtDetalles = new JTextArea();
        txtDetalles.setFont(new Font("Monospaced", Font.PLAIN, 11));
        txtDetalles.setEditable(false);
        txtDetalles.setLineWrap(true);
        txtDetalles.setWrapStyleWord(true);
        txtDetalles.setText("Seleccione un artículo para ver su información completa...");
        
        JScrollPane scrollDetalles = new JScrollPane(txtDetalles);
        panelDetalles.add(scrollDetalles, BorderLayout.CENTER);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTabla, panelDetalles);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.45);
        
        add(splitPane, BorderLayout.CENTER);
    }
    
    // Carga los artículos desde el controlador y los muestra en la tabla
    private void cargarArticulos() {
        modeloTabla.setRowCount(0);
        List<Articulo> articulos = controlador.obtenerTodosLosArticulos();
        
        for (Articulo a : articulos) {
            Object[] fila = {
                a.getId(),
                a.getTitulo(),
                a.getAutores(),
                a.getAnioPublicacion()
            };
            modeloTabla.addRow(fila);
        }
        
        lblTotal.setText("Total de artículos: " + articulos.size());
        txtBuscar.setText("");
    }
    
    // Realiza una búsqueda por palabra clave y actualiza la tabla con los resultados
    private void buscarArticulos() {
        String termino = txtBuscar.getText().trim();
        
        if (termino.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor ingrese un término de búsqueda",
                "Búsqueda",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        modeloTabla.setRowCount(0);
        List<Articulo> articulos = controlador.buscarArticulos(termino);
        
        for (Articulo a : articulos) {
            Object[] fila = {
                a.getId(),
                a.getTitulo(),
                a.getAutores(),
                a.getAnioPublicacion()
            };
            modeloTabla.addRow(fila);
        }
        
        lblTotal.setText("Resultados encontrados: " + articulos.size());
        
        if (articulos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No se encontraron artículos con el término: " + termino,
                "Búsqueda",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Muestra la información bibliográfica completa del artículo seleccionado
    private void mostrarDetallesArticulo() {
        int filaSeleccionada = tablaArticulos.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            Articulo articulo = controlador.obtenerArticuloPorId(id);
            
            if (articulo != null) {
                StringBuilder detalles = new StringBuilder();
                detalles.append("═══════════════════════════════════════════════════════════════\n");
                detalles.append("  INFORMACIÓN BIBLIOGRÁFICA COMPLETA\n");
                detalles.append("═══════════════════════════════════════════════════════════════\n\n");
                
                detalles.append("TÍTULO:\n");
                detalles.append(articulo.getTitulo()).append("\n\n");
                
                detalles.append("AUTORES:\n");
                detalles.append(articulo.getAutores()).append("\n\n");
                
                detalles.append("AÑO DE PUBLICACIÓN: ").append(articulo.getAnioPublicacion()).append("\n\n");
                
                detalles.append("FUENTE:\n");
                detalles.append(articulo.getFuente()).append("\n\n");
                
                if (articulo.getDoi() != null && !articulo.getDoi().isEmpty()) {
                    detalles.append("DOI: ").append(articulo.getDoi()).append("\n\n");
                }
                
                detalles.append("───────────────────────────────────────────────────────────────\n");
                detalles.append("RESUMEN:\n");
                detalles.append("───────────────────────────────────────────────────────────────\n\n");
                detalles.append(articulo.getResumen()).append("\n\n");
                
                detalles.append("───────────────────────────────────────────────────────────────\n");
                detalles.append("PALABRAS CLAVE:\n");
                detalles.append("───────────────────────────────────────────────────────────────\n\n");
                detalles.append(articulo.getPalabrasClave()).append("\n\n");
                
                detalles.append("───────────────────────────────────────────────────────────────\n");
                detalles.append("CITA APA:\n");
                detalles.append("───────────────────────────────────────────────────────────────\n\n");
                detalles.append(articulo.getCitaAPA()).append("\n");
                
                txtDetalles.setText(detalles.toString());
                txtDetalles.setCaretPosition(0);
            }
        }
    }
}