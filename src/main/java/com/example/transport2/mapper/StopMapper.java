package com.example.transport2.mapper;

import com.example.transport2.dto.StopDto;
import com.example.transport2.dto.stop.StopViewDto;
import com.example.transport2.dto.stop.StopEditDto;
import com.example.transport2.dto.stop.StopSaveDto;
import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.model.Stop;
import com.example.transport2.service.LocationService;
import com.example.transport2.service.RouteService;
import com.example.transport2.service.StopService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class StopMapper {
    LocationService locationService;
    RouteService routeService;
    StopService stopService;

    public Stop fromDto(@Valid StopViewDto dto) {
        return Stop.builder()
                .id(dto.getId())
                .name(dto.getName())
                .comment(dto.getComment())
                .location(locationService.getLocationById(dto.getLocationId()))
                .build();
    }

    public Stop fromDto(@Valid StopEditDto dto) {
        return Stop.builder()
                .id(dto.getId())
                .name(dto.getName())
                .comment(dto.getComment())
                .location(locationService.getLocationById(dto.getLocationId()))
                .build();
    }

    public Stop fromDto(@Valid StopSaveDto dto) {
        return Stop.builder()
                .name(dto.getName())
                .comment(dto.getComment())
                .location(locationService.getLocationById(dto.getLocationId()))
                .build();
    }

    public List<Stop> fromDto(@Valid List<StopViewDto> dtolist) {
        return dtolist.stream()
                .map(this::fromDto)
                .toList();
    }

    public StopViewDto toViewDto(@Valid Stop stop) {
        return StopViewDto.builder()
                .id(stop.getId())
                .name(stop.getName())
                .comment(stop.getComment())
                .locationId(stop.getLocation().getId())
                .build();
    }

    public StopDto toDto(@Valid Stop stop) {
        return StopDto.builder()
                .id(stop.getId())
                .name(stop.getName())
                .comment(stop.getComment())
                .locationId(stop.getLocation().getId())
                .locationName(stop.getLocation().getName())
                .build();
    }

    public List<StopDto> toDto(@Valid List<Stop> stopList) {
        return stopList.stream()
                .map(this::toDto)
                .toList();
    }
    public List<StopViewDto> toViewDto(@Valid List<Stop> stopList) {
        return stopList.stream()
                .map(this::toViewDto)
                .toList();
    }

    /**
     * @param stop   сущность остановки
     * @param routes список маршрутов по этой остановке
     * @return DTO для информации о транспорте по остановке
     */
    public StopTransportDto toBigTransportDto(@Valid Integer id, LocalTime currentTime, DayOfWeek dayOfWeek) {
        Stop stop = stopService.getById(id);
        return StopTransportDto.builder()
                .stopId(id)
                .stopName(stop.getName())
                .location(stop.getLocation().getName())
                .transports(routeService.getArrivalTransports(id, currentTime, dayOfWeek))
                .routesTime(routeService.getArrivalTimes(id, currentTime, dayOfWeek))
                .build();
    }
}