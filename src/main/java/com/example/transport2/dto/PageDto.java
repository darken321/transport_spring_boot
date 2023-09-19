package com.example.transport2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull
    List<T> content;

    @Positive
    int currentPage;

    @Positive
    int totalPages;

    boolean last;

    @Positive
    int size;

    @Positive
    long totalElements;

}
