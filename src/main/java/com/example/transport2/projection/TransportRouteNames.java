package com.example.transport2.projection;

/**
 * интерфейс для получения расписания одной остановки в конкретном маршруте
 */

public interface TransportRouteNames {
    Integer getTransportRouteId();

    String getStartStopName();

    String getEndStopName();
}
