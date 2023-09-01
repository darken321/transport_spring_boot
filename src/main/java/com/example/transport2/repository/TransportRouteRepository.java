package com.example.transport2.repository;

import com.example.transport2.model.RouteStops;
import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportRoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportRouteRepository extends JpaRepository<TransportRoute, Integer> { //<тип сущности, тип айдишника>
}
