package com.example.Veterinaria.Service;

import com.example.Veterinaria.Model.Empleado;


import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones disponibles para la gestión de empleados
 * en la veterinaria.
 */
public interface EmpleadoService {

    Empleado saveEmpleado(Empleado empleado);
    List<Empleado> getAllEmpleados();
    Optional<Empleado> getEmpleadoById(Long id);
    Empleado getEmpleadoByDni(String dni);
    List<Empleado> findByCargo(String cargo);
    Empleado updateEmpleado(Long id, Empleado empleadoDetails);
    void deleteEmpleadoById(Long id);
} 