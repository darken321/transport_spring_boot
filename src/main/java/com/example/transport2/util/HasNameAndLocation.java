package com.example.transport2.util;

/**
 * Интерфейс для обрезки строк в DTO
 */
public interface HasNameAndLocation {
    String getName();
    void setName(String name);
    String getLocation();
    void setLocation(String location);
}
