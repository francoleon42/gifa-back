package com.gifa_api.service.impl;

import com.gifa_api.dto.RegistrarItemDeInventarioDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IItemDeIventarioService;
import com.gifa_api.service.IMantenimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        ItemDeInventario itemIventario = itemDeInventarioRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("No se encontró el item con id: " + itemId));

        if(itemIventario.getUmbral() <  itemIventario.getStock() - 1 ){
            itemIventario.desminuirStock();
        }

        itemDeInventarioRepository.save(itemIventario);

    }
}
