package com.gifa_api.testUnitario;

import com.gifa_api.dto.mantenimiento.FinalizarMantenimientoDTO;
import com.gifa_api.dto.mantenimiento.ItemUtilizadoRequestDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.model.ItemUsadoMantenimiento;
import com.gifa_api.model.Mantenimiento;
import com.gifa_api.repository.IMantenimientoRepository;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.repository.ItemUsadoMantenimientoRepository;
import com.gifa_api.service.impl.ItemUsadoMantenimientoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ItemUsadoMantenimientoServiceImplTest {

    @InjectMocks
    private ItemUsadoMantenimientoServiceImpl itemUsadoMantenimientoService;

    @Mock
    private ItemDeInventarioRepository itemDeInventarioRepository;

    @Mock
    private ItemUsadoMantenimientoRepository itemUsadoMantenimientoRepository;

    @Mock
    private IMantenimientoRepository iMantenimientoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAgregaritemUtilizadoEnMantenimiento_mantenimientoNoEncontrado() {
        // Arrange
        Integer idMantenimiento = 1;
        FinalizarMantenimientoDTO finalizarMantenimientoDTO = new FinalizarMantenimientoDTO();
        when(iMantenimientoRepository.findById(idMantenimiento)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            itemUsadoMantenimientoService.agregaritemUtilizadoEnMantenimiento(idMantenimiento, finalizarMantenimientoDTO);
        });

        verify(itemDeInventarioRepository, never()).findById(anyInt());
        verify(itemUsadoMantenimientoRepository, never()).save(any(ItemUsadoMantenimiento.class));
    }

    @Test
    void testAgregaritemUtilizadoEnMantenimiento_itemNoEncontrado() {
        // Arrange
        Integer idMantenimiento = 1;
        FinalizarMantenimientoDTO finalizarMantenimientoDTO = new FinalizarMantenimientoDTO();
        ItemUtilizadoRequestDTO itemUtilizado = new ItemUtilizadoRequestDTO();
        itemUtilizado.setIdItem(1);
        itemUtilizado.setCantidad(2);
        finalizarMantenimientoDTO.setItems(Collections.singletonList(itemUtilizado));

        Mantenimiento mantenimiento = new Mantenimiento();

        when(iMantenimientoRepository.findById(idMantenimiento)).thenReturn(Optional.of(mantenimiento));
        when(itemDeInventarioRepository.findById(itemUtilizado.getIdItem())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            itemUsadoMantenimientoService.agregaritemUtilizadoEnMantenimiento(idMantenimiento, finalizarMantenimientoDTO);
        });

        verify(itemUsadoMantenimientoRepository, never()).save(any(ItemUsadoMantenimiento.class));
    }

    @Test
    void testAgregaritemUtilizadoEnMantenimiento_conMantenimientoYItemExistentes() {
        // Arrange
        Integer idMantenimiento = 1;
        FinalizarMantenimientoDTO finalizarMantenimientoDTO = new FinalizarMantenimientoDTO();
        ItemUtilizadoRequestDTO itemUtilizado = new ItemUtilizadoRequestDTO();
        itemUtilizado.setIdItem(1);
        itemUtilizado.setCantidad(2);
        finalizarMantenimientoDTO.setItems(Collections.singletonList(itemUtilizado));

        Mantenimiento mantenimiento = new Mantenimiento();
        ItemDeInventario itemDeInventario = new ItemDeInventario();

        when(iMantenimientoRepository.findById(idMantenimiento)).thenReturn(Optional.of(mantenimiento));
        when(itemDeInventarioRepository.findById(itemUtilizado.getIdItem())).thenReturn(Optional.of(itemDeInventario));

        // Act
        itemUsadoMantenimientoService.agregaritemUtilizadoEnMantenimiento(idMantenimiento, finalizarMantenimientoDTO);

        // Assert
        verify(itemUsadoMantenimientoRepository, times(1)).save(any(ItemUsadoMantenimiento.class));
    }


}
