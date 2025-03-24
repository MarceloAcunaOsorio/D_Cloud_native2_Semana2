package com.example.Veterinaria.Service.Impl;

import com.example.Veterinaria.Model.Inventario;
import com.example.Veterinaria.Repository.InventarioRepository;
import com.example.Veterinaria.Service.InventarioService;
import com.example.Veterinaria.Service.NotificacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz InventarioService que proporciona la lógica de negocio
 * para la gestión del inventario en la veterinaria.
 */
@Service
@Transactional
public class InventarioServiceImpl implements InventarioService {

    private static final Logger logger = LoggerFactory.getLogger(InventarioServiceImpl.class);

 
    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private NotificacionService notificacionService;

    @Override
    public List<Inventario> getAllInventario() {
        try {
            return inventarioRepository.findAll();
        } catch (Exception e) {
            logger.error("Error al obtener todo el inventario: {}", e.getMessage());
            throw new RuntimeException("Error al obtener el inventario", e);
        }
    }


    @Override
    public Optional<Inventario> getInventarioById(Long id) {
        try {
            return inventarioRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error al obtener inventario por ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al obtener el inventario por ID", e);
        }
    }


    @Override
    public Inventario getCodProductoInventarioById(String codigo) {
        try {
            return inventarioRepository.findByCodigo(codigo);
        } catch (Exception e) {
            logger.error("Error al obtener producto por código {}: {}", codigo, e.getMessage());
            throw new RuntimeException("Error al obtener producto por código", e);
        }
    }


    @Override
    public List<Inventario> getCategoriaInventarioById(String categoria) {
        try {
            return inventarioRepository.findByCategoria(categoria);
        } catch (Exception e) {
            logger.error("Error al obtener productos por categoría {}: {}", categoria, e.getMessage());
            throw new RuntimeException("Error al obtener productos por categoría", e);
        }
    }


    @Override
    public List<Inventario> getStockBajo() {
        try {
            return inventarioRepository.findByStockActualLessThanEqual(0);
        } catch (Exception e) {
            logger.error("Error al obtener productos con stock bajo: {}", e.getMessage());
            throw new RuntimeException("Error al obtener productos con stock bajo", e);
        }
    }

 
    @Override
    public Inventario saveInventario(Inventario inventario) {
        try {
            if (inventario.getCodigo() != null && 
                inventarioRepository.findByCodigo(inventario.getCodigo()) != null) {
                throw new RuntimeException("Ya existe un producto con ese código");
            }
            return inventarioRepository.save(inventario);
        } catch (Exception e) {
            logger.error("Error al guardar producto: {}", e.getMessage());
            throw new RuntimeException("Error al guardar el producto", e);
        }
    }


    @Override
    public void deleteInventarioById(Long id) {
        try {
            inventarioRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error al eliminar producto con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al eliminar el producto", e);
        }
    }

    /**
     * Actualiza los datos de un producto existente
     * @param id Identificador único del producto a actualizar
     * @param inventarioDetails Nuevos datos del producto
     * @return Producto actualizado
     * @throws RuntimeException si el producto no existe o si el nuevo código ya está en uso
     */
    @Override
    public Inventario updateInventario(Long id, Inventario inventarioDetails) {
        try {
            return inventarioRepository.findById(id)
                    .map(inventario -> {
                        if (!inventario.getCodigo().equals(inventarioDetails.getCodigo()) && 
                            inventarioRepository.findByCodigo(inventarioDetails.getCodigo()) != null) {
                            throw new RuntimeException("Ya existe un producto con ese código");
                        }
                        
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
                        return inventarioRepository.save(inventario);
                    })
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        } catch (Exception e) {
            logger.error("Error al actualizar producto con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al actualizar el producto", e);
        }
    }

    /**
     * Valida el stock de los productos y retorna aquellos que están por debajo
     * de su stock mínimo establecido
     * @return Lista de productos con stock bajo
     */
    @Override
    public List<Inventario> validarStockBajo() {
        try {
            List<Inventario> productosBajoStock = inventarioRepository.findAll().stream()
                    .filter(producto -> producto.getStockActual() <= producto.getStockMinimo())
                    .toList();

            // Notificar para cada producto con stock bajo
            for (Inventario producto : productosBajoStock) {
                notificacionService.enviarNotificacionBajoStock(
                    producto.getNombre(),
                    producto.getStockActual(),
                    producto.getStockMinimo()
                );
                logger.info("Notificación enviada para producto: {}", producto.getNombre());
            }

            return productosBajoStock;
        } catch (Exception e) {
            logger.error("Error al validar stock bajo: {}", e.getMessage());
            throw new RuntimeException("Error al validar stock bajo", e);
        }
    }

    /**
     * Verifica si un producto específico tiene stock bajo
     * @param producto Producto a verificar
     * @return true si el stock está por debajo del mínimo, false en caso contrario
     */
    private boolean tieneStockBajo(Inventario producto) {
        return producto.getStockActual() <= producto.getStockMinimo();
    }

    /**
     * Actualiza el stock de un producto
     * @param id Identificador único del producto
     * @param cantidad Cantidad a agregar (positivo) o restar (negativo)
     * @return Producto con el stock actualizado
     * @throws RuntimeException si el producto no existe o si la operación resulta en stock negativo
     */
    @Override
    public Inventario actualizarStockInventario(Long id, int cantidad) {
        try {
            return inventarioRepository.findById(id)
                    .map(producto -> {
                        int nuevoStock = producto.getStockActual() + cantidad;
                        if (nuevoStock < 0) {
                            throw new RuntimeException("No hay suficiente stock disponible");
                        }
                        producto.setStockActual(nuevoStock);
                        
                        // Validar si el nuevo stock está por debajo del mínimo
                        if (tieneStockBajo(producto)) {
                            notificacionService.enviarNotificacionBajoStock(
                                producto.getNombre(),
                                producto.getStockActual(),
                                producto.getStockMinimo()
                            );
                            logger.info("ALERTA: Stock bajo para el producto: {}", producto.getNombre());
                        }
                        
                        return inventarioRepository.save(producto);
                    })
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        } catch (Exception e) {
            logger.error("Error al actualizar stock del producto con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al actualizar stock del producto", e);
        }
    }
} 