package com.example.transport2.controller;

import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportType;
import com.example.transport2.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/transport")
public class TransportApi {

    private final TransportRepository transportRepository;

    @Autowired
    public TransportApi(TransportRepository transportRepository) {

        this.transportRepository = transportRepository;

    }

    @GetMapping("filters")
    public List<Transport> getByFilters(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) String type) {
        if (name != null && type != null) {
            List<Transport> list = new ArrayList<>();
            list.add(transportRepository
                            .findByNameAndType(name, TransportType.valueOf(type))
                            .orElseThrow(() -> new EntityNotFoundException("не нашел запись")));
            return list;
        }
        if (name != null) {
            List<Transport> list = new ArrayList<>();
            list.add(transportRepository
                            .findByName(name)
                            .orElseThrow(() -> new EntityNotFoundException("не нашел запись")));
            return list;
        }
        if (type != null) {
            return transportRepository
                            .findAllByType(TransportType.valueOf(type));
        }
        return transportRepository.findAll();
    }
}