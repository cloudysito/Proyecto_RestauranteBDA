/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Dominio.Ingrediente;
import Dominio.UnidadMedida;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author santi
 */
public class IngredienteSeleccionCantidad extends JPanel {
    private final Ingrediente ingrediente;
    private final JLabel lblNombre;
    private final JTextField txtCantidad;
    private final JLabel lblUnidad;

    public IngredienteSeleccionCantidad(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;

        setLayout(new GridBagLayout());
        setOpaque(false);
        setMaximumSize(new Dimension(315, 35));
        setPreferredSize(new Dimension(315, 35));

        Font font = new Font("Century Gothic", Font.PLAIN, 18);
        Color textColor = Color.BLACK;

        // Label para el nombre del ingrediente
        lblNombre = new JLabel(ingrediente.getNombre());
        lblNombre.setFont(font);
        lblNombre.setForeground(textColor);
        lblNombre.setHorizontalAlignment(SwingConstants.LEFT);

        // Campo de texto para la cantidad
        txtCantidad = new JTextField(5);
        txtCantidad.setFont(font);
        txtCantidad.setPreferredSize(new Dimension(60, 30));

        // Label para la unidad de medida con abreviaturas
        String abreviatura = "";
        if(ingrediente.getUnidadMedida() == UnidadMedida.PIEZA) {
            abreviatura = "pz    ";
        } else if(ingrediente.getUnidadMedida() == UnidadMedida.GR) {
            abreviatura = "gr    ";
        } else if(ingrediente.getUnidadMedida() == UnidadMedida.ML) {
            abreviatura = "ml    ";
        }
        
        lblUnidad = new JLabel(abreviatura);
        lblUnidad.setFont(font);
        lblUnidad.setForeground(textColor);

        // Configuración del layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 0, 5);

        // Nombre del ingrediente (se expande)
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(lblNombre, gbc);

        // Campo de cantidad (tamaño fijo)
        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(txtCantidad, gbc);

        // Unidad de medida (tamaño fijo)
        gbc.gridx = 2;
        add(lblUnidad, gbc);
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public Float getCantidad() {
        try {
            return Float.parseFloat(txtCantidad.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void setCantidad(Float cantidad) {
        txtCantidad.setText(cantidad != null ? String.valueOf(cantidad) : "");
    }
}
