package com.example.transport2.repository;

import com.example.transport2.model.RouteStops;
import com.example.transport2.model.Stop;
import com.example.transport2.model.StopTime;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface StopTimeRepository extends JpaRepository<StopTime, Integer> { //<тип сущности, тип айдишника>
//    List<StopTime> findAllByIdAndDayOfWeekOrderByTime(int routeStopId, DayOfWeek dayOfWeeK);

    List<StopTime> findByRouteStops_IdAndDayOfWeekOrderByTime(int routeStopsId, DayOfWeek dayOfWeek);
//
//    @Query("SELECT st FROM StopTime st " +
//            "WHERE st.routeStops = :routeStop " +
//            "AND st.dayOfWeek = :dayOfWeek " +
//            "AND st.time >= :currentTime " +
//            "ORDER BY st.time ASC")
//    List<StopTime> findNextArrivalTimes(RouteStops routeStop, DayOfWeek dayOfWeek);

//    @Query("SELECT st FROM StopTime st " +
//            "WHERE st.routeStops.id = :routeStopId " +
//            "AND st.dayOfWeek = :dayOfWeek " +
//            "AND st.time >= CURRENT_TIME " +
//            "ORDER BY st.time ASC")
//    List<StopTime> findNearestArrivalTime(@Param("routeStopId") Integer routeStopId, @Param("dayOfWeek") DayOfWeek dayOfWeek);
}