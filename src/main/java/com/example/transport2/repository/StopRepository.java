package com.example.transport2.repository;

import com.example.transport2.model.Location;
import com.example.transport2.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StopRepository extends JpaRepository<Stop, Integer> {
    List<Stop> findAllByNameContainingIgnoreCase(String name);
    boolean existsByNameLikeIgnoreCase(String name);
    List<Stop> findByNameIgnoreCaseAndLocation(String name, Location location);
    Optional<Stop> findStopById(Integer stopId);
}