package com.example.transport2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransportType {
    BUS("Автобус", "автобуса", "автобусов"),
    TROLLEYBUS("Троллейбус", "троллейбуса", "троллейбусов"),
    TRAM("Трамвай", "трамвая", "трамваев");
    private String description;
    private String descriptionA;
    private String descriptionOf;
}