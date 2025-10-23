
package Dominio;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author santi
 */
@Entity
@Table(name="detalles_comandas")
public class DetalleComanda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name= "id_detalle_comanda")
    private Long id;
    
    @Column(name="cantidad_producto", nullable = false)
    private Integer cantidadProducto;
    
    @Column(name="precio", nullable = false)
    private Float precio;
    
    @Column(name="notas", nullable = false, length = 250)
    private String nota;

    @Transient 
    private Float importe;
    
    @ManyToOne()
    @JoinColumn(name = "id_producto")
    private Producto producto;
    
    @ManyToOne()
    @JoinColumn(name = "id_comanda")
    private Comanda comanda;
    
    public DetalleComanda() {
    }

    public DetalleComanda(Long id, Integer cantidadProducto, Float precio, String nota, Float importe) {
        this.id = id;
        this.cantidadProducto = cantidadProducto;
        this.precio = precio;
        this.nota = nota;
        this.importe = importe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(Integer cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Float getImporte() {
        return importe;
    }

    public void setImporte(Float importe) {
        this.importe = importe;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DetalleComanda other = (DetalleComanda) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "DetalleComanda{" + "id=" + id + '}';
    }
    
}
