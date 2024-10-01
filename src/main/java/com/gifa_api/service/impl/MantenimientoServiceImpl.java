package com.gifa_api.service.impl;

import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.ItemDeInventario;
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
    public void finalizarMantenimiento(Integer id) {
        Mantenimiento mantenimiento = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el mantenimiento con id: " + id));

        mantenimiento.finalizar();
    }
}
