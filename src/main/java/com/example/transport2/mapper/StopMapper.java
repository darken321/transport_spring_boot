package com.example.transport2.mapper;

import com.example.transport2.dto.StopDto;
import com.example.transport2.dto.StopSaveDto;
import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.model.Location;
import com.example.transport2.model.Stop;
import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportRoute;
import com.example.transport2.repository.LocationRepository;
import com.example.transport2.service.StopTimeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class StopMapper {
    LocationRepository locationRepository;
    StopTimeService stopTimeService;

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
                        .id(locationRepository.findByName(dto.getLocation()).get().getId()) //вытащить id по location.name ??
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

    public StopTransportDto toDto(@Valid Stop stop, List<Transport> transports, List<TransportRoute> routes) {
        return StopTransportDto.builder()
                .id(stop.getId())
                .name(stop.getName())
                .location(stop.getLocation().getName())
                .transports(transports.stream()
                        .map(t -> StopTransportDto.StopTransportInfoDto.builder()
                                .id(t.getId())
                                .name(t.getName())
                                .transportType(t.getType())
                                .build())
                        .toList()
                )
                .routesTime(routes.stream()
                        .map(t -> StopTransportDto.StopTransportTimeDto.builder()
                                .id(t.getId())
                                .name(t.getTransport().getName())
                                .transportType(t.getTransport().getType())
                                .routeName(t.getStartStop().getName() + " - " + t.getEndStop().getName())
                                .arrivalTime(stopTimeService.getArrivalTime(t.getId(), LocalDate.now().getDayOfWeek()))
//                                .timeToArrival(Duration.between(LocalTime.now(),stopTimeService.getArrivalTime(t.getId(), LocalDate.now().getDayOfWeek())))
                                .build())
                        .toList()
                )
                .build();
    }
}