/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import java.util.List;

/**
 *
 * @author garfi
 */
public interface IIngredientesProductosBO {

    public IngredienteProducto registrarIngredienteProducto(IngredienteProductoDTO ingredienteProductoDTO) throws NegocioException;

    public abstract List<IngredienteProductoDTO> buscarPorProducto(Long idProducto) throws NegocioException;

    public void eliminarIngredientesPorProducto(Long idProducto) throws NegocioException;
    
}
