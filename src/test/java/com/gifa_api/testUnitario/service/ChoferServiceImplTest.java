package com.gifa_api.testUnitario.service;

import com.gifa_api.dto.chofer.AsignarChoferDTO;
import com.gifa_api.dto.chofer.ChoferRegistroDTO;
import com.gifa_api.dto.chofer.ChoferResponseDTO;
import com.gifa_api.dto.vehiculo.VehiculoResponseDTO;
import com.gifa_api.exception.BadRequestException;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Chofer;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IChoferRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.impl.ChoferServiceImpl;
import com.gifa_api.utils.mappers.ChoferMapper;
import com.gifa_api.utils.mappers.VehiculoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
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

    @Mock
    private  VehiculoMapper vehiculoMapper;

    @InjectMocks
    private ChoferServiceImpl choferService;

    private ChoferRegistroDTO choferRegistro;
    private Chofer chofer;
    private  AsignarChoferDTO asignarChoferDTO;
    private Vehiculo vehiculo;
    private VehiculoResponseDTO vehiculoResponseDTO;
    @BeforeEach
    void setUp(){
        choferRegistro = ChoferRegistroDTO.builder().username("username").password("password").nombre("nombre").build();
        asignarChoferDTO = AsignarChoferDTO.builder().idChofer(1).idVehiculo(1).build();

        vehiculo = Vehiculo.builder()
                .chofers(Set.of(Chofer.builder().id(1).
                        nombre("chofer")
                        .vehiculo(vehiculo)
                        .build()))
                .id(1)
                .patente("ABC123")
                .antiguedad(10)
                .modelo("TOYOTA")
                .fechaVencimiento(LocalDate.now().plusDays(1))
                .build();

        vehiculoResponseDTO = VehiculoResponseDTO.builder()
                .id(1)
                .patente("ABC123")
                .antiguedad(10)
                .modelo("toyota")
                .fechaVencimiento(LocalDate.now().plusDays(1))
                .build();
        chofer = Chofer.builder().id(1).
                nombre("chofer")
                .vehiculo(vehiculo)
                .build();
    }

    @Test
    void registrarChofer_userNameVacioLanzaExcepcion(){
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
    void campoContraseniaoNoPuedeSerNulo(){
        choferRegistro.setPassword(null);
        verificarNoRegistroDeChoferInvalido();
    }

    @Test
    void campoContraseniaNoPuedeEstarVacio(){
        choferRegistro.setPassword("");
        verificarNoRegistroDeChoferInvalido();
    }

    @Test
    void registrarChofer_nombreVacioLanzaExcepcion(){
        choferRegistro.setNombre("");
        verificarNoRegistroDeChoferInvalido();
    }


    @Test
    void campoNombreNoPuedeSerNulo(){
        choferRegistro.setNombre(null);
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
    void inhabilitarUsuarioChoferConUsuarioInvalido_lanzaExcepcion(){
        when(choferRepository.findByUsuario_Id(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> choferService.inhabilitarUsuarioChofer(1));

        verify(choferRepository,never()).save(any(Chofer.class));

    }
    @Test
    void inhabilitarUsuario_inhabilitaUsuarioExitosamente(){
        when(choferRepository.findByUsuario_Id(1)).thenReturn(Optional.of(chofer));

        choferService.inhabilitarUsuarioChofer(1);

        assertEquals(0,vehiculo.getChofers().size());
        assertEquals(chofer.getId(),1);
        verify(choferRepository,times(1)).save(any(Chofer.class));

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

    @Test
    void obtenerVehiculo_devueveVehiculoAsociado(){
        when(vehiculoMapper.toVehiculoResponseDTO(choferRepository.findVehiculoByChofer(chofer.getId()))).thenReturn(vehiculoResponseDTO);

        VehiculoResponseDTO vehiculoDelChofer = choferService.obtenerVehiculo(chofer.getId());

        assertEquals(vehiculoDelChofer.getId(),vehiculoResponseDTO.getId());
        assertEquals(vehiculoDelChofer.getPatente(),vehiculoResponseDTO.getPatente());
        assertEquals(vehiculoDelChofer.getAntiguedad(),vehiculoResponseDTO.getAntiguedad());
        assertEquals(vehiculoDelChofer.getModelo(),vehiculoResponseDTO.getModelo());
        assertEquals(vehiculoDelChofer.getFechaVencimiento(),vehiculoResponseDTO.getFechaVencimiento());

    }

    public void verificarNoRegistroDeChoferInvalido(){
        assertThrows(BadRequestException.class, () -> choferService.registro(choferRegistro));
        verify(choferRepository, never()).save(any(Chofer.class));
    }
}