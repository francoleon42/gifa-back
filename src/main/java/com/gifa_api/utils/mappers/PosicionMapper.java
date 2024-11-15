package com.gifa_api.utils.mappers;


import com.gifa_api.dto.traccar.PosicionRequestDTO;
import com.gifa_api.dto.traccar.PosicionResponseDTO;
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
                .fechaHora(posicion.getFechaHora().toLocalDate())
                .build();
    }

    public List<PosicionResponseDTO> toPosicionResponseDTOList(List<Posicion> posiciones) {
        return posiciones.stream()
                .map(this::toPosicionResponseDTO)
                .collect(Collectors.toList());
    }


    public PosicionResponseDTO toPoscicionResponseFromPosicionRequest(PosicionRequestDTO posicionRequestDTO) {
        return PosicionResponseDTO.builder()
                .id(posicionRequestDTO.getId())
                .latitude(posicionRequestDTO.getLatitude())
                .longitude(posicionRequestDTO.getLongitude())
                .fechaHora(posicionRequestDTO.getFixTime().toLocalDate()) // Convierte OffsetDateTime a LocalDate
                .build();
    }

    public List<PosicionResponseDTO> mapPosicionesRequestToPosicionesResponseDTO(List<PosicionRequestDTO> posiciones) {
        return posiciones.stream()
                .map(this:: toPoscicionResponseFromPosicionRequest)
                .collect(Collectors.toList());
    }
}