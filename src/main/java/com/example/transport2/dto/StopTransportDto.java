package com.example.transport2.dto;


import com.example.transport2.model.TransportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
import java.util.List;

/**
 * DTO для информации о транспорте по остановке
 * Id, имя и локация остановки
 * внутри два вложенных списка информации
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StopTransportDto {

    @Positive
    @NotNull
    Integer id;

    @NotBlank
    @Size(min = 2)
    String name;

    @NotBlank
    @Size(min = 3)
    String location;

    @NotNull
    List<StopTransportInfoDto> transports;

    /** короткий список транспорта по остановке
    ID,
    name номер (имя) транспорта,
    transportType тип транспорта
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class StopTransportInfoDto {
        @Positive
        @NotNull
        Integer id;

        @NotBlank
        String name;

        @NotNull
        TransportType transportType;
    }

    @NotNull
    List<StopTransportTimeDto> routesTime;
    /**Список транспорта по остановке
    ID,
    name номер (имя) транспорта,
    transportType тип транспорта
    routeName название (имя) маршрута (начало и конец),
    arrivalTime времена прибытия.
    timeToArrival строка, время, оставшееся до прибытия.
    hoursToArrival часы
    minutesToArrival и минуты для прибытия
     **/
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class StopTransportTimeDto {

        @Positive
        @NotNull
        Integer id;

        @NotBlank
        String name;

        @NotNull
        TransportType transportType;

        @NotBlank
        String routeName;

        @NotBlank
        Time arrivalTime;

        @NotBlank
        String timeToArrival;

        @NotBlank
        long hoursToArrival;

        @NotBlank
        long minutesToArrival;
    }
}
