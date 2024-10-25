package com.gifa_api.service;

import com.gifa_api.dto.login.*;

public interface IAuthService {
    LoginResponseDTO login(LoginRequestDTO userDto);
    LoginResponseDTO register(RegisterRequestDTO userToRegisterDto);
    void logout(String token);
    void update(Integer id, UpdateRequestDTO userToUpdateDto);
    GetUserByUsernameResponseDTO getUserByUsername(String username);
}
