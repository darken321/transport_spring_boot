package com.example.transport2.dto.stop;

import com.example.transport2.util.StringPatterns;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * короткое DTO для update остановки по API,
 * содержит id обновляемой остановки
 * содержит id локации, к которой принадлежит остановка
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StopEditDto {

    @Positive
    @NotNull
    Integer id;

    @NotBlank
    @Size(min = 2)
    @Pattern(regexp = StringPatterns.STOP_NAME_PATTERN , message = "поле 'название остановки' должно соответствовать ")
    String name;

    @NotNull
    String comment;

    @NotNull
    @Positive
    Integer locationId;
}
