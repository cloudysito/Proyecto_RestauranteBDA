/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;
import Dominio.Ingrediente;
import exception.NegocioException;
import interfaces.IIngredientesBO;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 *
 * @author leoca
 */
public class BuscadorIngredientes extends javax.swing.JPanel {

    private IIngredientesBO ingredientesBO;
    private Consumer<List<Ingrediente>> onResults;
    private static final Logger LOG = Logger.getLogger(BuscadorIngredientes.class.getName());
    FontManager fontManager = new FontManager();

    public BuscadorIngredientes(IIngredientesBO ingredientesBO, Consumer<List<Ingrediente>> onResults) {
        this.ingredientesBO = ingredientesBO;
        this.onResults = onResults;
        initComponents();
        setOpaque(false);
        jButtonBuscar.addActionListener(this::buscarIngredientes);
    }

    private void buscarIngredientes(ActionEvent e) {
        String filtroNombre = jTextFieldFiltroNombre.getText().toLowerCase();
        try {
            List<Ingrediente> ingredientesNombre = ingredientesBO.consultarIngredientesPorNombre(filtroNombre);

            onResults.accept(List.copyOf(ingredientesNombre));
        } catch (NegocioException ex) {
            LOG.severe("Hubo un error al llenar la tabla" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonBuscar = new javax.swing.JButton();
        jTextFieldFiltroNombre = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(280, 30));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setPreferredSize(new java.awt.Dimension(280, 30));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/botonBuscar.png"))); // NOI18N
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 30, 30));

        jTextFieldFiltroNombre.setPreferredSize(new java.awt.Dimension(250, 30));
        jTextFieldFiltroNombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextFieldFiltroNombreMousePressed(evt);
            }
        });
        jTextFieldFiltroNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFiltroNombreActionPerformed(evt);
            }
        });
        jTextFieldFiltroNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldFiltroNombreKeyPressed(evt);
            }
        });
        jPanel1.add(jTextFieldFiltroNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 30));

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jTextFieldFiltroNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFiltroNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFiltroNombreActionPerformed

    private void jTextFieldFiltroNombreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldFiltroNombreMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFiltroNombreMousePressed

    private void jTextFieldFiltroNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltroNombreKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ActionEvent actionEvent = new ActionEvent(evt.getSource(), evt.getID(), evt.paramString());
            buscarIngredientes(actionEvent);
        }
    }//GEN-LAST:event_jTextFieldFiltroNombreKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextFieldFiltroNombre;
    // End of variables declaration//GEN-END:variables
}
