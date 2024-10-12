package com.gifa_api.service.impl;

import com.gifa_api.dto.item.ItemDeInventarioDTO;
import com.gifa_api.dto.item.ItemDeInventarioRequestDTO;
import com.gifa_api.dto.item.UtilizarItemDeInventarioDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IItemDeIventarioService;
import com.gifa_api.utils.mappers.ItemDeInventarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemDeInventarioServiceImpl implements IItemDeIventarioService {

    private final ItemDeInventarioRepository itemDeInventarioRepository;
    private final ItemDeInventarioMapper itemDeInventarioMapper;

    @Override
    public void registrar(ItemDeInventarioRequestDTO itemDeInventarioDTO) {
        ItemDeInventario itemDeInventario = ItemDeInventario
                .builder()
                .nombre(itemDeInventarioDTO.getNombre())
                .umbral(itemDeInventarioDTO.getUmbral())
                .stock(itemDeInventarioDTO.getStock())
                .cantCompraAutomatica(itemDeInventarioDTO.getCantCompraAutomatica())
                .build();
        itemDeInventarioRepository.save(itemDeInventario);
    }

    @Override
    public void utilizarItem(Integer itemId, UtilizarItemDeInventarioDTO utilizarItemDeInventarioDTO) {
        ItemDeInventario itemIventario = obtenerById(itemId);

        if(0 <  itemIventario.getStock() - utilizarItemDeInventarioDTO.getCantidadADisminuir() ){
            itemIventario.desminuirStock(utilizarItemDeInventarioDTO.getCantidadADisminuir());
        }
        itemDeInventarioRepository.save(itemIventario);

    }

    @Override
    public ItemDeInventario obtenerById(Integer id) {
         ItemDeInventario itemIventario = itemDeInventarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el item con id: " + id));
         return itemIventario;
    }

    @Override
    public List<ItemDeInventarioDTO> obtenerAllitems() {
        return itemDeInventarioMapper.mapToItemDeInventarioDTO(itemDeInventarioRepository.findAll());
    }


}
