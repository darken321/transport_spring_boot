package com.example.transport2;

import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportType;
import com.example.transport2.repository.TransportRepository;
import com.example.transport2.service.TransportService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.transport2.model.TransportType.BUS;
import static com.example.transport2.model.TransportType.TROLLEYBUS;


@SpringBootTest
@Slf4j
class Transport2ApplicationTests {

    @Autowired
    TransportRepository transportRepository;
    @Autowired
    TransportService transportService;

//    @Test
    void findTransport() {
        Transport findById = transportRepository
                .findById(14).orElseThrow(() -> new EntityNotFoundException("не нашел запись"));
        log.info(findById.toString());

        Transport findByName = transportRepository
                .findByName("101").orElseThrow(() -> new EntityNotFoundException("не нашел запись"));
        log.info(findByName.toString());

        Transport findByNameAndType = transportRepository
                .findByNameAndType("105", TROLLEYBUS).orElseThrow(() -> new EntityNotFoundException("не нашел запись"));
        log.info(findByNameAndType.toString());

        List<Transport> findAllByType = transportRepository
                .findAllByType(BUS);
        log.info(findAllByType.toString());
    }

    @Test
    void findLocationTest() {
        List<Transport> byLocationIdAndType = transportService.getByLocationIdAndType(1, BUS);
        byLocationIdAndType.forEach(System.out::println);
    }
}
