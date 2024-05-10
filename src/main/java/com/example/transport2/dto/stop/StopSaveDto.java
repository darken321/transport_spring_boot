package com.example.transport2.dto.stop;

import com.example.transport2.util.HasNameLocationComment;
import com.example.transport2.util.StringPatterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StopSaveDto implements HasNameLocationComment {

    @NotBlank
    @Size(min = 2)
    @Pattern(regexp = StringPatterns.STOP_NAME_PATTERN , message = "поле 'название остановки' должно соответствовать ")
    String name;

    @NotNull
    String comment;

    @NotBlank
    @Size(min = 3)
    @Pattern(regexp = StringPatterns.STOP_NAME_PATTERN , message = "поле 'локация' должно соответствовать ")
    String location;
}
