package com.example.transport2.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LocationTransportDto {

    @NotNull
    List<ShortTransportDto> transports;

    @Size(min=3)
    String locationName;

    @NotNull
    String transportType;
}
