package com.example.transport2.mapper;

import com.example.transport2.dto.ScheduleDto;
import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.dto.StopTransportInfoName;
import com.example.transport2.model.TransportType;
import com.example.transport2.projection.StopRoutesInfo;
import com.example.transport2.projection.StopTransportInfo;
import com.example.transport2.projection.TimeAndDayOfWeek;
import com.example.transport2.repository.StopRepository;
import com.example.transport2.service.StopTimeService;
import com.example.transport2.service.TransportRouteService;
import com.example.transport2.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoutesMapper {
    private final StopRepository stopRepository;
//    private final TransportRouteService transportRouteService;


    private StopTransportDto.StopTransportTimeDto stopTransportDto(StopTransportInfoName info, Time currentTime) {
        return StopTransportDto.StopTransportTimeDto.builder()
                .id(info.getId())
                .name(info.getTransportName())
                .transportType(TransportType.valueOf(info.getTransportType()))
                //TODO вынести бизнес логику в сервис
                .routeName(info.getRouteName())
//                .routeName(stopRepository.findStopById(info.getStartStopId()).get().getName() + " - " +
//                        stopRepository.findStopById(info.getEndStopId()).get().getName())
                .arrivalTime(info.getTime())
                .timeToArrival(TimeUtils.timeToArrival(info.getTime(), currentTime))
                .hoursToArrival(TimeUtils.getToArrivalHours(info.getTime(), currentTime))
                .minutesToArrival(TimeUtils.getToArrivalMinutes(info.getTime(), currentTime))
                .build();
    }

    public List<StopTransportDto.StopTransportTimeDto>
    allToStopTransportDto(List<StopTransportInfoName> sortedArrivalTimes, Time currentTime) {
        return sortedArrivalTimes.stream()
                .map(times -> stopTransportDto(times, currentTime))
                .toList();
    }

    private StopTransportDto.StopTransportInfoDto stopTransportInfoDto(StopRoutesInfo info) {
        return StopTransportDto.StopTransportInfoDto.builder()
                .id(info.getId())
                .name(info.getTransportName())
                .transportType(TransportType.valueOf(info.getTransportType()))
                .build();
    }

    public List<StopTransportDto.StopTransportInfoDto> allToTransportDto(List<StopRoutesInfo> sortedTransportTypes) {
        return sortedTransportTypes.stream()
                .map(this::stopTransportInfoDto)
                .toList();
    }

    public List<ScheduleDto> allToScheduleDto(List<TimeAndDayOfWeek> arrivalTimesSchedule) {
        return arrivalTimesSchedule.stream()
                .map(this::toScheduleDto)
                .toList();
    }

    private ScheduleDto toScheduleDto(TimeAndDayOfWeek schedule) {
        return new ScheduleDto(schedule.getTime(), DayOfWeek.valueOf(schedule.getDayOfWeek()));
    }
}