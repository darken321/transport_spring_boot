package com.example.transport2.service;

import com.example.transport2.model.StopTime;
import com.example.transport2.repository.RouteStopRepository;
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

    public List<LocalTime> getArrivalTimes(int routeStopId, DayOfWeek dayOfWeek, int numbers, LocalTime currentTime) {
        List<StopTime> sortedTimes = stopTimeRepository.findByRouteStops_IdAndDayOfWeekOrderByTime(routeStopId, dayOfWeek);
//        int limit = numbers;
//        List<LocalTime> nearestArrivalTime = new ArrayList<>();
//        for (StopTime stopTime : sortedTimes) {
//            if (stopTime.getTime().isAfter(currentTime)) {
//                nearestArrivalTime.add(stopTime.getTime());
//                if (--limit == 0) break;
//            }
//        }
//        return nearestArrivalTime;

        List<LocalTime> localTimes = sortedTimes.stream()
                .map(StopTime::getTime)
                .filter(t -> t.isAfter(currentTime))
                .limit(numbers)
                .toList();
        return localTimes;

        //TODO сделать список на время
        // и через цикл и стрим
    }
}