package com.example.Veterinaria.Controller;

import com.example.Veterinaria.Model.Cliente;
import com.example.Veterinaria.Model.Mascota;
import com.example.Veterinaria.Repository.MascotaRepository;
import com.example.Veterinaria.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "*")
public class MascotaController {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

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
    @PreAuthorize("hasRole('ROLE_CLIENTE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createMascota(@RequestBody Mascota mascota) {
        // Validar que el cliente no sea nulo
        if (mascota.getCliente() == null || mascota.getCliente().getId() == null) {
            return ResponseEntity
                .badRequest()
                .body("El cliente es requerido para crear una mascota");
        }
        
        // Verificar que el cliente existe
        Cliente cliente = clienteRepository.findById(mascota.getCliente().getId())
            .orElse(null);
        if (cliente == null) {
            return ResponseEntity
                .badRequest()
                .body("El cliente especificado no existe");
        }
        
        mascota.setCliente(cliente);
        Mascota nuevaMascota = mascotaRepository.save(mascota);
        return ResponseEntity.ok(nuevaMascota);
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