package com.gifa_api.service.impl;

import com.gifa_api.dto.proveedoresYPedidos.ActualizarGestorDePedidosDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.GestorDePedidos;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.repository.GestorDePedidosRepository;
import com.gifa_api.service.IGestorDePedidosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class GestorDePedidosServiceImpl implements IGestorDePedidosService {
    private final GestorDePedidosRepository gestorDePedidosRepository;

    @Override
    public GestorDePedidos obtenerGestorDePedidos() {
        return gestorDePedidosRepository.findById(1)
                .orElseGet(() -> {
                    GestorDePedidos nuevoGestor = GestorDePedidos.builder()
                            .presupuesto(0.0) // Valor inicial
                            .cantDePedidoAutomatico(30) // Valor inicial
                            .build();
                    return gestorDePedidosRepository.save(nuevoGestor);
                });
    }

    @Override
    public void actualizarGestorDePedidos(ActualizarGestorDePedidosDTO actualizarGestorDePedidosDTO) {
        GestorDePedidos gestorDePedidos = obtenerElGestor();
        gestorDePedidos.setCantDePedidoAutomatico(actualizarGestorDePedidosDTO.getCantDePedidoAutomatico());
        gestorDePedidos.setPresupuesto(actualizarGestorDePedidosDTO.getPresupuesto());
    }

    public GestorDePedidos obtenerElGestor() {
        GestorDePedidos gestorDePedidos = gestorDePedidosRepository.findById(1)
                .orElseThrow(() -> new NotFoundException("No se encontró el gestor de pedido con id: " + 1 ));
        return gestorDePedidos;
    }
}
