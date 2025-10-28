/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import GUI.ControlPresentacion.ControlPresentacion;
import dto.ClienteFrecuenteDTO;
import interfaces.IClientesBO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author riosr
 */
public class PerfilCliente extends javax.swing.JPanel {

    /**
     * Creates new form GUIPerfilClientes
     */
    private IClientesBO clientesBO;
    private ClienteFrecuenteDTO clienteDTO;
    private ControlPresentacion control;
    private static final Logger LOG = Logger.getLogger(PerfilCliente.class.getName());
    FontManager fontManager = new FontManager();
    
    
    /**
     * Creates new form ListaClientes
     */
    public PerfilCliente() {
        initComponents();
    }

    public PerfilCliente(ControlPresentacion control, IClientesBO clientesBO, ClienteFrecuenteDTO clienteDTO) {
        this.control = control;
        initComponents();
        this.clientesBO = clientesBO;
        this.clienteDTO = clienteDTO;
//        setLocationRelativeTo(null);
        
        cargarDatosCliente(clienteDTO);
        
        btnConfirmar1.setVisible(false);
        jTextFieldFechaRegistro.setEditable(false);
        jTextFieldPuntos.setEditable(false);
        

        Color nonEditableBg = new Color(25, 30, 52); 
        jTextFieldFechaRegistro.setBackground(nonEditableBg);
        jTextFieldPuntos.setBackground(nonEditableBg);    
        
        Color editableBg = new Color(40, 45, 72); 
        jTextFieldNombreCliente.setBackground(editableBg);
        jTextFieldApellidoPaterno.setBackground(editableBg);
        jTextFieldApellidoMaterno.setBackground(editableBg);
        jTextFieldCorreo.setBackground(editableBg);
        jTextFieldTelefono.setBackground(editableBg);

        setEditableFields(false);
        
        
        actualizarNombreCliente(clienteDTO);
        revalidate();
        repaint();

    }
    
    public void actualizarNombreCliente(ClienteFrecuenteDTO cliente) {
        
        JLabel lblNombreCliente = new JLabel();
        lblNombreCliente.setFont(fontManager.getGreatVibesRegular(105f));
        lblNombreCliente.setForeground(Color.WHITE); 

        jPanelNombreCliente.setLayout(new BorderLayout());

        lblNombreCliente.setHorizontalAlignment(JLabel.CENTER);
        lblNombreCliente.setVerticalAlignment(JLabel.CENTER);

        jPanelNombreCliente.add(lblNombreCliente, BorderLayout.CENTER);

        String nombreCompleto = cliente.getNombre();
        String primerNombre = nombreCompleto.split(" ")[0]; 
        
        if (cliente != null) {
            lblNombreCliente.setText(primerNombre + " " + cliente.getApellidoPaterno());
        } else {
            lblNombreCliente.setText("Cliente Desconocido");
        }
    }
    
    private void cargarDatosCliente(ClienteFrecuenteDTO cliente) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
        String fechaFormateada = dateFormat.format(cliente.getFechaRegistro().getTime());

