package com.example.transport2.dto.transport;

import com.example.transport2.model.TransportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * короткое DTO для возвращения транспорта по API,
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransportViewDto {

    @Positive
    @NotNull
    Integer id;

    @NotBlank
    String name;

    @NotNull
    String comment;

    @NotNull
    TransportType type;
}