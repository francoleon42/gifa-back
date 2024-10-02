package com.gifa_api.service.impl;

import com.gifa_api.dto.mantenimiento.AsignarMantenimientoRequestDTO;
import com.gifa_api.dto.mantenimiento.MantenimientosPendientesResponseDTO;
import com.gifa_api.dto.mantenimiento.MantenimientosResponseDTO;
import com.gifa_api.exception.BadRoleException;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Mantenimiento;
import com.gifa_api.model.Usuario;
import com.gifa_api.repository.IMantenimientoRepository;
import com.gifa_api.repository.IUsuarioRepository;
import com.gifa_api.service.IMantenimientoService;
import com.gifa_api.utils.enums.EstadoMantenimiento;
import com.gifa_api.utils.enums.EstadoVehiculo;
import com.gifa_api.utils.enums.Rol;
import com.gifa_api.utils.mappers.MantenimientoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MantenimientoServiceImpl implements IMantenimientoService {
    private final IMantenimientoRepository IMantenimientoRepository;
    private final IUsuarioRepository IUsuarioRepository;
    private final MantenimientoMapper mantenimientoMapper;

    public MantenimientoServiceImpl(@Autowired IMantenimientoRepository IMantenimientoRepository, IUsuarioRepository iUsuarioRepository) {
        this.IMantenimientoRepository = IMantenimientoRepository;
        IUsuarioRepository = iUsuarioRepository;
        this.mantenimientoMapper = new MantenimientoMapper();
    }

    @Override
    public List<Mantenimiento> verMantenimientosPorId(Integer id) {
        return IMantenimientoRepository.findByVehiculoId(id);
    }

    @Override
    public void registrarMantenimientoManualmente(Integer id) {

    }

    @Override
    public MantenimientosResponseDTO verMantenimientos() {
        return mantenimientoMapper.mapListToMantenimientosDTO(IMantenimientoRepository.findAll());
    }

    @Override
    public MantenimientosPendientesResponseDTO verMantenimientosPendientes() {
        return mantenimientoMapper.mapListToMantenimientosPendientesDTO(IMantenimientoRepository.findAllByEstadoMantenimiento(EstadoMantenimiento.PENDIENTE));
    }

    public void asignarMantenimiento(Integer mantenimientoId, AsignarMantenimientoRequestDTO asignarMantenimientoRequestDTO) {
        Mantenimiento mantenimiento = findById(mantenimientoId);

        Usuario usuario = IUsuarioRepository.findById(asignarMantenimientoRequestDTO.getOperadorId())
                .filter(u -> u.getRol() == Rol.OPERADOR)
                .orElseThrow(() -> new BadRoleException("El usuario a asignar al mantenimiento no es un operador. Id: " + asignarMantenimientoRequestDTO.getOperadorId()));

        mantenimiento.setOperador(usuario);
        mantenimiento.setEstadoMantenimiento(EstadoMantenimiento.APROBADO);
    }

    private Mantenimiento findById(Integer mantenimientoId){
        return IMantenimientoRepository.findById(mantenimientoId)
                .orElseThrow(() -> new NotFoundException("No se encontró el mantenimiento con id: " + mantenimientoId));
    }

    @Override
    public void finalizarMantenimiento(Integer mantenimientoId) {
        Mantenimiento mantenimiento = findById(mantenimientoId);
        mantenimiento.setEstadoMantenimiento(EstadoMantenimiento.FINALIZADO);
        mantenimiento.getVehiculo().setEstadoVehiculo(EstadoVehiculo.REPARADO);
        IMantenimientoRepository.save(mantenimiento);
    }
}
