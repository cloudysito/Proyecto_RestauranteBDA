/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author santi
 */
public class IngredienteVisualPanel extends JPanel{
    public IngredienteVisualPanel(String nombre, float cantidad, String unidad) {

        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        setOpaque(false);
        setMaximumSize(new Dimension(600, 35));
        setPreferredSize(new Dimension(600, 35));

        Font font = new Font("Century Gothic", Font.PLAIN, 18);
        Color textColor = Color.BLACK;

        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(font);
        lblNombre.setForeground(textColor);

        JLabel lblCantidad = new JLabel(" - " + cantidad);
        lblCantidad.setFont(font);
        lblCantidad.setForeground(textColor);

        JLabel lblUnidad = new JLabel(" " + unidad);
        lblUnidad.setFont(font);
        lblUnidad.setForeground(textColor);

        add(lblNombre);
        add(lblCantidad);
        add(lblUnidad);
    }
}
