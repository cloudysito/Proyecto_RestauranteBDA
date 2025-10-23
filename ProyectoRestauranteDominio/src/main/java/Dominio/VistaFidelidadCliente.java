/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "vista_fidelidad_clientes")
public class VistaFidelidadCliente implements Serializable {

    @Id
    @Column(name = "id_cliente")
    private Long idCliente;
      
    @Column(name = "visitas")
    private Integer visitas;
    
    @Column(name = "gasto_total")
    private Double gastoTotal;
    
    @Column(name = "puntos_fidelidad")
    private Integer puntosFidelidad;

    @Column(name = "ultima_visita")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar ultimaVisita;

    public Long getIdCliente() {
        return idCliente;
    }
    
    public Integer getVisitas() {
        return visitas;
    }

    public Double getGastoTotal() {
        return gastoTotal;
    }

    public Integer getPuntosFidelidad() {
        return puntosFidelidad;
    }

    public Calendar getUltimaVisita() {
        return ultimaVisita;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VistaFidelidadCliente)) {
            return false;
        }
        VistaFidelidadCliente other = (VistaFidelidadCliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wonderland.sistemarestaurantesdominio.NewEntity[ id=" + idCliente + " ]";
    }
    
}
