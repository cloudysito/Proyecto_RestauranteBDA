
package dto;

import Dominio.Ingrediente;
import Dominio.Producto;



/**
 *
 * @author santi
 */
public class IngredienteProductoDTO {
    private Long id;
    private Float cantidad;
    private Ingrediente ingrediente;
    private Producto producto;
    private Long idIngrediente;
    private Long idProducto;
    
    public IngredienteProductoDTO() {
    }

    public IngredienteProductoDTO(Long id, Float cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    public IngredienteProductoDTO(Float cantidad, Long idIngrediente, Long idProducto) {
        this.cantidad = cantidad;
        this.idIngrediente = idIngrediente;
        this.idProducto = idProducto;
    }
    
 
    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    public Long getIdIngrediente() {
        return idIngrediente != null ? idIngrediente : (ingrediente != null ? ingrediente.getId() : null);
    }

    public void setIdIngrediente(Long idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public Long getIdProducto() {
        return idProducto != null ? idProducto : (producto != null ? producto.getId() : null);
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }
 }