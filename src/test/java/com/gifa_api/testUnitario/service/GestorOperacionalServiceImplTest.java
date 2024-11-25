package com.gifa_api.testUnitario.service;

import com.gifa_api.dto.gestorOperacional.GestorOperacionalConsumoDeLitrosPorKmRequestDTO;
import com.gifa_api.dto.gestorOperacional.GestorOperacionalPresupuestoRequestDTO;
import com.gifa_api.dto.gestorOperacional.GestorOperacionalResponseDTO;
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

    private GestorOperacionalResponseDTO gestorOperacionalResponseDTO;

    @BeforeEach
    void setUp() {
        gestorOperacional = GestorOperacional.builder()
                .consumoDeLitrosPorKm(1)
                .presupuesto(1000d)
                .build();

        gestorOperacionalResponseDTO = GestorOperacionalResponseDTO.builder()
                .consumoDeLitrosPorKm(1)
                .presupuesto(1000d)
                .build();
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
        when(gestorOperacionalMapper.obtenerGestorOperacionalDTO(gestorOperacional)).thenReturn(gestorOperacionalResponseDTO);

        GestorOperacionalResponseDTO result = gestorOperacionalService.obtenerGestorOperacional();

        assertNotNull(result);
        assertEquals(gestorOperacional.getPresupuesto(), result.getPresupuesto());
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
    void actualizarPresupuesto_SeActualizaElPresupuesto() {
        when(gestorOperacionalRepository.findById(1)).thenReturn(Optional.of(gestorOperacional));
        GestorOperacionalPresupuestoRequestDTO presupuestoNuevo = GestorOperacionalPresupuestoRequestDTO
                .builder()
                .presupuesto(1d)
                .build();
        gestorOperacionalService.actualizarPresupuesto(presupuestoNuevo);

        verify(gestorOperacionalRepository,times(1)).save(any(GestorOperacional.class));
    }

    @Test
    void actualizarLitrosPorKM_SeActualizaElConsumoDeNafta() {
        when(gestorOperacionalRepository.findById(1)).thenReturn(Optional.of(gestorOperacional));
        GestorOperacionalConsumoDeLitrosPorKmRequestDTO consumoNuevo = GestorOperacionalConsumoDeLitrosPorKmRequestDTO
                .builder()
                .consumoDeLitrosPorKm(1)
                .build();
        gestorOperacionalService.actualizarconsumoDeLitrosPorKm(consumoNuevo);

        verify(gestorOperacionalRepository,times(1)).save(any(GestorOperacional.class));
    }
}
