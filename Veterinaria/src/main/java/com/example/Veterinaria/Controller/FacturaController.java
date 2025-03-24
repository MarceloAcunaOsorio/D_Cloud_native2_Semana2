package com.example.Veterinaria.Controller;

import com.example.Veterinaria.Model.Factura;
import com.example.Veterinaria.Repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {

    @Autowired
    private FacturaRepository facturaRepository;

    @GetMapping
    public List<Factura> getAllFacturas() {
        return facturaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> getFacturaById(@PathVariable Long id) {
        return facturaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/numero/{numeroFactura}")
    public ResponseEntity<Factura> getFacturaByNumero(@PathVariable String numeroFactura) {
        Factura factura = facturaRepository.findByNumeroFactura(numeroFactura);
        return factura != null ? ResponseEntity.ok(factura) : ResponseEntity.notFound().build();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Factura> getFacturasByClienteId(@PathVariable Long clienteId) {
        return facturaRepository.findByClienteId(clienteId);
    }

    @GetMapping("/estado/{estadoPago}")
    public List<Factura> getFacturasByEstadoPago(@PathVariable String estadoPago) {
        return facturaRepository.findByEstadoPago(estadoPago);
    }

    @PostMapping
    public Factura createFactura(@RequestBody Factura factura) {
        return facturaRepository.save(factura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Factura> updateFactura(@PathVariable Long id, @RequestBody Factura facturaDetails) {
        return facturaRepository.findById(id)
                .map(factura -> {
                    factura.setNumeroFactura(facturaDetails.getNumeroFactura());
                    factura.setCliente(facturaDetails.getCliente());
                    factura.setMascota(facturaDetails.getMascota());
                    factura.setFechaEmision(facturaDetails.getFechaEmision());
                    factura.setSubtotal(facturaDetails.getSubtotal());
                    factura.setIva(facturaDetails.getIva());
                    factura.setTotal(facturaDetails.getTotal());
                    factura.setEstadoPago(facturaDetails.getEstadoPago());
                    factura.setMetodoPago(facturaDetails.getMetodoPago());
                    factura.setDescripcion(facturaDetails.getDescripcion());
                    factura.setEstado(facturaDetails.getEstado());
                    return ResponseEntity.ok(facturaRepository.save(factura));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFactura(@PathVariable Long id) {
        return facturaRepository.findById(id)
                .map(factura -> {
                    facturaRepository.delete(factura);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 