package com.gifa_api.service;

import com.gifa_api.dto.chofer.AsignarChoferDTO;
import com.gifa_api.dto.chofer.ChoferRegistroDTO;
import com.gifa_api.dto.chofer.ChoferResponseDTO;
import com.gifa_api.model.Chofer;

import java.util.List;

public interface IChoferService {
    void registro(ChoferRegistroDTO choferRegistroDTO);
    void asignarVehiculo(AsignarChoferDTO asignarChoferDTO);
    void inhabilitarUsuarioChofer(Integer idUsuario);
    List<ChoferResponseDTO> obtenerAll();

}
