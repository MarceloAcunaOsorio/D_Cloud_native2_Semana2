package com.example.Veterinaria.Service.Impl;

import com.example.Veterinaria.Model.Factura;
import com.example.Veterinaria.Repository.FacturaRepository;
import com.example.Veterinaria.Service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz FacturaService que proporciona la lógica de negocio
 * para la gestión de facturas en la veterinaria.
 */
@Service
@Transactional
public class FacturaServiceImpl implements FacturaService {

    /**
     * Repositorio para realizar operaciones CRUD con la entidad Factura
     */
    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    public List<Factura> getAllFacturas() {
        return facturaRepository.findAll();
    }

    @Override
    public Optional<Factura> getFacturaById(Long id) {
        return facturaRepository.findById(id);
    }

    @Override
    public Factura getNumeroFacturaById(String numeroFactura) {
        return facturaRepository.findByNumeroFactura(numeroFactura);
    }

    @Override
    public List<Factura> getClienteIdById(Long clienteId) {
        return facturaRepository.findByClienteId(clienteId);
    }

    @Override
    public List<Factura> getEstadoPagoById(String estadoPago) {
        return facturaRepository.findByEstadoPago(estadoPago);
    }

    @Override
    public Factura saveFactura(Factura factura) {
        // Aquí podrías agregar validaciones adicionales antes de guardar
        return facturaRepository.save(factura);
    }

    @Override
    public void deleteFacturaById(Long id) {
        facturaRepository.deleteById(id);
    }

    @Override
    public Factura updateFactura(Long id, Factura facturaDetails) {
        return facturaRepository.findById(id)
                .map(factura -> {
                    factura.setNumeroFactura(facturaDetails.getNumeroFactura());
                    factura.setCliente(facturaDetails.getCliente());
                    factura.setMascota(facturaDetails.getMascota());
                    factura.setFechaEmision(facturaDetails.getFechaEmision());
                    factura.setFechaVencimiento(facturaDetails.getFechaVencimiento());
                    factura.setSubtotal(facturaDetails.getSubtotal());
                    factura.setIva(facturaDetails.getIva());
                    factura.setTotal(facturaDetails.getTotal());
                    factura.setEstadoPago(facturaDetails.getEstadoPago());
                    factura.setMetodoPago(facturaDetails.getMetodoPago());
                    factura.setDescripcion(facturaDetails.getDescripcion());
                    factura.setNumeroOperacion(facturaDetails.getNumeroOperacion());
                    factura.setEmpleado(facturaDetails.getEmpleado());
                    factura.setDescuento(facturaDetails.getDescuento());
                    factura.setObservaciones(facturaDetails.getObservaciones());
                    factura.setEstado(facturaDetails.getEstado());
                    return facturaRepository.save(factura);
                })
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con id: " + id));
    }
} 