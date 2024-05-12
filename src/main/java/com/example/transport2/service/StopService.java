package com.example.transport2.service;

import com.example.transport2.model.Location;
import com.example.transport2.model.Stop;
import com.example.transport2.model.Transport;
import com.example.transport2.repository.StopRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StopService {
    private final StopRepository stopRepository;

    public Stop save(@Valid Stop stop) {
        trimStopFields(stop);
        // Проверяем, существует ли уже остановка с таким же именем в том же городе
        List<Stop> existingStops = stopRepository.findByNameIgnoreCaseAndLocation(stop.getName(), stop.getLocation());

        if (!existingStops.isEmpty()) {
            throw new EntityExistsException("Остановка с названием " + stop.getName() + " уже существует в городе " + stop.getLocation().getName());
        }
        // Если остановка с таким именем в этом городе не найдена, сохраняем новую остановку
        return stopRepository.save(stop);
    }

    public Stop getById(Integer id) {
        return stopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("stop with id " + id + " not found in DB"));
    }

    public List<Stop> getAll() {
        return stopRepository.findAll();
    }

    public List<Stop> findAllByNameContaining(String name) {
        return stopRepository.findAllByNameContainingIgnoreCase(name);
    }

    public Stop update(@Valid Stop stop) {
        trimStopFields(stop);
        if (stopRepository.findById(stop.getId()).isEmpty()) {
            throw new EntityNotFoundException("Остановка с id " + stop.getId() + " не найдена");
        }
        return this.save(stop);
    }

    public void delete(int id) {
        stopRepository.deleteById(id);
    }

    private void trimStopFields(Stop stop) {
        if (stop.getName() != null) {
            stop.setName(stop.getName().trim());
        }
        if (stop.getComment() != null) {
            stop.setComment(stop.getComment().trim());
        }
    }
}
