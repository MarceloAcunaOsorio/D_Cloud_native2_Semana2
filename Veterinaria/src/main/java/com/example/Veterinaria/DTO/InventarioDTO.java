package com.example.Veterinaria.DTO;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InventarioDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String categoria;
    private Integer stockActual;
    private Integer stockMinimo;
    private String unidadMedida;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private LocalDate fechaVencimiento;
    private String proveedor;
    private String ubicacion;
    private Boolean estado;
    private LocalDate fechaUltimaActualizacion;
} 