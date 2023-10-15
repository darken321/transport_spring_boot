package com.example.transport2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.DayOfWeek;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleDto {
        Time time;
        DayOfWeek dayOfWeek;
}
