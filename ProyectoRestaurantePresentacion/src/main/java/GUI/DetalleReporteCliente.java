/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import GUI.ControlPresentacion.ControlPresentacion;
import dto.ClienteFrecuenteDTO;
import interfaces.IClientesBO;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *
 * @author riosr
 */
public class DetalleReporteCliente extends javax.swing.JPanel {

    private IClientesBO clientesBO;
    private ClienteFrecuenteDTO clienteDTO;
    private ControlPresentacion control;
    private static final Logger LOG = Logger.getLogger(DetalleReporteCliente.class.getName());
    FontManager fontManager = new FontManager();
    
    /**
     * Creates new form DetalleReporteCliente
     */
    public DetalleReporteCliente(ControlPresentacion control, IClientesBO clientesBO, ClienteFrecuenteDTO clienteDTO) {
        this.control = control;
        initComponents();
        this.clientesBO = clientesBO;
        this.clienteDTO = clienteDTO;
//        setLocationRelativeTo(null);
        
        cargarDatosCliente(clienteDTO);
        configurarCamposNoEditables();
        
        
        revalidate();
        repaint();
    }

    
    private void configurarCamposNoEditables() {
        Color nonEditableBg = new Color(33, 32, 33); 
        
        jTextFieldNombreCliente.setEditable(false);
        jTextFieldApellidoPaterno.setEditable(false);
        jTextFieldApellidoMaterno.setEditable(false);
        jTextFieldPuntos.setEditable(false);
        
        jTextFieldNombreCliente.setBackground(nonEditableBg);
        jTextFieldApellidoPaterno.setBackground(nonEditableBg);
        jTextFieldApellidoMaterno.setBackground(nonEditableBg);
        jTextFieldPuntos.setBackground(nonEditableBg);
    }
    
    private void cargarDatosCliente(ClienteFrecuenteDTO cliente) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 

        String fechaRegistroFormateada = dateFormat.format(cliente.getFechaRegistro().getTime());
        
        String ultimaVisitaFormateada = "No registrada";
        if (cliente.getUltimaVisita() != null) {
            ultimaVisitaFormateada = dateFormat.format(cliente.getUltimaVisita().getTime());
        }

        jTextFieldNombreCliente.setText(cliente.getNombre());
        jTextFieldApellidoPaterno.setText(cliente.getApellidoPaterno());
        jTextFieldApellidoMaterno.setText(cliente.getApellidoMaterno());
        jTextFieldPuntos.setText(String.valueOf(cliente.getPuntosFidelidad()) + " pts");
        
