package com.example.Veterinaria.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmpleadoDTO {
    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private String cargo;
    private String especialidad;
    private String telefono;
    private String email;
    private String direccion;
    private LocalDate fechaContratacion;
    private double salario;
    private String horario;
    private Boolean estado;
    private LocalDate fechaNacimiento;
    private String genero;
    private String numeroEmergencia;
    private String contactoEmergencia;
} 