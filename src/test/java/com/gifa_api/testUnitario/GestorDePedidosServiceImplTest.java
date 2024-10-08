package com.gifa_api.testUnitario;

import com.gifa_api.dto.proveedoresYPedidos.GestorDePedidosDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.GestorDePedidos;
import com.gifa_api.repository.IGestorDePedidosRepository;
import com.gifa_api.service.impl.GestorDePedidosServiceImpl;
import com.gifa_api.utils.mappers.GestorDePedidosMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GestorDePedidosServiceImplTest {

    @Mock
    private IGestorDePedidosRepository gestorDePedidosRepository;

    @Mock
    private GestorDePedidosMapper gestorDePedidosMapper;

    @InjectMocks
    private GestorDePedidosServiceImpl gestorDePedidosService;

    private GestorDePedidos gestorDePedidos;
    private GestorDePedidosDTO gestorDePedidosDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gestorDePedidos = GestorDePedidos.builder()
                .id(1)
                .cantDePedidoAutomatico(10)
                .presupuesto(5000.0)
                .build();

        gestorDePedidosDTO = GestorDePedidosDTO.builder()
                .cantDePedidoAutomatico(20)
                .presupuesto(7000.0)
                .build();
    }

    @Test
    void actualizarGestorDePedidos_debeLanzarNotFoundExceptionSiNoExisteGestor() {
        // Arrange
        when(gestorDePedidosRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            gestorDePedidosService.actualizarGestorDePedidos(gestorDePedidosDTO);
        });

        assertEquals("No se encontró el gestor de pedido con id: 1", exception.getMessage());
        verify(gestorDePedidosRepository, times(1)).findById(1);
    }

    @Test
    void obtenerGestorDePedidos_debeLanzarNotFoundExceptionSiNoExisteGestor() {
        // Arrange
        when(gestorDePedidosRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            gestorDePedidosService.getGestorDePedidos();
        });

        assertEquals("No se encontró el gestor de pedido con id: 1", exception.getMessage());
        verify(gestorDePedidosRepository, times(1)).findById(1);
    }

    @Test
    void obtenerGestorDePedidos_debeDevolverGestorDePedidosDTO() {
        // Arrange
        when(gestorDePedidosRepository.findById(1)).thenReturn(Optional.of(this.gestorDePedidos));
        when(gestorDePedidosMapper.obtenerGestorDePedidosDTO(this.gestorDePedidos)).thenReturn(this.gestorDePedidosDTO);

        // Act
        GestorDePedidosDTO resultado = gestorDePedidosService.obtenerGestorDePedidos();

        // Assert
        assertNotNull(resultado);
        assertEquals(gestorDePedidosDTO.getCantDePedidoAutomatico(), resultado.getCantDePedidoAutomatico());
        assertEquals(gestorDePedidosDTO.getPresupuesto(), resultado.getPresupuesto());
        verify(gestorDePedidosRepository, times(1)).findById(1);
        verify(gestorDePedidosMapper, times(1)).obtenerGestorDePedidosDTO(gestorDePedidos);
    }

    @Test
    void actualizarGestorDePedidos_debeActualizarCorrectamente() {
        // Arrange
        when(gestorDePedidosRepository.findById(1)).thenReturn(Optional.of(gestorDePedidos));

        // Act
        gestorDePedidosService.actualizarGestorDePedidos(gestorDePedidosDTO);

        // Assert
        assertEquals(gestorDePedidosDTO.getCantDePedidoAutomatico(), gestorDePedidos.getCantDePedidoAutomatico());
        assertEquals(gestorDePedidosDTO.getPresupuesto(), gestorDePedidos.getPresupuesto());
        verify(gestorDePedidosRepository, times(1)).save(gestorDePedidos);
    }
}
