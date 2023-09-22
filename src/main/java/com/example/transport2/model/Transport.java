package com.example.transport2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NotBlank
    @Pattern(regexp = "^[а-яА-ЯёЁ0-9\\-]+(\\s[а-яА-ЯёЁ0-9\\-]+)*$")
    String name;
    @Enumerated(EnumType.STRING)
    TransportType type;
    @ManyToOne
    @NonNull
    Location location;
}