package com.example.Veterinaria.Repository;

import com.example.Veterinaria.Model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Factura findByNumeroFactura(String numeroFactura);
    List<Factura> findByClienteId(Long clienteId);
    List<Factura> findByEstadoPago(String estadoPago);
} 