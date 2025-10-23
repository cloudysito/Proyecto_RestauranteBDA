/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Excepciones.PersistenciaException;
import Interfaces.IEmpleadosDAO;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author emiim
 */
public class EmpleadosDAO implements IEmpleadosDAO {

    /**
     * Inicia sesión de un empleado verificando las credenciales proporcionadas.
     *
     * @param usuario Nombre de usuario del empleado.
     * @param contrasena Contraseña del empleado.
     * @return El objeto Empleado si las credenciales son válidas; de lo
     * contrario, null.
     * @throws PersistenciaException Si ocurre un error inesperado durante la
     * consulta.
     */
    @Override
    public Empleado iniciarSesion(String usuario, String contrasena) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            String jpqlQuery = "SELECT e FROM Empleado e WHERE e.usuario = :usuario AND e.contrasena = :contrasena";
            TypedQuery<Empleado> query = entityManager.createQuery(jpqlQuery, Empleado.class);
            query.setParameter("usuario", usuario);
            query.setParameter("contrasena", contrasena);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Usuario o contraseña incorrectos
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo realizar el inicio de sesión: " + e.getMessage(), e);
        }
    }    
}
