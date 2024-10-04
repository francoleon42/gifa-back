package com.gifa_api.service.impl;

import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.model.ItemUsadoMantenimiento;
import com.gifa_api.model.Mantenimiento;
import com.gifa_api.repository.IMantenimientoRepository;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.repository.ItemUsadoMantenimientoRepository;
import com.gifa_api.service.IitemUsadoMantenimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemUsadoMantenimientoServiceImpl implements IitemUsadoMantenimientoService{
    private final ItemDeInventarioRepository itemDeInventarioRepository;
    private final ItemUsadoMantenimientoRepository itemUsadoMantenimientoRepository;
    private final IMantenimientoRepository  iMantenimientoRepository;

    @Override
    public void agregaritemUtilizadoEnMantenimiento(Integer idItem, Integer idMantenimiento, Integer cantidad) {
        ItemDeInventario itemDeInventario= itemDeInventarioRepository.findById(idItem)
                .orElseThrow(() -> new NotFoundException("No se encontró el item con id: " + idItem));

        Mantenimiento mantenimiento= iMantenimientoRepository.findById(idMantenimiento)
                .orElseThrow(() -> new NotFoundException("No se encontró el mantenimiento con id: " + idMantenimiento));

        ItemUsadoMantenimiento itemUsadoMantenimiento = ItemUsadoMantenimiento
                .builder()
                .cantidad(cantidad)
                .itemDeInventario(itemDeInventario)
                .mantenimiento(mantenimiento)
                .build();
        itemUsadoMantenimientoRepository.save(itemUsadoMantenimiento);
    }
}