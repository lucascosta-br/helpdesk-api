package com.lucascostabr.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/autenticacao/**").permitAll()
                        //.requestMatchers("/autenticacao/login").permitAll()
                        //.requestMatchers("/autenticacao/registrar").hasAnyRole("ADMIN", "TECNICO")

                        // ANEXO CONTROLLER
                        .requestMatchers(HttpMethod.POST, "/api/v1/anexos/upload/**").hasAnyRole("ADMIN", "CLIENTE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/anexos/uploadMultiplos/**").hasAnyRole("ADMIN", "CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/api/v1/anexos/downloadFile/**").hasAnyRole("ADMIN", "TECNICO")

                        // CHAMADO CONTROLLER
                        .requestMatchers(HttpMethod.POST, "/api/v1/chamados").hasAnyRole("ADMIN", "CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/api/v1/chamados").hasAnyRole("ADMIN", "CLIENTE", "TECNICO")
                        .requestMatchers(HttpMethod.GET, "/api/v1/chamados/{id}").hasAnyRole("ADMIN", "CLIENTE", "TECNICO")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/chamados/{id}").hasAnyRole("ADMIN", "TECNICO")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/chamados/{id}").hasAnyRole("ADMIN", "TECNICO")

                        // CLIENTE CONTROLLER
                        .requestMatchers(HttpMethod.POST, "/api/v1/clientes").hasAnyRole("ADMIN", "CLIENTE", "TECNICO")
                        .requestMatchers(HttpMethod.POST, "/api/v1/clientes/criarVarios").hasAnyRole("ADMIN", "TECNICO")
                        .requestMatchers(HttpMethod.GET, "/api/v1/clientes/exportarTodos").hasAnyRole("ADMIN", "TECNICO")
                        .requestMatchers(HttpMethod.GET, "/api/v1/clientes").hasAnyRole("ADMIN", "CLIENTE", "TECNICO")
                        .requestMatchers(HttpMethod.GET, "/api/v1/clientes/{id}").hasAnyRole("ADMIN", "CLIENTE", "TECNICO")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/clientes/{id}").hasAnyRole("ADMIN", "TECNICO")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/clientes/{id}").hasAnyRole("ADMIN", "TECNICO")

                        // RELATORIO CHAMADO CONTROLLER
                        .requestMatchers(HttpMethod.GET, "/api/v1/relatorios/chamados").hasAnyRole("ADMIN", "TECNICO")

                        // TECNICO CONTROLLER
                        .requestMatchers(HttpMethod.POST, "/api/v1/tecnicos").hasAnyRole("ADMIN", "TECNICO")
                        .requestMatchers(HttpMethod.POST, "/api/v1/tecnicos/criarVarios").hasAnyRole("ADMIN", "TECNICO")
                        .requestMatchers(HttpMethod.GET, "/api/v1/tecnicos/exportarTodos").hasAnyRole("ADMIN", "TECNICO")
                        .requestMatchers(HttpMethod.GET, "/api/v1/tecnicos").hasAnyRole("ADMIN", "TECNICO")
                        .requestMatchers(HttpMethod.GET, "/api/v1/tecnicos/{id}").hasAnyRole("ADMIN", "TECNICO")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/tecnicos/{id}").hasAnyRole("ADMIN", "TECNICO")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tecnicos/{id}").hasAnyRole("ADMIN", "TECNICO")

                        // Todas as outras requisições precisam de autenticação
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
