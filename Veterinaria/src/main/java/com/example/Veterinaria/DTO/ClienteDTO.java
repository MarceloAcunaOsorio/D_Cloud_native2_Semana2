package com.example.Veterinaria.DTO;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String direccion;
    private Boolean estado;
} 