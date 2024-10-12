package com.gifa_api.service;

import com.gifa_api.dto.chofer.AsignarChoferDTO;
import com.gifa_api.dto.chofer.ChoferEditDTO;
import com.gifa_api.dto.chofer.ChoferRegistroDTO;
import com.gifa_api.dto.chofer.ChoferResponseDTO;

import java.util.List;

public interface IChoferService {
    void registro(ChoferRegistroDTO choferRegistroDTO);
    void asignarVehiculo(AsignarChoferDTO asignarChoferDTO);
    void habilitar(ChoferEditDTO choferEditDTO);
    void inhabilitar(ChoferEditDTO choferEditDTO);
    List<ChoferResponseDTO> obtenerAll();
}
