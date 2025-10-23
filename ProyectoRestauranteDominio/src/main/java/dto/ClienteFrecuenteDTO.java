/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import Dominio.Cliente;
import java.util.Calendar;


/**
 *
 * @author santi
 */
public class ClienteFrecuenteDTO {
    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correoElectronico;
    private String telefono;
    private Calendar fechaRegistro;
    
    private Cliente cliente;
    private int visitas;
    private double gastoTotal;
    private int puntosFidelidad;
    private Calendar ultimaVisita;

    public ClienteFrecuenteDTO() {
    }

    public ClienteFrecuenteDTO(Long id) {
        this.id = id;
    }
    
    public ClienteFrecuenteDTO(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, String telefono, Calendar fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
    }

    public ClienteFrecuenteDTO(String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, String telefono) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
    }

    public ClienteFrecuenteDTO(Cliente cliente, int visitas, double gastoTotal, int puntosFidelidad) {
        this.cliente = cliente;
        this.visitas = visitas;
        this.gastoTotal = gastoTotal;
        this.puntosFidelidad = puntosFidelidad;
    }

    public ClienteFrecuenteDTO(Cliente cliente, int visitas, double gastoTotal, int puntosFidelidad, Calendar ultimaVisita) {
        this.cliente = cliente;
        this.visitas = visitas;
        this.gastoTotal = gastoTotal;
        this.puntosFidelidad = puntosFidelidad;
        this.ultimaVisita = ultimaVisita;
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
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Calendar getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Calendar fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getVisitas() {
        return visitas;
    }

    public void setVisitas(int visitas) {
        this.visitas = visitas;
    }

    public double getGastoTotal() {
        return gastoTotal;
    }

    public void setGastoTotal(double gastoTotal) {
        this.gastoTotal = gastoTotal;
    }

    public int getPuntosFidelidad() {
        return puntosFidelidad;
    }

    public void setPuntosFidelidad(int puntosFidelidad) {
        this.puntosFidelidad = puntosFidelidad;
    }

    public Calendar getUltimaVisita() {
        return ultimaVisita;
    }

    public void setUltimaVisita(Calendar ultimaVisita) {
        this.ultimaVisita = ultimaVisita;
    }    
    
}
