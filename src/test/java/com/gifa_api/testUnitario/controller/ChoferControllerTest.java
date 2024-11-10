//package com.gifa_api.testUnitario.controller;
//
//import com.gifa_api.controller.ChoferController;
//import com.gifa_api.dto.chofer.AsignarChoferDTO;
//import com.gifa_api.dto.chofer.ChoferRegistroDTO;
//import com.gifa_api.dto.chofer.ChoferResponseDTO;
//import com.gifa_api.service.IChoferService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class ChoferControllerTest {
//    @Mock
//    private IChoferService choferService;
//
//    @InjectMocks
//    private ChoferController choferController;
//
//    @Test
//    void registrar_devuelveStatusCreated(){
//        doNothing().when(choferService).registro(new ChoferRegistroDTO());
//        assertEquals(HttpStatus.CREATED,choferController.createChofer(new ChoferRegistroDTO()).getStatusCode());
//    }
//
//    @Test
//    void AsignarChofer_devuelveStatusOK(){
//        doNothing().when(choferService).asignarVehiculo(new AsignarChoferDTO());
//        assertEquals(HttpStatus.OK,choferController.AsignarChofer(new AsignarChoferDTO()).getStatusCode());
//    }
//
//    @Test
//    void habilitar_devuelveStatusOK(){
//        doNothing().when(choferService).habilitar(1);
//        assertEquals(HttpStatus.OK,choferController.habilitar(1).getStatusCode());
//    }
//
//    @Test
//    void inhabilitar_devuelveStatusOK(){
//        doNothing().when(choferService).inhabilitar(1);
//        assertEquals(HttpStatus.OK,choferController.createChofer(1).getStatusCode());
//    }
//
//    @Test
//    void getAllChofers_devuelveStatusOK(){
//        ChoferResponseDTO chofer1 = ChoferResponseDTO.builder().idChofer(1).build();
//        ChoferResponseDTO chofer2 = ChoferResponseDTO.builder().idChofer(2).build();
//        when(choferService.obtenerAll()).thenReturn(List.of(chofer1,chofer2));
//        assertEquals(HttpStatus.OK,choferController.getAllChofers().getStatusCode());
//        assertEquals(List.of(chofer1,chofer2),choferController.getAllChofers().getBody());
//    }
//}
