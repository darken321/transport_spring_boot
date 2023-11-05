package com.example.transport2.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Time;

/**
 * интерфейс для получения расписания одной остановки в конкретном маршруте
 */

public interface TimeAndDayOfWeek {
    Time getTime();

    String getDayOfWeek();
}
