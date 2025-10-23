/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import Dominio.Cliente;
import Dominio.Comanda;
import Dominio.DetalleComanda;
import Dominio.EstadoComanda;
import Dominio.Mesa;
import java.util.Calendar;
import java.util.List;


/**
 *
 * @author santi
 */
public class ComandaDTO {
    private Long id;
    private String folio;
    private EstadoComanda estadoComanda;
    private Calendar fechaHoraCreacion;
    private Mesa mesa;
    private Cliente cliente;
    private List<DetalleComanda> detallesComandas;
    private List<ProductoSeleccionadoDTO> productosSeleccionados;

    public ComandaDTO() {
    }

    public ComandaDTO(Long id) {
        this.id = id;
    }

    public ComandaDTO(Long id, String folio, EstadoComanda estadoComanda, Mesa mesa, Cliente cliente, List<DetalleComanda> detallesComandas) {
        this.id = id;
        this.folio = folio;
        this.estadoComanda = estadoComanda;
        this.mesa = mesa;
        this.cliente = cliente;
        this.detallesComandas = detallesComandas;
    }

    public ComandaDTO(Long id, String folio, EstadoComanda estadoComanda, Calendar fechaHoraCreacion, Mesa mesa, Cliente cliente, List<DetalleComanda> detallesComandas) {
        this.id = id;
        this.folio = folio;
        this.estadoComanda = estadoComanda;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.mesa = mesa;
        this.cliente = cliente;
        this.detallesComandas = detallesComandas;
    }
    
    public ComandaDTO(Comanda comanda) {
        this.id = comanda.getId();
        this.folio = comanda.getFolio();
        this.estadoComanda = comanda.getEstadoComanda();
        this.fechaHoraCreacion = comanda.getFechaHoraCreacion();
        this.mesa = comanda.getMesa();
        this.cliente = comanda.getCliente();
    } 

    public Calendar getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(Calendar fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public EstadoComanda getEstadoComanda() {
        return estadoComanda;
    }

    public void setEstadoComanda(EstadoComanda estadoComanda) {
        this.estadoComanda = estadoComanda;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<DetalleComanda> getDetallesComandas() {
        return detallesComandas;
    }

    public void setDetallesComandas(List<DetalleComanda> detallesComandas) {
        this.detallesComandas = detallesComandas;
    }
    
    public List<ProductoSeleccionadoDTO> getProductosSeleccionados() {
    return productosSeleccionados;
    }

    public void setProductosSeleccionados(List<ProductoSeleccionadoDTO> productosSeleccionados) {
        this.productosSeleccionados = productosSeleccionados;
    }


    
}
