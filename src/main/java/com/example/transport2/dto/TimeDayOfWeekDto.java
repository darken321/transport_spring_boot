package com.example.transport2.dto;

import lombok.*;

import java.time.LocalTime;

/**
 * Информация о времени прибытия для расписания
 */
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class TimeDayOfWeekDto {
        String time;
        String dayOfWeek;
}
