package com.example.transport2.controller;

import com.example.transport2.dto.StopOneTransportDto;
import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.mapper.StopMapper;
import com.example.transport2.mapper.TransportRouteMapper;
import com.example.transport2.model.Stop;
import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportRoute;
import com.example.transport2.projection.TimeAndDayOfWeek;
import com.example.transport2.projection.TransportRouteNames;
import com.example.transport2.projection.TransportRouteStops;
import com.example.transport2.repository.RouteStopRepository;
import com.example.transport2.repository.TransportRouteRepository;
import com.example.transport2.service.StopService;
import com.example.transport2.service.StopTimeService;
import com.example.transport2.service.TransportRouteService;
import com.example.transport2.service.TransportService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * класс для запросов маршрутов
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/routes")
public class RouteApi {

    private final TransportRouteService transportRouteService;
    private final TransportRouteMapper transportRouteMapper;
    private final StopService stopService;
    private final StopTimeService stopTimeService;
    private final TransportService transportService;


    /**
     * Запрос расписания по одной остановке конкретного маршрута.
     * Возвращает объект DTO - {время, день недели}
     *
     * @param route id маршрута из таблицы transport_route
     * @param stop  id остановки из таблицы route_stops
     * @return объекты типа TimeAndDayOfWeek - время Time и день недели String
     */
    //добавить список маршрутов и остановок в DTO
    //TODO может ли API  возвращать сразу интерфейс, который получается из БД, а не DTO если они одинаковы (-маппинг)
    //может ли API сразу лезть в репо мимо сервиса или лучше в сервисе проверить данные?
    @GetMapping
    public List<TimeAndDayOfWeek> getByFilters(@RequestParam(required = true) Integer route, @RequestParam(required = true) Integer stop) {

        return transportRouteService.getByRouteAndStop(route, stop);
    }

    /**
     * Три времени прибытия транспорта по остановке с маршрутами и всеми остановками
     */
    @GetMapping("route/{routeId}/transport/{transportId}/stop/{stopId}")

    public StopOneTransportDto getByRouteAndTransport(@PathVariable Integer routeId, @PathVariable @NotNull Integer transportId, @PathVariable @NotNull Integer stopId) {

        Stop stop = stopService.getById(stopId);
        String stopName = stop.getName();
        String location = stop.getName();
        Transport transport = transportService.getById(transportId);
        String transportType = transport.getType().name();
        String transportName = transport.getName();
        List<Time> nearest3Times = transportRouteService.get3NearestTimes(stopId, routeId);
        List<TransportRouteNames> routes = transportRouteService.getRouteNames(routeId, transportId);
        List<TransportRouteStops> stops = transportRouteService.GetRouteStops(routeId);
        List<StopTransportDto.StopTransportInfoDto> transports = stopTimeService.getArrivalTransports(stopId);
        List<StopTransportDto.StopTransportTimeDto> routesTime = stopTimeService.getArrivalTimes(stopId);

        return transportRouteMapper.toBigDto(routeId,
                stopName,
                location,
                transportType,
                transportName,
                nearest3Times,
                routes,
                stops,
                transports,
                routesTime
        );
    }

    //TODO добавить запрос расписания определенного транспорта
    //возврат всех остановок всех маршрутов одного транспорта https://kogda.by/routes/brest/trolleybus/100
    // тип, название транспорта, локация и список DTO (маршруты с id и названиями остановок)
}