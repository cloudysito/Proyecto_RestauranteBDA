/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Dominio.Comanda;
import Dominio.EstadoMesa;
import Dominio.Mesa;
import Dominio.Producto;
import GUI.ControlPresentacion.ControlPresentacion;
import dto.ComandaDTO;
import dto.DetalleComandaDTO;
import dto.ProductoSeleccionadoDTO;
import exception.NegocioException;
import interfaces.IComandasBO;
import interfaces.IDetallesComandasBO;
import interfaces.IMesasBO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author riosr
 */
public class ResumenComanda extends javax.swing.JPanel {

    private ControlPresentacion control;
    private ComandaDTO comandaDTO;
    private List<ProductoSeleccionadoDTO> productosSeleccionados;
    private IDetallesComandasBO detallesComandasBO;
    private IComandasBO comandasBO;
    private IMesasBO mesasBO;
    private Mesa mesa;
    private boolean esComandaNueva;
    
    FontManager fontManager = new FontManager();

    public ResumenComanda() {
        initComponents();
    }

    public ResumenComanda(ControlPresentacion control, List<ProductoSeleccionadoDTO> productosSeleccionados, ComandaDTO comandaDTO, 
                          IDetallesComandasBO detallesComandasBO, boolean esComandaNueva, IComandasBO comandasBO, Mesa mesa, IMesasBO mesasBO) {        
        this.control = control;
        this.productosSeleccionados = productosSeleccionados;
        this.comandaDTO = comandaDTO;
        this.detallesComandasBO = detallesComandasBO;
        this.comandasBO = comandasBO;
        this.esComandaNueva = esComandaNueva;
        this.mesa = mesa;
        this.mesasBO = mesasBO;
        initComponents();
//        setLocationRelativeTo(null);

        cargarProductos(productosSeleccionados);
        configurarVisibilidadBotones();

        jPanelResumen.setOpaque(false);
        jScrollPaneResumen.setBorder(null);
        jScrollPaneResumen.getViewport().setOpaque(false);
        jScrollPaneResumen.setOpaque(false);
        jPanelTotal.setOpaque(false);
        
        if (comandaDTO != null && comandaDTO.getFechaHoraCreacion() != null) {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd - MM - yyyy");
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH : mm : ss");

            Date fecha = comandaDTO.getFechaHoraCreacion().getTime();

            JLabel lblFecha = new JLabel("Fecha : " + formatoFecha.format(fecha));
            lblFecha.setFont(fontManager.getNunitoSemiBold(18f));
            lblFecha.setForeground(Color.WHITE);

            JLabel lblHora = new JLabel("Hora : " + formatoHora.format(fecha));
            lblHora.setFont(fontManager.getNunitoSemiBold(18f));
            lblHora.setForeground(Color.WHITE);

            JLabel lblFolio = new JLabel("Folio : " + (comandaDTO.getFolio() != null ? comandaDTO.getFolio() : "N/A"));
            lblFolio.setFont(fontManager.getNunitoSemiBold(18f));
            lblFolio.setForeground(Color.WHITE);

            Mesa mesaComanda = comandaDTO.getMesa();
            JLabel lblMesa = new JLabel("Mesa : " + (mesaComanda != null ? mesaComanda.getNumeroMesa() : "Desconocida"));
            lblMesa.setFont(fontManager.getNunitoSemiBold(18f));
            lblMesa.setForeground(Color.WHITE);

            jPanelFolio.add(lblFolio);
            jPanelMesa.add(lblMesa);
            jPanelFecha.add(lblFecha);
            jPanelHora.add(lblHora);
        }
    }

    private void cargarProductos(List<ProductoSeleccionadoDTO> productos) {
        jPanelResumen.setLayout(new BoxLayout(jPanelResumen, BoxLayout.Y_AXIS));

        Float total = 0f;

        for (ProductoSeleccionadoDTO producto : productos) {

            jPanelResumen.add(new PanelResumen(producto));
            total += producto.getCantidad() * producto.getPrecioUnitario();
        }

        Color colorTexto = new Color(32, 56, 107);

        JLabel lblTotal = new JLabel(String.format("$%.2f", total));
        jPanelTotal.add(lblTotal, BorderLayout.WEST);
        lblTotal.setFont(fontManager.getNunitoBold(22f));
        lblTotal.setForeground(colorTexto);

        configurarVisibilidadBotones();
        jPanelResumen.revalidate();
    }

