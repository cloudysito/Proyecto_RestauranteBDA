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
public interface IMesasBO {

    public abstract List<Mesa> agregarMesas(NuevaMesaDTO nuevaMesa) throws NegocioException;

    public abstract List<Mesa> mostrarMesas() throws NegocioException;

    public abstract Mesa cambiarEstadoMesa(Long idMesa, EstadoMesa nuevoEstado) throws NegocioException;

}
