package com.example.transport2.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@ToString
@Getter
@Builder
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ValidationExceptionDto {
    List<String> messages;
    UUID uuid;
    ZonedDateTime exceptionServerTime;
    String type;
}