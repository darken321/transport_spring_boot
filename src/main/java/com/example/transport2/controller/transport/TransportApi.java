package com.example.transport2.controller.transport;

import com.example.transport2.dto.LocationTransportDto;
import com.example.transport2.dto.PageDto;
import com.example.transport2.dto.TransportDto;
import com.example.transport2.mapper.TransportMapper;
import com.example.transport2.model.Transport;
import com.example.transport2.model.TransportType;
import com.example.transport2.service.location.LocationService;
import com.example.transport2.service.TransportService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/transport")
public class TransportApi {

    private final TransportService transportService;
    private final LocationService locationService;
    private final TransportMapper transportMapper;

    @GetMapping("{id}")
    public TransportDto getById(@PathVariable Integer id) {
        Transport transport = transportService.getById(id);
        return transportMapper.toDto(transport);
    }

    @GetMapping
    public List<TransportDto> getByFilters(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) TransportType type) {

        List<Transport> transportList = transportService.getByFilters(name, type);
        return transportMapper.toDto(transportList);
    }

    @GetMapping("page")
    public PageDto<TransportDto> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                                        @RequestParam(required = false, defaultValue = "20") int size) {
        return transportMapper.pageToDto(transportService.getAllPages(page, size));
    }

    @GetMapping("location/{locationId}/type/{type}")
    public LocationTransportDto getByLocationIdAndType(@PathVariable int locationId,
                                                       @PathVariable @NotNull TransportType type) {
        List<Transport> byLocationIdAndType = transportService.getByLocationIdAndType(locationId, type);
        return LocationTransportDto.builder()
                .locationName(locationService.getLocationById(locationId).getName())
                .transportType(type.name())
                .transports(transportMapper.toShortTransportDto(byLocationIdAndType))
                .build();
    }
}