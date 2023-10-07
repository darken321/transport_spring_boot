package com.example.transport2.repository;

import com.example.transport2.model.StopTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface StopTimeRepository extends JpaRepository<StopTime, Integer> {
    //    List<StopTime> findByRouteStops_IdAndDayOfWeekOrderByTime(int routeStopsId, DayOfWeek dayOfWeek);
    @Query(value =
            "SELECT transport_route.transport_id,\n" +
                    "       transport.name,\n" +
                    "       transport.type,\n" +
                    "       transport_route.start_stop_id,\n" +
                    "       transport_route.end_stop_id,\n" + "" +
                    "       stop_time.time\n" +
                    "FROM stop_time\n" +
                    "         JOIN route_stops ON stop_time.route_stops_id = route_stops.id\n" +
                    "         JOIN transport_route ON route_stops.route_id = transport_route.id\n" +
                    "         jOIN transport ON transport_route.transport_id = transport.id\n" +
                    "WHERE stop_id = :stopId\n" +
                    "  AND day_of_week = :dayOfWeek\n" +
                    "  AND stop_time.time > :time\n" +
                    "ORDER BY time\n" +
                    "LIMIT :limit"
            , nativeQuery = true)
    List<Object[]> findSortedArrivalTimes(@Param("stopId") Integer stopId,
                                          @Param("dayOfWeek") String dayOfWeek,
                                          @Param("time") Time time,
                                          @Param("limit") int recordsLimit);

    @Query(value =
            "SELECT DISTINCT transport_route.transport_id,\n" +
                    "       transport.name,\n" +
                    "       transport.type\n" +
                    "FROM stop_time\n" +
                    "         JOIN route_stops ON stop_time.route_stops_id = route_stops.id\n" +
                    "         JOIN transport_route ON route_stops.route_id = transport_route.id\n" +
                    "         jOIN transport ON transport_route.transport_id = transport.id\n" +
                    "WHERE stop_id = :stopId\n" +
                    "  AND day_of_week = :dayOfWeek\n" +
                    "  AND stop_time.time > :time\n" +
                    "ORDER BY transport.name\n"
            , nativeQuery = true)
    List<Object[]> findSortedTransports(@Param("stopId") Integer stopId,
                                        @Param("dayOfWeek") String dayOfWeek,
                                        @Param("time") Time time);
}
//TODO написан SQL запрос
