package com.example.transport2.mapper;

import com.example.transport2.dto.StopDto;
import com.example.transport2.dto.StopSaveDto;
import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.model.Location;
import com.example.transport2.model.Stop;
import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportRoute;
import com.example.transport2.repository.LocationRepository;
import com.example.transport2.repository.RouteStopRepository;
import com.example.transport2.service.StopTimeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class StopMapper {
    LocationRepository locationRepository;
    StopTimeService stopTimeService;
    RouteStopRepository routeStopRepository;

    public Stop fromDto(@Valid StopDto dto) {
        return Stop.builder()
                .id(dto.getId())
                .name(dto.getName())
                .location(Location.builder().name(dto.getLocation()).build())
                .build();
    }

    public Stop fromDto(@Valid StopSaveDto dto) {
        return Stop.builder()
                .name(dto.getName())
                .location(Location.builder()
                        .id(locationRepository.findByName(dto.getLocation()).get().getId())
                        .name(dto.getLocation())
                        .build())
                .build();
    }

    public List<Stop> fromDto(@Valid List<StopDto> dtolist) {
        return dtolist.stream()
                .map(this::fromDto)
                .toList();
    }

    public StopDto toDto(@Valid Stop stop) {
        return StopDto.builder()
                .id(stop.getId())
                .name(stop.getName())
                .location(stop.getLocation().getName())
                .build();
    }

    public List<StopDto> toDto(@Valid List<Stop> stopList) {
        return stopList.stream()
                .map(this::toDto)
                .toList();
    }

    public StopTransportDto toDto(@Valid Stop stop, List<TransportRoute> routes) {
        return StopTransportDto.builder()
                .id(stop.getId())
                .name(stop.getName())
                .location(stop.getLocation().getName())
                .transports(getStopTransportInfoDto(stop.getId(), routes.get(0)))
                .routesTime(getStopTransportTimeDto(stop.getId(), routes.get(0)))
                .build();
    }

    private @NotNull List<StopTransportDto.StopTransportInfoDto> getStopTransportInfoDto(Integer stopId, TransportRoute transportRoute) {
        //TODO установка времени и дня недели
//        Time currentTime = Time.valueOf(LocalTime.now());
        Time currentTime = Time.valueOf(LocalTime.of(16, 00));
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
//        DayOfWeek dayOfWeek = DayOfWeek.TUESDAY;

        int routeStopsId = routeStopRepository.findByStopIdAndRouteId(stopId, transportRoute.getId()).getId();
        return stopTimeService.getArrivalTransports(routeStopsId, dayOfWeek, currentTime);
    }

    private @NotNull List<StopTransportDto.StopTransportTimeDto> getStopTransportTimeDto(Integer stopId, TransportRoute transportRoute) {
        //TODO установка времени и дня недели
//        Time currentTime = Time.valueOf(LocalTime.now());
        Time currentTime = Time.valueOf(LocalTime.of(16, 00));
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
//        DayOfWeek dayOfWeek = DayOfWeek.TUESDAY;

        int routeStopsId = routeStopRepository.findByStopIdAndRouteId(stopId, transportRoute.getId()).getId();
        return stopTimeService.getArrivalTimes(routeStopsId, dayOfWeek, currentTime);
    }
}