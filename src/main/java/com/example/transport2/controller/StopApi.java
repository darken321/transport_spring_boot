package com.example.transport2.controller;

import com.example.transport2.dto.StopDto;
import com.example.transport2.dto.StopSaveDto;
import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.mapper.StopMapper;
import com.example.transport2.model.Stop;
import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportRoute;
import com.example.transport2.service.StopService;
import com.example.transport2.service.TransportRouteService;
import com.example.transport2.service.TransportService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
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
    private final TransportRouteService transportRouteService;
    private final StopMapper stopMapper;

    @GetMapping
    public List<StopDto> getByFilters(@RequestParam(required = false) @Size(min = 3) String name) {

        if (name != null) {
            return stopMapper.toDto(stopService.findAllByNameContaining(name));
        }
        return stopMapper.toDto(stopService.getAll());
    }

    @GetMapping("{id}")
    public StopTransportDto getById(@PathVariable Integer id) {
        Stop stop = stopService.getById(id); //сущность отстановки по ID
//        List<Transport> transports = transportService.getByStopId(id); //список транспортов по остановке
        List<TransportRoute> routes = transportRouteService.getByStopId(id); //список маршрутов по остановке
        return stopMapper.toDto(stop, routes);
    }


    @PostMapping
    public StopDto save(@RequestBody @Valid StopSaveDto dto) {
        Stop stop = stopMapper.fromDto(dto);
        Stop save = stopService.save(stop);
        return stopMapper.toDto(save);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        stopService.delete(id);
    }
}