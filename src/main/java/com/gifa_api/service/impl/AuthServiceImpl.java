package com.gifa_api.service.impl;

import com.gifa_api.config.jwt.JwtService;
import com.gifa_api.dto.login.*;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Chofer;
import com.gifa_api.repository.IChoferRepository;
import com.gifa_api.utils.enums.Rol;
import com.gifa_api.exception.RegisterException;
import com.gifa_api.model.Usuario;
import com.gifa_api.repository.IUsuarioRepository;
import com.gifa_api.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final IUsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IChoferRepository choferRepository;

    @Override
    public LoginResponseDTO login(LoginRequestDTO userDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(),
                userDto.getPassword()));

        Usuario user = userRepository
                .findByUsuario(userDto.getUsername())
                .orElseThrow();

        String token = jwtService.getToken(user);

        return LoginResponseDTO
                .builder()
                .username(userDto.getUsername())
                .token(token)
                .role(user.getRol())
                .roleEntity(generateRoleEntity(user))
                .build();
    }

    private RolEntityDTO generateRoleEntity(Usuario user) {
        switch (user.getRol()) {
            case CHOFER -> {
                return generateChoferResponseDTO(user);
            }
            default -> {
                return null;
            }
        }
    }

    private RolEntityDTO generateChoferResponseDTO(Usuario user) {
        Chofer chofer = choferRepository.findByUsuario_Id(user.getId())
                .orElseThrow(() -> new NotFoundException("No se encontró el chofer del id asignado al usuario id: " + user.getId()));
        return ChoferLoginResponseDTO.builder()
                .id(chofer.getId())
                .nombre(chofer.getNombre())
                .build();
    }

    @Override
    public LoginResponseDTO register(RegisterRequestDTO userToRegisterDto) {
        if (userRepository.existsByUsuario(userToRegisterDto.getUsername()))
            throw new RegisterException("El usuario ya existe en la base de datos.");

        Usuario user = Usuario
                .builder()
                .usuario(userToRegisterDto.getUsername())
                .contrasena(passwordEncoder.encode(userToRegisterDto.getPassword()))
                .rol(Rol.getRol(userToRegisterDto.getRole()))
                .build();

        userRepository.save(user);

        return LoginResponseDTO
                .builder()
                .username(user.getUsuario())
                .token(jwtService.getToken(user))
                .role(user.getRol())
                .build();
    }

    @Override
    public void logout(String token) {
        String jwt = token.substring(7);
        jwtService.addToBlacklist(jwt);
    }
}
