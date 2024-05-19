package com.example.transport2.mapper;

import com.example.transport2.dto.PageDto;
import com.example.transport2.dto.ShortTransportDto;
import com.example.transport2.dto.TransportDto;
import com.example.transport2.dto.transport.TransportEditDto;
import com.example.transport2.dto.transport.TransportSaveDto;
import com.example.transport2.dto.transport.TransportViewDto;
import com.example.transport2.model.Location;
import com.example.transport2.model.Transport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TransportMapper {
    private final PageMapper pageMapper;

    public ShortTransportDto toShortTransportDto(Transport transport) {
        return ShortTransportDto.builder()
                .name(transport.getName())
                .id(transport.getId())
                .build();
    }

    public List<ShortTransportDto> toShortTransportDto(List<Transport> transportList) {
        return transportList.stream()
                .map(this::toShortTransportDto)
                .toList();
    }

    public TransportDto toDto(Transport transport) {
        return TransportDto.builder()
                .id(transport.getId())
                .name(transport.getName())
                .comment(transport.getComment())
                .type(transport.getType())
                .locationId(transport.getLocation().getId())
                .locationName(transport.getLocation().getName())
                .build();
    }

    public TransportViewDto toViewDto(Transport transport) {
        return TransportViewDto.builder()
                .id(transport.getId())
                .name(transport.getName())
                .comment(transport.getComment())
                .type(transport.getType())
                .locationId(transport.getLocation().getId())
                .build();
    }

    public List<TransportDto> toDto(List<Transport> transportList) {
        return transportList.stream()
                .map(this::toDto)
                .toList();
    }

    public Transport fromDto(TransportDto dto) {
        return Transport.builder()
                .id(dto.getId())
                .name(dto.getName())
                .comment(dto.getComment())
                .type(dto.getType())
                .location(Location.builder()
                        .id(dto.getLocationId())
                        .name(dto.getLocationName())
                        .build())
                .build();
    }
    public Transport fromDto(TransportEditDto dto) {
        return Transport.builder()
                .id(dto.getId())
                .name(dto.getName())
                .comment(dto.getComment())
                .type(dto.getType())
                .location(Location.builder()
                        .id(dto.getLocationId())
                        .build())
                .build();
    }
    public Transport fromDto(TransportViewDto dto) {
        return Transport.builder()
                .id(dto.getId())
                .name(dto.getName())
                .comment(dto.getComment())
                .type(dto.getType())
                .location(Location.builder()
                        .id(dto.getLocationId())
                        .build())
                .build();
    }

    public Transport fromDto(TransportSaveDto dto) {
        return Transport.builder()
                .name(dto.getName())
                .comment(dto.getComment())
                .type(dto.getType())
                .location(Location.builder()
                        .id(dto.getLocationId())
                        .build())
                .build();
    }

    public List<Transport> allFromDto(List<TransportDto> list) {
        return list.stream()
                .map(this::fromDto)
                .toList();
    }

    public PageDto<TransportDto> pageToDto(Page<Transport> pageTransport) {
        PageDto<TransportDto> pageDto = (PageDto<TransportDto>) pageMapper.toDto(pageTransport);
        pageDto.setContent(toDto(pageTransport.getContent()));
        return pageDto;
    }
}
