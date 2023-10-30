package com.example.transport2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * список маршрутов транспорта
 * ID маршрута в таблице
 * startStopName название начальной
 * endStopName и конечной остановки маршрута
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteInfoDto {

    @Positive
    @NotNull
    Integer transportRouteId;

    @NotBlank
    String startStopName;

    @NotBlank
    String endStopName;
}
