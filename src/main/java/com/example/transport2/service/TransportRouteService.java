package com.example.transport2.service;

import com.example.transport2.mapper.RoutesMapper;
import com.example.transport2.model.RouteStops;
import com.example.transport2.model.TransportRoute;
import com.example.transport2.projection.TimeAndDayOfWeek;
import com.example.transport2.projection.TransportRouteNames;
import com.example.transport2.projection.TransportRouteStops;
import com.example.transport2.repository.RouteStopRepository;
import com.example.transport2.repository.StopTimeRepository;
import com.example.transport2.repository.TransportRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportRouteService {
    private final RouteStopRepository routeStopRepository;
    private final StopTimeRepository stopTimeRepository;
    private final RoutesMapper routesMapper;
    private final TransportRouteRepository transportRouteRepository;


    /**
     * Возвращает список маршрутов, которые проходят по остановке
     *
     * @param stopId id остановки
     * @return
     */
    public List<TransportRoute> getByStopId(Integer stopId) {
        //итого нужен список маршрутов TransportRoute, которые проходят по остановке stopId
        //по stop id найти список маршрутов, которые проходят по этой остановке
        List<RouteStops> allByStopId = routeStopRepository.findAllByStopId(stopId);
        //вернул список маршрутов transportRoutes
        return allByStopId.stream()
                .map(RouteStops::getRoute)
                .toList();
    }

    public List<TimeAndDayOfWeek> getByRouteAndStop(Integer routeStopsId, Integer stopId) {
        return stopTimeRepository.findSortedArrivalTimesSchedule(routeStopsId, stopId);
    }

    public List<Time> get3NearestTimes(Integer stopId, Integer routeId) {
        Time currentTime = Time.valueOf(LocalTime.of(16, 00));
        String dayOfWeek = LocalDate.now().getDayOfWeek().name();
        return transportRouteRepository.get3NearestTimes(stopId, dayOfWeek, currentTime, routeId);
    }

    public List<TransportRouteNames> getRouteNames(Integer routeId, Integer transportId) {
        return transportRouteRepository.findTransportRoute(routeId, transportId);
    }

    public List<TransportRouteStops> GetRouteStops(Integer routeId) {
        return transportRouteRepository.findRouteStops(routeId);
    }

}

