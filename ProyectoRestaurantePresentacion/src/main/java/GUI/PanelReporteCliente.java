package GUI;

import dto.ClienteFrecuenteDTO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelReporteCliente extends JPanel {
    private final FontManager fontManager = new FontManager();
    private JLabel lblPuntos;
    private JLabel lblVisitas;
    private JLabel lblNombre;

    public PanelReporteCliente(ClienteFrecuenteDTO clienteDTO, String textoBoton, ActionListener accion) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 40, 15));
        setOpaque(false);
        setBorder(null);
        setPreferredSize(new Dimension(854, 60));

        Font fuente = fontManager.getNotoSerifCondensedRegular(22f);
        Color colorTexto = Color.WHITE;

        // Nombre completo
        lblNombre = new JLabel(clienteDTO.getNombre() + " " + clienteDTO.getApellidoPaterno() + " " + clienteDTO.getApellidoMaterno());
        lblNombre.setFont(fuente);
        lblNombre.setForeground(colorTexto);
        lblNombre.setPreferredSize(new Dimension(300, 30));
        add(lblNombre);

        // Visitas
        lblVisitas = new JLabel(String.valueOf(clienteDTO.getVisitas()));
        lblVisitas.setFont(fuente);
        lblVisitas.setForeground(colorTexto);
        lblVisitas.setPreferredSize(new Dimension(100, 30));
        add(lblVisitas);

        // Puntos
        lblPuntos = new JLabel(clienteDTO.getPuntosFidelidad() + " pts");
        lblPuntos.setFont(fuente);
        lblPuntos.setForeground(colorTexto);
        lblPuntos.setPreferredSize(new Dimension(100, 30));
        add(lblPuntos);

        // Botón
        ImageIcon icono = new ImageIcon(getClass().getResource("/images/BotonVer.png"));
        JButton boton = new JButton(icono);
        boton.setContentAreaFilled(false); 
        boton.setBorderPainted(false);    
        boton.setFocusPainted(false);
        boton.setFont(fuente);
        boton.addActionListener(accion);
        boton.setPreferredSize(new Dimension(80, 30));
        add(boton);
    }

    public void actualizarPuntos(Integer puntos) {
        if (lblPuntos != null) {
            lblPuntos.setText((puntos != null ? puntos + " pts" : "--"));
        }
    }

    public void actualizarVisitas(Integer visitas) {
        if (lblVisitas != null) {
            lblVisitas.setText((visitas != null ? String.valueOf(visitas) : "--"));
        }
    }

    public void actualizarNombre(String nombreCompleto) {
        if (lblNombre != null) {
            lblNombre.setText(nombreCompleto != null ? nombreCompleto : "--");
        }
    }
}
