package com.gifa_api.service.impl;

import com.gifa_api.controller.MantenimientoController;
import com.gifa_api.dto.mantenimiento.AsignarMantenimientoRequestDTO;
import com.gifa_api.dto.mantenimiento.MantenimientosPendientesResponseDTO;
import com.gifa_api.dto.mantenimiento.MantenimientosResponseDTO;
import com.gifa_api.dto.mantenimiento.RegistrarMantenimientoDTO;
import com.gifa_api.exception.BadRoleException;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Mantenimiento;
import com.gifa_api.model.Usuario;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IMantenimientoRepository;
import com.gifa_api.repository.IUsuarioRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.IMantenimientoService;
import com.gifa_api.utils.enums.EstadoMantenimiento;
import com.gifa_api.utils.enums.EstadoVehiculo;
import com.gifa_api.utils.enums.Rol;
import com.gifa_api.utils.mappers.MantenimientoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MantenimientoServiceImpl implements IMantenimientoService {
    private  IMantenimientoRepository IMantenimientoRepository;
    private  IUsuarioRepository IUsuarioRepository;
    private  MantenimientoMapper mantenimientoMapper;
    private  IVehiculoRepository iVehiculoRepository;
    private  MantenimientoController mantenimientoController;

    public MantenimientoServiceImpl(@Autowired IMantenimientoRepository IMantenimientoRepository, IUsuarioRepository iUsuarioRepository, IVehiculoRepository iVehiculoRepository, MantenimientoController mantenimientoController) {
        this.IMantenimientoRepository = IMantenimientoRepository;
        IUsuarioRepository = iUsuarioRepository;
        this.iVehiculoRepository = iVehiculoRepository;
        this.mantenimientoMapper = new MantenimientoMapper();
        this.mantenimientoController = mantenimientoController;
    }



    @Override
    public void crearMantenimiento(RegistrarMantenimientoDTO registrarMantenimientoDTO) {
        Vehiculo vehiculo = iVehiculoRepository.findById(registrarMantenimientoDTO.getVehiculo_id())
                .orElseThrow(() -> new NotFoundException("No se encontró el vehiculo con id: " + registrarMantenimientoDTO.getVehiculo_id()));
        Mantenimiento mantenimiento = Mantenimiento
                .builder()
                .asunto(registrarMantenimientoDTO.getAsunto())
                .vehiculo(vehiculo)
                .build();
    }

    @Override
    public List<Mantenimiento> verMantenimientosPorVehiculo(Integer id) {
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

    // acepta un mantenimiento un operador
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

    // fix para que si utilizo un respuesto que se agrege a respuestosUtilizado.
    @Override
    public void finalizarMantenimiento(Integer mantenimientoId) {
        Mantenimiento mantenimiento = findById(mantenimientoId);
        mantenimiento.setEstadoMantenimiento(EstadoMantenimiento.FINALIZADO);
        mantenimiento.getVehiculo().setEstadoVehiculo(EstadoVehiculo.REPARADO);
                    mantenimiento.setFechaFinalizacion(LocalDate.now());
        IMantenimientoRepository.save(mantenimiento);
    }


}
