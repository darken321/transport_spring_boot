package com.example.transport2.controller;

import com.example.transport2.dto.ScheduleDto;
import com.example.transport2.service.TransportRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * класс для запросов маршрутов
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/routes")
public class RouteApi {

    private final TransportRouteService transportRouteService;

    /**
     * Запрос расписания по одной остановке конкретного маршрута.
     * Возвращает объект DTO - {время, день недели}
     * @param route id маршрута из таблицы transport_route
     * @param stop id остановки из таблицы route_stops
     * @return объекты типа TimeAndDayOfWeek - время Time и день недели String
     */
    @GetMapping
    public List<ScheduleDto> getByFilters(@RequestParam(required = true) Integer route,
                                         @RequestParam(required = true) Integer stop) {

        return transportRouteService.getByRouteAndStop(route, stop);
    }
}