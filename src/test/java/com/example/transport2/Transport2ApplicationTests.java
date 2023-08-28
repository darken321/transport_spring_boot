package com.example.transport2;

import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportType;
import com.example.transport2.repository.TransportRepository;
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

    @Test
    void saveTransportTest() {
        Transport transport;
        for (int i = 0; i <= 9; i++) {
            transport = Transport.builder()
                    .type(TROLLEYBUS)
                    .name("10" + i)
                    .build();
            transportRepository.save(transport);
        }
        List<String> autobus = new ArrayList<>(Arrays.asList("1", "1А", "2", "2А", "3", "5", "6"));
        for (String s : autobus) {
            transport = Transport.builder()
                    .type(BUS)
                    .name(s)
                    .build();
            transportRepository.save(transport);
        }
    }

    @Test
    void findTransport() {
        Transport transport;
        for (int i = 0; i <= 9; i++) {
            transport = Transport.builder()
                    .type(TROLLEYBUS)
                    .name("10" + i)
                    .build();
            transportRepository.save(transport);
        }
        List<String> autobus = new ArrayList<>(Arrays.asList("1", "1А", "2", "2А", "3", "5", "6"));
        for (String s : autobus) {
            transport = Transport.builder()
                    .type(BUS)
                    .name(s)
                    .build();
            transportRepository.save(transport);
        }

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
}
