package com.example.transport2.repository;

import com.example.transport2.model.TransportRoute;
import com.example.transport2.projection.TransportRouteNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransportRouteRepository extends JpaRepository<TransportRoute, Integer> { //<тип сущности, тип айдишника>
    /**
     * запрос возвращает список маршрутов по выбранному транспорту
     * при этом *маршрут, по которому делается запрос, должен быть первый в списке
     * @param routeId id маршрута в transport_route
     * @param transportId id транспорта
     * @return
     */
    @Query(value = """
            SELECT transport_route.id AS transportRouteId,
                   start_stop.name    AS startStopName,
                   end_stop.name      AS endStopName
            FROM transport_route
                     JOIN stop AS start_stop ON start_stop_id = start_stop.id
                     JOIN stop AS end_stop ON end_stop_id = end_stop.id
            WHERE transport_id = :transportId
            ORDER BY transport_route.id = :routeId DESC
                        """
            , nativeQuery = true)
    List<TransportRouteNames> findTransportRoute(@Param("routeId") Integer routeId, @Param("transportId") Integer transportId);
}
