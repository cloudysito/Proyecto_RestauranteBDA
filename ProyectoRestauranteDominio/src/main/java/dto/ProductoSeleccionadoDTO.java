/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import Dominio.Producto;



/**
 *
 * @author santi
 */
public class ProductoSeleccionadoDTO {  
    private Producto producto;
    private Long idProducto;
    private String nombreProducto;
    private Integer cantidad;
    private Float precioUnitario;
    private String notas;

    public ProductoSeleccionadoDTO() {
    }

    public ProductoSeleccionadoDTO(Long idProducto, String nombreProducto, Float precioUnitario, Integer cantidad, String notas) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.notas = notas;
    }

    public ProductoSeleccionadoDTO(String nombreProducto, Integer cantidad, Float precioUnitario, String notas) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.notas = notas;
    }  
    
    public ProductoSeleccionadoDTO(Producto producto, Integer cantidad, Float precioUnitario, String notas) {
        this.producto = producto;
        this.idProducto = producto.getId();         
        this.nombreProducto = producto.getNombre();
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.notas = notas;
    }
 
    public float getImporte() {
        return cantidad * precioUnitario;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Float getPrecioUnitario() {
        return precioUnitario;
    }

    public String getNotas() {
        return notas;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(Float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
       
    
}
