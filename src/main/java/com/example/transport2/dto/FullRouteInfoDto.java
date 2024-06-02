package com.example.transport2.dto;

import com.example.transport2.model.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * DTO содержит информацию о маршруте включая список остановок
 @transportRouteId - ID маршрута в таблице. Должно быть положительным и не null.
 @startStopName - Название начальной остановки маршрута. Не должно быть пустым.
 @endStopName - Название конечной остановки маршрута. Не должно быть пустым.
 @transportRouteName - Название маршрута. Не должно быть null.
 @stops - Список остановок на маршруте. Не должно быть null

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
    String startStopLocation;

    @NotBlank
    String endStopName;

    @NotBlank
    String endStopLocation;

    @NotNull
    String transportRouteName;

    @NotNull
    List<StopInfoDto> stops;
}
