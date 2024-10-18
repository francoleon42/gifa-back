package com.gifa_api.testUnitario.controller;

import com.gifa_api.controller.VehiculoController;
import com.gifa_api.dto.vehiculo.ListaVehiculosResponseDTO;
import com.gifa_api.dto.vehiculo.RegistarVehiculoDTO;
import com.gifa_api.service.IVehiculoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculoControllerTest {
    @InjectMocks
    private VehiculoController vehiculoController;

    @Mock
    private IVehiculoService vehiculoService;

    @Test
    void verVehiculos_ShouldReturnOk() {
        ListaVehiculosResponseDTO responseDTO = new ListaVehiculosResponseDTO();
        when(vehiculoService.getVehiculos()).thenReturn(responseDTO);

        ResponseEntity<ListaVehiculosResponseDTO> response = vehiculoController.verVehiculos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(vehiculoService).getVehiculos();
    }

    @Test
    void registrar_ShouldReturnCreated() {
        RegistarVehiculoDTO registarVehiculoDTO = new RegistarVehiculoDTO();

        ResponseEntity<?> response = vehiculoController.registrar(registarVehiculoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(vehiculoService).registrar(registarVehiculoDTO);
    }

    @Test
    void inhabilitar_ShouldReturnOk() {
        Integer id = 1;

        ResponseEntity<?> response = vehiculoController.inhabilitar(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(vehiculoService).inhabilitar(id);
    }

    @Test
    void habilitar_ShouldReturnOk() {
        Integer id = 1;

        ResponseEntity<?> response = vehiculoController.habilitar(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(vehiculoService).habilitar(id);
    }
}
