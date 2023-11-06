package com.example.transport2.projection;

/**
 * интерфейс для получения всех расписаний одного транспорта
 */

public interface TransportRoutesInfo {

    Integer getId();

    String getStartStopName();

    String getEndStopName();

    String getRouteName();
}
