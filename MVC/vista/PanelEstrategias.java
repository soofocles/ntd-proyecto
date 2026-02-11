package vista;

import controlador.ArticuloControlador;
import controlador.EstrategiaControlador;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.Articulo;
import modelo.EstrategiaInversion;


// Panel para gestionar (CRUD) las estrategias de inversión
// Permite crear, ver, editar y eliminar estrategias usando `EstrategiaControlador`
public class PanelEstrategias extends JPanel {
    
    private EstrategiaControlador controlador;
    private ArticuloControlador articuloControlador;
    private JTable tablaEstrategias;
    private DefaultTableModel modeloTabla;
    
    // Constructor: prepara controladores, componentes y carga las estrategias
    public PanelEstrategias() {
        controlador = new EstrategiaControlador();
        articuloControlador = new ArticuloControlador();
        inicializarComponentes();
        cargarEstrategias();
    }
    
    // Inicializa la tabla, botones y layout del panel de estrategias
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("Gestión de Estrategias de Inversión (CRUD)");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(231, 76, 60));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);
        
        String[] columnas = {"ID", "Nombre", "Tipo", "Riesgo", "Retorno (%)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaEstrategias = new JTable(modeloTabla);
        tablaEstrategias.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaEstrategias.setRowHeight(30);
        tablaEstrategias.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaEstrategias.getTableHeader().setBackground(new Color(231, 76, 60));
        tablaEstrategias.getTableHeader().setForeground(Color.BLACK);
        tablaEstrategias.getTableHeader().setReorderingAllowed(false);
        
        tablaEstrategias.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaEstrategias.getColumnModel().getColumn(1).setPreferredWidth(300);
        tablaEstrategias.getColumnModel().getColumn(2).setPreferredWidth(150);
        tablaEstrategias.getColumnModel().getColumn(3).setPreferredWidth(80);
        tablaEstrategias.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        JScrollPane scrollTabla = new JScrollPane(tablaEstrategias);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Estrategias Registradas"));
        add(scrollTabla, BorderLayout.CENTER);

        // En el constructor o método inicializador
        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);
              

    }
    
// Método para crear botones con colores, hover y pressed
private JButton crearBoton(String texto, Color color) {
    JButton boton = new JButton(texto) {
        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isPressed()) {
                g.setColor(color.darker());
            } else if (getModel().isRollover()) {
                g.setColor(color.brighter());
            } else {
                g.setColor(color);
            }
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
    };
    boton.setFont(new Font("Arial", Font.BOLD, 12));
    boton.setForeground(Color.WHITE);
    boton.setFocusPainted(false);
    boton.setBorderPainted(false);
    boton.setContentAreaFilled(false);
    boton.setOpaque(false);
    return boton;
}

