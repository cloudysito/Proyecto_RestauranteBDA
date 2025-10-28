/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;


import Dominio.ClienteFrecuente;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



public class ClientePanel extends JPanel {

        private final ClienteFrecuente cliente;
        private JLabel lblPuntos;
        private Long idRol;

        public ClientePanel(ClienteFrecuente cliente, String textoBoton, ActionListener accionBoton, Long idRol) {
            
            this.cliente = cliente;
            this.idRol = idRol;
            
            FontManager fontManager = new FontManager();

            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.WHITE));
            setBackground(new Color(19, 28, 54));

            setPreferredSize(new Dimension(680, 130));
            setMinimumSize(new Dimension(680, 130));
            setMaximumSize(new Dimension(680, 130));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(3, 10, 3, 10);

            JLabel lblTelefono = new JLabel(cliente.getTelefono());
            lblTelefono.setForeground(Color.WHITE);
            lblTelefono.setFont(fontManager.getNotoSerifCondensedRegular(16f));

            JLabel lblNombre = new JLabel(cliente.getNombre().toUpperCase() + " " +
                                        cliente.getApellidoPaterno().toUpperCase());
            lblNombre.setFont(fontManager.getNotoSerifCondensedRegular(27f));
            lblNombre.setForeground(Color.WHITE);
            lblNombre.setToolTipText(lblNombre.getText());
            lblNombre.setHorizontalAlignment(SwingConstants.LEFT);
            lblNombre.setPreferredSize(new Dimension(350, 30));
            lblNombre.setMinimumSize(new Dimension(350, 30));
            lblNombre.setMaximumSize(new Dimension(350, 30));

            // Label para los puntos 
            lblPuntos = new JLabel("Puntos: --");
            lblPuntos.setForeground(Color.WHITE);
            lblPuntos.setFont(fontManager.getNotoSerifCondensedRegular(27f));

            // Panel contenedor para el botón y los puntos
            JPanel panelDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            panelDerecha.setOpaque(false);
            panelDerecha.add(lblPuntos);
            
            // Botón
            if(idRol == 1){
            JButton btnAccion = new JButton(textoBoton);
            btnAccion.setPreferredSize(new Dimension(176, 36));
            btnAccion.setMinimumSize(new Dimension(176, 36));
            btnAccion.setMaximumSize(new Dimension(176, 36));
            btnAccion.setBackground(Color.WHITE);
            btnAccion.setFont(fontManager.getNunitoRegular(14f));
            btnAccion.setForeground(new Color(0, 0, 0));
            btnAccion.addActionListener(accionBoton);
            panelDerecha.add(btnAccion);
            }

            // Correo electrónico
            JLabel lblCorreo = new JLabel(
                (cliente.getCorreoElectronico() != null && !cliente.getCorreoElectronico().isEmpty())
                ? cliente.getCorreoElectronico()
                : "Correo electrónico no registrado.");
            lblCorreo.setFont(fontManager.getNotoSerifCondensedRegular(16f));
            lblCorreo.setForeground(new Color(178, 220, 251));

            // Layout
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            add(lblTelefono, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            add(lblNombre, gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.EAST;
            add(panelDerecha, gbc); 

            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.WEST;
            add(lblCorreo, gbc);

            setOpaque(false);
        }
        
        public void actualizarPuntos(Integer puntos) {
            if (puntos != null) {
                lblPuntos.setText(String.format(puntos + " pts"));
            } else {
                lblPuntos.setText("Puntos: --");
            }
        }
        
        public ClientePanel(ClienteFrecuente cliente, String textoBoton, ActionListener accionBoton) {
            
            this.cliente = cliente;
            this.idRol = idRol;
            
            FontManager fontManager = new FontManager();

            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.WHITE));
            setBackground(new Color(19, 28, 54));

            setPreferredSize(new Dimension(680, 130));
            setMinimumSize(new Dimension(680, 130));
            setMaximumSize(new Dimension(680, 130));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(3, 10, 3, 10);

            JLabel lblTelefono = new JLabel(cliente.getTelefono());
            lblTelefono.setForeground(Color.WHITE);
            lblTelefono.setFont(fontManager.getNotoSerifCondensedRegular(16f));

            JLabel lblNombre = new JLabel(cliente.getNombre().toUpperCase() + " " +
                                        cliente.getApellidoPaterno().toUpperCase());
            lblNombre.setFont(fontManager.getNotoSerifCondensedRegular(27f));
            lblNombre.setForeground(Color.WHITE);
            lblNombre.setToolTipText(lblNombre.getText());
            lblNombre.setHorizontalAlignment(SwingConstants.LEFT);
            lblNombre.setPreferredSize(new Dimension(350, 30));
            lblNombre.setMinimumSize(new Dimension(350, 30));
            lblNombre.setMaximumSize(new Dimension(350, 30));

            // Label para los puntos 
            lblPuntos = new JLabel("Puntos: --");
            lblPuntos.setForeground(Color.WHITE);
            lblPuntos.setFont(fontManager.getNotoSerifCondensedRegular(27f));

            // Panel contenedor para el botón y los puntos
            JPanel panelDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            panelDerecha.setOpaque(false);
            panelDerecha.add(lblPuntos);
            
            // Botón
            
            JButton btnAccion = new JButton(textoBoton);
            btnAccion.setPreferredSize(new Dimension(176, 36));
            btnAccion.setMinimumSize(new Dimension(176, 36));
            btnAccion.setMaximumSize(new Dimension(176, 36));
            btnAccion.setBackground(Color.WHITE);
            btnAccion.setFont(fontManager.getNunitoRegular(14f));
            btnAccion.setForeground(new Color(0, 0, 0));
            btnAccion.addActionListener(accionBoton);
            panelDerecha.add(btnAccion);
            

            // Correo electrónico
            JLabel lblCorreo = new JLabel(
                (cliente.getCorreoElectronico() != null && !cliente.getCorreoElectronico().isEmpty())
                ? cliente.getCorreoElectronico()
                : "Correo electrónico no registrado.");
            lblCorreo.setFont(fontManager.getNotoSerifCondensedRegular(16f));
            lblCorreo.setForeground(new Color(178, 220, 251));

            // Layout
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            add(lblTelefono, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            add(lblNombre, gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.EAST;
            add(panelDerecha, gbc); 

            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.WEST;
            add(lblCorreo, gbc);

            setOpaque(false);
        }
}


       



