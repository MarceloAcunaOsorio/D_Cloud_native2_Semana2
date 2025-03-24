package com.example.Veterinaria.Security;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String password;
    private String telefono;
    private String direccion;
    private String cargo;        // Para empleados
    private String especialidad; // Para empleados
    private Double salario;      // Para empleados
    private String horario;      // Para empleados

    // Constructor vacío
    public RegisterRequest() {
    }

    // Constructor para clientes
    public RegisterRequest(String nombre, String apellido, String dni, String email, 
                         String password, String telefono, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Constructor completo para empleados
    public RegisterRequest(String nombre, String apellido, String dni, String email, 
                         String password, String telefono, String direccion, 
                         String cargo, String especialidad, Double salario, String horario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.direccion = direccion;
        this.cargo = cargo;
        this.especialidad = especialidad;
        this.salario = salario;
        this.horario = horario;
    }

    // Getters y setters son generados por Lombok con la anotación @Data
} 