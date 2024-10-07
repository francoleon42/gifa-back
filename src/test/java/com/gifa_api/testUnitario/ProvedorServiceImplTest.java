package com.gifa_api.testUnitario;

import com.gifa_api.dto.proveedoresYPedidos.RegistroProveedorRequestDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.utils.enums.EstadoPedido;
import com.gifa_api.model.Pedido;
import com.gifa_api.model.Proveedor;
import com.gifa_api.repository.IPedidoRepository;
import com.gifa_api.repository.IProveedorRepository;
import com.gifa_api.service.impl.ProvedorServiceImpl;
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

    @Test
    void registrarProveedor_debeGuardarProveedor() {
        RegistroProveedorRequestDTO requestDTO = new RegistroProveedorRequestDTO("Proveedor1", "email@proveedor.com");

        provedorService.registrarProveedor(requestDTO);


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
    void obtenerByid_debeLanzarNotFoundExceptionSiNoExiste() {
        when(proveedorRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> provedorService.obtenerByid(1));
        assertEquals("No se encontró el proveedor con id: 1", exception.getMessage());
        verify(proveedorRepository, times(1)).findById(1);
    }

    @Test
    void simulacionDeAceptacionORechazoProovedor_debeActualizarEstadoPedidos() {
        // Arrange
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
}
