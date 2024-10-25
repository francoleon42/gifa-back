package com.gifa_api.utils.mappers;


import com.gifa_api.dto.traccar.PosicionResponseDTO;
import com.gifa_api.model.Posicion;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PosicionMapper {

    public PosicionResponseDTO toPosicionResponseDTO(Posicion posicion) {
        return PosicionResponseDTO.builder()
                .id(posicion.getId())
                .latitude(posicion.getLatitude())
                .longitude(posicion.getLongitude())
                .fechaHora(posicion.getFechaHora())
                .build();
    }

    public List<PosicionResponseDTO> toPosicionResponseDTOList(List<Posicion> posiciones) {
        return posiciones.stream()
                .map(this::toPosicionResponseDTO)
                .collect(Collectors.toList());
    }
}