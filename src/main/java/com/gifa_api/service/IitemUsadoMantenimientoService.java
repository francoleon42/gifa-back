package com.gifa_api.service;

import com.gifa_api.dto.mantenimiento.FinalizarMantenimientoDTO;
import com.gifa_api.model.Mantenimiento;

public interface IitemUsadoMantenimientoService {
    void agregaritemUtilizadoEnMantenimiento(Integer idMantenimiento , FinalizarMantenimientoDTO finalizarMantenimientoDTO);
}
