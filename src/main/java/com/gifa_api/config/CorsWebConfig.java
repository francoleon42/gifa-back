package com.gifa_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsWebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Permite todas las rutas
                .allowedOrigins("http://localhost:5173")  // Permitir solicitudes solo desde el puerto 5173
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")  // Métodos HTTP permitidos
                .allowedHeaders("*")  // Permitir todos los encabezados
                .allowCredentials(true);  // Permitir el envío de credenciales
    }
}
