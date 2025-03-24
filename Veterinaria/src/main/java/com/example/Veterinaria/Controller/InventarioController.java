package com.example.Veterinaria.Controller;

import com.example.Veterinaria.Model.Inventario;
import com.example.Veterinaria.Repository.InventarioRepository;
import com.example.Veterinaria.Service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "*")
public class InventarioController {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    public List<Inventario> getAllInventario() {
        return inventarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> getInventarioById(@PathVariable Long id) {
        return inventarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoria}")
    public List<Inventario> getInventarioByCategoria(@PathVariable String categoria) {
        return inventarioRepository.findByCategoria(categoria);
    }

    @GetMapping("/bajo-stock")
    public List<Inventario> getInventarioBajoStock() {
        return inventarioRepository.findByStockActualLessThanEqual(0);
    }

    @PostMapping
    public Inventario createInventario(@RequestBody Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventario> updateInventario(@PathVariable Long id, @RequestBody Inventario inventarioDetails) {
        return inventarioRepository.findById(id)
                .map(inventario -> {
                    inventario.setCodigo(inventarioDetails.getCodigo());
                    inventario.setNombre(inventarioDetails.getNombre());
                    inventario.setDescripcion(inventarioDetails.getDescripcion());
                    inventario.setCategoria(inventarioDetails.getCategoria());
                    inventario.setStockActual(inventarioDetails.getStockActual());
                    inventario.setStockMinimo(inventarioDetails.getStockMinimo());
                    inventario.setPrecioCompra(inventarioDetails.getPrecioCompra());
                    inventario.setPrecioVenta(inventarioDetails.getPrecioVenta());
                    inventario.setFechaVencimiento(inventarioDetails.getFechaVencimiento());
                    inventario.setProveedor(inventarioDetails.getProveedor());
                    inventario.setEstado(inventarioDetails.getEstado());
                    
                    // Verificar si el stock está bajo
                    if (inventario.getStockActual() <= inventario.getStockMinimo()) {
                        notificacionService.enviarNotificacionBajoStock(
                            inventario.getNombre(),
                            inventario.getStockActual(),
                            inventario.getStockMinimo()
                        );
                    }
                    
                    return ResponseEntity.ok(inventarioRepository.save(inventario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventario(@PathVariable Long id) {
        return inventarioRepository.findById(id)
                .map(inventario -> {
                    inventarioRepository.delete(inventario);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Nuevo endpoint para verificar todo el inventario bajo
    @GetMapping("/verificar-stock")
    public ResponseEntity<?> verificarStockBajo() {
        List<Inventario> inventarioBajo = inventarioRepository.findByStockActualLessThanEqual(0);
        
        for (Inventario item : inventarioBajo) {
            notificacionService.enviarNotificacionBajoStock(
                item.getNombre(),
                item.getStockActual(),
                item.getStockMinimo()
            );
        }
        
        return ResponseEntity.ok(Map.of(
            "mensaje", "Verificación completada",
            "itemsBajoStock", inventarioBajo.size()
        ));
    }
} 