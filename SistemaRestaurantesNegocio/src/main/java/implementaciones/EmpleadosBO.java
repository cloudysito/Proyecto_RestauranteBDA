/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementaciones;

import exception.NegocioException;
import interfaces.IEmpleadosBO;

/**
 *
 * @author garfi
 */
public class EmpleadosBO implements IEmpleadosBO {

    private IEmpleadosDAO empleadosDAO;

    public EmpleadosBO(IEmpleadosDAO empleadosDAO) {
        this.empleadosDAO = empleadosDAO;
    }

    @Override
    public EmpleadoDTO iniciarSesion(String usuario, String contrasena) throws NegocioException {
        try {
            Empleado empleado = empleadosDAO.iniciarSesion(usuario, contrasena);

            if (empleado != null) {
                return new EmpleadoDTO(
                        empleado.getId(),
                        empleado.getUsuario(),
                        empleado.getRol().getId()
                );
            }

            return null;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al intentar iniciar sesión del empleado");
        }
    }
}
