package com.example.transport2.controller;

import com.example.transport2.mapper.StopMapper;
import com.example.transport2.model.ScheduleTime;
import com.example.transport2.model.Stop;
import com.example.transport2.model.TransportRoute;
import com.example.transport2.service.StopService;
import com.example.transport2.service.TransportRouteService;
import com.example.transport2.service.TransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/routes")
public class RouteApi {

    private final TransportRouteService transportRouteService;

    @GetMapping
    public List<ScheduleTime> getByFilters(@RequestParam(required = true) Integer route,
                                           @RequestParam(required = true) Integer stop) {

        return transportRouteService.getByRouteAndStop(route, stop);
    }

}