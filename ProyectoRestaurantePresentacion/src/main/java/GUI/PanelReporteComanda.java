/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import dto.ComandaDTO;
import dto.DetalleComandaDTO;
import exception.NegocioException;
import interfaces.IDetallesComandasBO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelReporteComanda extends JPanel {
    
    private final ComandaDTO comanda;
    private final IDetallesComandasBO detallesComandasBO;

     public PanelReporteComanda(ComandaDTO comanda, IDetallesComandasBO detallesComandasBO) {
        this.comanda = comanda;
        this.detallesComandasBO = detallesComandasBO;

        FontManager fontManager = new FontManager();

        setPreferredSize(new Dimension(854, 50));
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.WHITE));
        setBackground(new Color(19, 28, 54));
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(3, 10, 3, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        String folio = comanda.getFolio() != null ? comanda.getFolio() : "--";
        String nombreCliente = comanda.getCliente() != null ? comanda.getCliente().getNombreCompleto() : "Sin cliente";
        String estado = comanda.getEstadoComanda() != null ? comanda.getEstadoComanda().toString() : "SIN ESTADO";
        String mesa = comanda.getMesa() != null ? comanda.getMesa().getNumeroMesa().toString() : "--";

        Calendar calendar = comanda.getFechaHoraCreacion();
        String fechaStr = calendar != null
                ? DateTimeFormatter.ofPattern("dd/MM/yy HH:mm").format(calendar.toInstant().atZone(ZoneId.systemDefault()))
                : "--";

        float totalVenta = calcularTotalVenta(comanda);
        String totalFormateado = String.format("$%.2f", totalVenta);

        // Aumenta el weightx para que se distribuya mejor visualmente (ajustable)
        addColumna(gbc, 0, folio, fontManager, 0.15);
        addColumna(gbc, 1, nombreCliente, fontManager, 0.20);
        addColumna(gbc, 2, estado, fontManager, 0.15);
        addColumna(gbc, 3, totalFormateado, fontManager, 0.15);
        addColumna(gbc, 4, mesa, fontManager, 0.10);
        addColumna(gbc, 5, fechaStr, fontManager, 0.25);
    }

    private void addColumna(GridBagConstraints gbc, int columna, String texto, FontManager fontManager, double weightx) {
        gbc.gridx = columna;
        gbc.weightx = weightx;
        JLabel label = new JLabel(texto);
        label.setFont(fontManager.getNotoSerifCondensedRegular(18f));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, gbc);
    }

    private float calcularTotalVenta(ComandaDTO comanda) {
        float total = 0f;
        try {
            List<DetalleComandaDTO> detalles = detallesComandasBO.obtenerDetallesDTOPorComanda(comanda);
            for (DetalleComandaDTO detalle : detalles) {
                total += detalle.getPrecio() * detalle.getCantidadProducto();
            }
        } catch (NegocioException e) {
            e.printStackTrace();
        }
        return total;
    }
}

