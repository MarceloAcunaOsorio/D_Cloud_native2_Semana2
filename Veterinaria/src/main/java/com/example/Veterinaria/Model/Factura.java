package com.example.Veterinaria.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FACTURAS")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NUMERO_FACTURA", unique = true, nullable = false, length = 20)
    private String numeroFactura;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENTE_ID", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MASCOTA_ID", nullable = false)
    private Mascota mascota;

    @Column(name = "FECHA_EMISION", nullable = false)
    private LocalDateTime fechaEmision;

    @Column(name = "FECHA_VENCIMIENTO")
    private LocalDateTime fechaVencimiento;

    @Column(name = "SUBTOTAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "IVA", nullable = false, precision = 10, scale = 2)
    private BigDecimal iva;

    @Column(name = "TOTAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "ESTADO_PAGO", nullable = false, length = 20)
    private String estadoPago; // PENDIENTE, PAGADO, ANULADO

    @Column(name = "METODO_PAGO", length = 50)
    private String metodoPago; // EFECTIVO, TARJETA, TRANSFERENCIA

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

    @Column(name = "NUMERO_OPERACION", length = 50)
    private String numeroOperacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLEADO_ID")
    private Empleado empleado; // Empleado que generó la factura

    @Column(name = "DESCUENTO", precision = 10, scale = 2)
    private BigDecimal descuento;

    @Column(name = "OBSERVACIONES", length = 255)
    private String observaciones;

    @Column(name = "TIPO_COMPROBANTE", length = 20)
    private String tipoComprobante; // FACTURA, BOLETA

    @Column(name = "ESTADO")
    private Boolean estado;

    @Column(name = "FECHA_PAGO")
    private LocalDateTime fechaPago;
} 