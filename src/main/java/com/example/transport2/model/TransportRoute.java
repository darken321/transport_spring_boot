package com.example.transport2.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * класс описывает маршрут
 * transport   - айдишник транспортного средства
 * startStop   - айди начальной остановки
 * endStop     - айди конечной остановки
 * name   - название маршрута
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransportRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    Transport transport;
    @ManyToOne
    Stop startStop;
    @ManyToOne
    Stop endStop;
    String name;

}