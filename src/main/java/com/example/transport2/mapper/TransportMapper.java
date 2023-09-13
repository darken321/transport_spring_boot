package com.example.transport2.mapper;

import com.example.transport2.dto.PageDto;
import com.example.transport2.dto.StopDto;
import com.example.transport2.dto.TransportDto;
import com.example.transport2.model.Stop;
import com.example.transport2.model.Transport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransportMapper {
    private final PageMapper pageMapper;

    public TransportDto toDto(Transport transport) {
        return TransportDto.builder()
                .id(transport.getId())
                .name(transport.getName())
                .type(transport.getType())
                .location(transport.getLocation())
                .build();
    }

    public List<TransportDto> allToDto(List<Transport> transportList) {
        List<TransportDto> list = new ArrayList<>();
        for (Transport transport : transportList) {
            list.add(TransportDto.builder()
                    .id(transport.getId())
                    .name(transport.getName())
                    .location(transport.getLocation())
                    .build());
        }
        return list;
    }

    public PageDto<TransportDto> pageToDto(Page<Transport> pageTransport) {
        PageDto<TransportDto> pageDto = (PageDto<TransportDto>) pageMapper.toDto(pageTransport);
        pageDto.setContent(allToDto(pageTransport.getContent()));
        return pageDto;
    }
}
