package com.example.transport2.mapper;

import com.example.transport2.dto.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageMapper {
    public PageDto<?> toDto(Page<?> page) {
        return PageDto.builder()
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .last(page.isLast())
                .build();
    }
}
