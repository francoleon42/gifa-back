package com.gifa_api.service.impl;

import com.gifa_api.dto.proveedoresYPedidos.RegistroProveedorRequestDTO;
import com.gifa_api.model.Proveedor;
import com.gifa_api.service.IProvedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProvedorServiceImpl implements IProvedorService {
    @Override
    public void registrarProveedor(RegistroProveedorRequestDTO requestDTO) {
        Proveedor proveedor = Proveedor
                .builder()
                .nombre(requestDTO.getNombre())
                .email(requestDTO.getEmail())
                .build();
    }
}
