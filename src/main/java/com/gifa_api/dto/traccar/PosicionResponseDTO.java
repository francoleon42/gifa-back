package com.gifa_api.dto.traccar;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gifa_api.model.Dispositivo;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PosicionResponseDTO {

    Integer id;
    double latitude;
    double longitude;


    LocalDate fechaHora;
}
