/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementaciones;

import Dominio.DetalleComanda;
import Excepciones.PersistenciaException;
import Interfaces.IDetallesComandasDAO;
import dto.ComandaDTO;
import dto.DetalleComandaDTO;
import dto.ProductoSeleccionadoDTO;
import exception.NegocioException;
import interfaces.IDetallesComandasBO;
import java.util.List;

/**
 *
 * @author garfi
 */
public class DetallesComandasBO implements IDetallesComandasBO {
    
    private IDetallesComandasDAO detallesComandasDAO;

    public DetallesComandasBO(IDetallesComandasDAO detallesComandasDAO) {
        this.detallesComandasDAO = detallesComandasDAO;
    }


   @Override
    public DetalleComanda guardarDetalleComanda(DetalleComandaDTO detalleComandaDTO) throws NegocioException {
        try {
            return this.detallesComandasDAO.guardarDetalleComanda(detalleComandaDTO);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al guardar el detalle de la comanda", ex);
        }
    }

    @Override
    public List<ProductoSeleccionadoDTO> obtenerDetalleComandaPorComanda(ComandaDTO comandaDTO) throws NegocioException {
        try {
            return this.detallesComandasDAO.obtenerDetalleComandaPorComanda(comandaDTO);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al obtener los detalles de la comanda", ex);
        }
    }

    @Override
    public void editarDetalleComanda(Long idComanda, ProductoSeleccionadoDTO productoSeleccionado) throws NegocioException {
        try {
            this.detallesComandasDAO.editarDetalleComanda(idComanda, productoSeleccionado);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al editar el detalle de la comanda", ex);
        }
    }

    @Override
    public DetalleComanda ActualizarDetallesComanda(DetalleComandaDTO detalleComandaDTO) throws NegocioException {
        try {
            return this.detallesComandasDAO.ActualizarDetallesComanda(detalleComandaDTO);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al actualizar el detalle de la comanda", ex);
        }
    }

    @Override
    public List<DetalleComandaDTO> obtenerDetallesDTOPorComanda(ComandaDTO comandaDTO) throws NegocioException {
        try {
            return this.detallesComandasDAO.obtenerDetallesDTOPorComanda(comandaDTO);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo obtener el detalle de la comanda");
        }
    }
  
}