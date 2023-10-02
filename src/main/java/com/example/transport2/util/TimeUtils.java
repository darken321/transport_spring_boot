package com.example.transport2.util;

import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.time.LocalTime;

@UtilityClass
public class TimeUtils {
    /**
     * Метод возвращает время до прибытия транспорта в расчете от текущего момента
     */

    public static String timeToArrival(LocalTime arrivalTime, LocalTime currentTime) {
        long minutes = getToArrivalMinutes(arrivalTime, currentTime);
        long hours = getToArrivalHours(arrivalTime, currentTime);
        return "Через " + ((hours != 0) ? hours + " ч. " : "") +  minutes + " мин.";
    }

    public static long getToArrivalMinutes(LocalTime arrivalTime, LocalTime currentTime) {
        Duration remainingTime = Duration.between(currentTime, arrivalTime);
        return remainingTime.toMinutes() % 60;
    }

    public static long getToArrivalHours(LocalTime arrivalTime, LocalTime currentTime) {
        Duration remainingTime = Duration.between(currentTime, arrivalTime);
        return remainingTime.toHours();
    }

}