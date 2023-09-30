package com.example.transport2.util;

import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.time.LocalTime;

@UtilityClass
public class TimeUtils {
    /**
     * Метод возвращает время до прибытия транспорта в расчете от текущего момента
     * @param arrivalTime время до прибытия транспорта
     */

    public static String getArrivalTime(LocalTime arrivalTime) {
        long minutes = getArrivalMinutes(arrivalTime);
        long hours = getArrivalHours(arrivalTime);
        return "Через " + ((hours != 0) ? hours + " ч. " : "") +  minutes + " мин.";
    }

    public static long getArrivalMinutes(LocalTime arrivalTime) {
        LocalTime currentTime = LocalTime.now();
        Duration remainingTime = Duration.between(currentTime, arrivalTime);
        return remainingTime.toMinutes() % 60;
    }

    public static long getArrivalHours(LocalTime arrivalTime) {
        LocalTime currentTime = LocalTime.now();
        Duration remainingTime = Duration.between(currentTime, arrivalTime);
        return remainingTime.toHours();
    }

}