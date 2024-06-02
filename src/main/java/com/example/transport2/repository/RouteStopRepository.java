package com.example.transport2.repository;

import com.example.transport2.model.RouteStops;
import com.example.transport2.projection.TransportRouteStops;
import com.example.transport2.projection.TransportRouteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteStopRepository extends JpaRepository<RouteStops, Integer> {
    List<RouteStops> findAllByStopId(Integer stopId);

    RouteStops findByStopIdAndRouteId(Integer stopId, Integer routeId);

    /**
     * возвращает список информации о маршрутах одного транспорта.
     *
     * @param transportId - id транспорта
     * @return названия начальной и конечной остановки, название маршрута, id маршрута
     */
    @Query(value = """
            SELECT transport_route.id AS id,
                   start_stop.name AS startStopName,
                   start_location.name AS startStopLocation,
                   end_stop.name AS endStopName,
                   end_location.name AS endStopLocation,
                   transport_route.name AS routeName
            FROM transport_route
                     JOIN stop AS start_stop ON transport_route.start_stop_id = start_stop.id
                     JOIN stop AS end_stop ON transport_route.end_stop_id = end_stop.id
                     JOIN location AS start_location on start_stop.location_id = start_location.id
                     JOIN location AS end_location on end_stop.location_id = end_location.id
            WHERE transport_id = :id
                        """
            , nativeQuery = true)
    List<TransportRouteInfo> findTransportRoutes(@Param("id") Integer transportId);

    /**
     * возвращает список информации об одном маршруте.
     *
     * @param routeId - id маршрута
     * @return названия и локации начальной и конечной остановки, название маршрута, id маршрута
     */
    @Query(value = """
            SELECT transport_route.id AS id,
                   start_stop.name AS startStopName,
                   start_location.name AS startStopLocation,
                   end_stop.name AS endStopName,
                   end_location.name AS endStopLocation,
                   transport_route.name AS routeName
            FROM transport_route
                     JOIN stop AS start_stop ON transport_route.start_stop_id = start_stop.id
                     JOIN stop AS end_stop ON transport_route.end_stop_id = end_stop.id
                     JOIN location AS start_location on start_stop.location_id = start_location.id
                     JOIN location AS end_location on end_stop.location_id = end_location.id
            WHERE transport_route.id = :id
                                    """
            , nativeQuery = true)
    TransportRouteInfo findRouteInfo(@Param("id") Integer routeId);

    /**
     * запрос возвращает список остановок по выбранному маршруту
     * отсортированный по полю "порядок остановок" stop_order
     *
     * @param routeId id маршрута
     * @return список названий остановок c их id
     */
    @Query(value = """
            SELECT route_stops.id AS routeId,
                   stop.id AS stopId,
                   route_stops.stop_order AS stopNumber,
                   stop.name AS stopName,
                   stop.comment AS comment,
                   location.name AS locationName,
                   location.id AS locationId
            FROM route_stops
                     JOIN stop ON route_stops.stop_id = stop.id
                     JOIN location ON stop.location_id = location.id
            WHERE route_stops.route_id = :routeId
            ORDER BY route_stops.stop_order
            """, nativeQuery = true)
    List<TransportRouteStops> findRouteStops(@Param("routeId") Integer routeId);
}