package com.example.transport2.controller;

import com.example.transport2.dto.TransportDto;
import com.example.transport2.mapper.TransportMapper;
import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportType;
import com.example.transport2.repository.*;
import com.example.transport2.service.TransportService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/transport")
public class TransportApi {

    private final TransportRepository transportRepository;
    private final TransportService transportService;
    private final TransportMapper transportMapper;

    @Autowired
    public TransportApi(TransportRepository transportRepository, TransportService transportService, TransportMapper transportMapper) {

        this.transportRepository = transportRepository;
        this.transportService = transportService;
        this.transportMapper = transportMapper;
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
            return transportRepository
                    .findAllByName(name);
        }
        if (type != null) {
            return transportRepository
                    .findAllByType(TransportType.valueOf(type));
        }
        return transportRepository.findAll();
    }

    @GetMapping("{id}")
    public TransportDto getById(@PathVariable Integer id) {
        Transport transport = transportService.getById(id);
        return transportMapper.toDto(transport);
    }

    @GetMapping
    public Page<Transport> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                                  @RequestParam(required = false, defaultValue = "20") int size){
        return transportService.getAll(page, size);
    }
}