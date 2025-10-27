/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Dominio.Producto;
import Dominio.TipoProducto;
import dto.NuevoProductoDTO;
import exception.NegocioException;
import java.util.List;

/**
 *
 * @author garfi
 */
public interface IProductosBO {

    public Producto registrarProducto(NuevoProductoDTO nuevoProducto) throws NegocioException;

    public abstract List<Producto> obtenerTodos() throws NegocioException;

    public abstract List<Producto> obtenerProductoPorTipo(TipoProducto tipo) throws NegocioException;

    public Producto editarProducto(NuevoProductoDTO productoDTO) throws NegocioException;

    public abstract List<Producto> buscarPorNombre(String nombre) throws NegocioException;
    
}