// Panel de botones con los 5 botones pintados
private JPanel crearPanelBotones() {
    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
    panelBotones.setBackground(new Color(236, 240, 241));

    JButton btnCrear = crearBoton("CREAR", new Color(46, 204, 113));
    JButton btnVer = crearBoton("VER", new Color(52, 152, 219));
    JButton btnEditar = crearBoton("EDITAR", new Color(243, 156, 18));
    JButton btnEliminar = crearBoton("ELIMINAR", new Color(231, 76, 60));
    JButton btnRefrescar = crearBoton("REFRESCAR", new Color(149, 165, 166));

    // Acciones de ejemplo
    btnCrear.addActionListener(e -> mostrarDialogoCrear());
    btnVer.addActionListener(e -> verDetallesEstrategia());
    btnEditar.addActionListener(e -> mostrarDialogoEditar());
    btnEliminar.addActionListener(e -> eliminarEstrategia());
    btnRefrescar.addActionListener(e -> cargarEstrategias());

    panelBotones.add(btnCrear);
    panelBotones.add(btnVer);
    panelBotones.add(btnEditar);
    panelBotones.add(btnEliminar);
    panelBotones.add(btnRefrescar);

    return panelBotones;
}

    
    // Solicita al controlador la lista de estrategias y la muestra en la tabla
    private void cargarEstrategias() {
        modeloTabla.setRowCount(0);
        List<EstrategiaInversion> estrategias = controlador.obtenerTodasLasEstrategias();
        
        for (EstrategiaInversion e : estrategias) {
            Object[] fila = {
                e.getId(),
                e.getNombre(),
                e.getTipoEstrategia(),
                e.getNivelRiesgo(),
                String.format("%.2f%%", e.getRetornoEsperado())
            };
            modeloTabla.addRow(fila);
        }
    }
    
    // Muestra un diálogo para crear una nueva estrategia y la guarda si es válido
    private void mostrarDialogoCrear() {
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                     "Crear Nueva Estrategia", true);
        dialogo.setSize(600, 550);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = crearPanelFormulario(null);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setBackground(new Color(46, 204, 113));
        btnGuardar.setForeground(Color.WHITE);
        
        btnGuardar.addActionListener(e -> {
            EstrategiaInversion estrategia = obtenerDatosFormulario(panel);
            if (estrategia != null) {
                if (controlador.crearEstrategia(estrategia)) {
                    JOptionPane.showMessageDialog(dialogo,
                        "Estrategia creada exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    dialogo.dispose();
                    cargarEstrategias();
                } else {
                    JOptionPane.showMessageDialog(dialogo,
                        "Error al crear la estrategia",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnGuardar);
        
        dialogo.add(panel, BorderLayout.CENTER);
        dialogo.add(panelBoton, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }
    
    // Muestra los detalles completos de la estrategia seleccionada en un diálogo
    private void verDetallesEstrategia() {
        int fila = tablaEstrategias.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                "Por favor seleccione una estrategia",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) modeloTabla.getValueAt(fila, 0);
        EstrategiaInversion estrategia = controlador.obtenerEstrategiaPorId(id);
        
        if (estrategia != null) {
            StringBuilder detalles = new StringBuilder();
            detalles.append("═══════════════════════════════════════════════\n");
            detalles.append("  DETALLES DE LA ESTRATEGIA\n");
            detalles.append("═══════════════════════════════════════════════\n\n");
            detalles.append("ID: ").append(estrategia.getId()).append("\n\n");
            detalles.append("Nombre: ").append(estrategia.getNombre()).append("\n\n");
            detalles.append("Tipo: ").append(estrategia.getTipoEstrategia()).append("\n\n");
            detalles.append("Nivel de Riesgo: ").append(estrategia.getNivelRiesgo()).append("\n\n");
            detalles.append("Retorno Esperado: ").append(String.format("%.2f%%", 
                estrategia.getRetornoEsperado())).append("\n\n");
            detalles.append("─────────────────────────────────────────────────\n");
            detalles.append("Descripción:\n\n");
            detalles.append(estrategia.getDescripcion()).append("\n\n");
            detalles.append("─────────────────────────────────────────────────\n");
            detalles.append("Tecnologías Utilizadas:\n\n");
            detalles.append(estrategia.getTecnologiasUtilizadas()).append("\n\n");
            
            if (estrategia.getArticuloRelacionadoId() != null) {
                Articulo art = articuloControlador.obtenerArticuloPorId(
                    estrategia.getArticuloRelacionadoId());
                if (art != null) {
                    detalles.append("─────────────────────────────────────────────────\n");
                    detalles.append("Artículo Relacionado:\n\n");
                    detalles.append(art.getTitulo()).append("\n");
                }
            }
            
            JTextArea txtDetalles = new JTextArea(detalles.toString());
            txtDetalles.setEditable(false);
            txtDetalles.setFont(new Font("Monospaced", Font.PLAIN, 11));
            txtDetalles.setLineWrap(true);
            txtDetalles.setWrapStyleWord(true);
            
            JScrollPane scroll = new JScrollPane(txtDetalles);
            scroll.setPreferredSize(new Dimension(600, 400));
            
            JOptionPane.showMessageDialog(this, scroll, 
                "Detalles de la Estrategia", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Muestra un diálogo para editar la estrategia seleccionada
    private void mostrarDialogoEditar() {
        int fila = tablaEstrategias.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                "Por favor seleccione una estrategia para editar",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) modeloTabla.getValueAt(fila, 0);
        EstrategiaInversion estrategia = controlador.obtenerEstrategiaPorId(id);
        
        if (estrategia == null) return;
        
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                     "Editar Estrategia", true);
        dialogo.setSize(600, 550);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = crearPanelFormulario(estrategia);
        
        JButton btnGuardar = new JButton("Actualizar");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setBackground(new Color(243, 156, 18));
        btnGuardar.setForeground(Color.WHITE);
        
        btnGuardar.addActionListener(e -> {
            EstrategiaInversion estrategiaActualizada = obtenerDatosFormulario(panel);
            if (estrategiaActualizada != null) {
                estrategiaActualizada.setId(id);
                if (controlador.actualizarEstrategia(estrategiaActualizada)) {
                    JOptionPane.showMessageDialog(dialogo,
                        "Estrategia actualizada exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    dialogo.dispose();
                    cargarEstrategias();
                } else {
                    JOptionPane.showMessageDialog(dialogo,
                        "Error al actualizar la estrategia",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnGuardar);
        
        dialogo.add(panel, BorderLayout.CENTER);
        dialogo.add(panelBoton, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }
    
    // Elimina la estrategia seleccionada después de pedir confirmación al usuario
    private void eliminarEstrategia() {
        int fila = tablaEstrategias.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                "Por favor seleccione una estrategia para eliminar",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nombre = (String) modeloTabla.getValueAt(fila, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar la estrategia?\n\n" + nombre,
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (controlador.eliminarEstrategia(id)) {
                JOptionPane.showMessageDialog(this,
                    "Estrategia eliminada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarEstrategias();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar la estrategia",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Crea el formulario (panel) usado para crear/editar una estrategia
    // Si `estrategia` es null se prepara vacío, si no, se llena con sus valores
    private JPanel crearPanelFormulario(EstrategiaInversion estrategia) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Campos
        JTextField txtNombre = new JTextField(30);
        JTextArea txtDescripcion = new JTextArea(4, 30);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        
        JTextField txtTipo = new JTextField(30);
        JComboBox<String> cmbRiesgo = new JComboBox<>(new String[]{"Bajo", "Medio", "Alto"});
        JTextArea txtTecnologias = new JTextArea(3, 30);
        txtTecnologias.setLineWrap(true);
        txtTecnologias.setWrapStyleWord(true);
        
        JTextField txtRetorno = new JTextField(30);
        
        JComboBox<String> cmbArticulo = new JComboBox<>();
        cmbArticulo.addItem("Sin artículo relacionado");
        List<Articulo> articulos = articuloControlador.obtenerTodosLosArticulos();
        for (Articulo art : articulos) {
            cmbArticulo.addItem(art.getId() + " - " + art.getTitulo());
        }
        
        if (estrategia != null) {
            txtNombre.setText(estrategia.getNombre());
            txtDescripcion.setText(estrategia.getDescripcion());
            txtTipo.setText(estrategia.getTipoEstrategia());
            cmbRiesgo.setSelectedItem(estrategia.getNivelRiesgo());
            txtTecnologias.setText(estrategia.getTecnologiasUtilizadas());
            txtRetorno.setText(String.valueOf(estrategia.getRetornoEsperado()));
            
            if (estrategia.getArticuloRelacionadoId() != null) {
                for (int i = 0; i < cmbArticulo.getItemCount(); i++) {
                    String item = cmbArticulo.getItemAt(i);
                    if (item.startsWith(estrategia.getArticuloRelacionadoId() + " -")) {
                        cmbArticulo.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
        
        int row = 0;
        agregarCampo(panel, gbc, row++, "Nombre:", txtNombre);
        agregarCampo(panel, gbc, row++, "Descripción:", new JScrollPane(txtDescripcion));
        agregarCampo(panel, gbc, row++, "Tipo de Estrategia:", txtTipo);
        agregarCampo(panel, gbc, row++, "Nivel de Riesgo:", cmbRiesgo);
        agregarCampo(panel, gbc, row++, "Tecnologías:", new JScrollPane(txtTecnologias));
        agregarCampo(panel, gbc, row++, "Retorno Esperado (%):", txtRetorno);
        agregarCampo(panel, gbc, row++, "Artículo Relacionado:", cmbArticulo);

        panel.putClientProperty("txtNombre", txtNombre);
        panel.putClientProperty("txtDescripcion", txtDescripcion);
        panel.putClientProperty("txtTipo", txtTipo);
        panel.putClientProperty("cmbRiesgo", cmbRiesgo);
        panel.putClientProperty("txtTecnologias", txtTecnologias);
        panel.putClientProperty("txtRetorno", txtRetorno);
        panel.putClientProperty("cmbArticulo", cmbArticulo);
        
        return panel;
    }
    
    // Método auxiliar para añadir una fila de etiqueta + campo al formulario
    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int row, 
                             String label, JComponent campo) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lbl, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(campo, gbc);
    }
    
    // Lee los valores del formulario, valida y devuelve un objeto EstrategiaInversion
    // Retorna null si hay errores de validación (por ejemplo, campos vacíos o número inválido)
    @SuppressWarnings("unchecked")
    private EstrategiaInversion obtenerDatosFormulario(JPanel panel) {
        try {
            JTextField txtNombre = (JTextField) panel.getClientProperty("txtNombre");
            JTextArea txtDescripcion = (JTextArea) panel.getClientProperty("txtDescripcion");
            JTextField txtTipo = (JTextField) panel.getClientProperty("txtTipo");
            JComboBox<String> cmbRiesgo = (JComboBox<String>) panel.getClientProperty("cmbRiesgo");
            JTextArea txtTecnologias = (JTextArea) panel.getClientProperty("txtTecnologias");
            JTextField txtRetorno = (JTextField) panel.getClientProperty("txtRetorno");
            JComboBox<String> cmbArticulo = (JComboBox<String>) panel.getClientProperty("cmbArticulo");
            
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String tipo = txtTipo.getText().trim();
            String riesgo = (String) cmbRiesgo.getSelectedItem();
            String tecnologias = txtTecnologias.getText().trim();
            String retornoStr = txtRetorno.getText().trim();
            
            if (nombre.isEmpty() || descripcion.isEmpty() || tipo.isEmpty() || 
                tecnologias.isEmpty() || retornoStr.isEmpty()) {
                JOptionPane.showMessageDialog(panel,
                    "Por favor complete todos los campos",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
                return null;
            }
            
            double retorno = Double.parseDouble(retornoStr);
            
            Integer articuloId = null;
            if (cmbArticulo.getSelectedIndex() > 0) {
                String seleccion = (String) cmbArticulo.getSelectedItem();
                articuloId = Integer.parseInt(seleccion.split(" - ")[0]);
            }
            
            return new EstrategiaInversion(nombre, descripcion, tipo, riesgo, 
                                          tecnologias, retorno, articuloId);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel,
                "El retorno esperado debe ser un número válido",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}