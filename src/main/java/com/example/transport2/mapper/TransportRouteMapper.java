package com.example.transport2.mapper;

import com.example.transport2.dto.*;
import com.example.transport2.dto.StopInfoDto;
import com.example.transport2.projection.TransportInfo;
import com.example.transport2.projection.TransportRouteNames;
import com.example.transport2.projection.TransportRouteStops;
import com.example.transport2.projection.TransportRoutesInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class TransportRouteMapper {

    public StopOneTransportDto toBigDto(@Valid @NotNull Integer stopId,
                                        String stopName,
                                        String location,
                                        String transportType,
                                        String transportName,
                                        List<LocalTime> nearest3Times,
                                        List<TransportRouteNames> routes,
                                        List<TransportRouteStops> stops,
                                        List<StopTransportDto.StopTransportInfoDto> transports,
                                        List<StopTransportDto.StopTransportTimeDto> routesTime
    ) {
        return StopOneTransportDto.builder()
                .id(stopId)
                .stopName(stopName)
                .location(location)
                .transportType(transportType)
                .transportName(transportName)
                .nearest3Times(nearest3Times)
                .routes(allToRouteDto(routes))
                .stops(allToStopDto(stops))
                .transports(transports)
                .routesTime(routesTime)
                .build();
    }

    private List<RouteInfoDto> allToRouteDto(@Valid List<TransportRouteNames> routes) {
        return routes.stream()
                .map(this::toDto)
                .toList();
    }

    public List<StopInfoDto> allToStopDto(@Valid List<TransportRouteStops> stops) {
        return stops.stream()
                .map(this::toDto)
                .toList();
    }

    private RouteInfoDto toDto(@Valid TransportRouteNames route) {
        return RouteInfoDto.builder()
                .transportRouteId(route.getTransportRouteId())
                .startStopName(route.getStartStopName())
                .endStopName(route.getEndStopName())
                .build();
    }

    private StopInfoDto toDto(@Valid TransportRouteStops stop) {
        return StopInfoDto.builder()
                .stopId(stop.getStopId())
                .stopName(stop.getStopName())
                .comment(stop.getComment())
                .locationId(stop.getLocationId())
                .locationName(stop.getLocationName())
                .build();
    }

    public FullScheduleOneRouteTransportDto toScheduleDto(@Valid @NotNull Integer stopId,
                                                          String stopName,
                                                          String location,
                                                          String transportType,
                                                          String transportName,
                                                          List<TransportRouteNames> routes,
                                                          List<TransportRouteStops> stops,
                                                          List<TimeDayOfWeekDto> scheduleDto) {
        return FullScheduleOneRouteTransportDto.builder()
                .stopId(stopId)
                .stopName(stopName)
                .location(location)
                .transportType(transportType)
                .transportName(transportName)
                .routes(allToRouteDto(routes))
                .stops(allToStopDto(stops))
                .schedule(scheduleDto)
                .build();
    }

    public OneTransportRoutesDto allToDto(TransportInfo info, List<TransportRoutesInfo> transportRoutes) {
        return OneTransportRoutesDto.builder()
                .location(info.getLocation())
                .transportName(info.getName())
                .transportType(info.getType())
                .routes(toDto(transportRoutes))
                .build();
    }

    private List<FullRouteInfoDto> toDto(List<TransportRoutesInfo> transportRoutes) {
        return transportRoutes.stream()
                .map(this::toDto)
                .toList();
    }

    private FullRouteInfoDto toDto(TransportRoutesInfo tr) {
        return FullRouteInfoDto.builder()
                .transportRouteId(tr.getId())
                .startStopName(tr.getStartStopName())
                .endStopName(tr.getEndStopName())
                .transportRouteName(tr.getRouteName())
                .build();
    }
}
