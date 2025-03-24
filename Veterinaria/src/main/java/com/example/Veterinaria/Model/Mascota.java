package com.example.Veterinaria.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MASCOTAS")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "ESPECIE", nullable = false, length = 30)
    private String especie;

    @Column(name = "RAZA", length = 30)
    private String raza;

    @Column(name = "FECHA_NACIMIENTO")
    private String fechaNacimiento;

    @Column(name = "PESO")
    private Double peso;

    @Column(name = "COLOR", length = 30)
    private String color;

    @Column(name = "ESTADO")
    private Boolean estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENTE_ID", nullable = false)
    private Cliente cliente;
} 