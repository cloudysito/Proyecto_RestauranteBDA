/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import dto.ProductoSeleccionadoDTO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class PanelResumen extends JPanel {
    
    private final FontManager fontManager = new FontManager();
    
    public PanelResumen(ProductoSeleccionadoDTO producto) {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(450, 60));
        setMaximumSize(new Dimension(Short.MAX_VALUE, 60));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
        setOpaque(false);

        Font fuenteBold = fontManager.getNunitoBold(16f);
        Color colorTexto = new Color(32, 56, 107);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 0, 15); 

        int[] columnWidths = {130, 40, 60, 70, 120}; 

        JLabel lblNombre = new JLabel(producto.getNombreProducto());
        lblNombre.setFont(fuenteBold);
        lblNombre.setForeground(colorTexto);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        lblNombre.setPreferredSize(new Dimension(columnWidths[0], 30));
        add(lblNombre, gbc);

        JLabel lblCantidad = new JLabel(String.valueOf(producto.getCantidad()));
        lblCantidad.setFont(fuenteBold);
        lblCantidad.setForeground(colorTexto);
        lblCantidad.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 1;
        gbc.weightx = 0;
        lblCantidad.setPreferredSize(new Dimension(columnWidths[1], 30));
        add(lblCantidad, gbc);

        JLabel lblPrecio = new JLabel(String.format("$%.2f", producto.getPrecioUnitario()));
        lblPrecio.setFont(fuenteBold);
        lblPrecio.setForeground(colorTexto);
        lblPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 2;
        gbc.weightx = 0;
        lblPrecio.setPreferredSize(new Dimension(columnWidths[2], 30));
        add(lblPrecio, gbc);

        Float importe = producto.getCantidad() * producto.getPrecioUnitario();
        JLabel lblImporte = new JLabel(String.format("$%.2f", importe));
        lblImporte.setFont(fuenteBold);
        lblImporte.setForeground(colorTexto);
        lblImporte.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 3;
        gbc.weightx = 0;
        lblImporte.setPreferredSize(new Dimension(columnWidths[3], 30));
        add(lblImporte, gbc);

        JTextArea txtNotas = new JTextArea(producto.getNotas());
        txtNotas.setFont(fontManager.getNunitoBold(14f));
        txtNotas.setForeground(colorTexto);
        txtNotas.setOpaque(false);
        txtNotas.setEditable(false);
        txtNotas.setLineWrap(true);
        txtNotas.setWrapStyleWord(true);
        txtNotas.setBorder(null);
        
        JScrollPane scrollNotas = new JScrollPane(txtNotas);
        scrollNotas.setBorder(null);
        scrollNotas.setOpaque(false);
        scrollNotas.getViewport().setOpaque(false);
        scrollNotas.setPreferredSize(new Dimension(columnWidths[4], 40));
        scrollNotas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        gbc.gridx = 4;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(scrollNotas, gbc);
    }
}

