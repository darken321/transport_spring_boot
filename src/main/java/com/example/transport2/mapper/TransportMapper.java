package com.example.transport2.mapper;

import com.example.transport2.dto.TransportDto;
import com.example.transport2.model.Transport;
import org.springframework.stereotype.Component;

@Component
public class TransportMapper {
    public TransportDto toDto(Transport transport) {
        return TransportDto.builder()
                .id(transport.getId())
                .name(transport.getName())
                .type(transport.getType())
                .build();
    }
}
