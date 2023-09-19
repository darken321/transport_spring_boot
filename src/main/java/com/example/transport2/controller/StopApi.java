package com.example.transport2.controller;

import com.example.transport2.dto.StopDto;
import com.example.transport2.dto.StopSaveDto;
import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.mapper.StopMapper;
import com.example.transport2.model.Stop;
import com.example.transport2.model.Transport;
import com.example.transport2.service.StopService;
import com.example.transport2.service.TransportService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/stops")
public class StopApi {

    private final StopService stopService;
    private final TransportService transportService;
    private final StopMapper stopMapper;

    @GetMapping
    public List<StopDto> getByFilters(@RequestParam(required = false) @Size(min = 3) String name) {

        if (name != null) {
            return stopMapper.toDto(stopService.findAllByNameContaining(name));
        }
        return stopMapper.toDto(stopService.getAll());
    }

    @GetMapping("{id}")
    public StopTransportDto getById(@PathVariable @Positive Integer id) {
        Stop stop = stopService.getById(id);
        List<Transport> transports = transportService.getByStopId(id);
        return stopMapper.toDto(stop, transports);
    }

    @PostMapping
    public StopDto save(@RequestBody @Valid StopSaveDto dto) {
        Stop stop = stopMapper.fromDto(dto);
        Stop save = stopService.save(stop);
        return stopMapper.toDto(save);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable @Positive Integer id) {
        stopService.delete(id);
    }
}