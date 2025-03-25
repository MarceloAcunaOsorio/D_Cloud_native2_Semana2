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

    // El primer 0 son los segundos
    // El segundo 0 son los minutos
    // El 23 es la hora en formato 24h
    // Los * * ? significan "cualquier día del mes, cualquier mes, cualquier día de la semana"
    @Scheduled(cron = "0 13 23 * * ?")
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