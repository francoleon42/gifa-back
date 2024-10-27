package com.gifa_api.testUnitario.service;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemUsadoMantenimientoServiceImplTest {

    @InjectMocks
    private ItemUsadoMantenimientoServiceImpl itemUsadoMantenimientoService;

    @Mock
    private ItemDeInventarioRepository itemDeInventarioRepository;

    @Mock
    private ItemUsadoMantenimientoRepository itemUsadoMantenimientoRepository;

    @Mock
    private IMantenimientoRepository iMantenimientoRepository;

    @Test
    void testAgregaritemUtilizadoEnMantenimiento_mantenimientoNoEncontrado() {
        Integer idMantenimiento = 1;
        FinalizarMantenimientoDTO finalizarMantenimientoDTO = new FinalizarMantenimientoDTO();
        when(iMantenimientoRepository.findById(idMantenimiento)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> {
            itemUsadoMantenimientoService.agregaritemUtilizadoEnMantenimiento(idMantenimiento, finalizarMantenimientoDTO);
        });

        verify(iMantenimientoRepository,times(1)).findById(idMantenimiento);
        verify(itemDeInventarioRepository, never()).findById(anyInt());
        verify(itemUsadoMantenimientoRepository, never()).save(any(ItemUsadoMantenimiento.class));
    }



    @Test
    void testAgregaritemUtilizadoEnMantenimiento_itemNoEncontrado() {

        Integer idMantenimiento = 1;
        FinalizarMantenimientoDTO finalizarMantenimientoDTO = new FinalizarMantenimientoDTO();
        ItemUtilizadoRequestDTO itemUtilizado = new ItemUtilizadoRequestDTO();
        itemUtilizado.setIdItem(1);
        itemUtilizado.setCantidad(2);
        finalizarMantenimientoDTO.setItems(Collections.singletonList(itemUtilizado));

        Mantenimiento mantenimiento = new Mantenimiento();

        when(iMantenimientoRepository.findById(idMantenimiento)).thenReturn(Optional.of(mantenimiento));
        when(itemDeInventarioRepository.findById(itemUtilizado.getIdItem())).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> {
            itemUsadoMantenimientoService.agregaritemUtilizadoEnMantenimiento(idMantenimiento, finalizarMantenimientoDTO);
        });

        verify(iMantenimientoRepository,times(1)).findById(idMantenimiento);
        verify(itemDeInventarioRepository,times(1)).findById(itemUtilizado.getIdItem());

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
        verify(iMantenimientoRepository,times(1)).findById(idMantenimiento);
        verify(itemDeInventarioRepository,times(1)).findById(itemUtilizado.getIdItem());
        verify(itemUsadoMantenimientoRepository, times(1)).save(any(ItemUsadoMantenimiento.class));
    }
}