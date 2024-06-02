package com.example.transport2.mapper;

import com.example.transport2.dto.RouteInfoDto;
import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.dto.route.RouteViewDto;
import com.example.transport2.model.TransportType;
import com.example.transport2.projection.StopRoutesInfo;
import com.example.transport2.projection.StopTransportInfo;
import com.example.transport2.projection.TransportRouteInfo;
import com.example.transport2.projection.TransportRouteStops;
import com.example.transport2.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RouteMapper {

    private StopTransportDto.StopTransportTimeDto stopTransportDto(StopTransportInfo info, LocalTime currentTime) {
        return StopTransportDto.StopTransportTimeDto.builder()
                .transportId(info.getId())
                .transportName(info.getTransportName())
                .transportType(TransportType.valueOf(info.getTransportType()))
                .startStopName(info.getStartStopName())
                .endStopName(info.getEndStopName())
                .arrivalTime(info.getTime())
                .timeToArrival(TimeUtils.timeToArrival(info.getTime(), currentTime))
                .hoursToArrival(TimeUtils.getToArrivalHours(info.getTime(), currentTime))
                .minutesToArrival(TimeUtils.getToArrivalMinutes(info.getTime(), currentTime))
                .build();
    }

    public List<StopTransportDto.StopTransportTimeDto>
    allToStopTransportDto(List<StopTransportInfo> sortedArrivalTimes, LocalTime currentTime) {
        return sortedArrivalTimes.stream()
                .map(times -> stopTransportDto(times, currentTime))
                .toList();
    }

    private StopTransportDto.StopTransportInfoDto stopTransportInfoDto(StopRoutesInfo info) {
        return StopTransportDto.StopTransportInfoDto.builder()
                .transportId(info.getId())
                .transportName(info.getTransportName())
                .transportType(TransportType.valueOf(info.getTransportType()))
                .build();
    }

    public List<StopTransportDto.StopTransportInfoDto> allToTransportDto(List<StopRoutesInfo> sortedTransportTypes) {
        return sortedTransportTypes.stream()
                .map(this::stopTransportInfoDto)
                .toList();
    }

    private RouteViewDto toRouteViewDto(TransportRouteStops trs) {
        return RouteViewDto.builder()
                .routeId(trs.getRouteId())
                .stopId(trs.getStopId())
                .stopOrder(trs.getStopNumber())
                .stopName(trs.getStopName())
                .stopComment(trs.getComment())
                .locationId(trs.getLocationId())
                .locationName(trs.getLocationName())
                .build();
    }
    public List<RouteViewDto> allToRouteViewDto(List<TransportRouteStops> routeStops) {
        return routeStops.stream()
                .map(this::toRouteViewDto)
                .toList();
    }
}