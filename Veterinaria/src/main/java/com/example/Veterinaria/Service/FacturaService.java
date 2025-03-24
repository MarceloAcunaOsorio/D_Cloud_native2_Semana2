package com.example.Veterinaria.Service;

import com.example.Veterinaria.Model.Factura;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones disponibles para la gestión de facturas
 * en la veterinaria.
 */
public interface FacturaService {

    List<Factura> getAllFacturas();

    Optional<Factura> getFacturaById(Long id);

    Factura getNumeroFacturaById(String numeroFactura);

    List<Factura> getClienteIdById(Long clienteId);

    List<Factura> getEstadoPagoById(String estadoPago);

    Factura saveFactura(Factura factura);

    void deleteFacturaById(Long id);

    Factura updateFactura(Long id, Factura facturaDetails);
} 