package com.gifa_api.testUnitario;

import com.gifa_api.dto.proveedoresYPedidos.AsociacionProveedorDeITemDTO;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.model.Proveedor;
import com.gifa_api.model.ProveedorDeItem;
import com.gifa_api.repository.IProveedorDeItemRepository;
import com.gifa_api.service.IItemDeIventarioService;
import com.gifa_api.service.IProvedorService;
import com.gifa_api.service.impl.ProveedorDeItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ProveedorDeItemServiceImplTest {
    @Mock
    private IItemDeIventarioService itemDeIventarioService;

    @Mock
    private IProvedorService provedorService;

    @Mock
    private IProveedorDeItemRepository proveedorDeItemRepository;

    @InjectMocks
    private ProveedorDeItemServiceImpl proveedorDeItemService;

    @Test
    void asociarProveedorAItem_debeGuardarProveedorDeItem() {
        // Arrange
        AsociacionProveedorDeITemDTO dto = new AsociacionProveedorDeITemDTO(1, 2, 100.0);
        ItemDeInventario itemDeInventario = new ItemDeInventario();
        Proveedor proveedor = new Proveedor();

        when(itemDeIventarioService.obtenerById(dto.getIdItem())).thenReturn(itemDeInventario);
        when(provedorService.obtenerByid(dto.getIdProveedor())).thenReturn(proveedor);

        // Act
        proveedorDeItemService.asociarProveedorAItem(dto);

        // Assert
        verify(itemDeIventarioService, times(1)).obtenerById(dto.getIdItem());
        verify(provedorService, times(1)).obtenerByid(dto.getIdProveedor());
        verify(proveedorDeItemRepository, times(1)).save(any(ProveedorDeItem.class));
    }

    @Test
    void proveedorMasEconomico_debeRetornarProveedorConPrecioMasBajo() {
        Integer idItem = 1;
        ProveedorDeItem proveedorDeItem = new ProveedorDeItem();

        when(proveedorDeItemRepository.findProveedorMasEconomicoByItemId(idItem)).thenReturn(proveedorDeItem);

        ProveedorDeItem result = proveedorDeItemService.proveedorMasEconomico(idItem);

        assertNotNull(result);
        assertEquals(proveedorDeItem, result);
        verify(proveedorDeItemRepository, times(1)).findProveedorMasEconomicoByItemId(idItem);
    }
}

