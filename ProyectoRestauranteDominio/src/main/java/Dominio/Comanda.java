/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "comandas")
public class Comanda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_comanda")
    private Long id;
    
    @Column (name = "folio", unique = true, nullable = false , length = 15)
    private String folio;
    
    @Enumerated (EnumType.STRING)
    @Column (name = "estado" , nullable = false)
    private EstadoComanda estadoComanda; 
    
    @Transient
    private Float totalVenta;
    
    @Temporal (TemporalType.TIMESTAMP)
    @Column (name = "fecha_hora_creacion" , nullable = false)
    private Calendar fechaHoraCreacion;
    
    @ManyToOne()
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    
    @ManyToOne()
    @JoinColumn(name = "id_mesa", nullable = false)
    private Mesa mesa;
    
//    @OneToMany(mappedBy = "comanda", cascade = CascadeType.REMOVE)
//    private List<DetalleComanda> detallesComandas;
//    
    public Comanda() {
    }

    public Comanda(Long id, String folio, EstadoComanda estadoComanda, Float totalVenta, Calendar fechaHora_creacion, Cliente cliente, Mesa mesa/*, List<DetalleComanda> detallesComandas*/) {
        this.id = id;
        this.folio = folio;
        this.estadoComanda = estadoComanda;
        this.totalVenta = totalVenta;
        this.fechaHoraCreacion = fechaHora_creacion;
        this.cliente = cliente;
        this.mesa = mesa;
        //this.detallesComandas = detallesComandas;
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

    public Calendar getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(Calendar fechaHora_creacion) {
        this.fechaHoraCreacion = fechaHora_creacion;
    }

    public Float getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Float totalVenta) {
        this.totalVenta = totalVenta;
    }

//    public List<DetalleComanda> getDetallesComandas() {
//        return detallesComandas;
//    }
//
//    public void setDetallesComandas(List<DetalleComanda> detallesComandas) {
//        this.detallesComandas = detallesComandas;
//    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comanda)) {
            return false;
        }
        Comanda other = (Comanda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Comanda[ id=" + id + " ]";
    }
    
}

