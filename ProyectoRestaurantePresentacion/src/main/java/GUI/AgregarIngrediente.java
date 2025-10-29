/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Dominio.Ingrediente;
import Dominio.UnidadMedida;
import GUI.ControlPresentacion.ControlPresentacion;
import dto.IngredienteProductoDTO;
import dto.NuevoIngredienteDTO;
import exception.NegocioException;
import interfaces.IIngredientesBO;
import interfaces.IIngredientesProductosBO;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author riosr
 */
public class AgregarIngrediente extends javax.swing.JPanel {
    
    private IIngredientesProductosBO ingredientesProductosBO;
    private IIngredientesBO ingredientesBO;
    private ControlPresentacion control;
    private Long idProducto;
    private Object padre;
    private List<IngredienteSeleccionPanel> panelesIngredientesDisponibles;
    private List<IngredienteSeleccionCantidad> panelesIngredientesSeleccionados;
    private static final Logger LOG = Logger.getLogger(AgregarIngrediente.class.getName());

    //Constructor
    public AgregarIngrediente(Long idProducto, IIngredientesBO ingredientesBO, IIngredientesProductosBO ingredientesProductosBO, Object padre, List<IngredienteProductoDTO> ingredientesYaSeleccionados) {
        this.idProducto = idProducto;
        this.ingredientesBO = ingredientesBO;
        this.ingredientesProductosBO = ingredientesProductosBO;
        this.padre = padre;

        initComponents();
//        setLocationRelativeTo(null);
        mostrarIngredientes();
        agregarBuscador();
        panelesIngredientesDisponibles = new ArrayList<>();
        panelesIngredientesSeleccionados = new ArrayList<>();

    }

    public void mostrar() {
        setVisible(true);
    }

    public void cerrar() {
        setVisible(false);
//        dispose();
    }

    public void cargarListaDeIngredientes(List<Ingrediente> ingredientes) {
        jPanelListaIngredientes.removeAll();
        jPanelListaIngredientes.setLayout(new BoxLayout(jPanelListaIngredientes, BoxLayout.Y_AXIS));
        jPanelListaIngredientes.setOpaque(false);

        for (Ingrediente ingrediente : ingredientes) {
            IngredienteSeleccionPanel panel = new IngredienteSeleccionPanel(ingrediente, this::agregarIngredienteSeleccionado);
            jPanelListaIngredientes.add(panel);
        }
        jPanelListaIngredientes.revalidate();
        jPanelListaIngredientes.repaint();
    }

    private void agregarIngredienteSeleccionado(Ingrediente ingrediente) {
        // Primero buscamos si ya existe un panel para este ingrediente
        Component[] componentes = jPanelListaIngredientesSeleccionados.getComponents();
        for (Component componente : componentes) {
            if (componente instanceof IngredienteSeleccionCantidad) {
                IngredienteSeleccionCantidad panelExistente = (IngredienteSeleccionCantidad) componente;
                if (panelExistente.getIngrediente().equals(ingrediente)) {
                    // Si encontramos el panel para este ingrediente, lo eliminamos
                    jPanelListaIngredientesSeleccionados.remove(panelExistente);
                    jPanelListaIngredientesSeleccionados.revalidate();
                    jPanelListaIngredientesSeleccionados.repaint();
                    return; // Salimos del método después de eliminar
                }
            }
        }

        // Si no se encontró el ingrediente, creamos un nuevo panel
        IngredienteSeleccionCantidad panelSeleccionado = new IngredienteSeleccionCantidad(ingrediente);
        jPanelListaIngredientesSeleccionados.setLayout(new BoxLayout(jPanelListaIngredientesSeleccionados, BoxLayout.Y_AXIS));
        jPanelListaIngredientesSeleccionados.add(panelSeleccionado);
        jPanelListaIngredientesSeleccionados.revalidate();
        jPanelListaIngredientesSeleccionados.repaint();
    }

