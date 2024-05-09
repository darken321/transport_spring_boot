package com.example.transport2.dto.location;


import com.example.transport2.util.HasName;
import com.example.transport2.util.StringPatterns;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto implements HasName {

    @Positive
    @NotNull
    Integer id;

    @NotBlank
    @Size(min = 3)
    @Pattern(regexp = StringPatterns.STOP_NAME_PATTERN , message = "поле 'локация' должно соответствовать ")
    String name;
}
