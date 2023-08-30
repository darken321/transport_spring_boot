package com.example.transport2;

import com.example.transport2.repository.TransportRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Transport2Application {

    public static void main(String[] args) {
        SpringApplication.run(Transport2Application.class, args);
    }
}