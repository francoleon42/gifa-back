//package com.gifa_api.testUnitario.controller;
//
//import com.gifa_api.controller.TraccarController;
//import com.gifa_api.dto.traccar.PosicionResponseDTO;
//import com.gifa_api.dto.traccar.VerInconsistenciasRequestDTO;
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
//import java.time.LocalDate;
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
//        ResponseEntity<?> response = traccarController.verInconsistenciasDeCombustible(LocalDate.now(),LocalDate.now().plusDays(1));
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//
//    @Test
//    void testGetPosicionesEnVivo() {
//        // Mock inputs
//        String unicoId = "12345";
//
//        // Mock outputs
//        List<PosicionResponseDTO> mockResponse = List.of(
//                PosicionResponseDTO.builder()
//                        .id(1)
//                        .latitude(-34.603722)
//                        .longitude(-58.381592)
//                        .fechaHora(LocalDate.of(2024, 11, 7))
//                        .build(),
//                PosicionResponseDTO.builder()
//                        .id(2)
//                        .latitude(-34.604722)
//                        .longitude(-58.382592)
//                        .fechaHora(LocalDate.of(2024, 11, 7))
//                        .build()
//        );
//        when(traccarService.obtenerPosicionesEnVivo(unicoId)).thenReturn(mockResponse);
//
//        // Call the method
//        ResponseEntity<?> response = traccarController.getPosicionesEnVivo(unicoId);
//
//        // Assertions
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(mockResponse, response.getBody());
//
//        // Verify service interaction
//        verify(traccarService, times(1)).obtenerPosicionesEnVivo(unicoId);
//    }
//
//    @Test
//    void testObtenerPosicionesEnRangoDeFechas() {
//        // Mock inputs
//        String unicoId = "12345";
//        LocalDate from = LocalDate.of(2024, 11, 1);
//        LocalDate to = LocalDate.of(2024, 11, 7);
//
//        // Mock outputs
//        List<PosicionResponseDTO> mockResponse = List.of(
//                PosicionResponseDTO.builder()
//                        .id(1)
//                        .latitude(-34.603722)
//                        .longitude(-58.381592)
//                        .fechaHora(LocalDate.of(2024, 11, 2))
//                        .build(),
//                PosicionResponseDTO.builder()
//                        .id(2)
//                        .latitude(-34.604722)
//                        .longitude(-58.382592)
//                        .fechaHora(LocalDate.of(2024, 11, 5))
//                        .build(),
//                PosicionResponseDTO.builder()
//                        .id(3)
//                        .latitude(-34.605722)
//                        .longitude(-58.383592)
//                        .fechaHora(LocalDate.of(2024, 11, 7))
//                        .build()
//        );
//        when(traccarService.obtenerPosicionesEnRangoDeFechas(unicoId, from, to)).thenReturn(mockResponse);
//
//        ResponseEntity<?> response = traccarController.obtenerPosicionesEnRangoDeFechas(unicoId, from, to);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(mockResponse, response.getBody());
//
//        verify(traccarService, times(1)).obtenerPosicionesEnRangoDeFechas(unicoId, from, to);
//    }
//}
