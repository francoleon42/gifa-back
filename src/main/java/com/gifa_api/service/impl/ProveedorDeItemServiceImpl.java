package com.gifa_api.service.impl;

import com.gifa_api.dto.proveedoresYPedidos.AsociacionProveedorDeITemDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.model.Proveedor;
import com.gifa_api.model.ProveedorDeItem;
import com.gifa_api.repository.IProveedorDeItemRepository;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IProvedorService;
import com.gifa_api.service.IProveedorDeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProveedorDeItemServiceImpl implements IProveedorDeItemService {
    private final ItemDeInventarioRepository itemDeInventarioRepository;
    private final IProvedorService provedorService;
    private final IProveedorDeItemRepository iProveedorDeItemRepository;
    @Override
    public void asociarProveedorAItem(AsociacionProveedorDeITemDTO asociacionProveedorDeItemDTO) {
       ItemDeInventario itemDeInventario = itemDeInventarioRepository.findById(asociacionProveedorDeItemDTO.getIdItem())
               .orElseThrow(() -> new NotFoundException("No se encontró el id del item para asociacion con proovedor: " + asociacionProveedorDeItemDTO.getIdItem()));

        Proveedor proveedor = provedorService.obtenerByid(asociacionProveedorDeItemDTO.getIdProveedor());
        ProveedorDeItem proveedorDeItem = ProveedorDeItem
                .builder()
                .itemDeInventario(itemDeInventario)
                .precio(asociacionProveedorDeItemDTO.getPrecio())
                .proveedor(proveedor)
                .build();

        iProveedorDeItemRepository.save(proveedorDeItem);
    }

    @Override
    public ProveedorDeItem proveedorMasEconomico(Integer idItem) {
       return  iProveedorDeItemRepository.findProveedorMasEconomicoByItemId(idItem);
    }
}
