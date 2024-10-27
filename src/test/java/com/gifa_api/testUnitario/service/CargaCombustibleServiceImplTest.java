package com.gifa_api.testUnitario.service;

import com.gifa_api.dto.gestionDeCombustilble.CargaCombustibleRequestDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.CargaCombustible;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.repository.ICargaCombustibleRepository;
import com.gifa_api.repository.ITarjetaRepository;
import com.gifa_api.service.impl.CargaCombustibleServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CargaCombustibleServiceImplTest {

    @Mock
    private ITarjetaRepository tarjetaRepository;

    @Mock
    private ICargaCombustibleRepository cargaCombustibleRepository;

    @InjectMocks
    private CargaCombustibleServiceImpl cargaCombustibleService;

    private CargaCombustibleRequestDTO cargaCombustible;

    @BeforeEach
    void setUp(){
        cargaCombustible  = CargaCombustibleRequestDTO
                .builder()
                .cantidadLitros(10)
                .numeroTarjeta(12345678) // Usando un número entero para la tarjeta
                .FechaYhora(OffsetDateTime.now())
                .build();
    }

    @Test
    void cargarCombustible_cantidadDeLitrosNoPuedeSerNull() {
        cargaCombustible.setCantidadLitros(null);
        verificarNoRegistroDeCargaDeCombustibleInvalida();
    }

    @Test
    void cargarCombustible_cantidadDeLitrosDebeSerPositiva() {
        cargaCombustible.setCantidadLitros(0);
        verificarNoRegistroDeCargaDeCombustibleInvalida() ;   }

    @Test
    void cargarCombustible_campoTarjetaNoPuedeSerNull() {
        cargaCombustible.setNumeroTarjeta(null);
        verificarNoRegistroDeCargaDeCombustibleInvalida();
    }

    @Test
    void cargarCombustible_tarjetaInvalidaLanzaExcepcion() {
        when(tarjetaRepository.findById(cargaCombustible.getNumeroTarjeta())).thenReturn(empty());

        assertThrows(NotFoundException.class, () -> cargaCombustibleService.cargarCombustible(cargaCombustible));
        verify(tarjetaRepository, times(1)).findById(cargaCombustible.getNumeroTarjeta());
        verify(cargaCombustibleRepository, never()).save(any(CargaCombustible.class));
    }

    @Test
    void cargarCombustible_tarjetaValida_lanzaExcepcionSiTarjetaNoSeEncuentra() {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setNumero(12345678); // Establecemos un número de tarjeta válido

        when(tarjetaRepository.findById(cargaCombustible.getNumeroTarjeta())).thenReturn(Optional.of(tarjeta));
        when(cargaCombustibleRepository.save(any(CargaCombustible.class))).thenReturn(new CargaCombustible());

        cargaCombustibleService.cargarCombustible(cargaCombustible); // Llamada al método público

        verify(cargaCombustibleRepository, times(1)).save(any(CargaCombustible.class));
    }

    @Test
    void cargarCombustible_tarjetaValida_yCargaExitosa() {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setNumero(12345678); // Establecemos un número de tarjeta válido

        when(tarjetaRepository.findById(cargaCombustible.getNumeroTarjeta())).thenReturn(Optional.of(tarjeta));
        when(cargaCombustibleRepository.save(any(CargaCombustible.class))).thenReturn(new CargaCombustible());

        cargaCombustibleService.cargarCombustible(cargaCombustible); // Llamada al método público

        verify(cargaCombustibleRepository, times(1)).save(any(CargaCombustible.class));
    }

    private void verificarNoRegistroDeCargaDeCombustibleInvalida(){
        assertThrows(IllegalArgumentException.class, () -> cargaCombustibleService.cargarCombustible(cargaCombustible));
        verify(cargaCombustibleRepository,never()).save(any(CargaCombustible.class));
    }

}
