package com.example.transport2.repository;

import com.example.transport2.model.RouteStops;
import com.example.transport2.projection.TransportRouteStops;
import com.example.transport2.projection.TransportRoutesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteStopRepository extends JpaRepository<RouteStops, Integer> {
    List<RouteStops> findAllByStopId(Integer stopId);
    RouteStops findByStopIdAndRouteId(Integer stopId,Integer routeId);
    /**
     * возвращает список информации о маршрутах транспорта.
     * @param transportId - id транспорта
     * @return названия начальной и конечной остановки, название маршрута, id маршрута
     */
    @Query(value = """
            SELECT transport_route.id AS id,
                   start_stop.name AS startStopName,
                   end_stop.name AS endStopName,
                   transport_route.name AS routeName
            FROM transport_route
                     JOIN stop AS start_stop ON transport_route.start_stop_id = start_stop.id
                     JOIN stop AS end_stop ON transport_route.end_stop_id = end_stop.id
            WHERE transport_id = :id
            """
            , nativeQuery = true)
    List<TransportRoutesInfo> findTransportRoutes(@Param("id") Integer transportId);

    /**
     * запрос возвращает список остановок по выбранному маршруту
     * отсортированный по полю "порядок остановок" stop_order
     * @param routeId id маршрута
     * @return список названий остановок c их id
     */
    @Query(value = """
            SELECT stop_id AS stopId,
                   name AS stopName
            FROM route_stops
                    JOIN stop on route_stops.stop_id = stop.id
            WHERE route_id=:routeId
            ORDER BY stop_order
            """
            , nativeQuery = true)
    List<TransportRouteStops> findRouteStops(@Param("routeId") Integer routeId);
}