    public void confirmarComanda() {
        for (ProductoSeleccionadoDTO producto : productosSeleccionados) {
            DetalleComandaDTO detalleComandaDTO = new DetalleComandaDTO();
            detalleComandaDTO.setCantidadProducto(producto.getCantidad());
            detalleComandaDTO.setPrecio(producto.getPrecioUnitario());
            detalleComandaDTO.setNota(producto.getNotas());

            Producto productoEntidad = new Producto();
            productoEntidad.setId(producto.getIdProducto());
            detalleComandaDTO.setProducto(productoEntidad);

            Comanda comandaEntidad = new Comanda();
            comandaEntidad.setId(comandaDTO.getId());
            detalleComandaDTO.setComanda(comandaEntidad);

            try {
                if (esComandaNueva == false) {
                    this.detallesComandasBO.guardarDetalleComanda(detalleComandaDTO);
                } else if (esComandaNueva == true) {
                    this.detallesComandasBO.ActualizarDetallesComanda(detalleComandaDTO);
                }

                JOptionPane.showMessageDialog(this, "Se registro el detalle de la comanda con exito", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            } catch (NegocioException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar un detalle: " + e.getMessage());
            }
        }
    }

    private void marcarComandaEntregada(ComandaDTO comandaDTO) {
        try {
            this.comandasBO.modificarEstadoComanda(comandaDTO);

            mesasBO.cambiarEstadoMesa(mesa.getId(), EstadoMesa.DISPONIBLE);

            JOptionPane.showMessageDialog(this, "Comanda marcada como ENTREGADA");
//            this.dispose();
            this.setVisible(false);
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelarComanda(ComandaDTO comandaDTO) {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea cancelar esta comanda?",
                "Confirmar cancelacion",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                this.comandasBO.cancelarComanda(comandaDTO);

                mesasBO.cambiarEstadoMesa(mesa.getId(), EstadoMesa.DISPONIBLE);

                JOptionPane.showMessageDialog(this, "Comanda cancelada exitosamente");
//                this.dispose();
            this.setVisible(false);

            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al cancelar: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void configurarVisibilidadBotones() {
        jButtonEditarComanda.setVisible(!esComandaNueva);
        jButtonEditarComanda.setVisible(!esComandaNueva);
        jButtonComandaEntregada.setVisible(!esComandaNueva);
        jButtonAnterior.setVisible(esComandaNueva);
        
        if(comandaDTO.getCliente() != null){
            jButtonAsociarCliente.setVisible(false);
        }

//        jButtonConfirmar.setVisible(esComandaNueva);
    }

  
    public void mostrar() {
        setVisible(true);
    }

    public void cerrar() {
        setVisible(false);
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
        jButtonEliminarComanda = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanelFolio = new javax.swing.JPanel();
        jPanelTotal = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButtonConfirmar = new javax.swing.JButton();
        jPanelFecha = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanelHora = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanelMesa = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPaneResumen = new javax.swing.JScrollPane();
        jPanelResumen = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jButtonEditarComanda = new javax.swing.JButton();
        MesasRegresar2 = new javax.swing.JButton();
        jButtonAnterior = new javax.swing.JButton();
        jButtonComandaEntregada = new javax.swing.JButton();
        jButtonAsociarCliente = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(124, 184, 245));

        jLabel1.setFont(new java.awt.Font("Segoe UI Variable", 1, 48)); // NOI18N
        jLabel1.setText("Resumen Comanda");

        jButtonEliminarComanda.setBackground(new java.awt.Color(255, 122, 122));
        jButtonEliminarComanda.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jButtonEliminarComanda.setText("Borrar");
        jButtonEliminarComanda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarComandaActionPerformed(evt);
            }
        });

        jLabel4.setFont(fontManager.getNotoSerifCondensedRegular(20f));
        jLabel4.setText("Importe");

        jPanelFolio.setOpaque(false);

        jPanelTotal.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(fontManager.getNotoSerifCondensedRegular(20f));
        jLabel2.setText("Q");

        jButtonConfirmar.setContentAreaFilled(false);
        jButtonConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarActionPerformed(evt);
            }
        });

        jPanelFecha.setOpaque(false);

        jLabel5.setFont(fontManager.getNotoSerifCondensedRegular(20f));
        jLabel5.setText("Producto");

        jPanelHora.setOpaque(false);

        jLabel6.setFont(fontManager.getNotoSerifCondensedRegular(20f));
        jLabel6.setText("Notas");

        jPanelMesa.setOpaque(false);

        jLabel3.setFont(fontManager.getNotoSerifCondensedRegular(20f));
        jLabel3.setText("Precio");

        jScrollPaneResumen.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanelResumen.setLayout(new javax.swing.BoxLayout(jPanelResumen, javax.swing.BoxLayout.Y_AXIS));
        jScrollPaneResumen.setViewportView(jPanelResumen);

        jLabel7.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        jLabel7.setText("Total:");

        jButtonEditarComanda.setBackground(new java.awt.Color(0, 204, 255));
        jButtonEditarComanda.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jButtonEditarComanda.setText("Editar");
        jButtonEditarComanda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarComandaActionPerformed(evt);
            }
        });

        MesasRegresar2.setBackground(new java.awt.Color(153, 255, 153));
        MesasRegresar2.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        MesasRegresar2.setText("Confirmar");
        MesasRegresar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MesasRegresar2ActionPerformed(evt);
            }
        });

        jButtonAnterior.setBackground(new java.awt.Color(255, 122, 122));
        jButtonAnterior.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jButtonAnterior.setText("Salir");
        jButtonAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnteriorActionPerformed(evt);
            }
        });

        jButtonComandaEntregada.setBackground(new java.awt.Color(0, 204, 255));
        jButtonComandaEntregada.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jButtonComandaEntregada.setText("Entregada");
        jButtonComandaEntregada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonComandaEntregadaActionPerformed(evt);
            }
        });

        jButtonAsociarCliente.setBackground(new java.awt.Color(0, 204, 255));
        jButtonAsociarCliente.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jButtonAsociarCliente.setText("AsociarCliente");
        jButtonAsociarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAsociarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(476, 476, 476)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(344, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jButtonAnterior)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(MesasRegresar2)
                .addGap(69, 69, 69))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonComandaEntregada)
                        .addGap(36, 36, 36)
                        .addComponent(jButtonEditarComanda)
                        .addGap(32, 32, 32)
                        .addComponent(jButtonEliminarComanda)
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonAsociarCliente)
                        .addGap(68, 68, 68))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jPanelHora, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(280, 280, 280)
                            .addComponent(jPanelFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(280, 280, 280)
                            .addComponent(jLabel5)
                            .addGap(121, 121, 121)
                            .addComponent(jLabel2)
                            .addGap(41, 41, 41)
                            .addComponent(jLabel3)
                            .addGap(47, 47, 47)
                            .addComponent(jLabel4)
                            .addGap(58, 58, 58)
                            .addComponent(jLabel6))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(250, 250, 250)
                            .addComponent(jScrollPaneResumen, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(550, 550, 550)
                            .addComponent(jPanelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(280, 280, 280)
                            .addComponent(jPanelMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(240, 240, 240)
                            .addComponent(jPanelFolio, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(210, 210, 210)
                            .addComponent(jButtonConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jButtonAsociarCliente)
                .addGap(58, 58, 58)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEliminarComanda)
                    .addComponent(jButtonEditarComanda)
                    .addComponent(jButtonComandaEntregada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 265, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAnterior)
                    .addComponent(MesasRegresar2))
                .addGap(21, 21, 21))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanelHora, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanelFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(240, 240, 240)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(jLabel6))
                    .addGap(4, 4, 4)
                    .addComponent(jScrollPaneResumen, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(jPanelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(20, 20, 20)
                    .addComponent(jPanelMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(jPanelFolio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jButtonConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
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

    private void jButtonEliminarComandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarComandaActionPerformed
        // TODO add your handling code here:
        cancelarComanda(comandaDTO);
        control.mostrarVentanaInicioComanda();
    }//GEN-LAST:event_jButtonEliminarComandaActionPerformed

    private void jButtonConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarActionPerformed
        try{
            confirmarComanda();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Volviendo a la pantalla principal");
        }
        control.mostrarVentanaPrincipal();
        cerrar();
    }//GEN-LAST:event_jButtonConfirmarActionPerformed

    private void jButtonEditarComandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarComandaActionPerformed
        // TODO add your handling code here:
        control.mostrarSeleccionarProductosComanda(mesa, comandaDTO, false, productosSeleccionados);
        for (ProductoSeleccionadoDTO producto : productosSeleccionados) {
            System.out.println("Producto ID: " + producto.getIdProducto());
        }
        cerrar();
    }//GEN-LAST:event_jButtonEditarComandaActionPerformed

    private void MesasRegresar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MesasRegresar2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MesasRegresar2ActionPerformed

    private void jButtonAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnteriorActionPerformed
        // TODO add your handling code here:
        control.mostrarSeleccionarProductosComanda(
                comandaDTO.getMesa(), 
                comandaDTO, 
                false, 
                productosSeleccionados);            
        cerrar();
    }//GEN-LAST:event_jButtonAnteriorActionPerformed

    private void jButtonComandaEntregadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonComandaEntregadaActionPerformed
        // TODO add your handling code here:
         marcarComandaEntregada(comandaDTO);
        control.mostrarVentanaPrincipal();
    }//GEN-LAST:event_jButtonComandaEntregadaActionPerformed

    private void jButtonAsociarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAsociarClienteActionPerformed
        // TODO add your handling code here:
        if (comandaDTO != null && comandaDTO.getId() != null) {
            control.mostrarAsociarCliente(comandaDTO);
        } else {
            JOptionPane.showMessageDialog(this, "La comanda aún no está inicializada correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAsociarClienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton MesasRegresar2;
    private javax.swing.JButton jButtonAnterior;
    private javax.swing.JButton jButtonAsociarCliente;
    private javax.swing.JButton jButtonComandaEntregada;
    private javax.swing.JButton jButtonConfirmar;
    private javax.swing.JButton jButtonEditarComanda;
    private javax.swing.JButton jButtonEliminarComanda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelFecha;
    private javax.swing.JPanel jPanelFolio;
    private javax.swing.JPanel jPanelHora;
    private javax.swing.JPanel jPanelMesa;
    private javax.swing.JPanel jPanelResumen;
    private javax.swing.JPanel jPanelTotal;
    private javax.swing.JScrollPane jScrollPaneResumen;
    // End of variables declaration//GEN-END:variables
}
