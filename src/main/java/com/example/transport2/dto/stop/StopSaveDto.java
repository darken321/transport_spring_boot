package com.example.transport2.dto.stop;

import com.example.transport2.util.StringPatterns;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * короткое DTO для save остановки по API,
 * не содержит id обновляемой остановки
 * содержит id локации, к которой принадлежит остановка
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StopSaveDto {

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
