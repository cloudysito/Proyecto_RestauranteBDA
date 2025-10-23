package dto;

import Dominio.TipoProducto;
import java.util.List;


/**
 *
 * @author santi
 */
public class NuevoProductoDTO {
    private Long id;
    private String nombre;
    private Float precio;
    private TipoProducto tipoProducto;
      
    public NuevoProductoDTO() {
    }

    public NuevoProductoDTO(String nombre, Float precio, TipoProducto tipoProducto) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipoProducto = tipoProducto;
    }

    
    public NuevoProductoDTO(Long id, String nombre, Float precio, TipoProducto tipoProducto) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipoProducto = tipoProducto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    
    
    
}
