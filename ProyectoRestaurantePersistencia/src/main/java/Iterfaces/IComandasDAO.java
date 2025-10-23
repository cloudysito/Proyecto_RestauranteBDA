/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Iterfaces;

import Dominio.ClienteFrecuente;
import Dominio.Comanda;
import Excepciones.PersistenciaException;
import dto.ComandaDTO;
import dto.NuevaComandaDTO;
import java.util.Calendar;
import java.util.List;


/**
 *
 * @author santi
 */
public interface IComandasDAO {
    public abstract Comanda crearNuevarComanda(NuevaComandaDTO nuevaComanda) throws PersistenciaException;
    
    public abstract Comanda asociarClienteAComanda(Comanda comanda, ClienteFrecuente cliente) throws PersistenciaException;
    
    public abstract Comanda obtenerComandaPorId(Long idComanda) throws PersistenciaException;
    
    public abstract ComandaDTO obtenerComandaActivaPorMesa(Long idMesa) throws PersistenciaException;
    
    public abstract Comanda modificarEstadoComanda(ComandaDTO comandaDTO) throws PersistenciaException;
    
    public abstract Comanda cancelarComanda(ComandaDTO comandaDTO) throws PersistenciaException;
    
    public abstract List<ComandaDTO> obtenerComandas() throws PersistenciaException;
    
    public abstract List<ComandaDTO> obtenerComandasPorFechas(Calendar fechaInicio, Calendar fechaFin) throws PersistenciaException;

}
