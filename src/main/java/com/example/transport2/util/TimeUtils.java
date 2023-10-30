package com.example.transport2.util;

import lombok.experimental.UtilityClass;

import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 * утилиты для преобразования времени
 */
@UtilityClass
public class TimeUtils {
    /**
     * Преобразует разницу двух времен в строку "Через ЧЧ часов ММ мин."
     *
     * @param arrivalTime время прибытия
     * @param currentTime текущее время
     * @return
     */
    public static String timeToArrival(Time arrivalTime, Time currentTime) {
        long minutes = getToArrivalMinutes(arrivalTime, currentTime);
        long hours = getToArrivalHours(arrivalTime, currentTime);
        return "Через " + ((hours != 0) ? hours + " ч. " : "") + minutes + " мин.";
    }

    /**
     * Преобразует разницу минут в int между двумя временами
     *
     * @param arrivalTime время прибытия
     * @param currentTime текущее время
     * @return
     */
    public static int getToArrivalMinutes(Time arrivalTime, Time currentTime) {
        long millisecondsDiff = arrivalTime.getTime() - currentTime.getTime();
        return (int) (millisecondsDiff / (60 * 1000)) - getToArrivalHours(arrivalTime, currentTime) * 60;
    }

    /**
     * Преобразует разницу часов в int между двумя временами
     *
     * @param arrivalTime время прибытия
     * @param currentTime текущее время
     * @return
     */
    public static int getToArrivalHours(Time arrivalTime, Time currentTime) {
        long millisecondsDiff = arrivalTime.getTime() - currentTime.getTime();
        return (int) (millisecondsDiff / (60 * 60 * 1000));
    }

    /**
     * Обрезает время до часов минут и преобразует в строку
     *
     * @param time время, которое нужно преобразовать
     * @return строку времени в формате HH:mm
     */
    public static String timeCutToSting(Time time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(time);
    }
}