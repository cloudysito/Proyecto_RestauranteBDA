/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementaciones;

import DAOs.IngredienteProductoDAO;
import Dominio.IngredienteProducto;
import Excepciones.PersistenciaException;
import dto.IngredienteProductoDTO;
import exception.NegocioException;
import interfaces.IIngredientesProductosBO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author garfi
 */
public class IngredientesProductosBO implements IIngredientesProductosBO{
    private IngredienteProductoDAO ingredienteProductoDAO;
    private IngredienteProductoDTO ingredienteProductoDTO;
    
    
    public IngredientesProductosBO(IngredienteProductoDAO ingredienteProductoDAO) {
        this.ingredienteProductoDAO = ingredienteProductoDAO;
    }

    @Override
    public IngredienteProducto registrarIngredienteProducto(IngredienteProductoDTO ingredienteProductoDTO) throws NegocioException {
         if (ingredienteProductoDTO == null) {
        throw new NegocioException("No se proporcionó información del ingrediente.");
    }

    if (ingredienteProductoDTO.getIdProducto() == null) {
        throw new NegocioException("Falta el ID del producto.");
    }

    if (ingredienteProductoDTO.getIdIngrediente() == null) {
        throw new NegocioException("Debes seleccionar un ingrediente válido.");
    }

    if (ingredienteProductoDTO.getCantidad() == null) {
        throw new NegocioException("La cantidad debe ser mayor a cero.");
    }
    
        try {
            return ingredienteProductoDAO.registrarIngredienteProducto(ingredienteProductoDTO);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo registrar");
        }
    }

    @Override
    public List<IngredienteProductoDTO> buscarPorProducto(Long idProducto) throws NegocioException {
        List<IngredienteProducto> entidades;
        try {
            entidades = ingredienteProductoDAO.buscarPorProducto(idProducto);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar los ingredientes asociados al producto", ex);
        }

        List<IngredienteProductoDTO> ingredientesProductosDTOS = new ArrayList<>();

        for (IngredienteProducto entidad : entidades) {
            IngredienteProductoDTO ingredienteProductoDTO = new IngredienteProductoDTO();
            ingredienteProductoDTO.setIdProducto(entidad.getProducto().getId());
            ingredienteProductoDTO.setIdIngrediente(entidad.getIngrediente().getId());
            ingredienteProductoDTO.setIngrediente(entidad.getIngrediente());
            ingredienteProductoDTO.setCantidad(entidad.getCantidad());
            ingredientesProductosDTOS.add(ingredienteProductoDTO);
        }

        return ingredientesProductosDTOS;
    }

    @Override
    public void eliminarIngredientesPorProducto(Long idProducto) throws NegocioException {
        try {
            this.ingredienteProductoDAO.eliminarIngredientesPorProducto(idProducto);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al eliminar los ingredientes asociados al producto", ex);
        }
    }
}
