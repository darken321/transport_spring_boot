package com.example.transport2.controller;

import com.example.transport2.dto.*;
import com.example.transport2.mapper.TransportRouteMapper;
import com.example.transport2.model.Stop;
import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportRoute;
import com.example.transport2.projection.*;
import com.example.transport2.repository.TransportRouteRepository;
import com.example.transport2.service.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.example.transport2.util.TimeUtils.timeCutToSting;

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
    private final TransportService transportService;
    private final TransportRouteRepository transportRouteRepository;
    private final TransportRouteMapper transportRouteMapper;
    private final StopService stopService;
    private final RouteService routeService;
    private final RouteStopService routeStopService;

    /**
     * Запрос полного расписания по одной остановке конкретного маршрута.
     * Возвращает объект DTO
     * - тип транспорта
     * - имя транспорта
     * - локация остановки
     * - имя остановки
     * - routes все маршруты этого транспорта в формате RouteInfoDto
     * - список остановок этого маршрута StopInfoDto
     * - {время, день недели} ScheduleDto
     *
     * @param routeId id маршрута из таблицы transport_route
     * @param stopId  id остановки из таблицы route_stops
     * @return объекты типа TimeAndDayOfWeek - время Time и день недели String
     */
    @GetMapping("fullschedule/{routeId}/stop/{stopId}")

    public FullScheduleOneRouteTransportDto getByFilters(@PathVariable Integer routeId, @PathVariable @NotNull Integer stopId) {

        Stop stop = stopService.getById(stopId);
        String stopName = stop.getName();
        String location = stop.getLocation().getName();
        TransportRoute transportRoute = transportRouteRepository.findById(routeId).get();
        Transport transport = transportRoute.getTransport();
        String transportType = transport.getType().name();
        String transportName = transport.getName();
        Integer transportId = transport.getId();
        List<TransportRouteNames> routes = transportRouteService.getRouteNames(routeId, transportId);
        List<TransportRouteStops> stops = transportRouteService.getRouteStops(routeId);

        List<TimeAndDayOfWeek> schedule = transportRouteService.getByRouteAndStop(routeId, stopId);
        //Преобразует время Time во время String и обрезает секунды
        List<TimeDayOfWeekDto> timeWeekDtos = schedule.stream()
                .map(t -> new TimeDayOfWeekDto(timeCutToSting(t.getTime()), t.getDayOfWeek()))
                .toList();

        return transportRouteMapper.toScheduleDto(
                stopId,
                stopName,
                location,
                transportType,
                transportName,
                routes,
                stops,
                timeWeekDtos
        );
    }

    /**
     * Запрос короткого расписания по одной остановке конкретного маршрута.
     * Возвращает объект DTO
     * - тип транспорта
     * - имя транспорта
     * - локация остановки
     * - имя остановки
     * - routes все маршруты этого транспорта в формате RouteInfoDto
     * - список остановок этого маршрута в формате StopInfoDto
     * - три ближайших времени прибытия этого транспорта на эту остановку
     * - список транспортов по этой остановке
     * - список маршрутов по этой остановке с временами прибытия
     *
     * @param routeId id маршрута из таблицы transport_route
     * @param stopId  id остановки из таблицы route_stops
     * @return
     */
    @GetMapping("shortschedule/{routeId}/stop/{stopId}")
    public StopOneTransportDto getByRouteAndTransport(@PathVariable Integer routeId, @PathVariable @NotNull Integer stopId) {
        LocalTime currentTime = LocalTime.of(16, 00);
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        Stop stop = stopService.getById(stopId);
        String stopName = stop.getName();
        String location = stop.getLocation().getName();
        TransportRoute transportRoute = transportRouteRepository.findById(routeId).get();
        Transport transport = transportRoute.getTransport();
        String transportType = transport.getType().name();
        String transportName = transport.getName();
        Integer transportId = transport.getId();
        List<TransportRouteNames> routes = transportRouteService.getRouteNames(routeId, transportId);
        List<TransportRouteStops> stops = transportRouteService.getRouteStops(routeId);

        List<Time> nearest3Times = transportRouteService.get3NearestTimes(stopId, routeId, currentTime, dayOfWeek);
        List<LocalTime> nearest3LocalTimes = nearest3Times.stream()
                .map(Time::toLocalTime)
                .toList();
        List<StopTransportDto.StopTransportInfoDto> transports = routeService.getArrivalTransports(stopId, currentTime, dayOfWeek);
        List<StopTransportDto.StopTransportTimeDto> routesTime = routeService.getArrivalTimes(stopId, currentTime, dayOfWeek);

        return transportRouteMapper.toBigDto(
                routeId,
                stopName,
                location,
                transportType,
                transportName,
                nearest3LocalTimes,
                routes,
                stops,
                transports,
                routesTime
        );
    }

    /**
     * запрос всех расписаний определенного транспорта
     * @param transportId id транспорта
     * @return инфо о транспорте, список маршрутов и внутри каждого список его остановок
     */
    @GetMapping("transportroute/{transportId}")
    public OneTransportRoutesDto getByTransport(@PathVariable Integer transportId) {
        //взял инфу о транспорте
        TransportInfo transportInfo = transportService.getTransportInfoById(transportId);
        // берем инфу о маршрутах
        List<TransportRoutesInfo> transportRoutes = transportRouteService.getTransportRoutes(transportId);
        // и раскладываю ее в DTO
        OneTransportRoutesDto oneTransportRoutesDto = transportRouteMapper.allToDto(transportInfo,transportRoutes);

        //пройтись по маршрутам и найти для них остановки
        for (FullRouteInfoDto route : oneTransportRoutesDto.getRoutes()) {
            //получил список остановок для этого маршрута
            List<TransportRouteStops> routeStops = routeStopService.getRouteStops(route.getTransportRouteId());
            //routeStops закинуть в маппер чтоб добавить остановки в DTO
            route.setStops(transportRouteMapper.allToStopDto(routeStops));
        }
        return oneTransportRoutesDto;
    }
}