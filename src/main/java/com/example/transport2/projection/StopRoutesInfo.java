package com.example.transport2.projection;

import java.sql.Time;

/**
 * интерфейс для получения расписания одной остановки в конкретном маршруте
 */

public interface StopRoutesInfo {
    Integer getId();
    String getTransportName();
    String getTransportType();
}
