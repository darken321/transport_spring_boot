package com.example.transport2.controller;

import com.example.transport2.dto.StopDto;
import com.example.transport2.dto.StopSaveDto;
import com.example.transport2.mapper.StopMapper;
import com.example.transport2.model.Stop;
import com.example.transport2.service.StopService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/stops")
public class StopApi {

    private final StopService stopService;
    private final StopMapper stopMapper;

    @GetMapping
    public List<StopDto> getByFilters(@RequestParam(required = false) String name) {

        if (name != null) {
            return stopMapper.toDto(stopService.findAllByNameContaining(name));
        }
        return stopMapper.toDto(stopService.getAll());
    }

    @GetMapping("{id}")
    public StopDto getById(@PathVariable Integer id) {
        Stop stop = stopService.getById(id);
        return stopMapper.toDto(stop);
    }

    @PostMapping
    public StopDto save(@RequestBody @Valid StopSaveDto dto) {
        Stop stop = stopMapper.fromDto(dto);
        Stop save = stopService.save(stop);
        return stopMapper.toDto(save);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        stopService.delete(id);
    }
}