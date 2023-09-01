package com.example.transport2.repository;

import com.example.transport2.model.RouteStops;
import com.example.transport2.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteStopRepository extends JpaRepository<RouteStops, Integer> { //<тип сущности, тип айдишника>
}
