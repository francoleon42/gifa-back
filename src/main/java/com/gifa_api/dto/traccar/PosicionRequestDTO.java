package com.gifa_api.dto.traccar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PosicionRequestDTO {
    private int id;
    private int deviceId;
    private String protocol;
    private OffsetDateTime deviceTime;
    private OffsetDateTime fixTime;
    private OffsetDateTime serverTime;
    private boolean outdated;
    private boolean valid;
    private double latitude;
    private double longitude;
    private double altitude;
    private double speed;
    private double course;
    private String address;
    private double accuracy;
    private Map<String, Object> network;
    private List<Integer> geofenceIds;
    private Map<String, Object> attributes;
}
