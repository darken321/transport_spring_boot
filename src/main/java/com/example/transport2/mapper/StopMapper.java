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
import com.example.transport2.util.TimeUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class StopMapper {
    LocationRepository locationRepository;
    StopTimeService stopTimeService;
    RouteStopRepository routeStopRepository;
    private final static int TIMES_NUMBER = 3;

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

    public StopTransportDto toDto(@Valid Stop stop, List<Transport> transports, List<TransportRoute> routes) {
        return StopTransportDto.builder()
                .id(stop.getId())
                .name(stop.getName())
                .location(stop.getLocation().getName())
                .transports(getStopTransportInfoDtos(transports))
                //должен вернуть список DTO для транспортов, которые еще ходят по этой остановке
                .routesTime(routes.stream()
                        .map(t -> getStopTransportTimeDto(stop.getId(), TIMES_NUMBER, t))
                        .toList()
                )
                .build();
    }

    private static List<StopTransportDto.StopTransportInfoDto> getStopTransportInfoDtos(List<Transport> transports) {
        return transports.stream()
                .map(t -> StopTransportDto.StopTransportInfoDto.builder()
                        .id(t.getId())
                        .name(t.getName())
                        .transportType(t.getType())
                        .build())
                .toList();
    }


    private StopTransportDto.StopTransportTimeDto getStopTransportTimeDto(Integer stopId, int number, TransportRoute t) {
//        LocalTime currentTime = LocalTime.now();
        LocalTime currentTime = LocalTime.of(10, 18);
        int routeStopsId = routeStopRepository.findByStopIdAndRouteId(stopId, t.getId()).getId();
        // Если транспорта после этого времени нет то длина arrivalTimes = 0 и ниже вылетает ошибка, не может извлечь нулевой элемент
        List<LocalTime> arrivalTimes = stopTimeService.getArrivalTimes(routeStopsId, LocalDate.now().getDayOfWeek(), number, currentTime);
        return StopTransportDto.StopTransportTimeDto.builder()
                .id(t.getTransport().getId())
                .name(t.getTransport().getName())
                .transportType(t.getTransport().getType())
                .routeName(t.getStartStop().getName() + " - " + t.getEndStop().getName())
                .arrivalTimes(arrivalTimes)
                .timeToArrival(TimeUtils.timeToArrival(arrivalTimes.get(0), currentTime))
                .hoursToArrival(TimeUtils.getToArrivalHours(arrivalTimes.get(0), currentTime))
                .minutesToArrival(TimeUtils.getToArrivalMinutes(arrivalTimes.get(0), currentTime))
                .build();
    }
}