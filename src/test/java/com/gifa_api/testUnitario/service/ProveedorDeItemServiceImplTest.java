package com.gifa_api.testUnitario.service;

import com.gifa_api.dto.proveedoresYPedidos.AsociacionProveedorDeITemDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.model.Proveedor;
import com.gifa_api.model.ProveedorDeItem;
import com.gifa_api.repository.IProveedorDeItemRepository;
import com.gifa_api.service.IItemDeIventarioService;
import com.gifa_api.service.IProvedorService;
import com.gifa_api.service.impl.ProveedorDeItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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

    private AsociacionProveedorDeITemDTO asociacion;
    private   ItemDeInventario itemDeInventario;
    private Proveedor proveedor;

    @BeforeEach
    void setUp(){
        asociacion = new AsociacionProveedorDeITemDTO(1,1,3.0);
        itemDeInventario = new ItemDeInventario();
        proveedor = new Proveedor();
    }

    @Test
    void asociarProveedorAItem_noEncuentraItem() {
        when(itemDeIventarioService.obtenerById(asociacion.getIdItem())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> proveedorDeItemService.asociarProveedorAItem(asociacion));

        verify(itemDeIventarioService, times(1)).obtenerById(asociacion.getIdItem());
        verify(provedorService,never()).obtenerByid(asociacion.getIdProveedor());
        verify(proveedorDeItemRepository,never()).save(any(ProveedorDeItem.class));

    }

    @Test
    void asociarProveedorAItem_noEncuentraProveedor() {
        when(itemDeIventarioService.obtenerById(asociacion.getIdItem())).thenReturn(new ItemDeInventario());
        when(provedorService.obtenerByid(asociacion.getIdProveedor())).thenThrow(NotFoundException.class);


        assertThrows(NotFoundException.class, () -> proveedorDeItemService.asociarProveedorAItem(asociacion));

        verify(itemDeIventarioService, times(1)).obtenerById(asociacion.getIdItem());
        verify(provedorService,times(1)).obtenerByid(asociacion.getIdProveedor());
        verify(proveedorDeItemRepository,never()).save(any(ProveedorDeItem.class));

    }

        @Test
    void asociarProveedorAItem_debeGuardarProveedorDeItem() {
        // Arrange
        when(itemDeIventarioService.obtenerById(asociacion.getIdItem())).thenReturn(itemDeInventario);
        when(provedorService.obtenerByid(asociacion.getIdProveedor())).thenReturn(proveedor);

        // Act
        proveedorDeItemService.asociarProveedorAItem(asociacion);

        // Assert
        verify(itemDeIventarioService, times(1)).obtenerById(asociacion.getIdItem());
        verify(provedorService, times(1)).obtenerByid(asociacion.getIdProveedor());
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