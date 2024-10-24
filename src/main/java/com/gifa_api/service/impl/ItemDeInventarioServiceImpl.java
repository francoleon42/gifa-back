package com.gifa_api.service.impl;

import com.gifa_api.dto.item.ItemDeInventarioDTO;
import com.gifa_api.dto.item.ItemDeInventarioRequestDTO;
import com.gifa_api.dto.item.UtilizarItemDeInventarioDTO;
import com.gifa_api.exception.BadRoleException;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IItemDeIventarioService;
import com.gifa_api.service.IPedidoService;
import com.gifa_api.utils.mappers.ItemDeInventarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemDeInventarioServiceImpl implements IItemDeIventarioService {

    private final ItemDeInventarioRepository itemDeInventarioRepository;
    private final ItemDeInventarioMapper itemDeInventarioMapper;
    private final IPedidoService pedidoService;

    @Override
    public void registrar(ItemDeInventarioRequestDTO itemDeInventarioDTO) {
        // Validar el DTO
        validarItemDeInventarioRequestDTO(itemDeInventarioDTO);
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

        if(0 <=  itemIventario.getStock() - utilizarItemDeInventarioDTO.getCantidadADisminuir() ){
            itemIventario.desminuirStock(utilizarItemDeInventarioDTO.getCantidadADisminuir());
        }else{
            throw new BadRoleException("Stock insuficiente esta en cero");
        }
        itemDeInventarioRepository.save(itemIventario);
        revisarNecesidadDePedido(itemIventario.getId());
    }
    private void revisarNecesidadDePedido(Integer idItemDeInventario){
        pedidoService.hacerPedidos(idItemDeInventario);
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

    private void validarItemDeInventarioRequestDTO(ItemDeInventarioRequestDTO itemDeInventarioDTO) {
        if (itemDeInventarioDTO.getNombre() == null || itemDeInventarioDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del ítem no puede estar vacío.");
        }
        if (itemDeInventarioDTO.getUmbral() == null || itemDeInventarioDTO.getUmbral() < 0) {
            throw new IllegalArgumentException("El umbral debe ser mayor o igual a cero.");
        }
        if (itemDeInventarioDTO.getStock() == null || itemDeInventarioDTO.getStock() < 0) {
            throw new IllegalArgumentException("El stock debe ser mayor o igual a cero.");
        }
        if (itemDeInventarioDTO.getCantCompraAutomatica() == null || itemDeInventarioDTO.getCantCompraAutomatica() < 0) {
            throw new IllegalArgumentException("La cantidad de compra automática debe ser mayor o igual a cero.");
        }
    }

}
