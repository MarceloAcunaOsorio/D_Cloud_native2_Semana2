package com.example.Veterinaria.Security;

import com.example.Veterinaria.Model.Cliente;
import com.example.Veterinaria.Model.Empleado;
import com.example.Veterinaria.Repository.ClienteRepository;
import com.example.Veterinaria.Repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
        // Primero buscar en empleados
        Empleado empleado = empleadoRepository.findByDni(dni);
        if (empleado != null) {
            return User.builder()
                .username(empleado.getDni())
                .password(empleado.getPassword())
                .authorities(empleado.getRoles().stream()
                    .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                    .collect(Collectors.toList()))
                .build();
        }

        // Si no es empleado, buscar en clientes
        Cliente cliente = clienteRepository.findByDni(dni);
        if (cliente != null) {
            return User.builder()
                .username(cliente.getDni())
                .password(cliente.getPassword())
                .authorities("ROLE_CLIENTE")
                .build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado con DNI: " + dni);
    }
} 