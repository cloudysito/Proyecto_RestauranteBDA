/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;
import Dominio.Producto;
import GUI.ControlPresentacion.ControlPresentacion;
import interfaces.IProductosBO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author santi
 */

public class ProductoPanel extends JPanel {

    private ControlPresentacion control;
    private IProductosBO productosBO;
    private Producto producto;
    private Long idRol;

    public ProductoPanel(ControlPresentacion control, IProductosBO productosBO, Producto producto, Long idRol) {
        this.control = control;
        this.productosBO = productosBO;
        this.producto = producto;
        this.idRol = idRol;

        setPreferredSize(new Dimension(300, 28));
        setMaximumSize(new Dimension(300, 28));
        setMinimumSize(new Dimension(300, 28));
        setLayout(new GridBagLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));

        GridBagConstraints gbc = new GridBagConstraints();
        Font font = new Font("Century Gothic", Font.PLAIN, 14);
        Color textColor = Color.WHITE;

        JLabel lblNombre = new JLabel(producto.getNombre());
        lblNombre.setFont(font);
        lblNombre.setForeground(textColor);

        JLabel lblPrecio = new JLabel(String.format("$%.2f", producto.getPrecio()));
        lblPrecio.setFont(font);
        lblPrecio.setForeground(textColor);
        
        if(idRol == 1){
        JButton btnEditar = new JButton("✎");
        btnEditar.setIcon(new ImageIcon(getClass().getResource("/images/lapizitoEditar.png")));
        btnEditar.setFont(font);
        btnEditar.setContentAreaFilled(false);
        btnEditar.setBorderPainted(false);
        btnEditar.setFocusPainted(false);
        btnEditar.setPreferredSize(new Dimension(30, 30));
        btnEditar.setMargin(new Insets(0, 0, 0, 0));
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setToolTipText("Editar producto");
        btnEditar.addActionListener(e -> {
            control.mostrarEditarProducto(producto);
        });
        
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(btnEditar, gbc);
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 0, 5);
        add(lblNombre, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 5);
        add(lblPrecio, gbc);

        
    }
}