package com.example.Veterinaria.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitaDTO {
    private Long id;
    private Long mascotaId;
    private Long clienteId;
    private LocalDateTime fechaHora;
    private String tipoConsulta;
    private String motivo;
    private String estadoCita;
    private String notas;
    private Integer duracionMinutos;
    private String veterinario;
    private Boolean recordatorioEnviado;
    private LocalDateTime fechaCreacion;
    private Boolean estado;
} 