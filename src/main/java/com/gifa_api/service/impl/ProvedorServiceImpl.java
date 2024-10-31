package com.gifa_api.service.impl;

import com.gifa_api.dto.proveedor.ProveedorResponseDTO;
import com.gifa_api.dto.proveedor.RegistroProveedorRequestDTO;
import com.gifa_api.exception.BadRequestException;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.*;
import com.gifa_api.repository.IPedidoRepository;
import com.gifa_api.repository.IProveedorRepository;
import com.gifa_api.service.IProvedorService;
import com.gifa_api.utils.enums.EstadoPedido;
import com.gifa_api.utils.mappers.ProveedorMapper;
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
    private final ProveedorMapper proveedorMapper;

    @Override
    public void registrarProveedor(RegistroProveedorRequestDTO requestDTO) {
        // Validar el DTO
        validarRegistroProveedorDTO(requestDTO);

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

    @Override
    public List<ProveedorResponseDTO> obtenerProveedores() {
        return proveedorMapper.mapToProveedorResponseDTO(iProveedorRepository.findAll());
    }

    @Scheduled(fixedRate = 86400000)
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
    private void validarRegistroProveedorDTO(RegistroProveedorRequestDTO requestDTO) {
        if (requestDTO.getNombre() == null || requestDTO.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre no puede estar vacío.");
        }
        if (!requestDTO.getNombre().matches("^[a-zA-Z\\s]+$")) {
            throw new BadRequestException("El nombre no debe contener dígitos ni caracteres especiales.");
        }
        if (requestDTO.getEmail() == null || requestDTO.getEmail().trim().isEmpty()) {
            throw new BadRequestException("El email no puede estar vacío.");
        }
        if (!requestDTO.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new BadRequestException("El formato del email no es válido.");
        }
    }
}

