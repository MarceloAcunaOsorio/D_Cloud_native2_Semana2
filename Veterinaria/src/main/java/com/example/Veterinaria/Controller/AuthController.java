package com.example.Veterinaria.Controller;

import com.example.Veterinaria.Model.Cliente;
import com.example.Veterinaria.Model.Empleado;
import com.example.Veterinaria.Security.JwtProvider;
import com.example.Veterinaria.Security.RegisterRequest;
import com.example.Veterinaria.Security.LoginRequest;
import com.example.Veterinaria.Security.JwtResponse;
import com.example.Veterinaria.Service.ClienteService;
import com.example.Veterinaria.Service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), 
                loginRequest.getPassword()
            )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String token = jwtProvider.generateToken(authentication);
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");
        
        return ResponseEntity.ok(new JwtResponse(
            token,
            loginRequest.getUsername(),
            role,
            "Bearer"
        ));
    }

    @PostMapping("/register/cliente")
    public ResponseEntity<?> registerCliente(@RequestBody RegisterRequest registerRequest) {
        try {
            Cliente cliente = new Cliente();
            cliente.setNombre(registerRequest.getNombre());
            cliente.setApellido(registerRequest.getApellido());
            cliente.setDni(registerRequest.getDni());
            cliente.setEmail(registerRequest.getEmail());
            cliente.setPassword(registerRequest.getPassword());
            cliente.setTelefono(registerRequest.getTelefono());
            cliente.setDireccion(registerRequest.getDireccion());
            cliente.setEstado(true);

            Cliente registeredCliente = clienteService.saveCliente(cliente);
            
            // Autenticar automáticamente después del registro
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    registerRequest.getDni(),
                    registerRequest.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);

            return ResponseEntity.ok(new JwtResponse(
                jwt,
                registeredCliente.getDni(),
                "ROLE_CLIENTE",
                "Bearer"
            ));
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body("Error en el registro: " + e.getMessage());
        }
    }

    @PostMapping("/register/empleado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerEmpleado(@RequestBody RegisterRequest registerRequest) {
        try {
            Empleado empleado = new Empleado();
            empleado.setNombre(registerRequest.getNombre());
            empleado.setApellido(registerRequest.getApellido());
            empleado.setDni(registerRequest.getDni());
            empleado.setEmail(registerRequest.getEmail());
            empleado.setPassword(registerRequest.getPassword());
            empleado.setTelefono(registerRequest.getTelefono());
            empleado.setDireccion(registerRequest.getDireccion());
            empleado.setEstado(true);

            Empleado registeredEmpleado = empleadoService.saveEmpleado(empleado);

            return ResponseEntity.ok("Empleado registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body("Error en el registro: " + e.getMessage());
        }
    }
}