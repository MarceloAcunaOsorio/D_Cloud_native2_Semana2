package com.example.Veterinaria.Service;

import com.example.Veterinaria.Model.Mascota;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones disponibles para la gestión de mascotas
 * en la veterinaria.
 */
public interface MascotaService {

 
    List<Mascota> getAllMascota();

    Optional<Mascota> getMascotaById(Long id);

    List<Mascota> getClienteMascotaIdById(Long clienteId);

    List<Mascota> getEspecieMascotaById(String especie);

    Mascota saveMascota(Mascota mascota);

    void deleteMascotaById(Long id);

    Mascota updateMascota(Long id, Mascota mascotaDetails);
} 