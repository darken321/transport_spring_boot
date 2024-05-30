package com.example.transport2.dto.transport;

import com.example.transport2.model.TransportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
}