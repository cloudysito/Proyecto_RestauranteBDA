/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Dominio.Mesa;
import Dominio.Producto;
import GUI.ControlPresentacion.ControlPresentacion;
import dto.ComandaDTO;
import dto.DetalleComandaDTO;
import dto.ProductoSeleccionadoDTO;
import exception.NegocioException;
import interfaces.IDetallesComandasBO;
import interfaces.IProductosBO;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.SystemColor.control;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author riosr
 */
public class SeleccionarProductosComanda extends javax.swing.JPanel {

    private ControlPresentacion control;
    private Mesa mesa;
    private ComandaDTO comandaDTO;
    private IProductosBO productosBO;
    private IDetallesComandasBO detallesComandasBO;
    private DetalleComandaDTO detalleComandaDTO;
    private boolean esComandaNueva;

    private static final Logger LOG = Logger.getLogger(SeleccionarProductosComanda.class.getName());

    FontManager fontManager = new FontManager();

    private List<ProductoSeleccionadoDTO> productosSeleccionados;
    public SeleccionarProductosComanda() {
        initComponents();
    }

    public SeleccionarProductosComanda(ControlPresentacion control, Mesa mesa, ComandaDTO comandaDTO, IProductosBO productosBO, IDetallesComandasBO detallesComandasBO, DetalleComandaDTO detalleComandaDTO, boolean EsComandaNueva, List<ProductoSeleccionadoDTO> productosSeleccionados) {
        this.control = control;
        this.mesa = mesa;
        this.comandaDTO = comandaDTO;
        this.productosBO = productosBO;
        this.detallesComandasBO = detallesComandasBO;
        this.detalleComandaDTO = detalleComandaDTO;
        this.esComandaNueva = esComandaNueva;
        this.productosSeleccionados=productosSeleccionados;
        initComponents();
//        setLocationRelativeTo(null);
        
        if (comandaDTO != null && comandaDTO.getFechaHoraCreacion() != null) {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd - MM - yyyy");
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH : mm : ss");

            Calendar fechaCreacion = comandaDTO.getFechaHoraCreacion();
            Date fecha = fechaCreacion.getTime();

            JLabel lblFecha = new JLabel("Fecha : " + formatoFecha.format(fecha));
            lblFecha.setFont(fontManager.getNunitoSemiBold(18f));
            lblFecha.setForeground(Color.WHITE);

            JLabel lblHora = new JLabel("Hora : " + formatoHora.format(fecha));
            lblHora.setFont(fontManager.getNunitoSemiBold(18f));
            lblHora.setForeground(Color.WHITE);

            jPanelFecha.removeAll(); 
            jPanelFecha.setLayout(new BorderLayout());
            jPanelFecha.add(lblFecha, BorderLayout.CENTER);
            jPanelFecha.revalidate();
            jPanelFecha.repaint();

            jPanelHora.removeAll(); 
            jPanelHora.setLayout(new BorderLayout());
            jPanelHora.add(lblHora, BorderLayout.CENTER);
            jPanelHora.revalidate();
            jPanelHora.repaint();
        } 
        
        mostrarProductos();
        cargarProductosSeleccionados();

        if (!esComandaNueva && (productosSeleccionados == null || productosSeleccionados.isEmpty())) {
            cargarProductosComandaExistente(comandaDTO);
        }

        jPanelListaProductos.setOpaque(false);
        jScrollPaneListsProductos.setOpaque(false);
        jScrollPaneListsProductos.getViewport().setOpaque(false);

        jPanelProductosSeleccionados.setOpaque(false);
        jScrollPaneProductosSeleccionados.setOpaque(false);
        jScrollPaneProductosSeleccionados.getViewport().setOpaque(false);
        jScrollPaneProductosSeleccionados.setBorder(null);

        BuscadorProductosComandas buscador = new BuscadorProductosComandas(productosBO, this);
        jPanelBuscador.setLayout(new BorderLayout());
        jPanelBuscador.add(buscador, BorderLayout.CENTER);
        buscador.setOpaque(false);
        
        if(comandaDTO.getCliente() != null){
            jButtonAsociarCliente.setVisible(false);
        }

        JLabel lblMesa = new JLabel("Mesa : " + (mesa != null ? mesa.getNumeroMesa() : "Desconocida"));
        lblMesa.setFont(fontManager.getNunitoSemiBold(18f));
        lblMesa.setForeground(Color.WHITE);

        JLabel lblFolio = new JLabel("Folio : " + (comandaDTO.getFolio() != null ? comandaDTO.getFolio() : "N/A"));
        lblFolio.setFont(fontManager.getNunitoSemiBold(18f));
        lblFolio.setForeground(Color.WHITE);

        jPanelFolio.setOpaque(false);
        jPanelFecha.setOpaque(false);
        jPanelHora.setOpaque(false);
        jPanelMesa.setOpaque(false);
        jPanelBuscador.setOpaque(false);

        jPanelCliente.setOpaque(false);

        jPanelFolio.add(lblFolio, BorderLayout.CENTER);
        jPanelMesa.add(lblMesa);
        
       
    }

