package com.example.Veterinaria.Service;

import com.example.Veterinaria.Model.HistorialClinico;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones disponibles para la gestión de historiales clínicos
 * en la veterinaria.
 */
public interface HistorialClinicoService {


    List<HistorialClinico> getAllHistorialClinico();

    Optional<HistorialClinico> getHistorialClinicoById(Long id);

    List<HistorialClinico> getMascotaIdById(Long mascotaId);

    List<HistorialClinico> getVeterinarioIdById(Long veterinarioId);

    HistorialClinico saveHistorialClinico(HistorialClinico historial);

    void deleteHistorialClinicoById(Long id);
    
    HistorialClinico updateHistorialClinico(Long id, HistorialClinico historialDetails);
}