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

    @Override
    public void registrarProveedor(RegistroProveedorRequestDTO requestDTO) {
        Proveedor proveedor = Proveedor
                .builder()
                .nombre(requestDTO.getNombre())
                .email(requestDTO.getEmail())
                .build();

        iProveedorRepository.save(proveedor);
    }

    @Override
    public Proveedor obtenerByid(Integer id) {
        Proveedor proveedor = iProveedorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el proveedor con id: " + id));
        return proveedor;
    }

    @Scheduled(fixedRate = 86400000)
    public void simulacionDeAceptacionORechazoProovedor() {
        Random random = new Random();
        int decision ;
                List<Pedido> pedidos = pedidoRepository.findPedidosByEstado(EstadoPedido.PENDIENTE);
        for (Pedido pedido : pedidos) {
            decision = random.nextInt(100);
            if (decision < 30) {

                pedido.setEstadoPedido(EstadoPedido.RECHAZADO);
            }else if(decision > 30){
                pedido.setEstadoPedido(EstadoPedido.ACEPTADO);
            }
            pedidoRepository.save(pedido);
        }
    }
}
