package com.gifa_api.service.impl;

import com.gifa_api.dto.proveedoresYPedidos.RegistroProveedorRequestDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.model.Proveedor;
import com.gifa_api.repository.IProveedorRepository;
import com.gifa_api.service.IProvedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProvedorServiceImpl implements IProvedorService {
    private final IProveedorRepository iProveedorRepository;

    @Override
    public void registrarProveedor(RegistroProveedorRequestDTO requestDTO) {
        Proveedor proveedor = Proveedor
                .builder()
                .nombre(requestDTO.getNombre())
                .email(requestDTO.getEmail())
                .build();

        iProveedorRepository.save(proveedor);
    }

  ;  @Override
    public Proveedor obtenerByid(Integer id) {
        Proveedor proveedor = iProveedorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el proveedor con id: " + id));
        return proveedor;
    }
}
