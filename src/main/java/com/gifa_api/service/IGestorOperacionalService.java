package com.gifa_api.service;





import com.gifa_api.dto.gestorOperacional.GestorOperacionalConsumoDeLitrosPorKmRequestDTO;
import com.gifa_api.dto.gestorOperacional.GestorOperacionalPresupuestoRequestDTO;
import com.gifa_api.dto.gestorOperacional.GestorOperacionalResponseDTO;
import com.gifa_api.model.GestorOperacional;

public interface IGestorOperacionalService {
    GestorOperacionalResponseDTO obtenerGestorOperacional();

    void actualizarPresupuesto(GestorOperacionalPresupuestoRequestDTO gestorOperacionalPresupuestoRequestDTO);
    void actualizarconsumoDeLitrosPorKm(GestorOperacionalConsumoDeLitrosPorKmRequestDTO gestorOperacionalConsumoDeLitrosPorKmRequestDTO);

    GestorOperacional getGestorOperacional();
}
