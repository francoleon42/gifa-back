package com.gifa_api.testUnitario.controller;

import com.gifa_api.controller.GestorOperacionalController;
import com.gifa_api.dto.GestorOperacionalDTO;
import com.gifa_api.model.GestorOperacional;
import com.gifa_api.service.IGestorOperacionalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GestorOperacionalControllerTest {
    @Mock
    private IGestorOperacionalService gestorOperacionalService;

    @InjectMocks
    private GestorOperacionalController gestorOperacionalController;

    @Test
    void actualizarGestorDePedidos_devuelveStatusOK(){
       doNothing().when(gestorOperacionalService).actualizarGestorOperacional(new GestorOperacionalDTO());
       assertEquals(HttpStatus.OK,gestorOperacionalController.actualizarGestorDePedidos(new GestorOperacionalDTO()).getStatusCode());

    }

    @Test
    void obtenerGestorDePedidos_devuelveGestorDePedidosYstatusOK(){
        GestorOperacionalDTO gestorOperacional = GestorOperacionalDTO.
                builder()
                .presupuesto(6.66)
                .build();
        
        when(gestorOperacionalService.obtenerGestorOperacional()).thenReturn(gestorOperacional);

        assertEquals(HttpStatus.OK,gestorOperacionalController.obtenerGestorDePedidos().getStatusCode());
        assertEquals(gestorOperacional,gestorOperacionalController.obtenerGestorDePedidos().getBody());
    }

}
