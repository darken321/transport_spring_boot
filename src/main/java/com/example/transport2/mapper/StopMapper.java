package com.example.transport2.mapper;

import com.example.transport2.dto.StopDto;
import com.example.transport2.dto.StopSaveDto;
import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.model.*;
import com.example.transport2.repository.LocationRepository;
import com.example.transport2.repository.RouteStopRepository;
import com.example.transport2.service.StopTimeService;
import com.example.transport2.util.TimeUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
                .transports(getStopTransportInfoDtos(transports))
                //должен вернуть список DTO для транспортов, которые еще ходят по этой остановке
                .routesTime(getStopTransportTimeDtos(routes, stop.getId(), 1))
                .build();
    }

    private static List<StopTransportDto.StopTransportInfoDto> getStopTransportInfoDtos(List<Transport> transports) {
        List<StopTransportDto.StopTransportInfoDto> stopTransportInfoDtos = transports.stream()
                .map(t -> StopTransportDto.StopTransportInfoDto.builder()
                        .id(t.getId())
                        .name(t.getName())
                        .transportType(t.getType())
                        .build())
                .toList();
        return stopTransportInfoDtos;
    }

    //должен вернуть список DTO для транспортов, которые еще ходят по этой остановке, 
    private List<StopTransportDto.StopTransportTimeDto> getStopTransportTimeDtos(List<TransportRoute> routes, Integer stopId, int number) {
        List<StopTransportDto.StopTransportTimeDto> stopTransportTimeDtos = new ArrayList<>();
        for (TransportRoute transportRoute : routes) {
            int routeStopsId = routeStopRepository.findByStopIdAndRouteId(stopId, transportRoute.getId()).getId();
            LocalTime arrivalTime = stopTimeService.getArrivalTime(routeStopsId, LocalDate.now().getDayOfWeek());
            stopTransportTimeDtos.add(
                    StopTransportDto.StopTransportTimeDto.builder()
                            .id(transportRoute.getTransport().getId())
                            .name(transportRoute.getTransport().getName())
                            .transportType(transportRoute.getTransport().getType())
                            .routeName(transportRoute.getStartStop().getName() + " - " + transportRoute.getEndStop().getName())
                            .arrivalTime(arrivalTime)
                            .timeToArrival(TimeUtils.timeToArrival(arrivalTime))
                            .hoursToArrival(TimeUtils.getToArrivalHours(arrivalTime))
                            .minutesToArrival(TimeUtils.getToArrivalMinutes(arrivalTime))
                            .build()
            );
        }
        return stopTransportTimeDtos;

//        List<StopTransportDto.StopTransportTimeDto> stopTransportTimeDtos = routes.stream()
//                .map(t -> {
//                            LocalTime arrivalTime = stopTimeService.getArrivalTime(t.getId() , LocalDate.now().getDayOfWeek());
//                            return StopTransportDto.StopTransportTimeDto.builder()
//                                    .id(t.getId())
//                                    .name(t.getTransport().getName())
//                                    .transportType(t.getTransport().getType())
//                                    .routeName(t.getStartStop().getName() + " - " + t.getEndStop().getName())
//                                    .arrivalTime(stopTimeService.getArrivalTime(t.getId(), LocalDate.now().getDayOfWeek())) //stopId или t.getId()
//                                    .timeToArrival(TimeUtils.timeToArrival(arrivalTime))
//                                    .hoursToArrival(arrivalTime.getHour())
//                                    .minutesToArrival(arrivalTime.getMinute())
//                                    .build();
//                        }
//                )
//                .toList();
//        return stopTransportTimeDtos;
    }
}