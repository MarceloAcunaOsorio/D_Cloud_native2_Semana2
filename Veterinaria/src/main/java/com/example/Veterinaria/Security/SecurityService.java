package com.example.Veterinaria.Security;

import com.example.Veterinaria.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private ClienteService clienteService;

    public boolean isClienteOwner(Long clienteId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        String currentUserDni = authentication.getName();
        return clienteService.getClienteById(clienteId)
                .map(cliente -> cliente.getDni().equals(currentUserDni))
                .orElse(false);
    }
} 