/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Dominio.Ingrediente;
import GUI.ControlPresentacion.ControlPresentacion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author riosr
 */
public class IngredientePanel  extends  JPanel{
     private Long idRol;

    public IngredientePanel(Ingrediente ingrediente, Long idRol) {
        setPreferredSize(new Dimension(910, 50));
        setLayout(new GridBagLayout());
        setOpaque(false);
        this.idRol = idRol;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font font;

        try {
            InputStream is = getClass().getResourceAsStream("/fonts/NotoSerif_Condensed-Regular.ttf"); 
            if (is == null) {
                throw new IOException("No se encontró la fuente");
            }

            font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 20f);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            font = new Font("SansSerif", Font.PLAIN, 18); // Fallback
        }

        Color textColor = Color.WHITE;

        JLabel lblNombre = new JLabel(String.format("%-20s", ingrediente.getNombre()));
        lblNombre.setFont(font);
        lblNombre.setForeground(textColor);
        lblNombre.setPreferredSize(new Dimension(150, 25));

        String stockText = (ingrediente.getStock() % 1 == 0) ? String.format("%.0f", ingrediente.getStock()) : String.format("%.2f", ingrediente.getStock());
        JLabel lblStock = new JLabel(stockText);
        lblStock.setFont(font);
        lblStock.setForeground(textColor);
        lblStock.setPreferredSize(new Dimension(60, 20));

        JLabel lblUnidad = new JLabel(String.format("%-15s", ingrediente.getUnidadMedida().getDescripcion()));
        lblUnidad.setFont(font);
        lblUnidad.setForeground(textColor);
        lblUnidad.setPreferredSize(new Dimension(100, 25));

        if (idRol == 1) {
            JButton btnEdit = new JButton();
            btnEdit.setPreferredSize(new Dimension(32, 32));
            btnEdit.setContentAreaFilled(false);
            btnEdit.setBorderPainted(false);
            btnEdit.setFocusPainted(false);

            try {
                ImageIcon iconEditar = new ImageIcon(getClass().getResource("/images/botonEditar.png"));
                // Establecer el tamaño del icono para que coincida con el tamaño del botón
                iconEditar = new ImageIcon(iconEditar.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
                btnEdit.setIcon(iconEditar);
            } catch (Exception e) {
                e.printStackTrace();
            }

            btnEdit.addActionListener(e -> {
                ControlPresentacion control = new ControlPresentacion();
                control.mostrarEditarNombreIngrediente(ingrediente);
            });

            JButton btnAdd = new JButton();
            btnAdd.setPreferredSize(new Dimension(32, 32));
            btnAdd.setContentAreaFilled(false);
            btnAdd.setBorderPainted(false);
            btnAdd.setFocusPainted(false);

            try {
                ImageIcon iconAgregar = new ImageIcon(getClass().getResource("/images/botonAgregarMesas.png"));
                // Establecer el tamaño del icono para que coincida con el tamaño del botón
                iconAgregar = new ImageIcon(iconAgregar.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
                btnAdd.setIcon(iconAgregar);
            } catch (Exception e) {
                e.printStackTrace();
            }

            btnAdd.addActionListener(e -> {
                ControlPresentacion control = new ControlPresentacion();
                control.mostrarAñadirStockIngrediente(ingrediente);
            });

            gbc.gridx = 5;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(8, 0, 8, 2);
            btnEdit.setPreferredSize(new Dimension(32, 32));
            add(btnEdit, gbc);

            gbc.gridx = 6;
            gbc.insets = new Insets(8, 0, 8, 0);
            btnAdd.setPreferredSize(new Dimension(32, 32));
            add(btnAdd, gbc);

        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        add(lblNombre, gbc);

        gbc.gridx = 1;
        add(lblStock, gbc);

        gbc.gridx = 2;
        add(new JLabel(" - "), gbc);

        gbc.gridx = 3;
        add(lblUnidad, gbc);

        gbc.gridx = 4;
        gbc.weightx = 1;
        add(Box.createHorizontalGlue(), gbc);

    }

    @Override
    protected void paintComponent(Graphics g) {
        // Pinta fondo semi-transparente
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(80, 80, 80, 150));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }
}


