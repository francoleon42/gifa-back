//package com.gifa_api.testUnitario.service;
//
//import com.gifa_api.dto.mantenimiento.MantenimientosResponseDTO;
//import com.gifa_api.dto.vehiculo.ListaVehiculosResponseDTO;
//import com.gifa_api.dto.vehiculo.RegistarVehiculoDTO;
//import com.gifa_api.dto.vehiculo.VehiculoResponseConQrDTO;
//import com.gifa_api.dto.vehiculo.VehiculoResponseDTO;
//import com.gifa_api.exception.BadRequestException;
//import com.gifa_api.exception.NotFoundException;
//import com.gifa_api.model.Mantenimiento;
//import com.gifa_api.model.Tarjeta;
//import com.gifa_api.model.Vehiculo;
//import com.gifa_api.repository.ITarjetaRepository;
//import com.gifa_api.repository.IVehiculoRepository;
//import com.gifa_api.service.impl.VehiculoServiceImpl;
//import com.gifa_api.utils.enums.EstadoDeHabilitacion;
//import com.gifa_api.utils.enums.EstadoVehiculo;
//import com.gifa_api.utils.mappers.VehiculoMapper;
//import com.gifa_api.utils.mappers.VehiculoResponseConQrMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.stubbing.Answer;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//@ExtendWith(MockitoExtension.class)
//class VehiculoServiceImplTest {
//
//    @Mock
//    private IVehiculoRepository vehiculoRepository;
//
//    @Mock
//    private ITarjetaRepository tarjetaRepository;
//
//    @Mock
//    private VehiculoMapper vehiculoMapper;
//
//    @Mock
//    private VehiculoResponseConQrMapper vehiculoResponseConQrMapper;
//
//    @Mock
//    private Vehiculo vehiculoFlota;
//
//    @InjectMocks
//    private VehiculoServiceImpl vehiculoService;
//
//    private RegistarVehiculoDTO vehiculoDTO;
//
//    private Tarjeta tarjeta;
//
//    private Vehiculo vehiculo;
//     List<Mantenimiento> mantenimientos;
//
//
//    @BeforeEach
//    void setUp(){
//         vehiculoDTO = RegistarVehiculoDTO
//                .builder()
//                .patente("ABC123")
//                .antiguedad(0)
//                .kilometraje(0)
//                .modelo("toyota")
//                .fechaRevision(LocalDate.now().plusDays(1))
//                .build();
//
//         tarjeta = Tarjeta.builder().numero(12345678).build();
//
//         //Integer id = 1;
//         vehiculo = Vehiculo.builder()
//                 .id(1)
//                .patente(vehiculoDTO.getPatente())
//                .antiguedad(vehiculoDTO.getAntiguedad())
//                .kilometraje(vehiculoDTO.getKilometraje())
//                .modelo(vehiculoDTO.getModelo())
//                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
//                .estadoVehiculo(EstadoVehiculo.REPARADO)
//                .fechaVencimiento(vehiculoDTO.getFechaRevision())
//                .tarjeta(tarjeta)
//                 .mantenimientos(Set.of(new Mantenimiento()))
//                .build();
//    }
//
//    @Test
//    void registrar_patenteViejaInvalidaLanzaExcepcion(){
//        vehiculoDTO.setPatente("AD1234");
//        verificacionDeNoRegistroDeVehiculoInvalido();
//    }
//
//    @Test
//    void registrar_patenteNuevanvalidaLanzaExcepcion(){
//        vehiculoDTO.setPatente("AD1821K");
//        verificacionDeNoRegistroDeVehiculoInvalido();
//    }
//
//    @Test
//    void registrar_PatenteNulaLanzaExcepcion(){
//        vehiculoDTO.setPatente(null);
//        verificacionDeNoRegistroDeVehiculoInvalido();
//    }
//    @Test
//    void registrar_antiguedadNoPuedeSerNull(){
//        vehiculoDTO.setAntiguedad(null);
//        verificacionDeNoRegistroDeVehiculoInvalido();
//    }
//
//    @Test
//    void registrar_antiguedadNoPuedeSerNegativa(){
//        vehiculoDTO.setAntiguedad(-1);
//        verificacionDeNoRegistroDeVehiculoInvalido();
//    }
//
//    @Test
//    void registrar_kilometrajeNoPuedeSerNull(){
//        vehiculoDTO.setKilometraje(null);
//        verificacionDeNoRegistroDeVehiculoInvalido();
//    }
//
//    @Test
//    void registrar_kilometrajeNoPuedeSerNegativa(){
//        vehiculoDTO.setKilometraje(-1);
//        verificacionDeNoRegistroDeVehiculoInvalido();
//    }
//
//    @Test
//    void modeloNoPuedeSerVacio(){
//        vehiculoDTO.setModelo("");
//        verificacionDeNoRegistroDeVehiculoInvalido();
//    }
//
//    @Test
//    void modeloNoPuedeSerNull(){
//        vehiculoDTO.setModelo(null);
//        verificacionDeNoRegistroDeVehiculoInvalido();
//    }
//
//    @Test
//    void fechaRevisionNoPuedeSerNull(){
//        vehiculoDTO.setFechaRevision(null);
//        verificacionDeNoRegistroDeVehiculoInvalido();
//    }
//
//    @Test
//    void fechaRevisionNoPuedeSerAnteriorAlaFechaActual(){
//        vehiculoDTO.setFechaRevision(LocalDate.now().minusDays(1));
//        verificacionDeNoRegistroDeVehiculoInvalido();
//    }
//
//    @Test
//    void registrarConPatenteVieja() {
//        verificarGeneracionDeQrDeVehiculoRegistrado();
//    }
//
//    @Test
//    void testRegistrarConPatenteNueva() {
//        vehiculo.setPatente("AB123CD");
//        verificarGeneracionDeQrDeVehiculoRegistrado();
//    }
//
//    @Test
//    void testGetVehiculos() {
//        ListaVehiculosResponseDTO responseDTO = new ListaVehiculosResponseDTO();
//        when(vehiculoMapper.toListaVehiculosResponseDTO(any())).thenReturn(responseDTO);
//
//        ListaVehiculosResponseDTO result = vehiculoService.getVehiculos();
//
//        assertNotNull(result);
//        verify(vehiculoMapper, times(1)).toListaVehiculosResponseDTO(any());
//    }
//
//    @Test
//    void testInhabilitarVehiculoNotFound() {
//        Integer vehiculoId = 1;
//        when(vehiculoRepository.findById(vehiculoId)).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> vehiculoService.inhabilitar(vehiculoId));
//    }
//
//    @Test
//    void testInhabilitarVehiculoQueEstaHabilitado() {
//        when(vehiculoRepository.findById(vehiculo.getId())).thenReturn(Optional.of(vehiculo));
//
//        vehiculoService.inhabilitar(vehiculo.getId());
//
//        verify(vehiculoRepository, times(1)).save(vehiculo);
//        assertEquals(EstadoDeHabilitacion.INHABILITADO,vehiculo.getEstadoDeHabilitacion());
//    }
//
//    @Test
//    void habilitarVehiculoNotFound() {
//        Integer vehiculoId = 1;
//        when(vehiculoRepository.findById(vehiculoId)).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> vehiculoService.habilitar(vehiculoId));
//    }
//
//    @Test
//    void testHabilitarVehiculoQueEstaInhabilitado() {
//        vehiculo.setEstadoDeHabilitacion(EstadoDeHabilitacion.INHABILITADO);
//        when(vehiculoRepository.findById(vehiculo.getId())).thenReturn(Optional.of(vehiculo));
//
//        vehiculoService.habilitar(vehiculo.getId());
//
//        verify(vehiculoRepository, times(1)).save(vehiculo);
//        assertEquals(EstadoDeHabilitacion.HABILITADO,vehiculo.getEstadoDeHabilitacion());
//    }
//
//
//    @Test
//    void testObtenerHistorialDeVehiculoNotFound() {
//        when(vehiculoRepository.findById(vehiculo.getId())).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> vehiculoService.obtenerHistorialDeVehiculo(vehiculo.getId()));
//        verify(vehiculoResponseConQrMapper,never()).toVehiculoResponseConQrDTO(vehiculo,mantenimientos);
//    }
//
//    @Test
//    void testObtenerHistorialDeVehiculoFound() {
//        MantenimientosResponseDTO mantenimientoDTO =   MantenimientosResponseDTO.builder().build();
//        VehiculoResponseConQrDTO vehiculoResponseQR =  VehiculoResponseConQrDTO.builder()
//                .vehiculo(new VehiculoResponseDTO())
//                .mantenimientos(mantenimientoDTO)
//                .build();
//
//        when(vehiculoRepository.findById(vehiculo.getId())).thenReturn(Optional.of(vehiculo));
//        List<Mantenimiento> mantenimientos =  new ArrayList<>(vehiculo.getMantenimientos());
//
//        when(vehiculoResponseConQrMapper.toVehiculoResponseConQrDTO(vehiculo, mantenimientos)).thenReturn(vehiculoResponseQR);
//
//        vehiculoService.obtenerHistorialDeVehiculo(vehiculo.getId());
//        verify(vehiculoResponseConQrMapper, times(1)).toVehiculoResponseConQrDTO(vehiculo, mantenimientos);
//    }
//
//    public void verificarGeneracionDeQrDeVehiculoRegistrado(){
//        when(tarjetaRepository.save(any(Tarjeta.class))).thenReturn(tarjeta);
//
//        when(vehiculoRepository.save(any(Vehiculo.class))).thenAnswer(invocation -> {
//            vehiculo = invocation.getArgument(0);
//            vehiculo.setId(1); // Simular que se le asigna un ID
//            return   vehiculo; // Retornar el vehículo guardado
//        });
//
//        vehiculoService.registrar(vehiculoDTO);
//        assertNotNull(vehiculo.getQr());
//        verify(tarjetaRepository, times(1)).save(any(Tarjeta.class));
//        verify(vehiculoRepository, times(2)).save(any(Vehiculo.class));
//    }
//
//    public void verificacionDeNoRegistroDeVehiculoInvalido(){
//        assertThrows(BadRequestException.class,() -> vehiculoService.registrar(vehiculoDTO));
//        verify(vehiculoRepository,never()).save(any(Vehiculo.class));
//    }
//}
