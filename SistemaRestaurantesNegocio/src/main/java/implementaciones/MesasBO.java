/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementaciones;

import Dominio.EstadoMesa;
import Dominio.Mesa;
import Excepciones.PersistenciaException;
import Interfaces.IMesasDAO;
import dto.NuevaMesaDTO;
import exception.NegocioException;
import interfaces.IMesasBO;
import java.util.List;

/**
 *
 * @author garfi
 */
public class MesasBO implements IMesasBO {
    
    private IMesasDAO mesasDAO;
    
    public static final int LIMITE_MESAS = 80;
    
    public MesasBO(IMesasDAO mesasDAO) {
        this.mesasDAO = mesasDAO;
    }

    @Override
    public List<Mesa> agregarMesas(NuevaMesaDTO nuevaMesa) throws NegocioException {
        try {
            return mesasDAO.agregarMesas(nuevaMesa);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al registrar nuevas mesas", ex);
        }
    }

    @Override
    public List<Mesa> mostrarMesas() throws NegocioException {
        try {
            return this.mesasDAO.mostrarMesas();
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al obtener la lista de mesas", ex);
        }
    }

    @Override
    public Mesa cambiarEstadoMesa(Long idMesa, EstadoMesa nuevoEstado) throws NegocioException {
        try {
            return this.mesasDAO.cambiarEstadoMesa(idMesa, nuevoEstado);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al cambiar el estado de la mesa", ex);
        }
    }
}
