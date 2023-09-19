package com.example.transport2.dto;


import com.example.transport2.model.TransportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StopTransportDto {

    @Positive
    @NotNull
    Integer id;

    @NotBlank
    @Size(min = 2)
    String name;

    @NotBlank
    @Size(min = 3)
    String location;

    @NotNull
    List<StopTransportInfoDto> transports;


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class StopTransportInfoDto {
        @Positive
        @NotNull
        Integer id;

        @NotBlank
        String name;

        @NotNull
        TransportType transportType;
    }
}
