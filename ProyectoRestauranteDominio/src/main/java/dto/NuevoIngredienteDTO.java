/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import Dominio.UnidadMedida;


/**
 *
 * @author santi
 */
public class NuevoIngredienteDTO {
    
    private Long id;
    private String nombre;
    private Float stock;
    private UnidadMedida unidadMedida;

    public NuevoIngredienteDTO(String nombre, Float stock, UnidadMedida unidadMedida) {
        this.nombre = nombre;
        this.stock = stock;
        this.unidadMedida = unidadMedida;
    }

    public NuevoIngredienteDTO(Long id, String nombre, Float stock, UnidadMedida unidadMedida) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.unidadMedida = unidadMedida;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Float getStock() {
        return stock;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }
    
    
    
}