        if (cliente != null) {

            jTextFieldNombreCliente.setText(cliente.getNombre());
            jTextFieldApellidoPaterno.setText(cliente.getApellidoPaterno());
            jTextFieldApellidoMaterno.setText(cliente.getApellidoMaterno());
            jTextFieldTelefono.setText(cliente.getTelefono());
            jTextFieldCorreo.setText(cliente.getCorreoElectronico());
            jTextFieldFechaRegistro.setText(fechaFormateada);

            jTextFieldPuntos.setText(String.valueOf(cliente.getPuntosFidelidad()) + " pts");
            jLabelVisitas.setText(String.valueOf(cliente.getVisitas()));
            jLabelTotalAcumulado.setText(String.format("$%.2f", cliente.getGastoTotal()));
        }
    }
    
    private void setEditableFields(boolean editable) {
        jTextFieldNombreCliente.setEditable(editable);
        jTextFieldApellidoPaterno.setEditable(editable);
        jTextFieldApellidoMaterno.setEditable(editable);
        jTextFieldTelefono.setEditable(editable);
        jTextFieldCorreo.setEditable(editable);

        Color bgColor = editable ? new Color(60, 65, 92) : new Color(40, 45, 72);
        jTextFieldNombreCliente.setBackground(bgColor);
        jTextFieldApellidoPaterno.setBackground(bgColor);
        jTextFieldApellidoMaterno.setBackground(bgColor);
        jTextFieldCorreo.setBackground(bgColor);
        jTextFieldTelefono.setBackground(bgColor);
    }
    
    public void mostrarInformacionCliente(){
        
    }
    
    public void mostrar(){
        setVisible(true);
    }
    
    public void cerrar(){
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
        jLabel2 = new javax.swing.JLabel();
        jTextFieldTelefono = new javax.swing.JTextField();
        jTextFieldApellidoMaterno = new javax.swing.JTextField();
        jTextFieldApellidoPaterno = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldNombreCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldCorreo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldPuntos = new javax.swing.JTextField();
        btnPerfilClienteRegresar1 = new javax.swing.JButton();
        btnConfirmar1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldFechaRegistro = new javax.swing.JTextField();
        jPanelNumVisitas = new javax.swing.JPanel();
        jLabelVisitas = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabelTotalAcumulado = new javax.swing.JLabel();
        jPanelNombreCliente = new javax.swing.JPanel();

        jPanel1.setBackground(new java.awt.Color(124, 184, 245));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Acumulado:");

        jTextFieldTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTelefonoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Nombre Completo");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Correo Electronico");

        jTextFieldCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCorreoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Puntos");

        btnPerfilClienteRegresar1.setBackground(new java.awt.Color(255, 122, 122));
        btnPerfilClienteRegresar1.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        btnPerfilClienteRegresar1.setText("Regresar");
        btnPerfilClienteRegresar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerfilClienteRegresar1ActionPerformed(evt);
            }
        });

        btnConfirmar1.setBackground(new java.awt.Color(153, 255, 153));
        btnConfirmar1.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        btnConfirmar1.setText("Confirmar");
        btnConfirmar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmar1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("Fecha Registro");

        jTextFieldFechaRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFechaRegistroActionPerformed(evt);
            }
        });

        jPanelNumVisitas.setBackground(new java.awt.Color(204, 204, 204));
        jPanelNumVisitas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelVisitas.setFont(fontManager.getNotoSerifCondensedRegular(64f));
        jLabelVisitas.setForeground(new java.awt.Color(255, 255, 255));
        jPanelNumVisitas.add(jLabelVisitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 80, 110));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("Telefono");

        jLabelTotalAcumulado.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelTotalAcumulado.setText("10");

        jPanelNombreCliente.setBackground(new java.awt.Color(204, 204, 204));
        jPanelNombreCliente.setLayout(new javax.swing.BoxLayout(jPanelNombreCliente, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(btnPerfilClienteRegresar1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(659, 659, 659)
                        .addComponent(btnConfirmar1)))
                .addGap(33, 33, 33))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jTextFieldNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jTextFieldApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextFieldApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jTextFieldFechaRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jTextFieldPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanelNumVisitas, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelTotalAcumulado)
                                .addGap(41, 41, 41))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jPanelNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanelNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldFechaRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPerfilClienteRegresar1)
                        .addGap(21, 21, 21))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanelNumVisitas, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabelTotalAcumulado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnConfirmar1)
                        .addGap(29, 29, 29))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTelefonoActionPerformed

    private void jTextFieldCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCorreoActionPerformed

    private void btnPerfilClienteRegresar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerfilClienteRegresar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPerfilClienteRegresar1ActionPerformed

    private void btnConfirmar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnConfirmar1ActionPerformed

    private void jTextFieldFechaRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFechaRegistroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFechaRegistroActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar1;
    private javax.swing.JButton btnPerfilClienteRegresar1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelTotalAcumulado;
    private javax.swing.JLabel jLabelVisitas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelNombreCliente;
    private javax.swing.JPanel jPanelNumVisitas;
    private javax.swing.JTextField jTextFieldApellidoMaterno;
    private javax.swing.JTextField jTextFieldApellidoPaterno;
    private javax.swing.JTextField jTextFieldCorreo;
    private javax.swing.JTextField jTextFieldFechaRegistro;
    private javax.swing.JTextField jTextFieldNombreCliente;
    private javax.swing.JTextField jTextFieldPuntos;
    private javax.swing.JTextField jTextFieldTelefono;
    // End of variables declaration//GEN-END:variables
}
