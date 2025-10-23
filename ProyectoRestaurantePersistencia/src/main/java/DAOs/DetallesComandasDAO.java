/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Excepciones.PersistenciaException;
import Interfaces.IDetallesComandasDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author emiim
 */
public class DetallesComandasDAO implements IDetallesComandasDAO {      

    /**
     * Guarda un nuevo detalle de comanda en la base de datos.
     *
     * @param detalleComandaDTO Objeto con los datos del detalle.
     * @return El detalle de comanda registrado.
     * @throws PersistenciaException Si ocurre un error al guardar el detalle.
     */
    @Override
    public DetalleComanda guardarDetalleComanda(DetalleComandaDTO detalleComandaDTO) throws PersistenciaException {
        EntityManager em = ManejadorConexiones.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Producto producto = em.find(Producto.class, detalleComandaDTO.getProducto().getId());
            Comanda comanda = em.find(Comanda.class, detalleComandaDTO.getComanda().getId());

            DetalleComanda detalle = new DetalleComanda();
            detalle.setProducto(producto);
            detalle.setComanda(comanda);
            detalle.setCantidadProducto(detalleComandaDTO.getCantidadProducto());
            detalle.setPrecio(detalleComandaDTO.getPrecio());
            detalle.setNota(detalleComandaDTO.getNota());

            em.persist(detalle);

            tx.commit();
            return detalle;

        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("No se pudo guardar el detalle de la comanda: " + ex.getMessage(), ex);
        }
    }

    /**
     * Registra una lista de detalles de comanda asociados a una comanda
     * específica.
     *
     * @param listaDto Lista de objetos detalle a registrar.
     * @return El último detalle registrado.
     * @throws PersistenciaException Si ocurre un error al registrar los
     * detalles.
     */
    @Override
    public DetalleComanda guardarDetallesComandas(List<DetalleComandaDTO> listaDto) throws PersistenciaException {
        EntityManager em = ManejadorConexiones.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        DetalleComanda ultimoGuardado = null;

        try {
            tx.begin();

            for (DetalleComandaDTO detalleComandaDTO : listaDto) {
                Producto producto = em.find(Producto.class, detalleComandaDTO.getProducto().getId());
                Comanda comanda = em.find(Comanda.class, detalleComandaDTO.getComanda().getId());

                DetalleComanda detalle = new DetalleComanda();
                detalle.setProducto(producto);
                detalle.setComanda(comanda);
                detalle.setCantidadProducto(detalleComandaDTO.getCantidadProducto());
                detalle.setPrecio(detalleComandaDTO.getPrecio());
                detalle.setNota(detalleComandaDTO.getNota());

                em.persist(detalle);
                ultimoGuardado = detalle;
            }

            tx.commit();
            return ultimoGuardado;

        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("No se pudo guardar los detalles de la comanda: " + ex.getMessage(), ex);
        }
    }

    /**
     * Obtiene los productos seleccionados en una comanda específica.
     *
     * @param comandaDTO La comanda desde la cual se obtendrán los productos.
     * @return Lista de productos seleccionados.
     * @throws PersistenciaException Si ocurre un error al consultar los
     * detalles.
     */
    @Override
    public List<ProductoSeleccionadoDTO> obtenerDetalleComandaPorComanda(ComandaDTO comandaDTO) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            String jpql = "SELECT new wonderland.sistemarestaurantesdominio.dtos.ProductoSeleccionadoDTO("
             + "dc.producto, dc.cantidadProducto, dc.precio, dc.nota) "  
             + "FROM DetalleComanda dc WHERE dc.comanda.id = :idComanda";
            
            Query query = entityManager.createQuery(jpql);
            query.setParameter("idComanda", comandaDTO.getId());

            return query.getResultList();

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("No se pudo obtener el detalle de la comanda: " + e);
        }
    }

    /**
     * Edita un detalle de comanda existente según el producto asociado.
     *
     * @param idComanda ID de la comanda a modificar.
     * @param productoSeleccionado Datos del producto actualizado.
     * @throws PersistenciaException Si ocurre un error durante la edición.
     */
    @Override
    public void editarDetalleComanda(Long idComanda, ProductoSeleccionadoDTO productoSeleccionado) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            entityManager.getTransaction().begin();

            String jpql = "SELECT dc FROM DetalleComanda dc WHERE dc.comanda.id = :idComanda AND dc.producto.nombre = :nombreProducto";
            DetalleComanda detalle = entityManager.createQuery(jpql, DetalleComanda.class)
                    .setParameter("idComanda", idComanda)
                    .setParameter("nombreProducto", productoSeleccionado.getNombreProducto())
                    .getSingleResult();

            detalle.setCantidadProducto(productoSeleccionado.getCantidad());
            detalle.setPrecio(productoSeleccionado.getPrecioUnitario());
            detalle.setNota(productoSeleccionado.getNotas());

            entityManager.merge(detalle);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("No se pudo editar el detalle de la comanda: " + e);
        }
    }

    /**
     * Actualiza un detalle existente o lo registra si no existe.
     *
     * @param detalleComandaDTO Detalle a actualizar o registrar.
     * @return El detalle actualizado o registrado.
     * @throws PersistenciaException Si ocurre un error durante la operación.
     */
    @Override
    public DetalleComanda ActualizarDetallesComanda(DetalleComandaDTO detalleComandaDTO) throws PersistenciaException {
        EntityManager em = ManejadorConexiones.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Long idComanda = detalleComandaDTO.getComanda().getId();
            Long idProducto = detalleComandaDTO.getProducto().getId();

            DetalleComanda detalleExistente = em.createQuery(
                    "SELECT dc FROM DetalleComanda dc WHERE dc.comanda.id = :idComanda AND dc.producto.id = :idProducto",
                    DetalleComanda.class)
                    .setParameter("idComanda", idComanda)
                    .setParameter("idProducto", idProducto)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            Producto producto = em.find(Producto.class, idProducto);
            Comanda comanda = em.find(Comanda.class, idComanda);

            DetalleComanda detalleActualizado;

            if (detalleExistente != null) {
                detalleExistente.setCantidadProducto(detalleComandaDTO.getCantidadProducto());
                detalleExistente.setPrecio(detalleComandaDTO.getPrecio());
                detalleExistente.setNota(detalleComandaDTO.getNota());
                em.merge(detalleExistente);
                detalleActualizado = detalleExistente;
            } else {
                DetalleComanda nuevoDetalle = new DetalleComanda();
                nuevoDetalle.setProducto(producto);
                nuevoDetalle.setComanda(comanda);
                nuevoDetalle.setCantidadProducto(detalleComandaDTO.getCantidadProducto());
                nuevoDetalle.setPrecio(detalleComandaDTO.getPrecio());
                nuevoDetalle.setNota(detalleComandaDTO.getNota());

                em.persist(nuevoDetalle);
                detalleActualizado = nuevoDetalle;
            }

            tx.commit();
            return detalleActualizado;

        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("No se pudo actualizar el detalle de la comanda: " + ex.getMessage(), ex);
        }
    }

    /**
     * Recupera todos los detalles de una comanda como objetos
     * DetalleComandaDTO.
     *
     * @param comandaDTO Comanda para la cual se buscan los detalles.
     * @return Lista de detalles asociados.
     */
    @Override
    public List<DetalleComandaDTO> obtenerDetallesDTOPorComanda(ComandaDTO comandaDTO) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
        
        try{
            TypedQuery<DetalleComanda> query = entityManager.createQuery(
            "SELECT d FROM DetalleComanda d WHERE d.comanda.id = :idComanda", DetalleComanda.class);
            query.setParameter("idComanda", comandaDTO.getId());
            List<DetalleComanda> resultados = query.getResultList();

            List<DetalleComandaDTO> detallesDTO = new ArrayList<>();
            for (DetalleComanda detalle : resultados) {
            DetalleComandaDTO detalleComandaDTO = new DetalleComandaDTO();
            detalleComandaDTO.setIdDetalleComanda(detalle.getId());
            detalleComandaDTO.setCantidadProducto(detalle.getCantidadProducto());
            detalleComandaDTO.setPrecio(detalle.getPrecio());
            detalleComandaDTO.setProducto(detalle.getProducto());
            detallesDTO.add(detalleComandaDTO);
            }
            
            return detallesDTO;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("No se pudo editar el detalle de la comanda: " + e);
        }
    }   
}