        jLabelVisitas.setText(String.valueOf(cliente.getVisitas()));
        jLabelTotalAcumulado.setText(String.format("$%.2f", cliente.getGastoTotal()));
    }
    
    private void generarReportePDF(ClienteFrecuenteDTO clienteDTO) {
        Document documento = new Document();

        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar reporte de cliente");
            fileChooser.setSelectedFile(new File("Reporte_Cliente_" + 
                clienteDTO.getNombre().replace(" ", "_") + ".pdf"));

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File outputFile = fileChooser.getSelectedFile();

                PdfWriter.getInstance(documento, new FileOutputStream(outputFile));
                documento.open();

                Paragraph titulo = new Paragraph("INFORME DE CLIENTE FRECUENTE", 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingAfter(20f);
                documento.add(titulo);

                Paragraph subtitulo = new Paragraph("Generado el: " + 
                    new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()), 
                    FontFactory.getFont(FontFactory.HELVETICA, 10));
                subtitulo.setAlignment(Element.ALIGN_CENTER);
                subtitulo.setSpacingAfter(30f);
                documento.add(subtitulo);

                PdfPTable tablaDatos = new PdfPTable(2);
                tablaDatos.setWidthPercentage(90);
                tablaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablaDatos.setSpacingBefore(20f);
                tablaDatos.setSpacingAfter(30f);

                float[] columnWidths = {1f, 3f};
                tablaDatos.setWidths(columnWidths);

                PdfPCell headerCell = new PdfPCell(new Phrase("DATOS PERSONALES", 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                headerCell.setColspan(2);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(new com.itextpdf.text.BaseColor(200, 200, 200));
                headerCell.setPadding(8f);
                tablaDatos.addCell(headerCell);

                agregarFilaTabla(tablaDatos, "Nombre completo:", 
                    clienteDTO.getNombre() + " " + clienteDTO.getApellidoPaterno() + 
                    (clienteDTO.getApellidoMaterno() != null ? " " + clienteDTO.getApellidoMaterno() : ""));

                agregarFilaTabla(tablaDatos, "Correo electrónico:", 
                    clienteDTO.getCorreoElectronico() != null ? clienteDTO.getCorreoElectronico() : "No registrado");

                agregarFilaTabla(tablaDatos, "Teléfono:", 
                    clienteDTO.getTelefono() != null ? clienteDTO.getTelefono() : "No registrado");

                agregarFilaTabla(tablaDatos, "Fecha de registro:", 
                    new SimpleDateFormat("dd/MM/yyyy").format(clienteDTO.getFechaRegistro().getTime()));

                documento.add(tablaDatos);

                PdfPTable tablaEstadisticas = new PdfPTable(2);
                tablaEstadisticas.setWidthPercentage(90);
                tablaEstadisticas.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablaEstadisticas.setSpacingBefore(20f);
                tablaEstadisticas.setSpacingAfter(30f);
                tablaEstadisticas.setWidths(columnWidths);

                PdfPCell statsHeader = new PdfPCell(new Phrase("ESTADÍSTICAS DE VISITAS", 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                statsHeader.setColspan(2);
                statsHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
                statsHeader.setBackgroundColor(new com.itextpdf.text.BaseColor(200, 200, 200));
                statsHeader.setPadding(8f);
                tablaEstadisticas.addCell(statsHeader);

                agregarFilaTabla(tablaEstadisticas, "Total de visitas:", 
                    String.valueOf(clienteDTO.getVisitas()));

                agregarFilaTabla(tablaEstadisticas, "Total gastado:", 
                    String.format("$%,.2f", clienteDTO.getGastoTotal()));

                agregarFilaTabla(tablaEstadisticas, "Puntos de fidelidad:", 
                    String.valueOf(clienteDTO.getPuntosFidelidad()) + " pts");

                agregarFilaTabla(tablaEstadisticas, "Última visita:", 
                    clienteDTO.getUltimaVisita() != null ? 
                    new SimpleDateFormat("dd/MM/yyyy").format(clienteDTO.getUltimaVisita().getTime()) : 
                    "No registrada");

                documento.add(tablaEstadisticas);

                Paragraph nota = new Paragraph("Este reporte fue generado automáticamente por el sistema de Wonderland Restaurantes", 
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8));
                nota.setAlignment(Element.ALIGN_CENTER);
                nota.setSpacingBefore(20f);
                documento.add(nota);

                documento.close();

                JOptionPane.showMessageDialog(this, 
                    "<html><b>¡Reporte generado con éxito!</b><br>" + 
                    "Guardado en: " + outputFile.getAbsolutePath() + "</html>", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al generar el PDF: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            LOG.severe("Error al generar PDF: " + e.getMessage());
        }
    }
    
    private void agregarFilaTabla(PdfPTable tabla, String etiqueta, String valor) {
        PdfPCell celdaEtiqueta = new PdfPCell(new Phrase(etiqueta, 
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
        celdaEtiqueta.setBorder(Rectangle.NO_BORDER);
        celdaEtiqueta.setPadding(5f);

        PdfPCell celdaValor = new PdfPCell(new Phrase(valor, 
            FontFactory.getFont(FontFactory.HELVETICA, 10)));
        celdaValor.setBorder(Rectangle.NO_BORDER);
        celdaValor.setPadding(5f);

        tabla.addCell(celdaEtiqueta);
        tabla.addCell(celdaValor);
    }
    
    
    public void mostrar() {
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
        jLabel1 = new javax.swing.JLabel();
        btnClientesRegresar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldNombreCliente = new javax.swing.JTextField();
        jTextFieldApellidoPaterno = new javax.swing.JTextField();
        jTextFieldApellidoMaterno = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldTelefono = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldCorreoElectronico = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldPuntos = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabelVisitas = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(145, 192, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Detalle Reporte Cliente");

        btnClientesRegresar.setBackground(new java.awt.Color(255, 122, 122));
        btnClientesRegresar.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        btnClientesRegresar.setText("Regresar");
        btnClientesRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesRegresarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Nombre completo:");

        jTextFieldNombreCliente.setText("jTextField1");
        jTextFieldNombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreClienteActionPerformed(evt);
            }
        });

        jTextFieldApellidoPaterno.setText("jTextField2");

        jTextFieldApellidoMaterno.setText("jTextField3");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Telefono:");

        jTextFieldTelefono.setText("jTextField4");

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Correo electronico:");

        jTextFieldCorreoElectronico.setText("jTextField5");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Puntos:");

        jTextFieldPuntos.setText("jTextField6");

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Visitas:");

        jLabelVisitas.setBackground(new java.awt.Color(0, 0, 0));
        jLabelVisitas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelVisitas.setText("Visitas:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btnClientesRegresar)
                        .addGap(275, 275, 275)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldNombreCliente)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextFieldApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addComponent(jTextFieldApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextFieldTelefono)
                            .addComponent(jTextFieldCorreoElectronico))
                        .addGap(167, 167, 167)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabelVisitas))))
                .addContainerGap(188, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClientesRegresar)
                    .addComponent(jLabel1))
                .addGap(79, 79, 79)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(39, 39, 39)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelVisitas))
                .addGap(31, 31, 31)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(144, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void jTextFieldNombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNombreClienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClientesRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelVisitas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextFieldApellidoMaterno;
    private javax.swing.JTextField jTextFieldApellidoPaterno;
    private javax.swing.JTextField jTextFieldCorreoElectronico;
    private javax.swing.JTextField jTextFieldNombreCliente;
    private javax.swing.JTextField jTextFieldPuntos;
    private javax.swing.JTextField jTextFieldTelefono;
    // End of variables declaration//GEN-END:variables
}
