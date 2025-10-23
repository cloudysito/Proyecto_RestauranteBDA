/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Iterfaces;

import Dominio.Empleado;
import Excepciones.PersistenciaException;



/**
 *
 * @author santi
 */
public interface IEmpleadosDAO {
    public abstract Empleado iniciarSesion(String usuario, String contrasena) throws PersistenciaException;
}
