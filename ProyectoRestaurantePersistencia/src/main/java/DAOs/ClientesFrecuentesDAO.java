/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Excepciones.PersistenciaException;
import Interfaces.IClientesFrecuentesDAO;
import java.util.Calendar;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author emiim
 */
public class ClientesFrecuentesDAO implements IClientesFrecuentesDAO {
     /**
     * Registra un nuevo cliente frecuente en la base de datos.
     * 
     * @param nuevoCliente DTO con los datos del nuevo cliente
     * @return El cliente frecuente registrado
     * @throws PersistenciaException Si ocurre un error durante la operación
     */
    @Override
    public ClienteFrecuente registrarCliente(NuevoClienteFrecuenteDTO nuevoCliente) throws PersistenciaException {
        
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
        
        try{
            entityManager.getTransaction().begin();
        
            ClienteFrecuente cliente = new ClienteFrecuente();
            cliente.setNombre(nuevoCliente.getNombre());
            cliente.setApellidoPaterno(nuevoCliente.getApellidoPaterno());
            cliente.setApellidoMaterno(nuevoCliente.getApellidoMaterno());
            cliente.setCorreoElectronico(nuevoCliente.getCorreoElectronico());
            cliente.setTelefono(nuevoCliente.getTelefono());
            cliente.setFechaRegistro(Calendar.getInstance());

            entityManager.persist(cliente);
            entityManager.getTransaction().commit();

            return cliente;
        } catch (Exception e){
            entityManager.getTransaction().rollback();           
            throw new PersistenciaException("No se pudo registrar el cliente" + e);           
        }        
    }
    
    /**
     * Consulta clientes cuyo nombre completo coincida parcialmente con el filtro.
     * 
     * @param filtroBusqueda Cadena de búsqueda
     * @return Lista de clientes que coinciden con el filtro
     * @throws PersistenciaException Si ocurre un error durante la consulta
     */
    @Override
    public List<ClienteFrecuente> consultarClientesPorNombre(String filtroBusqueda) throws PersistenciaException{
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
        
        try{
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<ClienteFrecuente> criteria = builder.createQuery(ClienteFrecuente.class);
            Root<ClienteFrecuente> entidadCliente = criteria.from(ClienteFrecuente.class);

            String filtro = "%" + filtroBusqueda.toLowerCase() + "%";

            criteria = criteria.select(entidadCliente).where(
                                                            builder.like(
                                                                builder.lower(
                                                                    builder.concat(
                                                                        builder.concat(
                                                                            builder.concat(entidadCliente.get("nombre"), " "),
                                                                            builder.concat(entidadCliente.get("apellidoPaterno"), " ")
                                                                        ),
                                                                        entidadCliente.get("apellidoMaterno")
                                                                    )
                                                                ),
                                                                filtro
                                                            )
                                                        );

            TypedQuery<ClienteFrecuente> query = entityManager.createQuery(criteria);
            List<ClienteFrecuente> clientes = query.getResultList();
            
            return clientes;
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            
            throw new PersistenciaException("No se pudieron obtener los clientes" + e);
            
        }            
    }
    
    /**
     * Consulta clientes cuyo teléfono encriptado coincida parcialmente con el filtro.
     * 
     * @param filtroBusqueda Cadena de búsqueda
     * @return Lista de clientes que coinciden con el filtro
     * @throws PersistenciaException Si ocurre un error durante la consulta
     */
    @Override
    public List<ClienteFrecuente> consultarClientesPorTelefono(String filtroBusqueda) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
        
