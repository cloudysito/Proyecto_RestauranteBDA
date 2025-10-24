/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Dominio.Ingrediente;
import Excepciones.PersistenciaException;
import Interfaces.IIngredientesDAO;
import conexcion.ManejadorConexiones;
import dto.NuevoIngredienteDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author emiim
 */
public class IngredientesDAO implements IIngredientesDAO {

    /**
     * Registra un nuevo ingrediente en la base de datos.
     *
     * @param nuevoIngrediente DTO con los datos del nuevo ingrediente
     * @return El ingrediente registrado
     * @throws PersistenciaException Si ocurre un error durante la operación
     */
    @Override
    public Ingrediente registrarIngrediente(NuevoIngredienteDTO nuevoIngrediente) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            entityManager.getTransaction().begin();

            Ingrediente ingrediente = new Ingrediente();
            ingrediente.setNombre(nuevoIngrediente.getNombre());
            ingrediente.setStock(nuevoIngrediente.getStock());
            ingrediente.setUnidadMedida(nuevoIngrediente.getUnidadMedida());

            entityManager.persist(ingrediente);
            entityManager.getTransaction().commit();

            return ingrediente;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al registrar el ingrediente: " + e.getMessage(), e);
        }
    }

    /**
     * Consulta todos los ingredientes ordenados alfabéticamente.
     *
     * @return Lista de ingredientes
     * @throws PersistenciaException Si ocurre un error durante la consulta
     */
    @Override
    public List<Ingrediente> consultarIngredientes() throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            String jpqlQuery = "SELECT i FROM Ingrediente i ORDER BY i.nombre ASC";

            TypedQuery<Ingrediente> query = entityManager.createQuery(jpqlQuery, Ingrediente.class);
            return query.getResultList();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al consultar los ingredientes: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene todos los ingredientes sin orden específico.
     *
     * @return Lista de ingredientes
     * @throws PersistenciaException Si ocurre un error al recuperar los
     * ingredientes
     */
    @Override
    public List<Ingrediente> obtenerTodos() throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            TypedQuery<Ingrediente> query = entityManager.createQuery("SELECT i FROM Ingrediente i", Ingrediente.class);
            return query.getResultList();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al obtener todos los ingredientes: " + e.getMessage(), e);
        }
    }

    /**
     * Edita un ingrediente existente, actualizando su nombre, stock y unidad de
     * medida.
     *
     * @param nuevoIngredienteDTO DTO con los nuevos datos del ingrediente
     * @return El ingrediente actualizado
     * @throws PersistenciaException Si ocurre un error durante la actualización
     */
    @Override
    public Ingrediente editarNombre(NuevoIngredienteDTO nuevoIngredienteDTO) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            entityManager.getTransaction().begin();

            Ingrediente ingredienteEncontrado = buscarIngredienteId(nuevoIngredienteDTO.getId());
            ingredienteEncontrado.setNombre(nuevoIngredienteDTO.getNombre());
            ingredienteEncontrado.setStock(nuevoIngredienteDTO.getStock());
            ingredienteEncontrado.setUnidadMedida(nuevoIngredienteDTO.getUnidadMedida());

            entityManager.merge(ingredienteEncontrado);
            entityManager.getTransaction().commit();

            return ingredienteEncontrado;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al editar el ingrediente: " + e.getMessage(), e);
        }
    }

    /**
     * Aumenta el stock de un ingrediente existente.
     *
     * @param nuevoIngredienteDTO DTO con los datos del ingrediente actualizado
     * @return El ingrediente con el nuevo stock
     * @throws PersistenciaException Si ocurre un error durante la actualización
     */
    @Override
    public Ingrediente aumentarStock(NuevoIngredienteDTO nuevoIngredienteDTO) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            entityManager.getTransaction().begin();

            Ingrediente ingredienteEncontrado = buscarIngredienteId(nuevoIngredienteDTO.getId());
            ingredienteEncontrado.setNombre(nuevoIngredienteDTO.getNombre());
            ingredienteEncontrado.setStock(nuevoIngredienteDTO.getStock());
            ingredienteEncontrado.setUnidadMedida(nuevoIngredienteDTO.getUnidadMedida());

            entityManager.merge(ingredienteEncontrado);
            entityManager.getTransaction().commit();

            return ingredienteEncontrado;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al aumentar el stock del ingrediente: " + e.getMessage(), e);
        }
    }

    /**
     * Busca un ingrediente por su ID.
     *
     * @param idIngrediente Identificador del ingrediente
     * @return El ingrediente encontrado, o null si no existe
     * @throws PersistenciaException Si ocurre un error durante la búsqueda
     */
    @Override
    public Ingrediente buscarIngredienteId(Long idIngrediente) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            return entityManager.find(Ingrediente.class, idIngrediente);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al buscar ingrediente por ID: " + e.getMessage(), e);
        }
    }

    /**
     * Consulta los ingredientes cuyo nombre contenga una cadena específica.
     *
     * @param nombre Cadena para buscar coincidencias parciales en los nombres
     * @return Lista de ingredientes que coincidan
     * @throws PersistenciaException Si ocurre un error durante la búsqueda
     */
    @Override
    public List<Ingrediente> consultarIngredientesPorNombre(String nombre) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            String jpqlQuery = "SELECT i FROM Ingrediente i WHERE i.nombre LIKE :nombre ORDER BY i.nombre ASC";

            TypedQuery<Ingrediente> query = entityManager.createQuery(jpqlQuery, Ingrediente.class);
            query.setParameter("nombre", "%" + nombre + "%");

            return query.getResultList();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al buscar ingredientes por nombre: " + e.getMessage(), e);
        }
    }
}
