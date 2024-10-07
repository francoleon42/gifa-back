package com.gifa_api.testUnitario;

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
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CargaCombustibleServiceImplTest {

    @Mock
    private ITarjetaRepository tarjetaRepository;

    @Mock
    private ICargaCombustibleRepository cargaCombustibleRepository;

    @InjectMocks
    private CargaCombustibleServiceImpl cargaCombustibleService;

    private  CargaCombustibleRequestDTO cargaCombustible;

    @BeforeEach
    void setUp() {
        cargaCombustible = CargaCombustibleRequestDTO.builder()
                .numeroTarjeta(12345678)
                .cantidadLitros(50)
                .FechaYhora(LocalDateTime.now())
                .build();
    }

    @Test
    void cargarCombustible_debeLanzarNotFoundExceptionCuandoTarjetaNoExiste() {

        when(tarjetaRepository.findById(cargaCombustible.getNumeroTarjeta())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cargaCombustibleService.cargarCombustible(cargaCombustible));

        verify(cargaCombustibleRepository, never()).save(any(CargaCombustible.class));
    }

    @Test
    void cargarCombustible_debeGuardarCargaCombustibleCuandoTarjetaExiste() {
        Tarjeta tarjeta = new Tarjeta();
        when(tarjetaRepository.findById(cargaCombustible.getNumeroTarjeta())).thenReturn(Optional.of(tarjeta));

        cargaCombustibleService.cargarCombustible(cargaCombustible);

        verify(tarjetaRepository, times(1)).findById(cargaCombustible.getNumeroTarjeta());
        verify(cargaCombustibleRepository, times(1)).save(any(CargaCombustible.class));
    }
}
