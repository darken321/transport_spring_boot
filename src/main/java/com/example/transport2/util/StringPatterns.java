package com.example.transport2.util;

public interface StringPatterns {
    //в названии может быть латинские или символы кирилицы, цифры и знаки № и дефис
    // не начинается и не заканчивается пробелом
    String STOP_NAME_PATTERN ="^[а-яА-ЯёЁ0-9a-zA-Z№\\-]+(\\s[а-яА-ЯёЁ0-9a-zA-Z№\\-]+)*$";
}
