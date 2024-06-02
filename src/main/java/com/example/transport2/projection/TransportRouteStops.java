package com.example.transport2.projection;

/**
 * интерфейс для получения списка остановок,
 * айди остановок, порядка остановок, названия остановок,
 * комментариев к остановке по одному маршруту
 */
public interface TransportRouteStops {
    Integer getRouteId();

    Integer getStopId();

    Integer getStopNumber();

    String getStopName();

    String getComment();

    String getLocationName();

    Integer getLocationId();
}
