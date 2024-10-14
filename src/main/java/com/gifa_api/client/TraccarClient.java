package com.gifa_api.client;

import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.dto.traccar.CrearDispositivoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class TraccarClient implements ITraccarCliente {


    private WebClient webClient;
    private final WebClient.Builder webClientBuilder;

    private final String username = "gifaemail@email.com";
    private final String password = "123456";


    private WebClient getWebClient() {
        if (webClient == null) {
            webClient = webClientBuilder.baseUrl("http://54.227.167.207:8082/api").build();
        }
        return webClient;
    }

    @Override
    public Mono<CrearDispositivoResponseDTO> postCrearDispositivoTraccar(CrearDispositivoRequestDTO request) {
        WebClient client = getWebClient();
        String basicAuthHeader = getBasicAuthHeader();

        System.out.println("-----------AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA-----------");
        return webClient.post()
                .uri("/devices") // Endpoint específico de la API
                .header("Authorization", basicAuthHeader) // Añadir encabezado Basic Auth
                .bodyValue(request) // Enviar el cuerpo de la solicitud
                .retrieve() // Obtener la respuesta
                .bodyToMono(CrearDispositivoResponseDTO.class); // Convertir la respuesta a ResponseDTO
    }

    // Método para generar el encabezado de Basic Auth
    private String getBasicAuthHeader() {
        String auth = this.username + ":" + this.password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        return "Basic " + new String(encodedAuth);
    }


}
