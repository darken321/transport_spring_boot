package com.example.transport2.mapper;

import com.example.transport2.dto.StopOneTransportDto;
import com.example.transport2.projection.TransportRouteNames;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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

    public List<StopOneTransportDto.RouteInfoDto> allToDto(@Valid List<TransportRouteNames> routes) {
        return routes.stream()
                .map(this::toDto)
                .toList();
    }
}