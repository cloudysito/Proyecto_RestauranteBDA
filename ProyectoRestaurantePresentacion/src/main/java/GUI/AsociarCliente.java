/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Dominio.ClienteFrecuente;
import Dominio.Comanda;
import GUI.ControlPresentacion.ControlPresentacion;
import dto.ClienteFrecuenteDTO;
import dto.ComandaDTO;
import exception.NegocioException;
import interfaces.IClientesBO;
import interfaces.IComandasBO;
import java.awt.BorderLayout;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author riosr
 */
public class AsociarCliente extends javax.swing.JPanel {

    private ControlPresentacion control;
    private IClientesBO clientesBO;
    private IComandasBO comandasBO;
    private ComandaDTO comandaDTO;

    public AsociarCliente(ControlPresentacion control, IClientesBO clientesBO, IComandasBO comandasBO, ComandaDTO comandaDTO) {
        this.control = control;
        this.clientesBO = clientesBO;
        this.comandasBO = comandasBO;
        this.comandaDTO = comandaDTO;
        initComponents();
//        setLocationRelativeTo(null);

        agregarBuscador();
        mostrarInformacionClientes();

        jPanelListaClientes.setOpaque(false);
        jScrollPaneClientes.setOpaque(false);
        jScrollPaneClientes.getViewport().setOpaque(false);
        jScrollPaneClientes.setBorder(null);
        jPanelBuscador.setOpaque(false);

    }

    public void mostrar() {
        setVisible(true);
    }

    public void cerrar() {
        setVisible(false);
//        dispose();
    }

    private void agregarBuscador() {
        BuscadorClientes buscadorClientes = new BuscadorClientes(clientesBO, this::actualizarListaClientes);
        jPanelBuscador.add(buscadorClientes, BorderLayout.CENTER);
    }

    private ClienteFrecuenteDTO crearClienteDTOConPuntos(ClienteFrecuente cliente) {
        ClienteFrecuenteDTO dto = new ClienteFrecuenteDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellidoPaterno(),
                cliente.getApellidoMaterno(),
                cliente.getCorreoElectronico(),
                cliente.getTelefono(),
                cliente.getFechaRegistro()
        );

        try {
            ClienteFrecuenteDTO datosFidelidad = clientesBO.obtenerDatosFidelidad(cliente.getId());
            dto.setPuntosFidelidad(datosFidelidad.getPuntosFidelidad());
        } catch (NegocioException ex) {
            dto.setPuntosFidelidad(0);
        }

        return dto;
    }

    private void actualizarListaClientes(List<ClienteFrecuente> clientes) {
        jPanelListaClientes.removeAll();
        for (ClienteFrecuente cliente : clientes) {

            ClienteFrecuenteDTO clienteDTO = crearClienteDTOConPuntos(cliente);

            ClientePanel panel = new ClientePanel(
                    cliente,
                    "Seleccionar",
                    e -> {
                        try {
                            Long idComanda = comandaDTO.getId();
                            Comanda comanda = comandasBO.obtenerComandaPorId(idComanda);
                            comandasBO.asociarClienteAComanda(comanda, cliente);
                            JOptionPane.showMessageDialog(this, "Cliente asociado exitosamente.");
                            setVisible(false);
                        } catch (NegocioException ex) {
                            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
            );
            panel.actualizarPuntos(clienteDTO.getPuntosFidelidad());
            jPanelListaClientes.add(panel);
        }
        jPanelListaClientes.revalidate();
        jPanelListaClientes.repaint();
    }

    private void mostrarInformacionClientes() {
        jPanelListaClientes.removeAll();
        try {
            for (ClienteFrecuente cliente : clientesBO.obtenerClientes()) {

                ClienteFrecuenteDTO clienteDTO = crearClienteDTOConPuntos(cliente);

                ClientePanel panel = new ClientePanel(
                        cliente,
                        "Seleccionar",
                        e -> {
                            try {
                                Long idComanda = comandaDTO.getId();
                                Comanda comanda = comandasBO.obtenerComandaPorId(idComanda);
                                comandasBO.asociarClienteAComanda(comanda, cliente);
                                JOptionPane.showMessageDialog(this, "Cliente asociado exitosamente.");
                                setVisible(false);
                            } catch (NegocioException ex) {
                                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                );
                panel.actualizarPuntos(clienteDTO.getPuntosFidelidad());
                jPanelListaClientes.add(panel);
            }
        } catch (NegocioException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        jPanelListaClientes.revalidate();
        jPanelListaClientes.repaint();
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
        jButtonRegistrarCliente = new javax.swing.JButton();
        jPanelBuscador = new javax.swing.JPanel();
        jScrollPaneClientes = new javax.swing.JScrollPane();
        jPanelListaClientes = new javax.swing.JPanel();

        jPanel1.setBackground(new java.awt.Color(124, 184, 245));

        jLabel1.setFont(new java.awt.Font("Segoe UI Variable", 1, 48)); // NOI18N
        jLabel1.setText("Clientes");

        btnClientesRegresar.setBackground(new java.awt.Color(255, 122, 122));
        btnClientesRegresar.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        btnClientesRegresar.setText("Regresar");
        btnClientesRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesRegresarActionPerformed(evt);
            }
        });

        jButtonRegistrarCliente.setBackground(new java.awt.Color(121, 255, 161));
        jButtonRegistrarCliente.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        jButtonRegistrarCliente.setText("Agregar");
        jButtonRegistrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarClienteActionPerformed(evt);
            }
        });

        jPanelBuscador.setLayout(new java.awt.BorderLayout());

        jScrollPaneClientes.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanelListaClientes.setLayout(new javax.swing.BoxLayout(jPanelListaClientes, javax.swing.BoxLayout.Y_AXIS));
        jScrollPaneClientes.setViewportView(jPanelListaClientes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnClientesRegresar)
                        .addGap(581, 581, 581)
                        .addComponent(jButtonRegistrarCliente)
                        .addGap(0, 30, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanelBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jScrollPaneClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPaneClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClientesRegresar)
                    .addComponent(jButtonRegistrarCliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        cerrar();
        control.mostrarVentanaPrincipal();
    }//GEN-LAST:event_btnClientesRegresarActionPerformed

    private void jButtonRegistrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarClienteActionPerformed
        // TODO add your handling code here:
        cerrar();
        control.mostrarRegistrarCliente();
    }//GEN-LAST:event_jButtonRegistrarClienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClientesRegresar;
    private javax.swing.JButton jButtonRegistrarCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelBuscador;
    private javax.swing.JPanel jPanelListaClientes;
    private javax.swing.JScrollPane jScrollPaneClientes;
    // End of variables declaration//GEN-END:variables
}
