/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Excepciones.PersistenciaException;
import Interfaces.IMesasDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author emiim
 */
public class MesasDAO implements IMesasDAO {    
    /**
     * Agrega un conjunto de nuevas mesas al sistema, comenzando desde el número
     * especificado.
     *
     * @param nuevaMesa DTO con el número inicial de mesa a registrar
     * @return Lista de mesas registradas
     * @throws PersistenciaException Si ocurre un error durante la operación
     */
    @Override
    public List<Mesa> agregarMesas(NuevaMesaDTO nuevaMesa) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            List<Mesa> mesas = new ArrayList<>();
            entityManager.getTransaction().begin();

            Integer ultimaMesa = obtenerUltimaMesa(entityManager);
            if (nuevaMesa.getNumeroMesa() == null) {
                nuevaMesa.setNumeroMesa(ultimaMesa + 1);
            }

            for (int i = 0; i < 20; i++) {
                Mesa mesa = new Mesa();
                mesa.setNumeroMesa(nuevaMesa.getNumeroMesa() + i);
                mesa.setEstado(EstadoMesa.DISPONIBLE);
                entityManager.persist(mesa);
                mesas.add(mesa);
            }

            entityManager.getTransaction().commit();
            return mesas;

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al agregar nuevas mesas: " + e.getMessage(), e);
        }
    }

    /**
     * Consulta y devuelve todas las mesas registradas en el sistema.
     *
     * @return Lista de mesas ordenadas por número
     * @throws PersistenciaException Si ocurre un error durante la consulta
     */
    @Override
    public List<Mesa> mostrarMesas() throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            String jpqlQuery = "SELECT v FROM Mesa v ORDER BY v.numeroMesa ASC";
            TypedQuery<Mesa> query = entityManager.createQuery(jpqlQuery, Mesa.class);
            return query.getResultList();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al consultar las mesas: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene el número de la última mesa registrada en el sistema.
     *
     * @param entityManager EntityManager activo
     * @return El número de la mesa más alta o 0 si no hay mesas registradas
     * @throws PersistenciaException Si ocurre un error durante la consulta
     */
    public Integer obtenerUltimaMesa(EntityManager entityManager) throws PersistenciaException {
        try {
            String jpql = "SELECT MAX(m.numeroMesa) FROM Mesa m";
            Query query = entityManager.createQuery(jpql);

            Integer ultimaMesa = (Integer) query.getSingleResult();

            return (ultimaMesa != null) ? ultimaMesa : 0;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al obtener la última mesa registrada: " + e.getMessage(), e);
        }
    }

    /**
     * Cambia el estado de una mesa según su identificador.
     *
     * @param idMesa ID de la mesa a actualizar
     * @param nuevoEstado Nuevo estado a asignar (e.g. DISPONIBLE, OCUPADA)
     * @return La mesa actualizada
     * @throws PersistenciaException Si ocurre un error durante la actualización
     */
    @Override
    public Mesa cambiarEstadoMesa(Long idMesa, EstadoMesa nuevoEstado) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            entityManager.getTransaction().begin();

            String jpql = "SELECT m FROM Mesa m WHERE m.id = :id";
            TypedQuery<Mesa> query = entityManager.createQuery(jpql, Mesa.class);
            query.setParameter("id", idMesa);

            Mesa mesa = query.getSingleResult();
            mesa.setEstado(nuevoEstado);

            Mesa mesaActualizada = entityManager.merge(mesa);
            entityManager.getTransaction().commit();

            return mesaActualizada;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al cambiar el estado de la mesa: " + e.getMessage(), e);
        }
    }
}
