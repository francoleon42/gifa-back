package com.gifa_api.service.impl;

import com.gifa_api.client.ITraccarCliente;
import com.gifa_api.dto.traccar.PosicionResponseDTO;
import com.gifa_api.model.Posicion;
import com.gifa_api.repository.IPosicionRepository;
import com.gifa_api.service.IDispositivoService;
import com.gifa_api.service.ITraccarService;
import com.gifa_api.utils.mappers.PosicionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PosicionServiceImpl  {
    private final IPosicionRepository posicionRepository;
    private final ITraccarCliente traccarCliente;
    private final ITraccarService traccarService;
    private final IDispositivoService dispositivoService;
    private final PosicionMapper posicionMapper;





    public List<PosicionResponseDTO> getPosicionesDeDispositivo(String unicoID) {
        return posicionMapper.toPosicionResponseDTOList(posicionRepository.findByUnicoId(unicoID));
    }

    
    public List<Posicion> getPosicionesDeDispositivoDespuesDeFecha(String unicoID, OffsetDateTime fecha) {
        return posicionRepository.findByUnicoIdAndDespuesFecha(unicoID, fecha);
    }
}



