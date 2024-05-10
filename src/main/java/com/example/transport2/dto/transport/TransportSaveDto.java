package com.example.transport2.dto.transport;

import com.example.transport2.model.TransportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransportSaveDto {

    @NotBlank
    String name;

    @NotNull
    String comment;

    @NotNull
    TransportType type;

    @NotNull
    @Positive
    Integer locationId;

    @NotNull
    @Size(min = 3)
    String locationName;
}