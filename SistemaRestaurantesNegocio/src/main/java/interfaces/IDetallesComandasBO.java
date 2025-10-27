/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Dominio.DetalleComanda;
import dto.ComandaDTO;
import dto.DetalleComandaDTO;
import dto.ProductoSeleccionadoDTO;
import exception.NegocioException;
import java.util.List;

/**
 *
 * @author garfi
 */
public interface IDetallesComandasBO {

    public DetalleComanda guardarDetalleComanda(DetalleComandaDTO detalleComandaDTO) throws NegocioException;

    public abstract List<ProductoSeleccionadoDTO> obtenerDetalleComandaPorComanda(ComandaDTO comandaDTO) throws NegocioException;

    public abstract void editarDetalleComanda(Long idComanda, ProductoSeleccionadoDTO productoSeleccionado) throws NegocioException;

    public DetalleComanda ActualizarDetallesComanda(DetalleComandaDTO detalleComandaDTO) throws NegocioException;

    public abstract List<DetalleComandaDTO> obtenerDetallesDTOPorComanda(ComandaDTO comandaDTO) throws NegocioException;
    
}
