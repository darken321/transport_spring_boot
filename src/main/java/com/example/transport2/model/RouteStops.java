package com.example.transport2.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Класс описывает остановки маршрута
 * route       -- айдишник маршрута
 * stop        -- айди остановки
 * stopOrder   -- порядковый номер остановки в маршруте
 */
@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteStops {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    TransportRoute route;
    @ManyToOne
    Stop stop;
    Integer stopOrder; //TODO индексы или список и почему
}