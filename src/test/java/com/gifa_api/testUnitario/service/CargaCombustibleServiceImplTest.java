package com.gifa_api.testUnitario.service;

import com.gifa_api.dto.gestionDeCombustilble.CargaCombustibleRequestDTO;
import com.gifa_api.exception.BadRequestException;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.CargaCombustible;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.repository.ICargaCombustibleRepository;
import com.gifa_api.repository.ITarjetaRepository;
import com.gifa_api.service.impl.CargaCombustibleServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private List<CargaCombustible> cargasDeCombustible ;
    private CargaCombustibleRequestDTO cargaCombustibleDTO;

    @BeforeEach
    void setUp(){
        cargaCombustibleDTO = CargaCombustibleRequestDTO
                .builder()
                .cantidadLitros(10)
                .id(1)
                .build();

        CargaCombustible cargaCombustible = CargaCombustible.builder()
                .tarjeta(null)
                .precioPorLitro(1.0f)
                .costoTotal(3.0f)
                .fechaHora(OffsetDateTime.now())
                .cantidadLitros(10)
                .build();

        CargaCombustible cargaCombustible2 = CargaCombustible.builder()
                .tarjeta(null)
                .precioPorLitro(1.0f)
                .costoTotal(3.0f)
                .fechaHora(OffsetDateTime.now().plusDays(1))
                .cantidadLitros(10)
                .build();

        cargasDeCombustible = List.of(cargaCombustible,cargaCombustible2);

    }

    @Test
    @DisplayName("cargar combustible con campo litros igual a null lanza BadRequestException")
    void cargarCombustibleCantidadLitrosNulo() {
        cargaCombustibleDTO.setCantidadLitros(null);
        verificarNoRegistroDeCargaDeCombustibleInvalida();
    }

    @Test
    @DisplayName("cargar combustible con campo litros igual a 0 lanza BadRequestException")
    void cargarCombustibleSinLitros() {
        cargaCombustibleDTO.setCantidadLitros(0);
        verificarNoRegistroDeCargaDeCombustibleInvalida() ;
    }

    @Test
    @DisplayName("cargar combustible con campo tarjeta igual a null lanza BadRequestException")
    void cargarCombustibleConCampoTarjetaVacio_lanzaBadRequestException(){
        cargaCombustibleDTO.setId(null);
        verificarNoRegistroDeCargaDeCombustibleInvalida() ;
    }

    @Test
    @DisplayName("cargar combustible con tarjeta que no existe en la base de datos lanza NotFoundException")
    void cargarCombustible_tarjetaInvalidaLanzaExcepcion() {
        when(tarjetaRepository.findById(cargaCombustibleDTO.getId())).thenReturn(empty());

        assertThrows(NotFoundException.class, () -> cargaCombustibleService.cargarCombustible(cargaCombustibleDTO));
        verify(tarjetaRepository, times(1)).findById(cargaCombustibleDTO.getId());
        verify(cargaCombustibleRepository, never()).save(any(CargaCombustible.class));
    }

    @Test
    @DisplayName("cargar combustible correctamente con tarjeta existente")
    void cargarCombustible() {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setNumero(12345678); // Establecemos un número de tarjeta válido

        when(tarjetaRepository.findById(cargaCombustibleDTO.getId())).thenReturn(Optional.of(tarjeta));

        cargaCombustibleService.cargarCombustible(cargaCombustibleDTO); // Llamada al método público

        verify(cargaCombustibleRepository, times(1)).save(any(CargaCombustible.class));
    }

    @Test
    @DisplayName("Ver combustible entre un rango de fechas")
    void combustibleCargadoEntreFechas(){
        OffsetDateTime fechaActual= OffsetDateTime.now();
        OffsetDateTime fechaPosterior= OffsetDateTime.now().plusDays(1);
        Integer numeroDeTarjeta = 1;
        when(cargaCombustibleRepository.findByNumeroTarjetaAndFechaBetween(numeroDeTarjeta,
                fechaActual,
                fechaPosterior))
                .thenReturn(cargasDeCombustible);

        double totalDeLitros = cargaCombustibleService.combustibleCargadoEntreFechas(numeroDeTarjeta,
                fechaActual,
                fechaPosterior);

        assertEquals(20,totalDeLitros);
        verify(cargaCombustibleRepository,times(1)).findByNumeroTarjetaAndFechaBetween(numeroDeTarjeta,
                fechaActual,
                fechaPosterior);
    }

    public void verificarNoRegistroDeCargaDeCombustibleInvalida(){
        assertThrows(BadRequestException.class, () -> cargaCombustibleService.cargarCombustible(cargaCombustibleDTO));
        verify(cargaCombustibleRepository,never()).save(any(CargaCombustible.class));
    }

}
