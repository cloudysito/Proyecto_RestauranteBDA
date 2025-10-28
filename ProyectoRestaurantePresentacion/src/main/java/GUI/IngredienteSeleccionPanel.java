/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Dominio.Ingrediente;
import Dominio.UnidadMedida;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author payde
 */
public class IngredienteSeleccionPanel extends JPanel {

    private final Ingrediente ingrediente;
    private final JRadioButton radioSeleccionar;
    private final JLabel lblUnidad;
    private ActionListener seleccionListener;

    public IngredienteSeleccionPanel(Ingrediente ingrediente, Consumer<Ingrediente> onSeleccionarIngrediente) {
        this.ingrediente = ingrediente;

        setLayout(new GridBagLayout());
        setOpaque(false);
        setMaximumSize(new Dimension(280, 35));
        setPreferredSize(new Dimension(280, 35));

        Font font = new Font("Century Gothic", Font.PLAIN, 18);
        Color textColor = Color.BLACK;

        // Panel para contener nombre y unidad
        JPanel nombreUnidadPanel = new JPanel(new GridBagLayout());
        nombreUnidadPanel.setOpaque(false);

        // RadioButton con el nombre del ingrediente
        radioSeleccionar = new JRadioButton(ingrediente.getNombre());
        radioSeleccionar.setFont(font);
        radioSeleccionar.setForeground(textColor);
        radioSeleccionar.setOpaque(false);
        radioSeleccionar.setHorizontalAlignment(SwingConstants.LEFT);

        String abreviatura = "";
        if(ingrediente.getUnidadMedida() == UnidadMedida.PIEZA){
            abreviatura = "pz     ";
        }else if(ingrediente.getUnidadMedida() == UnidadMedida.GR){
            abreviatura = "gr     ";
        }else if(ingrediente.getUnidadMedida() == UnidadMedida.ML){
            abreviatura = "ml     ";
        }
        
        // Label para la unidad de medida (siempre visible)
        lblUnidad = new JLabel(abreviatura);
        lblUnidad.setFont(font);
        lblUnidad.setForeground(textColor);
        lblUnidad.setVisible(true);  // Asegurarse que sea visible

        // Configuración GridBag para nombre y unidad
        GridBagConstraints gbcNombreUnidad = new GridBagConstraints();
        gbcNombreUnidad.insets = new Insets(0, 5, 0, 5);

        // Nombre del ingrediente (se expande)
        gbcNombreUnidad.gridx = 0;
        gbcNombreUnidad.weightx = 1.0;
        gbcNombreUnidad.fill = GridBagConstraints.HORIZONTAL;
        gbcNombreUnidad.anchor = GridBagConstraints.WEST;
        nombreUnidadPanel.add(radioSeleccionar, gbcNombreUnidad);

        // Unidad de medida (fija)
        gbcNombreUnidad.gridx = 1;
        gbcNombreUnidad.weightx = 0;
        gbcNombreUnidad.fill = GridBagConstraints.NONE;
        nombreUnidadPanel.add(lblUnidad, gbcNombreUnidad);

        // Configuración GridBag para el panel principal
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 0, 5);

        // Panel de nombre+unidad (ocupa todo el espacio)
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(nombreUnidadPanel, gbc);

        radioSeleccionar.addActionListener(e -> {
            boolean selected = radioSeleccionar.isSelected();
            onSeleccionarIngrediente.accept(ingrediente);
            revalidate();
            repaint();
        });
    }

    public boolean esSeleccionado() {
        return radioSeleccionar.isSelected();
    }

    public Float getCantidad() {
        return ingrediente.getStock();  // Directamente del ingrediente
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void seleccionar(boolean estado) {
        radioSeleccionar.setSelected(estado);
        // Eliminamos la línea que ocultaba la unidad
    }

    public void setSeleccionListener(ActionListener listener) {
        this.seleccionListener = listener;
    }
}
