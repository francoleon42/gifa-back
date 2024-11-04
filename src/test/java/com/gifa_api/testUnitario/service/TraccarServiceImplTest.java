//package com.gifa_api.testUnitario.service;
//
//import com.gifa_api.client.TraccarClient;
//import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
//import com.gifa_api.dto.traccar.DispositivoResponseDTO;
//import com.gifa_api.service.impl.TraccarServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class TraccarServiceImplTest {
//    @Mock
//    private TraccarClient traccarCliente;
//
//    @InjectMocks
//    private TraccarServiceImpl serviceTraccar;
//
//    @Test
//    void crearDispositivo(){
//        CrearDispositivoRequestDTO dispositivo = new CrearDispositivoRequestDTO();
//        serviceTraccar.crearDispositivo(dispositivo);
//
//        verify(traccarCliente,times(1)).postCrearDispositivoTraccar(dispositivo);
//    }
//
//    @Test
//    void obtenerDispositivos(){
//        DispositivoResponseDTO dispositivo1 = DispositivoResponseDTO.builder().id(1).name("a").status("a").uniqueId("1").build();
//        DispositivoResponseDTO dispositivo2 = DispositivoResponseDTO.builder().id(2).name("a").status("a").uniqueId("1").build();
//        DispositivoResponseDTO dispositivo3 = DispositivoResponseDTO.builder().id(3).name("a").status("a").uniqueId("1").build();
//
//        List<DispositivoResponseDTO> dispositivos = Arrays.asList(dispositivo1, dispositivo2, dispositivo3);
//
//        when(traccarCliente.getDispositivos()).thenReturn(dispositivos);
//
//        List<DispositivoResponseDTO> dispositivosPrueba = serviceTraccar.obtenerDispositivos();
//        assertEquals(dispositivos,dispositivosPrueba);
//        assertEquals(dispositivos.size(),dispositivosPrueba.size());
//    }
//}
