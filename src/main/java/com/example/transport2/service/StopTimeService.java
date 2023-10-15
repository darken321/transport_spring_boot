package com.example.transport2.service;

import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.mapper.RoutesMapper;
import com.example.transport2.projection.StopRoutesInfo;
import com.example.transport2.projection.StopTransportInfo;
import com.example.transport2.repository.StopRepository;
import com.example.transport2.repository.StopTimeRepository;
import com.example.transport2.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StopTimeService {
    private final StopTimeRepository stopTimeRepository;
    private final RoutesMapper routesMapper;
    private final StopRepository stopRepository;

    public List<StopTransportDto.StopTransportInfoDto> getArrivalTransports(int routeStopId) {
        //TODO установка времени и дня недели
//        Time currentTime = Time.valueOf(LocalTime.now());
        Time currentTime = Time.valueOf(LocalTime.of(16, 00));
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
//        DayOfWeek dayOfWeek = DayOfWeek.TUESDAY;

        List<StopRoutesInfo> sortedTransports = stopTimeRepository
                .findSortedTransports(routeStopId, dayOfWeek.name(), currentTime);
        return routesMapper.allToTransportDto(sortedTransports);
    }

    public List<StopTransportDto.StopTransportTimeDto> getArrivalTimes(int stopId) {
        //TODO установка времени и дня недели
//        Time currentTime = Time.valueOf(LocalTime.now());
        Time currentTime = Time.valueOf(LocalTime.of(16, 00));
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
//        DayOfWeek dayOfWeek = DayOfWeek.TUESDAY;

        List<StopTransportInfo> sortedArrivalTimes = stopTimeRepository
                .findSortedArrivalTimes(stopId, dayOfWeek.name(), currentTime, Constants.RECORDS_NUMBER);
        return routesMapper.allToStopTransportDto(sortedArrivalTimes, currentTime);
    }
}