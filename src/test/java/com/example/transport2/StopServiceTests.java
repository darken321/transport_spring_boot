package com.example.transport2;

import static org.assertj.core.api.Assertions.*;

import com.example.transport2.model.Location;
import com.example.transport2.repository.LocationRepository;
import com.example.transport2.repository.StopRepository;
import com.example.transport2.service.stop.StopService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.transport2.model.Stop;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class StopServiceTests {
    @Autowired
    StopService stopService;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    StopRepository stopRepository;

    @Test
    @Transactional
    void saveSuccessFullTest(){
//        org.assertj.core.api.Assertions.assertThat(10).isEqualTo(10);
//        org.junit.jupiter.api.Assertions.assertEquals(10,10);
        //Создал location и сохранил в БД
        Location location = Location.builder()
                .name("Брест")
                .build();
        locationRepository.save(location);

        //Создал остановку и сохранил в БД вместе с location
        Stop expectedStop = Stop.builder()
                .name("тест")
                .location(location)
                .build();
        //Сохранили в actualStop то, что вернул метод save
        Stop actualStop = stopService.save(expectedStop);

        //Протестировал actualStop
        assertThat(actualStop).isNotNull();
        assertThat(actualStop.getId()).isNotNull().isGreaterThan(0);
        assertThat(actualStop.getName()).isEqualTo(expectedStop.getName());

        Stop actualDbStop = stopRepository.findStopById(actualStop.getId()).orElseThrow();
        assertThat(actualDbStop.getName()).isEqualTo(expectedStop.getName());
        assertThat(stopRepository.findStopById(actualStop.getId())).isNotEmpty().get().usingRecursiveComparison().isEqualTo(actualStop);
    }
}
