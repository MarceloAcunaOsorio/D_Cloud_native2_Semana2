package com.example.Veterinaria.Controller;

import com.example.Veterinaria.Model.HistorialClinico;
import com.example.Veterinaria.Repository.HistorialClinicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiales")
@CrossOrigin(origins = "*")
public class HistorialClinicoController {

    @Autowired
    private HistorialClinicoRepository historialRepository;

    @GetMapping
    public List<HistorialClinico> getAllHistoriales() {
        return historialRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialClinico> getHistorialById(@PathVariable Long id) {
        return historialRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mascota/{mascotaId}")
    public List<HistorialClinico> getHistorialesByMascotaId(@PathVariable Long mascotaId) {
        return historialRepository.findByMascotaId(mascotaId);
    }

    @GetMapping("/veterinario/{veterinarioId}")
    public List<HistorialClinico> getHistorialesByVeterinarioId(@PathVariable Long veterinarioId) {
        return historialRepository.findByVeterinarioId(veterinarioId);
    }

    @PostMapping
    public HistorialClinico createHistorial(@RequestBody HistorialClinico historial) {
        return historialRepository.save(historial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialClinico> updateHistorial(@PathVariable Long id, @RequestBody HistorialClinico historialDetails) {
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
                    return ResponseEntity.ok(historialRepository.save(historial));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHistorial(@PathVariable Long id) {
        return historialRepository.findById(id)
                .map(historial -> {
                    historialRepository.delete(historial);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 