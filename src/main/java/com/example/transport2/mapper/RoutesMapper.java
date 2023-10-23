package com.example.transport2.mapper;

import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.model.TransportType;
import com.example.transport2.projection.StopRoutesInfo;
import com.example.transport2.projection.StopTransportInfo;
import com.example.transport2.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoutesMapper {

    private StopTransportDto.StopTransportTimeDto stopTransportDto(StopTransportInfo info, Time currentTime) {
        return StopTransportDto.StopTransportTimeDto.builder()
                .transportId(info.getId())
                .transportName(info.getTransportName())
                .transportType(TransportType.valueOf(info.getTransportType()))
                .startStopName(info.getStartStopName())
                .endStopName(info.getEndStopName())
                .arrivalTime(info.getTime())
                .timeToArrival(TimeUtils.timeToArrival(info.getTime(), currentTime))
                .hoursToArrival(TimeUtils.getToArrivalHours(info.getTime(), currentTime))
                .minutesToArrival(TimeUtils.getToArrivalMinutes(info.getTime(), currentTime))
                .build();
    }

    public List<StopTransportDto.StopTransportTimeDto>
    allToStopTransportDto(List<StopTransportInfo> sortedArrivalTimes, Time currentTime) {
        return sortedArrivalTimes.stream()
                .map(times -> stopTransportDto(times, currentTime))
                .toList();
    }

    private StopTransportDto.StopTransportInfoDto stopTransportInfoDto(StopRoutesInfo info) {
        return StopTransportDto.StopTransportInfoDto.builder()
                .transportId(info.getId())
                .transportName(info.getTransportName())
                .transportType(TransportType.valueOf(info.getTransportType()))
                .build();
    }

    public List<StopTransportDto.StopTransportInfoDto> allToTransportDto(List<StopRoutesInfo> sortedTransportTypes) {
        return sortedTransportTypes.stream()
                .map(this::stopTransportInfoDto)
                .toList();
    }

//    public List<ScheduleDto> allToScheduleDto(List<TimeAndDayOfWeek> arrivalTimesSchedule) {
//        return arrivalTimesSchedule.stream()
//                .map(this::toScheduleDto)
//                .toList();
//    }
//
//    private ScheduleDto toScheduleDto(TimeAndDayOfWeek schedule) {
//        return new ScheduleDto(schedule.getTime(), DayOfWeek.valueOf(schedule.getDayOfWeek()));
//    }
}