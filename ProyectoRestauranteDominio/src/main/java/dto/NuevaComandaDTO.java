/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import Dominio.Cliente;
import Dominio.DetalleComanda;
import Dominio.EstadoComanda;
import Dominio.Mesa;
import java.util.Calendar;
import java.util.List;


/**
 *
 * @author Dana Chavez
 */
public class NuevaComandaDTO {
    private String folio;
    private EstadoComanda estadoComanda;
    private Mesa mesa;
    private Cliente cliente;
    private List<DetalleComanda> detallesComandas;

    public NuevaComandaDTO() {
    }

    public NuevaComandaDTO(String folio, EstadoComanda estadoComanda, Mesa mesa, Cliente cliente, List<DetalleComanda> detallesComandas) {
        this.folio = folio;
        this.estadoComanda = estadoComanda;
        this.mesa = mesa;
        this.cliente = cliente;
        this.detallesComandas = detallesComandas;
    }

    public NuevaComandaDTO(String folio, EstadoComanda estadoComanda, Mesa mesa) {
        this.folio = folio;
        this.estadoComanda = estadoComanda;
        this.mesa = mesa;
    }

    public NuevaComandaDTO(String folio, EstadoComanda estadoComanda) {
        this.folio = folio;
        this.estadoComanda = estadoComanda;
    }

    public NuevaComandaDTO(EstadoComanda estadoComanda, Mesa mesa) {
        this.estadoComanda = estadoComanda;
        this.mesa = mesa;
    }
    
    public String getFolio() {
        return folio;
    }

    public EstadoComanda getEstadoComanda() {
        return estadoComanda;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<DetalleComanda> getDetallesComandas() {
        return detallesComandas;
    }
    
}
