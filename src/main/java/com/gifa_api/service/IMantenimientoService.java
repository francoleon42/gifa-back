package com.gifa_api.service;

import com.gifa_api.dto.mantenimiento.AsignarMantenimientoRequestDTO;
import com.gifa_api.dto.mantenimiento.MantenimientosPendientesResponseDTO;
import com.gifa_api.dto.mantenimiento.MantenimientosResponseDTO;
import com.gifa_api.dto.mantenimiento.RegistrarMantenimientoDTO;
import com.gifa_api.model.Mantenimiento;

import java.util.List;

public interface IMantenimientoService {

    void crearMantenimiento(RegistrarMantenimientoDTO registrarMantenimientoDTO);
    List<Mantenimiento> verMantenimientosPorVehiculo(Integer id);

    void registrarMantenimientoManualmente(Integer id);

    MantenimientosPendientesResponseDTO verMantenimientosPendientes();

    MantenimientosResponseDTO verMantenimientos();

    void asignarMantenimiento(Integer mantenimientoId, AsignarMantenimientoRequestDTO asignarMantenimientoRequestDTO);

    void finalizarMantenimiento(Integer mantenimientoId);

}
