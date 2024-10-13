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

    private static final String ADMINISTRADOR = Rol.ADMINISTRADOR.toString();
    private static final String SUPERVISOR = Rol.SUPERVISOR.toString();
    private static final String GERENTE = Rol.GERENTE.toString();
    private static final String OPERADOR = Rol.OPERADOR.toString();
    private static final String CHOFER = Rol.CHOFER.toString();

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
                    configureVehiculoEndpoints(authRequest);
                    configureChoferEndpoints(authRequest);
                    configurePedidoEndpoints(authRequest);
                    configureInventarioEndpoints(authRequest);
                    configureMantenimientoEndpoints(authRequest);
                    configureCargaCombustibleEndpoints(authRequest);
                    configureMetabaseEndpoints(authRequest);
                    configureAuthenticatedEndpoints(authRequest);
                })
                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://52.70.76.55", "http://52.70.76.55:80"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization", "Access-Control-Allow-Origin"));

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

    private void configureVehiculoEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/vehiculo/registrar").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/vehiculo/verAll").hasAnyRole(ADMINISTRADOR, SUPERVISOR)
                .requestMatchers(HttpMethod.PATCH, "/vehiculo/habilitar/{id}").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.PATCH, "/vehiculo/inhabilitar/{id}").hasRole(ADMINISTRADOR);
    }

    private void configureChoferEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/chofer/registrar").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.PATCH, "/chofer/asignarChofer").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.PATCH, "/chofer/inhabilitar").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.PATCH, "/chofer/habilitar").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/chofer/verChofers").hasRole(ADMINISTRADOR);
    }

    private void configurePedidoEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/pedido/registrarProveedor").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.POST, "/pedido/asociarProveedor").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/pedido/verAll").hasRole(SUPERVISOR);
    }

    private void configureInventarioEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/inventario/registrarItem").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/inventario/obtenerItems").hasAnyRole(ADMINISTRADOR, SUPERVISOR, OPERADOR)
                .requestMatchers(HttpMethod.PATCH, "/inventario/utilizarItem/{id}").hasRole(OPERADOR);
    }

    private void configureMantenimientoEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/mantenimiento/crearManual").hasRole(SUPERVISOR)
                .requestMatchers(HttpMethod.GET, "/mantenimiento/pendientes").hasRole(OPERADOR)
                .requestMatchers(HttpMethod.POST, "/mantenimiento/asignar/{mantenimientoId}").hasRole(OPERADOR)
                .requestMatchers(HttpMethod.POST, "/mantenimiento/finalizar/{mantenimientoId}").hasRole(OPERADOR)
                .requestMatchers(HttpMethod.GET, "/mantenimiento/finalizados").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/mantenimiento/porVehiculo/{id}").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/mantenimiento/verMisMantenimientos").hasRole(OPERADOR);
    }

    private void configureCargaCombustibleEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/gestionDeCombustible/cargarCombustible").hasRole(CHOFER);

    }

    private void configureMetabaseEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.GET, "/metabase/token").hasRole(GERENTE);

    }

    private void configureAuthenticatedEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/auth/logout").authenticated();
    }
}
