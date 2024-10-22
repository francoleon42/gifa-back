package com.gifa_api.testUnitario.controller;

import com.gifa_api.controller.InventarioController;
import com.gifa_api.dto.item.ItemDeInventarioDTO;
import com.gifa_api.dto.item.ItemDeInventarioRequestDTO;
import com.gifa_api.dto.item.UtilizarItemDeInventarioDTO;
import com.gifa_api.service.IItemDeIventarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventarioControllerTest {

    @InjectMocks
    private InventarioController inventarioController;

    @Mock
    private IItemDeIventarioService itemDeIventarioService;

    @Test
    void testRegistrarItem() {
        ItemDeInventarioRequestDTO itemDeInventarioRequestDTO = new ItemDeInventarioRequestDTO();
        // Configura itemDeInventarioRequestDTO según sea necesario

        ResponseEntity<?> response = inventarioController.registrarItem(itemDeInventarioRequestDTO);

        verify(itemDeIventarioService, times(1)).registrar(itemDeInventarioRequestDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUtilizarItem() {
        Integer itemId = 1;
        UtilizarItemDeInventarioDTO utilizarItemDeInventarioDTO = new UtilizarItemDeInventarioDTO();
        // Configura utilizarItemDeInventarioDTO según sea necesario

        ResponseEntity<?> response = inventarioController.utilizarItem(itemId, utilizarItemDeInventarioDTO);

        verify(itemDeIventarioService, times(1)).utilizarItem(itemId, utilizarItemDeInventarioDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testObtenerAllItems() {
        List<ItemDeInventarioDTO> items = Collections.singletonList(new ItemDeInventarioDTO());
        when(itemDeIventarioService.obtenerAllitems()).thenReturn(items);

        ResponseEntity<List<ItemDeInventarioDTO>> response = inventarioController.obtenerAllItems();

        verify(itemDeIventarioService, times(1)).obtenerAllitems();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(items, response.getBody());
    }
}
