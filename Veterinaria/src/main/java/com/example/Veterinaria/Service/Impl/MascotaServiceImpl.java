package com.example.Veterinaria.Service.Impl;

import com.example.Veterinaria.Model.Mascota;
import com.example.Veterinaria.Repository.MascotaRepository;
import com.example.Veterinaria.Service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz MascotaService que proporciona la lógica de negocio
 * para la gestión de mascotas en la veterinaria.
 */
@Service
@Transactional
public class MascotaServiceImpl implements MascotaService {

    /**
     * Repositorio para realizar operaciones CRUD con la entidad Mascota
     */
    @Autowired
    private MascotaRepository mascotaRepository;

    /**
     * Recupera todas las mascotas de la base de datos
     * @return Lista con todas las mascotas encontradas
     */
    @Override
    public List<Mascota> getAllMascota() {
        return mascotaRepository.findAll();
    }

    /**
     * Busca una mascota específica por su ID
     * @param id Identificador único de la mascota
     * @return Optional conteniendo la mascota si existe
     */
    @Override
    public Optional<Mascota> getMascotaById(Long id) {
        return mascotaRepository.findById(id);
    }

    /**
     * Busca todas las mascotas de un cliente específico
     * @param clienteId Identificador del cliente
     * @return Lista de mascotas del cliente
     */
    @Override
    public List<Mascota> getClienteMascotaIdById(Long clienteId) {
        return mascotaRepository.findByClienteId(clienteId);
    }

    /**
     * Busca mascotas por su especie
     * @param especie Especie de la mascota (PERRO, GATO, etc.)
     * @return Lista de mascotas de la especie especificada
     */
    @Override
    public List<Mascota> getEspecieMascotaById(String especie) {
        return mascotaRepository.findByEspecie(especie);
    }

    /**
     * Guarda una nueva mascota en la base de datos
     * @param mascota Datos de la mascota a guardar
     * @return Mascota guardada con su ID generado
     */
    @Override
    public Mascota saveMascota(Mascota mascota) {
        // Aquí podrías agregar validaciones adicionales antes de guardar
        return mascotaRepository.save(mascota);
    }

    /**
     * Elimina una mascota de la base de datos
     * @param id Identificador único de la mascota a eliminar
     */
    @Override
    public void deleteMascotaById(Long id) {
        mascotaRepository.deleteById(id);
    }

    /**
     * Actualiza los datos de una mascota existente
     * @param id Identificador único de la mascota a actualizar
     * @param mascotaDetails Nuevos datos de la mascota
     * @return Mascota actualizada
     * @throws RuntimeException si la mascota no existe
     */
    @Override
    public Mascota updateMascota(Long id, Mascota mascotaDetails) {
        return mascotaRepository.findById(id)
                .map(mascota -> {
                    mascota.setNombre(mascotaDetails.getNombre());
                    mascota.setEspecie(mascotaDetails.getEspecie());
                    mascota.setRaza(mascotaDetails.getRaza());
                    mascota.setFechaNacimiento(mascotaDetails.getFechaNacimiento());
                    mascota.setPeso(mascotaDetails.getPeso());
                    mascota.setColor(mascotaDetails.getColor());
                    mascota.setCliente(mascotaDetails.getCliente());
                    mascota.setEstado(mascotaDetails.getEstado());
                    return mascotaRepository.save(mascota);
                })
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + id));
    }
} 