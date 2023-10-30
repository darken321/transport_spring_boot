package com.example.transport2.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * DTO полное расписание
 * для информации о всех временах прибытия одного транспорта
 * по одной остановке одного маршрута
 * содержит:
 * информацию о транспорте и остановке
 * routes список маршрутов этого транспорта
 * stops список остановок этого маршрута
 * расписание на все дни недели
 * переход из ближайших рейсов
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FullScheduleOneRouteTransportDto {

    @Positive
    @NotNull
    Integer stopId;

    @NotBlank
    @Size(min = 2)
    String stopName;

    @NotBlank
    @Size(min = 3)
    String location;

    @NotBlank
    @Size(min = 2)
    String transportType;

    @NotBlank
    @Size(min = 2)
    String transportName;

    @NotNull
    List<RouteInfoDto> routes;

    @NotNull
    List<StopInfoDto> stops;

    @NotNull
    List<TimeDayOfWeekDto> schedule;
}
