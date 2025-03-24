package com.example.Veterinaria.Service.Impl;

import com.example.Veterinaria.Model.Cliente;
import com.example.Veterinaria.Model.Rol;
import com.example.Veterinaria.Repository.ClienteRepository;
import com.example.Veterinaria.Repository.RolRepository;
import com.example.Veterinaria.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz ClienteService que proporciona la lógica de negocio
 * para la gestión de clientes en la veterinaria.
 */
@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    /**
     * Repositorio para realizar operaciones CRUD con la entidad Cliente
     */
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Recupera todos los clientes de la base de datos
     * @return Lista con todos los clientes encontrados
     */
    @Override
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    /**
     * Busca un cliente específico por su ID
     * @param id Identificador único del cliente
     * @return Optional conteniendo el cliente si existe
     */
    @Override
    public Optional<Cliente> getClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    /**
     * Busca un cliente por su número de DNI
     * @param dni Número de documento de identidad del cliente
     * @return Cliente encontrado o null si no existe
     */
    @Override
    public Cliente getClienteDniById(String dni) {
        return clienteRepository.findByDni(dni);
    }

    /**
     * Guarda un nuevo cliente en la base de datos
     * @param cliente Datos del cliente a guardar
     * @return Cliente guardado con su ID generado
     * @throws RuntimeException si ya existe un cliente con el mismo DNI
     */
    @Override
    public Cliente saveCliente(Cliente cliente) {
        // Validar que no exista otro cliente con el mismo DNI
        if (cliente.getDni() != null && clienteRepository.findByDni(cliente.getDni()) != null) {
            throw new RuntimeException("Ya existe un cliente con ese DNI");
        }

        // Validar que la contraseña no sea nula
        if (cliente.getPassword() == null || cliente.getPassword().trim().isEmpty()) {
            throw new RuntimeException("La contraseña es obligatoria");
        }

        // Encriptar la contraseña
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));

        // Asignar rol de CLIENTE si no tiene roles asignados
        if (cliente.getRoles() == null || cliente.getRoles().isEmpty()) {
            Rol rolCliente = rolRepository.findByNombre("ROLE_CLIENTE")
                .orElseThrow(() -> new RuntimeException("Error: Rol de cliente no encontrado"));
            cliente.getRoles().add(rolCliente);
        }

        return clienteRepository.save(cliente);
    }

    /**
     * Elimina un cliente de la base de datos
     * @param id Identificador único del cliente a eliminar
     */
    @Override
    public void deleteClienteById(Long id) {
        clienteRepository.deleteById(id);
    }

    /**
     * Actualiza los datos de un cliente existente
     * @param id Identificador único del cliente a actualizar
     * @param clienteDetails Nuevos datos del cliente
     * @return Cliente actualizado
     * @throws RuntimeException si el cliente no existe o si el nuevo DNI ya está en uso
     */
    @Override
    public Cliente updateCliente(Long id, Cliente clienteDetails) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    // Validar DNI único si se está cambiando
                    if (!cliente.getDni().equals(clienteDetails.getDni()) && 
                        clienteRepository.findByDni(clienteDetails.getDni()) != null) {
                        throw new RuntimeException("Ya existe un cliente con ese DNI");
                    }

                    // Actualizar campos básicos
                    cliente.setNombre(clienteDetails.getNombre());
                    cliente.setApellido(clienteDetails.getApellido());
                    cliente.setDni(clienteDetails.getDni());
                    cliente.setTelefono(clienteDetails.getTelefono());
                    cliente.setEmail(clienteDetails.getEmail());
                    cliente.setDireccion(clienteDetails.getDireccion());
                    cliente.setEstado(clienteDetails.getEstado());

                    // Actualizar contraseña solo si se proporciona una nueva
                    if (clienteDetails.getPassword() != null && !clienteDetails.getPassword().trim().isEmpty()) {
                        cliente.setPassword(passwordEncoder.encode(clienteDetails.getPassword()));
                    }

                    return clienteRepository.save(cliente);
                })
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }
} 