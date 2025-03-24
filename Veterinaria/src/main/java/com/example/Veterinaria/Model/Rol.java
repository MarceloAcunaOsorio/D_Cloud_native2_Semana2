package com.example.Veterinaria.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;  // ROLE_ADMIN, ROLE_EMPLEADO, ROLE_CLIENTE
} 