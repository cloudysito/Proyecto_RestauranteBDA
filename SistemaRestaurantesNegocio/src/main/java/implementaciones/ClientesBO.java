/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementaciones;

import Dominio.ClienteFrecuente;
import Excepciones.PersistenciaException;
import Interfaces.IClientesFrecuentesDAO;
import Utilidad.SeguridadUtil;
import dto.ClienteFrecuenteDTO;
import dto.NuevoClienteFrecuenteDTO;
import exception.NegocioException;
import interfaces.IClientesBO;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author garfi
 */
public class ClientesBO implements IClientesBO {

    private IClientesFrecuentesDAO clientesFrecuentesDAO;

    private static final int LIMITE_CARACTERES_NOMBRE = 100;
    private static final int LIMITE_CARACTERES_CORREO_ELECTRONICO = 100;
    private static final int LIMITE_CARACTERES_TELEFONO = 20;
    private static final int LIMITE_CARACTERES = 100;

    public ClientesBO(IClientesFrecuentesDAO clientesFrecuentesDAO) {
        this.clientesFrecuentesDAO = clientesFrecuentesDAO;
    }

    @Override
    public ClienteFrecuente registrarCliente(NuevoClienteFrecuenteDTO nuevoClienteFrecuente) throws NegocioException {

        // Validaciones de campos obligatorios
        if (nuevoClienteFrecuente.getNombre() == null || nuevoClienteFrecuente.getNombre().trim().isEmpty()) {
            throw new NegocioException("Debes proporcionar el nombre del cliente");
        }

        if (nuevoClienteFrecuente.getApellidoPaterno() == null || nuevoClienteFrecuente.getApellidoPaterno().trim().isEmpty()) {
            throw new NegocioException("Debes proporcionar el apellido paterno del cliente");
        }

        if (nuevoClienteFrecuente.getApellidoMaterno() == null || nuevoClienteFrecuente.getApellidoMaterno().trim().isEmpty()) {
            throw new NegocioException("Debes proporcionar el apellido materno del cliente");
        }

        if (nuevoClienteFrecuente.getTelefono() == null || nuevoClienteFrecuente.getTelefono().trim().isEmpty()) {
            throw new NegocioException("Debes proporcionar el teléfono del cliente");
        }

        // Validaciones de formato (solo letras para nombre y apellidos)
        if (!nuevoClienteFrecuente.getNombre().matches("^[a-zA-Z ]+$")) {
            throw new NegocioException("El nombre solo puede contener letras y espacios simples");
        }

        if (!nuevoClienteFrecuente.getApellidoPaterno().matches("^[a-zA-Z]+$")) {
            throw new NegocioException("El apellido paterno solo puede contener letras");
        }

        if (!nuevoClienteFrecuente.getApellidoMaterno().matches("^[a-zA-Z]+$")) {
            throw new NegocioException("El apellido materno solo puede contener letras");
        }

        // Validaciones de longitud
        if (nuevoClienteFrecuente.getNombre().length() > LIMITE_CARACTERES_NOMBRE) {
            throw new NegocioException("El nombre del cliente excede el límite de " + LIMITE_CARACTERES_NOMBRE + " caracteres.");
        }

        if (nuevoClienteFrecuente.getApellidoPaterno().length() > LIMITE_CARACTERES_NOMBRE) {
            throw new NegocioException("El apellido paterno excede el límite de " + LIMITE_CARACTERES_NOMBRE + " caracteres.");
        }

        if (nuevoClienteFrecuente.getApellidoMaterno().length() > LIMITE_CARACTERES_NOMBRE) {
            throw new NegocioException("El apellido materno excede el límite de " + LIMITE_CARACTERES_NOMBRE + " caracteres.");
        }

        if (nuevoClienteFrecuente.getCorreoElectronico().length() > LIMITE_CARACTERES_CORREO_ELECTRONICO) {
            throw new NegocioException("El correo electrónico excede el límite de " + LIMITE_CARACTERES_CORREO_ELECTRONICO + " caracteres.");
        }

        // Validación de formato de teléfono
        String telefono = nuevoClienteFrecuente.getTelefono();
        if (!telefono.matches("^\\+?\\d+$")) {
            throw new NegocioException("El teléfono solo puede contener números y puede comenzar con un +.");
        }

        if (nuevoClienteFrecuente.getTelefono().length() > LIMITE_CARACTERES_TELEFONO) {
            throw new NegocioException("El teléfono excede el límite de " + LIMITE_CARACTERES_TELEFONO + " caracteres.");
        }

        // Validación de formato de correo electrónico
        String correo = nuevoClienteFrecuente.getCorreoElectronico();
        if (correo != null && !correo.trim().isEmpty()) {
            if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                throw new NegocioException("El formato del correo electrónico no es válido.");
            }
        }

