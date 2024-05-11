package com.example.transport2.repository;

import com.example.transport2.model.Location;
import com.example.transport2.model.Stop;
import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportType;
import com.example.transport2.projection.TransportInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransportRepository extends JpaRepository<Transport, Integer> { //<тип сущности, тип айдишника>
    Optional<Transport> findByNameAndType(String name, TransportType t);

    List<Transport> findByNameContainingAndType(String name, TransportType t);

    Optional<Transport> findByName(String name);

    Optional<Transport> findAllByName(String name);

    List<Transport> findAllByNameContaining(String name);

    List<Transport> findAllByType(TransportType t);

    Transport getTransportById(Integer id);

    List<Transport> findAllByLocationIdAndTypeOrderByName(int locationId, TransportType transportType);

    List<Transport> findAllByNameContainingIgnoreCase(String name);

    boolean existsByNameContainingIgnoreCaseAndLocationAndType(String name, Location location, TransportType type);

    /**
     * запрос информации по транспорту по id транспорта
     * @param transportId id транспорта
     * @return location, имя и тип транспорта
     */
    @Query(value = """
            SELECT l.name AS location,
                   transport.type AS type,
                   transport.name AS name
            FROM transport
                     JOIN location l on transport.location_id = l.id
            WHERE transport.id = :transportId

            """
            , nativeQuery = true)
    TransportInfo findTransportInfoById(Integer transportId);

    List<Transport> findByNameIgnoreCaseAndLocationAndType(String name, Location location, TransportType type);

}

