/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import Dominio.Comanda;
import Dominio.Producto;



/**
 *
 * @author santi
 */
public class DetalleComandaDTO {
    
    private Long idDetalleComanda;
    private Integer cantidadProducto;
    private Float precio;
    private String nota;
    private Producto producto;
    private Comanda comanda;

    public DetalleComandaDTO() {
    }

    public DetalleComandaDTO(Long idDetalleComanda, Integer cantidadProducto, Float precio, String nota, Producto producto, Comanda comanda) {
        this.idDetalleComanda = idDetalleComanda;
        this.cantidadProducto = cantidadProducto;
        this.precio = precio;
        this.nota = nota;
        this.producto = producto;
        this.comanda = comanda;
    }

    public Long getIdDetalleComanda() {
        return idDetalleComanda;
    }

    public Integer getCantidadProducto() {
        return cantidadProducto;
    }

    public Float getPrecio() {
        return precio;
    }

    public String getNota() {
        return nota;
    }

    public Producto getProducto() {
        return producto;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setIdDetalleComanda(Long idDetalleComanda) {
        this.idDetalleComanda = idDetalleComanda;
    }

    public void setCantidadProducto(Integer cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }
    
    
    
    
    
}
