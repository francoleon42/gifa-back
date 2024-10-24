package com.gifa_api.service.impl;

import com.gifa_api.dto.mantenimiento.RegistrarMantenimientoDTO;
import com.gifa_api.dto.vehiculo.ListaVehiculosResponseDTO;
import com.gifa_api.dto.vehiculo.RegistarVehiculoDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.ITarjetaRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IMantenimientoService;
import com.gifa_api.service.IVehiculoService;
import com.gifa_api.utils.enums.EstadoDeHabilitacion;
import com.gifa_api.utils.enums.EstadoVehiculo;
import com.gifa_api.utils.mappers.VehiculoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements IVehiculoService {
    private final IVehiculoRepository vehiculoRepository;
    private final IMantenimientoService iMantenimientoService;
    private final ITarjetaRepository tarjetaRepository;
    private final VehiculoMapper vehiculoMapper;


    @Override
    public ListaVehiculosResponseDTO getVehiculos() {
        return vehiculoMapper.toListaVehiculosResponseDTO(vehiculoRepository.findAll());
    }

    @Override
    public void registrar(RegistarVehiculoDTO vehiculoDTO) {
        // Validar el DTO
        validarRegistrarVehiculoDTO(vehiculoDTO);

        Tarjeta tarjeta = Tarjeta.builder()
                .numero(generarNumeroTarjeta())
                .build();
        tarjetaRepository.save(tarjeta);

        Vehiculo vehiculo = Vehiculo
                .builder()
                .patente(vehiculoDTO.getPatente())
                .antiguedad(vehiculoDTO.getAntiguedad())
                .kilometraje(vehiculoDTO.getKilometraje())
                .modelo(vehiculoDTO.getModelo())
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .estadoVehiculo(EstadoVehiculo.REPARADO)
                .fechaVencimiento(vehiculoDTO.getFechaRevision())
                .tarjeta(tarjeta)
                .build();

        vehiculoRepository.save(vehiculo);
    }

    private Integer generarNumeroTarjeta() {
        return new Random().nextInt(90000000) + 10000000;
    }

    @Override
    public void inhabilitar(Integer vehiculoId) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new NotFoundException("No se encontró el vehiculo con id: " + vehiculoId));

        vehiculo.inhabilitar();
        vehiculoRepository.save(vehiculo);
    }

    @Override
    public void habilitar(Integer vehiculoId) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new NotFoundException("No se encontró el vehiculo con id: " + vehiculoId));

        vehiculo.habilitar();
        vehiculoRepository.save(vehiculo);
    }

    @Scheduled(fixedRate = 86400000)  // Ejecuta cada 24 horas (86400000 milisegundos)
    public void verificarFechaVencimiento() {
        LocalDate hoy = LocalDate.now();
        List<Vehiculo> allVehiculos = vehiculoRepository.findAll();
        for (Vehiculo vehiculo : allVehiculos) {
            if (!vehiculo.getFechaVencimiento().isAfter(hoy)) {
                iMantenimientoService.crearMantenimiento(RegistrarMantenimientoDTO
                        .builder()
                        .vehiculo_id(vehiculo.getId())
                        .asunto("Revision periodica")
                        .build());
            }
        }

    }

    private void validarRegistrarVehiculoDTO(RegistarVehiculoDTO vehiculoDTO) {
        // Validar patente
        String patenteRegex = "^(?:[A-Z]{3}\\d{3}|[A-Z]{2}\\d{3}[A-Z]{2})$"; // Regex para ambos formatos de patente
        if (vehiculoDTO.getPatente() == null || !vehiculoDTO.getPatente().matches(patenteRegex)) {
            throw new IllegalArgumentException("La patente debe tener el formato correcto (3 letras, 3 números )  or (2 letras, 3 dígitos, 2 letras).");
        }
        if (vehiculoDTO.getAntiguedad() == null || vehiculoDTO.getAntiguedad() < 0) {
            throw new IllegalArgumentException("La antigüedad debe ser mayor o igual a 0.");
        }
        if (vehiculoDTO.getKilometraje() == null || vehiculoDTO.getKilometraje() < 0) {
            throw new IllegalArgumentException("El kilometraje debe ser mayor o igual a 0.");
        }
        if (vehiculoDTO.getModelo() == null || vehiculoDTO.getModelo().trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo no puede estar vacío.");
        }
        // Validar fecha de revisión
        if (vehiculoDTO.getFechaRevision() == null || vehiculoDTO.getFechaRevision().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de revisión debe ser posterior a la fecha actual.");
        }
    }
}
