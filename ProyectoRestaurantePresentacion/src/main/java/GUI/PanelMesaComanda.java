/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Dominio.Mesa;
import GUI.ControlPresentacion.ControlPresentacion;
import dto.ComandaDTO;
import dto.ProductoSeleccionadoDTO;
import interfaces.IComandasBO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.List;
import javax.swing.*;

public class PanelMesaComanda extends JButton {
    
    private IComandasBO comandasBO;
    private final Mesa mesa;
    private final VentanaInicioComanda ventana;
    private final ControlPresentacion control;
    
    private List<ProductoSeleccionadoDTO> productosSeleccionados;
    private ComandaDTO comandaDTO;
    
    private Color colorTexto = Color.BLACK;
    private Color colorFondo;
    FontManager fontManager = new FontManager();

    public PanelMesaComanda(Mesa mesa, VentanaInicioComanda ventana, ControlPresentacion control) {
        this.mesa = mesa;
        this.ventana = ventana;
        this.control = control;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(120, 120));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);

        switch (mesa.getEstado()) {
            case DISPONIBLE:
                colorFondo = new Color(217, 217, 217);
                colorTexto = new Color(14, 12, 66);
                break;
            case RESERVADA:
                colorFondo = new Color(105, 169, 215);
                colorTexto = new Color(14, 12, 66);
                break;
            default:
                colorFondo = Color.WHITE;
                colorTexto = Color.BLACK;
        }

        setText(String.valueOf(mesa.getNumeroMesa()));
        setFont(fontManager.getNunitoSemiBold(40f));
        setForeground(colorTexto);
        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.CENTER);

        addActionListener(this::manejarClicMesa);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int diameter = 100;
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;
        g2.setColor(colorFondo);
        g2.fillOval(x, y, diameter, diameter);

        g2.setColor(colorTexto);
        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(getText(), g2);
        int textX = (getWidth() - (int) r.getWidth()) / 2;
        int textY = (getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();
        g2.drawString(getText(), textX, textY);
    }

    @Override
    public boolean contains(int x, int y) {
        int diameter = 100;
        int circleX = (getWidth() - diameter) / 2;
        int circleY = (getHeight() - diameter) / 2;
        return new Ellipse2D.Float(circleX, circleY, diameter, diameter).contains(x, y);
    }

    private void manejarClicMesa(ActionEvent e) {
        System.out.println("Clic en mesa " + mesa.getNumeroMesa() + " - Estado: " + mesa.getEstado());
        switch (mesa.getEstado()) {
            case DISPONIBLE ->
                control.mostrarConfirmacionInicioComanda(mesa, ventana);
            case RESERVADA ->
            {
                boolean esComandaNueva = false;
                control.mostrarResumenComandaMesaReservada(mesa, ventana, esComandaNueva);
               // ventana.cerrar();
            }


        }
    }
}
