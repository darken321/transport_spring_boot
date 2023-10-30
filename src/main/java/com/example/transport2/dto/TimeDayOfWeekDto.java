package com.example.transport2.dto;

import lombok.*;

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
