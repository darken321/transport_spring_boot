package com.example.transport2.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Класс описисывает остановку
 * stopName - название остановки
 * coordinate координаты остановки
 */

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    @Embedded
    Coordinate coordinate;
}

