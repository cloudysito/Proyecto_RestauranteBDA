/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Dominio.Producto;
import Dominio.TipoProducto;
import Excepciones.PersistenciaException;
import Interfaces.IProductosDAO;
import conexcion.ManejadorConexiones;
import dto.NuevoProductoDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author emiim
 */
public class ProductosDAO implements IProductosDAO{
    /**
     * Registra un nuevo producto en la base de datos.
     *
     * @param nuevoProducto DTO con los datos del nuevo producto
     * @return El producto registrado
     * @throws PersistenciaException Si ocurre un error durante el registro
     */
    @Override
    public Producto registrarProducto(NuevoProductoDTO nuevoProducto) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            entityManager.getTransaction().begin();

            Producto producto = new Producto();
            producto.setNombre(nuevoProducto.getNombre());
            producto.setPrecio(nuevoProducto.getPrecio());
            producto.setTipoProducto(nuevoProducto.getTipoProducto());

            entityManager.persist(producto);
            entityManager.getTransaction().commit();

            return producto;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al registrar el producto: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Obtiene todos los productos almacenados en la base de datos.
     *
     * @return Lista de productos
     * @throws PersistenciaException Si ocurre un error durante la consulta
     */
    @Override
    public List<Producto> obtenerTodos() throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            TypedQuery<Producto> query = entityManager.createQuery("SELECT p FROM Producto p", Producto.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Obtiene los productos filtrados por tipo.
     *
     * @param tipo Tipo de producto a filtrar
     * @return Lista de productos que coinciden con el tipo
     * @throws PersistenciaException Si ocurre un error durante la consulta
     */
    @Override
    public List<Producto> obtenerProductoPorTipo(TipoProducto tipo) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            return entityManager
                    .createQuery("SELECT p FROM Producto p WHERE p.tipoProducto = :tipo", Producto.class)
                    .setParameter("tipo", tipo)
                    .getResultList();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al obtener productos por tipo: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Edita los datos de un producto existente.
     *
     * @param productoDTO DTO con los nuevos datos del producto
     * @return Producto actualizado
     * @throws PersistenciaException Si ocurre un error durante la actualización
     */
    @Override
    public Producto editarProducto(NuevoProductoDTO productoDTO) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            entityManager.getTransaction().begin();

            Producto productoEncontrado = entityManager.find(Producto.class, productoDTO.getId());

            productoEncontrado.setNombre(productoDTO.getNombre());
            productoEncontrado.setPrecio(productoDTO.getPrecio());
            productoEncontrado.setTipoProducto(productoDTO.getTipoProducto());

            entityManager.merge(productoEncontrado);
            entityManager.getTransaction().commit();

            return productoEncontrado;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al editar el producto: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Busca productos cuyo nombre contenga una cadena específica.
     *
     * @param nombre Cadena a buscar en los nombres de productos
     * @return Lista de productos que coincidan parcialmente con el nombre
     * @throws PersistenciaException Si ocurre un error durante la búsqueda
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();

        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Producto> criteria = builder.createQuery(Producto.class);
            Root<Producto> entidadProducto = criteria.from(Producto.class);

            Predicate nombreLike = builder.like(
                    builder.lower(entidadProducto.get("nombre")),
                    "%" + nombre.toLowerCase() + "%"
            );

            criteria.select(entidadProducto).where(nombreLike);

            TypedQuery<Producto> query = entityManager.createQuery(criteria);
            return query.getResultList();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new PersistenciaException("Error al buscar productos por nombre: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }
}
