package com.gifa_api.service.impl;



import com.gifa_api.dto.gestorOperacional.GestorOperacionalConsumoDeLitrosPorKmRequestDTO;
import com.gifa_api.dto.gestorOperacional.GestorOperacionalPresupuestoRequestDTO;
import com.gifa_api.dto.gestorOperacional.GestorOperacionalResponseDTO;
import com.gifa_api.exception.NotFoundException;

import com.gifa_api.model.GestorOperacional;

import com.gifa_api.repository.IGestorOperacionalRepository;

import com.gifa_api.service.IGestorOperacionalService;

import com.gifa_api.utils.mappers.GestorOperacionalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GestorOperacionalServiceImpl implements IGestorOperacionalService {
    private final IGestorOperacionalRepository gestorOperacionalRepository;
    private final GestorOperacionalMapper gestorOperacionalMapper;


    @Override
    public GestorOperacionalResponseDTO obtenerGestorOperacional() {
        return gestorOperacionalMapper.obtenerGestorOperacionalDTO(getGestorOperacional());
    }




    @Override
    public GestorOperacional getGestorOperacional() {
        GestorOperacional gestorDePedidos = gestorOperacionalRepository.findById(1)
                .orElseThrow(() -> new NotFoundException("No se encontró el gestor de pedido con id: " + 1));
        return gestorDePedidos;
    }


    @Override
    public void actualizarPresupuesto(GestorOperacionalPresupuestoRequestDTO gestorOperacionalPresupuestoRequestDTO) {
        GestorOperacional gestorOperacional = getGestorOperacional();
        gestorOperacional.setPresupuesto(gestorOperacionalPresupuestoRequestDTO.getPresupuesto());
        gestorOperacionalRepository.save(gestorOperacional);
    }
    @Override
    public void actualizarconsumoDeLitrosPorKm(GestorOperacionalConsumoDeLitrosPorKmRequestDTO gestorOperacionalConsumoDeLitrosPorKmRequestDTO) {
        GestorOperacional gestorOperacional = getGestorOperacional();
        gestorOperacional.setConsumoDeLitrosPorKm(gestorOperacionalConsumoDeLitrosPorKmRequestDTO.getConsumoDeLitrosPorKm());
        gestorOperacionalRepository.save(gestorOperacional);
    }


}
