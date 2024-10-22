package com.gifa_api.dto.traccar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VerInconsistenciasRequestDTO {

    OffsetDateTime fecha;
}
