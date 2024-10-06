package com.gifa_api.config;

import com.gifa_api.config.jwt.JwtAuthenticationFilter;
import com.gifa_api.utils.enums.Rol;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests(authRequest -> {
                    configurePublicEndpoints(authRequest);
                    configureAdminEndpoints(authRequest);
                    configureSupervisorEndpoints(authRequest);
                    configureGerenteEndpoints(authRequest);
                    configureOperadorEndpoints(authRequest);
                    configureAuthenticatedEndpoints(authRequest);
                })
                .sessionManagement( sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://52.70.76.55"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private void configurePublicEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/ping").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
    }

    private void configureAdminEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        String administrador = Rol.ADMINISTRADOR.toString();
        authRequest
                .requestMatchers(HttpMethod.POST, "/auth/register").hasRole(administrador)
                .requestMatchers(HttpMethod.POST, "/vehiculo/registrar").hasRole(administrador)
                .requestMatchers(HttpMethod.POST, "/pedido/registrarProveedor").hasRole(administrador)
                .requestMatchers(HttpMethod.POST, "/pedido/asociarProveedor").hasRole(administrador)
                .requestMatchers(HttpMethod.POST, "/inventario/registrarItem").hasRole(administrador)
                .requestMatchers(HttpMethod.PATCH, "/vehiculo/habilitar/{id}", "/vehiculo/inhabilitar/{id}").hasRole(administrador)
                .requestMatchers(HttpMethod.PATCH, "/pedido/actualizarGestor").hasRole(administrador)
                .requestMatchers(HttpMethod.GET, "/vehiculo/verAll").hasRole(administrador)
                .requestMatchers(HttpMethod.GET, "/mantenimiento/verAll").hasRole(administrador)
                .requestMatchers(HttpMethod.GET, "/mantenimiento/porVehiculo/{id}").hasRole(administrador);
    }


    private void configureSupervisorEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        String supervisor = Rol.SUPERVISOR.toString();
        authRequest
                .requestMatchers(HttpMethod.POST, "/mantenimiento/crearManual").hasRole(supervisor)
                .requestMatchers(HttpMethod.GET, "/pedido/verAll").hasRole(supervisor);

    }

    private void configureGerenteEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        String gerente = Rol.GERENTE.toString();
    }

    private void configureOperadorEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        String operador = Rol.OPERADOR.toString();
        authRequest
                .requestMatchers(HttpMethod.GET, "/mantenimiento/pendientes").hasRole(operador)
                .requestMatchers(HttpMethod.PATCH, "/mantenimiento/asignar/{mantenimientoId}").hasRole(operador)
                .requestMatchers(HttpMethod.POST, "/mantenimiento/finalizar/{mantenimientoId}").hasRole(operador)
                .requestMatchers(HttpMethod.PATCH, "/inventario/utilizarItem/{id}").hasRole(operador)
                .requestMatchers(HttpMethod.GET, "/inventario/obtenerItems").hasRole(operador)
                .requestMatchers(HttpMethod.GET, "/mantenimiento/verMisMantenimientos").hasRole(operador);

    }

    private void configureAuthenticatedEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/auth/logout").authenticated();
    }
}