    public void mostrarIngredientes() {
        try {
            List<Ingrediente> ingredientes = ingredientesBO.consultarIngredientes();
            cargarListaDeIngredientes(ingredientes);
        } catch (NegocioException e) {
            LOG.getLogger(AgregarIngrediente.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public List<IngredienteProductoDTO> obtenerIngredientesSeleccionados() {
        List<IngredienteProductoDTO> seleccionados = new ArrayList<>();

        JPanel panelIngredientes = (JPanel) jScrollPaneConfirmarCantidad.getViewport().getView();

        for (Component comp : panelIngredientes.getComponents()) {
            if (comp instanceof IngredienteSeleccionCantidad panel) {

                Ingrediente ingrediente = panel.getIngrediente();
                Float cantidad = panel.getCantidad();

                if (cantidad != null && cantidad > 0) {
                    IngredienteProductoDTO ingredienteProductoDTO = new IngredienteProductoDTO();
                    ingredienteProductoDTO.setIngrediente(ingrediente);
                    ingredienteProductoDTO.setIdIngrediente(ingrediente.getId());
                    ingredienteProductoDTO.setCantidad(cantidad);

                    boolean actualizado = false;
                    for (int i = 0; i < seleccionados.size(); i++) {
                        if (seleccionados.get(i).getIdIngrediente().equals(ingrediente.getId())) {
                            seleccionados.set(i, ingredienteProductoDTO);
                            actualizado = true;
                            break;
                        }
                    }
                    if (!actualizado) {
                        seleccionados.add(ingredienteProductoDTO);
                    }
                }

            }
        }

        return seleccionados;
    }

    private void guardarIngredientesSeleccionados(Long idProducto, List<IngredienteProductoDTO> ingredientesDTO) {
    try {
        ingredientesProductosBO.eliminarIngredientesPorProducto(idProducto);

        if (ingredientesDTO == null || ingredientesDTO.isEmpty()) {
            return;
        }

        for (IngredienteProductoDTO dto : ingredientesDTO) {
            if (dto.getCantidad() != null && dto.getCantidad() > 0) {
                dto.setIdProducto(idProducto);
                ingredientesProductosBO.registrarIngredienteProducto(dto);
            }
        }
    } catch (NegocioException e) {
        LOG.log(Level.SEVERE, "Error al guardar ingredientes", e);
        throw new RuntimeException("Error al guardar ingredientes", e);
    }
}

    private void actualizarListaIngredientes(List<Ingrediente> ingredientes) {
        jPanelListaIngredientes.removeAll();
        panelesIngredientesDisponibles.clear();

        for (Ingrediente ingrediente : ingredientes) {
            IngredienteSeleccionPanel panel = new IngredienteSeleccionPanel(ingrediente, this::agregarIngredienteSeleccionado);
            panelesIngredientesDisponibles.add(panel);
            jPanelListaIngredientes.add(panel);
        }

        jPanelListaIngredientes.revalidate();
        jPanelListaIngredientes.repaint();
    }

    private void agregarBuscador() {
        BuscadorIngredientes buscadorIngredientes = new BuscadorIngredientes(ingredientesBO, this::actualizarListaIngredientes);
        jPanelBuscador.add(buscadorIngredientes, BorderLayout.CENTER);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButtonConfirmar = new javax.swing.JButton();
        jButtonAnterior = new javax.swing.JButton();
        jPanelBuscador = new javax.swing.JPanel();
        jScrollPaneConfirmarCantidad = new javax.swing.JScrollPane();
        jPanelListaIngredientesSeleccionados = new javax.swing.JPanel();
        jScrollPaneIngredientesDisponibles = new javax.swing.JScrollPane();
        jPanelListaIngredientes = new javax.swing.JPanel();

        jPanel2.setBackground(new java.awt.Color(124, 184, 245));

        jLabel1.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        jLabel1.setText("Agregar Ingrediente");

        jButtonConfirmar.setBackground(new java.awt.Color(124, 245, 143));
        jButtonConfirmar.setFont(new java.awt.Font("Segoe UI Variable", 0, 14)); // NOI18N
        jButtonConfirmar.setText("Guardar");
        jButtonConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarActionPerformed(evt);
            }
        });

        jButtonAnterior.setBackground(new java.awt.Color(245, 124, 124));
        jButtonAnterior.setFont(new java.awt.Font("Segoe UI Variable", 0, 14)); // NOI18N
        jButtonAnterior.setText("Regresar");
        jButtonAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnteriorActionPerformed(evt);
            }
        });

        jPanelBuscador.setLayout(new javax.swing.BoxLayout(jPanelBuscador, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPaneConfirmarCantidad.setViewportView(jPanelListaIngredientesSeleccionados);

        jScrollPaneIngredientesDisponibles.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneIngredientesDisponibles.setToolTipText("");
        jScrollPaneIngredientesDisponibles.setMaximumSize(new java.awt.Dimension(410, 230));
        jScrollPaneIngredientesDisponibles.setViewportView(jPanelListaIngredientes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jButtonAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 403, Short.MAX_VALUE)
                .addComponent(jButtonConfirmar)
                .addGap(52, 52, 52))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jPanelBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPaneIngredientesDisponibles, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPaneConfirmarCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(jPanelBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneIngredientesDisponibles, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPaneConfirmarCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAnterior)
                    .addComponent(jButtonConfirmar))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    
    private void jButtonConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarActionPerformed
        // TODO add your handling code here:
        List<IngredienteProductoDTO> ingredientesSeleccionados = obtenerIngredientesSeleccionados();

        if (ingredientesSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún ingrediente.");
            return;
        }

        guardarIngredientesSeleccionados(idProducto, obtenerIngredientesSeleccionados());

        if (padre instanceof NuevoProducto nuevo) {
            nuevo.mostrarIngredientesSeleccionados(ingredientesSeleccionados);
        } else if (padre instanceof EditarProducto editar) {
            editar.mostrarIngredientesSeleccionados(ingredientesSeleccionados);
        }

        JOptionPane.showMessageDialog(this, "Ingredientes agregados correctamente.");
        this.setVisible(false);

    }//GEN-LAST:event_jButtonConfirmarActionPerformed

    private void jButtonAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnteriorActionPerformed
        // TODO add your handling code here:
        cerrar();
        control.mostrarListaProductos();
    }//GEN-LAST:event_jButtonAnteriorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnterior;
    private javax.swing.JButton jButtonConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelBuscador;
    private javax.swing.JPanel jPanelListaIngredientes;
    private javax.swing.JPanel jPanelListaIngredientesSeleccionados;
    private javax.swing.JScrollPane jScrollPaneConfirmarCantidad;
    private javax.swing.JScrollPane jScrollPaneIngredientesDisponibles;
    // End of variables declaration//GEN-END:variables
}
