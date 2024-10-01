package com.gifa_api.service.impl;

import com.gifa_api.dto.RegistrarItemDeInventarioDTO;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IItemDeIventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemDeInventarioServiceImpl implements IItemDeIventarioService {

    private final ItemDeInventarioRepository itemDeInventarioRepository;

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
    public void utilizarItem(Integer id) {
        Optional<ItemDeInventario> itemIventario = itemDeInventarioRepository.findById(id);
        if(itemIventario.get().getUmbral() <  itemIventario.get().getStock() - 1 ){
            itemIventario.get().desminuirStock();
        }
        itemDeInventarioRepository.save(itemIventario.get());
    }
}
