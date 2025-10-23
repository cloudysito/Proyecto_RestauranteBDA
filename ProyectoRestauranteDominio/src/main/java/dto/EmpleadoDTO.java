/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author santi
 */
public class EmpleadoDTO {
    private Long id;
    private String usuario;
    private Long idRol;

    public EmpleadoDTO() {
    }

    public EmpleadoDTO(Long id, String usuario, Long idRol) {
        this.id = id;
        this.usuario = usuario;
        this.idRol = idRol;
    }

    public EmpleadoDTO(String usuario, Long idRol) {
        this.usuario = usuario;
        this.idRol = idRol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

   
}
