package com.example.Veterinaria.Service;

import com.example.Veterinaria.Model.Inventario;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones disponibles para la gestión del inventario
 * en la veterinaria.
 */
public interface InventarioService {
    
    List<Inventario> getAllInventario();
    
    Optional<Inventario> getInventarioById(Long id);
    
    Inventario getCodProductoInventarioById(String codigo);
    
    List<Inventario> getCategoriaInventarioById(String categoria);
    
    List<Inventario> getStockBajo();
    
    Inventario saveInventario(Inventario inventario);
    
    void deleteInventarioById(Long id);
    
    Inventario updateInventario(Long id, Inventario inventarioDetails);
    
    List<Inventario> validarStockBajo();
    
    Inventario actualizarStockInventario(Long id, int cantidad);
} 