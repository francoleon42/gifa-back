package com.gifa_api.service;

import com.gifa_api.dto.proveedoresYPedidos.RegistroProveedorRequestDTO;
import com.gifa_api.model.Proveedor;

public interface IProvedorService {

    void registrarProveedor(RegistroProveedorRequestDTO requestDTO);
    Proveedor obtenerByid(Integer id);
}
