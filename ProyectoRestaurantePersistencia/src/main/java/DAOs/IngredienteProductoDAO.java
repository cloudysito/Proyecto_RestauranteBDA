/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Excepciones.PersistenciaException;
import Interfaces.IIngredienteProductoDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author emiim
 */
public class IngredienteProductoDAO implements IIngredienteProductoDAO{
    
        /**
     * Registra una nueva relación entre un producto y un ingrediente.
     *
     * @param ingredienteProductoDTO DTO con los datos de la relación (producto,
     * ingrediente, cantidad).
     * @return La entidad `IngredienteProducto` registrada.
     * @throws PersistenciaException Si ocurre un error al guardar la relación.
     */
    @Override
    public IngredienteProducto registrarIngredienteProducto(IngredienteProductoDTO ingredienteProductoDTO) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            entityManager.getTransaction().begin();

            IngredienteProducto relacion = new IngredienteProducto();

            Producto producto = entityManager.find(Producto.class, ingredienteProductoDTO.getIdProducto());
            if (producto == null) {
                throw new PersistenceException("El producto no existe");
            }
            relacion.setProducto(producto);

            Ingrediente ingrediente = entityManager.find(Ingrediente.class, ingredienteProductoDTO.getIdIngrediente());
            if (ingrediente == null) {
                throw new PersistenceException("El ingrediente no existe");
            }
            relacion.setIngrediente(ingrediente);

            relacion.setCantidad(ingredienteProductoDTO.getCantidad());

            entityManager.persist(relacion);
            entityManager.getTransaction().commit();

            return relacion;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al registrar la relación ingrediente-producto: " + e.getMessage(), e);
        }
    }

    /**
     * Busca todas las relaciones ingrediente-producto asociadas a un producto
     * específico.
     *
     * @param idProducto ID del producto para el cual se desea obtener las
     * asociaciones.
     * @return Lista de objetos `IngredienteProducto` vinculados al producto.
     * @throws PersistenciaException Si ocurre un error durante la consulta.
     */
    @Override
    public List<IngredienteProducto> buscarPorProducto(Long idProducto) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            String jpql = "SELECT ip FROM IngredienteProducto ip WHERE ip.producto.id = :idProducto";
            TypedQuery<IngredienteProducto> query = entityManager.createQuery(jpql, IngredienteProducto.class);
            query.setParameter("idProducto", idProducto);

            return query.getResultList();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al buscar ingredientes por producto: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina todas las asociaciones ingrediente-producto de un producto
     * específico.
     *
     * @param idProducto ID del producto cuyas asociaciones serán eliminadas.
     * @throws PersistenciaException Si ocurre un error durante la eliminación.
     */
    @Override
    public void eliminarIngredientesPorProducto(Long idProducto) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("DELETE FROM IngredienteProducto ip WHERE ip.producto.id = :idProducto");
            query.setParameter("idProducto", idProducto);
            query.executeUpdate();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al eliminar ingredientes asociados al producto: " + e.getMessage(), e);
        }
    }
  
}
