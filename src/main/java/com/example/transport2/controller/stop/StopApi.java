package com.example.transport2.controller.stop;

import com.example.transport2.controller.swagger.StopApiSwagger;
import com.example.transport2.dto.stop.StopViewDto;
import com.example.transport2.dto.stop.StopEditDto;
import com.example.transport2.dto.stop.StopSaveDto;
import com.example.transport2.dto.StopTransportDto;
import com.example.transport2.mapper.StopMapper;
import com.example.transport2.model.Stop;
import com.example.transport2.service.StopService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/stops")
public class StopApi implements StopApiSwagger {

    private final StopService stopService;
    private final StopMapper stopMapper;

    /**
     * Сохраняет остановку в БД
     *
     * @param dto DTO остановки
     * @return имя и локация остановки
     */
    @PostMapping
    public StopViewDto save(@RequestBody @Valid StopSaveDto dto) {
        Stop stop = stopMapper.fromDto(dto);
        Stop save = stopService.save(stop);
        return stopMapper.toViewDto(save);
    }

    @GetMapping
    public List<StopViewDto> getByFilters(@Parameter(description = "часть наименования остановки")
                                      @RequestParam(required = false) @Size(min = 3)
                                      String name) {

        if (name != null) {
            return stopMapper.toViewDto(stopService.findAllByNameContaining(name));
        }
        return stopMapper.toViewDto(stopService.getAll());
    }

    /**
     * Возвращает данные по ID остановки
     *
     * @param id ID остановки
     * @return имя остановки, список транспорта по ней и список транспорта с сортировкой по времени прибытия
     */
    @GetMapping("{id}")
    public StopTransportDto getById(@PathVariable Integer id) {
        LocalTime currentTime = LocalTime.of(16, 00);
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        return stopMapper.toBigTransportDto(id, currentTime, dayOfWeek);
    }

    @PutMapping
    public StopViewDto update(@RequestBody @Valid StopEditDto dto) {
        Stop stop = stopMapper.fromDto(dto);
        Stop update = stopService.update(stop);
        return stopMapper.toViewDto(update);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        stopService.delete(id);
    }
}