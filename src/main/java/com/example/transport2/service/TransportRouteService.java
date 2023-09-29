package com.example.transport2.service;

import com.example.transport2.model.RouteStops;
import com.example.transport2.model.TransportRoute;
import com.example.transport2.repository.RouteStopRepository;
import com.example.transport2.repository.TransportRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportRouteService {
    private final TransportRouteRepository transportRouteRepository;
    private final RouteStopRepository routeStopRepository;

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
}
