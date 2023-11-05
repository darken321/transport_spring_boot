package com.example.transport2.service;

import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.mapper.RoutesMapper;
import com.example.transport2.projection.StopRoutesInfo;
import com.example.transport2.projection.StopTransportInfo;
import com.example.transport2.repository.StopTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.example.transport2.util.Constants.NUMBER_OF_RECORDS;

@Service
@Slf4j
@RequiredArgsConstructor
public class RouteService {
    private final StopTimeRepository stopTimeRepository;
    private final RoutesMapper routesMapper;

    /**
     *
     * @param stopId номер остановки
     * @return Возвращает DTO список и тип транспорта по остановке
     */

    public List<StopTransportDto.StopTransportInfoDto> getArrivalTransports(int stopId, Time currentTime, DayOfWeek dayOfWeek) {
        List<StopRoutesInfo> sortedTransports = stopTimeRepository
                .findSortedTransports(stopId, dayOfWeek.name(), currentTime);
        return routesMapper.allToTransportDto(sortedTransports);
    }

    /**
     * @param stopId номер остановки
     * @return Возвращает DTO список и тип транспорта, время прибытия и название маршрута по остановке
     */
    public List<StopTransportDto.StopTransportTimeDto> getArrivalTimes(int stopId, Time currentTime, DayOfWeek dayOfWeek) {
        List<StopTransportInfo> sortedArrivalTimes = stopTimeRepository
                .findSortedArrivalTimes(stopId, dayOfWeek.name(), currentTime, NUMBER_OF_RECORDS);

        return routesMapper.allToStopTransportDto(sortedArrivalTimes, currentTime);
    }
}