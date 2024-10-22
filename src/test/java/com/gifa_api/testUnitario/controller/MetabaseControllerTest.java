package com.gifa_api.testUnitario.controller;

import com.gifa_api.controller.MetabaseController;
import com.gifa_api.dto.metabase.MetabaseTokenResponseDTO;
import com.gifa_api.service.IMetabaseTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetabaseControllerTest {

    @InjectMocks
    private MetabaseController metabaseController;

    @Mock
    private IMetabaseTokenService metabaseTokenService;

    @Test
    void testGetToken() {
        Long dashboardId = 1L;
        MetabaseTokenResponseDTO metabaseTokenResponseDTO = new MetabaseTokenResponseDTO();
        // Configura metabaseTokenResponseDTO según sea necesario

        when(metabaseTokenService.generateTokenForDashboard(dashboardId)).thenReturn(metabaseTokenResponseDTO);

        ResponseEntity<MetabaseTokenResponseDTO> response = metabaseController.getToken(dashboardId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(metabaseTokenResponseDTO, response.getBody());
    }
}
