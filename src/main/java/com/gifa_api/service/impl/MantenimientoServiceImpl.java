package com.gifa_api.service.impl;

import com.gifa_api.model.Mantenimiento;
import com.gifa_api.repository.MantenimientoRepository;
import com.gifa_api.service.IMantenimientoService;

import java.util.List;

public class MantenimientoServiceImpl implements IMantenimientoService {
    MantenimientoRepository mantenimientoRepository;

    @Override
    public List<Mantenimiento> verMantenimientos(Integer id) {
        return mantenimientoRepository.findByVehiculoId(id);
    }

    @Override
    public void registrarMantenimientoManualmente(Integer id) {

    }

    @Override
    public void FinalizarMantenimiento(Integer id) {

    }
}
