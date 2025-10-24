/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Dominio.ClienteFrecuente;
import Dominio.Comanda;
import Dominio.EstadoComanda;
import Excepciones.PersistenciaException;
import Interfaces.IComandasDAO;
import conexcion.ManejadorConexiones;
import dto.ComandaDTO;
import dto.NuevaComandaDTO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author emiim
 */
public class ComandasDAO implements IComandasDAO {          

    
    /**
     * Crea una nueva comanda con la información proporcionada en el DTO.
     *
     * @param nuevaComanda Objeto DTO que contiene los datos de la nueva comanda.
     * @return La entidad Comanda creada y persistida en la base de datos.
     * @throws PersistenciaException Si ocurre un error al persistir la comanda.
     */
    @Override
    public Comanda crearNuevarComanda(NuevaComandaDTO nuevaComanda) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
        try {
            entityManager.getTransaction().begin();

            Comanda comanda = new Comanda();
            comanda.setFolio(generarFolio());
            comanda.setEstadoComanda(nuevaComanda.getEstadoComanda());
            comanda.setFechaHoraCreacion(Calendar.getInstance());
            comanda.setMesa(nuevaComanda.getMesa());
            comanda.setCliente(nuevaComanda.getCliente());

            entityManager.persist(comanda);
            entityManager.getTransaction().commit();
            return comanda;

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("No se pudo crear la comanda: " + e);
        }
    }

    /**
     * Genera un folio único basado en la fecha actual y un número consecutivo.
     *
     * @return Un folio generado con el formato OB-yyyyMMdd-XXX.
     * @throws PersistenciaException Si ocurre un error al generar el folio.
     */
    public String generarFolio() throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
        Calendar calendar = Calendar.getInstance();
        String fecha = String.format("%04d%02d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
        int consecutivo = obtenerNumeroConsecutivo(entityManager, fecha);
        return "OB-" + fecha + "-" + String.format("%03d", consecutivo);
    }

    /**
     * Obtiene el número consecutivo para folios en un mismo día.
     *
     * @param entityManager El EntityManager utilizado para la consulta.
     * @param fecha Fecha base para buscar los folios existentes.
     * @return El número consecutivo siguiente.
     * @throws PersistenciaException Si ocurre un error durante la consulta.
     */
    private int obtenerNumeroConsecutivo(EntityManager entityManager, String fecha) throws PersistenciaException {
        try {
            String jpql = "SELECT MAX(c.folio) FROM Comanda c WHERE c.folio LIKE :fecha";
            Query query = entityManager.createQuery(jpql);
            query.setParameter("fecha", "OB-" + fecha + "-%");

            String ultimoFolio = (String) query.getSingleResult();

            if (ultimoFolio != null) {
                String consecutivoStr = ultimoFolio.substring(12);
                return Integer.parseInt(consecutivoStr) + 1;
            } else {
                return 1;
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("No se pudo obtener el número consecutivo: " + e);
        }
    }

    /**
     * Asocia un cliente frecuente a una comanda ya registrada.
     *
     * @param comanda La comanda existente a la que se asociará el cliente.
     * @param cliente El cliente frecuente que se asociará.
     * @return La comanda actualizada con el cliente asociado.
     * @throws PersistenciaException Si ocurre un error al realizar la asociación.
     */
    @Override
    public Comanda asociarClienteAComanda(Comanda comanda, ClienteFrecuente cliente) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            comanda.setCliente(cliente);
            Comanda comandaActualizada = entityManager.merge(comanda);
            entityManager.getTransaction().commit();
            return comandaActualizada;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("No se pudo asociar el cliente a la comanda: " + e);
        }
    }

    /**
     * Obtiene una comanda a partir de su identificador único.
     *
     * @param idComanda ID de la comanda a buscar.
     * @return La comanda correspondiente al ID.
     * @throws PersistenciaException Si ocurre un error durante la búsqueda.
     */
    @Override
    public Comanda obtenerComandaPorId(Long idComanda) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
        try {
            return entityManager.find(Comanda.class, idComanda);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("No se pudo obtener la comanda por ID: " + e);
        }
    }

    /**
     * Obtiene la comanda activa asociada a una mesa (estado ABIERTO).
     *
     * @param idMesa ID de la mesa.
     * @return Un objeto ComandaDTO de la comanda activa.
     * @throws PersistenciaException Si ocurre un error durante la búsqueda.
     */
    @Override
    public ComandaDTO obtenerComandaActivaPorMesa(Long idMesa) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
        try {
            String jpql = "SELECT c FROM Comanda c WHERE c.mesa.id = :idMesa AND c.estadoComanda = :estado";
            Query query = entityManager.createQuery(jpql);
            query.setParameter("idMesa", idMesa);
            query.setParameter("estado", EstadoComanda.ABIERTA);
            Comanda comanda = (Comanda) query.getSingleResult();
            return new ComandaDTO(comanda);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("No se pudo obtener la comanda activa por mesa: " + e);
        }
    }

    /**
     * Modifica el estado de una comanda a ENTREGADA.
     *
     * @param comandaDTO Objeto DTO con los datos de la comanda a modificar.
     * @return La entidad Comanda modificada.
     * @throws PersistenciaException Si ocurre un error al actualizar el estado.
     */
    @Override
    public Comanda modificarEstadoComanda(ComandaDTO comandaDTO) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Comanda comanda = entityManager.find(Comanda.class, comandaDTO.getId());
            comanda.setEstadoComanda(EstadoComanda.ENTREGADA);
            entityManager.merge(comanda);
            entityManager.getTransaction().commit();
            return comanda;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("No se pudo cambiar el estado de la comanda a ENTREGADA: " + e);
        }
    }

    /**
     * Cambia el estado de una comanda a CANCELADA.
     *
     * @param comandaDTO Objeto DTO de la comanda a cancelar.
     * @return La comanda con estado actualizado a CANCELADA.
     * @throws PersistenciaException Si ocurre un error al cancelar la comanda.
     */
    @Override
    public Comanda cancelarComanda(ComandaDTO comandaDTO) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Comanda comanda = entityManager.find(Comanda.class, comandaDTO.getId());
            comanda.setEstadoComanda(EstadoComanda.CANCELADA);
            entityManager.merge(comanda);
            entityManager.getTransaction().commit();
            return comanda;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("No se pudo cancelar la comanda: " + e);
        }
    }

    /**
     * Obtiene todas las comandas registradas ordenadas por fecha de creación.
     *
     * @return Lista de objetos ComandaDTO correspondientes a todas las comandas.
     * @throws PersistenciaException Si ocurre un error al consultar la base de datos.
     */
    @Override
    public List<ComandaDTO> obtenerComandas() throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
        try {
            String jpqlQuery = "SELECT v FROM Comanda v ORDER BY v.fechaHoraCreacion ASC";
            TypedQuery<Comanda> query = entityManager.createQuery(jpqlQuery, Comanda.class);
            List<Comanda> comandas = query.getResultList();

            List<ComandaDTO> comandasDTO = new ArrayList<>();
            for (Comanda comanda : comandas) {
                comandasDTO.add(new ComandaDTO(comanda));
            }
            return comandasDTO;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("No se pudieron obtener todas las comandas: " + e);
        }
    }

    /**
     * Obtiene todas las comandas registradas dentro de un rango de fechas.
     *
     * @param fechaInicio Fecha inicial del rango.
     * @param fechaFin Fecha final del rango.
     * @return Lista de objetos ComandaDTO dentro del rango especificado.
     * @throws PersistenciaException Si ocurre un error al consultar las comandas.
     */
    @Override
    public List<ComandaDTO> obtenerComandasPorFechas(Calendar fechaInicio, Calendar fechaFin) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            List<Comanda> comandas = entityManager.createQuery(
                    "SELECT c FROM Comanda c WHERE c.fechaHoraCreacion BETWEEN :inicio AND :fin",
                    Comanda.class)
                    .setParameter("inicio", fechaInicio)
                    .setParameter("fin", fechaFin)
                    .getResultList();
            entityManager.getTransaction().commit();

            List<ComandaDTO> comandasDTO = new ArrayList<>();
            for (Comanda comanda : comandas) {
                comandasDTO.add(new ComandaDTO(comanda));
            }
            return comandasDTO;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("No se pudieron obtener las comandas por fechas: " + e);
        }
    } 
}
