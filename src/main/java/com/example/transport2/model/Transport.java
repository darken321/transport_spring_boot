package com.example.transport2.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

/** Класс описывает транспорт
 * transportName - название транспорта
 * transportType - тип автобус, троллейбус, трамвай
 * transportNumber - номер транспорта (маршрута)
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
    String name;
    @Enumerated(EnumType.STRING)
    TransportType type;
}