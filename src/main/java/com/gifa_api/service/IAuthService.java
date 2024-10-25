package com.gifa_api.service;

import com.gifa_api.dto.login.LoginRequestDTO;
import com.gifa_api.dto.login.LoginResponseDTO;
import com.gifa_api.dto.login.RegisterRequestDTO;
import com.gifa_api.dto.login.UpdateRequestDTO;

public interface IAuthService {
    LoginResponseDTO login(LoginRequestDTO userDto);
    LoginResponseDTO register(RegisterRequestDTO userToRegisterDto);
    void logout(String token);
    void update(Integer id, UpdateRequestDTO userToUpdateDto);
}
