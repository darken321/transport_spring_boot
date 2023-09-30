package com.example.transport2.dto;


import com.example.transport2.model.TransportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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

    @NotNull
    List<StopTransportTimeDto> routesTime;

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

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class StopTransportTimeDto {
        //список транспорта по остановке
        //внутри список из:
        // ID,
        // name номер (имя) транспорта,
        // transportType тип транспорта
        // routeName название (имя) маршрута (начало и конец),
        // arrivalTime время прибытия.
        // timeToArrival время, оставшееся до прибытия.

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
        LocalTime arrivalTime;

        @NotBlank
        String timeToArrival;

        @NotBlank
        long hoursToArrival;

        @NotBlank
        long minutesToArrival;
    }
}
