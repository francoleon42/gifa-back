package com.gifa_api.testUnitario.service;

import com.gifa_api.dto.GestorOperacionalDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.GestorOperacional;
import com.gifa_api.repository.IGestorOperacionalRepository;
import com.gifa_api.service.impl.GestorOperacionalServiceImpl;
import com.gifa_api.utils.mappers.GestorOperacionalMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class GestorOperacionalServiceImplTest {

    @InjectMocks
    private GestorOperacionalServiceImpl gestorOperacionalService;

    @Mock
    private IGestorOperacionalRepository gestorOperacionalRepository;

    @Mock
    private GestorOperacionalMapper gestorOperacionalMapper;

    private GestorOperacional gestorOperacional;
    private GestorOperacionalDTO gestorOperacionalDTO;

    @BeforeEach
    void setUp() {
        gestorOperacional = new GestorOperacional();
        gestorOperacional.setPresupuesto(10000.0); // Establecer un presupuesto de ejemplo

        gestorOperacionalDTO = new GestorOperacionalDTO();
        gestorOperacionalDTO.setPresupuesto(15000.0); // Establecer un nuevo presupuesto de ejemplo
    }

    @Test
    void getGestorOperacionalInvalido() {
        when(gestorOperacionalRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            gestorOperacionalService.getGestorOperacional();
        });

        assertEquals("No se encontró el gestor de pedido con id: 1", exception.getMessage());
    }

    @Test
    void obtenerGestorOperacional_ShouldReturnGestorOperacionalDTO() {
        when(gestorOperacionalRepository.findById(1)).thenReturn(Optional.of(gestorOperacional));
        when(gestorOperacionalMapper.obtenerGestorOperacionalDTO(gestorOperacional)).thenReturn(gestorOperacionalDTO);

        GestorOperacionalDTO result = gestorOperacionalService.obtenerGestorOperacional();

        assertNotNull(result);
        assertEquals(gestorOperacionalDTO.getPresupuesto(), result.getPresupuesto());
        verify(gestorOperacionalRepository).findById(1);
        verify(gestorOperacionalMapper).obtenerGestorOperacionalDTO(gestorOperacional);
    }

    @Test
    void getGestorOperacional_ShouldReturnGestorOperacional() {
        when(gestorOperacionalRepository.findById(1)).thenReturn(Optional.of(gestorOperacional));

        GestorOperacional result = gestorOperacionalService.getGestorOperacional();

        assertNotNull(result);
        assertEquals(gestorOperacional.getPresupuesto(), result.getPresupuesto());
        verify(gestorOperacionalRepository).findById(1);
    }

    @Test
    void actualizarGestorOperacional_ShouldUpdateGestorOperacional() {
        when(gestorOperacionalRepository.findById(1)).thenReturn(Optional.of(gestorOperacional));

        gestorOperacionalService.actualizarGestorOperacional(gestorOperacionalDTO);

        assertEquals(gestorOperacionalDTO.getPresupuesto(), gestorOperacional.getPresupuesto());
        verify(gestorOperacionalRepository).save(gestorOperacional);
    }
}
