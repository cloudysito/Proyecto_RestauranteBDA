/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author santi
 */
public class NuevaMesaDTO {
    
    private Long id;
    private Integer numeroMesa;

    public NuevaMesaDTO() {
    }
    
    public NuevaMesaDTO(Long id) {
        this.id = id;
    }

    public NuevaMesaDTO(Long id, Integer numeroMesa) {
        this.id = id;
        this.numeroMesa = numeroMesa;
    }

    public NuevaMesaDTO(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }
    
    public Long getId() {
        return id;
    }

    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    
}
