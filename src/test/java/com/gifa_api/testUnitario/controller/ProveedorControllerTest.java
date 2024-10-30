package com.gifa_api.testUnitario.controller;

import com.gifa_api.controller.ProveedorController;
import com.gifa_api.dto.item.ItemDeInventarioDTO;
import com.gifa_api.dto.proveedor.ProveedorDeITemRequestDTO;
import com.gifa_api.dto.proveedor.ProveedorDeITemResponseDTO;
import com.gifa_api.dto.proveedor.ProveedorResponseDTO;
import com.gifa_api.dto.proveedor.RegistroProveedorRequestDTO;
import com.gifa_api.service.IProvedorService;
import com.gifa_api.service.IProveedorDeItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProveedorControllerTest {
    @Mock
    private IProvedorService provedorService;

    @Mock
    private IProveedorDeItemService proveedorDeItemService;

    @InjectMocks
    private ProveedorController proveedorController;

    @Test
    void registrar_devuelveStatusCreated(){
        doNothing().when(provedorService).registrarProveedor(new RegistroProveedorRequestDTO());
        assertEquals(HttpStatus.CREATED,proveedorController.registrar(new RegistroProveedorRequestDTO()).getStatusCode());
    }

//    @Test
//    void asociarProveedorAItemr_devuelveStatusCreated(){
//        doNothing().when(proveedorDeItemService).asociarProveedorAItem(new ProveedorDeITemRequestDTO());
//        assertEquals(HttpStatus.CREATED,proveedorController.asociarProveedorAItem(new ProveedorDeITemRequestDTO()).getStatusCode());
//    }

    @Test
    void allProveedores_devuelveStatusOkYlosProveedores() {
        List<ProveedorResponseDTO> proveedores = List.of(ProveedorResponseDTO.builder().id(1).email("email1@gmail.com").build()
                ,ProveedorResponseDTO.builder().email("email2@gmail.com").id(2).build(),
                ProveedorResponseDTO.builder().email("email3@gmail.com").id(3).build());

        when(provedorService.obtenerProveedores()).thenReturn(proveedores);

        assertEquals(HttpStatus.OK, proveedorController.allProveedores().getStatusCode());
        assertEquals(proveedores, proveedorController.allProveedores().getBody());
    }

    @Test
    void allProveedoresConItems_devuelveProveedoresConItemyStatusOK(){
        ProveedorDeITemResponseDTO proveedoItem = ProveedorDeITemResponseDTO.
                builder()
                .item(new ItemDeInventarioDTO())
                .precio(3.3)
                .proveedor(new ProveedorResponseDTO())

                .build();
        ProveedorDeITemResponseDTO proveedoItem2= ProveedorDeITemResponseDTO.
                builder()
                .item(new ItemDeInventarioDTO())
                .precio(3.3)
                .proveedor(new ProveedorResponseDTO())

                .build();
       List<ProveedorDeITemResponseDTO> proveedoresItems = List.of(proveedoItem,proveedoItem2);

       when(proveedorDeItemService.obtenerAll()).thenReturn(proveedoresItems);
       assertEquals(HttpStatus.OK,proveedorController.allProveedoresConItems().getStatusCode());
       assertEquals(proveedoresItems,proveedorController.allProveedoresConItems().getBody());
    }
}
