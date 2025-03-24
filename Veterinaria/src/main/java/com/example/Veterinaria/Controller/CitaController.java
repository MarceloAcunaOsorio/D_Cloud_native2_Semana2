package com.example.Veterinaria.Controller;

import com.example.Veterinaria.Model.Cita;
import com.example.Veterinaria.Repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaController {

    @Autowired
    private CitaRepository citaRepository;

    @GetMapping
    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> getCitaById(@PathVariable Long id) {
        return citaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mascota/{mascotaId}")
    public List<Cita> getCitasByMascotaId(@PathVariable Long mascotaId) {
        return citaRepository.findByMascotaId(mascotaId);
    }

    @GetMapping("/estado/{estadoCita}")
    public List<Cita> getCitasByEstado(@PathVariable String estadoCita) {
        return citaRepository.findByEstadoCita(estadoCita);
    }

    @PostMapping
    public Cita createCita(@RequestBody Cita cita) {
        return citaRepository.save(cita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> updateCita(@PathVariable Long id, @RequestBody Cita citaDetails) {
        return citaRepository.findById(id)
                .map(cita -> {
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
                    return ResponseEntity.ok(citaRepository.save(cita));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCita(@PathVariable Long id) {
        return citaRepository.findById(id)
                .map(cita -> {
                    citaRepository.delete(cita);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 