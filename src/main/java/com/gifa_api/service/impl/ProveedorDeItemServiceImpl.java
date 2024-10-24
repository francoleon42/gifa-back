package com.gifa_api.service.impl;

import com.gifa_api.dto.proveedoresYPedidos.AsociacionProveedorDeITemRequestDTO;
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

@Service
@RequiredArgsConstructor
public class ProveedorDeItemServiceImpl implements IProveedorDeItemService {
    private final ItemDeInventarioRepository itemDeInventarioRepository;
    private final IProvedorService provedorService;
    private final IProveedorDeItemRepository iProveedorDeItemRepository;
    @Override
    public void asociarProveedorAItem(AsociacionProveedorDeITemRequestDTO asociacionProveedorDeItemRequestDTO) {
       ItemDeInventario itemDeInventario = itemDeInventarioRepository.findById(asociacionProveedorDeItemRequestDTO.getIdItem())
               .orElseThrow(() -> new NotFoundException("No se encontró el id del item para asociacion con proovedor: " + asociacionProveedorDeItemRequestDTO.getIdItem()));

        Proveedor proveedor = provedorService.obtenerByid(asociacionProveedorDeItemRequestDTO.getIdProveedor());
        ProveedorDeItem proveedorDeItem = ProveedorDeItem
                .builder()
                .itemDeInventario(itemDeInventario)
                .precio(asociacionProveedorDeItemRequestDTO.getPrecio())
                .proveedor(proveedor)
                .build();

        iProveedorDeItemRepository.save(proveedorDeItem);
    }

    @Override
    public ProveedorDeItem proveedorMasEconomico(Integer idItem) {
       return  iProveedorDeItemRepository.findProveedorMasEconomicoByItemId(idItem);
    }
}
