package com.example.transport2.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
import java.util.List;

/**
 * DTO для информации об одном и всем транспорте по остановке
 * переход из списка транспорта-маршрута-времени по остановке
 * внутри 4 вложенных листа DTO
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
    List<Time> nearest3Times;

    @NotNull
    List<RouteInfoDto> routes;

    /**
     * список маршрутов этого транспорта
     * ID маршрута в таблице
     * startStopName название начальной
     * endStopName и конечной остановки маршрута
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class RouteInfoDto {

        @Positive
        @NotNull
        Integer transportRouteId;

        @NotBlank
        String startStopName;

        @NotBlank
        String endStopName;
    }

    @NotNull
    List<StopInfoDto> stops;

    /**
     * список всех остановок этого транспорта в этом маршруте
     * ID остановки в таблице
     * stopName название остановки
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class StopInfoDto {

        @Positive
        @NotNull
        Integer stopId;

        @NotBlank
        String stopName;
    }

    @NotNull
    List<StopTransportDto.StopTransportInfoDto> transports;

    @NotNull
    List<StopTransportDto.StopTransportTimeDto> routesTime;
}
