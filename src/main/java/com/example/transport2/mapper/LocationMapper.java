package com.example.transport2.mapper;

import com.example.transport2.dto.location.LocationViewDto;
import com.example.transport2.dto.location.LocationEditDto;
import com.example.transport2.dto.location.LocationSaveDto;
import com.example.transport2.model.Location;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LocationMapper {


    public LocationViewDto toDto(Location location) {
        return LocationViewDto.builder()
                .id(location.getId())
                .name(location.getName())
                .build();
    }

    public List<LocationViewDto> toDto(List<Location>  locationList) {
        return locationList.stream()
                .map(this::toDto)
                .toList();
    }


    public Location fromDto(@Valid LocationViewDto dto) {
        return Location.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    public Location fromDto(@Valid LocationEditDto dto) {
        return Location.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    public Location fromDto(@Valid LocationSaveDto dto) {
        return Location.builder()
                .name(dto.getName())
                .build();
    }

}
