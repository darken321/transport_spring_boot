package com.example.transport2.model;

import com.example.transport2.util.StringPatterns;
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
    @Pattern(regexp = StringPatterns.STOP_NAME_PATTERN, message = "поле должно соответствовать ")
    String name;
    @ManyToOne
    @NonNull
    Location location;
}
