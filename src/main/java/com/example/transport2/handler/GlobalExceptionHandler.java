package com.example.transport2.handler;

import com.example.transport2.dto.ExceptionDto;
import com.example.transport2.dto.ValidationExceptionDto;
import com.example.transport2.mapper.ExceptionMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 *
 */

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    ExceptionMapper dtoMapper;
    //TODO доделать обработку ошибок
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDto handleException(Exception exception) {
          log.error(exception.getMessage(), exception);
        return createExceptionDto(exception);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handleUserNotFoundException(EntityNotFoundException exception) {
        return createExceptionDto(exception);
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionDto handleUserExistException(EntityExistsException exception) {
        return createExceptionDto(exception);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleBadRequestException(ConstraintViolationException exception) {
        return createExceptionDto(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDto handleNotValidArgumentException(MethodArgumentNotValidException exception) {
        return ValidationExceptionDto.builder()
                .messages(exception.getBindingResult().getAllErrors().stream().map(e->e.getDefaultMessage()).toList())
                .uuid(UUID.randomUUID())
                .type(exception.getClass().getSimpleName())
                .exceptionServerTime(ZonedDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleNotValidArgumentException(MethodArgumentTypeMismatchException exception) {
        return createExceptionDto(exception);
    }

    private ExceptionDto createExceptionDto(Exception exception) {
        ExceptionDto exceptionDto = dtoMapper.exceptionToDto(exception);
        log.warn(String.valueOf(exceptionDto));
        return exceptionDto;
    }
}