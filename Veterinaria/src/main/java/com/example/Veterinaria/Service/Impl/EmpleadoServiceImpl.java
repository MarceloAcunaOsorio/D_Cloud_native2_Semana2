package com.example.Veterinaria.Service.Impl;

import com.example.Veterinaria.Model.Empleado;
import com.example.Veterinaria.Model.Rol;
import com.example.Veterinaria.Repository.EmpleadoRepository;
import com.example.Veterinaria.Repository.RolRepository;
import com.example.Veterinaria.Service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz EmpleadoService que proporciona la lógica de negocio
 * para la gestión de empleados en la veterinaria.
 */
@Service
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {

    /**
     * Repositorio para realizar operaciones CRUD con la entidad Empleado
     */
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Recupera todos los empleados de la base de datos
     * @return Lista con todos los empleados encontrados
     */
    @Override
    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    /**
     * Busca un empleado específico por su ID
     * @param id Identificador único del empleado
     * @return Optional conteniendo el empleado si existe
     */
    @Override
    public Optional<Empleado> getEmpleadoById(Long id) {
        return empleadoRepository.findById(id);
    }

    /**
     * Busca un empleado por su número de DNI
     * @param dni Número de documento de identidad del empleado
     * @return Empleado encontrado o null si no existe
     */
    @Override
    public Empleado getEmpleadoByDni(String dni) {
        return empleadoRepository.findByDni(dni);
    }

    /**
     * Busca empleados por su cargo
     * @param cargo Cargo del empleado (VETERINARIO, ASISTENTE, etc.)
     * @return Lista de empleados con el cargo especificado
     */
    @Override
    public List<Empleado> findByCargo(String cargo) {
        return empleadoRepository.findByCargo(cargo);
    }

    /**
     * Guarda un nuevo empleado en la base de datos
     * @param empleado Datos del empleado a guardar
     * @return Empleado guardado con su ID generado
     * @throws RuntimeException si ya existe un empleado con el mismo DNI o si la contraseña es nula
     */
    @Override
    public Empleado saveEmpleado(Empleado empleado) {
        if (empleado.getDni() != null && empleadoRepository.findByDni(empleado.getDni()) != null) {
            throw new RuntimeException("Ya existe un empleado con ese DNI");
        }

        if (empleado.getPassword() == null || empleado.getPassword().trim().isEmpty()) {
            throw new RuntimeException("La contraseña es obligatoria");
        }

        empleado.setPassword(passwordEncoder.encode(empleado.getPassword()));

        // Asignar rol de EMPLEADO si no tiene roles asignados
        if (empleado.getRoles() == null || empleado.getRoles().isEmpty()) {
            Rol rolEmpleado = rolRepository.findByNombre("ROLE_EMPLEADO")
                .orElseThrow(() -> new RuntimeException("Error: Rol de empleado no encontrado"));
            empleado.getRoles().add(rolEmpleado);
        }

        return empleadoRepository.save(empleado);
    }

    /**
     * Elimina un empleado de la base de datos
     * @param id Identificador único del empleado a eliminar
     */
    @Override
    public void deleteEmpleadoById(Long id) {
        empleadoRepository.deleteById(id);
    }

    /**
     * Actualiza los datos de un empleado existente
     * @param id Identificador único del empleado a actualizar
     * @param empleadoDetails Nuevos datos del empleado
     * @return Empleado actualizado
     * @throws RuntimeException si el empleado no existe o si el nuevo DNI ya está en uso
     */
    @Override
    public Empleado updateEmpleado(Long id, Empleado empleadoDetails) {
        return empleadoRepository.findById(id)
                .map(empleado -> {
                    if (!empleado.getDni().equals(empleadoDetails.getDni()) && 
                        empleadoRepository.findByDni(empleadoDetails.getDni()) != null) {
                        throw new RuntimeException("Ya existe un empleado con ese DNI");
                    }

                    empleado.setNombre(empleadoDetails.getNombre());
                    empleado.setApellido(empleadoDetails.getApellido());
                    empleado.setDni(empleadoDetails.getDni());
                    empleado.setEmail(empleadoDetails.getEmail());
                    empleado.setTelefono(empleadoDetails.getTelefono());
                    empleado.setDireccion(empleadoDetails.getDireccion());
                    empleado.setCargo(empleadoDetails.getCargo());
                    empleado.setEspecialidad(empleadoDetails.getEspecialidad());
                    empleado.setSalario(empleadoDetails.getSalario());
                    empleado.setHorario(empleadoDetails.getHorario());
                    empleado.setEstado(empleadoDetails.getEstado());

                    if (empleadoDetails.getPassword() != null && !empleadoDetails.getPassword().trim().isEmpty()) {
                        empleado.setPassword(passwordEncoder.encode(empleadoDetails.getPassword()));
                    }

                    return empleadoRepository.save(empleado);
                })
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
    }
} 