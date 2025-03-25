package com.example.Veterinaria.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificacionService {

    private static final Logger logger = LoggerFactory.getLogger(NotificacionService.class);

    @Value("${notification.serverless.url}")
    private String serverlessUrl;

    private final RestTemplate restTemplate;

    public NotificacionService() {
        this.restTemplate = new RestTemplate();
    }

    public void enviarNotificacionBajoStock(String producto, int stockActual, int stockMinimo) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> payload = new HashMap<>();
            payload.put("tipo", "BAJO_STOCK");
            payload.put("producto", producto);
            payload.put("stockActual", stockActual);
            payload.put("stockMinimo", stockMinimo);
            payload.put("mensaje", String.format("¡Alerta! El producto %s tiene un stock bajo (Actual: %d, Mínimo: %d)", 
                producto, stockActual, stockMinimo));

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            
            // Enviar y registrar respuesta
            ResponseEntity<String> response = restTemplate.postForEntity(serverlessUrl, request, String.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("✅ Notificación enviada correctamente a las {} | Producto: {}, Stock: {}",
                    LocalDateTime.now(), producto, stockActual);
            } else {
                logger.error("X Error al enviar notificación (Código {}): {}",
                    response.getStatusCode(), response.getBody());
            }
        } catch (Exception e) {
            logger.error("X Error enviando notificación: {}", e.getMessage(), e);
        }
    }
}