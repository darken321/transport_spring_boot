package com.example.transport2.repository;

import com.example.transport2.model.Stop;
import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StopRepository extends JpaRepository<Stop, Integer> {
    List<Stop> findAllByNameContaining(String name);
}