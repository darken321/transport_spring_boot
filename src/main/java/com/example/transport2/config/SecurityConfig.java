package com.example.transport2.config;

import com.example.transport2.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // Отключаем CSRF защиту для упрощения
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Разрешаем доступ ко всем запросам
                )
                .formLogin().disable() // Отключаем форму логина
                .httpBasic().disable(); // Отключаем базовую аутентификацию

//        @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                //Настройка формы входа и указание URL для обработки запроса на вход
//                .formLogin(fl -> fl
//                        .loginProcessingUrl("/api/v1/login").permitAll()
//                        .defaultSuccessUrl("/api/v1/guest")
//                )
//                //Указание URL для выхода из системы (/api/v1/logout) и удаление куки сессии
//                .logout(logout -> logout
//                        .deleteCookies("JSESSIONID")
//                        .invalidateHttpSession(true)
//                        .logoutUrl("/api/v1/logout").permitAll()
//                )
//                .authorizeHttpRequests(ahr -> ahr
////                        .requestMatchers("/api/v1/guest","/stops/**").permitAll()
////                                .requestMatchers("/api/v1/admin").hasAuthority(Role.ADMIN.name())
//                                .requestMatchers("/api/v1/routes/**",
//                                        "/api/v1/transport/**",
//                                        "/api/v1/stops/**").hasAnyAuthority(Role.ADMIN.name())
//
////                        .requestMatchers(HttpMethod.GET, "/api/v1/products").permitAll()
////                                доступ к методам post и delete пути api/v1/stops есть только у админа
//                                .requestMatchers(HttpMethod.POST, "/api/v1/stops").hasAuthority(Role.ADMIN.name())
//                                .requestMatchers(HttpMethod.DELETE, "/api/v1/stops").hasAuthority(Role.ADMIN.name())
//                                .anyRequest().permitAll()
//                );
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}