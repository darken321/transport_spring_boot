package com.example.transport2.util;

import com.example.transport2.projection.StopTransportInfo;
import com.example.transport2.repository.StopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RouteUtils {
    private final StopRepository stopRepository;
    public String getRouteName(StopTransportInfo info) {
        return stopRepository.findStopById(info.getStartStopId()).get().getName() + " - " +
                stopRepository.findStopById(info.getEndStopId()).get().getName();
    }
}