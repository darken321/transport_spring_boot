package com.example.transport2;

import com.example.transport2.model.Transport;
import com.example.transport2.repository.TransportRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.transport2.model.TransportType.BUS;
import static com.example.transport2.model.TransportType.TROLLEYBUS;

@Component
public class PopulateDB {

    private TransportRepository transportRepository;

    @Autowired
    public PopulateDB(TransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

    @PostConstruct
    public void init() {
        System.out.println("Метод init класса HumanApiBean PostConstruct");
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
}
