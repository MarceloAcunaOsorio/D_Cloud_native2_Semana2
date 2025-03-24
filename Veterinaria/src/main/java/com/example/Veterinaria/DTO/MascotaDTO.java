package com.example.Veterinaria.DTO;

import lombok.Data;

@Data
public class MascotaDTO {
    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private String fechaNacimiento;
    private Double peso;
    private String color;
    private Boolean estado;
    private Long clienteId;
} 