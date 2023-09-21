package com.example.transport2.service;

import com.example.transport2.dto.ExceptionDto;
import com.example.transport2.mapper.ExceptionMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 */

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class ExceptionHandler {
    ExceptionMapper dtoMapper;
    //TODO
//    public ExceptionDto handleException(Exception ex, HttpStatus httpStatus) {
//        ExceptionDto exceptionDto = ExceptionDto.builder()
//                .message(ex.getMessage())
//                .uuid(UUID.randomUUID())
//                .exceptionServerTime(ZonedDateTime.now())
//                .build();
//        log.warn(String.valueOf(exceptionDto));
//        return exceptionDto;
//    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handleUserNotFoundException(EntityNotFoundException exception) {
        ExceptionDto exceptionDto = dtoMapper.exceptionToDto(exception);
        log.warn(String.valueOf(exceptionDto));
        return exceptionDto;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionDto handleUserExistException(EntityExistsException exception) {
        ExceptionDto exceptionDto = dtoMapper.exceptionToDto(exception);
        log.warn(String.valueOf(exceptionDto));
        return exceptionDto;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleBadRequestException(ConstraintViolationException exception) {
        ExceptionDto exceptionDto = dtoMapper.exceptionToDto(exception);
        log.warn(String.valueOf(exceptionDto));
        return exceptionDto;
    }
}