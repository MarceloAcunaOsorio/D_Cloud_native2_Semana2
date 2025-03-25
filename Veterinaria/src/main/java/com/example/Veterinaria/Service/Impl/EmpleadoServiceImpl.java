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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz EmpleadoService que proporciona la lógica de negocio
 * para la gestión de empleados en la veterinaria.
 */
@Service
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {


    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Optional<Empleado> getEmpleadoById(Long id) {
        return empleadoRepository.findById(id);
    }

    @Override
    public Empleado getEmpleadoByDni(String dni) {
        return empleadoRepository.findByDni(dni);
    }


    @Override
    public List<Empleado> findByCargo(String cargo) {
        return empleadoRepository.findByCargo(cargo);
    }


    @Override
    public Empleado saveEmpleado(Empleado empleado) {
        // Logs detallados para depuración
        System.out.println("=== Debug Información Empleado ===");
        System.out.println("Empleado completo: " + empleado);
        System.out.println("DNI: " + empleado.getDni());
        System.out.println("Nombre: " + empleado.getNombre());
        System.out.println("Cargo: " + empleado.getCargo());
        System.out.println("==============================");
        
    
        // Validar cargo primero
        if (empleado.getCargo() == null) {
            throw new IllegalArgumentException("El cargo no puede ser nulo");
        }
    
        // Resto de validaciones...
        if (empleado.getDni() != null && empleadoRepository.findByDni(empleado.getDni()) != null) {
            throw new RuntimeException("Ya existe un empleado con ese DNI");
        }
    
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

        // Validar cargo - Mejorada la validación
        if (empleado.getCargo() == null) {
            throw new IllegalArgumentException("El cargo no puede ser nulo");
        }

        String cargo = empleado.getCargo().trim();
        if (cargo.isEmpty()) {
            throw new IllegalArgumentException("El cargo no puede estar vacío");
        }

        // Validar que sea un cargo válido
        if (!isValidCargo(cargo)) {
            throw new IllegalArgumentException("Cargo no válido. Los cargos permitidos son: Veterinario, Asistente, Recepcionista");
        }

        // Normalizar el cargo
        empleado.setCargo(cargo);
        
        // Establecer valores por defecto
        if (empleado.getEstado() == null) {
            empleado.setEstado(true);
        }

        // Guardar el empleado con try-catch mejorado
        try {
            return empleadoRepository.save(empleado);
        } catch (Exception e) {
            System.err.println("Error al guardar empleado: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el empleado: " + e.getMessage());
        }
}

private boolean isValidCargo(String cargo) {
    List<String> cargosValidos = Arrays.asList("Veterinario", "Asistente", "Recepcionista");
    return cargosValidos.contains(cargo);
}
    


    @Override
    public void deleteEmpleadoById(Long id) {
        empleadoRepository.deleteById(id);
    }


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