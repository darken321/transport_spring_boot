package com.example.transport2.mapper;

import com.example.transport2.dto.StopDto;
import com.example.transport2.dto.StopSaveDto;
import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.dto.StopTransportInfoName;
import com.example.transport2.model.Location;
import com.example.transport2.model.Stop;
import com.example.transport2.model.TransportRoute;
import com.example.transport2.projection.StopTransportInfo;
import com.example.transport2.repository.LocationRepository;
import com.example.transport2.repository.RouteStopRepository;
import com.example.transport2.repository.StopRepository;
import com.example.transport2.service.StopTimeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class StopMapper {
    LocationRepository locationRepository;
    StopTimeService stopTimeService;
    RouteStopRepository routeStopRepository;
    StopRepository stopRepository;

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

    /**
     * @param stop   сущность остановки
     * @param routes
     * @return DTO для информации о транспорте по остановке
     */
    public StopTransportDto toDto(@Valid Stop stop, List<TransportRoute> routes) {
        return StopTransportDto.builder()
                .id(stop.getId())
                .name(stop.getName())
                .location(stop.getLocation().getName())
                .transports(getStopTransportInfoDto(stop.getId(), routes.get(0)))
                .routesTime(getStopTransportTimeDto(stop.getId(), routes.get(0)))
                .build();
    }

    /**
     * @param stopId         id остановки
     * @param transportRoute маршрут транспорта
     * @return короткий список транспорта по остановке маршрута - ID, имя, тип
     */
    private @NotNull List<StopTransportDto.StopTransportInfoDto> getStopTransportInfoDto(Integer stopId, TransportRoute transportRoute) {

        int routeStopsId = routeStopRepository.findByStopIdAndRouteId(stopId, transportRoute.getId()).getId();
        return stopTimeService.getArrivalTransports(routeStopsId);
    }

    /**
     * @param stopId
     * @param transportRoute
     * @return список транспорта (с его маршрутом) по остановке сортированный по времени прибытия
     */
    private @NotNull List<StopTransportDto.StopTransportTimeDto> getStopTransportTimeDto(Integer stopId, TransportRoute transportRoute) {

        int routeStopsId = routeStopRepository.findByStopIdAndRouteId(stopId, transportRoute.getId()).getId();
        return stopTimeService.getArrivalTimes(routeStopsId);
    }

    //TODO тут проблема
//    public List<StopTransportInfoName> getStopTransportInfoNames(List<StopTransportInfo> sortedArrivalTimes) {
//        List<StopTransportInfoName> newList = new ArrayList<>();
//        for (StopTransportInfo e : sortedArrivalTimes) {
//            newList.add(StopTransportInfoName.builder()
//                    .id(e.getId())
//                    .transportName(e.getTransportName())
//                    .transportType(e.getTransportType())
//                    .startStopId(e.getStartStopId())
//                    .endStopId(e.getEndStopId())
//                    .time(e.getTime())
//                    .routeName(getRouteName(e))
//                    .build());
//        }
//        return newList;
//    }
//    private String getRouteName(StopTransportInfo e) {
//        return stopRepository.findStopById(e.getStartStopId()).get().getName() + " - "
//                + stopRepository.findStopById(e.getEndStopId()).get().getName();
//    }
}