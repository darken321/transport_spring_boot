package com.example.transport2.dto;


import com.example.transport2.util.StringPatterns;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Длинное DTO для показа остановки в web
 * содержит id и имя локации
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StopDto {

    @Positive
    @NotNull
    Integer id;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = StringPatterns.STOP_NAME_PATTERN, message = "поле 'название остановки' должно соответствовать ")
    String name;

    @NotNull
    String comment;

    @NotNull
    @Positive
    Integer locationId;

    @NotNull
    @Size(min = 3)
    @Pattern(regexp = StringPatterns.STOP_NAME_PATTERN , message = "поле 'локация' должно соответствовать ")
    String locationName;
}
