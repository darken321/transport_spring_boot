package com.example.transport2.repository;

import com.example.transport2.model.Location;
import com.example.transport2.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    Optional<Location> findByName(String name);
    List<Location> findAllByName(String name);
}