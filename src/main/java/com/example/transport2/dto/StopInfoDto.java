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
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StopInfoDto {

    @Positive
    @NotNull
    Integer stopId;

    @NotBlank
    @Pattern(regexp = StringPatterns.STOP_NAME_PATTERN , message = "поле 'локация' должно соответствовать ")
    String stopName;
}
