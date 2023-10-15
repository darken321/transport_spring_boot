package com.example.transport2.service;

import com.example.transport2.model.Stop;
import com.example.transport2.projection.StopTransportInfo;
import com.example.transport2.repository.StopRepository;
import com.example.transport2.repository.StopTimeRepository;
import com.example.transport2.util.Constants;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StopService {
    private final StopRepository stopRepository;
    private final StopTimeRepository stopTimeRepository;
    public List<Stop> getAll() {
        return stopRepository.findAll();
    }

    public List<Stop> findAllByNameContaining(String name) {
        return stopRepository.findAllByNameContaining(name);
    }

    public Stop getById(Integer id) {
        return stopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("stop with id " + id + " not found in DB"));
    }

    public Stop save(@Valid Stop stop) {

        if (stopRepository.existsByNameLikeIgnoreCase(stop.getName())) {
            throw new EntityExistsException("Остановка " + stop.getName() + " уже есть в базе данных");
        }
        return stopRepository.save(stop);
    }

    public void delete(int id) {
        stopRepository.deleteById(id);
    }

    public List<StopTransportInfo> getInfoById(Integer id) {
        return stopTimeRepository.findSortedArrivalTimes(
                id,
                LocalDate.now().getDayOfWeek().name(),
                Time.valueOf( LocalTime.now()),
                Constants.RECORDS_NUMBER);
    }
}
