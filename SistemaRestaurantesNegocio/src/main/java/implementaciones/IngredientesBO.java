/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementaciones;

import Dominio.Ingrediente;
import Dominio.UnidadMedida;
import Excepciones.PersistenciaException;
import Interfaces.IIngredientesDAO;
import dto.NuevoIngredienteDTO;
import exception.NegocioException;
import interfaces.IIngredientesBO;
import java.util.List;

/**
 *
 * @author garfi
 */
public class IngredientesBO implements IIngredientesBO {

    private IIngredientesDAO ingredientesDAO;

    public IngredientesBO(IIngredientesDAO ingredientesDAO) {
        this.ingredientesDAO = ingredientesDAO;
    }

    @Override
    public Ingrediente registrarIngrediente(NuevoIngredienteDTO nuevoIngrediente) throws NegocioException {

        if (nuevoIngrediente.getNombre() == null || nuevoIngrediente.getNombre().isEmpty()) {
            throw new NegocioException("Debes proporcionar el nombre del ingrediente");
        }

        if (nuevoIngrediente.getNombre().equalsIgnoreCase("Nombre")) {
            throw new NegocioException("Debes proporcionar el nombre del ingrediente");
        }

        if (nuevoIngrediente.getStock() < 0) {
            throw new NegocioException("Debes proporcionar una cantidad válida en stock");
        }

        if (Float.isNaN(nuevoIngrediente.getStock())) {
            throw new NegocioException("Se ingresó la cantidad incorrectamente");
        }

        if (nuevoIngrediente.getUnidadMedida() == null) {
            throw new NegocioException("Debes proporcionar la unidad de medida (Piezas, gr o ml)");
        }
        
        if (existeIngrediente(nuevoIngrediente.getNombre(), nuevoIngrediente.getUnidadMedida(), nuevoIngrediente.getId()) == true) {
            throw new NegocioException("Ya existe un ingrediente con el mismo nombre y unidad de medida");
        }

        try {
            return this.ingredientesDAO.registrarIngrediente(nuevoIngrediente);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo registrar el ingrediente");
        }
    }

    @Override
    public List<Ingrediente> consultarIngredientes() throws NegocioException {
        try {
            return this.ingredientesDAO.consultarIngredientes();
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo registrar el cliente");
        }
    }
    
    @Override
    public List<Ingrediente> obtenerTodos()throws NegocioException{
        try {
            return this.ingredientesDAO.obtenerTodos();
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudieron obtener los ingredientes");
        }
    }

    @Override
    public Ingrediente editarIngrediente(NuevoIngredienteDTO nuevoIngredienteDTO) throws NegocioException {

        if (existeIngrediente(nuevoIngredienteDTO.getNombre(), nuevoIngredienteDTO.getUnidadMedida(), nuevoIngredienteDTO.getId()) == true) {
            throw new NegocioException("Ya existe un ingrediente con el mismo nombre y unidad de medida");
        }

        if (nuevoIngredienteDTO.getNombre() == null || nuevoIngredienteDTO.getNombre().isEmpty()) {
            throw new NegocioException("Debes proporcionar el nombre del ingrediente");
        }

        try {
            return this.ingredientesDAO.editarNombre(nuevoIngredienteDTO);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo editar el nombre del ingrediente");
        }
    }

 
    public boolean existeIngrediente(String nombre, UnidadMedida unidadMedida, Long idExcluido) throws NegocioException {
        try {
            List<Ingrediente> ingredientes = ingredientesDAO.consultarIngredientes();
            return ingredientes.stream()
                    .anyMatch(i -> i.getNombre().equalsIgnoreCase(nombre)
                    && i.getUnidadMedida().equals(unidadMedida));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al verificar la existencia del ingrediente", ex);
        }
    }

    @Override
    public Ingrediente aumentarStock(NuevoIngredienteDTO nuevoIngredienteDTO) throws NegocioException {
        try {
            return this.ingredientesDAO.aumentarStock(nuevoIngredienteDTO);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al aumentar el stock del ingrediente", ex);
        }
    }

    @Override
    public List<Ingrediente> consultarIngredientesPorNombre(String filtroNombre) throws NegocioException {
        try {
            return this.ingredientesDAO.consultarIngredientesPorNombre(filtroNombre);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al consultar ingredientes por nombre", ex);
        }
    }
}
