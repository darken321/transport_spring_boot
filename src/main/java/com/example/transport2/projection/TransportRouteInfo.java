package com.example.transport2.projection;

/**
 * интерфейс для получения всех расписаний одного транспорта
 * Для шапки маршрута
 */

public interface TransportRouteInfo {

    Integer getId();

    String getStartStopName();

    String getStartStopLocation();

    String getEndStopName();

    String getEndStopLocation();

    String getRouteName();
}
