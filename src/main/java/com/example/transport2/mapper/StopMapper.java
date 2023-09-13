package com.example.transport2.mapper;

import com.example.transport2.dto.StopDto;
import com.example.transport2.model.Stop;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StopMapper {
    public StopDto toDto(Stop stop) {
        return StopDto.builder()
                .id(stop.getId())
                .name(stop.getName())
                .location(stop.getLocation())
                .build();
    }

    public Stop fromDto(StopDto dto) {
        return Stop.builder()
                .id(dto.getId())
                .name(dto.getName())
                .location(dto.getLocation())
                .build();
    }

    public List<StopDto> allToDto(List<Stop> stopList) {
        return stopList.stream()
                .map(this::toDto)
                .toList();
    }

    public List<Stop> allFromDto(List<StopDto> dtolist) {
        List<Stop> list = new ArrayList<>();
        for (StopDto dto : dtolist) {
            list.add(Stop.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .location(dto.getLocation())
                    .build());
        }
        return list;
    }
}