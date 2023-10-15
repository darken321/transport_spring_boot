package com.example.transport2.service;

import com.example.transport2.dto.ScheduleDto;
import com.example.transport2.mapper.RoutesMapper;
import com.example.transport2.model.RouteStops;
import com.example.transport2.model.TransportRoute;
import com.example.transport2.projection.StopTransportInfo;
import com.example.transport2.projection.TimeAndDayOfWeek;
import com.example.transport2.repository.RouteStopRepository;
import com.example.transport2.repository.StopRepository;
import com.example.transport2.repository.StopTimeRepository;
import com.example.transport2.repository.TransportRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportRouteService {
    private final TransportRouteRepository transportRouteRepository;
    private final RouteStopRepository routeStopRepository;
    private final StopTimeRepository stopTimeRepository;
    private final RoutesMapper routesMapper;
    private final StopRepository stopRepository;


    public List<TransportRoute> getByStopId(Integer stopId) {
        //итого нужен список маршрутов TransportRoute, которые проходят по остановке stopId

        //по stop id найти список маршрутов, которые проходят по этой остановке
        List<RouteStops> allByStopId = routeStopRepository.findAllByStopId(stopId);

        //вернул список маршрутов transportRoutes
        List<TransportRoute> transportRoutes = allByStopId.stream()
                .map(RouteStops::getRoute)
                .toList();

        return transportRoutes;
    }

    public List<ScheduleDto> getByRouteAndStop(Integer routeStopsId, Integer stopId) {
        List<TimeAndDayOfWeek> arrivalTimesSchedule = stopTimeRepository.findSortedArrivalTimesSchedule(routeStopsId, stopId);
        return routesMapper.allToScheduleDto(arrivalTimesSchedule);
    }

    public String getRouteName(StopTransportInfo info) {
        return stopRepository.findStopById(info.getStartStopId()).get().getName() + " - " +
                stopRepository.findStopById(info.getEndStopId()).get().getName();
    }
}

