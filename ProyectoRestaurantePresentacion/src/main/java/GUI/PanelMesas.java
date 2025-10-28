/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Dominio.EstadoMesa;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author santi
 */
public class PanelMesas extends JPanel {
    private int numeroMesa;
    private EstadoMesa estado;
    private Color colorFondo;
    private Color colorTexto;

    private static final Color DISPONIBLE_FONDO = new Color(217, 217, 217);
    private static final Color DISPONIBLE_TEXTO = new Color(14, 12, 66);
    private static final Color RESERVADA_FONDO = new Color(105, 169, 215);
    private static final Color RESERVADA_TEXTO = new Color(14, 12, 66);

    public PanelMesas(int numeroMesa, EstadoMesa estado) {
        this.numeroMesa = numeroMesa;
        this.estado = estado;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(120, 120));
        setOpaque(false);
        
        actualizarColores();
        
        JLabel mesaLabel = new JLabel(String.valueOf(numeroMesa), JLabel.CENTER);
        mesaLabel.setFont(new Font("Nunito SemiBold", Font.PLAIN, 40));
        mesaLabel.setForeground(colorTexto);
        add(mesaLabel, BorderLayout.CENTER);
    }
    
    private void actualizarColores() {
        switch (estado) {
            case DISPONIBLE:
                colorFondo = DISPONIBLE_FONDO;
                colorTexto = DISPONIBLE_TEXTO;
                break;
            case RESERVADA:
                colorFondo = RESERVADA_FONDO;
                colorTexto = RESERVADA_TEXTO;
                break;
            default:
                colorFondo = DISPONIBLE_FONDO;
                colorTexto = DISPONIBLE_TEXTO;
        }
    }
    
    public void setEstado(EstadoMesa nuevoEstado) {
        this.estado = nuevoEstado;
        actualizarColores();
        repaint();
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
    }
    
    @Override
    public boolean contains(int x, int y) {
        int diameter = 100;
        int circleX = (getWidth() - diameter) / 2;
        int circleY = (getHeight() - diameter) / 2;
        return new Ellipse2D.Float(circleX, circleY, diameter, diameter).contains(x, y);
    }
}

