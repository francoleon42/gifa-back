package com.gifa_api.config;

import com.gifa_api.utils.enums.Rol;
import com.gifa_api.model.Usuario;
import com.gifa_api.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bootstrap implements ApplicationRunner {
    private final IUsuarioRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Usuario admin = Usuario.builder()
                .usuario("admin")
                .contrasena("$2a$10$pnxUrqPKMU2HtJPt9RO1z.p5CK48s6Hus10QD1SFjCjvea9CxddKu")
                .rol(Rol.ADMINISTRADOR)
                .build();

        userRepository.save(admin);
    }
}
