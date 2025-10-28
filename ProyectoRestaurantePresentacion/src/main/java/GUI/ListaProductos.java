/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Dominio.Producto;
import Dominio.TipoProducto;
import GUI.ControlPresentacion.ControlPresentacion;
import exception.NegocioException;
import interfaces.IIngredientesBO;
import interfaces.IIngredientesProductosBO;
import interfaces.IProductosBO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author riosr
 */
public class ListaProductos extends javax.swing.JPanel {

    /**
     * Creates new form GUICliente
     */
    private JPanel panelPlatillos;
    private JPanel panelPostres;
    private JPanel panelBebidas;
    private ControlPresentacion control;
    private IProductosBO productosBO;
    private Long idRol;

    public ListaProductos(ControlPresentacion control, IProductosBO productosBO, Long idRol) {
        this.control = control;
        this.idRol = idRol;
       
        initComponents();
        this.productosBO = (IProductosBO) productosBO;

        panelPlatillos = new JPanel();
        panelPostres = new JPanel();
        panelBebidas = new JPanel();

        panelPlatillos.setLayout(new javax.swing.BoxLayout(panelPlatillos, javax.swing.BoxLayout.Y_AXIS));
        panelPostres.setLayout(new javax.swing.BoxLayout(panelPostres, javax.swing.BoxLayout.Y_AXIS));
        panelBebidas.setLayout(new javax.swing.BoxLayout(panelBebidas, javax.swing.BoxLayout.Y_AXIS));
        
        configurarVisibilidadBotones();
        
        panelPlatillos.setOpaque(false);

        jScrollPanePlatillos.setOpaque(false);
        jScrollPanePlatillos.getViewport().setOpaque(false);
        jScrollPanePlatillos.setBorder(null); 

        panelPostres.setOpaque(false);
        jScrollPanePostres.setOpaque(false);
        jScrollPanePostres.getViewport().setOpaque(false);
        jScrollPanePostres.setBorder(null);

        panelBebidas.setOpaque(false);
        jScrollPaneBebidas.setOpaque(false);
        jScrollPaneBebidas.getViewport().setOpaque(false);
        jScrollPaneBebidas.setBorder(null);
        
        jScrollPanePlatillos.setViewportView(panelPlatillos);
        jScrollPanePostres.setViewportView(panelPostres);
        jScrollPaneBebidas.setViewportView(panelBebidas);
    }
    
     private void configurarVisibilidadBotones() {
        if(idRol == 2 || idRol == 3){
            jButtonAnadirProducto.setVisible(false);
        }
    }
     
     public void mostrar(){
        setVisible(true);
    }
    
    public void cerrar(){
        setVisible(false);
//        dispose();
    }
    public void mostrarProductos(){
        try {
            panelPlatillos.removeAll();
            panelPostres.removeAll();
            panelBebidas.removeAll();
            
            List<Producto> platillos = productosBO.obtenerProductoPorTipo(TipoProducto.PLATILLO);
            List<Producto> postres = productosBO.obtenerProductoPorTipo(TipoProducto.POSTRE);
            List<Producto> bebidas = productosBO.obtenerProductoPorTipo(TipoProducto.BEBIDA);
            
            
            for (Producto producto : platillos) {
                panelPlatillos.add(new ProductoPanel(control, productosBO, producto,idRol));
            }
            
            for (Producto producto : postres) {
                panelPostres.add(new ProductoPanel(control, productosBO, producto,idRol));
            }
            
            for (Producto producto : bebidas) {
                panelBebidas.add(new ProductoPanel(control, productosBO, producto,idRol));
            }
            
            panelPlatillos.revalidate();
            panelPostres.revalidate();
            panelBebidas.revalidate();
            
            panelPlatillos.repaint();
            panelPostres.repaint();
            panelBebidas.repaint();
        } catch (NegocioException ex) {
            Logger.getLogger(ListaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarResultados(List<Producto> resultados) {
        panelPlatillos.removeAll();
        panelPostres.removeAll();
        panelBebidas.removeAll();

        for (Producto producto : resultados) {
            ProductoPanel panel = new ProductoPanel(control, productosBO, producto,idRol);
            switch (producto.getTipoProducto()) {
                case PLATILLO -> panelPlatillos.add(panel);
                case POSTRE -> panelPostres.add(panel);
                case BEBIDA -> panelBebidas.add(panel);
            }
        }
            panelPlatillos.revalidate();
            panelPostres.revalidate();
            panelBebidas.revalidate();
            panelPlatillos.repaint();
            panelPostres.repaint();
            panelBebidas.repaint();
    }
    
     private void jButtonRegresarActionPerformed(java.awt.event.ActionEvent evt) {                                                
              
        control.mostrarVentanaPrincipal();
        cerrar(); 
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
        txtClientesBuscar = new javax.swing.JTextField();
        btnClientesBuscar = new javax.swing.JButton();
        jButtonAnadirProducto = new javax.swing.JButton();
        jScrollPanePlatillos = new javax.swing.JScrollPane();
        jScrollPanePostres = new javax.swing.JScrollPane();
        jScrollPaneBebidas = new javax.swing.JScrollPane();

        jPanel1.setBackground(new java.awt.Color(124, 184, 245));

        jLabel1.setFont(new java.awt.Font("Segoe UI Variable", 1, 40)); // NOI18N
        jLabel1.setText("Productos");

        btnClientesRegresar.setBackground(new java.awt.Color(255, 122, 122));
        btnClientesRegresar.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        btnClientesRegresar.setText("Regresar");
        btnClientesRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesRegresarActionPerformed(evt);
            }
        });

        txtClientesBuscar.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnClientesBuscar.setBackground(new java.awt.Color(161, 173, 185));
        btnClientesBuscar.setText("Buscar");

        jButtonAnadirProducto.setBackground(new java.awt.Color(102, 255, 102));
        jButtonAnadirProducto.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jButtonAnadirProducto.setText("Añadir");
        jButtonAnadirProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnadirProductoActionPerformed(evt);
            }
        });

        jScrollPanePlatillos.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jScrollPanePostres.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jScrollPaneBebidas.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                        .addGap(224, 224, 224))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(306, 306, 306)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnClientesRegresar)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtClientesBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnClientesBuscar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jButtonAnadirProducto)
                .addGap(77, 77, 77))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPanePlatillos, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPanePostres, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPaneBebidas, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonAnadirProducto)
                        .addGap(8, 8, 8)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtClientesBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClientesBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(95, 95, 95)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPaneBebidas, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPanePlatillos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                        .addComponent(jScrollPanePostres, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(btnClientesRegresar)
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 38, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnClientesRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesRegresarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClientesRegresarActionPerformed

    private void jButtonAnadirProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnadirProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAnadirProductoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClientesBuscar;
    private javax.swing.JButton btnClientesRegresar;
    private javax.swing.JButton jButtonAnadirProducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPaneBebidas;
    private javax.swing.JScrollPane jScrollPanePlatillos;
    private javax.swing.JScrollPane jScrollPanePostres;
    private javax.swing.JTextField txtClientesBuscar;
    // End of variables declaration//GEN-END:variables
}
