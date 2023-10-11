package com.example.transport2.service;

import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.mapper.RoutesMapper;
import com.example.transport2.repository.StopTimeRepository;
import com.example.transport2.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StopTimeService {
    private final StopTimeRepository stopTimeRepository;
    private final RoutesMapper routesMapper;

    public List<StopTransportDto.StopTransportInfoDto> getArrivalTransports(int routeStopId, DayOfWeek dayOfWeek, Time currentTime) {
        List<Object[]> sortedTransports = stopTimeRepository.findSortedTransports(
                routeStopId,
                dayOfWeek.name(),
                currentTime);
        return routesMapper.allToTransportDto(sortedTransports, currentTime);
    }


    public List<StopTransportDto.StopTransportTimeDto> getArrivalTimes(int routeStopId, DayOfWeek dayOfWeek, Time currentTime) {

        List<Object[]> sortedArrivalTimes = stopTimeRepository.findSortedArrivalTimes(
                routeStopId,
                dayOfWeek.name(),
                currentTime,
                Constants.RECORDS_NUMBER);
        return routesMapper.allToStopTransportDto(sortedArrivalTimes, currentTime);

    }
}