package com.example.Veterinaria.Service;

import com.example.Veterinaria.Model.Cita;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones disponibles para la gestión de citas
 * en la veterinaria.
 */
public interface CitaService {

    /**
     * Recupera todas las citas almacenadas en la base de datos
     * @return Lista de todas las citas
     */
    List<Cita> getAllCita();

    /**
     * Busca una cita por su ID
     * @param id Identificador único de la cita
     * @return Optional que contiene la cita si existe
     */
    Optional<Cita> getCitaById(Long id);

    /**
     * Busca todas las citas de una mascota específica
     * @param mascotaId Identificador de la mascota
     * @return Lista de citas asociadas a la mascota
     */
    List<Cita> getCitasMascotaById(Long mascotaId);

    /**
     * Busca todas las citas de un cliente específico
     * @param clienteId Identificador del cliente
     * @return Lista de citas asociadas al cliente
     */
    List<Cita> getCitasClienteById(Long clienteId);

    /**
     * Busca citas programadas dentro de un rango de fechas
     * @param inicio Fecha y hora de inicio del rango
     * @param fin Fecha y hora de fin del rango
     * @return Lista de citas dentro del rango especificado
     */
    List<Cita> getCitaFechaHoraBetweenById(LocalDateTime inicio, LocalDateTime fin);

    /**
     * Busca citas por su estado
     * @param estadoCita Estado de la cita (PENDIENTE, COMPLETADA, CANCELADA, etc.)
     * @return Lista de citas con el estado especificado
     */
    List<Cita> getEstadoCitaById(String estadoCita);

    /**
     * Guarda una nueva cita en la base de datos
     * @param cita Entidad cita a guardar
     * @return Cita guardada con su ID generado
     */
    Cita saveCita(Cita cita);

    /**
     * Elimina una cita por su ID
     * @param id Identificador único de la cita a eliminar
     */
    void deleteCitaById(Long id);

    /**
     * Actualiza los datos de una cita existente
     * @param id Identificador único de la cita a actualizar
     * @param citaDetails Nuevos datos de la cita
     * @return Cita actualizada
     */
    Cita updateCita(Long id, Cita citaDetails);
} 