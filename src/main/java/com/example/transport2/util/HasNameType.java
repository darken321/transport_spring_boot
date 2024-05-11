package com.example.transport2.util;

import com.example.transport2.model.TransportType;

/**
 * Интерфейс для обрезки строк в DTO
 */
public interface HasNameType {
    String getName();
    void setName(String location);
    TransportType getType();
    void setType(TransportType location);
}
