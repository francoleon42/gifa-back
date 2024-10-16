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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CargaCombustibleServiceImpl implements ICargaCombustibleService {
    private final ITarjetaRepository tarjetaRepository;
    private final ICargaCombustibleRepository cargaCombustibleRepository;

    private List<Float> preciosCombustiblesCache;
    private LocalDate ultimaActualizacionPrecio;

    @Override
    public void cargarCombustible(CargaCombustibleRequestDTO cargaCombustibleRequestDTO) {
        // cargaCombustible conseguir preciosporlitro y asi calcular el total de esa carga.
        Tarjeta tarjeta = tarjetaRepository.findById(cargaCombustibleRequestDTO.getNumeroTarjeta())
                .orElseThrow(() -> new NotFoundException("No se encontró la tarjeta con id: " + cargaCombustibleRequestDTO.getNumeroTarjeta()));

        Float precioSuper = obtenerPrecioCombustible().get(0);

        CargaCombustible cargaCombustible = CargaCombustible
                .builder()
                .tarjeta(tarjeta)
                .cantidadLitros(cargaCombustibleRequestDTO.getCantidadLitros())
                .fechaHora(cargaCombustibleRequestDTO.getFechaYhora())
                .precioPorLitro(precioSuper)
                .costoTotal(precioSuper * cargaCombustibleRequestDTO.getCantidadLitros())
                .build();

        cargaCombustibleRepository.save(cargaCombustible);
    }

    public List<Float> obtenerPrecioCombustible() {
        LocalDate currentDate = LocalDate.now();

        if (preciosCombustiblesCache != null && ultimaActualizacionPrecio != null && ultimaActualizacionPrecio.isEqual(currentDate)) {
            return preciosCombustiblesCache;
        }

        preciosCombustiblesCache = actualizarPrecioCombustible();
        ultimaActualizacionPrecio = currentDate;

        return preciosCombustiblesCache;
    }

    private List<Float> actualizarPrecioCombustible() {
        String url = "https://surtidores.com.ar/precios/";
        List<Float> preciosCombustibles = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();

            LocalDate currentDate = LocalDate.now();
            String currentMonth = currentDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("es"));
            int currentYear = currentDate.getYear();

            Element table = doc.select("table").first();
            Elements rows = table.select("tr");

            int monthIndex = -1;

            for (Element row : rows) {
                if (row.text().contains(String.valueOf(currentYear))) {
                    Elements months = row.select("td");
                    for (int i = 0; i < months.size(); i++) {
                        if (months.get(i).text().equalsIgnoreCase(currentMonth)) {
                            monthIndex = i;
                            break;
                        }
                    }
                }

                if (monthIndex != -1) {
                    String[] combustibles = {"Súper", "Premium", "Gasoil", "Euro"};
                    for (int i = 0; i < combustibles.length; i++) {
                        Element combustibleRow = rows.get(rows.indexOf(row) + (i + 1));
                        Elements precios = combustibleRow.select("td");

                        String precioTexto = precios.get(monthIndex).text().replace(",", ".").trim();
                        try {
                            Float precio = Float.parseFloat(precioTexto);
                            preciosCombustibles.add(precio);
                        } catch (NumberFormatException e) {
                            System.out.println("No se pudo convertir el precio: " + precioTexto);
                            preciosCombustibles.add(0f);
                        }
                    }
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return preciosCombustibles;
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
