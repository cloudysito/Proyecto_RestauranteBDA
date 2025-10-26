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
public interface IIngredientesBO {

    public abstract Ingrediente registrarIngrediente(NuevoIngredienteDTO nuevoIngrediente) throws NegocioException;

    public abstract List<Ingrediente> consultarIngredientes() throws NegocioException;

    public abstract List<Ingrediente> obtenerTodos() throws NegocioException;

    public abstract Ingrediente editarIngrediente(NuevoIngredienteDTO nuevoIngredienteDTO) throws NegocioException;

    public abstract Ingrediente aumentarStock(NuevoIngredienteDTO nuevoIngredienteDTO) throws NegocioException;

    public abstract List<Ingrediente> consultarIngredientesPorNombre(String filtroNombre) throws NegocioException;
    
}
