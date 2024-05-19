package com.example.transport2.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * класс описывает маршрут
 * transport - айдишник транспортного средства;
 * startStop - айди начальной остановки;
 * endStop - айди конечной остановки;
 * name - название маршрута;
 * note - заметка, комментарий по маршруту;
 * mapImage - картинка маршрута;
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
    @NonNull
    Transport transport;

    @ManyToOne
    @NonNull
    Stop startStop;

    @ManyToOne
    @NonNull
    Stop endStop;

    String name;
}