package com.example.transport2.mapper;

import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.model.TransportType;
import com.example.transport2.repository.StopRepository;
import com.example.transport2.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoutesMapper {
    private final StopRepository stopRepository;

    public StopTransportDto.StopTransportTimeDto stopTransportDto(Object[] obj, Time currentTime) {
        return StopTransportDto.StopTransportTimeDto.builder()
                .id((Integer) obj[0])
                .name((String) obj[1])
                .transportType(TransportType.valueOf(((String) obj[2])))
                .routeName(stopRepository.findStopById((Integer) obj[3]).get().getName() + " - " +
                        stopRepository.findStopById((Integer) obj[4]).get().getName())
                .arrivalTime((Time) obj[5])
                .timeToArrival(TimeUtils.timeToArrival((Time) obj[5], currentTime))
                .hoursToArrival(TimeUtils.getToArrivalHours((Time) obj[5], currentTime))
                .minutesToArrival(TimeUtils.getToArrivalMinutes((Time) obj[5], currentTime))
                .build();
    }

    public List<StopTransportDto.StopTransportTimeDto> allToStopTransportDto(List<Object[]> sortedArrivalTimes, Time currentTime) {
        return sortedArrivalTimes.stream()
                .map(object -> stopTransportDto(object, currentTime))
                .toList();
    }

    public StopTransportDto.StopTransportInfoDto stopTransportInfoDto(Object[] obj) {
        return StopTransportDto.StopTransportInfoDto.builder()
                .id((Integer) obj[0])
                .name((String) obj[1])
                .transportType(TransportType.valueOf(((String) obj[2])))
                .build();
    }

    public List<StopTransportDto.StopTransportInfoDto> allToTransportDto(List<Object[]> sortedTransportTypes, Time currentTime) {
        return sortedTransportTypes.stream()
                .map(this::stopTransportInfoDto)
                .toList();
    }
}
//TODO добавил класс маппер для маршрутов