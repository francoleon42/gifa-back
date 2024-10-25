package com.gifa_api.service;

import com.gifa_api.dto.proveedor.ProveedorResponseDTO;
import com.gifa_api.dto.proveedor.RegistroProveedorRequestDTO;
import com.gifa_api.model.Proveedor;

import java.util.List;

public interface IProvedorService {

    void registrarProveedor(RegistroProveedorRequestDTO requestDTO);
    Proveedor obtenerByid(Integer id);
    List<ProveedorResponseDTO> obtenerProveedores();
}
