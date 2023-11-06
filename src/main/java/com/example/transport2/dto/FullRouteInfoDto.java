package com.example.transport2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * DTO для списока маршрутов транспорта
 * ID маршрута в таблице
 * startStopName название начальной
 * endStopName и конечной остановки маршрута
 * transportRouteName название маршрута
 * stops список остановок
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FullRouteInfoDto {

    @Positive
    @NotNull
    Integer transportRouteId;

    @NotBlank
    String startStopName;

    @NotBlank
    String endStopName;

    String transportRouteName;

    @NotNull
    List<StopInfoDto> stops;
}
