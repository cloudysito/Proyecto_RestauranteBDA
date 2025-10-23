/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import Utilidad.JasyptUtil;
import Utilidad.SeguridadUtil;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@Inheritance(strategy = InheritanceType.JOINED)
@Table (name = "clientes")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_cliente")
    private Long id;
    
    @Column (name = "nombre", length = 100, nullable = false)
    private String nombre;
    
    @Column (name = "apellido_paterno", length = 100, nullable = false)
    private String apellidoPaterno;
    
    @Column (name = "apellido_materno", length = 100, nullable = false)
    private String apellidoMaterno;
    
    @Column (name = "correo_electronico", length = 100)
    private String correoElectronico;
    
    @Column (name = "telefono" , unique = true, length = 255, nullable = false)
    private String telefonoEncriptado;
    
    @Transient
    private String telefono; 
    
    @Column(name = "hash_telefono", unique = true, nullable = false, length = 64)
    private String hashTelefono;
    
    @Temporal (TemporalType.TIMESTAMP)
    @Column (name = "fecha_registro", nullable = false)
    private Calendar fechaRegistro;
    
    @OneToMany(mappedBy = "cliente")
    private List<Comanda> comandas;

    public Cliente() {
        
    }

    public Cliente(String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, String telefono, Calendar fechaRegistro) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
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

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        if (this.telefono == null && this.telefonoEncriptado != null) {
            this.telefono = JasyptUtil.decrypt(this.telefonoEncriptado);
        }
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
        this.telefonoEncriptado = (telefono != null) ? 
            JasyptUtil.encrypt(telefono) : null;
        this.hashTelefono = (telefono != null) ? 
                SeguridadUtil.generarHash(telefono) : null;
    }

    public Calendar getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Calendar fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Comanda> getComandas() {
        return comandas;
    }

    public void setComandas(List<Comanda> comandas) {
        this.comandas = comandas;
    }

    public String getHashTelefono() {
        return hashTelefono;
    }

    public void setHashTelefono(String hashTelefono) {
        this.hashTelefono = hashTelefono;
    }
      
    /**
     * No se persiste en la base de datos
     * getter que devuelve el nombre completo para mostrar en interfaces 
     */
    @Transient
    public String getNombreCompleto() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
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
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Cliente[ id=" + id + " ]";
    }
    
}
