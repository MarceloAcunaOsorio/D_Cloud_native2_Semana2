package com.example.Veterinaria.Repository;

import com.example.Veterinaria.Model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Inventario findByCodigo(String codigo);
    List<Inventario> findByCategoria(String categoria);
    List<Inventario> findByStockActualLessThanEqual(Integer stockMinimo);
    List<Inventario> findByStockLessThanEqual(Integer stockMinimo);
    List<Inventario> findByStockLessThan(int cantidad);
} 