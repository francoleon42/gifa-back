package com.gifa_api.service.impl;

import com.gifa_api.dto.gestionDeCombustilble.CargaCombustibleRequestDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.CargaCombustible;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.repository.ICargaCombustibleRepository;
import com.gifa_api.repository.ITarjetaRepository;

import com.gifa_api.service.ICargaCombustibleService;
import com.gifa_api.service.IDispositivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CargaCombustibleServiceImpl implements ICargaCombustibleService {
    private final ITarjetaRepository tarjetaRepository;
    private final ICargaCombustibleRepository cargaCombustibleRepository;
    private final IDispositivoService dispositivoService;

    @Override
    public void cargarCombustible(CargaCombustibleRequestDTO cargaCombustibleRequestDTO) {
        // cargaCombustible conseguir preciosporlitro y asi calcular el total de esa carga.
        Tarjeta tarjeta = tarjetaRepository.findById(cargaCombustibleRequestDTO.getNumeroTarjeta())
                .orElseThrow(() -> new NotFoundException("No se encontró la tarjeta con id: " + cargaCombustibleRequestDTO.getNumeroTarjeta()));

        CargaCombustible cargaCombustible = CargaCombustible
                .builder()
                .tarjeta(tarjeta)
                .cantidadLitros(cargaCombustibleRequestDTO.getCantidadLitros())
                .fechaHora(cargaCombustibleRequestDTO.getFechaYhora())
                .build();

        cargaCombustibleRepository.save(cargaCombustible);
    }


    @Override
    public double combustibleCargadoEn(String numeroTarjeta, LocalDateTime fecha) {
        int cargaTotal = 0;
        List<CargaCombustible> cargasCombustible = cargaCombustibleRepository.findByNumeroTarjetaAndFechaAfter(numeroTarjeta, fecha);
        for (CargaCombustible carga : cargasCombustible) {
            cargaTotal += carga.getCantidadLitros();
        }
        return cargaTotal;
    }






}
