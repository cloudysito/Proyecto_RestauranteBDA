/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Dominio.EstadoMesa;
import Dominio.Mesa;
import Excepciones.PersistenciaException;
import dto.NuevaMesaDTO;
import java.util.List;


/**
 *
 * @author santi
 */
public interface IMesasDAO {
    
    public abstract List<Mesa> agregarMesas(NuevaMesaDTO nuevaMesa) throws PersistenciaException;
    
    public abstract List<Mesa> mostrarMesas() throws PersistenciaException;
    
    public abstract Mesa cambiarEstadoMesa(Long idMesa, EstadoMesa nuevaMesa) throws PersistenciaException;
     
}
