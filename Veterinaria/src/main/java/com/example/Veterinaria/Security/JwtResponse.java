package com.example.Veterinaria.Security;

public class JwtResponse {
    private String token;
    private String username;
    private String role;
    private String type;

    // Constructor con 4 parámetros
    public JwtResponse(String token, String username, String role, String type) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.type = type;
    }

    // Constructor con 3 parámetros (para mantener compatibilidad)
    public JwtResponse(String token, String username, String role) {
        this(token, username, role, "Bearer");
    }

    // Getters y setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
} 