package com.example.transport2.projection;

import com.example.transport2.model.TransportType;

import java.sql.Time;

/**
 * интерфейс для получения расписания одной остановки в конкретном маршруте
 */

public interface StopTransportInfo {
    Integer getId();
    String getTransportName();
    String getTransportType();
    Integer getStartStopId();
    Integer getEndStopId();
    String getStartStopName();
    String getEndStopName();
    Time getTime();
}
