package com.example.Veterinaria.Controller;

import com.example.Veterinaria.Model.Empleado;
import com.example.Veterinaria.Security.JwtProvider;
import com.example.Veterinaria.Security.LoginRequest;
import com.example.Veterinaria.Security.JwtResponse;
import com.example.Veterinaria.Security.RegisterRequest;
import com.example.Veterinaria.Service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    // Endpoint para registro de empleados (solo admin)
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerEmpleado(@RequestBody RegisterRequest registerRequest) {
        try {
            // Verificar si ya existe un empleado con el mismo DNI
            if (empleadoService.getEmpleadoByDni(registerRequest.getDni()) != null) {
                return ResponseEntity
                    .badRequest()
                    .body("Error: Ya existe un empleado con ese DNI");
            }

            Empleado empleado = new Empleado();
            empleado.setNombre(registerRequest.getNombre());
            empleado.setApellido(registerRequest.getApellido());
            empleado.setDni(registerRequest.getDni());
            empleado.setEmail(registerRequest.getEmail());
            empleado.setPassword(registerRequest.getPassword());
            empleado.setTelefono(registerRequest.getTelefono());
            empleado.setDireccion(registerRequest.getDireccion());
            empleado.setCargo(registerRequest.getCargo());
            empleado.setEspecialidad(registerRequest.getEspecialidad());
            empleado.setSalario(registerRequest.getSalario());
            empleado.setHorario(registerRequest.getHorario());
            empleado.setEstado(true);

            Empleado registeredEmpleado = empleadoService.saveEmpleado(empleado);
            
            return ResponseEntity.ok("Empleado registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body("Error en el registro: " + e.getMessage());
        }
    }

    // Endpoint para login de empleados
    @PostMapping("/login")
    public ResponseEntity<?> loginEmpleado(@RequestBody LoginRequest loginRequest) {
        try {
            // Verificar si existe el empleado
            Empleado empleado = empleadoService.getEmpleadoByDni(loginRequest.getUsername());
            if (empleado == null) {
                return ResponseEntity
                    .badRequest()
                    .body("Error: Empleado no encontrado");
            }

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(item -> item.getAuthority())
                .orElse("ROLE_EMPLEADO");
            
            return ResponseEntity.ok(new JwtResponse(
                jwt, 
                userDetails.getUsername(), 
                role,
                "Bearer"
            ));
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body("Error en la autenticación: " + e.getMessage());
        }
    }

    // Endpoints existentes
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Empleado> getAllEmpleados() {
        return empleadoService.getAllEmpleados();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable Long id) {
        return empleadoService.getEmpleadoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cargo/{cargo}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Empleado> getEmpleadosByCargo(@PathVariable String cargo) {
        return empleadoService.findByCargo(cargo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable Long id, @RequestBody Empleado empleadoDetails) {
        try {
            Empleado updatedEmpleado = empleadoService.updateEmpleado(id, empleadoDetails);
            return ResponseEntity.ok(updatedEmpleado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEmpleado(@PathVariable Long id) {
        try {
            empleadoService.deleteEmpleadoById(id);
            return ResponseEntity.ok().body("Empleado eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body("Error al eliminar el empleado: " + e.getMessage());
        }
    }
} 