package com.example.transport2.service;

import com.example.transport2.dto.FullRouteInfoDto;
import com.example.transport2.dto.RouteInfoDto;
import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.dto.route.RouteViewDto;
import com.example.transport2.mapper.RouteMapper;
import com.example.transport2.mapper.TransportRouteMapper;
import com.example.transport2.projection.StopRoutesInfo;
import com.example.transport2.projection.StopTransportInfo;
import com.example.transport2.projection.TransportRouteInfo;
import com.example.transport2.projection.TransportRouteStops;
import com.example.transport2.repository.RouteStopRepository;
import com.example.transport2.repository.StopTimeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static com.example.transport2.util.Constants.NUMBER_OF_RECORDS;

@Service
@Slf4j
@RequiredArgsConstructor
public class RouteService {
    private final StopTimeRepository stopTimeRepository;
    private final RouteMapper routeMapper;
    private final TransportRouteMapper transportRouteMapper;
    private final RouteStopRepository routeStopRepository;

    /**
     * @param stopId номер остановки
     * @return Возвращает DTO список и тип транспорта по остановке
     */

    public List<StopTransportDto.StopTransportInfoDto> getArrivalTransports(int stopId, LocalTime currentTime, DayOfWeek dayOfWeek) {
        List<StopRoutesInfo> sortedTransports = stopTimeRepository
                .findSortedTransports(stopId, dayOfWeek.name(), Time.valueOf(currentTime));
        return routeMapper.allToTransportDto(sortedTransports);
    }

    /**
     * @param stopId номер остановки
     * @return Возвращает DTO список и тип транспорта, время прибытия и название маршрута по остановке
     */
    public List<StopTransportDto.StopTransportTimeDto> getArrivalTimes(int stopId, LocalTime currentTime, DayOfWeek dayOfWeek) {
        List<StopTransportInfo> sortedArrivalTimes = stopTimeRepository
                .findSortedArrivalTimes(stopId, dayOfWeek.name(), Time.valueOf(currentTime), NUMBER_OF_RECORDS);

        return routeMapper.allToStopTransportDto(sortedArrivalTimes, currentTime);
    }

    public List<RouteViewDto> findAllById(Integer transportId) {
        List<TransportRouteStops> routeStops = routeStopRepository.findRouteStops(transportId);
        return routeMapper.allToRouteViewDto(routeStops);
    }

    public FullRouteInfoDto findById(Integer routeId) {
        TransportRouteInfo routeInfo = routeStopRepository.findRouteInfo(routeId);
        return transportRouteMapper.toDto(routeInfo);
    }
}