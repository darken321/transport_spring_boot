package com.example.transport2;

import com.example.transport2.model.*;
import com.example.transport2.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.sql.Time;

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

//    @PostConstruct
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
                "Областная больница",
                "Тельминский лес",
                "Восточный микрорайон",
                "Детский городок",
                "Парк Воинов-интернац-ов",//5
                "Технический университет",
                "Богданчука",
                "Завод",
                "ЦМТ",
                "Зеленая",//10
                "МОПРа",
                "Проспект Машерова",
                "ЦУМ",
                "Ленина",//14
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
                "Мытная", //26
                "Новая",
                "Грибоедова",
                "Театр",
                "Интурист",//30
                "Музей спасенных ценностей",
                "Карла Маркса",
                "Гимназия №1",
                "Промстройбанк",
                "Автовокзал",//35
                "Комсомольская",
                "Маяковского",
                "Спортшкола" //38
        ));

        for (String s : stopList) {
            stop = Stop.builder()
                    .location(locationRepository.findByName("Брест").orElseThrow())
                    .name(s)
                    .build();
            stopRepository.save(stop);
        }

        //Вставка значений в таблицу transport_route - маршруты транспорта, 100 и 101 троллейбус
        TransportRoute transportRoute;
        int[][] routeArray = new int[4][3];
        routeArray[0] = new int[]{1, 1, 26};
        routeArray[1] = new int[]{1, 26, 1};
        routeArray[2] = new int[]{2, 1, 35};
        routeArray[3] = new int[]{2, 35, 1};

        for (int[] ints : routeArray) {
            transportRoute = TransportRoute.builder()
                    .transport(transportRepository.findById(ints[0]).orElseThrow())
                    .startStop(stopRepository.findById(ints[1]).orElseThrow())
                    .endStop(stopRepository.findById(ints[2]).orElseThrow())
                    .build();
            transportRouteRepository.save(transportRoute);
        }
        //100 троллейбус туда обратно
        int[][] routeStopsArray = new int[86][2];
        for (int i = 1; i <= 26; i++) {
            routeStopsArray[i - 1] = new int[]{1, i};
            routeStopsArray[26 + i - 1] = new int[]{2, 26 - i + 1};
        }
        //101 троллейбус туда +17
        for (int i = 1; i <= 13; i++) {
            routeStopsArray[i + 52 - 1] = new int[]{3, i};
        }
        routeStopsArray[65] = new int[]{3, 32};
        routeStopsArray[66] = new int[]{3, 33};
        routeStopsArray[67] = new int[]{3, 34};
        routeStopsArray[68] = new int[]{3, 35};

        //101 троллейбус обратно
        routeStopsArray[69] = new int[]{4, 35};
        routeStopsArray[70] = new int[]{4, 36};
        routeStopsArray[71] = new int[]{4, 37};
        routeStopsArray[72] = new int[]{4, 38};
        routeStopsArray[73] = new int[]{4, 13};
        routeStopsArray[74] = new int[]{4, 12};
        routeStopsArray[75] = new int[]{4, 11};
        routeStopsArray[76] = new int[]{4, 10};
        routeStopsArray[77] = new int[]{4, 9};
        routeStopsArray[78] = new int[]{4, 8};
        routeStopsArray[79] = new int[]{4, 7};
        routeStopsArray[80] = new int[]{4, 6};
        routeStopsArray[81] = new int[]{4, 5};
        routeStopsArray[82] = new int[]{4, 4};
        routeStopsArray[83] = new int[]{4, 3};
        routeStopsArray[84] = new int[]{4, 2};
        routeStopsArray[85] = new int[]{4, 1};

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
        for (int k = 1; k <= routeStopsArray.length; k++) { // цикл по route_stops 86 строк
            for (DayOfWeek day : DayOfWeek.values()) { //дни недели
                for (int j = 0; j < 10; j++) { //время прибытия рандом, 10 раз на одну остановку
                    stopTime = StopTime.builder()
                            .routeStops(routeStopRepository.findById(k).orElseThrow())
                            .dayOfWeek(day)
                            .time(Time.valueOf(LocalTime.of(random.nextInt(24), random.nextInt(60))))
                            .build();
                    stopTimeRepository.save(stopTime);
                }
            }
        }
    }
}