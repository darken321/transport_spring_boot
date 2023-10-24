package com.example.transport2.mapper;

import com.example.transport2.dto.StopOneTransportDto;
import com.example.transport2.projection.TransportRouteNames;
import com.example.transport2.projection.TransportRouteStops;
import com.example.transport2.repository.TransportRouteRepository;
import com.example.transport2.service.StopService;
import com.example.transport2.service.StopTimeService;
import com.example.transport2.service.TransportRouteService;
import com.example.transport2.service.TransportService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class TransportRouteMapper {
    private final StopTimeService stopTimeService;
    private final StopService stopService;
    private final TransportService transportService;
    private final TransportRouteService transportRouteService;


    private StopOneTransportDto.RouteInfoDto toDto(@Valid TransportRouteNames route) {
        return StopOneTransportDto.RouteInfoDto.builder()
                .transportRouteId(route.getTransportRouteId())
                .startStopName(route.getStartStopName())
                .endStopName(route.getEndStopName())
                .build();
    }

    public List<StopOneTransportDto.RouteInfoDto> allToRouteDto(@Valid List<TransportRouteNames> routes) {
        return routes.stream()
                .map(this::toDto)
                .toList();
    }

    private StopOneTransportDto.StopInfoDto toDto(@Valid TransportRouteStops stop) {
        return StopOneTransportDto.StopInfoDto.builder()
                .stopId(stop.getStopId())
                .stopName(stop.getStopName())
                .build();
    }

    public List<StopOneTransportDto.StopInfoDto> allToStopDto(@Valid List<TransportRouteStops> stops) {
        return stops.stream()
                .map(this::toDto)
                .toList();
    }

    public StopOneTransportDto toBigDto(@Valid Integer routeId, @NotNull Integer transportId, @NotNull Integer stopId) {
        //TODO стоит ли выносить переменные в сервис?
        //что делать с временем в get3NearestTimes?
//        Time currentTime = Time.valueOf(LocalTime.of(16, 00));
//        String dayOfWeek = LocalDate.now().getDayOfWeek().name();
//        List<Time> times = transportRouteRepository.get3NearestTimes(stopId, dayOfWeek, currentTime, routeId);
//        List<TransportRouteNames> routeNames = transportRouteRepository.findTransportRoute(routeId, transportId);
//        List<TransportRouteStops> routeStops = transportRouteRepository.findRouteStops(routeId);
        return StopOneTransportDto.builder()
                .id(stopId)
                .stopName(stopService.getById(stopId).getName())
                .location(stopService.getById(stopId).getLocation().getName())
                //дублирующиеся запросы в БД??
                .transportType(transportService.getById(transportId).getType().name())
                .transportName(transportService.getById(transportId).getName())
                .nearest3Times(transportRouteService.get3NearestTimes(stopId, routeId)) //??
                .routes(allToRouteDto(transportRouteService.getRouteNames(routeId, transportId)))
                .stops(allToStopDto(transportRouteService.GetRouteStops(routeId)))
                .transports(stopTimeService.getArrivalTransports(stopId))
                .routesTime(stopTimeService.getArrivalTimes(stopId))
                .build();
    }
}