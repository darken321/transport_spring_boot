package com.example.transport2.dto;

import com.example.transport2.model.Location;
import com.example.transport2.model.TransportType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StopDto {

    Integer id;
    String name;
    Location location;
}
