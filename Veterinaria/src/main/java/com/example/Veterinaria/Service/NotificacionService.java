package com.example.Veterinaria.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificacionService {

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
            restTemplate.postForEntity(serverlessUrl, request, String.class);
        } catch (Exception e) {
            // Log error pero no interrumpir el flujo principal
            System.err.println("Error enviando notificación: " + e.getMessage());
        }
    }
} 