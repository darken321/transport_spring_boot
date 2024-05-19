package com.example.transport2.dto.stop;


import com.example.transport2.util.StringPatterns;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


/**
 * короткое DTO для возвращения остановки по API,
 * содержит id локации, к которой принадлежит остановка
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StopViewDto {

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
}
