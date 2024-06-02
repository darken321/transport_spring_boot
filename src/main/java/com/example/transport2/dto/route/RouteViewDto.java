package com.example.transport2.dto.route;


import com.example.transport2.util.StringPatterns;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


/**
 * короткое DTO для показа остановок одного маршрута в web
 * с id, порядковым номером остановки, id и локацией остановки
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteViewDto {

    @Positive
    @NotNull
    Integer routeId;

    @Positive
    @NotNull
    Integer stopId;

    //номер остановки по порядку
    @Positive
    @NotNull
    Integer stopOrder;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = StringPatterns.STOP_NAME_PATTERN, message = "поле 'название остановки' должно соответствовать ")
    String stopName;

    @NotNull
    String stopComment;

    @NotNull
    @Positive
    Integer locationId;

    @NotNull
    @Positive
    String locationName;
}
