package com.gifa_api.service.impl;

import com.gifa_api.dto.RegistrarItemDeInventarioDTO;
import com.gifa_api.dto.mantenimiento.RegistrarMantenimientoDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IItemDeIventarioService;
import com.gifa_api.service.IMantenimientoService;
import com.gifa_api.service.IPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemDeInventarioServiceImpl implements IItemDeIventarioService {

    private final ItemDeInventarioRepository itemDeInventarioRepository;
    private final IMantenimientoService iMantenimientoService;

    @Override
    public void registrar(RegistrarItemDeInventarioDTO registrarItemDeInventarioDTO) {
        ItemDeInventario itemDeInventario = ItemDeInventario
                .builder()
                .nombre(registrarItemDeInventarioDTO.getNombre())
                .umbral(registrarItemDeInventarioDTO.getUmbral())
                .stock(registrarItemDeInventarioDTO.getStock())
                .build();
        itemDeInventarioRepository.save(itemDeInventario);
    }

    @Override
    public void utilizarItem(Integer itemId) {
        ItemDeInventario itemIventario = obtenerById(itemId);

        if(itemIventario.getUmbral() <  itemIventario.getStock() - 1 ){
            itemIventario.desminuirStock();
        }
        itemDeInventarioRepository.save(itemIventario);

    }

    @Override
    public ItemDeInventario obtenerById(Integer id) {
         ItemDeInventario itemIventario = itemDeInventarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el item con id: " + id));
         return itemIventario;
    }


}
