package com.gifa_api.testUnitario.service;

import com.gifa_api.dto.gestionDeCombustilble.CargaCombustibleRequestDTO;
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
                .builder().cantidadLitros(10).numeroTarjeta(1).FechaYhora(OffsetDateTime.now())
                .build();
    }

    @Test
    void crearMantenimiento_asuntoNoPuedeSerNull(){
        cargaCombustible.setCantidadLitros(null);
        assertThrows(IllegalArgumentException.class, () -> cargaCombustibleService.cargarCombustible(cargaCombustible));
    }

    @Test
    void crearMantenimiento_cantidadDeLitrosTieneQueSerPositiva(){
        cargaCombustible.setCantidadLitros(0);
        assertThrows(IllegalArgumentException.class, () -> cargaCombustibleService.cargarCombustible(cargaCombustible));
    }

    @Test
    void crearMantenimiento_campoTarjetaNoPuedeSerNull(){
        cargaCombustible.setNumeroTarjeta(null);
        assertThrows(IllegalArgumentException.class, () -> cargaCombustibleService.cargarCombustible(cargaCombustible));
    }

}
