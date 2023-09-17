package com.example.transport2;

import com.example.transport2.model.*;
import com.example.transport2.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.example.transport2.model.TransportType.BUS;
import static com.example.transport2.model.TransportType.TROLLEYBUS;

@Slf4j
@AllArgsConstructor
@Component
public class PopulateDB {

    private final LocationRepository locationRepository;
    private final TransportRepository transportRepository;
    private final StopRepository stopRepository;
    private final TransportRouteRepository transportRouteRepository;
    private final RouteStopRepository routeStopRepository;
    private final StopTimeRepository stopTimeRepository;

    @PostConstruct
    public void init() {
        //Добавляю город
        Location location;
        location = Location.builder()
                .name("Брест")
                .build();
        locationRepository.save(location);

        //Заношу transport
        Transport transport;
        for (int i = 0; i <= 9; i++) {
            transport = Transport.builder()
                    .location(locationRepository.findByName("Брест").orElseThrow())
                    .type(TROLLEYBUS)
                    .name("10" + i)
                    .build();
            transportRepository.save(transport);
        }

//        List<String> autobus = new ArrayList<>(Arrays.asList("1", "1А", "2", "2А", "3", "5", "6"));
        List<String> autobus = new ArrayList<>(Arrays.asList("2", "2А", "1А", "1", "3", "5", "6"));
        for (String s : autobus) {
            transport = Transport.builder()
                    .location(locationRepository.findByName("Брест").orElseThrow())
                    .type(BUS)
                    .name(s)
                    .build();
            transportRepository.save(transport);
        }

        //Заношу остановки - stop
        Stop stop;
        List<String> stopList = new ArrayList<>(Arrays.asList(
                "Тельминский лес",
                "Восточный микрорайон",
                "Детский городок",
                "Парк Воинов-интернац-ов",
                "Технический университет",
                "Богданчука",
                "Завод",
                "ЦМТ",
                "Зеленая",
                "МОПРа",
                "Проспект Машерова",
                "ЦУМ",
                "Ленина",
                "Сквер Иконикова",
                "Стадион",
                "БТИ",
                "Крепость",
                "Соя",
                "Береговая",
                "Луговая",
                "Варшавское шоссе",
                "Махновича",
                "Грюнвальдская",
                "Благовещенская",
                "Мытная",
                "Новая",
                "Театр",
                "Интурист",
                "Музей спасенных ценностей"));

        for (String s : stopList) {
            stop = Stop.builder()
                    .location(locationRepository.findByName("Брест").orElseThrow())
                    .name(s)
                    .build();
            stopRepository.save(stop);
        }

        //Вставка значений в таблицу transport_route - маршруты транспорта
        TransportRoute transportRoute;
        int[][] routeArray = new int[6][3];
        routeArray[0] = new int[]{1, 3, 8};
        routeArray[1] = new int[]{1, 8, 3};
        routeArray[2] = new int[]{2, 1, 4};
        routeArray[3] = new int[]{2, 4, 1};
        routeArray[4] = new int[]{3, 5, 10};
        routeArray[5] = new int[]{3, 10, 5};
        for (int[] ints : routeArray) {
            transportRoute = TransportRoute.builder()
                    .transport(transportRepository.findById(ints[0]).orElseThrow())
                    .startStop(stopRepository.findById(ints[1]).orElseThrow())
                    .endStop(stopRepository.findById(ints[2]).orElseThrow())
                    .build();
            transportRouteRepository.save(transportRoute);
        }
        int[][] routeStopsArray = new int[12][2];
        routeStopsArray[0] = new int[]{1, 3};
        routeStopsArray[1] = new int[]{1, 4};
        routeStopsArray[2] = new int[]{1, 5};
        routeStopsArray[3] = new int[]{1, 6};
        routeStopsArray[4] = new int[]{1, 7};
        routeStopsArray[5] = new int[]{1, 8};

        routeStopsArray[6] = new int[]{2, 8};
        routeStopsArray[7] = new int[]{2, 7};
        routeStopsArray[8] = new int[]{2, 6};
        routeStopsArray[9] = new int[]{2, 5};
        routeStopsArray[10] = new int[]{2, 4};
        routeStopsArray[11] = new int[]{2, 3};

        //Вставка значений в таблицу route_stops остановки маршрутов
        RouteStops routeStops;
        int i = 1;
        for (int j = 0; j < routeStopsArray.length; j++) {
            if ((j > 0) && (routeStopsArray[j][0] != routeStopsArray[j - 1][0])) i = 1;
            int[] stops = routeStopsArray[j];
            routeStops = RouteStops.builder()
                    .route(transportRouteRepository.findById(stops[0]).orElseThrow())
                    .stop(stopRepository.findById(stops[1]).orElseThrow())
                    .stopOrder(i++)
                    .build();
            routeStopRepository.save(routeStops);
        }

        //вставка значений в таблицу stop_time - время прибытия по остановкам маршрутов
        Random random = new Random();
        StopTime stopTime;
        for (int k = 1; k <= 5; k++) { //цикл по остановкам маршрута
            for (DayOfWeek day : DayOfWeek.values()) { //дни недели
                for (int j = 0; j < 10; j++) { //время прибытия рандом
                    stopTime = StopTime.builder()
                            .time(LocalTime.of(random.nextInt(24), random.nextInt(60)))
                            .dayOfWeek(day)
                            .routeStops(routeStopRepository.findById(k).orElseThrow())
                            .build();
                    stopTimeRepository.save(stopTime);
                }
            }
        }
    }
}