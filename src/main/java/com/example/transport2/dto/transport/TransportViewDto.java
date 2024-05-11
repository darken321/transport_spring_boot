package com.example.transport2.dto.transport;

import com.example.transport2.model.TransportType;
import com.example.transport2.util.HasNameType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransportViewDto implements HasNameType {

    @Positive
    @NotNull
    Integer id;

    @NotBlank
    String name;

    @NotNull
    String comment;

    @NotNull
    TransportType type;

    @NotNull
    @Positive
    Integer locationId;
}