package com.gifa_api.service;

import com.gifa_api.model.Mantenimiento;

public interface IitemUsadoMantenimientoService {
    void agregaritemUtilizadoEnMantenimiento(Integer idItemIventario , Integer idMantenimiento , Integer cantidad);
}