    public void cargarListaDeProductos(List<Producto> productos) {
        jPanelListaProductos.removeAll();
        jPanelListaProductos.setLayout(new BoxLayout(jPanelListaProductos, BoxLayout.Y_AXIS));
        jPanelListaProductos.setOpaque(false);

        for (Producto producto : productos) {
            PanelProductoComanda panel = new PanelProductoComanda(producto, this::agregarProductoSeleccionado);
            jPanelListaProductos.add(panel);
        }

        jPanelListaProductos.revalidate();
        jPanelListaProductos.repaint();
    }

    private void agregarProductoSeleccionado(Producto producto) {
        PanelProductoSeleccionado panelSeleccionado = new PanelProductoSeleccionado(producto);
        jPanelProductosSeleccionados.setLayout(new BoxLayout(jPanelProductosSeleccionados, BoxLayout.Y_AXIS));
        jPanelProductosSeleccionados.add(panelSeleccionado);
        jPanelProductosSeleccionados.revalidate();
        jPanelProductosSeleccionados.repaint();
    }

    public void mostrarProductos() {
        try {
            List<Producto> productos = productosBO.obtenerTodos();
            cargarListaDeProductos(productos);
        } catch (NegocioException e) {
            LOG.getLogger(SeleccionarProductosComanda.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void mostrarResultados(List<Producto> resultados) {
        jPanelListaProductos.removeAll();
        jPanelListaProductos.setLayout(new BoxLayout(jPanelListaProductos, BoxLayout.Y_AXIS));
        jPanelListaProductos.setOpaque(false);

        for (Producto producto : resultados) {
            PanelProductoComanda panel = new PanelProductoComanda(producto, this::agregarProductoSeleccionado);
            jPanelListaProductos.add(panel);
        }

        jPanelListaProductos.revalidate();
        jPanelListaProductos.repaint();
    }

    public void cargarProductosComandaExistente(ComandaDTO comandaDTO) {
        try {
            List<ProductoSeleccionadoDTO> productosExistentes = detallesComandasBO.obtenerDetalleComandaPorComanda(comandaDTO);

            jPanelProductosSeleccionados.removeAll();

            for (ProductoSeleccionadoDTO productoDTO : productosExistentes) {
                agregarProductoDesdeDTO(productoDTO);
            }

            jPanelProductosSeleccionados.revalidate();
            jPanelProductosSeleccionados.repaint();
        } catch (NegocioException e) {
            LOG.log(Level.SEVERE, "Error al cargar productos existentes", e);
            JOptionPane.showMessageDialog(this, "Error al cargar productos de la comanda", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void agregarProductoDesdeDTO(ProductoSeleccionadoDTO productoSeleccionadoDTO) {
        Producto producto = productoSeleccionadoDTO.getProducto();
        PanelProductoSeleccionado panel = new PanelProductoSeleccionado(producto);

        panel.setCantidad(productoSeleccionadoDTO.getCantidad());
        panel.setNotas(productoSeleccionadoDTO.getNotas());

        jPanelProductosSeleccionados.setLayout(new BoxLayout(jPanelProductosSeleccionados, BoxLayout.Y_AXIS));
        jPanelProductosSeleccionados.add(panel);
        jPanelProductosSeleccionados.revalidate();
        jPanelProductosSeleccionados.repaint();
    }

    private void cargarProductosSeleccionados() {
        if (productosSeleccionados == null) {
            return;
        }

        for (ProductoSeleccionadoDTO productoSeleccionadoDTO : productosSeleccionados) {
            Producto producto = new Producto();
        producto.setId(productoSeleccionadoDTO.getIdProducto());
        producto.setNombre(productoSeleccionadoDTO.getNombreProducto());
        producto.setPrecio(productoSeleccionadoDTO.getPrecioUnitario());
        
            PanelProductoSeleccionado panel = new PanelProductoSeleccionado(producto);
            panel.cargarDesdeDTO(productoSeleccionadoDTO);
            jPanelProductosSeleccionados.setLayout(new BoxLayout(jPanelProductosSeleccionados, BoxLayout.Y_AXIS));
            jPanelProductosSeleccionados.add(panel);
        }

        jPanelProductosSeleccionados.revalidate();
        jPanelProductosSeleccionados.repaint();
    }
    
    public void mostrar() {
        setVisible(true);
    }

    public void cerrar() {
        setVisible(false);
//        dispose();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnClientesRegresar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanelFecha = new javax.swing.JPanel();
        jPanelHora = new javax.swing.JPanel();
        jPanelMesa = new javax.swing.JPanel();
        jPanelFolio = new javax.swing.JPanel();
        jPanelBuscador = new javax.swing.JPanel();
        jScrollPaneListsProductos = new javax.swing.JScrollPane();
        jPanelListaProductos = new javax.swing.JPanel();
        jScrollPaneProductosSeleccionados = new javax.swing.JScrollPane();
        jPanelProductosSeleccionados = new javax.swing.JPanel();
        jPanelCliente = new javax.swing.JPanel();
        jButtonAsociarCliente = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(124, 184, 245));

        jLabel1.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        jLabel1.setText("Seleccionar Producto");

        btnClientesRegresar.setBackground(new java.awt.Color(255, 122, 122));
        btnClientesRegresar.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        btnClientesRegresar.setText("Regresar");
        btnClientesRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesRegresarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel2.setText("Productos Seleccionados");

        jPanelFolio.setLayout(new java.awt.BorderLayout());

        jScrollPaneListsProductos.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneListsProductos.setViewportView(jPanelListaProductos);

        jPanelProductosSeleccionados.setLayout(new javax.swing.BoxLayout(jPanelProductosSeleccionados, javax.swing.BoxLayout.Y_AXIS));
        jScrollPaneProductosSeleccionados.setViewportView(jPanelProductosSeleccionados);

        jButtonAsociarCliente.setContentAreaFilled(false);
        jButtonAsociarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAsociarClienteActionPerformed(evt);
            }
        });
        jButtonAsociarCliente.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jButtonAsociarClientePropertyChange(evt);
            }
        });
        jPanelCliente.add(jButtonAsociarCliente);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnClientesRegresar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneListsProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(126, 126, 126))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jScrollPaneProductosSeleccionados, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jPanelFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jPanelBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(39, 39, 39)))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanelHora, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(201, 201, 201))
                            .addComponent(jPanelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanelMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(134, 134, 134)
                        .addComponent(jPanelFolio, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanelHora, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jPanelBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addComponent(jLabel2)))
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPaneListsProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(jScrollPaneProductosSeleccionados, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanelFolio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)))
                .addComponent(btnClientesRegresar))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnClientesRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesRegresarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClientesRegresarActionPerformed

    private void jButtonAsociarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAsociarClienteActionPerformed
//        if (ComandaDTO != null && ComandaDTO.getId() != null) {
//            control.mostrarAsociarCliente(ComandaDTO);
//        } else {
//            JOptionPane.showMessageDialog(this, "La comanda aún no está inicializada correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
    }//GEN-LAST:event_jButtonAsociarClienteActionPerformed

    private void jButtonAsociarClientePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jButtonAsociarClientePropertyChange

    }//GEN-LAST:event_jButtonAsociarClientePropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClientesRegresar;
    private javax.swing.JButton jButtonAsociarCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelBuscador;
    private javax.swing.JPanel jPanelCliente;
    private javax.swing.JPanel jPanelFecha;
    private javax.swing.JPanel jPanelFolio;
    private javax.swing.JPanel jPanelHora;
    private javax.swing.JPanel jPanelListaProductos;
    private javax.swing.JPanel jPanelMesa;
    private javax.swing.JPanel jPanelProductosSeleccionados;
    private javax.swing.JScrollPane jScrollPaneListsProductos;
    private javax.swing.JScrollPane jScrollPaneProductosSeleccionados;
    // End of variables declaration//GEN-END:variables
}
