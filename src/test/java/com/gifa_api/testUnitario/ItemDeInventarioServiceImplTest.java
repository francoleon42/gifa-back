package com.gifa_api.testUnitario;

import com.gifa_api.dto.item.ItemDeInventarioDTO;
import com.gifa_api.dto.item.ItemDeInventarioRequestDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.impl.ItemDeInventarioServiceImpl;
import com.gifa_api.utils.mappers.ItemDeInventarioMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemDeInventarioServiceImplTest {

    @InjectMocks
    private ItemDeInventarioServiceImpl itemDeInventarioService;

    @Mock
    private ItemDeInventarioRepository itemDeInventarioRepository;

    @Mock
    private ItemDeInventarioMapper itemDeInventarioMapper;

    @Test
    void testRegistrar() {
        // Arrange
        ItemDeInventarioRequestDTO itemDeInventarioDTO = new ItemDeInventarioRequestDTO("Producto A",5,10, 10);

        ItemDeInventario itemDeInventario = ItemDeInventario
                .builder()
                .nombre(itemDeInventarioDTO.getNombre())
                .umbral(itemDeInventarioDTO.getUmbral())
                .stock(itemDeInventarioDTO.getStock())
                .build();

        when(itemDeInventarioRepository.save(any(ItemDeInventario.class))).thenReturn(itemDeInventario);

        // Act
        itemDeInventarioService.registrar(itemDeInventarioDTO);

        // Assert
        verify(itemDeInventarioRepository, times(1)).save(any(ItemDeInventario.class));
    }

    @Test
    void testUtilizarItemconStockSuficiente() {
        // Arrange
        Integer itemId = 1;
        ItemDeInventario itemDeInventario = ItemDeInventario.builder()
                .id(itemId)
                .nombre("Producto A")
                .umbral(5)
                .stock(10)  // Suficiente stock
                .build();

        when(itemDeInventarioRepository.findById(itemId)).thenReturn(Optional.of(itemDeInventario));

        // Act
        itemDeInventarioService.utilizarItem(itemId);

        // Assert
        assertEquals(9, itemDeInventario.getStock());
        verify(itemDeInventarioRepository, times(1)).save(itemDeInventario);
    }

    @Test
    void testUtilizarItemsinStockSuficiente() {
        // Arrange
        Integer itemId = 1;
        ItemDeInventario itemDeInventario = ItemDeInventario.builder()
                .id(itemId)
                .nombre("Producto A")
                .umbral(5)
                .stock(5)  // Stock justo en el umbral
                .build();

        when(itemDeInventarioRepository.findById(itemId)).thenReturn(Optional.of(itemDeInventario));

        // Act
        itemDeInventarioService.utilizarItem(itemId);

        // Assert
        assertEquals(5, itemDeInventario.getStock()); // el stock no tiene que cambiar
        verify(itemDeInventarioRepository, times(1)).save(itemDeInventario);
    }

    @Test
    void testObtenerById_conIdValido() {
        // Arrange
        Integer itemId = 1;
        ItemDeInventario itemDeInventario = ItemDeInventario.builder()
                .id(itemId)
                .nombre("Producto A")
                .build();

        when(itemDeInventarioRepository.findById(itemId)).thenReturn(Optional.of(itemDeInventario));

        // Act
        ItemDeInventario result = itemDeInventarioService.obtenerById(itemId);

        // Assert
        assertNotNull(result);
        assertEquals("Producto A", result.getNombre());
        verify(itemDeInventarioRepository, times(1)).findById(itemId);
    }

    @Test
    void testObtenerByIdconIdInvalido() {
        // Arrange
        Integer itemId = 1;
        when(itemDeInventarioRepository.findById(itemId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> itemDeInventarioService.obtenerById(itemId));

        verify(itemDeInventarioRepository, times(1)).findById(itemId);
    }

    @Test
    void testObtenerAllitems() {
        // Arrange
        ItemDeInventario item1 = ItemDeInventario.builder().nombre("Producto A").build();
        ItemDeInventario item2 = ItemDeInventario.builder().nombre("Producto B").build();

        List<ItemDeInventario> itemList = Arrays.asList(item1, item2);

        when(itemDeInventarioRepository.findAll()).thenReturn(itemList);
        when(itemDeInventarioMapper.mapToItemDeInventarioDTO(anyList())).thenReturn(
                Arrays.asList(new ItemDeInventarioDTO(), new ItemDeInventarioDTO()));

        // Act
        List<ItemDeInventarioDTO> result = itemDeInventarioService.obtenerAllitems();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(itemDeInventarioRepository, times(1)).findAll();
        verify(itemDeInventarioMapper, times(1)).mapToItemDeInventarioDTO(itemList);
    }
}
