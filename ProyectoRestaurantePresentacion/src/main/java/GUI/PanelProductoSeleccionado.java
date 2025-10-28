/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Dominio.Producto;
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
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import org.w3c.dom.events.DocumentEvent;

/**
 *
 * @author santi
 */
public class PanelProductoSeleccionado extends JPanel {
     private JTextField txtCantidad;
    private JTextField txtNotas;
    private JLabel lblImporte;
    private Producto producto;

    public PanelProductoSeleccionado(Producto producto) {
        this.producto = producto;
        FontManager fontManager = new FontManager();
        Font fuente = fontManager.getNunitoRegular(16f);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        setLayout(new GridBagLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(420, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblNombre = new JLabel(producto.getNombre());
        lblNombre.setFont(fuente);
        lblNombre.setForeground(Color.WHITE);
        gbc.gridx = 0;
        add(lblNombre, gbc);

        txtCantidad = new JTextField("Cantidad", 3);
        Dimension fieldSize = new Dimension(60, 30); 
        txtCantidad.setPreferredSize(fieldSize);
        txtCantidad.setFont(fuente);
        gbc.gridx = 1;
        add(txtCantidad, gbc);

        lblImporte = new JLabel();
        lblImporte.setFont(fuente);
        lblImporte.setForeground(Color.WHITE);
        gbc.gridx = 2;
        add(lblImporte, gbc);
        actualizarImporte();

        txtNotas = new JTextField(12);
        txtNotas.setFont(fuente);
        txtNotas.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 3;
        add(txtNotas, gbc);

        txtCantidad.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                actualizarImporte();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                actualizarImporte();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                actualizarImporte();
            }
        });

    }

    private void actualizarImporte() {
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad < 0) cantidad = 0;
            double total = cantidad * producto.getPrecio();
            lblImporte.setText(String.format("$%.2f", total));
        } catch (NumberFormatException e) {
            lblImporte.setText("$0.00");
        }
    }

    public int getCantidad() {
        try {
            return Integer.parseInt(txtCantidad.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getNotas() {
        return txtNotas.getText();
    }

    public Producto getProducto() {
        return producto;
    }
    
    public void setCantidad(int cantidad) {
    txtCantidad.setText(String.valueOf(cantidad));
    actualizarImporte();
    }

    public void setNotas(String notas) {
        txtNotas.setText(notas);
    }
    
    public ProductoSeleccionadoDTO toDTO() {
        return new ProductoSeleccionadoDTO(producto.getId(), producto.getNombre(), producto.getPrecio(), getCantidad(), getNotas());
    }   
    
    public void cargarDesdeDTO(ProductoSeleccionadoDTO productoSeleccionadoDTO) {
        txtCantidad.setText(String.valueOf(productoSeleccionadoDTO.getCantidad()));
        txtNotas.setText(productoSeleccionadoDTO.getNotas());
        actualizarImporte();
    }
}
