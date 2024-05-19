package com.example.transport2.controller.location;

import com.example.transport2.dto.location.LocationViewDto;
import com.example.transport2.dto.location.LocationEditDto;
import com.example.transport2.dto.location.LocationSaveDto;
import com.example.transport2.mapper.LocationMapper;
import com.example.transport2.model.Location;
import com.example.transport2.service.LocationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/locations")
public class LocationApi {

    private final LocationService locationService;
    private final LocationMapper locationMapper;

    /**
     * Сохраняет локацию в БД
     *
     * @param dto DTO локации
     * @return имя локации
     */
    @PostMapping
    public LocationViewDto save(@RequestBody @Valid LocationSaveDto dto) {
        Location location = locationMapper.fromDto(dto);
        Location save = locationService.save(location);
        return locationMapper.toDto(save);
    }

    /**
     * Возвращает данные по ID локации
     *
     * @param id ID локации
     * @return
     */
    @GetMapping("{id}")
    public LocationViewDto getById(@PathVariable Integer id) {
        Location location = locationService.getLocationById(id);
        return locationMapper.toDto(location);
    }

    @GetMapping
    public List<LocationViewDto> getByFilters(@RequestParam(required = false) @Size(min = 3)String name) {
        if (name != null) {
            return locationMapper.toDto(locationService.findAllByNameContainingIgnoreCase(name));
        }
        return locationMapper.toDto(locationService.getAll());
    }

    @PutMapping
    public LocationViewDto update(@RequestBody @Valid LocationEditDto dto) {
        Location location = locationMapper.fromDto(dto);
        Location update = locationService.update(location);
        return locationMapper.toDto(update);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        locationService.delete(id);
    }

}