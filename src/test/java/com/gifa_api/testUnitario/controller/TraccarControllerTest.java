//package com.gifa_api.testUnitario.controller;
//
//import com.gifa_api.controller.TraccarController;
//import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
//import com.gifa_api.dto.traccar.PosicionResponseDTO;
//import com.gifa_api.dto.traccar.VerInconsistenciasRequestDTO;
//import com.gifa_api.service.IDispositivoService;
//import com.gifa_api.service.IPosicionService;
//import com.gifa_api.service.ITraccarService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.time.OffsetDateTime;
//import java.util.List;
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
//    @Mock
//    private IPosicionService posicionService;
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
//        VerInconsistenciasRequestDTO verInconsistenciasRequestDTO =
//                VerInconsistenciasRequestDTO.builder().build();
//        ResponseEntity<?> response = traccarController.verInconsistenciasDeCombustible(verInconsistenciasRequestDTO);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void getPosiciones_devuelvePosicionesYstatusOK(){
//        PosicionResponseDTO posicion1 = PosicionResponseDTO.builder()
//                .id(1)
//                .latitude(1)
//                .longitude(1)
//                .fechaHora(OffsetDateTime.MAX)
//                .build();
//        PosicionResponseDTO posicion2 = PosicionResponseDTO.builder()
//                .id(1)
//                .latitude(1)
//                .longitude(1)
//                .fechaHora(OffsetDateTime.MAX)
//                .build();
//
//        List<PosicionResponseDTO> posiciones = List.of(posicion1,posicion2);
//
//        when(posicionService.getPosicionesDeDispositivo("1")).thenReturn(posiciones);
//        assertEquals(HttpStatus.OK,traccarController.getPosiciones("1").getStatusCode());
//        assertEquals(posiciones,traccarController.getPosiciones("1").getBody());
//    }
//}
