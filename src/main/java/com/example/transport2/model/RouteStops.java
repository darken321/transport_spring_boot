package com.example.transport2.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Класс описывает остановки маршрута
 * route       -- айдишник маршрута
 * stop        -- айди остановки
 * distance    -- расстояние от первой остановки
 * stopOrder   -- порядковый номер остановки в маршруте
 */

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteStops {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    @NonNull
    TransportRoute route;
    @ManyToOne
    @NonNull
    Stop stop;
    Integer distance;
    @NonNull
    Integer stopOrder;
    }