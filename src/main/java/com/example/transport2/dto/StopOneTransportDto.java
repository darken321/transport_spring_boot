package com.example.transport2.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

/**
 * DTO Ближайшие рейсы
 * для информации о 3 ближайших временах прибытия одного транспорта
 * по одной остановке одного маршрута
 * содержит:
 * информацию о транспорте и остановке
 * список маршрутов по этой остановке
 * список остановок этого маршрута
 * список транспортов
 * переход из полного расписания
 * и из списка маршрутов транспорта
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StopOneTransportDto {

    @Positive
    @NotNull
    Integer id;

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

    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    List<LocalTime> nearest3Times;

    @NotNull
    List<RouteInfoDto> routes;

    @NotNull
    List<StopInfoDto> stops;

    @NotNull
    List<StopTransportDto.StopTransportInfoDto> transports;

    @NotNull
    List<StopTransportDto.StopTransportTimeDto> routesTime;
}
