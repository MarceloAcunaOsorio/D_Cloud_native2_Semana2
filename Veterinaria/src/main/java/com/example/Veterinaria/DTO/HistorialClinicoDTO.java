package com.example.Veterinaria.DTO;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class HistorialClinicoDTO {
    private Long id;
    private Long mascotaId;
    private Long veterinarioId;
    private LocalDateTime fechaConsulta;
    private String motivoConsulta;
    private String sintomas;
    private String diagnostico;
    private String tratamiento;
    private Double peso;
    private Double temperatura;
    private String observaciones;
    private LocalDateTime proximaVisita;
    private String examenesRealizados;
    private String resultadosExamenes;
    private String medicamentosRecetados;
    private BigDecimal costoConsulta;
    private String estadoSeguimiento;
    private String vacunasAplicadas;
    private Boolean estado;
} 