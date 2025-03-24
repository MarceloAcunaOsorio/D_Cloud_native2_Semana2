package com.example.Veterinaria.Service;

import com.example.Veterinaria.Model.Cliente;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones disponibles para la gestión de clientes
 */
public interface ClienteService {

    /**
     * Recupera todos los clientes almacenados en la base de datos
     * @return Lista de todos los clientes
     */
    List<Cliente> getAllClientes();

    /**
     * Busca un cliente por su ID
     * @param id Identificador único del cliente
     * @return Optional que contiene el cliente si existe
     */
    Optional<Cliente> getClienteById(Long id);

    /**
     * Busca un cliente por su número de DNI
     * @param dni Número de documento de identidad del cliente
     * @return Cliente encontrado o null si no existe
     */
    Cliente getClienteDniById(String dni);

    /**
     * Guarda un nuevo cliente en la base de datos
     * @param cliente Entidad cliente a guardar
     * @return Cliente guardado con su ID generado
     */
    Cliente saveCliente(Cliente cliente);

    /**
     * Elimina un cliente por su ID
     * @param id Identificador único del cliente a eliminar
     */
    void deleteClienteById(Long id);

    /**
     * Actualiza los datos de un cliente existente
     * @param id Identificador único del cliente a actualizar
     * @param clienteDetails Nuevos datos del cliente
     * @return Cliente actualizado
     */
    Cliente updateCliente(Long id, Cliente clienteDetails);
} 