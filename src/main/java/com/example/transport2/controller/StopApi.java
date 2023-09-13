package com.example.transport2.controller;

import com.example.transport2.dto.StopDto;
import com.example.transport2.dto.TransportDto;
import com.example.transport2.mapper.StopMapper;
import com.example.transport2.model.Stop;
import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportType;
import com.example.transport2.repository.StopRepository;
import com.example.transport2.repository.TransportRepository;
import com.example.transport2.service.StopService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/stops")
public class StopApi {

    private final StopRepository stopRepository;
    private final StopService stopService;
    private final StopMapper stopMapper;

    @GetMapping
    public List<StopDto> getByFilters(@RequestParam(required = false) String name) {

        if (name != null) {
            return stopMapper.allToDto(stopRepository.findAllByNameContaining(name));
        }

        return stopMapper.allToDto(stopService.getAll());
    }

    @GetMapping("{id}")
    public StopDto getById(@PathVariable Integer id) {
        Stop stop = stopService.getById(id);
        return stopMapper.toDto(stop);
    }

    @PostMapping
    public StopDto saveDto(@RequestBody StopDto dto) {
        Stop stop = stopMapper.fromDto(dto);
        stopService.saveDto(stop);
        return dto;
    }

    @DeleteMapping
    public StopDto deleteDto(@RequestBody StopDto dto) {
        Stop stop = stopMapper.fromDto(dto);
        stopService.deleteDto(stop);
        return dto;
    }
}