/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Iterfaces;

import Dominio.DetalleComanda;
import Excepciones.PersistenciaException;
import dto.ComandaDTO;
import dto.DetalleComandaDTO;
import dto.ProductoSeleccionadoDTO;
import java.util.List;


/**
 *
 * @author santi
 */
public interface IDetallesComandasDAO {
    
    public abstract DetalleComanda guardarDetalleComanda(DetalleComandaDTO detalleComanda) throws PersistenciaException;
    
    public abstract DetalleComanda guardarDetallesComandas(List<DetalleComandaDTO> detalleComanda) throws PersistenciaException;
    
    public abstract List<ProductoSeleccionadoDTO> obtenerDetalleComandaPorComanda(ComandaDTO comandaDTO) throws PersistenciaException;
    
    public abstract void editarDetalleComanda(Long idComanda, ProductoSeleccionadoDTO productoSeleccionado) throws PersistenciaException;
    
    public abstract DetalleComanda ActualizarDetallesComanda(DetalleComandaDTO detalleComandaDTO) throws PersistenciaException;
    
    public abstract List<DetalleComandaDTO> obtenerDetallesDTOPorComanda(ComandaDTO comandaDTO) throws PersistenciaException;
}
