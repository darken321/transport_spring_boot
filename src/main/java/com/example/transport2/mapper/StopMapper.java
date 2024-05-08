package com.example.transport2.mapper;

import com.example.transport2.dto.StopDto;
import com.example.transport2.dto.StopEditDto;
import com.example.transport2.dto.StopSaveDto;
import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.model.Location;
import com.example.transport2.model.Stop;
import com.example.transport2.repository.LocationRepository;
import com.example.transport2.repository.RouteStopRepository;
import com.example.transport2.repository.StopRepository;
import com.example.transport2.service.RouteService;
import com.example.transport2.service.stop.StopService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class StopMapper {
    LocationRepository locationRepository;
    RouteService routeService;
    StopService stopService;
    RouteStopRepository routeStopRepository;
    StopRepository stopRepository;

    public Stop fromDto(@Valid StopDto dto) {
        return Stop.builder()
                .id(dto.getId())
                .name(dto.getName())
                .location(Location.builder().name(dto.getLocation()).build())
                .build();
    }

    public Stop fromDto(@Valid StopEditDto dto) {
        return Stop.builder()
                .id(dto.getId())
                .name(dto.getName())
                .location(Location.builder()
                        .id(locationRepository.findByName(dto.getLocation()).get().getId())
                        .name(dto.getLocation())
                        .build())
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