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
                    configureProveedoresEndpoints(authRequest);
                    configureGestorOperacionalEndpoints(authRequest);
                    configureInventarioEndpoints(authRequest);
                    configureMantenimientoEndpoints(authRequest);
                    configureFuncionesTraccarEndpoints(authRequest);
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
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "https://gifa-ten.vercel.app"));
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
                .requestMatchers(HttpMethod.PATCH, "/vehiculo/inhabilitar/{id}").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/vehiculo/historialVehiculo/{patente}").hasRole(OPERADOR);
    }

    private void configureChoferEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/chofer/registrar").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.PATCH, "/chofer/asignarChofer").hasRole(SUPERVISOR)
                .requestMatchers(HttpMethod.GET, "/chofer/verChoferes").hasAnyRole(ADMINISTRADOR, SUPERVISOR)
                .requestMatchers(HttpMethod.GET, "/chofer/verVehiculo").hasAnyRole(CHOFER);

    }

    private void configurePedidoEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/pedido/generarPedido").hasRole(SUPERVISOR)
                .requestMatchers(HttpMethod.GET, "/pedido/aceptados").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/pedido/getRechazadosPendientesPresupuestoInsuficienteSinProveedor").hasRole(SUPERVISOR)
                .requestMatchers(HttpMethod.GET, "/pedido/verAll").hasRole(SUPERVISOR)
                .requestMatchers(HttpMethod.PATCH, "/pedido/confirmarPedidoRecibido/{id}").hasRole(ADMINISTRADOR);

    }

    private void configureProveedoresEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/proveedor/registrar").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.POST, "/proveedor/asociarAitem").hasRole(SUPERVISOR)
                .requestMatchers(HttpMethod.GET, "/proveedor/verAll").hasRole(SUPERVISOR)
                .requestMatchers(HttpMethod.GET, "/proveedor/verProveedoresDeItems").hasRole(SUPERVISOR);

    }

    private void configureGestorOperacionalEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.PATCH, "/gestorOperacional/actualizarPresupuesto").hasRole(SUPERVISOR)
                .requestMatchers(HttpMethod.PATCH, "/gestorOperacional/actualizarConsumoDeLitrosPorKm").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/gestorOperacional/obtener").hasAnyRole(SUPERVISOR,ADMINISTRADOR);
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
                .requestMatchers(HttpMethod.GET, "/mantenimiento/porVehiculo/{id}").hasAnyRole(ADMINISTRADOR, OPERADOR)
                .requestMatchers(HttpMethod.GET, "/mantenimiento/verMisMantenimientos").hasRole(OPERADOR);
    }

    private void configureCargaCombustibleEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/gestionDeCombustible/cargarCombustible").hasRole(CHOFER);

    }

    private void configureFuncionesTraccarEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/traccar/crearDispositivo").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/traccar/getDispositivos").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/traccar/verInconsistenciasDeCombustible").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/traccar/getPosicionesEnVivo/{unicoId}").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/traccar/getPosicionesEnRangoDeFechas/{unicoId}").hasRole(ADMINISTRADOR);


    }

    private void configureMetabaseEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.GET, "/metabase/token").hasRole(GERENTE);

    }

    private void configureAuthenticatedEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/auth/logout").authenticated()
                .requestMatchers(HttpMethod.PUT, "/auth/update/{id}").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.GET, "/auth/getAllUsers").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/auth/habilitar/{id}").hasRole(ADMINISTRADOR)
                .requestMatchers(HttpMethod.PATCH, "/auth/inhabilitar/{id}").hasRole(ADMINISTRADOR);
    }

    private void configureCargarCombustibleEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequest) {
        authRequest
                .requestMatchers(HttpMethod.POST, "/gestionDeCombustible/cargarCombustible").hasRole(CHOFER);
    }
}
