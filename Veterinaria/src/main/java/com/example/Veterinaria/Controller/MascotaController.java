package com.example.Veterinaria.Controller;

import com.example.Veterinaria.Model.Mascota;
import com.example.Veterinaria.Repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "*")
public class MascotaController {

    @Autowired
    private MascotaRepository mascotaRepository;

    @GetMapping
    public List<Mascota> getAllMascotas() {
        return mascotaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getMascotaById(@PathVariable Long id) {
        return mascotaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Mascota> getMascotasByClienteId(@PathVariable Long clienteId) {
        return mascotaRepository.findByClienteId(clienteId);
    }

    @PostMapping
    public Mascota createMascota(@RequestBody Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mascota> updateMascota(@PathVariable Long id, @RequestBody Mascota mascotaDetails) {
        return mascotaRepository.findById(id)
                .map(mascota -> {
                    mascota.setNombre(mascotaDetails.getNombre());
                    mascota.setEspecie(mascotaDetails.getEspecie());
                    mascota.setRaza(mascotaDetails.getRaza());
                    mascota.setFechaNacimiento(mascotaDetails.getFechaNacimiento());
                    mascota.setPeso(mascotaDetails.getPeso());
                    mascota.setColor(mascotaDetails.getColor());
                    mascota.setEstado(mascotaDetails.getEstado());
                    mascota.setCliente(mascotaDetails.getCliente());
                    return ResponseEntity.ok(mascotaRepository.save(mascota));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMascota(@PathVariable Long id) {
        return mascotaRepository.findById(id)
                .map(mascota -> {
                    mascotaRepository.delete(mascota);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 