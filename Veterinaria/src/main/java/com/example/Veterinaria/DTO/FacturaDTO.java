package com.example.Veterinaria.DTO;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FacturaDTO {
    private Long id;
    private String numeroFactura;
    private Long clienteId;
    private Long mascotaId;
    private LocalDateTime fechaEmision;
    private LocalDateTime fechaVencimiento;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private String estadoPago;
    private String metodoPago;
    private String descripcion;
    private String numeroOperacion;
    private Long empleadoId;
    private BigDecimal descuento;
    private String observaciones;
    private String tipoComprobante;
    private Boolean estado;
    private LocalDateTime fechaPago;
} 