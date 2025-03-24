package com.example.Veterinaria.Repository;

import com.example.Veterinaria.Model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Empleado findByDni(String dni);
    List<Empleado> findByCargo(String cargo);
} 