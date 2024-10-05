package com.gifa_api.service;

import com.gifa_api.dto.proveedoresYPedidos.RegistroProveedorRequestDTO;

public interface IProvedorService {

    void registrarProveedor(RegistroProveedorRequestDTO requestDTO);
}
