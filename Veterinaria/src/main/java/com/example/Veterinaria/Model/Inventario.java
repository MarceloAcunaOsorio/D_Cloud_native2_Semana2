package com.example.Veterinaria.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "INVENTARIO")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODIGO", unique = true, nullable = false, length = 20)
    private String codigo;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 255)
    private String descripcion;

    @Column(name = "CATEGORIA", nullable = false, length = 50)
    private String categoria; // Medicamento, Insumo clínico, etc.

    @Column(name = "STOCK_ACTUAL", nullable = false)
    private Integer stockActual;

    @Column(name = "STOCK_MINIMO", nullable = false)
    private Integer stockMinimo;

    @Column(name = "UNIDAD_MEDIDA", length = 20)
    private String unidadMedida; // Unidades, ml, mg, etc.

    @Column(name = "PRECIO_COMPRA", precision = 10, scale = 2)
    private BigDecimal precioCompra;

    @Column(name = "PRECIO_VENTA", precision = 10, scale = 2)
    private BigDecimal precioVenta;

    @Column(name = "FECHA_VENCIMIENTO")
    private LocalDate fechaVencimiento;

    @Column(name = "PROVEEDOR", length = 100)
    private String proveedor;

    @Column(name = "UBICACION", length = 50)
    private String ubicacion; // Ubicación en almacén

    @Column(name = "ESTADO")
    private Boolean estado;

    @Column(name = "FECHA_ULTIMA_ACTUALIZACION")
    private LocalDate fechaUltimaActualizacion;
} 