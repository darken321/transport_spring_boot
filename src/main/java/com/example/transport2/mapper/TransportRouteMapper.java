package com.example.transport2.mapper;

import com.example.transport2.dto.StopOneTransportDto;
import com.example.transport2.dto.StopTransportDto;
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

    public StopOneTransportDto toBigDto(@Valid @NotNull Integer stopId,
                                        String stopName,
                                        String location,
                                        String transportType,
                                        String transportName,
                                        List<Time> nearest3Times,
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
}