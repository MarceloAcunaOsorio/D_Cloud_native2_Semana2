package com.example.Veterinaria.Service.Impl;

import com.example.Veterinaria.Model.Cita;
import com.example.Veterinaria.Repository.CitaRepository;
import com.example.Veterinaria.Service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz CitaService que proporciona la lógica de negocio
 * para la gestión de citas en la veterinaria.
 */
@Service
@Transactional
public class CitaServiceImpl implements CitaService {

    /**
     * Repositorio para realizar operaciones CRUD con la entidad Cita
     */
    @Autowired
    private CitaRepository citaRepository;

    /**
     * Recupera todas las citas de la base de datos
     * @return Lista con todas las citas encontradas
     */
    @Override
    public List<Cita> getAllCita() {
        return citaRepository.findAll();
    }

    /**
     * Busca una cita específica por su ID
     * @param id Identificador único de la cita
     * @return Optional conteniendo la cita si existe
     */
    @Override
    public Optional<Cita> getCitaById(Long id) {
        return citaRepository.findById(id);
    }

    /**
     * Busca todas las citas de una mascota específica
     * @param mascotaId Identificador de la mascota
     * @return Lista de citas de la mascota
     */
    @Override
    public List<Cita> getCitasMascotaById(Long mascotaId) {
        return citaRepository.findByMascotaId(mascotaId);
    }

    /**
     * Busca todas las citas de un cliente específico
     * @param clienteId Identificador del cliente
     * @return Lista de citas del cliente
     */
    @Override
    public List<Cita> getCitasClienteById(Long clienteId) {
        return citaRepository.findByClienteId(clienteId);
    }

    /**
     * Busca citas en un rango de fechas específico
     * @param inicio Fecha y hora de inicio del rango
     * @param fin Fecha y hora de fin del rango
     * @return Lista de citas dentro del rango especificado
     */
    @Override
    public List<Cita> getCitaFechaHoraBetweenById(LocalDateTime inicio, LocalDateTime fin) {
        return citaRepository.findByFechaHoraBetween(inicio, fin);
    }

    /**
     * Busca citas por su estado
     * @param estadoCita Estado de la cita (PENDIENTE, COMPLETADA, CANCELADA, etc.)
     * @return Lista de citas con el estado especificado
     */
    @Override
    public List<Cita> getEstadoCitaById(String estadoCita) {
        return citaRepository.findByEstadoCita(estadoCita);
    }

    /**
     * Guarda una nueva cita en la base de datos
     * @param cita Datos de la cita a guardar
     * @return Cita guardada con su ID generado
     * @throws RuntimeException si hay conflicto de horarios
     */
    @Override
    public Cita saveCita(Cita cita) {
        // Validar que no haya otra cita en el mismo horario
        List<Cita> citasExistentes = citaRepository.findByFechaHoraBetween(
            cita.getFechaHora(),
            cita.getFechaHora().plusMinutes(cita.getDuracionMinutos())
        );
        
        if (!citasExistentes.isEmpty()) {
            throw new RuntimeException("Ya existe una cita programada para ese horario");
        }
        
        return citaRepository.save(cita);
    }

    /**
     * Elimina una cita de la base de datos
     * @param id Identificador único de la cita a eliminar
     */
    @Override
    public void deleteCitaById(Long id) {
        citaRepository.deleteById(id);
    }

    /**
     * Actualiza los datos de una cita existente
     * @param id Identificador único de la cita a actualizar
     * @param citaDetails Nuevos datos de la cita
     * @return Cita actualizada
     * @throws RuntimeException si la cita no existe o hay conflicto de horarios
     */
    @Override
    public Cita updateCita(Long id, Cita citaDetails) {
        return citaRepository.findById(id)
                .map(cita -> {
                    // Validar conflicto de horarios solo si cambia la fecha/hora
                    if (!cita.getFechaHora().equals(citaDetails.getFechaHora())) {
                        List<Cita> citasExistentes = citaRepository.findByFechaHoraBetween(
                            citaDetails.getFechaHora(),
                            citaDetails.getFechaHora().plusMinutes(citaDetails.getDuracionMinutos())
                        );
                        if (!citasExistentes.isEmpty()) {
                            throw new RuntimeException("Ya existe una cita programada para ese horario");
                        }
                    }
                    
                    cita.setMascota(citaDetails.getMascota());
                    cita.setCliente(citaDetails.getCliente());
                    cita.setFechaHora(citaDetails.getFechaHora());
                    cita.setTipoConsulta(citaDetails.getTipoConsulta());
                    cita.setMotivo(citaDetails.getMotivo());
                    cita.setEstadoCita(citaDetails.getEstadoCita());
                    cita.setNotas(citaDetails.getNotas());
                    cita.setDuracionMinutos(citaDetails.getDuracionMinutos());
                    cita.setVeterinario(citaDetails.getVeterinario());
                    cita.setRecordatorioEnviado(citaDetails.getRecordatorioEnviado());
                    cita.setEstado(citaDetails.getEstado());
                    return citaRepository.save(cita);
                })
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
    }
} 