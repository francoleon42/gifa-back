package com.gifa_api.service;

import com.gifa_api.model.Mantenimiento;

import java.util.List;

public interface IMantenimientoService {
    List<Mantenimiento> verMantenimientos(Integer id);
    void registrarMantenimientoManualmente(Integer id);
    void FinalizarMantenimiento(Integer id);

}
