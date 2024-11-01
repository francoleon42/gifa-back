package com.gifa_api.testUnitario.service;

import com.gifa_api.dto.chofer.AsignarChoferDTO;
import com.gifa_api.dto.chofer.ChoferRegistroDTO;
import com.gifa_api.dto.chofer.ChoferResponseDTO;
import com.gifa_api.exception.BadRequestException;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Chofer;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IChoferRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.impl.ChoferServiceImpl;
import com.gifa_api.utils.enums.EstadoChofer;
import com.gifa_api.utils.mappers.ChoferMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)

class ChoferServiceImplTest {
    @Mock
    private IVehiculoRepository vehiculoRepository;

    @Mock
    private IChoferRepository choferRepository;

    @Mock
    private  PasswordEncoder passwordEncoder;
    @Mock
    private  ChoferMapper choferMapper;
    @InjectMocks
    private ChoferServiceImpl choferService;

    private ChoferRegistroDTO choferRegistro;
    private Chofer chofer;
    private  AsignarChoferDTO asignarChoferDTO;
    private Vehiculo vehiculo;
    @BeforeEach
    void setUp(){
        choferRegistro = ChoferRegistroDTO.builder().username("username").password("password").nombre("nombre").build();
        asignarChoferDTO =  AsignarChoferDTO.builder().idChofer(1).idVehiculo(1).build();

        chofer = Chofer.builder().id(1).nombre("chofer").build();
        vehiculo = Vehiculo.builder()
                .chofers(Set.of(chofer))
                .id(1)
                .patente("ABC123")
                .build();
    }

    @Test
    void campoUserNameNoPuedeEstarVacio(){
        choferRegistro.setUsername("");
        verificarNoRegistroDeChoferInvalido();
    }
    @Test
    void campoUserNameNoPuedeSerNulo(){
        choferRegistro.setUsername(null);
        verificarNoRegistroDeChoferInvalido();
    }

    @Test
    void campoUserNameNoPuedeContenerCaracteresEspeciales(){
        choferRegistro.setUsername("fede#");
        verificarNoRegistroDeChoferInvalido();
    }

    @Test
    void campoContraseniaoPuedeSerNulo(){
        choferRegistro.setPassword(null);
        verificarNoRegistroDeChoferInvalido();
    }

    @Test
    void campoContraseniaNoPuedeEstarVacio(){
        choferRegistro.setPassword("");
        verificarNoRegistroDeChoferInvalido();
    }

    @Test
    void campoNombreNoPuedeEstarVacio(){
        choferRegistro.setNombre("");
        verificarNoRegistroDeChoferInvalido();
    }

    @Test
    void campoNombreNoPuedeSerNulo(){
        choferRegistro.setNombre(null);
        verificarNoRegistroDeChoferInvalido();
    }

    @Test
    void habilitarChofer_debeLanzarExcepcionSiNoExiste() {
        when(choferRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> choferService.habilitar(1));
        verify(choferRepository, never()).save(any(Chofer.class));
    }


    @Test
    void inhabilitarChofer_debeLanzarExcepcionSiNoExiste() {
        Integer idChofer = 1;
        when(choferRepository.findByIdWithVehiculo(idChofer)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> choferService.inhabilitar(1));
        verify(choferRepository, never()).save(any(Chofer.class));
    }

    @Test

    void asignarVehiculo_NoSeEncuentraVehiculoParaElChofer(){
        when(vehiculoRepository.findById(asignarChoferDTO.getIdVehiculo())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> choferService.asignarVehiculo(asignarChoferDTO));

        verify(choferRepository,never()).save(any(Chofer.class));
        verify(vehiculoRepository,times(1)).findById(asignarChoferDTO.getIdVehiculo());
    }

    @Test
    void asignarVehiculo_NoSeEncuentraElChofer(){
        when(vehiculoRepository.findById(asignarChoferDTO.getIdVehiculo())).thenReturn(Optional.of(new Vehiculo()));
        when(choferRepository.findById(asignarChoferDTO.getIdChofer())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> choferService.asignarVehiculo(asignarChoferDTO));

        verify(vehiculoRepository,times(1)).findById(asignarChoferDTO.getIdVehiculo());
        verify(choferRepository,times(1)).findById(asignarChoferDTO.getIdVehiculo());
        verify(choferRepository,never()).save(any(Chofer.class));
    }

    @Test
    void registrarChofer_usuarioVacioLanzaExcepcion(){
        choferRegistro.setUsername("");
        verificarNoRegistroDeChoferInvalido();
    }

