package com.example.transport2.util;

/**
 * Утилита обрезает пробелы у строковых полей DTO перед сохранением в БД
 */
public class DtoUtils {

    public static <T extends HasNameLocationComment> T trimNameLocationComment(T dto) {
        if (dto.getName() != null) {
            dto.setName(dto.getName().trim());
        }
        if (dto.getLocation() != null) {
            dto.setLocation(dto.getLocation().trim());
        }
        if (dto.getComment() != null) {
            dto.setComment(dto.getComment().trim());
        }
        return dto;
    }

    public static <T extends HasNameLocation> T trimNameLocation(T dto) {
        if (dto.getName() != null) {
            dto.setName(dto.getName().trim());
        }
        if (dto.getLocation() != null) {
            dto.setLocation(dto.getLocation().trim());
        }
        return dto;
    }


    public static <T extends HasName> T trimName(T dto) {
        if (dto.getName() != null) {
            dto.setName(dto.getName().trim());
        }
        return dto;
    }
}