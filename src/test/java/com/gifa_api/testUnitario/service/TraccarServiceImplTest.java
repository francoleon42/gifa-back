package com.gifa_api.testUnitario.service;

import com.gifa_api.client.TraccarClient;
import com.gifa_api.dto.traccar.DispositivoResponseDTO;
import com.gifa_api.dto.traccar.InconsistenciasKMconCombustiblesResponseDTO;
import com.gifa_api.dto.traccar.KilometrosResponseDTO;
import com.gifa_api.model.Dispositivo;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IChoferRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.ICargaCombustibleService;
import com.gifa_api.service.IDispositivoService;
import com.gifa_api.service.impl.TraccarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TraccarServiceImplTest {
    @Mock
    private TraccarClient traccarCliente;

    @Mock
    private IVehiculoRepository vehiculoRepository;

    @Mock
    private ICargaCombustibleService cargaCombustibleService;

    @Mock
    private IDispositivoService dispositivoService;

    @Mock
    private IChoferRepository choferRepository;

    @InjectMocks
    private TraccarServiceImpl serviceTraccar;

    private List <Vehiculo> vehiculos;

    @BeforeEach
    void setUp(){
        Tarjeta tarjeta = Tarjeta.builder()
                .numero(123)
                .build();
        Dispositivo dispositivo = Dispositivo.builder()
                .unicoId("1")
                .build();
        Vehiculo vehiculo1 = Vehiculo.builder()
                .id(1)
                .fechaVencimiento(LocalDate.now())
                .patente("AB123CD")
                .modelo("Toyota")
                .tarjeta(tarjeta)
                .dispositivo(dispositivo)
                .build();
        vehiculos = List.of(vehiculo1);


    }
    @Test
    void crearDispositivo(){
        Dispositivo dispositivo = new Dispositivo();
        serviceTraccar.crearDispositivo(dispositivo);

        verify(traccarCliente,times(1)).postCrearDispositivoTraccar(dispositivo);
    }

    @Test
    void obtenerDispositivos_devuelveTresDispositivos(){
        DispositivoResponseDTO dispositivo1 = DispositivoResponseDTO.builder().id(1).name("a").status("a").uniqueId("1").build();
        DispositivoResponseDTO dispositivo2 = DispositivoResponseDTO.builder().id(2).name("a").status("a").uniqueId("1").build();
        DispositivoResponseDTO dispositivo3 = DispositivoResponseDTO.builder().id(3).name("a").status("a").uniqueId("1").build();

        List<DispositivoResponseDTO> dispositivos = Arrays.asList(dispositivo1, dispositivo2, dispositivo3);

        when(traccarCliente.getDispositivos()).thenReturn(dispositivos);
        when(vehiculoRepository.findByPatente(anyString())).thenReturn(Optional.of(vehiculos.get(0)));

        List<DispositivoResponseDTO> dispositivosPrueba = serviceTraccar.obtenerDispositivos();
        assertEquals(dispositivos.size(),dispositivosPrueba.size());
    }

    @Test
    void obtenerDispositivos_devuelveNingunDispositivo(){
        DispositivoResponseDTO dispositivo1 = DispositivoResponseDTO.builder().id(1).name("a").status("a").uniqueId("1").build();
        DispositivoResponseDTO dispositivo2 = DispositivoResponseDTO.builder().id(2).name("a").status("a").uniqueId("1").build();
        DispositivoResponseDTO dispositivo3 = DispositivoResponseDTO.builder().id(3).name("a").status("a").uniqueId("1").build();

        List<DispositivoResponseDTO> dispositivos = Arrays.asList(dispositivo1, dispositivo2, dispositivo3);

        when(traccarCliente.getDispositivos()).thenReturn(dispositivos);
        when(vehiculoRepository.findByPatente(anyString())).thenReturn(Optional.empty());

        List<DispositivoResponseDTO> dispositivosPrueba = serviceTraccar.obtenerDispositivos();
        assertEquals(0,dispositivosPrueba.size());
    }

   //hacer los que faltan

//    @Test
//    void getInconsistencias_noDevuelveNada(){
//        LocalDate fecha = LocalDate.now();
//        OffsetDateTime fechaCasteadaAOffset = fecha.atStartOfDay().atOffset(ZoneOffset.UTC);
//        when(vehiculoRepository.findAll()).thenReturn(vehiculos);
//        when(dispositivoService.calcularKmDeDispositivoDespuesDeFecha(vehiculos.get(0).getDispositivo().getUnicoId(),fechaCasteadaAOffset))
//                .thenReturn(11);
//        when(cargaCombustibleService.combustibleCargadoEn(vehiculos.get(0).getTarjeta().getNumero(),LocalDate.now()))
//                .thenReturn(10.0);
//
//        List<InconsistenciasKMconCombustiblesResponseDTO> inconsistencias = serviceTraccar.getInconsistencias(fecha);
//
//        assertEquals(inconsistencias.size(),0);
//
//    }
//    @Test
//    void getInconsistencias_devueleInconsistencias(){
//        LocalDate fecha = LocalDate.now();
//        OffsetDateTime fechaCasteadaAOffset = fecha.atStartOfDay().atOffset(ZoneOffset.UTC);
//        List<String> nombresChoferesInconsistencias =  List.of("mono1","mono2");
//
//        when(vehiculoRepository.findAll()).thenReturn(vehiculos);
//        when(dispositivoService.calcularKmDeDispositivoDespuesDeFecha(vehiculos.get(0).getDispositivo().getUnicoId(),fechaCasteadaAOffset))
//                .thenReturn(10);
//        when(cargaCombustibleService.combustibleCargadoEn(vehiculos.get(0).getTarjeta().getNumero(),LocalDate.now()))
//                .thenReturn(11.0);
//        when(choferRepository.obtenerNombreDeChofersDeVehiculo(vehiculos.get(0).getId())).thenReturn(nombresChoferesInconsistencias);
//
//        List<InconsistenciasKMconCombustiblesResponseDTO> inconsistencias = serviceTraccar.getInconsistencias(fecha);
//
//        assertEquals(inconsistencias.size(),1);
//        assertEquals(inconsistencias.get(0).getNombresDeResponsables().get(0),"mono1");
//        assertEquals(inconsistencias.get(0).getNombresDeResponsables().get(1),"mono2");
//
//    }


}
