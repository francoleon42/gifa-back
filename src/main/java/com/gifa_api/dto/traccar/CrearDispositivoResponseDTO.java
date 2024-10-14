package com.gifa_api.dto.traccar;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CrearDispositivoResponseDTO {
    private Integer id;
//    private Map<String, Object> attributes; // Puede ser un objeto más específico si tienes un modelo definido
    private Integer groupId;
    private Integer calendarId;
    private String name;
    private String uniqueId;
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime lastUpdate;

    private Integer positionId;
    private String phone;
    private String model;
    private String contact;
    private String category;
    private Boolean disabled;
    private OffsetDateTime expirationTime;
}
