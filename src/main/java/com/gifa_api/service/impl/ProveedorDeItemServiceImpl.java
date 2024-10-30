package com.gifa_api.service.impl;


import com.gifa_api.dto.proveedor.ProveedorDeITemRequestDTO;
import com.gifa_api.dto.proveedor.ProveedorDeITemResponseDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.model.Proveedor;
import com.gifa_api.model.ProveedorDeItem;
import com.gifa_api.repository.IProveedorDeItemRepository;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IProvedorService;
import com.gifa_api.service.IProveedorDeItemService;
import com.gifa_api.utils.mappers.ProveedorDeItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorDeItemServiceImpl implements IProveedorDeItemService {
    private final ItemDeInventarioRepository itemDeInventarioRepository;
    private final IProvedorService provedorService;
    private final IProveedorDeItemRepository iProveedorDeItemRepository;
    private final ProveedorDeItemMapper proveedorDeItemMapper;

    @Override
    public void asociarProveedorAItem(ProveedorDeITemRequestDTO proveedorDeItemRequestDTO) {
        // Validar el DTO
        validarProveedorDeItemRequestDTO(proveedorDeItemRequestDTO);
       ItemDeInventario itemDeInventario = itemDeInventarioRepository.findById(proveedorDeItemRequestDTO.getIdItem())
               .orElseThrow(() -> new NotFoundException("No se encontró el id del item para asociacion con proovedor: " + proveedorDeItemRequestDTO.getIdItem()));

        Proveedor proveedor = provedorService.obtenerByid(proveedorDeItemRequestDTO.getIdProveedor());
        ProveedorDeItem proveedorDeItem = ProveedorDeItem
                .builder()
                .itemDeInventario(itemDeInventario)
                .precio(proveedorDeItemRequestDTO.getPrecio())
                .proveedor(proveedor)
                .build();

        iProveedorDeItemRepository.save(proveedorDeItem);
    }

    @Override
    public ProveedorDeItem proveedorMasEconomico(Integer idItem) {
       return  iProveedorDeItemRepository.findProveedorMasEconomicoByItemId(idItem);
    }

    @Override
    public List<ProveedorDeITemResponseDTO> obtenerAll() {
        return proveedorDeItemMapper.mapToProveedorDeItemResponseDTO(iProveedorDeItemRepository.findAll());
    }
    private void validarProveedorDeItemRequestDTO(ProveedorDeITemRequestDTO requestDTO) {
        if (requestDTO.getPrecio() == null || requestDTO.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero.");
        }
    }
}
