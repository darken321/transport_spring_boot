package com.example.transport2.repository;

import com.example.transport2.model.ScheduleTime;
import com.example.transport2.model.StopTime;
import com.example.transport2.projection.StopTransportInfo;
import com.example.transport2.projection.Test;
import com.example.transport2.projection.TimeAndDayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface StopTimeRepository extends JpaRepository<StopTime, Integer> {
    /**
     * запрос расписания и дня недели по определенному маршруту и остановке из этого маршрута
     *
     * @param stopId       - id остановки
     * @param routeStopsId id строки routeStops, то есть "остановки в маршруте"
     * @return список времен прибытия и дней недели
     */
    @Query(value = "SELECT stop_time.time,\n" +
            "        CAST(day_of_week AS Varchar) AS dayOfWeek\n" +
            "FROM stop_time\n" +
            "JOIN route_stops on stop_time.route_stops_id = route_stops.id\n" +
            "WHERE stop_id = :stopId\n" +
            "  AND route_stops_id = :routeId\n" +
            "ORDER BY stop_time.time\n"
            , nativeQuery = true)
    List<TimeAndDayOfWeek> findSortedArrivalTimesSchedule(@Param("routeId") Integer routeStopsId, @Param("stopId") Integer stopId);

    @Query(value = "SELECT transport.name AS transportName,\n" +
            "       stop.name AS stopName,\n" +
            "       transport_route.id\n" +
            "FROM transport_route\n" +
            "         JOIN transport on transport_route.transport_id = transport.id\n" +
            "         JOIN stop on transport_route.start_stop_id = stop.id\n" +
            "ORDER BY transport_route.id"
            , nativeQuery = true)
    List<Test> test();


    //TODO поменять Object на соответствующий тип через projection
    //https://www.google.com/search?q=spring+data+projection

//    Integer getId();
//    String getTransportName();
//    String getTransportType();
//    Integer getStartStopId();
//    Integer getEndStopId();
//    Time getTime();

    @Query(value =
            "SELECT transport_route.transport_id AS id,\n" +
                    "       transport.name AS transportName,\n" +
                    "       transport.type AS transportType,\n" +
                    "       transport_route.start_stop_id AS startStopId,\n" +
                    "       transport_route.end_stop_id AS endStopId,\n" +
                    "       stop_time.time AS time\n" +
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
    List<StopTransportInfo> findSortedArrivalTimes(@Param("stopId") Integer stopId,
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