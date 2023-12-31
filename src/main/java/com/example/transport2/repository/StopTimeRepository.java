package com.example.transport2.repository;

import com.example.transport2.model.StopTime;
import com.example.transport2.projection.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface StopTimeRepository extends JpaRepository<StopTime, Integer> {
    /**
     * Возвращает расписание - время и день недели по определенному маршруту и остановке из этого маршрута
     * @param stopId       id остановки
     * @param routeId route_id из route_stops
     * @return список времен прибытия и дней недели
     */
    @Query(value = """
            SELECT stop_time.time,
                    CAST(day_of_week AS Varchar) AS dayOfWeek
            FROM stop_time
            JOIN route_stops on stop_time.route_stops_id = route_stops.id
            WHERE stop_id = :stopId
               AND route_id = :routeId
            ORDER BY stop_time.time
            """
            , nativeQuery = true)
    List<TimeAndDayOfWeek> findSortedArrivalTimesSchedule(@Param("routeId") Integer routeId, @Param("stopId") Integer stopId);

    /**
     * Возвращает расписание одной остановки в конкретном маршруте
     * @param stopId Id остановки
     * @param dayOfWeek день недели
     * @param time время, после которого ищется в базе
     * @param recordsLimit сколько записей искать
     * @return Список: id транспорта, имя и тип транспорта, название начальной и конечной остановки, время прибытия
     */
    @Query(value ="""
            SELECT transport_route.transport_id AS id,
                   transport.name AS transportName,
                   transport.type AS transportType,
                   start_stop.name AS startStopName,
                   end_stop.name AS endStopName,
                   stop_time.time AS time
            FROM stop_time
                     JOIN route_stops ON stop_time.route_stops_id = route_stops.id
                     JOIN transport_route ON route_stops.route_id = transport_route.id
                     jOIN transport ON transport_route.transport_id = transport.id
                     JOIN stop AS start_stop ON transport_route.start_stop_id = start_stop.id
                     JOIN stop AS end_stop ON transport_route.end_stop_id = end_stop.id
            WHERE stop_id = :stopId
              AND day_of_week = :dayOfWeek
              AND stop_time.time > :time
            ORDER BY time
            LIMIT :limit
            """
            , nativeQuery = true)
        List<StopTransportInfo> findSortedArrivalTimes(@Param("stopId") Integer stopId,
                                                   @Param("dayOfWeek") String dayOfWeek,
                                                   @Param("time") Time time,
                                                   @Param("limit") int recordsLimit);

    /**
     * Возвращает список транспорта, который еше будет проходить по данной остановке после заданного времени
     * @param stopId id остановки
     * @param dayOfWeek день недели для поиска в БД
     * @param time время, после которого происходит поиск
     * @return список: id транспорта, имя транспорта, тип транспорта
     */
    @Query(value ="""
            SELECT DISTINCT transport_route.transport_id AS id ,
                            transport.name AS transportName,
                            transport.type AS transportType
                     FROM stop_time
                              JOIN route_stops ON stop_time.route_stops_id = route_stops.id
                              JOIN transport_route ON route_stops.route_id = transport_route.id
                              jOIN transport ON transport_route.transport_id = transport.id
                     WHERE stop_id = :stopId
                       AND day_of_week = :dayOfWeek
                       AND stop_time.time > :time
                     ORDER BY transportName
                     """
            , nativeQuery = true)
    List<StopRoutesInfo> findSortedTransports(@Param("stopId") Integer stopId,
                                              @Param("dayOfWeek") String dayOfWeek,
                                              @Param("time") Time time);
}