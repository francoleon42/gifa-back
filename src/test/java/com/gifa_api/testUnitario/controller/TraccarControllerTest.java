//package com.gifa_api.testUnitario.controller;
//
//import com.gifa_api.controller.TraccarController;
//import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
//import com.gifa_api.service.IDispositivoService;
//import com.gifa_api.service.ITraccarService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class TraccarControllerTest {
//    @InjectMocks
//    private TraccarController traccarController;
//
//    @Mock
//    private ITraccarService traccarService;
//
//    @Mock
//    private IDispositivoService dispositivoService;
//
//    @Test
//    void crearDispositivo_ShouldReturnCreated() {
//        CrearDispositivoRequestDTO requestDTO = new CrearDispositivoRequestDTO();
//        Integer idVehiculo = 1;
//
//        ResponseEntity<?> response = traccarController.crearDispositivo(requestDTO, idVehiculo);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        verify(traccarService).crearDispositivo(requestDTO);
//        verify(dispositivoService).crearDispositivo(requestDTO, idVehiculo);
//    }
//
//    @Test
//    void getDispositivos_ShouldReturnOk() {
//        ResponseEntity<?> response = traccarController.getDispositivos();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(traccarService).obtenerDispositivos();
//    }
//
//    @Test
//    void verInconsistenciasDeCombustible_ShouldReturnOk() {
//        ResponseEntity<?> response = traccarController.verInconsistenciasDeCombustible();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//}
