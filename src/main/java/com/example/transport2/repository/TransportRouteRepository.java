package com.example.transport2.repository;

import com.example.transport2.model.TransportRoute;
import com.example.transport2.projection.TransportRouteNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface TransportRouteRepository extends JpaRepository<TransportRoute, Integer> { //<тип сущности, тип айдишника>

    @Override
    Optional<TransportRoute> findById(Integer integer);

    /**
     * запрос возвращает список маршрутов по выбранному транспорту
     * при этом *маршрут, по которому делается запрос, должен быть первый в списке
     *
     * @param routeId     id маршрута в transport_route
     * @param transportId id транспорта
     * @return список маршрутов - начальная остановка, конечная остановка b transport_route_id
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

    /** запрос возвращает три ближайших времени прибытия транспорта на данную остановку
     * @param stopId Id остановки
     * @param dayOfWeek день недели
     * @param time время запроса, текущее время
     * @param routeId Id маршрута в transport_route
     * @return
     */

    @Query(value = """
            SELECT
                   stop_time.time AS time
            FROM stop_time
                     JOIN route_stops ON stop_time.route_stops_id = route_stops.id
                     JOIN transport_route ON route_stops.route_id = transport_route.id
            WHERE stop_id = :stopId
              AND day_of_week = :dayOfWeek
              AND stop_time.time > :time
              AND transport_route.id = :routeId
            ORDER BY time
            LIMIT '3'
            """
            , nativeQuery = true)
    List<Time> get3NearestTimes(@Param("stopId") Integer stopId,
                                @Param("dayOfWeek") String dayOfWeek,
                                @Param("time") Time time,
                                @Param("routeId") Integer routeId);
}
