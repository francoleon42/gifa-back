//package com.gifa_api.testUnitario.service;
//
//import com.gifa_api.dto.gestionDeCombustilble.CargaCombustibleRequestDTO;
//import com.gifa_api.exception.NotFoundException;
//import com.gifa_api.model.CargaCombustible;
//import com.gifa_api.model.Tarjeta;
//import com.gifa_api.repository.ICargaCombustibleRepository;
//import com.gifa_api.repository.ITarjetaRepository;
//import com.gifa_api.service.impl.CargaCombustibleServiceImpl;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDateTime;
//import java.time.OffsetDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class CargaCombustibleServiceImplTest {
//
//    @Mock
//    private ITarjetaRepository tarjetaRepository;
//
//    @Mock
//    private ICargaCombustibleRepository cargaCombustibleRepository;
//
//    @InjectMocks
//    private CargaCombustibleServiceImpl cargaCombustibleService;
//
//    @Test
//    void cargarCombustible_tarjetaExistente() {
//        // Arrange
//        CargaCombustibleRequestDTO requestDTO = new CargaCombustibleRequestDTO();
//        requestDTO.setNumeroTarjeta(123);
//        requestDTO.setCantidadLitros(50);
//        requestDTO.setFechaYhora(LocalDateTime.now());
//
//        Tarjeta tarjeta = new Tarjeta();
//        when(tarjetaRepository.findById(123)).thenReturn(Optional.of(tarjeta));
//
//        when(cargaCombustibleRepository.save(any(CargaCombustible.class))).thenReturn(new CargaCombustible());
//
//        // Act
//        cargaCombustibleService.cargarCombustible(requestDTO);
//
//        // Assert
//        verify(tarjetaRepository, times(1)).findById(123);
//        verify(cargaCombustibleRepository, times(1)).save(any(CargaCombustible.class));
//    }
//
//    @Test
//    void cargarCombustible_tarjetaNoExistente() {
//        // Arrange
//        CargaCombustibleRequestDTO requestDTO = new CargaCombustibleRequestDTO();
//        requestDTO.setNumeroTarjeta(123);
//
//        when(tarjetaRepository.findById(123)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
//            cargaCombustibleService.cargarCombustible(requestDTO);
//        });
//
//        assertEquals("No se encontró la tarjeta con id: 123", exception.getMessage());
//        verify(tarjetaRepository, times(1)).findById(123);
//        verify(cargaCombustibleRepository, never()).save(any(CargaCombustible.class));
//    }
////ver el tema de los metodos privados
////    @Test
////    void obtenerPrecioCombustible_actualizarPrecios() {
////        // Arrange
////        List<Float> preciosMock = List.of(100f, 200f, 300f, 400f);
////        LocalDate today = LocalDate.now();
////
////        // Act
////        when(cargaCombustibleService.actualizarPrecioCombustible()).thenReturn(preciosMock);
////        List<Float> precios = cargaCombustibleService.obtenerPrecioCombustible();
////
////        // Assert
////        assertEquals(preciosMock, precios);
////        assertEquals(today, cargaCombustibleService.ultimaActualizacionPrecio);
////    }
//
//    @Test
//    void combustibleCargadoEn() {
//        // Arrange
//        List<CargaCombustible> cargas = new ArrayList<>();
//        CargaCombustible carga1 = new CargaCombustible();
//        carga1.setCantidadLitros(30);
//        CargaCombustible carga2 = new CargaCombustible();
//        carga2.setCantidadLitros(50);
//        cargas.add(carga1);
//        cargas.add(carga2);
//
//        when(cargaCombustibleRepository.findByNumeroTarjetaAndFechaAfter(anyInt(), any(OffsetDateTime.class)))
//                .thenReturn(cargas);
//
//        // Act
//        double total = cargaCombustibleService.combustibleCargadoEn(123, OffsetDateTime.now());
//
//        // Assert
//        assertEquals(80.0, total);
//        verify(cargaCombustibleRepository, times(1)).findByNumeroTarjetaAndFechaAfter(anyInt(), any(OffsetDateTime.class));
//    }
//}
