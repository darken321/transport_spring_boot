package com.example.transport2.controller;

import com.example.transport2.model.Stop;
import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportType;
import com.example.transport2.repository.StopRepository;
import com.example.transport2.repository.TransportRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/stops")
public class StopApi {

    private final StopRepository stopRepository;

    @Autowired
    public StopApi(StopRepository stopRepository) {

        this.stopRepository = stopRepository;

    }

    @GetMapping("filters")
    public List<Stop> getByFilters(@RequestParam(required = false) String name) {

        if (name != null) {
            return stopRepository.findAllByName(name);
        }
        return stopRepository.findAll();
    }
    //TODO найти по ID и по имени
}