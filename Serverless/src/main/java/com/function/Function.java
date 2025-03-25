package com.function;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

public class Function {

    private static final Gson gson = new Gson();

    @FunctionName("StockNotificationProcessor")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        context.getLogger().info("Inicio de procesamiento - " + LocalDateTime.now());

        try {
            // 1. Validar Body
            String requestBody = request.getBody().orElse("");
            if (requestBody.isEmpty()) {
                context.getLogger().warning("Body vacío");
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"Se requiere un payload JSON\"}")
                    .build();
            }

            // 2. Parsear y Validar Campos Obligatorios
            Map<String, Object> notification;
            try {
                notification = gson.fromJson(requestBody, Map.class);
            } catch (JsonSyntaxException e) {
                context.getLogger().severe("JSON inválido: " + e.getMessage());
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"Formato JSON inválido\"}")
                    .build();
            }

            // Campos requeridos
            String[] requiredFields = {"tipo", "producto", "stockActual", "stockMinimo", "mensaje"};
            for (String field : requiredFields) {
                if (!notification.containsKey(field)) {
                    context.getLogger().warning("Campo faltante: " + field);
                    return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"Campo '" + field + "' es requerido\"}")
                        .build();
                }
            }

            // 3. Log Detallado (Opcional: enviar a Application Insights)
            context.getLogger().info("Payload recibido:");
            notification.forEach((key, value) -> 
                context.getLogger().info(key + " = " + value)
            );

            // 4. Procesamiento (Ejemplo: Enviar a Slack/DB)
            boolean processedSuccessfully = processNotification(notification, context);
            
            if (!processedSuccessfully) {
                throw new RuntimeException("Error simulado en procesamiento");
            }

            // 5. Respuesta Exitosa
            return request.createResponseBuilder(HttpStatus.OK)
                .body("{\"status\": \"success\", \"message\": \"Notificación procesada\"}")
                .build();

        } catch (Exception e) {
            context.getLogger().severe("Error crítico: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\": \"" + e.getMessage() + "\"}")
                .build();
        }
    }

    private boolean processNotification(Map<String, Object> notification, ExecutionContext context) {
        // Ejemplo: Enviar a Slack o guardar en DB
        context.getLogger().info("Enviando notificación a sistema externo...");
        
        // Simulación de éxito/fallo (reemplazar con lógica real)
        boolean mockSuccess = true; 
        
        if (mockSuccess) {
            context.getLogger().info("Procesamiento exitoso");
            return true;
        } else {
            context.getLogger().warning("Fallo en procesamiento externo");
            return false;
        }
    }
}
