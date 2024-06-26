package com.example.transport2.dto;

import com.example.transport2.util.StringPatterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * список всех остановок этого транспорта в этом маршруте
 * ID остановки в таблице
 * stopName название остановки
 * locationId Id локации, где находится остановка
 * locationName название локации, где находится остановка
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StopInfoDto {

    @Positive
    @NotNull
    Integer routeId;

    @Positive
    @NotNull
    Integer stopId;

    @Positive
    @NotNull
    Integer stopNumber;

    @NotBlank
    @Pattern(regexp = StringPatterns.STOP_NAME_PATTERN , message = "поле 'локация' должно соответствовать ")
    String stopName;

    @NotNull
    String comment;

    @NotBlank
    String locationName;

    @Positive
    @NotNull
    Integer locationId;
}
