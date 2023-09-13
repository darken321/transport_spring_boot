package com.example.transport2.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PageDto<T> {
    List<T> content;
    int currentPage;
    int totalPages;
    boolean last;
    int size;
    long totalElements;

}
