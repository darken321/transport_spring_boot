package com.example.transport2.repository;

import com.example.transport2.model.RouteStops;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteStopRepository extends JpaRepository<RouteStops, Integer> {
    List<RouteStops> findAllByStopId(Integer stopId);
    RouteStops findByStopIdAndRouteId(Integer stopId,Integer routeId);
}
