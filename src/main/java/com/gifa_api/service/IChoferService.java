package com.gifa_api.service;

import com.gifa_api.dto.chofer.ChoferEditDTO;
import com.gifa_api.dto.chofer.ChoferRegistroDTO;

public interface IChoferService {
    void registro(ChoferRegistroDTO choferRegistroDTO);
    void habilitarChofer(ChoferEditDTO choferEditDTO);
    void inhabilitarChofer(ChoferEditDTO choferEditDTO);
}
