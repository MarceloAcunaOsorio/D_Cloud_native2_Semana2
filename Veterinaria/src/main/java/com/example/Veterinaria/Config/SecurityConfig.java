package com.example.Veterinaria.Config;

import com.example.Veterinaria.Security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas
                .requestMatchers("/error").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                
                // Opciones CORS
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Rutas de Empleados
                .requestMatchers("/api/empleados/register").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/empleados/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/empleados/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/empleados/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/empleados/**").hasAnyRole("ADMIN")

                // Rutas de Clientes
                .requestMatchers(HttpMethod.GET, "/api/clientes/**").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")
                .requestMatchers(HttpMethod.PUT, "/api/clientes/**").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")
                .requestMatchers(HttpMethod.DELETE, "/api/clientes/**").hasRole("ADMIN")

                // Rutas de Mascotas
                .requestMatchers(HttpMethod.GET, "/api/mascotas/**").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")
                .requestMatchers(HttpMethod.POST, "/api/mascotas/**").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")
                .requestMatchers(HttpMethod.PUT, "/api/mascotas/**").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")
                .requestMatchers(HttpMethod.DELETE, "/api/mascotas/**").hasRole("ADMIN")

                // Rutas de Citas
                .requestMatchers(HttpMethod.GET, "/api/citas/**").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")
                .requestMatchers(HttpMethod.POST, "/api/citas/**").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")
                .requestMatchers(HttpMethod.PUT, "/api/citas/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers(HttpMethod.DELETE, "/api/citas/**").hasRole("ADMIN")

                // Rutas de Historiales Clínicos
                .requestMatchers(HttpMethod.GET, "/api/historiales/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers(HttpMethod.POST, "/api/historiales/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers(HttpMethod.PUT, "/api/historiales/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers(HttpMethod.DELETE, "/api/historiales/**").hasRole("ADMIN")

                // Rutas de Inventario
                .requestMatchers(HttpMethod.GET, "/api/inventario/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers(HttpMethod.POST, "/api/inventario/**").hasAnyRole("ADMIN","EMPLEADO")
                .requestMatchers(HttpMethod.PUT, "/api/inventario/**").hasAnyRole("ADMIN","EMPLEADO")
                .requestMatchers(HttpMethod.DELETE, "/api/inventario/**").hasRole("ADMIN")

                // Rutas de Facturas
                .requestMatchers(HttpMethod.GET, "/api/facturas/**").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")
                .requestMatchers(HttpMethod.POST, "/api/facturas/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers(HttpMethod.PUT, "/api/facturas/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers(HttpMethod.DELETE, "/api/facturas/**").hasRole("ADMIN")

                // Resto de endpoints requieren autenticación
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 