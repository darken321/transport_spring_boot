package com.example.transport2.repository;

import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Integer> { //<тип сущности, тип айдишника>
    Optional<Transport> findByNameAndType(String name, TransportType t);
    Optional<Transport> findByName(String name);
    List<Transport> findAllByType(TransportType t);
}
