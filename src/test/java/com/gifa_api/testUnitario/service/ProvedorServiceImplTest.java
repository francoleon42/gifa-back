package com.gifa_api.testUnitario.service;

import com.gifa_api.dto.proveedor.RegistroProveedorRequestDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.utils.enums.EstadoPedido;
import com.gifa_api.model.Pedido;
import com.gifa_api.model.Proveedor;
import com.gifa_api.repository.IPedidoRepository;
import com.gifa_api.repository.IProveedorRepository;
import com.gifa_api.service.impl.ProvedorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ProvedorServiceImplTest {

    @Mock
    private IProveedorRepository proveedorRepository;

    @Mock
    private IPedidoRepository pedidoRepository;

    @Mock
    private Random random;

    @InjectMocks
    private ProvedorServiceImpl provedorService;

    private RegistroProveedorRequestDTO proveedorRequestDTO;

    @BeforeEach
    void setUp(){
        proveedorRequestDTO = RegistroProveedorRequestDTO.builder()
                .nombre("nombre")
                .email("emailvalido@gmail.com")
                .build();
    }

    @Test
    void nombreNoPuedeSerNull(){
        proveedorRequestDTO.setNombre(null);
        verificarQueNoseRegistreProveedorInvalido();
    }

    @Test
    void nombreNoPuedeEstarVacio(){
        proveedorRequestDTO.setNombre("");
        verificarQueNoseRegistreProveedorInvalido();
    }

    @Test
    void nombreNoPuedeTenerDigitos(){
        proveedorRequestDTO.setNombre("fede1");
        verificarQueNoseRegistreProveedorInvalido();
    }

    @Test
    void nombreNoPuedeTenerCaracteresEspeciales(){
        proveedorRequestDTO.setNombre("fede#");
        verificarQueNoseRegistreProveedorInvalido();
    }

    @Test
    void emailNoPuedeEstarNull(){
        proveedorRequestDTO.setEmail(null);
        verificarQueNoseRegistreProveedorInvalido();
    }

    @Test
    void emailNoPuedeEstarVacio(){
        proveedorRequestDTO.setEmail("");
        verificarQueNoseRegistreProveedorInvalido();
    }

    @Test
    void emailNoDebeTenerFormatoInvalido_FaltaArroba() {
        proveedorRequestDTO.setEmail("emailinvalidogmail.com");
        verificarQueNoseRegistreProveedorInvalido();
    }

    @Test
    void emailNoDebeTenerFormatoInvalido_DominioFaltante() {
        proveedorRequestDTO.setEmail("emailvalido@.com");
        verificarQueNoseRegistreProveedorInvalido();
    }

    @Test
    void emailNoDebeTenerFormatoInvalido_ExtensionCorta() {
        proveedorRequestDTO.setEmail("emailvalido@gmail.c");
        verificarQueNoseRegistreProveedorInvalido();
    }

    @Test
    void emailNoDebeTenerFormatoInvalido_ExtensionLarga() {
        proveedorRequestDTO.setEmail("emailvalido@gmail.corporate");
        verificarQueNoseRegistreProveedorInvalido();
    }

    @Test
    void emailNoDebeTenerFormatoInvalido_CaracteresEspecialesAntesDelArroba() {
        proveedorRequestDTO.setEmail("nombre#usuario@gmail.com");
        verificarQueNoseRegistreProveedorInvalido();
    }

    @Test
    void emailNoDebeTenerFormatoInvalido_CaracteresEspecialesEnDominio() {
        proveedorRequestDTO.setEmail("nombre@dominio#.com");
        verificarQueNoseRegistreProveedorInvalido();
    }

    @Test
    void emailNoDebeTenerFormatoInvalido_SinParteLocal() {
        proveedorRequestDTO.setEmail("@gmail.com");
        verificarQueNoseRegistreProveedorInvalido();
    }

    @Test
    void emailNoDebeTenerFormatoInvalido_EspaciosEnBlanco() {
        proveedorRequestDTO.setEmail("email valido@gmail.com");
        verificarQueNoseRegistreProveedorInvalido();
    }

    @Test
    void obtenerByid_debeLanzarNotFoundExceptionSiNoExiste() {
        when(proveedorRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> provedorService.obtenerByid(1));

        verify(proveedorRepository, times(1)).findById(1);

    }

    @Test
    void registrarProveedor_debeGuardarProveedor() {
        provedorService.registrarProveedor(proveedorRequestDTO);

        verify(proveedorRepository, times(1)).save(any(Proveedor.class));
    }

    @Test
    void obtenerByid_debeDevolverProveedorSiExiste() {
        // Arrange
        Proveedor proveedor = Proveedor.builder()
                .id(1)
                .nombre("Proveedor1")
                .build();
        when(proveedorRepository.findById(1)).thenReturn(Optional.of(proveedor));

        Proveedor resultado = provedorService.obtenerByid(1);

        assertEquals(proveedor, resultado);
        verify(proveedorRepository, times(1)).findById(1);
    }

    @Test
    void simulacionDeAceptacionORechazoProovedor_debeActualizarEstadoPedidos() {
        Pedido pedido1 = new Pedido();
        pedido1.setEstadoPedido(EstadoPedido.PENDIENTE);
        Pedido pedido2 = new Pedido();
        pedido2.setEstadoPedido(EstadoPedido.PENDIENTE);

        List<Pedido> pedidos = Arrays.asList(pedido1, pedido2);
        when(pedidoRepository.findPedidosByEstado(EstadoPedido.PENDIENTE)).thenReturn(pedidos);

        when(random.nextInt(100)).thenReturn(25, 50); // El primero debe ser rechazado (< 30), el segundo aceptado (> 30)

        provedorService.simulacionDeAceptacionORechazoProovedor();

        assertEquals(EstadoPedido.RECHAZADO, pedido1.getEstadoPedido()); // Primer pedido debe ser rechazado
        assertEquals(EstadoPedido.ACEPTADO, pedido2.getEstadoPedido());  // Segundo pedido debe ser aceptado
        verify(pedidoRepository, times(2)).save(any(Pedido.class));
    }

    public void verificarQueNoseRegistreProveedorInvalido(){
        assertThrows(IllegalArgumentException.class,() -> provedorService.registrarProveedor(proveedorRequestDTO));
        verify(proveedorRepository, never() ).save(any(Proveedor.class));
    }

}
