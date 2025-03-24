package com.example.Veterinaria.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.Veterinaria.Service.Impl.InventarioServiceImpl;

@Component
public class InventarioScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(InventarioScheduler.class);

    @Autowired
    private InventarioServiceImpl inventarioServiceImpl; // Cambiamos a usar el Service en lugar del Controller

    // Ejecutar cada día a las 9:00 AM
    @Scheduled(cron = "0 0 22 * * ?")
    public void verificarStockDiario() {
        try {
            logger.info("Iniciando verificación diaria de stock");
            inventarioServiceImpl.validarStockBajo();
            logger.info("Verificación de stock completada exitosamente");
        } catch (Exception e) {
            logger.error("Error durante la verificación de stock: {}", e.getMessage(), e);
        }
    }
} 