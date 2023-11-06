package com.example.transport2.service;

import com.example.transport2.projection.TransportRouteStops;
import com.example.transport2.repository.RouteStopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RouteStopService {
    private final RouteStopRepository routeStopRepository;

    public List<TransportRouteStops> getRouteStops(Integer routeId) {
        return routeStopRepository.findRouteStops(routeId);
    }
}