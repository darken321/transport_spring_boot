package com.example.transport2.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/** Класс описывает транспорт
 * transportName - название транспорта
 * transportType - тип автобус, троллейбус, трамвай
 * transportNumber - номер транспорта (маршрута)
 */
@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String transportName;
    @Enumerated(EnumType.STRING)
    TransportType transportType;//TODO тип транспорта enum и номер string
    Integer transportNumber;

}