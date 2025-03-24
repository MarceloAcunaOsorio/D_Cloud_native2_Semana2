package com.example.Veterinaria.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CITAS")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MASCOTA_ID", nullable = false)
    private Mascota mascota;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENTE_ID", nullable = false)
    private Cliente cliente;

    @Column(name = "FECHA_HORA", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "TIPO_CONSULTA", nullable = false, length = 50)
    private String tipoConsulta; // Consulta general, vacunación, emergencia, etc.

    @Column(name = "MOTIVO", length = 255)
    private String motivo;

    @Column(name = "ESTADO_CITA", nullable = false, length = 20)
    private String estadoCita; // Programada, Completada, Cancelada, En espera

    @Column(name = "NOTAS", length = 500)
    private String notas;

    @Column(name = "DURACION_MINUTOS")
    private Integer duracionMinutos;

    @Column(name = "VETERINARIO", length = 100)
    private String veterinario;

    @Column(name = "RECORDATORIO_ENVIADO")
    private Boolean recordatorioEnviado;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "ESTADO")
    private Boolean estado;
} 