package com.example.transport2.service;

import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.dto.StopTransportInfoName;
import com.example.transport2.mapper.RoutesMapper;
import com.example.transport2.mapper.StopMapper;
import com.example.transport2.projection.StopRoutesInfo;
import com.example.transport2.projection.StopTransportInfo;
import com.example.transport2.repository.StopRepository;
import com.example.transport2.repository.StopTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.transport2.util.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class StopTimeService {
    private final StopTimeRepository stopTimeRepository;
    private final RoutesMapper routesMapper;
//    private final StopMapper stopMapper;
    private final StopRepository stopRepository;

    /**
     *
     * @param stopId номер остановки
     * @return Возвращает DTO список и тип транспорта по остановке
     */

    public List<StopTransportDto.StopTransportInfoDto> getArrivalTransports(int stopId) {
        Time currentTime = Time.valueOf(LocalTime.of(16, 00));
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        List<StopRoutesInfo> sortedTransports = stopTimeRepository
                .findSortedTransports(stopId, dayOfWeek.name(), currentTime);
        return routesMapper.allToTransportDto(sortedTransports);
    }

    /**
     * @param stopId номер остановки
     * @return Возвращает DTO список и тип транспорта, время прибытия и название маршурта по остановке
     */
    public List<StopTransportDto.StopTransportTimeDto> getArrivalTimes(int stopId) {
        //TODO сюда нужно притащить бизнес логику сложить два названия

        Time currentTime = Time.valueOf(LocalTime.of(16, 00));
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        List<StopTransportInfo> sortedArrivalTimes = stopTimeRepository
                .findSortedArrivalTimes(stopId, dayOfWeek.name(), currentTime, NUMBER_OF_RECORDS);

        List<StopTransportInfoName> stopTransportInfoNames = getStopTransportInfoNames(sortedArrivalTimes);
//            List<StopTransportInfo> updatedList = sortedArrivalTimes.stream()
//                    .peek(e -> e.setName(getRouteName(e)))
//                    .toList();
//        }

        //закинуть sortedArrivalTimes в другой сервис, чтоб он вернул похожую структуру, но с именем маршрута
        //а тот сервис обратится в репозиторий чтоб вытащить начало и конец марштура по start_ stop_id и end_stop_id
        //и уже этот новый объект отдавать в маппер
        return routesMapper.allToStopTransportDto(stopTransportInfoNames, currentTime);
    }

    private List<StopTransportInfoName> getStopTransportInfoNames(List<StopTransportInfo> sortedArrivalTimes) {
        List<StopTransportInfoName> newList = new ArrayList<>();
        for (StopTransportInfo e : sortedArrivalTimes) {
            newList.add(StopTransportInfoName.builder()
                    .id(e.getId())
                    .transportName(e.getTransportName())
                    .transportType(e.getTransportType())
                    .startStopId(e.getStartStopId())
                    .endStopId(e.getEndStopId())
                    .time(e.getTime())
                    .routeName(getRouteName(e))
                    .build());
        }
        return newList;
    }

    private String getRouteName(StopTransportInfo e) {
        return stopRepository.findStopById(e.getStartStopId()).get().getName() + " - "
                + stopRepository.findStopById(e.getEndStopId()).get().getName();
    }

}
//TODO установка времени и дня недели
//        DayOfWeek dayOfWeek = DayOfWeek.TUESDAY;
//        Time currentTime = Time.valueOf(LocalTime.now());