package com.example.transport2.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Time;

/**
 * Класс описывает время и день недели прибытия транспорта
 */

@AllArgsConstructor
@Getter
public class ScheduleTime {
    Time time;
//    DayOfWeek dayOfWeek;//?? String
    String dayOfWeek;
}