        try{
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<ClienteFrecuente> criteria = builder.createQuery(ClienteFrecuente.class);
            Root<ClienteFrecuente> entidadCliente = criteria.from(ClienteFrecuente.class);
            String filtro = filtroBusqueda;

            criteria = criteria.select(entidadCliente).where(builder.like(entidadCliente.get("telefonoEncriptado"), filtro));
            TypedQuery<ClienteFrecuente> query = entityManager.createQuery(criteria);
            List<ClienteFrecuente> clientes = query.getResultList();
            
        return clientes;
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            
            throw new PersistenciaException("No se pudieron obtener los clientes" + e);           
        }                
    }
    
    /**
     * Consulta clientes cuyo correo electrónico coincida parcialmente con el filtro.
     * 
     * @param filtroBusqueda Cadena de búsqueda
     * @return Lista de clientes que coinciden con el filtro
     * @throws PersistenciaException Si ocurre un error durante la consulta
     */
    @Override
    public List<ClienteFrecuente> consultarClientesPorCorreoElectronico(String filtroBusqueda) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
                  
        try{            
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<ClienteFrecuente> criteria = builder.createQuery(ClienteFrecuente.class);
            Root<ClienteFrecuente> entidadCliente = criteria.from(ClienteFrecuente.class);

            String filtro = filtroBusqueda.toLowerCase();

            criteria = criteria.select(entidadCliente).where(builder.like(entidadCliente.get("correoElectronico"), filtro));

            TypedQuery<ClienteFrecuente> query = entityManager.createQuery(criteria);
            List<ClienteFrecuente> clientes = query.getResultList();
            return clientes;

        } catch (Exception e){
            entityManager.getTransaction().rollback();           
            throw new PersistenciaException("No se pudieron obtener los clientes" + e);            
        }       
    }
    
    /**
     * Obtiene todos los clientes frecuentes registrados en la base de datos.
     * 
     * @return Lista de clientes frecuentes
     * @throws PersistenciaException Si ocurre un error durante la consulta
     */
    @Override
    public List<ClienteFrecuente> obtenerClientes() throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
        
            
        try{
            String jpqlQuery = "SELECT v FROM ClienteFrecuente v ORDER BY v.nombre ASC";

            TypedQuery<ClienteFrecuente> query = entityManager.createQuery(jpqlQuery, ClienteFrecuente.class);
            List<ClienteFrecuente> clientes = query.getResultList();
            
            return clientes;
        } catch (Exception e){
            entityManager.getTransaction().rollback();           
            throw new PersistenciaException("No se pudieron obtener los clientes" + e);            
        }     
    }
    
    /**
     * Edita la información de un cliente frecuente.
     * 
     * @param clienteFrecuenteDTO DTO con los datos actualizados
     * @return Cliente frecuente actualizado
     * @throws PersistenciaException Si ocurre un error durante la actualización
     */
    @Override
    public ClienteFrecuente editarCliente(ClienteFrecuenteDTO clienteFrecuenteDTO) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
         
        try{
            entityManager.getTransaction().begin();

            ClienteFrecuente clienteEncontrado = buscarClientePorId(clienteFrecuenteDTO.getId());
            clienteEncontrado.setNombre(clienteFrecuenteDTO.getNombre());
            clienteEncontrado.setApellidoPaterno(clienteFrecuenteDTO.getApellidoPaterno());
            clienteEncontrado.setApellidoMaterno(clienteFrecuenteDTO.getApellidoMaterno());
            clienteEncontrado.setCorreoElectronico(clienteFrecuenteDTO.getCorreoElectronico());
            clienteEncontrado.setTelefono(clienteFrecuenteDTO.getTelefono());

            entityManager.merge(clienteEncontrado);
            entityManager.getTransaction().commit();   
            
            return clienteEncontrado;
        } catch (Exception e){
            entityManager.getTransaction().rollback();            
            throw new PersistenciaException("No se pudo editar el cliente" + e);            
        }    
    }
    
    /**
     * Busca un cliente frecuente por su ID.
     * 
     * @param idCliente ID del cliente
     * @return ClienteFrecuente correspondiente al ID
     * @throws PersistenciaException Si ocurre un error durante la búsqueda
     */
    @Override
    public ClienteFrecuente buscarClientePorId(Long idCliente) throws PersistenciaException {   
        EntityManager entityManager = ManejadorConexiones.getEntityManager();            
        try{
            ClienteFrecuente cliente = entityManager.find(ClienteFrecuente.class, idCliente);
            
            return cliente;   
        } catch (Exception e){
            entityManager.getTransaction().rollback();           
            throw new PersistenciaException("No se pudo obtener el cliente" + e);            
        }           
    }
    
    /**
     * Obtiene los datos de fidelidad (visitas, gasto, puntos) de un cliente.
     * 
     * @param cliente ClienteFrecuente al que se le consultan los datos
     * @return DTO con los datos de fidelidad
     * @throws PersistenciaException Si ocurre un error durante la consulta
     */
    @Override
    public ClienteFrecuenteDTO obtenerClientesConFidelidad(ClienteFrecuente cliente) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
        
        try{           
            VistaFidelidadCliente vista = entityManager.find(VistaFidelidadCliente.class, cliente.getId());
            if (vista == null) {
                return new ClienteFrecuenteDTO(cliente, 0, 0f, 0);
            }
            
            return new ClienteFrecuenteDTO(cliente,vista.getVisitas(),vista.getGastoTotal(), vista.getPuntosFidelidad());
        } catch (Exception e){
            entityManager.getTransaction().rollback();           
            throw new PersistenciaException("No se pudo obtener el cliente" + e);          
        } 
    }
    
    /**
     * Obtiene los datos de fidelidad (visitas, gasto, puntos) de un cliente por su ID.
     * 
     * @param idCliente ID del cliente
     * @return DTO con los datos de fidelidad
     * @throws PersistenciaException Si ocurre un error durante la consulta
     */
    @Override
    public ClienteFrecuenteDTO obtenerDatosFidelidad(Long idCliente) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
                  
        try{           
            VistaFidelidadCliente vista = entityManager.find(VistaFidelidadCliente.class, idCliente);           
            if (vista == null) {
                return new ClienteFrecuenteDTO(null, 0, 0f, 0);
            }
            ClienteFrecuente cliente = entityManager.find(ClienteFrecuente.class, idCliente);
            
            return new ClienteFrecuenteDTO(cliente, vista.getVisitas(), vista.getGastoTotal(), vista.getPuntosFidelidad());
        } catch (Exception e){
            entityManager.getTransaction().rollback();            
            throw new PersistenciaException("No se pudieron obtener los datos de fidelidad" + e);            
        }
    }
    
    /**
     * Obtiene la fecha de la última visita de un cliente.
     * 
     * @param idCliente ID del cliente
     * @return Fecha de la última visita, o null si no hay registros
     * @throws PersistenciaException Si ocurre un error durante la consulta
     */
    @Override
    public Calendar obtenerUltimaVisita(Long idCliente) throws PersistenciaException {
        EntityManager entityManager = ManejadorConexiones.getEntityManager();
            
        try{
            String jpql = "SELECT c.fechaHoraCreacion FROM Comanda c WHERE c.cliente.id = :idCliente ORDER BY c.fechaHoraCreacion DESC";
            TypedQuery<Calendar> query = entityManager.createQuery(jpql, Calendar.class);
            query.setParameter("idCliente", idCliente);
            query.setMaxResults(1);
            List<Calendar> resultados = query.getResultList();
            
            return resultados.isEmpty() ? null : resultados.get(0);
        } catch (Exception e){
            entityManager.getTransaction().rollback();            
            throw new PersistenciaException("No se pudo obtener la ultima visita" + e);            
        }  
    }
}
