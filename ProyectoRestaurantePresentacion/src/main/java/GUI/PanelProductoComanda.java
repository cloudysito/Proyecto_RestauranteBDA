/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Dominio.Producto;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author santi
 */
public class PanelProductoComanda extends JPanel {
    
    FontManager fontManager = new FontManager();
    
    public PanelProductoComanda(Producto producto, Consumer<Producto> onSeleccionarProducto) {
        
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(520, 80)); 
        setMaximumSize(new Dimension(Short.MAX_VALUE, 80));
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        setOpaque(false); 
        
        Font fuenteNombre = fontManager.getNotoSerifCondensedRegular(24f);
        Font fuenteGrande = fontManager.getNunitoBold(16f);
        Font fuentePequena = fontManager.getNunitoRegular(16f);
        Color colorTexto = Color.WHITE;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 10, 4, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblNombre = new JLabel("<html>" + producto.getNombre() + "</html>");
        lblNombre.setFont(fuenteNombre);
        lblNombre.setForeground(colorTexto);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(lblNombre, gbc);

        gbc.gridx = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        add(crearEtiquetaConValor("Tipo", producto.getTipoProducto().toString(), fuentePequena, colorTexto), gbc);

        gbc.gridx = 2;
        add(crearEtiquetaConValor("Precio", String.format("$%.2f", producto.getPrecio()), fuentePequena, colorTexto), gbc);

        JButton btnSeleccionar = new JButton("Seleccionar");
        btnSeleccionar.setFont(fuentePequena);
        btnSeleccionar.setPreferredSize(new Dimension(110, 28));
        btnSeleccionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        btnSeleccionar.addActionListener(e -> {
            onSeleccionarProducto.accept(producto);
        });

        add(btnSeleccionar, gbc);
    }

    private JPanel crearEtiquetaConValor(String etiqueta, String valor, Font fuente, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(fuente.deriveFont(Font.BOLD));
        lblEtiqueta.setForeground(color);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(fuente);
        lblValor.setForeground(color);

        panel.add(lblEtiqueta);
        panel.add(lblValor);

        return panel;
    }
    
}
