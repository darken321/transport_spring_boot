package com.example.transport2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Класс описисывает остановку
 * name - название остановки
 * location - местоположение, город
 */

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Pattern(regexp = "^[а-яА-ЯёЁ0-9\\-]+(\\s[а-яА-ЯёЁ0-9\\-]+)*$")
//    @Pattern(regexp = "[а-яА-ЯёЁ0-9-]")
    String name;
    @ManyToOne
    @NonNull
    Location location;
}
