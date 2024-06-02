package com.example.transport2.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * DTO содержит информацию о транспорте и его маршруты
 * информацию о транспорте (тип, название, комментарий)
 * routes список маршрутов этого транспорта
 * внутри routes лежит список остановок этого маршрута
 * переход из списка транспортов
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OneTransportRoutesDto {
    @NotBlank
    @Size(min = 2)
    String transportType;

    @NotBlank
    @Size(min = 2)
    String transportName;

    @NotNull
    String comment;

    @NotNull
    List<FullRouteInfoDto> routes;
}
