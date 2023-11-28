package com.example.transport2.config;

import com.example.transport2.model.Role;
import com.example.transport2.model.User;
import com.example.transport2.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * бин добавляет админа и юзера в таблицу users
 * и удаляет по завершению работы
 */
@Component
@Scope("singleton")
@RequiredArgsConstructor
public class DataBaseFiller {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        userRepository.save(User.builder()
                .login("admin")
                .password(passwordEncoder.encode("12345"))
                .role(Role.ADMIN)
                .build());

        userRepository.save(User.builder()
                .login("user")
                .password(passwordEncoder.encode("12345"))
                .role(Role.USER)
                .build());
        userRepository.findAll().forEach(System.out::println);
    }

    @PreDestroy
    public void destroy() {
        userRepository.deleteAll();
    }
}