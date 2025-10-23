
package Interfaces;

import Dominio.IngredienteProducto;
import Excepciones.PersistenciaException;
import dto.IngredienteProductoDTO;
import java.util.List;


/**
 *
 * @author santi
 */
public interface IIngredienteProductoDAO {
    
    public IngredienteProducto registrarIngredienteProducto(IngredienteProductoDTO ingredienteProductoDTO) throws PersistenciaException;

    public abstract List<IngredienteProducto> buscarPorProducto(Long idProducto) throws PersistenciaException;
    
    public void eliminarIngredientesPorProducto(Long idProducto) throws PersistenciaException;
}
