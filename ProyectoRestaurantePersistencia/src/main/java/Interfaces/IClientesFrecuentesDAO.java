/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Dominio.ClienteFrecuente;
import Excepciones.PersistenciaException;
import dto.ClienteFrecuenteDTO;
import dto.NuevoClienteFrecuenteDTO;
import java.util.Calendar;
import java.util.List;


/**
 *
 * @author santi
 */
public interface IClientesFrecuentesDAO {
    
    public abstract ClienteFrecuente registrarCliente(NuevoClienteFrecuenteDTO nuevoCliente) throws PersistenciaException;
    
    public abstract List<ClienteFrecuente> consultarClientesPorNombre(String filtroBusqueda) throws PersistenciaException;
    
    public abstract List<ClienteFrecuente> consultarClientesPorTelefono(String filtroBusqueda) throws PersistenciaException;
    
    public abstract List<ClienteFrecuente> consultarClientesPorCorreoElectronico(String filtroBusqueda) throws PersistenciaException;
    
    public abstract List<ClienteFrecuente> obtenerClientes() throws PersistenciaException;
    
    public abstract ClienteFrecuente editarCliente(ClienteFrecuenteDTO clienteDTO) throws PersistenciaException;
    
    public abstract ClienteFrecuente buscarClientePorId(Long idCliente) throws PersistenciaException;

    public abstract ClienteFrecuenteDTO obtenerClientesConFidelidad(ClienteFrecuente cliente) throws PersistenciaException;
    
    public abstract ClienteFrecuenteDTO obtenerDatosFidelidad(Long idCliente) throws PersistenciaException;
    
    public abstract Calendar obtenerUltimaVisita(Long idCliente) throws PersistenciaException;
    
    
    
}
