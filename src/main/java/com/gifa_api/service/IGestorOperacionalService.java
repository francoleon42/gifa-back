package com.gifa_api.service;




import com.gifa_api.dto.GestorOperacionalDTO;
import com.gifa_api.model.GestorOperacional;

public interface IGestorOperacionalService {
    GestorOperacionalDTO obtenerGestorOperacional();

    void actualizarGestorOperacional(GestorOperacionalDTO gestorDePedidosDTO);

    GestorOperacional getGestorOperacional();
}
