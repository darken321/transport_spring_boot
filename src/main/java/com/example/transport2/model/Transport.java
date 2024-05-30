package com.example.transport2.model;

import com.example.transport2.util.StringPatterns;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Класс описывает транспорт
 * name - название транспорта
 * type - тип автобус, троллейбус, трамвай
 * location - местоположение, город
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = {"id", "comment"})
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank
    @Pattern(regexp = StringPatterns.STOP_NAME_PATTERN)
    String name;

    @NotNull
    String comment;

    @Enumerated(EnumType.STRING)
    TransportType type;
}