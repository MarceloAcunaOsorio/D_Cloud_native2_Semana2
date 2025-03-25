package com.example.Veterinaria.Security;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class RegisterRequest {
    private String dni;
    private String nombre;
    private String apellido;
    private String password;
    private String cargo;
    private String especialidad;
    private String telefono;
    private String email;
    private String direccion;
    private Double salario;
    private String horario;
    private String genero;
    private String numeroEmergencia;
    private String contactoEmergencia;

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