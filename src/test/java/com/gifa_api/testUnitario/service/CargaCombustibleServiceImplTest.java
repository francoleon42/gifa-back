package com.gifa_api.testUnitario.service;

import com.gifa_api.dto.gestionDeCombustilble.CargaCombustibleRequestDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.CargaCombustible;
import com.gifa_api.repository.ICargaCombustibleRepository;
import com.gifa_api.repository.ITarjetaRepository;
import com.gifa_api.service.impl.CargaCombustibleServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.BeforeEach;

import java.time.OffsetDateTime;

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
        cargaCombustible = CargaCombustibleRequestDTO
                .builder().cantidadLitros(10).numeroTarjeta(1).numeroTarjeta(12).FechaYhora(OffsetDateTime.now())
                .build();
    }

    @Test
    void cargarCombustible_cantidadDeLitrosNoPuedeSerNull(){
        cargaCombustible.setCantidadLitros(null);
        assertThrows(IllegalArgumentException.class, () -> cargaCombustibleService.cargarCombustible(cargaCombustible));
    }

    @Test
    void cargarCombustible_cantidadDeLitrosDebeSerPositiva(){
        cargaCombustible.setCantidadLitros(0);
        assertThrows(IllegalArgumentException.class, () -> cargaCombustibleService.cargarCombustible(cargaCombustible));
    }

    @Test
    void cargarCombustible_campoTarjetaNoPuedeSerNull(){
        cargaCombustible.setNumeroTarjeta(null);
        assertThrows(IllegalArgumentException.class, () -> cargaCombustibleService.cargarCombustible(cargaCombustible));
    }
    

    @Test
    void cargarCombustible_tarjetaInbalidaLanzaExcepcion(){
        assertThrows(NotFoundException.class,() ->cargaCombustibleService.cargarCombustible(cargaCombustible));
        verify(tarjetaRepository,times(1)).findById(cargaCombustible.getNumeroTarjeta());
        verify(cargaCombustibleRepository,never()).save(any(CargaCombustible.class));
    }

}
