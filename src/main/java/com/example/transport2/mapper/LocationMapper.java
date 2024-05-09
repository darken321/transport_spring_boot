package com.example.transport2.mapper;

import com.example.transport2.dto.location.LocationDto;
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


    public LocationDto toDto(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .name(location.getName())
                .build();
    }

    public List<LocationDto> toDto(List<Location>  locationList) {
        return locationList.stream()
                .map(this::toDto)
                .toList();
    }


    public Location fromDto(@Valid LocationDto dto) {
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
