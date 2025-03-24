package com.example.Veterinaria.Service.Impl;

import com.example.Veterinaria.Model.HistorialClinico;
import com.example.Veterinaria.Repository.HistorialClinicoRepository;
import com.example.Veterinaria.Service.HistorialClinicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz HistorialClinicoService que proporciona la lógica de negocio
 * para la gestión de historiales clínicos en la veterinaria.
 */
@Service
@Transactional
public class HistorialClinicoServiceImpl implements HistorialClinicoService {

    /**
     * Repositorio para realizar operaciones CRUD con la entidad HistorialClinico
     */
    @Autowired
    private HistorialClinicoRepository historialRepository;

    /**
     * Recupera todos los historiales clínicos de la base de datos
     * @return Lista con todos los historiales encontrados
     */
    @Override
    public List<HistorialClinico> getAllHistorialClinico() {
        return historialRepository.findAll();
    }

    /**
     * Busca un historial clínico específico por su ID
     * @param id Identificador único del historial
     * @return Optional conteniendo el historial si existe
     */
    @Override
    public Optional<HistorialClinico> getHistorialClinicoById(Long id) {
        return historialRepository.findById(id);
    }

    /**
     * Busca todos los historiales clínicos de una mascota específica
     * @param mascotaId Identificador de la mascota
     * @return Lista de historiales de la mascota
     */
    @Override
    public List<HistorialClinico> getMascotaIdById(Long mascotaId) {
        return historialRepository.findByMascotaId(mascotaId);
    }

    /**
     * Busca todos los historiales clínicos de un veterinario específico
     * @param veterinarioId Identificador del veterinario
     * @return Lista de historiales del veterinario
     */
    @Override
    public List<HistorialClinico> getVeterinarioIdById(Long veterinarioId) {
        return historialRepository.findByVeterinarioId(veterinarioId);
    }

    /**
     * Guarda un nuevo historial clínico en la base de datos
     * @param historial Datos del historial a guardar
     * @return Historial guardado con su ID generado
     */
    @Override
    public HistorialClinico saveHistorialClinico(HistorialClinico historial) {
        // Aquí podrías agregar validaciones adicionales antes de guardar
        return historialRepository.save(historial);
    }

    /**
     * Elimina un historial clínico de la base de datos
     * @param id Identificador único del historial a eliminar
     */
    @Override
    public void deleteHistorialClinicoById(Long id) {
        historialRepository.deleteById(id);
    }

    /**
     * Actualiza los datos de un historial clínico existente
     * @param id Identificador único del historial a actualizar
     * @param historialDetails Nuevos datos del historial
     * @return Historial actualizado
     * @throws RuntimeException si el historial no existe
     */
    @Override
    public HistorialClinico updateHistorialClinico(Long id, HistorialClinico historialDetails) {
        return historialRepository.findById(id)
                .map(historial -> {
                    historial.setMascota(historialDetails.getMascota());
                    historial.setVeterinario(historialDetails.getVeterinario());
                    historial.setFechaConsulta(historialDetails.getFechaConsulta());
                    historial.setMotivoConsulta(historialDetails.getMotivoConsulta());
                    historial.setSintomas(historialDetails.getSintomas());
                    historial.setDiagnostico(historialDetails.getDiagnostico());
                    historial.setTratamiento(historialDetails.getTratamiento());
                    historial.setPeso(historialDetails.getPeso());
                    historial.setTemperatura(historialDetails.getTemperatura());
                    historial.setObservaciones(historialDetails.getObservaciones());
                    historial.setProximaVisita(historialDetails.getProximaVisita());
                    historial.setEstado(historialDetails.getEstado());
                    return historialRepository.save(historial);
                })
                .orElseThrow(() -> new RuntimeException("Historial clínico no encontrado con id: " + id));
    }
} 