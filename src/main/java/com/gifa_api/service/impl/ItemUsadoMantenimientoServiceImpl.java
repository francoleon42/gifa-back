package com.gifa_api.service.impl;

import com.gifa_api.dto.mantenimiento.FinalizarMantenimientoDTO;
import com.gifa_api.dto.mantenimiento.ItemUtilizadoRequestDTO;
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
    public void agregaritemUtilizadoEnMantenimiento(Integer idMantenimiento, FinalizarMantenimientoDTO finalizarMantenimientoDTO) {
        Mantenimiento mantenimiento= iMantenimientoRepository.findById(idMantenimiento)
                .orElseThrow(() -> new NotFoundException("No se encontró el mantenimiento con id: " + idMantenimiento));

        for(ItemUtilizadoRequestDTO item : finalizarMantenimientoDTO.getItems()){
            ItemDeInventario itemDeInventario= itemDeInventarioRepository.findById(item.getIdItem())
                    .orElseThrow(() -> new NotFoundException("No se encontró el item con id: " + item.getIdItem()));

            ItemUsadoMantenimiento itemUsadoMantenimiento = ItemUsadoMantenimiento
                    .builder()
                    .cantidad(item.getCantidad())
                    .itemDeInventario(itemDeInventario)
                    .mantenimiento(mantenimiento)
                    .build();

            itemUsadoMantenimientoRepository.save(itemUsadoMantenimiento);
        }
    }
}