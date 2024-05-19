package com.example.transport2.controller.swagger;

import com.example.transport2.dto.stop.StopViewDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * интерфейс, который содержит аннотации сваггера
 * чтоб они не мешались в основном классе
 */
@Tag(name = "API остановок")
public interface StopApiSwagger {
    @Operation(summary = "Получить список остановок по фильтрам")
    @ApiResponse(responseCode = "200", description = "остановки получены успешно", content =
    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = StopViewDto.class))))
    List<StopViewDto> getByFilters(@Parameter(description = "часть наименования остановки")
                               @RequestParam(required = false) @Size(min = 3)
                               String name);
}
