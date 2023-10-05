package com.example.transport2.util;

import lombok.experimental.UtilityClass;

import java.sql.Time;

@UtilityClass
public class TimeUtils {
    /**
     * Метод возвращает время до прибытия транспорта в расчете от текущего момента
     */

    public static String timeToArrival(Time arrivalTime, Time currentTime) {
        long minutes = getToArrivalMinutes(arrivalTime, currentTime);
        long hours = getToArrivalHours(arrivalTime, currentTime);
        return "Через " + ((hours != 0) ? hours + " ч. " : "") + minutes + " мин.";
    }

    public static int getToArrivalMinutes(Time arrivalTime, Time currentTime) {
        long millisecondsDiff = arrivalTime.getTime() - currentTime.getTime();
        return (int) (millisecondsDiff / (60 * 1000)) - getToArrivalHours(arrivalTime, currentTime)*60;
    }

    public static int getToArrivalHours(Time arrivalTime, Time currentTime) {
        long millisecondsDiff = arrivalTime.getTime() - currentTime.getTime();
        return (int) (millisecondsDiff / (60 * 60 * 1000));
    }
}