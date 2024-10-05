package com.gifa_api.service.impl;

import com.gifa_api.dto.proveedoresYPedidos.AsociacionProveedorDeITemDTO;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.model.Proveedor;
import com.gifa_api.model.ProveedorDeItem;
import com.gifa_api.repository.IProveedorDeParteRepository;
import com.gifa_api.service.IItemDeIventarioService;
import com.gifa_api.service.IProvedorService;
import com.gifa_api.service.IProveedorDeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProveedorDeItemServiceImpl implements IProveedorDeItemService {
    private final IItemDeIventarioService  itemDeIventarioService;
    private final IProvedorService provedorService;
    private final IProveedorDeParteRepository iProveedorDeParteRepository;
    @Override
    public void asociarProveedorAItem(AsociacionProveedorDeITemDTO asociacionProveedorDeItemDTO) {
       ItemDeInventario itemDeInventario = itemDeIventarioService.obtenerById(asociacionProveedorDeItemDTO.getIdItem());
        Proveedor proveedor = provedorService.obtenerByid(asociacionProveedorDeItemDTO.getIdProveedor());
        ProveedorDeItem proveedorDeItem = ProveedorDeItem
                .builder()
                .itemDeInventario(itemDeInventario)
                .precio(asociacionProveedorDeItemDTO.getPrecio())
                .proveedor(proveedor)
                .build();

        iProveedorDeParteRepository.save(proveedorDeItem);
    }
}
