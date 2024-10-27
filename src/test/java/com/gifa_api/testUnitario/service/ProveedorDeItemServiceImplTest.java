package com.gifa_api.testUnitario.service;

import com.gifa_api.dto.proveedor.ProveedorDeITemRequestDTO;
import com.gifa_api.dto.proveedor.ProveedorDeITemResponseDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.model.Proveedor;
import com.gifa_api.model.ProveedorDeItem;
import com.gifa_api.repository.IProveedorDeItemRepository;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IProvedorService;
import com.gifa_api.service.impl.ProveedorDeItemServiceImpl;
import com.gifa_api.utils.mappers.ProveedorDeItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ProveedorDeItemServiceImplTest {

    @Mock
    private ItemDeInventarioRepository itemDeInventarioRepository;
    @Mock
    private IProvedorService provedorService;
    @Mock
    private IProveedorDeItemRepository iProveedorDeItemRepository;
    @Mock
    private ProveedorDeItemMapper proveedorDeItemMapper;

    @InjectMocks
    private ProveedorDeItemServiceImpl proveedorDeItemService;

    private ProveedorDeITemRequestDTO proveedorDeItemRequestDTO;

    @BeforeEach
    void setUp() {
        proveedorDeItemRequestDTO = ProveedorDeITemRequestDTO.builder()
                .idItem(1)
                .idProveedor(2)
                .precio(150.0)
                .build();
    }

    @Test
    void asociarProveedorAItem_ItemNoEncontrado_DeberiaLanzarNotFoundException() {
        when(itemDeInventarioRepository.findById(proveedorDeItemRequestDTO.getIdItem())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> proveedorDeItemService.asociarProveedorAItem(proveedorDeItemRequestDTO));

        verify(itemDeInventarioRepository).findById(proveedorDeItemRequestDTO.getIdItem());
        verify(iProveedorDeItemRepository, never()).save(any(ProveedorDeItem.class));
    }

    @Test
    void asociarProveedorAItem_ProveedorNoEncontrado_DeberiaLanzarNotFoundException() {
        ItemDeInventario item = new ItemDeInventario();
        when(itemDeInventarioRepository.findById(proveedorDeItemRequestDTO.getIdItem())).thenReturn(Optional.of(item));
        when(provedorService.obtenerByid(proveedorDeItemRequestDTO.getIdProveedor())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> proveedorDeItemService.asociarProveedorAItem(proveedorDeItemRequestDTO));

        verify(itemDeInventarioRepository).findById(proveedorDeItemRequestDTO.getIdItem());
        verify(provedorService).obtenerByid(proveedorDeItemRequestDTO.getIdProveedor());
        verify(iProveedorDeItemRepository, never()).save(any(ProveedorDeItem.class));
    }

    @Test
    void precioNoPuedeSerNulo() {
        proveedorDeItemRequestDTO.setPrecio(null);
        verificarNoAsociacionDePrecioInvalido();
    }

    @Test
    void precioDebeSerPositivo() {
        proveedorDeItemRequestDTO.setPrecio(0.0);
        verificarNoAsociacionDePrecioInvalido();
    }

    @Test
    void asociarProveedorAItem_ValidData_GuardarProveedorDeItem() {
        ItemDeInventario item = new ItemDeInventario();
        Proveedor proveedor = new Proveedor();
        when(itemDeInventarioRepository.findById(proveedorDeItemRequestDTO.getIdItem())).thenReturn(Optional.of(item));
        when(provedorService.obtenerByid(proveedorDeItemRequestDTO.getIdProveedor())).thenReturn(proveedor);

        proveedorDeItemService.asociarProveedorAItem(proveedorDeItemRequestDTO);

        verify(itemDeInventarioRepository).findById(proveedorDeItemRequestDTO.getIdItem());
        verify(provedorService).obtenerByid(proveedorDeItemRequestDTO.getIdProveedor());
        verify(iProveedorDeItemRepository).save(any(ProveedorDeItem.class));
    }

    @Test
    void proveedorMasEconomico_ItemExiste_DeberiaRetornarProveedorDeItem() {
        Integer idItem = 1;
        ProveedorDeItem proveedorDeItem = new ProveedorDeItem();
        when(iProveedorDeItemRepository.findProveedorMasEconomicoByItemId(idItem)).thenReturn(proveedorDeItem);

        ProveedorDeItem result = proveedorDeItemService.proveedorMasEconomico(idItem);

        assertEquals(proveedorDeItem, result);
        verify(iProveedorDeItemRepository).findProveedorMasEconomicoByItemId(idItem);
    }

    @Test
    void proveedorMasEconomico_ItemNoExiste_DeberiaRetornarNull() {
        Integer idItem = 1;
        when(iProveedorDeItemRepository.findProveedorMasEconomicoByItemId(idItem)).thenReturn(null);

        ProveedorDeItem result = proveedorDeItemService.proveedorMasEconomico(idItem);

        assertNull(result);
        verify(iProveedorDeItemRepository).findProveedorMasEconomicoByItemId(idItem);
    }

    @Test
    void obtenerAll_DeberiaRetornarListaDeProveedorDeItemResponseDTO() {
        ProveedorDeItem proveedorDeItem = new ProveedorDeItem();
        ProveedorDeITemResponseDTO proveedorDeItemResponseDTO = new ProveedorDeITemResponseDTO();
        when(iProveedorDeItemRepository.findAll()).thenReturn(Collections.singletonList(proveedorDeItem));
        when(proveedorDeItemMapper.mapToProveedorDeItemResponseDTO(anyList())).thenReturn(Collections.singletonList(proveedorDeItemResponseDTO));

        List<ProveedorDeITemResponseDTO> result = proveedorDeItemService.obtenerAll();

        assertEquals(1, result.size());
        assertEquals(proveedorDeItemResponseDTO, result.get(0));
        verify(iProveedorDeItemRepository).findAll();
        verify(proveedorDeItemMapper).mapToProveedorDeItemResponseDTO(anyList());
    }
    private void verificarNoAsociacionDePrecioInvalido(){
        assertThrows(IllegalArgumentException.class, () -> proveedorDeItemService.asociarProveedorAItem(proveedorDeItemRequestDTO));
        verify(iProveedorDeItemRepository,never()).save(any(ProveedorDeItem.class));
    }

}
