package com.example.transport2.mapper;

import com.example.transport2.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.UUID;

@Component
public class ExceptionMapper {
    public ExceptionDto exceptionToDto(Exception ex) {
        return ExceptionDto.builder()
                .message(ex.getMessage())
                .uuid(UUID.randomUUID())
                .exceptionServerTime(ZonedDateTime.now())
                .type(ex.getClass().getSimpleName())
                .build();
    }
}