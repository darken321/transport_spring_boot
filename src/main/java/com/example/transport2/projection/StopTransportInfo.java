package com.example.transport2.projection;

import com.example.transport2.model.TransportType;
import org.springframework.cglib.core.Local;

import java.sql.Time;
import java.time.LocalTime;

/**
 * интерфейс для получения расписания одной остановки в конкретном маршруте
 */

public interface StopTransportInfo {
    Integer getId();
    String getTransportName();
    String getTransportType();
    String getStartStopName();
    String getEndStopName();
    LocalTime getTime();
}
