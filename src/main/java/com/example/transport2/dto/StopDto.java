package com.example.transport2.dto;


import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StopDto {

    @Positive
    @NotNull
    Integer id;

    @NotNull
    @Size(min = 2)
    String name;

    @NotNull
    @Size(min = 3)
    String location;
}
