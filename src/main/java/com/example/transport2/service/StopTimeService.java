package com.example.transport2.service;

import com.example.transport2.model.StopTime;
import com.example.transport2.repository.RouteStopRepository;
import com.example.transport2.repository.StopRepository;
import com.example.transport2.repository.StopTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StopTimeService {
    private final StopTimeRepository stopTimeRepository;
    private final RouteStopRepository routeStopRepository;

    public LocalTime getArrivalTime(int routeStopId, DayOfWeek dayOfWeek) {
        //TODO ByRouteStopsId
//        List<StopTime> times = stopTimeRepository.findAllByIdAndDayOfWeekOrderByTime(routeStopId, dayOfWeek);
        List<StopTime> sortedTimes = stopTimeRepository
                .findByRouteStopsAndDayOfWeekOrderByTimeAsc(
                        routeStopRepository.getReferenceById(routeStopId), dayOfWeek);

        LocalTime currentTime = LocalTime.now();
        LocalTime nearestArrivalTime = null;
        for (StopTime stopTime : sortedTimes) {
            if (stopTime.getTime().isAfter(currentTime)) {
                nearestArrivalTime = stopTime.getTime();
                break;
            }
        } //TODO сделать список на время
        // и через цикл и стрим

        return nearestArrivalTime;
    }
}
