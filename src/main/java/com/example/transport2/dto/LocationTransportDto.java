package com.example.transport2.dto;


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
    List<ShortTransportDto> transports;
    String locationName;
    String transportType;

}
