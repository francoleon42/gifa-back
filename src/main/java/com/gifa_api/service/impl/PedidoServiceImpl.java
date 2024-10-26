package com.gifa_api.service.impl;

import com.gifa_api.dto.pedido.CrearPedidoDTO;
import com.gifa_api.dto.pedido.PedidoResponseDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.*;
import com.gifa_api.repository.IPedidoRepository;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IGestorOperacionalService;
import com.gifa_api.service.IPedidoService;
import com.gifa_api.service.IProveedorDeItemService;
import com.gifa_api.utils.enums.EstadoPedido;
import com.gifa_api.utils.mappers.PedidosMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements IPedidoService {
    private final IPedidoRepository pedidoRepository;
    private final ItemDeInventarioRepository itemDeInventarioRepository;
    private final IGestorOperacionalService gestorOperacionalService;
    private final IProveedorDeItemService proveedorDeItemService;
    private final PedidosMapper pedidosMapper;

    @Override
    public void createPedido(CrearPedidoDTO crearPedidoDTO) {
        // Validar el DTO
        validarCrearPedidoDTO(crearPedidoDTO);
        ItemDeInventario item = itemDeInventarioRepository.findById(crearPedidoDTO.getIdItem())
                .orElseThrow(() -> new NotFoundException("No se encontró el item con id: " + crearPedidoDTO.getIdItem()));
        Pedido pedido = Pedido
                .builder()
                .estadoPedido(EstadoPedido.PENDIENTE)
                .item(item)
                .cantidad(crearPedidoDTO.getCantidad())
                .fecha(LocalDate.now())
                .motivo(crearPedidoDTO.getMotivo())
                .build();
        pedidoRepository.save(pedido);

    }


    @Override
    public void hacerPedidos(Integer idItem) {
        ItemDeInventario item = itemDeInventarioRepository.findById(idItem)
                .orElseThrow(() -> new NotFoundException("No se encontró el item con id: " + idItem));

        GestorOperacional gestorOperacional = gestorOperacionalService.getGestorOperacional();
        int cantidad = item.getCantCompraAutomatica() + item.getUmbral();
        ProveedorDeItem proveerDeItemMasEconomico = proveedorDeItemService.proveedorMasEconomico(item.getId());

        if (item.getUmbral() > item.getStock()) {
            if ((proveerDeItemMasEconomico.getPrecio() * cantidad) < gestorOperacional.getPresupuesto()) {
                CrearPedidoDTO pedidoManualDTO = CrearPedidoDTO
                        .builder()
                        .idItem(item.getId())
                        .cantidad(cantidad)
                        .motivo("Solcitud de stock automatica")
                        .build();
                createPedido(pedidoManualDTO);
            }else{
                throw new RuntimeException("Presupuesto insuficiente para realizar el pedido.");
            }
        }
    }

    @Override
    public List<PedidoResponseDTO> obtenerPedidosAceptados() {
        return  pedidosMapper.mapToPedidoDTO(pedidoRepository.findPedidosByEstado(EstadoPedido.ACEPTADO));
    }

    @Override
    public List<PedidoResponseDTO> obtenerPedidosRechazadosYpendientes() {
        return pedidosMapper.mapToPedidoDTO(pedidoRepository.findPedidosByDosEstados(EstadoPedido.PENDIENTE,EstadoPedido.RECHAZADO));
    }

    @Override
    public void confirmarPedidoRecibido(Integer idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new NotFoundException("No se encontró el pedido con id: " + idPedido));

        if(pedido.getEstadoPedido().equals(EstadoPedido.ACEPTADO)) {
            pedido.getItem().aumentarStock(pedido.getCantidad());
            pedido.setEstadoPedido(EstadoPedido.FINALIZADO);
        }else{
            throw new RuntimeException("No se pudo confirmar el pedido recibido.");
        }

        pedidoRepository.save(pedido);
    }

    @Override
    public List<PedidoResponseDTO> obtenerPedidos() {
        return pedidosMapper.mapToPedidoDTO(pedidoRepository.findAll());
    }

    private void validarCrearPedidoDTO(CrearPedidoDTO crearPedidoDTO) {
        if (crearPedidoDTO.getCantidad() == null || crearPedidoDTO.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        if (crearPedidoDTO.getMotivo() == null || crearPedidoDTO.getMotivo().trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo no puede estar vacío.");
        }
    }
}



