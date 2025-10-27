/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dto.EmpleadoDTO;
import exception.NegocioException;

/**
 *
 * @author garfi
 */
public interface IEmpleadosBO {

    public abstract EmpleadoDTO iniciarSesion(String usuario, String constrasena) throws NegocioException;

}
