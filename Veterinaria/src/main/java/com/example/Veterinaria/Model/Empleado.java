package com.example.Veterinaria.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EMPLEADOS")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DNI", unique = true, nullable = false, length = 8)
    private String dni;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO", nullable = false, length = 50)
    private String apellido;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "CARGO", nullable = false, length = 50)
    private String cargo; // Veterinario, Asistente, Recepcionista, etc.

    @Column(name = "ESPECIALIDAD", length = 100)
    private String especialidad; // Para veterinarios: Cirugía, Dermatología, etc.

    @Column(name = "TELEFONO", length = 15)
    private String telefono;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "DIRECCION", length = 200)
    private String direccion;

    @Column(name = "FECHA_CONTRATACION")
    private LocalDate fechaContratacion;

    @Column(name = "SALARIO", columnDefinition = "DECIMAL(10,2)")
    private Double salario;

    @Column(name = "HORARIO", length = 100)
    private String horario;

    @Column(name = "ESTADO")
    private Boolean estado;

    @Column(name = "FECHA_NACIMIENTO")
    private LocalDate fechaNacimiento;

    @Column(name = "GENERO", length = 1)
    private String genero;

    @Column(name = "NUMERO_EMERGENCIA", length = 15)
    private String numeroEmergencia;

    @Column(name = "CONTACTO_EMERGENCIA", length = 100)
    private String contactoEmergencia;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "empleado_roles",
        joinColumns = @JoinColumn(name = "empleado_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>();

} 