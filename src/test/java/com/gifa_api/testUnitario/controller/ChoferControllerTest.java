package com.gifa_api.testUnitario.controller;

import com.gifa_api.controller.GestionDeCombustibleController;
import com.gifa_api.dto.gestionDeCombustilble.CargaCombustibleRequestDTO;
import com.gifa_api.service.ICargaCombustibleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GestionDeCombustibleControllerTest {

    @InjectMocks
    private GestionDeCombustibleController gestionDeCombustibleController;

    @Mock
    private ICargaCombustibleService cargaCombustibleService;

    @Test
    void testRegistrarCombustible() {
        CargaCombustibleRequestDTO cargaCombustibleRequestDTO = new CargaCombustibleRequestDTO();
        // Configura cargaCombustibleRequestDTO según sea necesario

        ResponseEntity<?> response = gestionDeCombustibleController.registrarCombustible(cargaCombustibleRequestDTO);

        verify(cargaCombustibleService, times(1)).cargarCombustible(cargaCombustibleRequestDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
