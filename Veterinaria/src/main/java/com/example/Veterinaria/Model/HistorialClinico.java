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
@Table(name = "HISTORIAL_CLINICO")
public class HistorialClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MASCOTA_ID", nullable = false)
    private Mascota mascota;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLEADO_ID", nullable = false)
    private Empleado veterinario;

    @Column(name = "FECHA_CONSULTA", nullable = false)
    private LocalDateTime fechaConsulta;

    @Column(name = "MOTIVO_CONSULTA", nullable = false, length = 255)
    private String motivoConsulta;

    @Column(name = "SINTOMAS", length = 500)
    private String sintomas;

    @Column(name = "DIAGNOSTICO", length = 500)
    private String diagnostico;

    @Column(name = "TRATAMIENTO", length = 500)
    private String tratamiento;

    @Column(name = "PESO")
    private Double peso;

    @Column(name = "TEMPERATURA")
    private Double temperatura;

    @Column(name = "OBSERVACIONES", length = 1000)
    private String observaciones;

    @Column(name = "PROXIMA_VISITA")
    private LocalDateTime proximaVisita;

    @Column(name = "EXAMENES_REALIZADOS", length = 500)
    private String examenesRealizados;

    @Column(name = "RESULTADOS_EXAMENES", length = 1000)
    private String resultadosExamenes;

    @Column(name = "MEDICAMENTOS_RECETADOS", length = 500)
    private String medicamentosRecetados;

    @Column(name = "COSTO_CONSULTA", precision = 10, scale = 2)
    private BigDecimal costoConsulta;

    @Column(name = "ESTADO_SEGUIMIENTO", length = 50)
    private String estadoSeguimiento;

    @Column(name = "VACUNAS_APLICADAS", length = 500)
    private String vacunasAplicadas;

    @Column(name = "ESTADO")
    private Boolean estado;
} 