package com.example.transport2.mapper;

import com.example.transport2.dto.StopOneTransportDto;
import com.example.transport2.projection.TransportRouteNames;
import com.example.transport2.projection.TransportRouteStops;
import com.example.transport2.repository.TransportRouteRepository;
import com.example.transport2.service.StopTimeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class TransportRouteMapper {
    private final StopTimeService stopTimeService;
    private final TransportRouteRepository transportRouteRepository;


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
//    public <T, R> List<R> allToDto(@Valid List<T> list) {
//        return list.stream()
//                .map(this::toDto)
//                .toList();
//    }

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

        Time currentTime = Time.valueOf(LocalTime.of(16, 00));
        String dayOfWeek = LocalDate.now().getDayOfWeek().name();
        List<Time> times = transportRouteRepository.find3NearestTimes(stopId, dayOfWeek, currentTime, routeId);
        List<TransportRouteNames> routeNames = transportRouteRepository.findTransportRoute(routeId, transportId);
        List<TransportRouteStops> routeStops = transportRouteRepository.findRouteStops(routeId);
        return StopOneTransportDto.builder()
                .id(stopId)
                .stopName("Имя остановки")
                .location("Локация остановки")
                .transportType("Тип транспорта")
                .transportName("Название транспорта")
                .nearest3Times(times)
                .routes(allToRouteDto(routeNames))
                .stops(allToStopDto(routeStops))
                .transports(stopTimeService.getArrivalTransports(stopId))
                .routesTime(stopTimeService.getArrivalTimes(stopId))
                .build();
    }

}