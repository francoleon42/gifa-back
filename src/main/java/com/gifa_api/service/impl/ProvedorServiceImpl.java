package com.gifa_api.service.impl;

import com.gifa_api.dto.proveedoresYPedidos.RegistroProveedorRequestDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.*;
import com.gifa_api.repository.IPedidoRepository;
import com.gifa_api.repository.IProveedorRepository;
import com.gifa_api.service.IProvedorService;
import com.gifa_api.utils.enums.EstadoPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ProvedorServiceImpl implements IProvedorService {
    private final IProveedorRepository iProveedorRepository;
    private final IPedidoRepository pedidoRepository;
    private final Random random; // Inyecta Random como dependencia

    @Override
    public void registrarProveedor(RegistroProveedorRequestDTO requestDTO) {
        Proveedor proveedor = Proveedor.builder()
                .nombre(requestDTO.getNombre())
                .email(requestDTO.getEmail())
                .build();

        iProveedorRepository.save(proveedor);
    }

    @Override
    public Proveedor obtenerByid(Integer id) {
        return iProveedorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el proveedor con id: " + id));
    }

    @Scheduled(fixedRate = 86400)
    public void simulacionDeAceptacionORechazoProovedor() {
        List<Pedido> pedidos = pedidoRepository.findPedidosByEstado(EstadoPedido.PENDIENTE);
        for (Pedido pedido : pedidos) {
            int decision = random.nextInt(100);  // Utiliza el Random inyectado
            if (decision < 30) {
                pedido.setEstadoPedido(EstadoPedido.RECHAZADO);
            } else if (decision > 30) {
                pedido.setEstadoPedido(EstadoPedido.ACEPTADO);
            }
            pedidoRepository.save(pedido);
        }
    }
}

