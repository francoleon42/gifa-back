package com.gifa_api.client;

import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.dto.traccar.CrearDispositivoResponseDTO;
import com.gifa_api.dto.traccar.ObtenerDispositivoRequestDTO;
import com.gifa_api.dto.traccar.PosicionDispositivoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TraccarClient implements ITraccarCliente {

    private final RestTemplate restTemplate;

    private final String username = "gifaemail@email.com";
    private final String password = "123456";
    private final String baseUrl = "http://54.227.167.207:8082/api";

    @Override
    public CrearDispositivoResponseDTO postCrearDispositivoTraccar(CrearDispositivoRequestDTO request) {
        // Crear la entidad que encapsula los encabezados y el cuerpo
        HttpHeaders headers = getHeaders();
        HttpEntity<CrearDispositivoRequestDTO> entity = new HttpEntity<>(request, headers);

        // Realizar la solicitud POST
        ResponseEntity<CrearDispositivoResponseDTO> response = restTemplate.exchange(
                baseUrl + "/devices",
                HttpMethod.POST,
                entity,
                CrearDispositivoResponseDTO.class
        );


        if (response.getStatusCode() == HttpStatus.CREATED || response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            // Manejar el caso de error si es necesario
            throw new RuntimeException("Error en la creación del dispositivo: " + response.getStatusCode());
        }
    }

    @Override
    public List<PosicionDispositivoDTO> getPosicionDispositivoTraccar(Integer deviceId, String from, String to) {
        HttpHeaders headers = getHeaders();
        HttpEntity<CrearDispositivoRequestDTO> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/positions");
        if (deviceId != null) {
            builder.queryParam("deviceId", deviceId);
        }
        if (from != null && !from.isEmpty()) {
            builder.queryParam("from", from);
        }
        if (to != null && !to.isEmpty()) {
            builder.queryParam("to", to);
        }

        ResponseEntity<PosicionDispositivoDTO[]> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                PosicionDispositivoDTO[].class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(response.getBody());
        } else {
            // Manejar el caso de error si es necesario
            throw new RuntimeException("Error al obtener las posiciones: " + response.getStatusCode());
        }
    }

    @Override
    public List<ObtenerDispositivoRequestDTO> getDispositivos() {
        HttpHeaders headers = getHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/devices");

        ResponseEntity<ObtenerDispositivoRequestDTO[]> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                ObtenerDispositivoRequestDTO[].class
        );


        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {

            return Arrays.asList(response.getBody());
        } else {
            throw new RuntimeException("Error al obtener los dispositivos: " + response.getStatusCode());
        }
    }

    @Override
    public ObtenerDispositivoRequestDTO getDispositivo(Integer deviceId) {
        HttpHeaders headers = getHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/devices");

        if (deviceId != null) {
            builder.queryParam("id", deviceId);
        }

        ResponseEntity<ObtenerDispositivoRequestDTO[]> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                ObtenerDispositivoRequestDTO[].class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            ObtenerDispositivoRequestDTO[] dispositivos = response.getBody();
            return Arrays.stream(dispositivos)
                    .filter(dispositivo -> dispositivo.getId().equals(deviceId)) // Comparar los IDs
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No se encontró el dispositivo con ID: " + deviceId));
        } else {
            throw new RuntimeException("Error al obtener los dispositivos: " + response.getStatusCode());
        }
    }


    private String getBasicAuthHeader() {
        String auth = this.username + ":" + this.password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        return "Basic " + new String(encodedAuth);
    }

    private HttpHeaders getHeaders() {
        String basicAuthHeader = getBasicAuthHeader();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", basicAuthHeader);
        headers.set("Content-Type", "application/json");
        return headers;
    }
}
