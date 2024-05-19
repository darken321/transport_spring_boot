package com.example.transport2.model;

import com.example.transport2.util.StringPatterns;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@EqualsAndHashCode(exclude = {"id", "comment"})
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = false)
    @Pattern(regexp = StringPatterns.STOP_NAME_PATTERN, message = "поле 'название остановки' должно соответствовать ")
    String name;

    @NotNull
    String comment;

    @ManyToOne
    @NonNull
    Location location;
}
