package com.example.transport2.controller;

import com.example.transport2.dto.FullScheduleOneRouteTransportDto;
import com.example.transport2.dto.StopOneTransportDto;
import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.dto.TimeDayOfWeekDto;
import com.example.transport2.mapper.TransportRouteMapper;
import com.example.transport2.model.Stop;
import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportRoute;
import com.example.transport2.projection.TimeAndDayOfWeek;
import com.example.transport2.projection.TransportRouteNames;
import com.example.transport2.projection.TransportRouteStops;
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
    private final TransportRouteRepository transportRouteRepository;
    private final TransportRouteMapper transportRouteMapper;
    private final StopService stopService;
    private final StopTimeService stopTimeService;
    private final TransportService transportService;

    /**
     * Запрос полного расписания по одной остановке конкретного маршрута.
     * Возвращает объект DTO
     * - тип транспорта
     * - имя транспорта
     * - локация остановки
     * - имя остановки
     * - routes маршруты транспорта по этой остановке RouteInfoDto
     * - список остановок этого маршрута StopInfoDto
     * - {время, день недели} ScheduleDto
     *
     * @param routeId id маршрута из таблицы transport_route
     * @param stopId  id остановки из таблицы route_stops
     * @return объекты типа TimeAndDayOfWeek - время Time и день недели String
     */
    //TODO добавить список маршрутов и остановок в DTO
    @GetMapping
    public FullScheduleOneRouteTransportDto getByFilters(@RequestParam(required = true) Integer routeId, @RequestParam(required = true) Integer stopId) {
        Stop stop = stopService.getById(stopId);
        String stopName = stop.getName();
        String location = stop.getLocation().getName();
        TransportRoute transportRoute = transportRouteRepository.findById(routeId).get();
        String transportType = transportRoute.getTransport().getType().name();
        String transportName = transportRoute.getTransport().getName();
        Integer transportId = transportRoute.getId();
        //TODO маршруты отдает неверно, работает только на routeId 1 и 2
        List<TransportRouteNames> routes = transportRouteService.getRouteNames(routeId, transportId);
        List<TransportRouteStops> stops = transportRouteRepository.findRouteStops(routeId);
        List<TimeAndDayOfWeek> schedule = transportRouteService.getByRouteAndStop(routeId, stopId);
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
     * Три времени прибытия транспорта по остановке с маршрутами и всеми остановками
     */
    @GetMapping("route/{routeId}/transport/{transportId}/stop/{stopId}")

    public StopOneTransportDto getByRouteAndTransport(@PathVariable Integer routeId, @PathVariable @NotNull Integer transportId, @PathVariable @NotNull Integer stopId) {

        Stop stop = stopService.getById(stopId);
        String stopName = stop.getName();
        String location = stop.getLocation().getName();
        Transport transport = transportService.getById(transportId);
        String transportType = transport.getType().name();
        String transportName = transport.getName();
        List<Time> nearest3Times = transportRouteService.get3NearestTimes(stopId, routeId);
        //TODO заменить поиск по остановке и транспорту поиском по остановке??
        List<TransportRouteNames> routes = transportRouteService.getRouteNames(routeId, transportId);
        List<TransportRouteStops> stops = transportRouteService.GetRouteStops(routeId);
        List<StopTransportDto.StopTransportInfoDto> transports = stopTimeService.getArrivalTransports(stopId);
        List<StopTransportDto.StopTransportTimeDto> routesTime = stopTimeService.getArrivalTimes(stopId);

        return transportRouteMapper.toBigDto(
                routeId,
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

    //TODO добавить запрос расписаний определенного транспорта
    //возврат всех остановок и всех маршрутов одного транспорта https://kogda.by/routes/brest/trolleybus/100
    //тип, название транспорта, локация и список DTO (маршруты с id и названиями остановок)
}