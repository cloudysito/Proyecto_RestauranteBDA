/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

// Importamos todo lo necesario de JPA (para la magia de la base de datos)
// y nuestras clases de utilidad para encriptar y hashear.
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
@Entity // Le decimos a JPA que esta clase es una entidad o sea, una tabla
@Inheritance(strategy = InheritanceType.JOINED) // Esto es para la herencia. JOINED = tablas separadas.
@Table (name = "clientes") // El nombre que tendrá la tabla en la BD.
public class Cliente implements Serializable { // Serializable, es importante para las entidades

    @Id // Marca esto como la llave primaria (PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Para que sea auto-incremental
    @Column (name = "id_cliente") // El nombre exacto de la columna en la BD
    private Long id;
    
    // Datos básicos del cliente
    @Column (name = "nombre", length = 100, nullable = false) // No puede ser nulo
    private String nombre;
    
    @Column (name = "apellido_paterno", length = 100, nullable = false)
    private String apellidoPaterno;
    
    @Column (name = "apellido_materno", length = 100, nullable = false)
    private String apellidoMaterno;
    
    @Column (name = "correo_electronico", length = 100)
    private String correoElectronico;
    
    // Aquí guardamos el teléfono encriptado en la BD
    @Column (name = "telefono" , unique = true, length = 255, nullable = false)
    private String telefonoEncriptado;
    
    @Transient // @Transient significa que JPA va a ignorar este campo. NO se guarda en la BD.
    private String telefono; // Esta variable la usamos en el código para tener el teléfono en claro
    
    // Guardamos un HASH del teléfono. Esto para buscar rápido sin tener que desencriptar.
    @Column(name = "hash_telefono", unique = true, nullable = false, length = 64) // El hash también es único
    private String hashTelefono;
    
    @Temporal (TemporalType.TIMESTAMP) // Para guardar fecha Y hora completas
    @Column (name = "fecha_registro", nullable = false)
    private Calendar fechaRegistro;
    
    // Relación: Un cliente puede tener muchas comandas
    @OneToMany(mappedBy = "cliente") // "mappedBy" dice que Comanda se encarga de la relación
    private List<Comanda> comandas;

    // Constructor vacío. JPA lo necesita siempre
    public Cliente() {
        
    }

    // Constructor "normal" para crear objetos Cliente más fácil
    public Cliente(String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, String telefono, Calendar fechaRegistro) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono; // usamos el setter de abajo
        this.fechaRegistro = fechaRegistro;
    }
    
    
    // --- GETTERS Y SETTERS ---
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

    /**
     * Getter "inteligente" para el teléfono. Si alguien pide el teléfono, nos
     * fijamos si ya lo tenemos en la variable "transient". Si no, lo
     * desencriptamos.
     */
    public String getTelefono() {
        // Si la variable 'telefono' en claro está vacía Y la variable 'telefonoEncriptado', de la BD, si tiene algo
        if (this.telefono == null && this.telefonoEncriptado != null) {
            // Entonces lo desencriptamos y lo guardamos en la variable 'telefono'.
            this.telefono = JasyptUtil.decrypt(this.telefonoEncriptado);
        }
        // Devolvemos el teléfono en claro.
        return this.telefono;
    }

    /**
     * Setter "inteligente". Cada vez que alguien ponga un teléfono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono; // 1. Guardamos el valor en la variable @Transient
        
        // 2. Encriptamos el teléfono y lo guardamos en la variable que SÍ va a la BD
        this.telefonoEncriptado = (telefono != null) ? 
            JasyptUtil.encrypt(telefono) : null;
        
        // 3. También generamos el HASH para poder buscar por teléfono
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

    // --Getters y Setters para el HASH--
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
    @Transient // Otra vez, para que JPA no intente guardar esto en una columna
    public String getNombreCompleto() {
        // Concatenamos los nombres
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }

    // --- Métodos autogenerados por el IDE ---
    // Necesarios para que JPA y las colecciones funcionen bien
    @Override
    public int hashCode() {
        int hash = 0;
        // El hashcode se basa solo en el ID.
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;// Comparamos los IDs. Si uno es nulo y el otro no, o si son distintos, FALSO.
        // Comparamos los IDs. Si uno es nulo y el otro no, o si son distintos, FALSO.
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true; // Si llegamos aquí, son iguales o ambos tienen ID nulo.
    }

    @Override
    public String toString() {
        // Un toString simple para debugging, solo muestra el ID.
        return "entidades.Cliente[ id=" + id + " ]";
    }
    
}
