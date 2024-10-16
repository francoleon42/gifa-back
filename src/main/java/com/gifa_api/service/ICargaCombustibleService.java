package com.gifa_api.service;

import com.gifa_api.dto.gestionDeCombustilble.CargaCombustibleRequestDTO;

import java.time.LocalDateTime;

public interface ICargaCombustibleService {

    void cargarCombustible(CargaCombustibleRequestDTO cargaCombustibleRequestDTO);
    double combustibleCargadoEn(String numeroTarjeta, LocalDateTime fecha);
}
