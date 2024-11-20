package com.gifa_api.service.impl;

import com.gifa_api.dto.pedido.CrearPedidoDTO;
import com.gifa_api.dto.pedido.PedidoResponseDTO;
import com.gifa_api.exception.BadRequestException;
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
    public void crearPedidoManual(CrearPedidoDTO crearPedidoDTO) {
        validarCrearPedidoDTO(crearPedidoDTO);

        ItemDeInventario item = obtenerItemPorId(crearPedidoDTO.getIdItem());

        GestorOperacional gestorOperacional = gestorOperacionalService.getGestorOperacional();
        int cantidad = item.getCantCompraAutomatica() + item.getUmbral();
        ProveedorDeItem proveedorMasEconomico = proveedorDeItemService.proveedorMasEconomico(item.getId());
        if(proveedorMasEconomico == null){
            crearPedido(item, cantidad, EstadoPedido.SIN_PROVEEDOR, "Solicitud de stock automática");
        }else{
            EstadoPedido estadoPedido = calcularEstadoPedidoPorPresupuesto(cantidad, proveedorMasEconomico.getPrecio(), gestorOperacional.getPresupuesto());
            crearPedido(item, crearPedidoDTO.getCantidad(), estadoPedido, crearPedidoDTO.getMotivo());
        }

    }

    private void crearPedido(ItemDeInventario item, int cantidad, EstadoPedido estadoPedido, String motivo) {
        Pedido pedido = Pedido.builder()
                .estadoPedido(estadoPedido)
                .item(item)
                .cantidad(cantidad)
                .fecha(LocalDate.now())
                .motivo(motivo)
                .build();
        pedidoRepository.save(pedido);
    }


    private ItemDeInventario obtenerItemPorId(Integer idItem) {
        return itemDeInventarioRepository.findById(idItem)
                .orElseThrow(() -> new NotFoundException("No se encontró el item con id: " + idItem));
    }

    @Override
    public void hacerPedidoAutomatico(Integer idItem) {
        ItemDeInventario item = obtenerItemPorId(idItem);

        GestorOperacional gestorOperacional = gestorOperacionalService.getGestorOperacional();
        int cantidad = item.getCantCompraAutomatica() + item.getUmbral();
        ProveedorDeItem proveedorMasEconomico = proveedorDeItemService.proveedorMasEconomico(item.getId());

        if (item.getUmbral() > item.getStock()) {
            if(proveedorMasEconomico == null){
                crearPedido(item, cantidad, EstadoPedido.SIN_PROVEEDOR, "Solicitud de stock automática");
            }else{
                EstadoPedido estadoPedido = calcularEstadoPedidoPorPresupuesto(cantidad, proveedorMasEconomico.getPrecio(), gestorOperacional.getPresupuesto());
                crearPedido(item, cantidad, estadoPedido, "Solicitud de stock automática");
            }
        }
    }
    private EstadoPedido calcularEstadoPedidoPorPresupuesto(int cantidad, double precioProveedor, double presupuesto) {
        return (precioProveedor * cantidad) < presupuesto ? EstadoPedido.PENDIENTE : EstadoPedido.PRESUPUESTO_INSUFICIENTE;
    }

    @Override
    public List<PedidoResponseDTO> obtenerPedidosAceptados() {
        return  pedidosMapper.mapToPedidoDTO(pedidoRepository.findPedidosByEstado(EstadoPedido.ACEPTADO));
    }

    @Override
    public List<PedidoResponseDTO> obtenerPedidosRechazadosYpendientesYpresupuestoInsuficienteSinProveedor() {
        return pedidosMapper.mapToPedidoDTO(pedidoRepository.findPedidosByCuatroEstados(EstadoPedido.PENDIENTE,EstadoPedido.RECHAZADO
                ,EstadoPedido.PRESUPUESTO_INSUFICIENTE, EstadoPedido.SIN_PROVEEDOR));

    }

    @Override
    public void confirmarPedidoRecibido(Integer idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new NotFoundException("No se encontró el pedido con id: " + idPedido));

        if(pedido.getEstadoPedido().equals(EstadoPedido.ACEPTADO)) {
            pedido.getItem().aumentarStock(pedido.getCantidad());
            pedido.setEstadoPedido(EstadoPedido.FINALIZADO);
            pedidoRepository.save(pedido);
        }else{
             throw new BadRequestException("No se pudo confirmar el pedido recibido.");
        }
    }

    @Override
    public List<PedidoResponseDTO> obtenerPedidos() {
        return pedidosMapper.mapToPedidoDTO(pedidoRepository.findAll());
    }

    private void validarCrearPedidoDTO(CrearPedidoDTO crearPedidoDTO) {
        if (crearPedidoDTO.getCantidad() == null || crearPedidoDTO.getCantidad() <= 0) {
            throw new BadRequestException("La cantidad debe ser mayor a cero.");
        }
        if (crearPedidoDTO.getMotivo() == null || crearPedidoDTO.getMotivo().trim().isEmpty()) {
            throw new BadRequestException("El motivo no puede estar vacío.");
        }
    }
}



