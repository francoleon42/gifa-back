package com.gifa_api.service.impl;


import com.gifa_api.dto.proveedoresYPedidos.GestorDePedidosDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.GestorDePedidos;
import com.gifa_api.repository.IGestorDePedidosRepository;
import com.gifa_api.service.IGestorDePedidosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GestorDePedidosServiceImpl implements IGestorDePedidosService {
    private final IGestorDePedidosRepository IGestorDePedidosRepository;


    @Override
    public GestorDePedidos obtenerGestorDePedidos() {
        GestorDePedidos gestorDePedidos = IGestorDePedidosRepository.findById(1)
                .orElseThrow(() -> new NotFoundException("No se encontró el gestor de pedido con id: " + 1 ));
        return gestorDePedidos;
    }

    @Override
    public void actualizarGestorDePedidos(GestorDePedidosDTO gestorDePedidosDTO) {
        GestorDePedidos gestorDePedidos = obtenerGestorDePedidos();
        gestorDePedidos.setCantDePedidoAutomatico(gestorDePedidosDTO.getCantDePedidoAutomatico());
        gestorDePedidos.setPresupuesto(gestorDePedidosDTO.getPresupuesto());
    }


}
