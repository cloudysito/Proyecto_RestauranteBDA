/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Iterfaces;

import Dominio.Ingrediente;
import Excepciones.PersistenciaException;
import dto.NuevoIngredienteDTO;
import java.util.List;


/**
 *
 * @author santi
 */
public interface IIngredientesDAO {
    
    public abstract Ingrediente registrarIngrediente(NuevoIngredienteDTO nuevoIngrediente) throws PersistenciaException;
    
    public abstract List<Ingrediente> consultarIngredientes() throws PersistenciaException;
    
    public abstract List<Ingrediente> obtenerTodos() throws PersistenciaException;
    
    public abstract Ingrediente buscarIngredienteId(Long idCliente) throws PersistenciaException;   
    
    public abstract Ingrediente editarNombre(NuevoIngredienteDTO nuevoIngredienteDTO) throws PersistenciaException;
    
    public abstract Ingrediente aumentarStock(NuevoIngredienteDTO nuevoIngredienteDTO) throws PersistenciaException;
    
    public abstract List<Ingrediente> consultarIngredientesPorNombre(String nombre) throws PersistenciaException;

}
