package com.example.transport2.dto;

import com.example.transport2.model.TransportType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransportDto {

    Integer id;
    String name;
    TransportType type;
}