    @Test
    void registrarChofer_usuarioConCaracteresEspecialesLanzaExcepcion(){
        choferRegistro.setUsername("diegote#");
        verificarNoRegistroDeChoferInvalido();
    }
    @Test
    void registrarChofer_contraseniaVaciaLanzaExcepcion(){
        choferRegistro.setPassword("");
        verificarNoRegistroDeChoferInvalido();
    }

    @Test
    void registrarChofer_nombreVacioLanzaExcepcion(){
        choferRegistro.setNombre("");
        verificarNoRegistroDeChoferInvalido();
    }

    @Test
    void asignarVehiculo(){
        Vehiculo vehiculo = new Vehiculo();
        when(vehiculoRepository.findById(asignarChoferDTO.getIdVehiculo())).thenReturn(Optional.of(vehiculo));
        when(choferRepository.findById(asignarChoferDTO.getIdChofer())).thenReturn(Optional.of(chofer));

        choferService.asignarVehiculo(asignarChoferDTO);

        verify(vehiculoRepository,times(1)).findById(asignarChoferDTO.getIdVehiculo());
        verify(choferRepository,times(1)).findById(asignarChoferDTO.getIdChofer());
        verify(choferRepository,times(1)).save(any(Chofer.class));
        assertEquals(chofer.getVehiculo(),vehiculo);
    }

    @Test
    void registrarChoferDebeGuardarChoferSiVehiculoExiste() {
        ChoferRegistroDTO choferRegistroDTO = new ChoferRegistroDTO("chofer","1234","CHOFER");

        when(passwordEncoder.encode(choferRegistroDTO.getPassword())).thenReturn("1234");
        choferService.registro(choferRegistroDTO);

        verify(choferRepository, times(1)).save(any(Chofer.class));
    }
    @Test
    void inhabilitarChofer_debeInhabilitarSiEstaHabilitado() {
        chofer.setEstadoChofer(EstadoChofer.HABILITADO);
        chofer.setVehiculo(vehiculo);
        when(choferRepository.findByIdWithVehiculo(1)).thenReturn(Optional.of(chofer));

        choferService.inhabilitar(1);

        assertEquals(EstadoChofer.INHABILITADO, chofer.getEstadoChofer());
        verify(choferRepository, times(1)).findByIdWithVehiculo(1);
        verify(choferRepository, times(1)).save(any(Chofer.class));
    }

    @Test
    void habilitarChofer_debeHabilitarSiEstaInhabilitado() {
        chofer.setEstadoChofer(EstadoChofer.INHABILITADO);

        when(choferRepository.findById(1)).thenReturn(Optional.of(chofer));

        choferService.habilitar(1);

        assertEquals(EstadoChofer.HABILITADO, chofer.getEstadoChofer());
        verify(choferRepository, times(1)).save(any(Chofer.class));
        verify(choferRepository, times(1)).findById(1);
    }

    @Test
    void designarVehiculoDeChoferInhabilitado() {
        chofer.setEstadoChofer(EstadoChofer.HABILITADO);
        chofer.setVehiculo(vehiculo);
        when(choferRepository.findByIdWithVehiculo(1)).thenReturn(Optional.of(chofer));
        choferService.inhabilitar(1);

        verify(vehiculoRepository,times(1)).save(any(Vehiculo.class));
        assertNull(chofer.getVehiculo());
    }

    @Test
    void obtenerTodosLosChoferes(){
        ChoferResponseDTO chofer1 = ChoferResponseDTO.builder().nombre("chofer1").build();
        ChoferResponseDTO chofer2 = ChoferResponseDTO.builder().nombre("chofer2").build();
        List<ChoferResponseDTO> choferes = List.of(chofer1,chofer2);

        when(choferMapper.obtenerListaChoferDTO(choferRepository.findAll())).thenReturn(choferes);

        List<ChoferResponseDTO> choferesMostrados = choferService.obtenerAll();
        assertNotNull(choferesMostrados);
        assertEquals(choferesMostrados.size(),choferes.size());
        assertEquals(choferesMostrados.get(0).getIdChofer(),choferes.get(0).getIdChofer());
        assertEquals(choferesMostrados.get(1).getIdChofer(),choferes.get(1).getIdChofer());
    }

    public void verificarNoRegistroDeChoferInvalido(){
        assertThrows(BadRequestException.class, () -> choferService.registro(choferRegistro));
        verify(choferRepository, never()).save(any(Chofer.class));
    }

}