        try {
            // Validar que no exista un cliente con el mismo hash de teléfono
            String hashTelefono = SeguridadUtil.generarHash(nuevoClienteFrecuente.getTelefono());
            if (existeClienteConHashTelefono(hashTelefono)) {
                throw new NegocioException("Ya existe un cliente registrado con este número de teléfono");
            }

            return this.clientesFrecuentesDAO.registrarCliente(nuevoClienteFrecuente);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo registrar el cliente");
        }
    }

    private boolean existeClienteConHashTelefono(String hashTelefono) throws PersistenciaException {
        List<ClienteFrecuente> clientes = clientesFrecuentesDAO.obtenerClientes();
        return clientes.stream()
                .anyMatch(c -> (c).getHashTelefono().equals(hashTelefono));
    }

    @Override
    public List<ClienteFrecuente> consultarClientesPorNombre(String filtroBusqueda) throws NegocioException {
        if (filtroBusqueda.length() > LIMITE_CARACTERES) {
            throw new NegocioException("El Filtro de busqueda es demasiado largo");
        }
        try {
            return this.clientesFrecuentesDAO.consultarClientesPorNombre(filtroBusqueda);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo consultar el cliente");
        }
    }

    @Override
    public List<ClienteFrecuente> consultarClientesPorTelefono(String filtroBusqueda) throws NegocioException {
        if (filtroBusqueda.length() > LIMITE_CARACTERES_TELEFONO) {
            throw new NegocioException("El Filtro de busqueda es demasiado largo");
        }
        try {
            return this.clientesFrecuentesDAO.consultarClientesPorTelefono(filtroBusqueda);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo registrar el cliente");
        }
    }

    @Override
    public List<ClienteFrecuente> consultarClientesPorCorreoElectronico(String filtroBusqueda) throws NegocioException {
        if (filtroBusqueda.length() > LIMITE_CARACTERES_CORREO_ELECTRONICO) {
            throw new NegocioException("El Filtro de busqueda es demasiado largo");
        }
        try {
            return this.clientesFrecuentesDAO.consultarClientesPorCorreoElectronico(filtroBusqueda);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudieron consultar los clientes");
        }
    }

    @Override
    public List<ClienteFrecuente> obtenerClientes() throws NegocioException {
        try {
            return this.clientesFrecuentesDAO.obtenerClientes();
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudieron consultar los clientes");
        }
    }

    @Override
    public ClienteFrecuente editarCliente(ClienteFrecuenteDTO clienteFrecuenteDTO) throws NegocioException {

        // Validaciones de campos obligatorios
        if (clienteFrecuenteDTO.getNombre() == null || clienteFrecuenteDTO.getNombre().trim().isEmpty()) {
            throw new NegocioException("Debes proporcionar el nombre del cliente");
        }

        if (clienteFrecuenteDTO.getApellidoPaterno() == null || clienteFrecuenteDTO.getApellidoPaterno().trim().isEmpty()) {
            throw new NegocioException("Debes proporcionar el apellido paterno del cliente");
        }

        if (clienteFrecuenteDTO.getApellidoMaterno() == null || clienteFrecuenteDTO.getApellidoMaterno().trim().isEmpty()) {
            throw new NegocioException("Debes proporcionar el apellido materno del cliente");
        }

        if (clienteFrecuenteDTO.getTelefono() == null || clienteFrecuenteDTO.getTelefono().trim().isEmpty()) {
            throw new NegocioException("Debes proporcionar el teléfono del cliente");
        }

        // Validaciones de formato (solo letras para nombre y apellidos)
        if (!clienteFrecuenteDTO.getNombre().matches("^[a-zA-Z ]+$")) {
            throw new NegocioException("El nombre solo puede contener letras y espacios simples");
        }

        if (!clienteFrecuenteDTO.getApellidoPaterno().matches("^[a-zA-Z]+$")) {
            throw new NegocioException("El apellido paterno solo puede contener letras");
        }

        if (!clienteFrecuenteDTO.getApellidoMaterno().matches("^[a-zA-Z]+$")) {
            throw new NegocioException("El apellido materno solo puede contener letras");
        }

        // Validaciones de longitud
        if (clienteFrecuenteDTO.getNombre().length() > LIMITE_CARACTERES_NOMBRE) {
            throw new NegocioException("El nombre del cliente excede el límite de " + LIMITE_CARACTERES_NOMBRE + " caracteres.");
        }

        if (clienteFrecuenteDTO.getApellidoPaterno().length() > LIMITE_CARACTERES_NOMBRE) {
            throw new NegocioException("El apellido paterno excede el límite de " + LIMITE_CARACTERES_NOMBRE + " caracteres.");
        }

        if (clienteFrecuenteDTO.getApellidoMaterno().length() > LIMITE_CARACTERES_NOMBRE) {
            throw new NegocioException("El apellido materno excede el límite de " + LIMITE_CARACTERES_NOMBRE + " caracteres.");
        }

        if (clienteFrecuenteDTO.getCorreoElectronico().length() > LIMITE_CARACTERES_CORREO_ELECTRONICO) {
            throw new NegocioException("El correo electrónico excede el límite de " + LIMITE_CARACTERES_CORREO_ELECTRONICO + " caracteres.");
        }

        if (clienteFrecuenteDTO.getTelefono().length() > LIMITE_CARACTERES_TELEFONO) {
            throw new NegocioException("El teléfono excede el límite de " + LIMITE_CARACTERES_TELEFONO + " caracteres.");
        }

        // Validación de formato de teléfono
        String telefono = clienteFrecuenteDTO.getTelefono();
        if (!telefono.matches("^\\+?\\d+$")) {
            throw new NegocioException("El teléfono solo puede contener números y puede comenzar con un +.");
        }

        // Validación de formato de correo electrónico
        String correo = clienteFrecuenteDTO.getCorreoElectronico();
        if (correo != null && !correo.trim().isEmpty()) {
            if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                throw new NegocioException("El formato del correo electrónico no es válido.");
            }
        }

        try {
            // Validar que no exista un cliente con el mismo hash de teléfono
            String hashTelefono = SeguridadUtil.generarHash(clienteFrecuenteDTO.getTelefono());
            if (existeClienteConHashTelefono(hashTelefono)) {
                throw new NegocioException("Ya existe un cliente registrado con este número de teléfono");
            }

            return this.clientesFrecuentesDAO.editarCliente(clienteFrecuenteDTO);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo editar el cliente");
        }
    }

    @Override
    public ClienteFrecuente buscarClientePorId(Long id) throws NegocioException {
        try {
            return this.clientesFrecuentesDAO.buscarClientePorId(id);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo consultar el cliente");
        }
    }

    @Override
    public ClienteFrecuenteDTO obtenerClientesConFidelidad(ClienteFrecuente cliente) throws NegocioException {
        try {
            return this.clientesFrecuentesDAO.obtenerClientesConFidelidad(cliente);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo consultar el cliente");
        }
    }

    @Override
    public ClienteFrecuenteDTO obtenerDatosFidelidad(Long idCliente) throws NegocioException {
        try {
            return this.clientesFrecuentesDAO.obtenerDatosFidelidad(idCliente);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudieron obtener los datos");
        }
    }

    @Override
    public Calendar obtenerUltimaVisita(Long idCliente) throws NegocioException {
        try {
            return this.clientesFrecuentesDAO.obtenerUltimaVisita(idCliente);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo obtener la ultima visita");
        }
    }
}
