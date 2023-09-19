package com.example.transport2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StopSaveDto {

    @NotBlank
    @Size(min = 2)
    @Pattern(regexp = "[а-яА-Я0-9-]+")
    String name;

    @NotBlank
    @Size(min = 3)
    @Pattern(regexp = "[а-яА-Я0-9-]+")
    String location;
}
