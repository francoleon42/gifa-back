package com.gifa_api.service;

import com.gifa_api.dto.proveedoresYPedidos.ProveedorResponseDTO;
import com.gifa_api.dto.proveedoresYPedidos.RegistroProveedorRequestDTO;
import com.gifa_api.model.Proveedor;

import java.util.List;

public interface IProvedorService {

    void registrarProveedor(RegistroProveedorRequestDTO requestDTO);
    Proveedor obtenerByid(Integer id);
    List<ProveedorResponseDTO> obtenerProveedores();
}
