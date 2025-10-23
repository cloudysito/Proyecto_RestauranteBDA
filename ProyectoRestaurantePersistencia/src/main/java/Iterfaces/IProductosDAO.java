/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Iterfaces;

import Dominio.Producto;
import Dominio.TipoProducto;
import Excepciones.PersistenciaException;
import dto.NuevoProductoDTO;
import java.util.List;


/**
 *
 * @author santi
 */
public interface IProductosDAO {
    
    public Producto registrarProducto(NuevoProductoDTO nuevoProducto) throws PersistenciaException;
    
    public abstract List<Producto> obtenerTodos() throws PersistenciaException;
    
    public abstract List<Producto> obtenerProductoPorTipo(TipoProducto tipo) throws PersistenciaException;
    
    public Producto editarProducto(NuevoProductoDTO nuevoProducto) throws PersistenciaException;
    
    public abstract List<Producto> buscarPorNombre(String nombre) throws PersistenciaException;
}
